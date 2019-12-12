package com.startspringboot.example.controller;

import com.startspringboot.example.domain.MemberVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Timestamp;

@Controller
public class SampleController {

   @GetMapping("/sample2")
   public void sample2(Model model){
       MemberVO vo =
               new MemberVO(123, "u00", "p00", "홍길동",
                       new Timestamp(System.currentTimeMillis()));
       model.addAttribute("vo", vo);
   }
}