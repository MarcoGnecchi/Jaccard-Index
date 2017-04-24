package com.cisco;

import java.net.URL;

public class TextFetchingException extends Throwable {
    public TextFetchingException(URL url) {
        super(url.toString());
    }
}
