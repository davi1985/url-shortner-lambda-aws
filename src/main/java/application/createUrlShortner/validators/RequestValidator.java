package application.createUrlShortner.validators;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class RequestValidator {

    public static Map<String, String> parseRequestBody(String body, ObjectMapper objectMapper) {
        try {
            return objectMapper.readValue(body, Map.class);
        } catch (Exception exception) {
            log.error("Error parsing request body: {}", exception.getMessage());
            throw new RuntimeException("Error parsing JSON body: " + exception.getMessage(), exception);
        }
    }

    public static long parseExpirationTime(String expirationTime) {
        try {
            return Long.parseLong(expirationTime);
        } catch (NumberFormatException e) {
            log.error("Error parsing expiration time: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid expiration time format", e);
        }
    }
}