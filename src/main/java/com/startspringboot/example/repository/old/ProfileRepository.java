package com.startspringboot.example.repository.old;

import com.startspringboot.example.domain.old.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}
