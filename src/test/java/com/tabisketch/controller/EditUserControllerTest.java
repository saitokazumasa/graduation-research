package com.tabisketch.controller;

import com.tabisketch.bean.entity.ExampleNewEmailVerificartionToken;
import com.tabisketch.bean.form.EditEmailForm;
import com.tabisketch.bean.form.ExampleEditEmailForm;
import com.tabisketch.service.IEditEmailService;
import com.tabisketch.service.ISendEditEmailMailService;
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

@WebMvcTest(EditUserController.class)
public class EditUserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ISendEditEmailMailService sendEditEmailMailService;
    @MockitoBean
    private IEditEmailService editEmailService;

    @Test
    @WithMockUser
    public void testGet() throws Exception {
        mockMvc.perform(get("/user/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/edit/index"));
    }

    @Test
    @WithMockUser
    public void testGetEmail() throws Exception {
        mockMvc.perform(get("/user/edit/email"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/edit/email/index"));
    }

    @Test
    @WithMockUser
    public void testPostEmail() throws Exception {
        final var form = ExampleEditEmailForm.gen();
        mockMvc.perform(post("/user/edit/email")
                        .flashAttr("editEmailForm", form)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/user/edit/email/send"));
    }

    @ParameterizedTest
    @WithMockUser
    @MethodSource("validationTestData")
    public void testValidation(final EditEmailForm form) throws Exception {
        this.mockMvc.perform(post("/user/edit/email")
                        .flashAttr("editEmailForm", form)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("editEmailForm"))
                .andExpect(model().attribute("editEmailForm", form))
                .andExpect(view().name("user/edit/email/index"));
    }

    private static Stream<Object> validationTestData() {
        final var stream = Stream.builder();
        stream.add(new EditEmailForm("", ""));
        stream.add(new EditEmailForm("", "password"));
        stream.add(new EditEmailForm("example@example.com", ""));
        return stream.build();
    }

    @Test
    @WithMockUser
    public void testSendEmail() throws Exception {
        final var email = ExampleEditEmailForm.gen().getNewEmail();
        this.mockMvc.perform(get("/user/edit/email/send")
                        .flashAttr("email", email)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("email"))
                .andExpect(model().attribute("email", email))
                .andExpect(view().name("user/edit/email/send"));
    }

    @Test
    @WithMockUser
    public void testVerifyEmail() throws Exception {
        final var uuid = ExampleNewEmailVerificartionToken.gen().getUuid().toString();
        this.mockMvc.perform(get("/user/edit/email/v/" + uuid))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/edit/email/complate"));
    }

    @Test
    @WithMockUser
    public void testComplate() throws Exception {
        this.mockMvc.perform(get("/user/edit/email/complate"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/edit/email/complate"));
    }
}
