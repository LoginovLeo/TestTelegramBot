package com.telegrambots.testBot.model;

import com.telegrambots.testBot.entity.User;
import org.springframework.stereotype.Component;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

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

    public  List<String> validateOrderDetails(User user){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator() ;
        Set<ConstraintViolation<User>> validate = validator.validate(user);
        List<String> errors = new ArrayList<>();
        List<String> good = new ArrayList<>();
       if (validate.size() > 0){
           for (ConstraintViolation<User> violation:validate){
               String message = violation.getMessage();
               errors.add(message);
           }
           return errors;
       }
        good.add("All good");
        return good;
    }
}
