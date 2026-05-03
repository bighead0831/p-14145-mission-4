package com.back.domain.wiseSaying.repository;

import com.back.WiseSaying;

import java.util.ArrayList;
import java.util.List;

/* --- 식자재 창고 | 창고지기 --- */
// Repository: 데이터를 저장 및 찾아주는 역할 (데이터베이스, 파일 등과 상호작용 담당)
public class WiseSayingRepository {
    private final List<WiseSaying> wiseSayingsList;
    private static int lastId;

    public WiseSayingRepository() {
        wiseSayingsList = new ArrayList<>();
        lastId = 0;
    }

    public void save(WiseSaying ws) {
        ws.setId(++lastId);
        wiseSayingsList.add(ws);
    }

    public List<WiseSaying> findAll() {
        return wiseSayingsList;
    }

    public List<WiseSaying> findKeyword(String keywordType, String keyword) {
        List<WiseSaying> resultList = new ArrayList<>();
        for(WiseSaying ws : wiseSayingsList) {
            if(keywordType.equals("author") && ws.getAuthor().contains(keyword))
                resultList.add(ws);
            if(keywordType.equals("content") && ws.getContent().contains(keyword))
                resultList.add(ws);
        }
        return resultList;
    }

    public boolean isIdReal(int id) {
        for(WiseSaying ws : wiseSayingsList) {
            if(ws.getId()==id)
                return true;
        }
        return false;
    }

    public void remove(int id) {
        wiseSayingsList.removeIf(ws -> ws.getId() == id);
    }

    public WiseSaying getDataById(int id) {
        WiseSaying resultWiseSaying = null;
        for(WiseSaying ws : wiseSayingsList) {
            if(ws.getId()==id) {
                resultWiseSaying = ws;
                break;
            }
        }
        return resultWiseSaying;
    }

    public void alter(WiseSaying changeWs) {
        for(WiseSaying ws : wiseSayingsList) {
            if(ws.getId()==changeWs.getId()) {
                ws.setContent(changeWs.getContent());
                ws.setAuthor(changeWs.getAuthor());
                wiseSayingsList.set(wiseSayingsList.indexOf(ws), ws);

                break;
            }
        }
    }

    public void setLastId() {
        lastId = 0;
    }
    public int getLastId() {
        return lastId;
    }
}
