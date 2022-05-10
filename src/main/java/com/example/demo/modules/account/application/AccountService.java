package com.example.demo.modules.account.application;

import com.example.demo.modules.account.application.request.AccountSearchRequest;
import com.example.demo.modules.account.application.request.AccountUpdateRequest;
import com.example.demo.modules.account.domain.UserAccount;
import com.example.demo.modules.account.domain.Account;
import com.example.demo.modules.account.infra.AccountRepository;
import com.example.demo.modules.common.type.YN;
import com.example.demo.modules.education.application.EducationService;
import com.example.demo.modules.education.domain.Education;
import com.example.demo.modules.education.infra.EducationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;
    private final EducationRepository educationRepository;
    private final EducationService educationService;
    private final PasswordEncoder passwordEncoder;

    public Account create(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    public Page<Account> list(AccountSearchRequest accountSearchRequest, Pageable pageable) {
        return accountRepository.accounts(accountSearchRequest, pageable);
    }

    public Account findOne(Long id){
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
        if(account.getIsDelete().equals(YN.Y)){
            List<Education> educationsByAccounts = educationRepository.findEducationsByAccounts(account);
            educationsByAccounts.forEach(education -> education.removeAccount(account));
        }

        return true;
    }

    public List<Education> educations(Long id) {
        Account account = accountRepository.findById(id).orElseThrow();
        return educationRepository.findEducationsByAccounts(account);
    }

    @Override
    public UserDetails loadUserByUsername(String nameOrEmail) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(nameOrEmail);
        if(account == null){
            account = accountRepository.findByName(nameOrEmail);
        }

        if(account == null){
            throw new UsernameNotFoundException(nameOrEmail);
        }
        return new UserAccount(account);
    }
}
