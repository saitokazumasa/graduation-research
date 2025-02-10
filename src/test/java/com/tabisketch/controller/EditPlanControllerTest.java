package com.tabisketch.controller;

import com.tabisketch.bean.entity.ExamplePlan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(EditPlanController.class)
public class EditPlanControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void testGet() throws Exception {
        final var uuid = ExamplePlan.gen().getUuid().toString();
        mockMvc.perform(get("/plan/" + uuid + "/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("plan/edit"));
    }
}
