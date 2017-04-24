package com.cisco;

import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class TextDownloaderService {

    @Autowired
    private RestTemplate restTemplate;

    private Logger log = LoggerFactory.getLogger(TextDownloaderService.class);

    public String fetchText(URL url) throws TextFetchingException {


        Document doc;
        ResponseEntity<String> httpResult = null;
        try {
            httpResult = restTemplate.getForEntity(url.toString(), String.class);
            doc = Jsoup.parse(httpResult.getBody());
            return doc.body().text();
        } catch (RestClientException | IllegalStateException | IllegalArgumentException e) {
            log.error("Error while retrieving url: {} \n {}",url.toString(),httpResult, e);
            throw new TextFetchingException(url);
        }
    }
}
