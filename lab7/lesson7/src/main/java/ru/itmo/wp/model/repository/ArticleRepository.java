package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Article;

import java.util.List;

public interface ArticleRepository {
    void save(Article article);
    Article find(long id);
    List<Article> findAll();
    List<Article> findAllShown();
    List<Article> findByUserId(long id);
    Article changeStatus(long id, boolean hide);
}
