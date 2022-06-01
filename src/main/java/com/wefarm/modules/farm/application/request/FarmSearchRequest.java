package com.wefarm.modules.farm.application.request;

import com.wefarm.modules.common.application.request.BasePageRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FarmSearchRequest extends BasePageRequest {

    private Long id;
    private String name;
    private String address;
    private Double lat;
    private Double lng;
    private String owner;
    private int price;
    private String phone;
    private String description;
    private String source;
    private String sourceUrl;
}
