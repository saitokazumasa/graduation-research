package com.tabisketch.optimize_route.service;

import java.io.IOException;

public interface ICallGeminiService {
    String execute(final String systemInstruction, final String prompt) throws IOException;
}
