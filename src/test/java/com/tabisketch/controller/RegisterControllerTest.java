package com.tabisketch.controller;

import com.tabisketch.bean.entity.ExampleEmailVerificationToken;
import com.tabisketch.bean.form.ExampleSendResetPasswordMailForm;
import com.tabisketch.bean.form.RegisterForm;
import com.tabisketch.bean.form.ExampleRegisterForm;
import com.tabisketch.service.IRegisterService;
import com.tabisketch.service.IVerifyEmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
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

@WebMvcTest(RegisterController.class)
public class RegisterControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private IRegisterService registerService;
    @MockitoBean
    private IVerifyEmailService verifyEmailService;

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
        final var form = ExampleRegisterForm.gen();
        this.mockMvc.perform(post("/register")
                        .flashAttr("registerForm", form)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/register/send"));
    }

    @ParameterizedTest
    @WithMockUser
    @MethodSource("validationTestData")
    public void testValidation(final RegisterForm form) throws Exception {
        this.mockMvc.perform(post("/register")
                        .flashAttr("registerForm", form)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("registerForm"))
                .andExpect(model().attribute("registerForm", form))
                .andExpect(view().name("register/index"));
    }

    private static Stream<Object> validationTestData() {
        final var stream = Stream.builder();
        stream.add(new RegisterForm("", "password", "password"));
        stream.add(new RegisterForm("example@example.com", "", "password"));
        stream.add(new RegisterForm("example@example.com", "password", ""));
        stream.add(new RegisterForm("example@example.com", "password", "pass"));
        return stream.build();
    }

    @Test
    @WithMockUser
    public void testSend() throws Exception {
        final var email = ExampleSendResetPasswordMailForm.gen().getEmail();
        this.mockMvc.perform(get("/register/send")
                        .flashAttr("email", email)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("email"))
                .andExpect(model().attribute("email", email))
                .andExpect(view().name("register/send"));
    }

    @Test
    @WithMockUser
    public void testVerify() throws Exception {
        final var uuid = ExampleEmailVerificationToken.gen().getUuid().toString();
        this.mockMvc.perform(get("/register/v/" + uuid))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register/complate"));
    }

    @Test
    @WithMockUser
    public void testComplate() throws Exception {
        this.mockMvc.perform(get("/register/complate"))
                .andExpect(status().isOk())
                .andExpect(view().name("register/complate"));
    }
}
