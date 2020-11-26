package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.repository.ArticleRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

public class ArticleRepositoryImpl  extends AbstractRepository<Article> implements ArticleRepository {

    private void saveResultSetter(Article article, ResultSet generatedKeys) throws SQLException {
        article.setId(generatedKeys.getLong(1));
        article.setCreationTime(find(article.getId()).getCreationTime());
    }

    private void saveStatementSetter(Article article, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, article.getUserId());
        preparedStatement.setString(2, article.getTitle());
        preparedStatement.setString(3, article.getText());
    }

    @Override
    public void save(Article article) {
        String saveSQLRequest = generateInsertSQL(article, " (`userId`, `title`, `text`, `creationTime`) VALUES (?, ?, ?, NOW())");
        super.save(article, saveSQLRequest, this::saveStatementSetter, this::saveResultSetter);
    }

    @Override
    public Article find(long id) {
        Article article = new Article();
        article.setId(id);
        return super.findBy(article,"SELECT * FROM Article WHERE id=?", this::idStatementSetter);
    }

    private void idStatementSetter(Article article, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, article.getId());
    }

    @Override
    public List<Article> findAll() {
        return super.findAll("SELECT * FROM Article ORDER BY id DESC");
    }

    private void shownStatementSetter(Article article, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setBoolean(1, article.getHidden());
    }

    @Override
    public List<Article> findAllShown() {
        Article article = new Article();
        article.setHidden(false);
        return super.filter(article, this::shownStatementSetter, generateSelectSQL(article, " hidden=?"));
    }

    private void userIdStatementSetter(Article article, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, article.getUserId());
    }

    @Override
    public List<Article> findByUserId(long userId) {
        Article article = new Article();
        article.setUserId(userId);
        return super.filter(article, this::userIdStatementSetter, generateSelectSQL(article, " userId=?"));
    }

    private void hiddenUpdateStatementSetter(Article article, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setBoolean(1, article.getHidden());
        preparedStatement.setLong(2, article.getId());
    }

    @Override
    public Article changeStatus(long id, boolean newStatus) {
        Article article = new Article();
        article.setHidden(newStatus);
        article.setId(id);
        super.update(article, this::hiddenUpdateStatementSetter, "UPDATE `Article` SET hidden=? WHERE id=?");
        return article;
    }

    @Override
    protected Article toEntity(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Article article = new Article();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    article.setId(resultSet.getLong(i));
                    break;
                case "userId":
                    article.setUserId(resultSet.getLong(i));
                    break;
                case "creationTime":
                    article.setCreationTime(resultSet.getTimestamp(i));
                    break;
                case "text":
                    article.setText(resultSet.getString(i));
                    break;
                case "title":
                    article.setTitle(resultSet.getString(i));
                    break;
                case "hidden":
                    article.setHidden(resultSet.getBoolean(i));
                    break;
                default:
                    // No operations.
            }
        }
        return article;
    }
}
