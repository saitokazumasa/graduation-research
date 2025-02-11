package com.tabisketch.bean.entity;

public class ExampleUser {
    private ExampleUser() {
    }

    public static User gen() {
        return new User(
                1,
                "example@example.com",
                "$2a$10$FFbAunp0hfeWTCune.XqwO/P/61fqWlbruV/8wqzrhM3Pw0VuXxpa",
                true
        );
    }
}
