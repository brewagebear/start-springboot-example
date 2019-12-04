package com.startspringboot.example.repository;

import com.startspringboot.example.domain.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}
