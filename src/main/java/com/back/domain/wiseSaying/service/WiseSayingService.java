package com.back.domain.wiseSaying.service;

import com.back.WiseSaying;
import com.back.domain.wiseSaying.repository.WiseSayingRepository;

import java.util.List;

/* --- 요리주방 |  --- */
// Service: 사용자의 요청을 처리하는 역할 (비즈니스 로직 담당)
public class WiseSayingService {
    private final WiseSayingRepository wiseSayingRepository;

    public WiseSayingService() {
        wiseSayingRepository = new WiseSayingRepository();
    }

    public WiseSaying write(String content, String author) {
        WiseSaying ws = new WiseSaying(0, content, author);
        wiseSayingRepository.save(ws);
        return ws;
    }

    public List<WiseSaying> findForList() {
        return wiseSayingRepository.findAll();
    }
    public List<WiseSaying> findForList(String keywordType, String keyword) {
        return wiseSayingRepository.findKeyword(keywordType, keyword);
    }

    public boolean findById(int id) {
        return wiseSayingRepository.isIdReal(id);
    }

    public void delete(int id) {
        wiseSayingRepository.remove(id);
    }

    public WiseSaying getById(int id) {
        return wiseSayingRepository.getDataById(id);
    }

    public void modify(int id, String content, String author) {
        WiseSaying ws = new WiseSaying(id, content, author);
        wiseSayingRepository.alter(ws);
    }

    public void setLastId() {
        wiseSayingRepository.setLastId();
    }
    public int getLastId() {
        return wiseSayingRepository.getLastId();
    }
}
