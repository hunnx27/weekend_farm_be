package com.demo.modules.education.infra;

import com.demo.modules.education.application.request.EducationSearchRequest;
import com.demo.modules.education.domain.Education;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EducationRepositoryExtension {

    Page<Education> educations(EducationSearchRequest educationSearchRequest, Pageable pageable);

    boolean deleteEducation(Long id);
}
