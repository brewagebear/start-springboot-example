package com.startspringboot.example.repository.old;

import com.startspringboot.example.domain.old.PDSBoard;
import com.startspringboot.example.domain.old.PDSFile;
import com.startspringboot.example.repository.old.PDSBoardRepository;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

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

    @Transactional
        @Test
        public void 파일명_수정_테스트(){

        Long fno = 1L;
        String newName = "updatedFile1.doc";

        int count = pdsBoardRepository.updatePDSFile(fno, newName);
        log.info("update count"+count);
    }

    @Transactional
        @Test
        public void 전통적_파일명_수정_테스트(){

        String newName = "updatedFile2.doc";
        // 반드시 번호가 존재하는 지 확인
        Optional<PDSBoard> result = pdsBoardRepository.findById(2L);

        result.ifPresent(pdsBoard -> {
            log.info("테이터가 존재하므로 update 시도");

            PDSFile target = new PDSFile();
            target.setFno(2L);
            target.setPdsfile(newName);

            int idx = pdsBoard.getFiles().indexOf(target);

            if(idx > -1){
                List<PDSFile> list = pdsBoard.getFiles();
                list.remove(idx);
                list.add(target);
                pdsBoard.setFiles(list);
            }
            pdsBoardRepository.save(pdsBoard);
        });
    }

    @Transactional
        @Test
        public void 첨부파일_삭제_테스트(){
        Long fno = 2L;
        int count = pdsBoardRepository.deletePDSFile(fno);
        log.info("DELETE PDSFILE: " + count);
    }

    @Test
        public void 조인처리를_위한_더미_데이터_생성(){
        List<PDSBoard> list = new ArrayList<>();

        IntStream.range(1, 100).forEach(i -> {
            PDSBoard pds = new PDSBoard();
            pds.setPname("자료 " + i);

            PDSFile file1 = new PDSFile();
            file1.setPdsfile("file1.doc");

            PDSFile file2 = new PDSFile();
            file2.setPdsfile("file2.doc");

            pds.setFiles(Arrays.asList(file1, file2));

            log.info("try to save pds");
            list.add(pds);
        });
        pdsBoardRepository.saveAll(list);
    }

    @Test
    public void 조인_후_요약보기(){
        pdsBoardRepository.getSummary().forEach(arr ->
                log.info(Arrays.toString(arr)));
    }

}