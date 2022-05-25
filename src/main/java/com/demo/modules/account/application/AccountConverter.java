package com.demo.modules.account.application;

import com.demo.modules.account.application.request.AccountUpdateRequest;
import com.demo.modules.account.domain.Account;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountConverter {

    void updateAccountFromDto(AccountUpdateRequest accountUpdateRequest,
        @MappingTarget Account account);

}
