package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Page {
    UserService userService = new UserService();
    TalkService talkService = new TalkService();
    HttpServletRequest request;

    private void action() {
        // No operation
    }

    void before(HttpServletRequest request, Map<String, Object> view) {
        this.request = request;
        putUser(request, view);
        getMessage(view);
    }

    void after(HttpServletRequest request, Map<String, Object> view) {
        view.put("userCount", userService.findCount());
    }

    private void putUser(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            view.put("user", user);
        }
    }

    void getMessage(Map<String, Object> view) {
        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }

    User getUser() {
        return (User) request.getSession().getAttribute("user");
    }

    void setMessage(String message) {
        request.getSession().setAttribute("message", message);
    }

    void setUser(User user) {
        request.getSession().setAttribute("user", user);
    }

}
