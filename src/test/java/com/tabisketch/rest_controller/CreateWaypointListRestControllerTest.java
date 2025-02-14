//package com.tabisketch.rest_controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.tabisketch.bean.form.ExampleCreateWaypointListForm;
//import com.tabisketch.bean.view_model.ExampleWaypointListViewModel;
//import com.tabisketch.service.ICreateWaypointListService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(CreateWaypointListRestController.class)
//public class CreateWaypointListRestControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @MockitoBean
//    private ICreateWaypointListService createWaypointListService;
//
//    @Test
//    @WithMockUser
//    public void testPost() throws Exception {
//        final var createWaypointListForm = ExampleCreateWaypointListForm.gen();
//        final var waypointListViewModel = ExampleWaypointListViewModel.gen();
//
//        when(this.createWaypointListService.execute(anyString(), any())).thenReturn(waypointListViewModel);
//        this.mockMvc.perform(post("/api/waypoint-list/create")
//                        .flashAttr("createWaypointListForm", createWaypointListForm)
//                        .with(csrf()))
//                .andExpect(status().isOk())
//                .andExpect(content().json(this.objectMapper.writeValueAsString(waypointListViewModel)));
//    }
//}
