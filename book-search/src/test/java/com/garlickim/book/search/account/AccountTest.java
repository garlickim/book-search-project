package com.garlickim.book.search.account;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.garlickim.book.search.domain.entity.Account;
import com.garlickim.book.search.service.impl.AccountServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountTest
{

    @Autowired
    MockMvc            mockMvc;

    @Autowired
    AccountServiceImpl accountService;

    @Test
    @WithAnonymousUser // anotation 방식으로 user 정보 set 하는 방법
    public void index_anonymous() throws Exception
    {
        this.mockMvc.perform(get("/user/login"))
                    .andDo(print())
                    .andExpect(status().isOk());
    }





    @Test // with 함수를 사용하여 직접 user 정보를 set 하는 방법
    public void test_index_user() throws Exception
    {
        this.mockMvc.perform(get("/").with(user("garlic").roles("USER")))
                    .andDo(print())
                    .andExpect(status().isOk());
    }





    @Test
    @Transactional // DB 테스트 시, 데이터가 dependancy 하지 않도록 @Transaction을 붙여주는 것이 좋다.
    public void login_success() throws Exception
    {
        String username = "garlic";
        String password = "1234";
        Account user = this.createUser(username, password);
        this.mockMvc.perform(formLogin().loginProcessingUrl("/user/login")
                                        .user(user.getUsername())
                                        .password(password))
                    .andExpect(authenticated());
    }





    @Test
    @Transactional()
    public void login_fail() throws Exception
    {
        String username = "garlic";
        String password = "1234";
        Account user = this.createUser(username, password);
        this.mockMvc.perform(formLogin().user(user.getUsername())
                                        .password("12345"))
                    .andExpect(unauthenticated());
    }





    private Account createUser(String username, String password)
    {
        return this.accountService.createNew(Account.builder()
                                                    .username(username)
                                                    .password(password)
                                                    .build());
    }
}
