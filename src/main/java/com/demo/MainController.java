package com.demo;

import com.demo.common.util.CookieUtils;
import com.demo.config.security.UserDetailServiceImpl;
import com.demo.config.security.jwt.JwtProvider;
import com.demo.modules.account.domain.Account;
import com.demo.modules.account.domain.UserAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MainController {

    private final UserDetailServiceImpl userDetailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @GetMapping("/hello")
    public HttpStatus hello() {
        return HttpStatus.OK;
    }

    @GetMapping("/")
    public void main(HttpServletRequest req, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserAccount) {
            Account account = ((UserAccount) authentication.getPrincipal()).getAccount();
            log.info(String.valueOf(account));
        }
        log.info(String.valueOf(req));
        log.info(String.valueOf(model));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletResponse response, @RequestParam String name, @RequestParam String password) {
        UserDetails principal = userDetailService.loadUserByUsername(name);
        if (!passwordEncoder.matches(password, principal.getPassword()))
            throw new UsernameNotFoundException("invalid Password");

        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        String token = jwtProvider.createToken(authentication);
        response.setHeader("Authorization", token);

        CookieUtils.addCookie(response, "Authorization", token, 180);

        return ResponseEntity.ok(token);
    }

}
