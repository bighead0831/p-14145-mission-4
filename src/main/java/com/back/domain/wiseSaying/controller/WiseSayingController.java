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
        int pageNum = rq.getParamInt("page", 1);

        if (!keywordType.isBlank() && !keywordType.equals("author") && !keywordType.equals("content")) {
            System.out.println("검색타입은 author 또는 content만 입력가능합니다.");
            return;
        }

        if(!keywordType.isBlank() && !keyword.isBlank())
            showKeyword(keywordType, keyword);

        showList(keywordType, keyword, pageNum);
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


    private void showKeyword(String keywordType, String keyword){
        System.out.println("----------------------");
        System.out.println(String.format("검색타입 : %s", keywordType));
        System.out.println(String.format("검색어 : %s", keyword));
        System.out.println("----------------------");
    }

    private void showList(String keywordType, String keyword, int pageNum) {
        boolean chkCreateSample = false;
        if(wiseSayingService.findForList().size()==0 && wiseSayingService.getLastId()==0) {
            chkCreateSample = true;
            for(int i=1; i<=10; i++)
                wiseSayingService.write("명언 "+i, "작자미상 "+i);
        }

        int totalPages = 1;
        WiseSaying[] pageWiseSaying;
        int cnt = 0;
        if (keywordType.isBlank() && keyword.isBlank()) {
            pageWiseSaying = new WiseSaying[wiseSayingService.findForList().size()];
            for(WiseSaying ws : wiseSayingService.findForList().reversed())
                pageWiseSaying[cnt++] = ws;
        } else {
            pageWiseSaying = new WiseSaying[wiseSayingService.findForList(keywordType, keyword).size()];
            for(WiseSaying ws : wiseSayingService.findForList(keywordType, keyword).reversed())
                pageWiseSaying[cnt++] = ws;
        }

        totalPages = pageWiseSaying.length/5 + (pageWiseSaying.length%5==0?0:1);
        if(pageNum>totalPages) {
            System.out.println("페이지번호가 존재하지 않습니다.");
            return;
        }

        System.out.println("번호 / 작가 / 명언\n----------------------");
        int lastPrintId = 5*(pageNum-1)+5<pageWiseSaying.length?5*(pageNum-1)+5:pageWiseSaying.length;
        for(int i=5*(pageNum-1); i<lastPrintId; i++) {
            System.out.println(String.format("%d / %s / %s", pageWiseSaying[i].getId(), pageWiseSaying[i].getAuthor(), pageWiseSaying[i].getContent()));
        }

        System.out.print("----------------------\n페이지 : ");
        for(int i=1; i<=totalPages; i++) {
            if(i==pageNum) System.out.printf("[%d]", i);
            else System.out.printf("%d", i);

            if(i<totalPages) System.out.print(" / ");
            else System.out.println("");
        }

        if(chkCreateSample==true) {
            for(int id=1; id<=10; id++) {
                wiseSayingService.delete(id);
                wiseSayingService.setLastId();
            }
        }
    }
}
