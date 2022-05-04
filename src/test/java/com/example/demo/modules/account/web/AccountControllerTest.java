package com.example.demo.modules.account.web;

import com.example.demo.modules.account.application.AccountService;
import com.example.demo.modules.account.domain.Account;
import com.example.demo.modules.account.infra.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Test
    void 계정_조회() throws Exception {
        Account account = new Account();
        account.setId(1L);
        account.setName("dlo");
        account.setAge("65");
        account.setEmail("test@test.com");
        account.setLocation("수원");
        accountRepository.save(account);

        MvcResult result = mockMvc.perform(get("/account/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        System.out.println(contentAsString);
    }
}