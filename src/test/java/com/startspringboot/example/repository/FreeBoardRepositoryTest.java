package com.startspringboot.example.repository;

import com.startspringboot.example.domain.FreeBoard;
import com.startspringboot.example.domain.FreeBoardReply;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Log
@Commit
class FreeBoardRepositoryTest {

    @Autowired
    private FreeBoardRepository boardRepository;

    @Autowired
    private FreeBoardReplyRepository replyRepository;

    @Test
    public void 더미_데이터_생성(){

        IntStream.range(1, 200).forEach(i -> {

            FreeBoard board = new FreeBoard();
            board.setTitle("Free Board Dummy Title...." + i);
            board.setContent("Free Board Dummy Content...." + i);
            board.setWriter("user" + i%10);

            boardRepository.save(board);
        });
    }

    /***
     * 댓글 추가하는 로직은 2가지 방법으로 나눠진다.
     * @1번방식 : 단방향에서 처리하듯이 FreeBoardReply를 생성하고, FreeBoard 자체는 새로 만들어서 bno속성만을 지정하여 처리
     * @2번방식 : 양방향이므로, FreeBoard 객체를 얻어온 후 FreeBoardReply를 댓글 리스트에 추가한 후에 FreeBoard 자체를 저장하는 방식
     *
     */
    @Transactional
        @Test
        public void JPA양방향_댓글_삽입_테스트(){
            Optional<FreeBoard> result = boardRepository.findById(199L);

            result.ifPresent(board ->{
                List<FreeBoardReply> replies = board.getReplies();
                FreeBoardReply reply = new FreeBoardReply();
                reply.setReply("REPLY.........");
                reply.setReplier("replier00");
                reply.setBoard(board);

                replies.add(reply);

                board.setReplies(replies);

                boardRepository.save(board);
        });
    }

    @Test
    public void 단방향_댓글_삽입_테스트(){

        FreeBoard board = new FreeBoard();
        board.setBno(199L);

        FreeBoardReply reply = new FreeBoardReply();
        reply.setReply("REPLY..........");
        reply.setReplier("replier00");
        reply.setBoard(board);

        replyRepository.save(reply);
    }

    @Test
    public void SQL성능비교_를_위한_페이징처리_테스트() {
        Pageable page = PageRequest.of(0, 10, Sort.Direction.DESC, "bno");
        boardRepository.findByBnoGreaterThan(0L, page).forEach(board -> {
            log.info(board.getBno() + " : " + board.getTitle());
        });
    }


    @Transactional
        @Test
        public void 제목과_댓글의_수가_같이_나오는_것을_테스트(){
            Pageable page = PageRequest.of(0, 10, Sort.Direction.DESC, "bno");
            boardRepository.findByBnoGreaterThan(0L, page).forEach(board->{
                log.info(board.getBno() +": " + board.getTitle() + ": " + board.getReplies().size());
        });
    }

}