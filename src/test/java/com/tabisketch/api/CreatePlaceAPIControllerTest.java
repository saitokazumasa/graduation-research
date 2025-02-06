package com.tabisketch.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tabisketch.bean.form.ExampleCreatePlaceForm;
import com.tabisketch.bean.response.implement.CreatePlaceResponse;
import com.tabisketch.service.ICreatePlaceService;
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

@WebMvcTest(CreatePlaceAPIController.class)
public class CreatePlaceAPIControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private ICreatePlaceService createPlaceService;

    @Test
    @WithMockUser
    public void testPost() throws Exception {
        final int placeId = 1;
        when(this.createPlaceService.execute(any())).thenReturn(placeId);

        final var createPlaceForm = ExampleCreatePlaceForm.generate();
        final String responseJson = this.objectMapper.writeValueAsString(CreatePlaceResponse.success(placeId));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/create-place")
                        .flashAttr("createPlaceForm", createPlaceForm)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson));
    }
}
