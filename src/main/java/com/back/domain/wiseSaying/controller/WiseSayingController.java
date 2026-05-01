package com.back.domain.wiseSaying.controller;

import com.back.Rq;
import com.back.WiseSaying;
import com.back.domain.wiseSaying.service.WiseSayingService;

import java.util.Scanner;

/* --- 홀 | 서빙직원 --- */
// Controller: 사용자와 상호작용하는 역사 (입출력 담당)
public class WiseSayingController {
    private final WiseSayingService wiseSayingService; // 서비스: 사용자의 요청을 처리하는 객체 (비즈니스 로직 담당)
    private Scanner scanner;

    public WiseSayingController(Scanner scanner) {
        wiseSayingService = new WiseSayingService();
        this.scanner = scanner;
    }

    public void actionWrite() {
        System.out.print("명언 : ");
        String content = scanner.nextLine();
        System.out.print("작가 : ");
        String author = scanner.nextLine();

        WiseSaying ws = wiseSayingService.write(content, author);
        System.out.println(String.format("%d번 명언이 등록되었습니다.", ws.getId()));
    }

    public void actionList(Rq rq) {
        String keywordType = rq.getParam("keywordType", "");
        String keyword = rq.getParam("keyword", "");
        if (!keywordType.isBlank() && !keywordType.equals("author") && !keywordType.equals("content")) {
            System.out.println("검색타입은 author 또는 content만 입력가능합니다.");
            return;
        }

        if(keywordType.isBlank() && keyword.isBlank()) {
            System.out.println("번호 / 작가 / 명언\n----------------------");
            for (WiseSaying ws : wiseSayingService.findForList().reversed())
                System.out.println(String.format("%d / %s / %s", ws.getId(), ws.getAuthor(), ws.getContent()));
        } else if(!keywordType.isBlank() && !keyword.isBlank()) {
            System.out.println("----------------------");
            System.out.println(String.format("검색타입 : %s", keywordType));
            System.out.println(String.format("검색어 : %s", keyword));
            System.out.println("----------------------");
            System.out.println("번호 / 작가 / 명언\n----------------------");
            for (WiseSaying ws : wiseSayingService.findForList(keywordType, keyword).reversed())
                System.out.println(String.format("%d / %s / %s", ws.getId(), ws.getAuthor(), ws.getContent()));
        }
    }

    public void actionDelete(Rq rq) {
        int id = rq.getParamInt("id", 0);
        if(id==0) {
            System.out.println("id를 입력해주세요.");
            return;
        }
        if(!wiseSayingService.findById(id)) {
            System.out.println(String.format("%d번 명언은 존재하지 않습니다.", id));
            return;
        }

        wiseSayingService.delete(id);
        System.out.println(String.format("%d번 명언이 삭제되었습니다.", id));
    }

    public void actionModify(Rq rq) {
        int id = rq.getParamInt("id", 0);
        if(id==0) {
            System.out.println("id를 입력해주세요.");
            return;
        }
        if(!wiseSayingService.findById(id)) {
            System.out.println(String.format("%d번 명언은 존재하지 않습니다.", id));
            return;
        }

        WiseSaying ws = wiseSayingService.getById(id);

        System.out.println(String.format("명언(기존) : %s", ws.getContent()));
        System.out.print("명언 : ");
        String content = scanner.nextLine();
        System.out.println(String.format("작가(기존) : %s", ws.getAuthor()));
        System.out.print("작가 : ");
        String author = scanner.nextLine();
        wiseSayingService.modify(id, content, author);
    }
}
