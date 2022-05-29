package com.wefarm.modules.farm.application.request;

import com.wefarm.modules.common.domain.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;

@Getter
@Setter
@NoArgsConstructor
public class FarmCreateRequest {

    private Long id;
    private String name;
    private String addr;
    private Double lat;
    private Double lng;
    private String owner;
    private int price;
    private String desc;
    private String source;
    private String sourceUrl;
}
