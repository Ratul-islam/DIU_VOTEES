package com.diuVote.DiuVOte.service.impl;

import com.diuVote.DiuVOte.dto.request.RegisterStudentRequest;
import com.diuVote.DiuVOte.dto.response.StudentProfileDto;
import com.diuVote.DiuVOte.entity.StudentProfile;
import com.diuVote.DiuVOte.repository.StudentProfileRepository;
import com.diuVote.DiuVOte.service.StudentProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentProfileServiceImpl implements StudentProfileService {

    private final StudentProfileRepository studentProfileRepository;

    @Override
    public StudentProfileDto registerOrUpdateStudent(RegisterStudentRequest request) {
        StudentProfile profile = studentProfileRepository
                .findByNfcId(request.getNfcId())
                .orElseGet(StudentProfile::new);

        profile.setNfcId(request.getNfcId());
        profile.setStudentId(request.getStudentId());
        profile.setName(request.getName());
        profile.setDepartment(request.getDepartment());
        profile.setFaculty(request.getFaculty());
        profile.setSession(request.getSession());
        profile.setSection(request.getSection());
        profile.setBatch(request.getBatch());

        StudentProfile saved = studentProfileRepository.save(profile);
        return toDto(saved);
    }

    @Override
    public StudentProfileDto getByNfcId(String nfcId) {
        StudentProfile profile = studentProfileRepository
                .findByNfcId(nfcId)
                .orElseThrow(() -> new IllegalArgumentException("Student with this NFC is not registered"));

        return toDto(profile);
    }

    private StudentProfileDto toDto(StudentProfile profile) {
        return new StudentProfileDto(
                profile.getId(),
                profile.getNfcId(),
                profile.getStudentId(),
                profile.getName(),
                profile.getDepartment(),
                profile.getFaculty(),
                profile.getSession(),
                profile.getSection(),
                profile.getBatch()
        );
    }
}