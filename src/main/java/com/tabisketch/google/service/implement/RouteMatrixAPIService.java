package com.tabisketch.google.service.implement;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tabisketch.google.bean.entity.RouteMatrixAPIRequest;
import com.tabisketch.google.bean.entity.RouteMatrixAPIResponse;
import com.tabisketch.google.service.IRouteMatrixAPIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class RouteMatrixAPIService implements IRouteMatrixAPIService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String apiUrl;
    private final String apiKey;

    public RouteMatrixAPIService(final @Value("${GOOGLE_MAPS_PLATFORM_API_KEY}") String apiKey) {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.apiUrl = "https://routes.googleapis.com/distanceMatrix/v2:computeRouteMatrix";
        this.apiKey = apiKey;
    }

    @Override
    public List<RouteMatrixAPIResponse> execute(final RouteMatrixAPIRequest routeMatrixAPIRequest) throws IOException {
        final var headers = generateHeaders();
        final var body = this.objectMapper.writeValueAsString(routeMatrixAPIRequest);

        final var request = new HttpEntity<>(body, headers);
        final var response = restTemplate.postForEntity(apiUrl, request, String.class);

        return this.objectMapper.readValue(response.getBody(), new TypeReference<>() {
        });
    }

    private HttpHeaders generateHeaders() {
        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Goog-Api-Key", this.apiKey);
        headers.set("X-Goog-FieldMask", "originIndex,destinationIndex,duration,distanceMeters,status,condition");
        return headers;
    }
}
