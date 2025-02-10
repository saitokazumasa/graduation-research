package com.tabisketch.bean.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class ExampleEmailVerificationToken {
    private ExampleEmailVerificationToken() {
    }

    public static EmailVerificationToken gen() {
        return new EmailVerificationToken(
                UUID.fromString("bd725533-53a3-4a2d-9289-7fcbc7250d82"),
                1,
                LocalDateTime.now().minusMinutes(5)
        );
    }
}
