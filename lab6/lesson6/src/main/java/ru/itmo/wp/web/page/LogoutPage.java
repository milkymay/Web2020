package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.domain.event.Event;
import ru.itmo.wp.model.domain.event.EventType;
import ru.itmo.wp.model.service.EventService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class LogoutPage extends Page {
    private final EventService eventService = new EventService();
    private void action(HttpServletRequest request, Map<String, Object> view) {
        User user = getUser();
        if (user != null) {
            request.getSession().setAttribute("message", "Good bye. Hope to see you soon!");
            Event event = new Event();
            event.setType(EventType.LOGOUT);
            event.setUserId(getUser().getId());
            eventService.save(event);
            request.getSession().removeAttribute("user");
        }
        throw new RedirectException("/index");
    }
}
