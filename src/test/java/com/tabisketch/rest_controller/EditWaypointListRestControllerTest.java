package com.tabisketch.rest_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tabisketch.bean.form.EditWaypointListForm;
import com.tabisketch.bean.form.ExampleEditWaypointListForm;
import com.tabisketch.bean.view_model.ExampleWaypointListViewModel;
import com.tabisketch.service.IEditWaypointListService;
import jakarta.servlet.ServletException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EditWaypointListRestController.class)
public class EditWaypointListRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private IEditWaypointListService editWaypointListService;

    @ParameterizedTest
    @WithMockUser
    @MethodSource("provideTestData")
    public void testPost(final EditWaypointListForm editWaypointListForm, final boolean isSuccess) throws Exception {
        final var waypointListViewModel = ExampleWaypointListViewModel.gen();
        when(this.editWaypointListService.execute(anyString(), any())).thenReturn(waypointListViewModel);

        if (isSuccess) {
            this.mockMvc.perform(post("/api/waypoint-list/edit")
                            .flashAttr("editWaypointListForm", editWaypointListForm)
                            .with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(content().json(this.objectMapper.writeValueAsString(waypointListViewModel)));
            return;
        }

        assertThrows(
                ServletException.class,
                () -> this.mockMvc.perform(post("/api/waypoint-list/edit")
                        .flashAttr("editWaypointListForm", editWaypointListForm)
                        .with(csrf()))
        );
    }

    private static Stream<Arguments> provideTestData() {
        final var example = ExampleEditWaypointListForm.gen();
        final var id = example.getId();
        final var mainTransporation = example.getMainTransporation();
        final var planUUID = example.getPlanUUID();

        return Stream.of(
                Arguments.of(example, true),
                Arguments.of(new EditWaypointListForm(0, null, planUUID), false),
                Arguments.of(new EditWaypointListForm(id, null, planUUID), false),
                Arguments.of(new EditWaypointListForm(id, mainTransporation, null), false)
        );
    }
}
