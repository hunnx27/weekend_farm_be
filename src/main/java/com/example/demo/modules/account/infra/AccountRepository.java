package com.example.demo.modules.account.infra;

import com.example.demo.modules.account.domain.Account;
import com.example.demo.modules.education.domain.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long>, AccountRepositoryExtension {

    Account findByEmail(String email);

}
