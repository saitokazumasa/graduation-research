package com.tabisketch.rest_controller;

import com.tabisketch.bean.entity.ExamplePlan;
import com.tabisketch.service.IDeletePlanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeletePlanRestController.class)
public class DeletePlanRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private IDeletePlanService deletePlanService;

    @Test
    @WithMockUser
    public void testPost() throws Exception {
        final var uuid = ExamplePlan.gen().getUuid();
        this.mockMvc.perform(post("/api/plan/delete/" + uuid)
                .with(csrf())
        ).andExpect(status().isOk());
    }
}
