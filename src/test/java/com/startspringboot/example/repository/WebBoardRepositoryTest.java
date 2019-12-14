package com.startspringboot.example.repository;

import com.startspringboot.example.domain.WebBoard;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Log
@Commit
class WebBoardRepositoryTest {

    @Autowired
    private WebBoardRepository webBoardRepository;

    @Test
        public void 더미_데이터_생성(){
        IntStream.range(0, 300).forEach(i ->{

            WebBoard board = new WebBoard();

            board.setTitle("Sample Board Title" + i);
            board.setContent("Content Sample ..." + i + ":of Board");
            board.setWriter("suer0" + (i % 10));

            webBoardRepository.save(board);
        });
    }

    @Test
        public void 검색_없는_단순_페이징처리가_되는지(){

        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "bno");

        Page<WebBoard> result = webBoardRepository.findAll(webBoardRepository.makePredicate(null, null), pageable);

        log.info("PAGE" + result.getPageable());
        log.info("----------------");
        result.getContent().forEach(board -> log.info("" + board));
    }

    @Test
        public void 제목검색_기능있는_페이징처리가_되는지(){

        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "bno");

        Page<WebBoard> result = webBoardRepository.findAll(
                webBoardRepository.makePredicate("t", "10"), pageable);
        log.info("PAGE : " + result.getPageable());
        log.info("----------------");
        result.getContent().forEach(webBoard -> log.info(""  + webBoard));
    }

    @Test
    public void 내용검색_기능있는_페이징처리가_되는지(){

        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "bno");

        Page<WebBoard> result = webBoardRepository.findAll(
                webBoardRepository.makePredicate("t", "10"), pageable);
        log.info("PAGE : " + result.getPageable());
        log.info("----------------");
        result.getContent().forEach(webBoard -> log.info(""  + webBoard));
    }

    @Test
    public void 글쓴이검색_기능있는_페이징처리가_되는지(){
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "bno");

        Page<WebBoard> result = webBoardRepository.findAll(
                webBoardRepository.makePredicate("w", "suer10"), pageable);
        log.info("PAGE : " + result.getPageable());
        log.info("----------------");
        result.getContent().forEach(webBoard -> log.info(""  + webBoard));
    }
}