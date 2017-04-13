package com.cisco;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class JaccardIndexController {

    @Autowired
    private JaccardIndexService jaccardIndexService;

    @Autowired
    private TextDownloaderService textDownloaderService;

    @RequestMapping(path = "/")
    public void performJaccardIndex(@RequestParam(value = "url1") String url1,
            @RequestParam(value = "url2") String url2) throws MalformedURLException, URISyntaxException {

        // Validating provided urls
        URL valid_url1 = new URL(url1);
        URL valid_url2 = new URL(url2);

        String text1 = textDownloaderService.fetchText(valid_url1);
        String text2 = textDownloaderService.fetchText(valid_url2);


        jaccardIndexService.perform(text1, text2);
    }

    @ExceptionHandler(MalformedURLException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void handleMalformedUrl() {
    }
}
