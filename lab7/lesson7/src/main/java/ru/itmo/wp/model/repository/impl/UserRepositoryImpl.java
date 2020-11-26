package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepositoryImpl extends AbstractRepository<UserRepositoryImpl.UserAndPasswordSha> implements UserRepository {
    private final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    protected static class UserAndPasswordSha {
        private final User user;
        private final String passwordSha;

        private UserAndPasswordSha(User user, String passwordSha) {
            this.user = user;
            this.passwordSha = passwordSha;
        }
    }

    private void idStatementSetter(UserAndPasswordSha pair, PreparedStatement statement) throws SQLException {
        statement.setLong(1, pair.user.getId());
    }

    @Override
    public User find(long id) {
        UserAndPasswordSha pair = new UserAndPasswordSha(new User(), "");
        pair.user.setId(id);
        return unwrap(super.findBy(pair,"SELECT * FROM User WHERE id=?", this::idStatementSetter));
    }

    private void loginStatementSetter(UserAndPasswordSha pair, PreparedStatement statement) throws SQLException {
        statement.setString(1, pair.user.getLogin());
    }

    @Override
    public User findByLogin(String login) {
        UserAndPasswordSha pair = new UserAndPasswordSha(new User(), "");
        pair.user.setLogin(login);
        return unwrap(super.findBy(pair,"SELECT * FROM User WHERE login=?", this::loginStatementSetter));
    }

    private void loginOrEmailAndPasswordStatementSetter(UserAndPasswordSha pair, PreparedStatement statement) throws SQLException {
        statement.setString(1, pair.user.getLogin());
        statement.setString(2, pair.user.getLogin());
        statement.setString(3, pair.passwordSha);
    }

    @Override
    public User findByLoginOrEmailAndPasswordSha(String loginOrEmail, String passwordSha) {
        UserAndPasswordSha pair = new UserAndPasswordSha(new User(), passwordSha);
        pair.user.setLogin(loginOrEmail);
        return unwrap(super.findBy(pair,"SELECT * FROM User WHERE (login=? OR email=?) AND passwordSha=?", this::loginOrEmailAndPasswordStatementSetter));
    }

    private void loginOrEmailStatementSetter(UserAndPasswordSha pair, PreparedStatement statement) throws SQLException {
        statement.setString(1, pair.user.getLogin());
        statement.setString(2, pair.user.getLogin());
    }

    @Override
    public User findByLoginOrEmail(String loginOrEmail) {
        UserAndPasswordSha pair = new UserAndPasswordSha(new User(), "");
        pair.user.setLogin(loginOrEmail);
        return unwrap(super.findBy(pair, "SELECT * FROM User WHERE (login=? OR email=?)", this::loginOrEmailStatementSetter));
    }

    private User unwrap(UserAndPasswordSha pair) {
        return pair == null ? null : pair.user;
    }

    @Override
    public long findCount() {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM User")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();
                    return resultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find count.", e);
        }
    }

    private void hiddenUpdateStatementSetter(UserAndPasswordSha userAndPasswordSha, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setBoolean(1, userAndPasswordSha.user.isAdmin());
        preparedStatement.setLong(2, userAndPasswordSha.user.getId());
    }

    @Override
    public User changeStatus(long id, boolean newStatus) {
        UserAndPasswordSha userAndPasswordSha = new UserAndPasswordSha(new User(), "");
        userAndPasswordSha.user.setAdmin(newStatus);
        userAndPasswordSha.user.setId(id);
        super.update(userAndPasswordSha, this::hiddenUpdateStatementSetter, "UPDATE `User` SET admin=? WHERE id=?");
        return unwrap(userAndPasswordSha);
    }

    @Override
    public List<User> findAll() {
        return super.findAll("SELECT * FROM User ORDER BY id DESC").stream().map(a -> a.user).collect(Collectors.toList());
    }

    protected UserAndPasswordSha toEntity(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        UserAndPasswordSha pair = new UserAndPasswordSha(new User(), "");
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    pair.user.setId(resultSet.getLong(i));
                    break;
                case "login":
                    pair.user.setLogin(resultSet.getString(i));
                    break;
                case "creationTime":
                    pair.user.setCreationTime(resultSet.getTimestamp(i));
                    break;
                case "email":
                    pair.user.setEmail(resultSet.getString(i));
                    break;
                case "admin":
                    pair.user.setAdmin(resultSet.getBoolean(i));
                    break;
                default:
                    // No operations.
            }
        }

        return pair;
    }

    private void saveStatementSetter(UserAndPasswordSha pair, PreparedStatement statement) throws SQLException {
        statement.setString(1, pair.user.getLogin());
        statement.setString(2, pair.passwordSha);
        statement.setString(3, pair.user.getEmail());
    }

    private void saveResultSetter(UserAndPasswordSha pair, ResultSet generatedKeys) throws SQLException {
        pair.user.setId(generatedKeys.getLong(1));
        pair.user.setCreationTime(find(pair.user.getId()).getCreationTime());
        pair.user.setEmail(find(pair.user.getId()).getEmail());
    }

    @Override
    public void save(User user, String passwordSha) {
        UserAndPasswordSha pair = new UserAndPasswordSha(user, passwordSha);
        String saveSQLRequest = "INSERT INTO `User` (`login`, `passwordSha`, `creationTime`, `email`) VALUES (?, ?, NOW(), ?)";
        super.save(pair, saveSQLRequest, this::saveStatementSetter, this::saveResultSetter);
    }
}
