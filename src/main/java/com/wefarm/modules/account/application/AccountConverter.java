package com.wefarm.modules.account.application;

import com.wefarm.modules.account.application.request.AccountUpdateRequest;
import com.wefarm.modules.account.domain.Account;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountConverter {

    void updateAccountFromDto(AccountUpdateRequest accountUpdateRequest,
        @MappingTarget Account account);

}
