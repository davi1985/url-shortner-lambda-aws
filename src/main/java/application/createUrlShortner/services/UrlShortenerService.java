package application.createUrlShortner.services;

import java.util.Map;

public interface UrlShortenerService {
    Map<String, String> handleShorteningRequest(Map<String, Object> input);
}
