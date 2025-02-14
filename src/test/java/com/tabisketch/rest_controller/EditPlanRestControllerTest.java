package com.tabisketch.rest_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tabisketch.bean.form.EditPlanForm;
import com.tabisketch.bean.form.ExampleEditPlanForm;
import com.tabisketch.bean.view_model.ExamplePlanViewModel;
import com.tabisketch.service.IEditPlanService;
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

@WebMvcTest(EditPlanRestController.class)
public class EditPlanRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private IEditPlanService editPlanService;

    @ParameterizedTest
    @WithMockUser
    @MethodSource("provideTestData")
    public void testPost(final EditPlanForm editPlanForm, final boolean isSuccess) throws Exception {
        final var planViewModel = ExamplePlanViewModel.gen();
        when(this.editPlanService.execute(anyString(), any())).thenReturn(planViewModel);

        if (isSuccess) {
            this.mockMvc.perform(post("/api/plan/edit")
                            .flashAttr("editPlanForm", editPlanForm)
                            .with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(content().json(this.objectMapper.writeValueAsString(planViewModel)));
            return;
        }

        assertThrows(
                ServletException.class,
                () -> this.mockMvc.perform(post("/api/plan/edit")
                                .flashAttr("editPlanForm", editPlanForm)
                                .with(csrf()))
        );
    }

    private static Stream<Arguments> provideTestData() {
        final var example = ExampleEditPlanForm.gen();
        final var uuid = example.getUuid();
        final var title = example.getTitle();
        final var thumbnailPath = example.getThumbnailPath();

        return Stream.of(
                Arguments.of(example, true),
                Arguments.of(new EditPlanForm(null, title, thumbnailPath, true, false), false),
                Arguments.of(new EditPlanForm(uuid, "", thumbnailPath, true, false), false),
                Arguments.of(new EditPlanForm(uuid, title, null, true, false), false)

        );
    }
}
