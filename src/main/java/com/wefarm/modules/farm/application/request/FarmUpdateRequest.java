package com.wefarm.modules.farm.application.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FarmUpdateRequest {

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
