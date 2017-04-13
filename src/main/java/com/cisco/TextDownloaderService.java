package com.cisco;

import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TextDownloaderService {

    @Autowired
    private RestTemplate restTemplate;

    public String fetchText(URL url) {

        String httpResult = restTemplate.getForObject(url.toString(), String.class);
        Document doc = Jsoup.parse(httpResult);
        return doc.body().text();
    }
}
