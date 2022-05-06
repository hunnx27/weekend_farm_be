package com.example.demo.modules.account.web;

import com.example.demo.modules.account.application.AccountService;
import com.example.demo.modules.account.domain.Account;
import com.example.demo.modules.account.infra.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void beforeEach() {
        Account account = new Account();
        account.setId(anyLong());
        account.setName("dlo");
        account.setAge("65");
        account.setEmail("test@test.com");
        account.setLocation("수원");
        accountRepository.save(account);
    }

    @AfterEach
    void afterEach() {
        accountRepository.deleteAll();
    }
    @DisplayName("계정 전체 조회")
    @Test
    void findAccounts() throws Exception {
        mockMvc.perform(get("/accounts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("dlo"));
    }

    @DisplayName("계정 단건 조회")
    @Test
    void findAccount() throws Exception {
        Long id = accountRepository.findAll().get(0).getId();
        mockMvc.perform(get("/accounts/{id}",  id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("dlo"));
    }

    @DisplayName("계정 변경")
    @Test
    void updateAccount() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("name", "변경 고");
        map.put("email", "변경이메일");
        map.put("age", "12");
        map.put("location", "판교");

        Long id = accountRepository.findAll().get(0).getId();
        mockMvc.perform(put("/accounts/{id}",  id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(map)))
                .andDo(print())
                .andExpect(status().isOk());

        Account account = accountRepository.findByEmail(map.get("email"));
        ModelMapper modelMapper = new ModelMapper();
        Account compare = modelMapper.map(map, Account.class);
        assertEquals(account.getEmail(), compare.getEmail());
    }

    @DisplayName("계정 삭제")
    @Test
    void deleteAccount() throws Exception {
        Long id = accountRepository.findAll().get(0).getId();
        mockMvc.perform(delete("/accounts/{id}",  id))
                .andDo(print())
                .andExpect(status().isOk());
    }
}