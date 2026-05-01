package com.back;

import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
    @Test
    @DisplayName("[1단계] == 명언 앱 ==")
    void t1() {
        final String out = AppTestRunner.run("");

        assertThat(out)
                .contains("== 명언 앱 ==");
    }

    @Test
    @DisplayName("[1단계] 종료")
    void t2() {
        final String out = AppTestRunner.run("""
                종료
                """);

        assertThat(out)
                .contains("프로그램을 종료합니다.");
    }

    @Test
    @DisplayName("[2단계] 등록 && [3단계] 등록시 생성된 명언번호 노출")
    void t3() {
        final String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                """);

        assertThat(out)
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("[4단계] 등록할때 마다 생성되는 명언번호가 증가")
    void t4() {
        final String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                현재를 사랑하라.
                작자미상
                """);

        assertThat(out)
                .contains("1번 명언이 등록되었습니다.")
                .contains("2번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("[5단계] 목록")
    void t5() {
        final String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                """);

        assertThat(out)
                .contains("번호 / 작가 / 명언")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .contains("1 / 작자미상 / 현재를 사랑하라.");
    }
}