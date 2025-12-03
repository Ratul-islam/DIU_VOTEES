package com.diuVote.DiuVOte.service;

import com.diuVote.DiuVOte.dto.request.RegisterStudentRequest;
import com.diuVote.DiuVOte.dto.response.StudentProfileDto;

public interface StudentProfileService {

    StudentProfileDto registerOrUpdateStudent(RegisterStudentRequest request);

    StudentProfileDto getByNfcId(String nfcId);
}