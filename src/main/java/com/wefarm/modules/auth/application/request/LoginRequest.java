package com.wefarm.modules.auth.application.request;

import lombok.Getter;

@Getter
public class LoginRequest {

    private String name;
    private String password;
}
