package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyArticlesPage extends Page {
    private final ArticleService articleService = new ArticleService();

    private void action() {
        User user = getUser();
        if (user == null) {
            setMessage("You must be authorized to see this page");
            throw new RedirectException("/index");
        }
    }

    public void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        User user = getUser();
        if (user != null) {
            view.put("articles", articleService.findByUserId(user.getId()));
        }
    }

    @Json
    private void findByUserId(Map<String, Object> view) {
        view.put("articles", articleService.findByUserId(getUser().getId()));
    }

}
