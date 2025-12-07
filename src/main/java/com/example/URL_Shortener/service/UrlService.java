package com.example.URL_Shortener.service;

import org.springframework.stereotype.Service;

@Service
public interface UrlService {
    String createShortUrl(String longUrl, String customCode, int expiryMinutes);
    String getOriginalUrl(String url);
    String displayAllUrl();
    String deleteUrl(String url);
    void deleteAllUrl();
}
