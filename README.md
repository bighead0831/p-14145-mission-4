# 명언게시판

- 콘솔 기반 명언 관리 애플리케이션
- Controller-Service-Repository (3-Layer Architecture)로 구성
- 쿼리스트링 방식의 명령어를 통해 명언의 등록·조회·수정·삭제·검색·페이징 기능 구현

---

## 프로젝트 구조

```
src/
├── main/java/com/back/
│   ├── Main.java                          # 진입점
│   ├── App.java                           # 명령 라우팅
│   ├── Rq.java                            # 명령어 파싱
│   ├── WiseSaying.java                    # 데이터 모델
│   └── domain/
│       ├── system/controller/
│       │   └── SystemController.java      # 종료 처리
│       └── wiseSaying/
│           ├── controller/WiseSayingController.java
│           ├── service/WiseSayingService.java
│           └── repository/WiseSayingRepository.java
└── test/java/com/back/
    ├── AppTest.java
    ├── AppTestRunner.java
    ├── domain/wiseSaying/controller/WiseSayingControllerTest.java
    └── standard/util/TestUtil.java
```

---

## 아키텍처

```
사용자 입력
    │
    ▼
Rq  ──────────────── 명령어 파싱 (actionName + params)
    │
    ▼
App ──────────────── 명령 라우팅
    │
    ▼
Controller ────────── 입출력 처리
    │
    ▼
Service ───────────── 비즈니스 로직
    │
    ▼
Repository ────────── 데이터 접근 (In-Memory)
```

### 계층별 역할

| 계층 | 클래스 | 역할 |
|------|--------|------|
| Controller | `WiseSayingController` | 사용자 입출력, 유효성 검사 |
| Service | `WiseSayingService` | 비즈니스 로직 조합 |
| Repository | `WiseSayingRepository` | 데이터 저장/조회/수정/삭제 |
| 파싱 | `Rq` | 명령어 문자열 파싱 |

---

## 명령어 명세

| 명령어 | 파라미터 | 설명 |
|--------|---------|------|
| `등록` | - | 명언/작가 입력 후 저장 |
| `목록` | `page`, `keywordType`, `keyword` | 명언 목록 조회 |
| `삭제?id={id}` | `id` (필수) | 해당 ID 명언 삭제 |
| `수정?id={id}` | `id` (필수) | 해당 ID 명언 수정 |
| `종료` | - | 프로그램 종료 |

### 사용 예시

```
등록
목록
목록?page=2
목록?keywordType=author&keyword=소크라테스
목록?keywordType=content&keyword=행복&page=1
삭제?id=3
수정?id=5
종료
```

---

## Rq 클래스 — 명령어 파싱

`Rq`는 사용자가 입력한 명령어 문자열을 분석해 `actionName`과 파라미터 맵으로 분리한다.

**파싱 형식:** `actionName?key1=value1&key2=value2`

```
목록?keywordType=author&keyword=작자&page=2
  │                   └─────────────────── params Map
  └─────────────────────────────────────── actionName
```

**주요 메서드**

| 메서드 | 반환 타입 | 설명 |
|--------|---------|------|
| `getActionName()` | String | 명령어명 반환 |
| `getParam(name, defaultValue)` | String | 문자열 파라미터 반환 |
| `getParamInt(name, defaultValue)` | int | 정수 파라미터 반환 |

파싱에는 Stream API를 사용하며, 값이 없거나 빈 파라미터는 자동으로 필터링된다.

---

## 검색 기능

`keywordType`과 `keyword` 파라미터로 동작한다.

| keywordType | 검색 대상 | 방식 |
|-------------|---------|------|
| `author` | 작가명 | 부분 일치 (`contains`) |
| `content` | 명언 내용 | 부분 일치 (`contains`) |

**흐름:**

```
Controller: keywordType 유효성 검사 (author / content만 허용)
    ↓
Service.findForList(keywordType, keyword)
    ↓
Repository.findKeyword() → contains로 필터링
    ↓
결과 리스트 반환
```

검색 결과는 페이징과 조합 가능하다 (`목록?keywordType=content&keyword=삶&page=1`).

---

## 페이징 기능

`page` 파라미터로 동작하며 기본값은 1이다. 페이지당 5개 항목을 최신순으로 표시한다.

**계산 로직:**

```
총 페이지 수 = 전체 개수 / 5 + (나머지 > 0 ? 1 : 0)
시작 인덱스 = 5 * (page - 1)
종료 인덱스 = min(시작 인덱스 + 5, 전체 개수)
```

**페이지 네비게이션 출력 형식:**

```
[1] / 2 / 3   ← 현재 페이지는 대괄호
```

페이지 번호가 범위를 초과하면 에러 메시지를 출력하고 종료한다.

---

## 데이터 모델

### WiseSaying

| 필드 | 타입 | 설명 |
|------|------|------|
| `id` | int | 자동 증가 고유 번호 |
| `content` | String | 명언 내용 |
| `author` | String | 작가명 |

ID는 `WiseSayingRepository`의 static 변수 `lastId`로 관리하며, `save()` 호출 시 1씩 증가한다.

저장소는 인메모리(`List<WiseSaying>`) 기반으로, 프로그램 종료 시 데이터가 초기화된다.

---

## 테스트

`AppTestRunner`가 System.out을 ByteArrayOutputStream으로 리다이렉트해 출력 결과를 String으로 캡처한다. 모든 테스트는 `"종료"` 명령이 자동 append된다.

| 테스트 클래스 | 주요 커버리지 |
|-------------|-------------|
| `AppTest` | 앱 실행, 종료, 기본 등록/목록 |
| `WiseSayingControllerTest` | CRUD, 에러 처리, 검색, 페이징, 통합 |
