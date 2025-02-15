package com.tabisketch.controller;

import com.tabisketch.bean.form.*;
import com.tabisketch.service.ICreatePlanService;
import com.tabisketch.service.ICreateWaypointListService;
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

import static org.mockito.ArgumentMatchers.any;
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
    @MockitoBean
    private IEditPlanService editPlanService;
    @MockitoBean
    private ICreateWaypointListService createWaypointListService;

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
        if (isSuccess) {
            when(this.editPlanService.execute(anyString(), any())).thenReturn(editPlanForm);
            when(this.createWaypointListService.execute(anyString(), any())).thenReturn(ExampleEditWaypointListForm.gen());

            this.mockMvc.perform(post("/plan/create")
                            .flashAttr("editPlanForm", editPlanForm)
                            .flashAttr("createWaypointListForm", createWaypointListForm)
                            .with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(model().hasNoErrors())
                    .andExpect(redirectedUrl("/plan/edit/" + editPlanForm.getUuid()));
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
