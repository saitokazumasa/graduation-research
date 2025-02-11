package com.tabisketch.controller;

import com.tabisketch.bean.entity.ExampleResetPasswordToken;
import com.tabisketch.bean.form.ExampleResetPasswordForm;
import com.tabisketch.bean.form.ExampleSendResetPasswordMailForm;
import com.tabisketch.bean.form.ResetPasswordForm;
import com.tabisketch.bean.form.SendResetPasswordMailForm;
import com.tabisketch.service.IResetPasswordService;
import com.tabisketch.service.ISendResetPasswordMailService;
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

@WebMvcTest(ResetPasswordController.class)
public class ResetPasswordControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ISendResetPasswordMailService sendResetPasswordMailService;
    @MockitoBean
    private IResetPasswordService resetPasswordService;

    @Test
    @WithMockUser
    public void testGet() throws Exception {
        this.mockMvc.perform(get("/reset-password"))
                .andExpect(status().isOk())
                .andExpect(view().name("reset-password/index"));
    }

    @Test
    @WithMockUser
    public void testPost() throws Exception {
        final var form = ExampleSendResetPasswordMailForm.gen();
        this.mockMvc.perform(post("/reset-password")
                        .flashAttr("sendResetPasswordMailForm", form)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/reset-password/send"));
    }

    @ParameterizedTest
    @WithMockUser
    @MethodSource("SRPMFormValidationTestData")
    public void testSRPMFormValidation(final SendResetPasswordMailForm form) throws Exception {
        this.mockMvc.perform(post("/reset-password")
                        .flashAttr("sendResetPasswordMailForm", form)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("sendResetPasswordMailForm"))
                .andExpect(model().attribute("sendResetPasswordMailForm", form))
                .andExpect(view().name("reset-password/index"));
    }

    private static Stream<Object> SRPMFormValidationTestData() {
        final var stream = Stream.builder();
        stream.add(new SendResetPasswordMailForm(""));
        return stream.build();
    }

    @Test
    @WithMockUser
    public void testSend() throws Exception {
        final var email = ExampleSendResetPasswordMailForm.gen().getEmail();
        this.mockMvc.perform(get("/reset-password/send")
                        .flashAttr("email", email)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("email"))
                .andExpect(model().attribute("email", email))
                .andExpect(view().name("reset-password/send"));
    }

    @Test
    @WithMockUser
    public void testGetReset() throws Exception {
        final var uuid = ExampleResetPasswordToken.gen().getUuid().toString();
        this.mockMvc.perform(get("/reset-password/r/" + uuid))
                .andExpect(status().isOk())
                .andExpect(view().name("reset-password/{uuid}"));
    }

    @Test
    @WithMockUser
    public void testPostReset() throws Exception {
        final var uuid = ExampleResetPasswordToken.gen().getUuid().toString();
        final var form = ExampleResetPasswordForm.gen();
        this.mockMvc.perform(post("/reset-password/r/" + uuid)
                        .flashAttr("resetPasswordForm", form)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/reset-password/complate"));
    }

    @ParameterizedTest
    @WithMockUser
    @MethodSource("RPFormValidationTestData")
    public void testRPFormValidation(final ResetPasswordForm form) throws Exception {
        final var uuid = ExampleResetPasswordToken.gen().getUuid().toString();
        this.mockMvc.perform(post("/reset-password/r/" + uuid)
                        .flashAttr("resetPasswordForm", form)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("resetPasswordForm"))
                .andExpect(model().attribute("resetPasswordForm", form))
                .andExpect(view().name("reset-password/{uuid}"));
    }

    private static Stream<Object> RPFormValidationTestData() {
        final var stream = Stream.builder();
        stream.add(new ResetPasswordForm("", ""));
        stream.add(new ResetPasswordForm("password", ""));
        stream.add(new ResetPasswordForm("", "password"));
        stream.add(new ResetPasswordForm("password", "pass"));
        return stream.build();
    }

    @Test
    @WithMockUser
    public void testComplate() throws Exception {
        this.mockMvc.perform(get("/reset-password/complate"))
                .andExpect(status().isOk())
                .andExpect(view().name("reset-password/complate"));
    }
}
