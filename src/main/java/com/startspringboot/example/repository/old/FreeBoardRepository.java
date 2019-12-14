package com.startspringboot.example.repository.old;

import com.startspringboot.example.domain.old.FreeBoard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import java.util.List;

public interface FreeBoardRepository extends CrudRepository<FreeBoard, Long> {
    public List<FreeBoard> findByBnoGreaterThan(Long bno, Pageable page);

    @Query("SELECT b.bno, b.title, COUNT(r) " +
            " FROM FreeBoard b LEFT OUTER JOIN b.replies r "+
            " WHERE  b.bno > 0 GROUP BY b ")
    public List<Object[]> getPage(Pageable page);

}
