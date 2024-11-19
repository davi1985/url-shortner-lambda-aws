package application.createUrlShortner.services.impl;

import application.createUrlShortner.models.UrlData;
import application.createUrlShortner.services.S3StorageService;
import application.createUrlShortner.services.UrlShortenerService;
import application.createUrlShortner.validators.RequestValidator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class UrlShortenerServiceImpl implements UrlShortenerService {

    private final S3StorageService s3StorageService;
    private final ObjectMapper objectMapper;

    public UrlShortenerServiceImpl(S3StorageService s3StorageService, ObjectMapper objectMapper) {
        this.s3StorageService = s3StorageService;
        this.objectMapper = objectMapper;
    }

    public Map<String, String> handleShorteningRequest(Map<String, Object> input) {
        String body = input.get("body").toString();
        Map<String, String> bodyMap = RequestValidator.parseRequestBody(body, objectMapper);
        String originalUrl = bodyMap.get("originalUrl");
        long expirationTimeInSeconds = RequestValidator.parseExpirationTime(bodyMap.get("expirationTime"));

        String shortUrlCode = UrlShortenerService.generateUrlCode();
        UrlData urlData = new UrlData(originalUrl, expirationTimeInSeconds);

        s3StorageService.saveUrlData(shortUrlCode, urlData);

        Map<String, String> response = new HashMap<>();
        response.put("code", shortUrlCode);

        return response;
    }
}