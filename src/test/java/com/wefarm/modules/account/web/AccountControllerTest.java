package com.wefarm.modules.account.web;

import static com.wefarm.common.testenv.WithAccountFixture.USERNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.wefarm.infra.MockMvcTest;
import com.wefarm.modules.account.application.AccountService;
import com.wefarm.modules.account.domain.Account;
import com.wefarm.modules.account.infra.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@MockMvcTest
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("계정 전체 조회")
    @Test
    @WithAccount(USERNAME)
    void findAccounts() throws Exception {
        mockMvc.perform(get("/api/accounts"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].name").value("dlo"));
    }

    @DisplayName("계정 단건 조회")
    @Test
    @WithAccount(USERNAME)
    void findAccount() throws Exception {
        Long id = accountRepository.findByName(USERNAME).getId();
        mockMvc.perform(get("/api/accounts/{id}", id))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("dlo"));
    }

    @DisplayName("계정 변경")
    @Test
    @WithAccount(USERNAME)
    void updateAccount() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("name", "변경 고");
        map.put("email", "변경이메일");
        map.put("age", "12");
        map.put("location", "판교");

        Long id = accountRepository.findByName(USERNAME).getId();
        mockMvc.perform(put("/api/accounts/{id}", id)
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
    @WithAccount(USERNAME)
    void deleteAccount() throws Exception {
        Long id = accountRepository.findByName(USERNAME).getId();
        mockMvc.perform(delete("/api/accounts/{id}", id))
            .andDo(print())
            .andExpect(status().isOk());
    }
}