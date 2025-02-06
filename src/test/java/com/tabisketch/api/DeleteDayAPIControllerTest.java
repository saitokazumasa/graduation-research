package com.tabisketch.api;

import com.tabisketch.bean.entity.ExampleDay;
import com.tabisketch.service.IDeleteDayService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(DeleteDayAPIControler.class)
public class DeleteDayAPIControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private IDeleteDayService __; // DIで使用

    @Test
    @WithMockUser
    public void testPost() throws Exception {
        final var id = ExampleDay.generate().getId();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/delete-day/" + id)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
