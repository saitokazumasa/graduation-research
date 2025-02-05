package com.tabisketch.optimize_route.service.implement;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.*;
import com.google.cloud.vertexai.generativeai.ContentMaker;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.tabisketch.optimize_route.service.ICallGeminiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class CallGeminiService implements ICallGeminiService {
    private final String projectId;

    public CallGeminiService(final @Value("${GOOGLE_PROJECT_ID}") String projectId) {
        this.projectId = projectId;
    }

    @Override
    public String execute(final String systemInstruction, final String prompt) throws IOException {
        try (final var vertexAI = new VertexAI(this.projectId, "asia-northeast1")) {

            final var generationConfig = GenerationConfig.newBuilder()
                    .setTemperature(0.0f)
                    .setTopP(0.95f)
                    .setMaxOutputTokens(8192)
                    .build();

            final var tools = new ArrayList<Tool>();
            tools.add(Tool.newBuilder()
                    .setGoogleSearchRetrieval(GoogleSearchRetrieval.newBuilder())
                    .build()
            );

            final var model = new GenerativeModel.Builder()
                    .setModelName("gemini-1.5-flash-002")
                    .setVertexAi(vertexAI)
                    .setGenerationConfig(generationConfig)
                    .setTools(tools)
                    .build()
                    .withSystemInstruction(
                            ContentMaker.fromString(systemInstruction)
                    );

            final var content = ContentMaker.fromString(prompt);
            final var response = model.generateContent(content);
            return response
                    .getCandidates(0)
                    .getContent()
                    .getParts(0)
                    .getText();
        }
    }
}
