package com.wefarm.modules.auth.web;

import com.wefarm.common.util.CookieUtils;
import com.wefarm.config.security.UserDetailServiceImpl;
import com.wefarm.config.security.jwt.JwtProvider;
import com.wefarm.modules.auth.application.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserDetailServiceImpl userDetailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(HttpServletResponse response,
        @RequestBody LoginRequest loginRequest) {

        UserDetails principal = userDetailService.loadUserByUsername(loginRequest.getName());
        if (!passwordEncoder.matches(loginRequest.getPassword(), principal.getPassword())) {
            throw new UsernameNotFoundException("invalid Password");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(principal,
            principal.getPassword(), principal.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        String token = jwtProvider.createToken(authentication);
        response.setHeader("Authorization", token);

        CookieUtils.addCookie(response, "Authorization", token, 180);

        return ResponseEntity.ok(token);
    }
}
