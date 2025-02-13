package com.tabisketch.bean.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class ExampleNewEmailVerificartionToken {
    private ExampleNewEmailVerificartionToken() {
    }

    public static NewEmailVerificationToken gen() {
        return new NewEmailVerificationToken(
                UUID.fromString("bd725533-53a3-4a2d-9289-7fcbc7250d82"),
                "example@example.com",
                1,
                LocalDateTime.now()
        );
    }
}
