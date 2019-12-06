package com.startspringboot.example.repository;

import com.startspringboot.example.domain.PDSBoard;
import com.startspringboot.example.domain.PDSFile;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Log
class PDSBoardRepositoryTest {

    @Autowired
    PDSBoardRepository pdsBoardRepository;

    @Test
    public void PDSBOARD_더미_데이터_생성(){
        PDSBoard pds = new PDSBoard();
        pds.setPname("Document");

        PDSFile file1 = new PDSFile();
        file1.setPdsfile("file1.doc");

        PDSFile file2 = new PDSFile();
        file2.setPdsfile("file2.doc");

        pds.setFiles(Arrays.asList(file1, file2));
        log.info("try to save pds");

        pdsBoardRepository.save(pds);
    }
}