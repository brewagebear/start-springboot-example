package com.startspringboot.example.repository;

import com.startspringboot.example.domain.Board;
import org.springframework.data.repository.CrudRepository;

import java.awt.print.Pageable;
import java.util.Collection;
import java.util.List;

public interface BoardRepository extends CrudRepository<Board, Long> {

    public List<Board> findBoardByTitle(String title);

    public Collection<Board> findByWriter(String writer);

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

    // id > ? ORDER BY id DESC
    public Collection<Board> findByIdGreaterThanOrderByIdDesc(Long id);
}
