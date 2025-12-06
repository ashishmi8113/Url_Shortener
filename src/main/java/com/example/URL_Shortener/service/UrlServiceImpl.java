package com.example.URL_Shortener.service;

import com.example.URL_Shortener.entity.UrlData;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UrlServiceImpl implements UrlService {

    private Map<String, UrlData> map=new ConcurrentHashMap<>();

    private String generateCode(){
        String s="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<7;i++){
            sb.append(s.charAt(random.nextInt(s.length())));
        }
        return sb.toString();
    }

    private boolean isValidUrl(String url){
        if(url.startsWith("http://") || url.startsWith("https://")){
            return true;
        }
        return false;
    }

    @Override
    public String createShortUrl(String longUrl, String customCode, int expiryMinutes) {
        if (!isValidUrl(longUrl)) {
            throw new RuntimeException("Invalid URL. Must start with http:// or https://");
        }
        String shortUrl="";
        if(customCode!=null && !customCode.equals("")){
            shortUrl=customCode;
        }
        else{
            shortUrl=generateCode();
        }

        // Collision Check, if new generated url already exists in MAP, then generate again, until we get the unique one.
        while(map.containsKey(shortUrl)){
            shortUrl=generateCode();
        }

        LocalDateTime expiryTime=LocalDateTime.now().plusMinutes(expiryMinutes);
        map.put(shortUrl,new UrlData(longUrl,expiryTime));
        return "http://localhost:8080/url/"+ shortUrl;
    }
    @Override
    public String getOriginalUrl(String url) {
        UrlData urlData=map.get(url);
        if(urlData==null){
            return null;
        }
        if(LocalDateTime.now().isAfter(urlData.getExpiryDate())){
            map.remove(url);
            return null;
        }
        int num=urlData.getClickCount();
        urlData.setClickCount(++num);

        return urlData.getLongUrl();
    }
}
