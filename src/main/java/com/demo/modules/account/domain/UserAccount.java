package com.demo.modules.account.domain;

import com.demo.modules.account.type.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

@Getter
public class UserAccount extends User implements OAuth2User {

    private final Account account;

    private Map<String, Object> attributes;

    public UserAccount(Account account) {
        super(account.getName(), account.getPassword(), Collections.singleton(new SimpleGrantedAuthority(Role.USER.getRole())));
        this.account = account;
    }

    public UserAccount(Account account, String password) {
        super(account.getEmail(), password, Collections.singleton(new SimpleGrantedAuthority(Role.USER.getRole())));
        this.account = account;
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isEnabled() {
        return true;
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority(account.getRole().getRole()));
        return authList;
    }

    @Override
    public String getName() {
        return account.getName();
    }

    public static UserAccount to(Account account){
        return new UserAccount(account, "");
    }
}
