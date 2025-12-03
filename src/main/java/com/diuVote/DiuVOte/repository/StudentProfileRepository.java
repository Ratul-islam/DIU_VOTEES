package com.diuVote.DiuVOte.repository;

import com.diuVote.DiuVOte.entity.StudentProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StudentProfileRepository extends MongoRepository<StudentProfile, String> {

    Optional<StudentProfile> findByNfcId(String nfcId);

    boolean existsByNfcId(String nfcId);
}