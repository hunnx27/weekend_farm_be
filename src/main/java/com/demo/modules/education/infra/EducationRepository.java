package com.demo.modules.education.infra;


import com.demo.modules.account.domain.Account;
import com.demo.modules.education.domain.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long>,
    EducationRepositoryExtension {

    Education findByName(String name);

    List<Education> findEducationsByAccounts(Account account);
}
