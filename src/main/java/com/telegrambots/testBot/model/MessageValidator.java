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
        String delimiter = ":";
        HashMap<String, String> orderMap = new HashMap<>();
        order.forEach((String item) -> {
            int position = item.indexOf(delimiter);
            if (position == -1) {
                return;
            }
            String key = item.substring(0, position);
            String val = item.substring(position + delimiter.length());
            orderMap.put(key, val);
        });
        return orderMap;
    }

    public  List<String> validateOrderDetails(User user){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator() ;
        Set<ConstraintViolation<User>> validate = validator.validate(user);
        List<String> errors = new ArrayList<>();
        List<String> validationPassed = new ArrayList<>();
       if (validate.size() > 0){
           for (ConstraintViolation<User> violation:validate){
               String message = violation.getMessage();
               errors.add(message);
           }
           return errors;
       }
        validationPassed.add("All validation passed");
        return validationPassed;
    }
}
