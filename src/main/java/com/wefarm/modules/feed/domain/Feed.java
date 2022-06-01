package com.wefarm.modules.feed.domain;

import com.wefarm.modules.common.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Setter
@Getter
public class Feed extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String title;
    private String message;
    private String likeCnt;
}
