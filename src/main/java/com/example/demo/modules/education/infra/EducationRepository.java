package com.example.demo.modules.education.infra;


import com.example.demo.modules.account.domain.Account;
import com.example.demo.modules.education.domain.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long>, EducationRepositoryExtension{

    Education findByName(String name);

    List<Education> findEducationsByAccounts(Account account);
}
