package com.demo.modules.organization.application.request;

import com.demo.modules.common.application.request.BasePageRequest;
import com.demo.modules.common.domain.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrganizationSearchRequest extends BasePageRequest {


    private String code;
    private String name;
    private String address;
}
