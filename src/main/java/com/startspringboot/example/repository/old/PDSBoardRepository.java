package com.startspringboot.example.repository.old;

import com.startspringboot.example.domain.old.PDSBoard;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PDSBoardRepository extends CrudRepository<PDSBoard, Long> {
    // @Modifying 애노테이션은 @Query 애노테이션의 한계점 (Select문만 가능을 해결하기 위해서 DML작업을 처리하는 애노테이션이다.
    @Modifying
        @Query("UPDATE FROM PDSFile f set f.pdsfile =?2 WHERE f.fno = ?1 ")
        public int updatePDSFile(Long fno, String newFileName);

    @Modifying
        @Query("DELETE FROM PDSFile f WHERE f.fno =?1")
        public int deletePDSFile(Long fno);

    @Query("SELECT p, COUNT(f) FROM PDSBoard p LEFT OUTER JOIN p.files f WHERE" +
            " p.pid > 0 GROUP BY p ORDER BY p.pid DESC")
    public List<Object[]> getSummary();


}
