package com.wefarm.modules.account.infra;

import com.wefarm.modules.account.domain.Account;
import com.wefarm.modules.account.application.request.AccountSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountRepositoryExtension {

    Page<Account> accounts(AccountSearchRequest accountSearchRequest, Pageable pageable);

    Account deleteAccount(Long id);
}
