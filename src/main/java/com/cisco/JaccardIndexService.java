package com.cisco;

import org.springframework.stereotype.Component;

@Component
public class JaccardIndexService {

    public int perform(String text1, String text2) {
        if (text1.equals(text2)){
            return 1;
        } else {
            return 0;
        }
    }

}
