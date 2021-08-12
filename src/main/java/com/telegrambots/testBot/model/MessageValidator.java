package com.telegrambots.testBot.model;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MessageValidator {

    public  Map<String, String> convertToMap(List<String> order) {
        String separator = ":";
        HashMap<String, String> orderMap = new HashMap<>();
        order.forEach((String item) -> {
            int pos = item.indexOf(separator);
            if (pos == -1) {
                return;
            }
            String key = item.substring(0, pos);
            String val = item.substring(pos + separator.length());
            orderMap.put(key, val);
        });
        return orderMap;
    }
}
