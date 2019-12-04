package com.startspringboot.example.repository;

import com.startspringboot.example.domain.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MemberRepository extends CrudRepository<Member, String> {
    /**
     * SELECT member.uid, count(fname)
     * from
     *  tbl_members member LEFT OUTER JOIN tbl_profile profile
     *  ON profile.member_uid = member.uid
     * WHERE member.uid = '1'
     * GROUP BY member.uid
     */
    @Query("SELECT m.id, COUNT(p.fileName) FROM Member m LEFT OUTER JOIN Profile p" +
            " ON m.id = p.member WHERE m.id = ?1 GROUP BY m")
    public List<Object[]> getMemberWithProfileCount(Long id);

    @Query("SELECT m, p FROM Member m LEFT OUTER JOIN Profile p" +
           " ON m.id = p.member WHERE m.id =?1 AND p.current = true")
    public List<Object[]> getMemberWithCurrentProfile(Long id);
}
