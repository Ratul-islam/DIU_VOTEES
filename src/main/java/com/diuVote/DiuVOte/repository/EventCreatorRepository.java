package com.diuVote.DiuVOte.repository;

import com.diuVote.DiuVOte.entity.EVENTCREATOR;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EventCreatorRepository extends MongoRepository<EVENTCREATOR, String> {

    Optional<EVENTCREATOR> findByEmail(String email);

    boolean existsByEmail(String email);
}