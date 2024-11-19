package application.createUrlShortner.models;

public record UrlData(String originalUrl, long expirationTime) {
}
