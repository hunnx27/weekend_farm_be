package com.wefarm.modules.account.infra;

import com.wefarm.modules.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>,
    AccountRepositoryExtension {

    Account findByEmail(String email);

    Account findByName(String name);

}
