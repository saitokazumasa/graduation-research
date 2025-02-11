package com.tabisketch.rest_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tabisketch.bean.entity.ExamplePlan;
import com.tabisketch.bean.form.EditPlanForm;
import com.tabisketch.bean.form.ExampleEditPlanForm;
import com.tabisketch.bean.output.ExamplePlanOutput;
import com.tabisketch.service.IEditPlanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
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

    @Test
    @WithMockUser
    public void testPost() throws Exception {
        final var uuid = ExamplePlan.gen().getUuid();
        final var editPlanForm = ExampleEditPlanForm.gen();
        final var output = ExamplePlanOutput.gen();

        when(this.editPlanService.execute(any(), any())).thenReturn(output);
        this.mockMvc.perform(post("/api/plan/edit/" + uuid)
                        .flashAttr("editPlanForm", editPlanForm)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json(this.objectMapper.writeValueAsString(output)));
    }

    @ParameterizedTest
    @WithMockUser
    @MethodSource("validationTestData")
    public void testValidation(final EditPlanForm form) {
        final var uuid = ExamplePlan.gen().getUuid();
        final var output = ExamplePlanOutput.gen();

        when(this.editPlanService.execute(any(), any())).thenReturn(output);
        try {
            this.mockMvc.perform(post("/api/plan/edit/" + uuid)
                            .flashAttr("editPlanForm", form)
                            .with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(content().json(this.objectMapper.writeValueAsString(output)));
        } catch (final Exception e) {
            System.out.println(e.getMessage());
            assert true;
            return;
        }
        assert false;
    }

    private static Stream<Object> validationTestData() {
        final var stream = Stream.builder();
        stream.add(new EditPlanForm("", "", true, false));
        return stream.build();
    }
}
