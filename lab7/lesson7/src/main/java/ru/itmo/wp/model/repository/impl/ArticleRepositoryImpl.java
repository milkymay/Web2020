package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.ArticleRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<Article> findAll() {
        return super.findAll("SELECT * FROM Article ORDER BY id DESC");
    }

    private void idStatementSetter(Article article, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, article.getId());
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
                default:
                    // No operations.
            }
        }
        return article;
    }
}
