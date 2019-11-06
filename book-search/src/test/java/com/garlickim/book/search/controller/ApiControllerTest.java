package com.garlickim.book.search.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garlickim.book.search.domain.entity.History;
import com.garlickim.book.search.domain.vo.BookSearch;
import com.garlickim.book.search.domain.vo.Document;
import com.garlickim.book.search.exception.BookSearchException;
import com.garlickim.book.search.service.impl.AccountServiceImpl;
import com.garlickim.book.search.service.impl.HistoryServiceImpl;
import com.garlickim.book.search.service.impl.KakaoServiceImpl;
import com.garlickim.book.search.service.impl.NaverServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiControllerTest
{
    @Autowired
    ObjectMapper       objectMapper;

    @Autowired
    MockMvc            mockMvc;

    @Mock
    AccountServiceImpl accountService;

    @Mock
    KakaoServiceImpl   kakaoService;

    @Mock
    NaverServiceImpl   naverService;

    @Mock
    HistoryServiceImpl historyService;





    @Test
    @WithMockUser(username = "garlic")
    public void procBookSearch() throws Exception
    {
        BookSearch bookSearch = BookSearch.builder()
                                          .keyword("자료구조")
                                          .type("1")
                                          .page(1)
                                          .build();

        when(this.kakaoService.searchBook(any(BookSearch.class))).thenReturn(Document.builder()
                                                                                     .build());

        MvcResult mvcResult = this.mockMvc.perform(post("/book/search").with(csrf())
                                                                       .contentType(MediaType.APPLICATION_JSON)
                                                                       .content(this.objectMapper.writeValueAsString(bookSearch)))
                                          .andExpect(status().isOk())
                                          .andReturn();

        Assert.assertNotNull(this.objectMapper.readValue(mvcResult.getResponse()
                                                                  .getContentAsString(),
                                                         Document.class)
                                              .getBooks());
    }





    @Test
    @WithMockUser(username = "garlic")
    public void procBookSearch2() throws Exception
    {
        BookSearch bookSearch = BookSearch.builder()
                                          .keyword("자료구조")
                                          .type("1")
                                          .page(1)
                                          .build();

        when(this.kakaoService.searchBook(any(BookSearch.class))).thenThrow(new BookSearchException());

        when(this.naverService.searchBook(any(BookSearch.class))).thenReturn(Document.builder()
                                                                                     .build());

        MvcResult mvcResult = this.mockMvc.perform(post("/book/search").with(csrf())
                                                                       .contentType(MediaType.APPLICATION_JSON)
                                                                       .content(this.objectMapper.writeValueAsString(bookSearch)))
                                          .andExpect(status().isOk())
                                          .andReturn();

        Assert.assertNotNull(this.objectMapper.readValue(mvcResult.getResponse()
                                                                  .getContentAsString(),
                                                         Document.class)
                                              .getBooks());
    }





    @Test
    @WithMockUser(username = "garlic")
    public void procKeywordHistory() throws Exception
    {
        when(this.historyService.findKeywordHistory(any(String.class))).thenReturn(List.of(History.builder()
                                                                                                  .build()));

        this.mockMvc.perform(get("/users/garlic/keywords").with(csrf())
                                                          .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
    }





    @Test
    @WithMockUser(username = "garlic")
    public void procKeywords() throws Exception
    {
        when(this.historyService.findByKeywordAndCount()).thenReturn(List.of());

        this.mockMvc.perform(get("/keywords").with(csrf())
                                             .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
    }





    @Test
    @WithMockUser(username = "garlic")
    public void procDuplicateUser() throws Exception
    {
        when(this.accountService.isExistUsername(any(String.class))).thenReturn(true);

        this.mockMvc.perform(get("/users/duplicate/garlic").with(csrf())
                                                           .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
    }
}
