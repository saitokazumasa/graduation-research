package com.tabisketch.service.implement;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerationConfig;
import com.google.cloud.vertexai.api.GoogleSearchRetrieval;
import com.google.cloud.vertexai.api.Tool;
import com.google.cloud.vertexai.generativeai.ContentMaker;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.tabisketch.service.ICallGeminiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class CallGeminiService implements ICallGeminiService {
    private final String projectId;
    private final String location;
    private final String modelName;

    public CallGeminiService(
            final @Value("${GEMINI_PROJECT_ID}") String projectId,
            final @Value("${GEMINI_LOCATION}") String location,
            final @Value("${GEMINI_MODEL_NAME}") String modelName
    ) {
        this.projectId = projectId;
        this.location = location;
        this.modelName = modelName;
    }

    public String execute(final String prompt) throws IOException {
        try (final var vertexAI = new VertexAI(this.projectId, this.location)) {
            final var tools = new ArrayList<Tool>();
            tools.add(Tool.newBuilder()
                    .setGoogleSearchRetrieval(GoogleSearchRetrieval.newBuilder())
                    .build()
            );
            final var generationConfig = GenerationConfig.newBuilder()
                    .setTemperature(1.0f)
                    .setTopP(0.95f)
                    .setMaxOutputTokens(8192)
                    .build();
            final var model = new GenerativeModel.Builder()
                    .setModelName(this.modelName)
                    .setVertexAi(vertexAI)
                    .setGenerationConfig(generationConfig)
                    .setTools(tools)
                    .build();
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
