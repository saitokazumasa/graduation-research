package com.tabisketch.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class CallGeminiServiceTest {
    @Autowired
    private ICallGeminiService callGeminiService;

    @Test
    public void testExecute() throws IOException {
        final var prompt = "今日の盛岡市の天気を教えて";
        final var result = this.callGeminiService.execute(prompt);
        System.out.println(result);
    }
}
