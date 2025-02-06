package com.tabisketch.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tabisketch.bean.form.ExampleUpdatePlaceForm;
import com.tabisketch.bean.response.implement.UpdatePlaceResponse;
import com.tabisketch.service.IUpdatePlaceService;
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

@WebMvcTest(UpdatePlaceAPIController.class)
public class UpdatePlaceAPIControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private IUpdatePlaceService updatePlaceService;

    @Test
    @WithMockUser
    public void testPost() throws Exception {
        final int placeId = 1;
        when(this.updatePlaceService.execute(any())).thenReturn(placeId);

        final var updatePlaceForm = ExampleUpdatePlaceForm.generate();
        final String responseJson = this.objectMapper.writeValueAsString(UpdatePlaceResponse.success(placeId));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/update-place")
                        .flashAttr("updatePlaceForm", updatePlaceForm)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson));
    }
}
