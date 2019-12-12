package com.startspringboot.example.repository.old;

import com.querydsl.core.BooleanBuilder;
import com.startspringboot.example.domain.old.Board;
import com.startspringboot.example.domain.QBoard;
import com.startspringboot.example.repository.old.BoardRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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
        results.forEach(System.out::println);
    }

    @Test
    public void 제목과_제목_키값으로_조회가_잘되는지(){
        Collection<Board> results = boardRepository.findByTitleContainingAndIdGreaterThan("5", 50L);
        results.forEach(System.out::println);
    }

    @Test
    public void 특정_숫자보다_큰_키값_정렬되는지(){
        Pageable paging = PageRequest.of(0, 10);

        Collection<Board> results = boardRepository.
                                    findByIdGreaterThanOrderByIdDesc(0L, paging);
        results.forEach(System.out::println);
    }

    /*
    @Test
    public void 특정_숫자보다_큰_값이_정렬되는지(){
        Pageable paging = PageRequest.of(0, 10, Sort.Direction.DESC, "id");

        Collection<Board> results = boardRepository.
                findByIdGreaterThan(0L, paging);
        results.forEach(board -> System.out.println(board));
    }
     */

    @Test
    public void 키_값_페이징_및_정렬이_잘되는지(){
        Pageable paging = PageRequest.of(0, 10, Sort.Direction.DESC, "id");

        Page<Board> result = boardRepository.findByIdGreaterThan(0L, paging);
        System.out.println("PAGE SIZE :" + result.getSize());
        System.out.println("TOTAL PAGES :" + result.getTotalPages());
        System.out.println("TOTAL COUNT :" + result.getTotalElements());
        System.out.println("NEXT :" + result.nextPageable());

        List<Board> list = result.getContent();
        list.forEach(System.out::println);
    }

    @Test
    public void 제목에_대한_검색이_잘되는지(){
        boardRepository.findByTitle2("17").
                forEach(System.out::println);
    }

    @Test
    public void 콘텐츠를_제외한_컬럼을_제목_검색을_통해서_잘되는지(){
        boardRepository.findByTitle2("17")
                .forEach(arr -> System.out.println(Arrays.toString(arr)));
    }

    @Test
    public void 페이징_테스트_처리(){
        Pageable pageable = PageRequest.of(0, 10);
        boardRepository.findByPage(pageable).forEach(System.out::println);
    }

    @Test
    public void Predicate_생성_및_테스트(){

        String type    = "t";
        String keyword = "17";

        BooleanBuilder builder = new BooleanBuilder();

        QBoard board = QBoard.board;

        builder.and(board.title.like("%" + keyword + "%"));

        // id > 0
        builder.and(board.id.gt(0L));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Board> result = boardRepository.findAll(builder, pageable);

        System.out.println("PAGE SIZE :" + result.getSize());
        System.out.println("TOTAL PAGES :" + result.getTotalPages());
        System.out.println("TOTAL COUNT :" + result.getTotalElements());
        System.out.println("NEXT :" + result.nextPageable());

        List<Board> list = result.getContent();
        list.forEach(System.out::println);
    }
}