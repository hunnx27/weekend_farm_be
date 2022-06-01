package com.wefarm.modules.farm.domain;

import com.wefarm.modules.common.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Farm extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String address;
    private Double lat;
    private Double lng;
    private String owner;
    private String phone;
    private int price;
    private String description;
    private String source;
    private String sourceUrl;
}
