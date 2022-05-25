package com.demo.modules.common.advisor;

import com.demo.modules.account.domain.Account;
import com.demo.modules.account.domain.UserAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class CommonExceptionAdvisor {

    @ExceptionHandler
    public String handleRuntimeException(HttpServletRequest req, RuntimeException e) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() != "anonymousUser" && authentication.isAuthenticated()){
            Account account = ((UserAccount) authentication.getPrincipal()).getAccount();
            if (account != null) {
                log.info("'{}' requested '{}'", account.getName(), req.getRequestURI());
            } else {
                log.info("requested '{}'", req.getRequestURI());
            }
        }
        log.error("bad request", e);
        return "error";
    }


}
