package com.tabisketch.controller;

import com.tabisketch.bean.entity.ExamplePlan;
import com.tabisketch.bean.entity.ExampleWaypointList;
import com.tabisketch.bean.form.CreateWaypointListForm;
import com.tabisketch.bean.form.EditPlanForm;
import com.tabisketch.bean.form.ExampleCreateWaypointListForm;
import com.tabisketch.bean.form.ExampleEditPlanForm;
import com.tabisketch.service.ICreatePlanService;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CreatePlanController.class)
public class CreatePlanControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ICreatePlanService createPlanService;

    @Test
    @WithMockUser
    public void testGet() throws Exception {
        final var editPlanForm = ExampleEditPlanForm.gen();
        when(this.createPlanService.execute(anyString())).thenReturn(editPlanForm);
        mockMvc.perform(get("/plan/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("editPlanForm"))
                .andExpect(model().attributeExists("createWaypointListForm"))
                .andExpect(view().name("plan/create"));
    }

    @ParameterizedTest
    @WithMockUser
    @MethodSource("provideTestData")
    public void testPost(
            final EditPlanForm editPlanForm,
            final CreateWaypointListForm createWaypointListForm,
            final boolean isSuccess
    ) throws Exception {
        final var uuid = ExamplePlan.gen().getUuid().toString();

        if (isSuccess) {
            this.mockMvc.perform(post("/plan/create")
                            .flashAttr("editPlanForm", editPlanForm)
                            .flashAttr("createWaypointListForm", createWaypointListForm)
                            .with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(model().hasNoErrors())
                    // TODO: UUIDを末尾につける
                    .andExpect(redirectedUrl("/plan/edit"));
            return;
        }

        this.mockMvc.perform(post("/plan/create")
                        .flashAttr("editPlanForm", editPlanForm)
                        .flashAttr("createWaypointListForm", createWaypointListForm)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("editPlanForm"))
                .andExpect(model().attributeExists("createWaypointListForm"))
                .andExpect(view().name("plan/create"));
    }

    private static Stream<Arguments> provideTestData() {
        final var editPlanForm = ExampleEditPlanForm.gen();
        final var createWaypointListForm = ExampleCreateWaypointListForm.gen();

        return Stream.of(
                Arguments.of(editPlanForm, createWaypointListForm, true)
                // TODO: ヴァリデーションテスト用クラスを作成する、今はテストが通らないため未作成
        );
    }
}
