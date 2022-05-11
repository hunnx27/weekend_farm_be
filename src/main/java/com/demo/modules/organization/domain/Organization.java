package com.demo.modules.organization.domain;

import com.demo.modules.common.domain.Address;
import com.demo.modules.common.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Setter
@Getter
public class Organization extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String code;
    private String name;
    @Embedded
    private Address address;
    private String lat;
    private String lng;
    private int totalMemberCount;
    private int currentMemberCount;


}
