package com.tabisketch.bean.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class ExampleResetPasswordToken {
    private ExampleResetPasswordToken() {
    }

    public static ResetPasswordToken gen() {
        return new ResetPasswordToken(
                UUID.fromString("bd725533-53a3-4a2d-9289-7fcbc7250d82"),
                1,
                LocalDateTime.now().minusMinutes(5)
        );
    }
}
