package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.CommentForm;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.service.CommentService;
import ru.itmo.wp.service.UserService;

@Component
public class CommentFormValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return CommentForm.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            CommentForm commentForm = (CommentForm) target;
            if (commentForm.getText().length() == 0) {
                errors.rejectValue("text", "text.cannot-be-empty", "Comment can't be empty");
            } else if (commentForm.getText().length() > 1000) {
                errors.rejectValue("text", "text.too-long", "Comment can't be more than 1000");
            }
        }
    }
}
