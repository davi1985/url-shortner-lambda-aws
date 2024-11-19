package application.createUrlShortner;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main implements RequestHandler<Map<String, Object>, Map<String, String>> {


    public static final int SUBSTRING_START_INDEX = 0;
    public static final int SUBSTRING_END_INDEX = 8;

    private final S3Client s3Client = S3Client.builder().build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, String> handleRequest(Map<String, Object> input, Context context) {
        String body = input.get("body").toString();

        Map<String, String> bodyMap;

        try {
            bodyMap = objectMapper.readValue(body, Map.class);
        } catch (Exception exception) {
            throw new RuntimeException("Error parsing JSON body: " + exception.getMessage(), exception);
        }

        String originalUrl = bodyMap.get("originalUrl");
        String expirationTime = bodyMap.get("expirationTime");
        long expirationTimeInSeconds = Long.parseLong(expirationTime);

        String shortUrlCode = generateUrlCode();

        UrlData urlData = new UrlData(originalUrl, expirationTimeInSeconds);

        try {
            String urlDataJson = objectMapper.writeValueAsString(urlData);

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket("url-shortener-lambda-dev")
                    .key(shortUrlCode + ".json")
                    .build();

            s3Client.putObject(request, RequestBody.fromString(urlDataJson));
        } catch (Exception exception) {
            throw new RuntimeException("Error saving data to S3: " + exception.getMessage(), exception);
        }

        Map<String, String> response = new HashMap<>();

        response.put("code", shortUrlCode);

        return response;
    }

    private static String generateUrlCode() {
        return UUID.randomUUID().toString().substring(SUBSTRING_START_INDEX, SUBSTRING_END_INDEX);
    }
}