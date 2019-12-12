package com.startspringboot.example.repository;

import com.querydsl.core.BooleanBuilder;
import com.startspringboot.example.domain.QWebBoard;
import com.startspringboot.example.domain.WebBoard;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.querydsl.core.types.Predicate;

public interface WebBoardRepository extends CrudRepository<WebBoard, Long>, QuerydslPredicateExecutor<WebBoard> {

    /***
     * @param type //검색에 필요한 타입
     * @param keyword // 검색 키워드
     * @return Predicate
     */
    public default Predicate makePredicate(String type, String keyword){

        BooleanBuilder builder = new BooleanBuilder();
        QWebBoard board = QWebBoard.webBoard;

        builder.and(board.bno.gt(0));

        if(type == null){
            return builder;
        }

        switch (type){
            case "t":
                builder.and(board.title.like("%" + keyword + "%"));
                break;
            case "c":
                builder.and(board.writer.like("%" + keyword + "%"));
                break;
        }
        return builder;
    }

}
