package com.cisco;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class JaccardIndexController {

    @Autowired
    private JaccardIndexService jaccardIndexService;

    @RequestMapping(path = "/")
    public void performJaccardIndex(@RequestParam(value = "url1") String url1,
            @RequestParam(value = "url2") String url2) throws MalformedURLException {

        // Validating provided urls
        URL valid_url1 = new URL(url1);
        URL valid_url2 = new URL(url2);

        jaccardIndexService.perform(valid_url1, valid_url2);
    }

    @ExceptionHandler(MalformedURLException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void handleMalformedUrl() {
    }
}
