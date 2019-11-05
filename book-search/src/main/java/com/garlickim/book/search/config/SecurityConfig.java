package com.garlickim.book.search.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.garlickim.book.search.service.impl.AccountServiceImpl;

// Spring Security Config 파일
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

    @Autowired
    AccountServiceImpl accountService;

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
            .antMatchers("/users", "/user/**", "/h2console/**", "/js/**", "/users/duplicate/**")
            .permitAll() // 해당 URL에 대하여 로그인없이 접근할 수 있도록 인가
            .anyRequest()
            .authenticated(); // 나머지 URL에 대하여 로그인이 필요

        http.csrf()
            .ignoringAntMatchers("/h2console/**");

        http.headers()
            .frameOptions()
            .disable();

        http.formLogin()
            .loginPage("/user/login")
            .defaultSuccessUrl("/");

        http.logout()
            .logoutUrl("/user/logout")
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID");

        // http 인증 방식 중, basic 인증(Base64 인코딩) 사용
        http.httpBasic();
    }





    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(this.accountService);
    }





    @Bean
    public PasswordEncoder passwordEncoder()
    {
        // 기본 패스워드 인코더를 Bcrypt로 설정 (security 5 이전에는 NoOpPasswordEncoder를 기본으로 사용함)
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
