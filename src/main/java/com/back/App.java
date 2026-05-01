package com.back;

import com.back.domain.system.controller.SystemController;
import com.back.domain.wiseSaying.controller.WiseSayingController;

import java.util.Scanner;

public class App {
    private Scanner scanner;
    public App(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {
        System.out.println("== 명언 앱 ==");

        WiseSayingController wiseSayingController = new WiseSayingController(scanner); // WiseSaying컨트롤러: 사용자와 명언게시판 관련 상호작용하는 객체 (입출력 담당)
        SystemController systemController = new SystemController(); // 시스템컨트롤러: 프로그램 종료 담당 객체

        while(true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine();

            Rq rq = new Rq(cmd); // 리퀘스트 객체 생성: 사용자의 명령을 담는 객체 (명령어 분석 담당)

            switch (rq.getActionName()) {
                case "등록" -> wiseSayingController.actionWrite();
                case "목록" -> wiseSayingController.actionList(rq);
                case "삭제" -> wiseSayingController.actionDelete(rq);
                case "수정" -> wiseSayingController.actionModify(rq);
                case "종료" -> {
                    systemController.actionExit();
                    return;
                }
            }
        }
    }
}
