package com.garlickim.book.search.account;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
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

    String             username = "garlic";
    String             password = "1234";





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
    @Transactional
    public void login_success() throws Exception
    {
        Account user = this.createUser(this.username, this.password);
        this.mockMvc.perform(formLogin().loginProcessingUrl("/user/login")
                                        .user(user.getUsername())
                                        .password(this.password))
                    .andExpect(authenticated());
    }





    @Test
    @Transactional()
    public void login_fail() throws Exception
    {
        Account user = this.createUser(this.username, this.password);
        this.mockMvc.perform(formLogin().user(user.getUsername())
                                        .password("12345"))
                    .andExpect(unauthenticated());
    }





    // ID 중복 체크 TRUE
    @Test
    @Transactional()
    public void isExistUsernameTest1()
    {
        Account user = this.createUser(this.username, this.password);
        Assert.assertTrue(this.accountService.isExistUsername(user.getUsername()));
    }





    // ID 중복 체크 FALSE
    @Test
    @Transactional()
    public void isExistUsernameTest2()
    {
        Assert.assertFalse(this.accountService.isExistUsername(this.username));
    }





    private Account createUser(String username, String password)
    {
        return this.accountService.createNew(Account.builder()
                                                    .username(username)
                                                    .password(password)
                                                    .build());
    }
}
