package com.demo.modules.account.domain;

import com.demo.modules.account.application.request.AccountUpdateRequest;
import com.demo.modules.education.domain.Education;
import com.demo.modules.common.type.YN;
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
public class Account {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @JsonIgnore
    private String password;
    private String email;
    private String age;
    private String location;

    @Enumerated(EnumType.STRING)
    private YN isDelete = YN.N;

    @ManyToMany(mappedBy = "accounts")
    @JsonBackReference
    private Set<Education> educations = new HashSet<>();

    public void setUpdateData(AccountUpdateRequest account){
        this.name = account.getName();
        this.email = account.getEmail();
        this.age = account.getAge();
        this.location = account.getLocation();
    }
}
