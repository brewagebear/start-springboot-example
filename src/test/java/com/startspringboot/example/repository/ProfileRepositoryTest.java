package com.startspringboot.example.repository;

import com.startspringboot.example.domain.Member;
import com.startspringboot.example.domain.Profile;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Log
@Commit
class ProfileRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Test
    public void 더미_유저_생성(){
        IntStream.range(1, 101).forEach(i -> {
            Member member = new Member();
            member.setUserEmail("user" + i);
            member.setPassword("pw" + i);
            member.setUserName("사용자" + i);

            memberRepository.save(member);
        });
    }

    @Test
    public void 더미_프로필_생성(){
        Member member = new Member();
        member.setId(1L);

        for(int i = 1; i < 5 ; i++){
          Profile profile = new Profile();
          profile.setFileName("face"+i+".jpg");
            if(i == 1){
                profile.setCurrent(true);
            }
            profile.setMember(member);
            profileRepository.save(profile);
        }
    }

    @Test
    public void 회원과_등록된_회원프로필_개수_조인_테스트(){
        List<Object[]> result = memberRepository.getMemberWithProfileCount(1L);
        result.forEach(arr -> System.out.println(Arrays.toString(arr)));
    }

    @Test
    public void 회원과_활성화된_프로필_조인_테스트(){
        List<Object[]> result = memberRepository.getMemberWithCurrentProfile(1L);
        result.forEach(arr -> System.out.println(Arrays.toString(arr)));
    }
}