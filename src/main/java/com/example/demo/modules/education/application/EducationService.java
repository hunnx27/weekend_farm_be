package com.example.demo.modules.education.application;

import com.example.demo.modules.account.domain.Account;
import com.example.demo.modules.account.infra.AccountRepository;
import com.example.demo.modules.education.application.request.EducationSearchRequest;
import com.example.demo.modules.education.application.request.EducationUpdateRequest;
import com.example.demo.modules.education.domain.Education;
import com.example.demo.modules.education.infra.EducationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EducationService {
    private final EducationRepository educationRepository;
    private final AccountRepository accountRepository;

    public Education create(Education education) {
        return educationRepository.save(education);
    }

    public Page<Education> list(EducationSearchRequest educationSearchRequest, Pageable pageable) {
        return educationRepository.educations(educationSearchRequest, pageable);
    }

    public Education detail(Long id) {
        return educationRepository.findById(id)
                .orElseThrow();
    }

    public Education update(EducationUpdateRequest educationUpdateRequest) {
        Education education = educationRepository.findById(educationUpdateRequest.getId()).orElseThrow();
        education.setName(educationUpdateRequest.getName());
        education.setSubject(educationUpdateRequest.getSubject());
        return educationRepository.save(education);
    }

    public Education join(Long id, Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElse(new Account());

        return educationRepository.findById(id)
                .map(x -> {
                    if(!x.getAccounts().contains(account)){
                        x.addAccount(account);
                        educationRepository.save(x);
                    }
                    return x;
                }).orElseThrow();
    }

    public Education leave(Long id, Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElse(new Account());

        return educationRepository.findById(id)
                .map(x -> {
                    if(x.getAccounts().contains(account)){
                        x.removeAccount(account);
                        educationRepository.save(x);
                    }
                    return x;
                }).orElseThrow();
    }

    public boolean delete(Long id) {
        educationRepository.findById(id)
                .ifPresent(x -> x.setAccounts(new HashSet<>()));
        return educationRepository.deleteEducation(id);
    }

}

