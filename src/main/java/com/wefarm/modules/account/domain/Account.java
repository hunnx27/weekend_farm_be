package com.wefarm.modules.account.domain;

import com.wefarm.modules.account.application.request.AccountUpdateRequest;
import com.wefarm.modules.account.type.Role;
import com.wefarm.modules.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @JsonIgnore
    private String password;
    @NotNull
    @Column(unique = true)
    private String email;
    private String age;
    private String location;

    private boolean isEmailVerified;
    private String profileImage;

    private Role role;

//    @ManyToMany(mappedBy = "accounts")
//    @JsonBackReference
//    private Set<Education> educations = new HashSet<>();

//    @ManyToOne
//    private Farm farm;

    public Account(String name, String email, String picture) {
        this.name = name;
        this.email = email;
        this.profileImage = picture;
        this.role = Role.USER;
    }

    public void setUpdateData(AccountUpdateRequest account) {
        this.name = account.getName();
        this.email = account.getEmail();
        this.age = account.getAge();
        this.location = account.getLocation();
    }

    public Account update(String name, String picture) {
        this.name = name;
        this.profileImage = picture;
        if (Objects.isNull(this.role)) {
            this.role = Role.USER;
        }

        return this;
    }

    public Account toAccount(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        return new Account(
            (String) attributes.get("name"),
            (String) attributes.get("email"),
            (String) attributes.get("picture"));
    }
}
