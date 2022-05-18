package com.demo.modules.account.infra;

import com.demo.modules.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>, AccountRepositoryExtension {

    Account findByEmail(String email);
    Account findByName(String name);

}
