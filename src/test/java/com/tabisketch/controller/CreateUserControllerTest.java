package com.tabisketch.controller;

import com.tabisketch.bean.entity.ExampleEmailVerificationToken;
import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.bean.form.CreateUserForm;
import com.tabisketch.bean.form.ExampleCreateUserForm;
import com.tabisketch.service.ICreateEmailVerificationTokenService;
import com.tabisketch.service.ICreateUserService;
import com.tabisketch.service.ISendMailService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CreateUserController.class)
public class CreateUserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ICreateUserService createUserService;
    @MockitoBean
    private ICreateEmailVerificationTokenService createEmailVerificationTokenService;
    @MockitoBean
    private ISendMailService sendMailService;

    @Test
    @WithMockUser
    public void testGet() throws Exception {
        this.mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register/index"));
    }

    @Test
    @WithMockUser
    public void testPost() throws Exception {
        final var form = ExampleCreateUserForm.gen();
        when(this.createUserService.execute(any())).thenReturn(ExampleUser.gen());
        when(this.createEmailVerificationTokenService.execute(any())).thenReturn(ExampleEmailVerificationToken.gen());
        this.mockMvc.perform(post("/register")
                        .flashAttr("createUserForm", form)
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/register/send"));
    }

    @ParameterizedTest
    @WithMockUser
    @MethodSource("validationTestData")
    public void testValidation(final CreateUserForm form) throws Exception {
        this.mockMvc.perform(post("/register")
                        .flashAttr("createUserForm", form)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("createUserForm"))
                .andExpect(model().attribute("createUserForm", form))
                .andExpect(view().name("register/index"));
    }

    private static Stream<Object> validationTestData() {
        final var stream = Stream.builder();
        stream.add(new CreateUserForm("", "password", "password"));
        stream.add(new CreateUserForm("example@example.com", "", "password"));
        stream.add(new CreateUserForm("example@example.com", "password", ""));
        stream.add(new CreateUserForm("example@example.com", "password", "pass"));
        return stream.build();
    }

    @Test
    @WithMockUser
    public void testSend() throws Exception {
        this.mockMvc.perform(get("/register/send")
                        .flashAttr("email", ExampleUser.gen().getEmail())
                        .with(csrf())
                ).andExpect(status().isOk())
                .andExpect(view().name("register/send"));
    }
}
