package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class UsersPage extends Page {
    private final UserService userService = new UserService();

    protected void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    @Json
    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    @Json
    private void findLoginByUserId(HttpServletRequest request, Map<String, Object> view) {
        view.put("author", userService.find(Long.parseLong(request.getParameter("userId"))));
    }

    @Json
    private void changeStatus(HttpServletRequest request, Map<String, Object> view) {
        User curUser = getUpdatedUser(view);
        if (curUser == null) {
            request.getSession().setAttribute("message", "You need to be authorized.");
            throw new RedirectException("/index");
        }
        if (!curUser.isAdmin()) {
            request.getSession().setAttribute("message", "You must be admin to change the user parameter");
            throw new RedirectException("/index");
        }
        long id = Long.parseLong(request.getParameter("userId"));
        boolean newStatus = !Boolean.parseBoolean(request.getParameter("isAdmin"));
        User user = userService.changeStatus(id, newStatus);
        view.put("changedUser", user);
        if (id == curUser.getId()) {
            throw new RedirectException("/users");
        }
    }
}