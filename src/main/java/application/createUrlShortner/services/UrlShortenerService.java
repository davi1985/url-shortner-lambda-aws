package application.createUrlShortner.services;

import java.util.Map;
import java.util.UUID;

public interface UrlShortenerService {
    int SUBSTRING_START_INDEX = 0;
    int SUBSTRING_END_INDEX = 8;

    Map<String, String> handleShorteningRequest(Map<String, Object> input);

    static String generateUrlCode() {
        return UUID.randomUUID().toString().substring(SUBSTRING_START_INDEX, SUBSTRING_END_INDEX);
    }
}
