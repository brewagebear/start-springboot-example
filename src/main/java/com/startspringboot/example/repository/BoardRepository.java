package com.startspringboot.example.repository;

import com.startspringboot.example.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import java.util.Collection;
import java.util.List;

public interface BoardRepository extends CrudRepository<Board, Long>, QuerydslPredicateExecutor<Board> {

    public List<Board> findBoardByTitle(String title);

    //public Collection<Board> findByWriter(String writer);

    /***
     *  단순 LIKE         = Like 키워드
     *  키워드 + '%'       = StartingWith
     *  '%' + 키워드       = EndingWith
     *  '%' + 키워드 + "%' = Containing
     */
    public Collection<Board> findByWriterContaining(String writer);

    /***
     *
     * 2개 이상의 속성을 이용해서 엔티티 검색을 하기 위함
     * 제목에 포함된 키워드나 타이틀에 포함된 키워드로 검색하기 위해서
     *  findBy + TitileContaining + Or + ConentContaining
     */
    // title LIKE % ? % OR content LIKE % ? %
    public Collection<Board> findByTitleContainingOrContentContaining(String title, String content);

    // title LIKE % ? % AND PK > ?
    public Collection<Board> findByTitleContainingAndIdGreaterThan(String title, Long id);

    // id > ? ORDER BY id DESC limit ?, ?
    public List<Board> findByIdGreaterThanOrderByIdDesc(Long id, Pageable paging);

    // 정렬 부분을 PageRequest.of 메소드에 인계함.
    //public List<Board> findByIdGreaterThan(Long id, Pageable paging);

    // List<T>가 아닌 Page<T>를 타입을 이용하여 사용
    public Page<Board> findByIdGreaterThan(Long id, Pageable paging);

    //제목에 대한 검색 처리
    @Query("SELECT b FROM Board b WHERE b.title LIKE %?1% AND b.id > 0 ORDER BY b.id DESC")
    public List<Board> findByTitle(String title);

    //내용에 대한 검색 처리
    @Query("SELECT b FROM Board b WHERE b.content LIKE %:content% AND b.id > 0 ORDER BY b.id DESC")
    public List<Board> findByContent(@Param("content") String content);

    //작성자에 대한 검색 처리
    @Query("SELECT b FROM #{#entityName} b WHERE b.writer LIKE %?1% AND b.id > 0 ORDER BY b.id DESC")
    List<Board> findByWriter(String writer);

    @Query("SELECT b.id, b.title, b.writer, b.regDate" +
            " FROM Board b WHERE b.title LIKE %?1% AND b.id > 0 ORDER BY b.id DESC")
    public List<Object[]> findByTitle2(String title);

    // 전체 게시물에 대한 페이징 처리 @Query로 작성한 내용 + 페이징 처리
   @Query("SELECT b FROM Board b WHERE b.id > 0 ORDER BY b.id DESC")
    public List<Board> findByPage(Pageable pageable);

}
