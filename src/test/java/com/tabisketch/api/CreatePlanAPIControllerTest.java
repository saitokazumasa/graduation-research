package com.tabisketch.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tabisketch.bean.form.ExampleCreatePlanForm;
import com.tabisketch.bean.response.implement.CreatePlanResponse;
import com.tabisketch.service.ICreatePlanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(CreatePlanAPIController.class)
public class CreatePlanAPIControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private ICreatePlanService createPlanService;

    @Test
    @WithMockUser
    public void testPost() throws Exception {
        final int planId = 1;
        when(this.createPlanService.execute(any())).thenReturn(planId);

        final var createPlanForm = ExampleCreatePlanForm.generate();
        final String responseJson = this.objectMapper.writeValueAsString(CreatePlanResponse.success(planId));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/create-plan")
                        .flashAttr("createPlanForm", createPlanForm)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson));
    }
}
