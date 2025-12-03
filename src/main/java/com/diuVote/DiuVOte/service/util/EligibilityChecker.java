package com.diuVote.DiuVOte.service.util;

import com.diuVote.DiuVOte.entity.EligibilityCriteria;
import com.diuVote.DiuVOte.entity.StudentProfile;

public class EligibilityChecker {

    public static boolean isEligible(EligibilityCriteria criteria, StudentProfile student) {
        if (criteria == null) {
            return true;
        }

        // Department
        if (criteria.getAllowedDepartments() != null && !criteria.getAllowedDepartments().isEmpty()) {
            if (student.getDepartment() == null ||
                    !criteria.getAllowedDepartments().contains(student.getDepartment())) {
                return false;
            }
        }

        // Session
        if (criteria.getAllowedSessions() != null && !criteria.getAllowedSessions().isEmpty()) {
            if (student.getSession() == null ||
                    !criteria.getAllowedSessions().contains(student.getSession())) {
                return false;
            }
        }

        // Section
        if (criteria.getAllowedSections() != null && !criteria.getAllowedSections().isEmpty()) {
            if (student.getSection() == null ||
                    !criteria.getAllowedSections().contains(student.getSection())) {
                return false;
            }
        }

        // Exact batches
        if (criteria.getAllowedBatches() != null && !criteria.getAllowedBatches().isEmpty()) {
            if (student.getBatch() == null ||
                    !criteria.getAllowedBatches().contains(student.getBatch())) {
                return false;
            }
        }

        // Batch range
        if (criteria.getBatchRangeStart() != null && criteria.getBatchRangeEnd() != null && student.getBatch() != null) {
            int b = student.getBatch();
            if (b < criteria.getBatchRangeStart() || b > criteria.getBatchRangeEnd()) {
                return false;
            }
        }

        return true;
    }
}