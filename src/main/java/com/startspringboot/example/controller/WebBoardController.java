package com.startspringboot.example.controller;

import com.startspringboot.example.domain.WebBoard;
import com.startspringboot.example.repository.WebBoardRepository;
import com.startspringboot.example.vo.PageMaker;
import com.startspringboot.example.vo.PageVO;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.function.Supplier;


@Controller
@RequestMapping("/board/")
@Log
public class WebBoardController {

    @Autowired
    private WebBoardRepository webBoardRepository;

    @GetMapping("/list")
    public void list(@ModelAttribute("PageVO") PageVO vo, Model model){

        Pageable page = vo.makePageable(0, "bno");

        Page<WebBoard> result = webBoardRepository.findAll(
                webBoardRepository.makePredicate(vo.getType(), vo.getKeyword()), page);

    //        log.info("" + page);
    //        log.info("" + result);
    //        log.info("TOTAL PAGE NUMBER : " + result.getTotalPages());
    //        log.info(vo.getType());
    //        log.info(vo.getKeyword());
        model.addAttribute("result", new PageMaker(result));
        model.addAttribute("pageVO", vo);
    }
}

