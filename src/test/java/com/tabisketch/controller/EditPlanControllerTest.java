package com.tabisketch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tabisketch.bean.entity.ExamplePlan;
import com.tabisketch.bean.form.EditPlanForm;
import com.tabisketch.bean.form.ExampleEditPlanForm;
import com.tabisketch.service.IEditPlanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EditPlanController.class)
public class EditPlanControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private IEditPlanService editPlanService;

    @Test
    @WithMockUser
    public void testGet() throws Exception {
        final var uuid = ExamplePlan.gen().getUuid().toString();
        this.mockMvc.perform(get("/plan/edit/" + uuid))
                .andExpect(status().isOk())
                .andExpect(view().name("plan/edit"));
    }

    @ParameterizedTest
    @WithMockUser
    @MethodSource("provideTestData")
    public void testPost(final EditPlanForm editPlanForm, final boolean isSuccess) throws Exception {
        final var uuid = ExamplePlan.gen().getUuid().toString();

        if (isSuccess) {
            this.mockMvc.perform(post("/plan/edit/" + uuid)
                            .flashAttr("editPlanForm", editPlanForm)
                            .with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(model().hasNoErrors())
                    .andExpect(redirectedUrl("/plan/edit/" + uuid));
            return;
        }

        this.mockMvc.perform(post("/plan/edit/" + uuid)
                        .flashAttr("editPlanForm", editPlanForm)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("editPlanForm"))
                .andExpect(model().attribute("editPlanForm", editPlanForm))
                .andExpect(view().name("plan/edit"));
    }

    private static Stream<Arguments> provideTestData() {
        final var example = ExampleEditPlanForm.gen();
        final var uuid = example.getUuid();
        final var title = example.getTitle();
        final var thumbnailPath = example.getThumbnailPath();

        return Stream.of(
                Arguments.of(example, true),
                Arguments.of(new EditPlanForm(uuid, "", thumbnailPath, true, false), false),
                Arguments.of(new EditPlanForm(uuid, title, null, true, false), false)
        );
    }
}
