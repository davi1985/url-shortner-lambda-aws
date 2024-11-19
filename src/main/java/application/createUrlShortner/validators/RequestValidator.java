package application.createUrlShortner.validators;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class RequestValidator {

    public static Map<String, String> parseRequestBody(String body, ObjectMapper objectMapper) {
        try {
            return objectMapper.readValue(body, Map.class);
        } catch (Exception exception) {
            throw new RuntimeException("Error parsing JSON body: " + exception.getMessage(), exception);
        }
    }

    public static long parseExpirationTime(String expirationTime) {
        try {
            return Long.parseLong(expirationTime);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid expiration time format", e);
        }
    }
}