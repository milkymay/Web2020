package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.PostForm;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PostFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PostForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Pattern tagPattern = Pattern.compile("[a-z]*");
        if (!errors.hasErrors()) {
                PostForm postForm = (PostForm) target;
                Arrays.stream(postForm.getTags().split("\\s+")).forEach(el -> {
                    Matcher matcher = tagPattern.matcher(el);
                    if (!matcher.matches()) {
                        errors.rejectValue("tags", "tags.only-latin-lowercase",
                                "Tags should contain latin lowercase letters only and be divided by whitespace ");
                    }
                });
        }
    }
}
