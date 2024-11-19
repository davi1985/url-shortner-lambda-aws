package application.createUrlShortner.services.impl;

import application.createUrlShortner.models.UrlData;
import application.createUrlShortner.services.S3StorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

public class S3StorageServiceImpl implements S3StorageService {

    private final S3Client s3Client = S3Client.builder().build();

    public void saveUrlData(String shortUrlCode, UrlData urlData) {
        try {
            String urlDataJson = new ObjectMapper().writeValueAsString(urlData);

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket("url-shortener-lambda-dev")
                    .key(shortUrlCode + ".json")
                    .build();

            s3Client.putObject(request, RequestBody.fromString(urlDataJson));
        } catch (Exception exception) {
            throw new RuntimeException("Error saving data to S3: " + exception.getMessage(), exception);
        }
    }
}