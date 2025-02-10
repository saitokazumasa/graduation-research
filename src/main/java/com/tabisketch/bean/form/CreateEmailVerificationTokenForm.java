package com.tabisketch.bean.form;

import com.tabisketch.bean.entity.EmailVerificationToken;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateEmailVerificationTokenForm {
    @Min(1)
    private int userId;

    public EmailVerificationToken toEmailVerificationToken() {
        return new EmailVerificationToken(
                UUID.randomUUID(),
                userId,
                LocalDateTime.now()
        );
    }
}
