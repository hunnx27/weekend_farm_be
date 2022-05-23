package com.demo.config.security;

import com.demo.common.filter.JwtFilter;
import com.demo.config.security.exception.AccessDeniedHandlerImpl;
import com.demo.config.security.exception.JwtAuthenticationEntryPointImpl;
import com.demo.config.security.jwt.JwtProvider;
import com.demo.config.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.demo.config.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.demo.config.security.oauth2.CustomOAuth2UserService;
import com.demo.config.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailServiceImpl userDetailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationEntryPointImpl authenticationEntryPoint;
    private final AccessDeniedHandlerImpl accessDeniedHandler;
    private final JwtProvider jwtProvider;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic().and()
                .cors().and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/index").not().authenticated()
//                .antMatchers(HttpMethod.POST, "/api/accounts").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
//                .antMatchers("/", "/hello").hasAnyRole()
                .anyRequest().authenticated();

//        http.exceptionHandling()
//                .authenticationEntryPoint(authenticationEntryPoint)
//                .accessDeniedHandler(accessDeniedHandler)
//                .and()
//                .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

        http.logout()
                .logoutSuccessUrl("/");

        http.oauth2Login()
                .defaultSuccessUrl("/login-success")
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler)
                .authorizationEndpoint()
                .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                .and()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

        http.addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
//        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository(){
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

}
