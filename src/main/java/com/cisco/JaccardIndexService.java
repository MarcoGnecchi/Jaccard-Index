package com.cisco;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class JaccardIndexService {

    public float perform(String text1, String text2) {

        text1 = text1.toLowerCase();
        text2 = text2.toLowerCase();

        Pattern pattern = Pattern.compile("(\\W+)");
        Set<String> collection1 = pattern.splitAsStream(text1).collect(Collectors.toSet());
        Set<String> collection2 = pattern.splitAsStream(text2).collect(Collectors.toSet());

        Set<String> union = new HashSet<>();
        union.addAll(collection1);
        union.addAll(collection2);

        Set<String> intersection = new HashSet<>();
        intersection.addAll(collection1);
        intersection.retainAll(collection2);

        float jaccardIndex = (float)intersection.size() / (float) union.size();

        return round(jaccardIndex, 3);
    }

    private float round(float value, int decimalPlace){
        return BigDecimal.valueOf(value).setScale(decimalPlace,BigDecimal.ROUND_HALF_UP).floatValue();
    }

}
