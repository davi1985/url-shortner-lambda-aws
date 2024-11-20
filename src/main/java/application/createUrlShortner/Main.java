package application.createUrlShortner;

import application.createUrlShortner.services.UrlShortenerService;
import application.createUrlShortner.services.impl.S3StorageServiceImpl;
import application.createUrlShortner.services.impl.UrlShortenerServiceImpl;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class Main implements RequestHandler<Map<String, Object>, Map<String, String>> {

    private final UrlShortenerService urlShortenerService;

    public Main(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    public Main() {
        this(new UrlShortenerServiceImpl(
                new S3StorageServiceImpl(), new ObjectMapper()));
    }

    @Override
    public Map<String, String> handleRequest(Map<String, Object> input, Context context) {
        return urlShortenerService.handleShorteningRequest(input);
    }
}