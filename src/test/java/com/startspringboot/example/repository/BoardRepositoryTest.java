package com.startspringboot.example.repository;

import com.startspringboot.example.domain.Board;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

//Junit4의 @RunWith(SpringRunner.class)와 동일
@ExtendWith(SpringExtension.class)
@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @Order(1)
    public void BoardRepostiroy의_실체가_어떤지(){
        // 실체 객체의 클래스 이름
        Class<?> clz = boardRepository.getClass();
        System.out.println(clz.getName());

        // 클래스가 구현하고 있는 인터페이스 이름
        Class<?>[] interfaces = clz.getInterfaces();
        Stream.of(interfaces).forEach(inter -> System.out.println(inter.getName()));

        //클래스의 부모 클래스
        Class<?> superClasses = clz.getSuperclass();
        System.out.println(superClasses.getName());
    }

    @Test
    @Order(2)
    public void BoardRepository에_값_삽입이_잘되는지(){
        Board board = new Board();
        board.setTitle("게시물의 제목");
        board.setContent("게시물 내용 넣기...");
        board.setWriter("user00");
        boardRepository.save(board);
    }

    @Test
    @Order(3)
    public void BoardRepository에_저장된_값이_제대로_조회되는지(){
        boardRepository.findById(1L).ifPresent((board) -> {
            System.out.println(board);
        });
    }

    @Test
    @Order(4)
    public void BoardRepository에_저장된_값이_제대로_갱신되는지(){
        System.out.println("Read First........................");
        Optional<Board> board = boardRepository.findById(1L);
        // newBoard = (board.isPresent()) ? board.get() : new Board(); 와 동일
        Board updateTarget = board.orElseGet(Board::new);

        System.out.println("Update Title......................");
        updateTarget.setTitle("수정된 제목입니다.");

        System.out.println("Call Save().......................");
        boardRepository.save(updateTarget);
    }

    @Test
    @Order(5)
    public void BoardRepository에_특정한_레코드가_삭제가_잘되는지(){
        Optional<Board> board = boardRepository.findById(1L);
        Board deleteTarget = board.orElseGet(Board::new);

        System.out.println("DELETE ENTITY");
        //deleteById는 매개변수를 PK로 받으나, delete는 해당 객체를 받음.
        boardRepository.delete(deleteTarget);
    }

    @Test
    public void 제목으로_값_조회가_잘되는지(){
        boardRepository.findBoardByTitle("제목..199").forEach(board -> System.out.println(board));
    }

    @Test
    public void 작성자로_값_조회가_잘되는지(){
        Collection<Board> results = boardRepository.findByWriter("user00");
        results.forEach(
                board -> System.out.println(board)
        );
    }

    @Test
    public void 작성자_이름에_포함_값으로_조회가_잘되는지(){
        Collection<Board> results = boardRepository.findByWriterContaining("05");
        results.forEach(board -> System.out.println(board));
    }

    @Test
    public void 제목과_제목_키값으로_조회가_잘되는지(){
        Collection<Board> results = boardRepository.findByTitleContainingAndIdGreaterThan("5", 50L);
        results.forEach(board -> System.out.println(board));
    }

}