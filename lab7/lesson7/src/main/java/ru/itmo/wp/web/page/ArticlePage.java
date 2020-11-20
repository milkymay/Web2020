package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ArticlePage extends Page {
    private ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (getUser() == null) {
            setMessage("You need to be authorized to see this page.");
            throw new RedirectException("/index");
        }
    }

    public void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
    }

    public void after(HttpServletRequest request, Map<String, Object> view) {
        request.removeAttribute("message");
        super.after(request, view);
    }

    private void create(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        String title = request.getParameter("title");
        String text = request.getParameter("text");

        articleService.validateArticle(title, text);
        User author = getUser();
        Article article = new Article();
        article.setTitle(title);
        article.setText(text);
        article.setUserId(author.getId());
        articleService.create(article);
        throw new RedirectException("/article");
    }

    @Json
    private void findAll(Map<String, Object> view) {
        view.put("articles", articleService.findAll());
    }

}