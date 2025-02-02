//package com.tabisketch.controller;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//@WebMvcTest(TopController.class)
//public class TopControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    @WithMockUser
//    public void testGet() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/top"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.view().name("top"));
//    }
//}
