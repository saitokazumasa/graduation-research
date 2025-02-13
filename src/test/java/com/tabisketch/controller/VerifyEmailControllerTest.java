package com.tabisketch.controller;

import com.tabisketch.bean.entity.ExampleEmailVerificationToken;
import com.tabisketch.bean.entity.ExampleNewEmailVerificartionToken;
import com.tabisketch.service.IEditEmailService;
import com.tabisketch.service.IVerifyEmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VerifyEmailController.class)
public class VerifyEmailControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private IVerifyEmailService verifyEmailService;
    @MockitoBean
    private IEditEmailService editEmailService;

    @Test
    @WithMockUser
    public void testVerifyNewEmail() throws Exception {
        final var uuid = ExampleEmailVerificationToken.gen().getUuid().toString();
        this.mockMvc.perform(get("/mail/v/" + uuid))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/mail/complete"));
    }

    @Test
    @WithMockUser
    public void testVerifyEmail() throws Exception {
        final var uuid = ExampleNewEmailVerificartionToken.gen().getUuid().toString();
        this.mockMvc.perform(get("/mail/nv/" + uuid))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/mail/complete"));
    }

    @Test
    @WithMockUser
    public void testComplete() throws Exception {
        this.mockMvc.perform(get("/mail/complete"))
                .andExpect(status().isOk())
                .andExpect(view().name("mail/complete"));
    }
}
