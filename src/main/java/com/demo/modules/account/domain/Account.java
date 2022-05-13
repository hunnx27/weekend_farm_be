package com.demo.modules.account.domain;

import com.demo.modules.account.application.request.AccountUpdateRequest;
import com.demo.modules.common.domain.BaseEntity;
import com.demo.modules.education.domain.Education;
import com.demo.modules.common.type.YN;
import com.demo.modules.organization.domain.Organization;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @JsonIgnore
    private String password;
    private String email;
    private String age;
    private String location;

    @ManyToMany(mappedBy = "accounts")
    @JsonBackReference
    private Set<Education> educations = new HashSet<>();

    @ManyToOne
    private Organization organization;

    public void setUpdateData(AccountUpdateRequest account){
        this.name = account.getName();
        this.email = account.getEmail();
        this.age = account.getAge();
        this.location = account.getLocation();
    }
}
