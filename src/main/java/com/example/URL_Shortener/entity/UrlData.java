package com.example.URL_Shortener.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UrlData {
    private String longUrl;
    private LocalDateTime expiryDate;
    private int clickCount;

    public UrlData(){}

    public UrlData(String url, LocalDateTime expiryDate) {
        this.longUrl = url;
        this.expiryDate = expiryDate;
        this.clickCount = 0;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String url) {
        this.longUrl = url;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    @Override
    public String toString() {
        return "{"+this.longUrl+","+this.expiryDate+","+this.clickCount+"}";
    }
}
