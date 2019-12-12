package com.startspringboot.example.controller;

import com.startspringboot.example.domain.MemberVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SampleController {

   @GetMapping("/sample2")
   public void sample2(Model model){
       MemberVO vo =
               new MemberVO(123, "u00", "p00", "홍길동",
                       new Timestamp(System.currentTimeMillis()));
       model.addAttribute("vo", vo);
   }

    @GetMapping("/sample3")
    public void sample3(Model model){
        List<MemberVO> list = new ArrayList<>();

        for (int i = 0; i < 10; i++){
            list.add(new MemberVO(123, "U0" + i ,"P0" + i, "홍길동" + i,
                    new Timestamp(System.currentTimeMillis())));
        }
        model.addAttribute("list", list);
    }
}