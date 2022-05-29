package com.wefarm.modules.education.infra;

import com.wefarm.modules.education.application.request.EducationSearchRequest;
import com.wefarm.modules.education.domain.Education;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EducationRepositoryExtension {

    Page<Education> educations(EducationSearchRequest educationSearchRequest, Pageable pageable);

    boolean deleteEducation(Long id);
}
