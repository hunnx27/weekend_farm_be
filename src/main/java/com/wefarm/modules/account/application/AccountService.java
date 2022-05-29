package com.wefarm.modules.account.application;

import com.wefarm.modules.account.application.request.AccountUpdateRequest;
import com.wefarm.modules.account.type.Role;
import com.wefarm.modules.account.application.request.AccountSearchRequest;
import com.wefarm.modules.account.domain.Account;
import com.wefarm.modules.account.infra.AccountRepository;
import com.wefarm.modules.common.type.YN;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;
//    private final EducationRepository educationRepository;
//    private final EducationService educationService;
    private final PasswordEncoder passwordEncoder;

    public Account create(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRole(Role.USER);
        return accountRepository.save(account);
    }

    public Page<Account> list(AccountSearchRequest accountSearchRequest, Pageable pageable) {
        return accountRepository.accounts(accountSearchRequest, pageable);
    }

    public Account findOne(Long id) {
        return accountRepository.findById(id)
            .orElseThrow(NoSuchElementException::new);
    }

    public Long update(AccountUpdateRequest account) {
        Account findOne = accountRepository.findById(account.getId())
            .orElseThrow(NoSuchElementException::new);
        findOne.setUpdateData(account);
        return accountRepository.save(findOne).getId();
    }

    public boolean delete(Long id) {
        Account account = accountRepository.deleteAccount(id);
//        if (account.getIsDelete().equals(YN.Y)) {
//            List<Education> educationsByAccounts = educationRepository.findEducationsByAccounts(
//                account);
//            educationsByAccounts.forEach(education -> education.removeAccount(account));
//        }

        return true;
    }

//    public List<Education> educations(Long id) {
//        Account account = accountRepository.findById(id).orElseThrow();
//        return educationRepository.findEducationsByAccounts(account);
//    }


}
