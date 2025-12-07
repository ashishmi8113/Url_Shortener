package com.example.URL_Shortener.controller;

import com.example.URL_Shortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/url")
public class UrlController {
    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(
            @RequestParam String url,
            @RequestParam(required = false) String custom,
            @RequestParam(defaultValue = "10") int expiry) {

        String shortUrl = urlService.createShortUrl(url, custom, expiry);
        return ResponseEntity.status(HttpStatus.CREATED).body(shortUrl);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        String originalUrl = urlService.getOriginalUrl(code);
        if (originalUrl == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build();
    }

    @PostMapping("/getAllUrl")
    public ResponseEntity<String> getAllUrl() {
        return ResponseEntity.status(HttpStatus.OK).body(urlService.displayAllUrl());
    }

    @PostMapping("/delete/{url}")
    public ResponseEntity<String> deleteUrl(@PathVariable String url) {
        return ResponseEntity.status(HttpStatus.OK).body(urlService.deleteUrl(url));
    }

    @PostMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllUrl() {
        urlService.deleteAllUrl();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
