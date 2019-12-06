package com.startspringboot.example.repository;

import com.startspringboot.example.domain.PDSBoard;
import org.springframework.data.repository.CrudRepository;

public interface PDSBoardRepository extends CrudRepository<PDSBoard, Long> {
}
