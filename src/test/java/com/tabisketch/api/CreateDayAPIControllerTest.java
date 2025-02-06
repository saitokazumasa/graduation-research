package com.tabisketch.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tabisketch.bean.form.ExampleCreateDayForm;
import com.tabisketch.bean.response.implement.CreateDayResponse;
import com.tabisketch.service.implement.CreateDayService;
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

@WebMvcTest(CreateDayAPIController.class)
public class CreateDayAPIControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private CreateDayService createDayService;

    @Test
    @WithMockUser
    public void testPost() throws Exception {
        final int dayId = 1;
        when(this.createDayService.execute(any())).thenReturn(dayId);

        final var createDayForm = ExampleCreateDayForm.generate();
        final String responseJson = this.objectMapper.writeValueAsString(CreateDayResponse.success(dayId));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/create-day")
                        .flashAttr("createDayForm", createDayForm)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson));
    }
}
