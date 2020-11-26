package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;

public class ArticleService {
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public void create(Article article) {
        articleRepository.save(article);
    }

    public void validateArticle(String title, String text) throws ValidationException {
        if (Strings.isNullOrEmpty(title)) {
            throw new ValidationException("Title is required");
        }
        if (Strings.isNullOrEmpty(text)) {
            throw new ValidationException("Article can't be empty");
        }
        if ((30 <= title.length()) || (title.length() <= 5)) {
            throw new ValidationException("Title can't be shorter than 5 or longer than 30 symbols");
        }
        if ((400 <= text.length()) || (text.length() <= 10)) {
            throw new ValidationException("Text can't be shorter than 10 or longer than 400 symbols");
        }
    }

    public Article find(long id) {
        return articleRepository.find(id);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public List<Article> findAllShown() {
        return articleRepository.findAllShown();
    }

    public List<Article> findByUserId(long id) {
        return articleRepository.findByUserId(id);
    }

    public Article changeStatus(long id, boolean newStatus) {
        return articleRepository.changeStatus(id, newStatus);
    }
}
