package com.back.domain.wiseSaying.controller;

import com.back.AppTestRunner;
//import com.back.global.app.AppConfig;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

public class WiseSayingControllerTest {
//    @Test
//    @DisplayName("`등록` 구현")
//    public void t1() {
//        final String out = AppTestRunner.run("""
//                등록
//                현재를 사랑하라.
//                작자미상
//                """);
//
//        assertThat(out)
//                .contains("명언 :")
//                .contains("작가 :");
//    }
//
//    @Test
//    @DisplayName("`등록`시 생성번호 출력 구현")
//    public void t2() {
//        final String out = AppTestRunner.run("""
//                등록
//                현재를 사랑하라.
//                작자미상
//                """);
//
//        assertThat(out)
//                .contains("1번 명언이 등록되었습니다.");
//    }
//
//    @Test
//    @DisplayName("`등록`시마다 증가되는 생성번호 출력")
//    public void t3() {
//        final String out = AppTestRunner.run("""
//                등록
//                현재를 사랑하라.
//                작자미상
//                등록
//                내 죽음을 적에게 알리지 마라.
//                이순신
//                """);
//
//        assertThat(out)
//                .contains("1번 명언이 등록되었습니다.")
//                .contains("2번 명언이 등록되었습니다.");
//    }
//
//    @Test
//    @DisplayName("`목록` 입력시 목록 출력")
//    public void t4() {
//        final String out = AppTestRunner.run("""
//                등록
//                현재를 사랑하라.
//                작자미상
//                등록
//                내 죽음을 적에게 알리지 마라.
//                이순신
//                목록
//                """);
//
//        assertThat(out)
//                .contains("""
//                        번호 / 작가 / 명언
//                        ----------------------
//                        2 / 이순신 / 내 죽음을 적에게 알리지 마라.
//                        1 / 작자미상 / 현재를 사랑하라.
//                        """);
//    }
//
//    @Test
//    @DisplayName("`삭제` 입력시 삭제 구현")
//    public void t5() {
//        final String out = AppTestRunner.run("""
//                등록
//                현재를 사랑하라.
//                작자미상
//                등록
//                내 죽음을 적에게 알리지 마라.
//                이순신
//                등록
//                네 자신을 알라.
//                소크라테스
//                삭제?id=2
//                목록
//                """);
//
//        assertThat(out)
//                .contains("2번 명언이 삭제되었습니다.")
//                .contains("""
//                        번호 / 작가 / 명언
//                        ----------------------
//                        3 / 소크라테스 / 네 자신을 알라.
//                        1 / 작자미상 / 현재를 사랑하라.
//                        """);
//    }
//
//    @Test
//    @DisplayName("`삭제` 입력시 존재하지 않는 명언삭제에 대한 예외처리")
//    public void t6() {
//        final String out = AppTestRunner.run("""
//                등록
//                현재를 사랑하라.
//                작자미상
//                등록
//                내 죽음을 적에게 알리지 마라.
//                이순신
//                등록
//                네 자신을 알라.
//                소크라테스
//                삭제?id=2
//                삭제?id=2
//                """);
//
//        assertThat(out)
//                .contains("2번 명언은 존재하지 않습니다.");
//    }
//
//    @Test
//    @DisplayName("`수정` 구현")
//    public void t7() {
//        final String out = AppTestRunner.run("""
//                등록
//                현재를 사랑하라.
//                작자미상
//                등록
//                내 죽음을 적에게 알리지 마라.
//                이순신
//                등록
//                네 자신을 알라.
//                소크라테스
//                수정?id=2
//                바람이 불어오는 곳, 그곳으로 가네.
//                김광석
//                목록
//                """);
//
//        assertThat(out)
//                .contains("명언(기존) : 내 죽음을 적에게 알리지 마라.")
//                .contains("작가(기존) : 이순신")
//                .contains("""
//                        번호 / 작가 / 명언
//                        ----------------------
//                        3 / 소크라테스 / 네 자신을 알라.
//                        2 / 김광석 / 바람이 불어오는 곳, 그곳으로 가네.
//                        1 / 작자미상 / 현재를 사랑하라.
//                        """);
//    }

    @Test
    @DisplayName("`검색` 구현")
    public void t8() {
        final String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록?keywordType=content&keyword=과거
                목록?keywordType=author&keyword=작자
                """);

        assertThat(out)
                .contains("""
                        ----------------------
                        검색타입 : content
                        검색어 : 과거
                        ----------------------
                        번호 / 작가 / 명언
                        ----------------------
                        2 / 작자미상 / 과거에 집착하지 마라.
                        """)
                .contains("""
                        ----------------------
                        검색타입 : author
                        검색어 : 작자
                        ----------------------
                        번호 / 작가 / 명언
                        ----------------------
                        2 / 작자미상 / 과거에 집착하지 마라.
                        1 / 작자미상 / 현재를 사랑하라.
                        """);
    }
}
