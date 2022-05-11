package com.demo.modules.account.infra;

import com.demo.modules.account.domain.Account;
import com.demo.modules.account.application.request.AccountSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountRepositoryExtension {

    Page<Account> accounts(AccountSearchRequest accountSearchRequest, Pageable pageable);

    Account deleteAccount(Long id);
}
