package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.domain.talk.Talk;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class TalksPage extends Page {
    private final TalkService talkService = new TalkService();
    private final UserService userService = new UserService();

    private void action(Map<String, Object> view) {
        User user = getUser();
        if (user == null) {
            setMessage("You must be authorized to see this page");
            throw new RedirectException("/index");
        }
    }

    private void send() throws ValidationException {
        if (getUser() == null) {
            throw new RedirectException("/index");
        }
        String receiver = request.getParameter("ReceiverLogin");
        String text = request.getParameter("text");
        talkService.validate(receiver, text, getUser().getId());
        Talk talk = new Talk();
        talk.setSourceUserId(getUser().getId());
        talk.setTargetUserId(userService.findByLoginOrEmail(request.getParameter("ReceiverLogin")).getId());
        talk.setText(request.getParameter("text"));
        talkService.save(talk);
        throw new RedirectException("/talks");
    }

    public void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        User user = getUser();
        if (user != null) {
            view.put("talks", talkService.findByTargetOrSourceUserId(getUser()));
        }
    }

    public void after(HttpServletRequest request, Map<String, Object> view) {
        request.removeAttribute("message");
        view.remove("message");
        view.put("users", userService.findAll());
        super.after(request, view);
    }
}
