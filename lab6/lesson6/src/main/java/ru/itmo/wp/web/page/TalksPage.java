package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.domain.talk.Talk;
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
//        throw new RedirectException("/talks");
    }

    private void send() {
        if (getUser() == null) {
            throw new RedirectException("/index");
        }
        String receiver = request.getParameter("ReceiverLogin");
        String message = request.getParameter("message");
        Talk talk = new Talk();
        talk.setSourceUserId(getUser().getId());
        talk.setTargetUserId(userService.findByLoginOrEmail(request.getParameter("ReceiverLogin")).getId());
        talk.setText(request.getParameter("message"));
        talkService.save(talk);
        throw new RedirectException("/talks");
    }

    public void after(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
        view.put("talks", talkService.findAll());
        super.after(request, view);
    }
}
