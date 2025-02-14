//package com.tabisketch.controller;
//
//import com.tabisketch.bean.entity.ExamplePlan;
//import com.tabisketch.service.ICreatePlanService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(CreatePlanController.class)
//public class CreatePlanControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @MockitoBean
//    private ICreatePlanService createPlanService;
//
//    @Test
//    @WithMockUser
//    public void testGet() throws Exception {
//        final var uuid = ExamplePlan.gen().getUuid().toString();
//        when(this.createPlanService.execute(anyString())).thenReturn(uuid);
//        mockMvc.perform(get("/plan/create"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/plan/edit/" + uuid));
//    }
//}
