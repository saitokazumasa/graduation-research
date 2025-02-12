package com.tabisketch.controller;

import com.tabisketch.service.IListPlanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ListPlansController.class)
public class ListPlanControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private IListPlanService listPlanService;

    @Test
    @WithMockUser
    public void testGet() throws Exception {
        when(this.listPlanService.execute(anyString())).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/plan/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("planList"))
                .andExpect(model().attribute("planList", new ArrayList<>()))
                .andExpect(view().name("plan/list"));
    }
}
