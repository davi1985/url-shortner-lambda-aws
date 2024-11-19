package application.createUrlShortner.services;

import application.createUrlShortner.models.UrlData;

public interface S3StorageService {
    void saveUrlData(String shortUrlCode, UrlData urlData);
}