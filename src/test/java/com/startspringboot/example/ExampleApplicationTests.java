package com.startspringboot.example;

import com.startspringboot.example.domain.Board;
import com.startspringboot.example.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ExampleApplicationTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void 더미데이터_생성() {
        for (int i = 1; i <= 200; i++){
            Board board = new Board();
            board.setTitle("제목.." + i);
            board.setContent("내용...." + i + ".... 채우기");
            board.setWriter("user0" + (i % 10));
            boardRepository.save(board);
        }
    }

}
