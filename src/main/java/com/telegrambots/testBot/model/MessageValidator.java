package com.telegrambots.testBot.model;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MessageValidator {

    public Map<String, String> convertToMap(List<String> order) {
        String separator = ":";
        HashMap<String, String> orderMap = new HashMap<>();
        order.forEach((String item) -> {
            int position = item.indexOf(separator);
            if (position == -1) {
                return;
            }
            String key = item.substring(0, position);
            String val = item.substring(position + separator.length());
            orderMap.put(key, val);
        });
        return orderMap;
    }

    public boolean validateOrder(Map<String, String> orderMap) {
        return orderMap.get("Фамилия") == null
                || orderMap.get("Имя") == null
                || orderMap.get("Телефон") == null
                || orderMap.get("Адрес") == null
                || orderMap.get("Работы") == null;
    }
}
