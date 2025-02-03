package com.tabisketch.service;

import java.io.IOException;

public interface ICallGeminiService {
    String execute(final String prompt) throws IOException;
}
