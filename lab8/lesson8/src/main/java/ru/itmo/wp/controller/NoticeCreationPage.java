package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.form.NoticeCredentials;
import ru.itmo.wp.form.validator.NoticeCredentialsValidator;
import ru.itmo.wp.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class NoticeCreationPage extends Page {
    private final NoticeService noticeService;
    private final NoticeCredentialsValidator noticeCredentialsValidator;

    public NoticeCreationPage(NoticeService noticeService, NoticeCredentialsValidator noticeCredentialsValidator) {
        this.noticeService = noticeService;
        this.noticeCredentialsValidator = noticeCredentialsValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        if (binder.getTarget() == null) return;
        if (noticeCredentialsValidator.supports(binder.getTarget().getClass())) {
            binder.addValidators(noticeCredentialsValidator);
        }
    }

    @GetMapping("/noticeCreation")
    public String create(Model model) {
        model.addAttribute("noticeForm", new NoticeCredentials());
        return "NoticeCreationPage";
    }

    @PostMapping("/noticeCreation")
    public String create(@Valid @ModelAttribute("noticeForm") NoticeCredentials noticeForm,
                       BindingResult bindingResult,
                       HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "NoticeCreationPage";
        }

        noticeService.save(noticeForm.getContent());
        putMessage(httpSession, "Notice saved");
        return "redirect:";
    }
}
