package com.library.service.valid;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
@Data
@Slf4j
public class ValidationImpl implements Validation {

    private final Validator validation;

    public Boolean validNewOperand(Object entity){
        Set<ConstraintViolation<Object>> violations = validation.validate(entity);
        if(!violations.isEmpty()) {
            log.info(entity.toString()+" is not valid ");
            violations.forEach(error->log.error("Error : "+error.getMessage()));
            return false;
        }
        return true;
    }
}
