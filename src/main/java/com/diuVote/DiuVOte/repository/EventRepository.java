package com.diuVote.DiuVOte.repository;

import com.diuVote.DiuVOte.entity.EVENT;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventRepository extends MongoRepository<EVENT, String> {

    List<EVENT> findByCreatorId(String creatorId);
}