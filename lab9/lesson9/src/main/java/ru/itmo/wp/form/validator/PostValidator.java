package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.service.UserService;

@Component
public class PostValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        ///
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        ///
    }
}
