package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.NoticeCredentials;
import ru.itmo.wp.service.NoticeService;

@Component
public class NoticeCredentialsValidator implements Validator {
    private final NoticeService noticeService;

    public NoticeCredentialsValidator(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NoticeCredentials.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            NoticeCredentials noticeForm = (NoticeCredentials) target;
            if (noticeForm.getContent() != null && noticeForm.getContent().length() < 2) {
                errors.rejectValue("content", "content.invalid-content", "content length must be at least 2");
            } else if (noticeForm.getContent() != null && noticeForm.getContent().length() > 30) {
                errors.rejectValue("content", "content.invalid-content", "content length can't be more than 30");
            }
        }
    }
}
