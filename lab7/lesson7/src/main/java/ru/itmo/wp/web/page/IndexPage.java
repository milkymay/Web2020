package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;
import ru.itmo.wp.model.service.ArticleService;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

/** @noinspection unused*/
public class IndexPage extends Page {
    private final ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        putMessage(request, view);
    }

    @Override
    void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        view.put("articles", articleService.findAllShown());
    }


    private void putMessage(HttpServletRequest request, Map<String, Object> view) {
        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }
}
