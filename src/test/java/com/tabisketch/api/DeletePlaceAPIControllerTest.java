package com.tabisketch.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tabisketch.bean.form.ExampleDeletePlaceForm;
import com.tabisketch.bean.response.implement.DeletePlaceResponse;
import com.tabisketch.service.IDeletePlaceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(DeletePlaceAPIController.class)
public class DeletePlaceAPIControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private IDeletePlaceService __; // DIで使用

    @Test
    @WithMockUser
    public void testPost() throws Exception {
        final var deletePlaceForm = ExampleDeletePlaceForm.generate();
        final String responseJson = this.objectMapper.writeValueAsString(DeletePlaceResponse.success());

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/delete-place")
                        .flashAttr("deletePlaceForm", deletePlaceForm)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson));
    }
}
