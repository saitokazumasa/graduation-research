package com.tabisketch.rest_controller;

import com.tabisketch.bean.entity.ExampleWaypointList;
import com.tabisketch.service.IDeleteWaypointListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeleteWaypointListRestController.class)
public class DeleteWaypointListRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private IDeleteWaypointListService deleteWaypointListService;

    @Test
    @WithMockUser
    public void testPost() throws Exception {
        final var id = ExampleWaypointList.gen().getId();
        this.mockMvc.perform(post("/api/waypoint-list/delete/" + id)
                .with(csrf())
        ).andExpect(status().isOk());
    }
}
