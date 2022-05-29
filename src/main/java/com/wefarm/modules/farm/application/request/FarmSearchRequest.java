package com.wefarm.modules.farm.application.request;

import com.wefarm.modules.common.application.request.BasePageRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FarmSearchRequest extends BasePageRequest {


    private String code;
    private String name;
    private String address;
}