package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.talk.Talk;
import ru.itmo.wp.model.repository.TalkRepository;

import java.sql.*;
import java.util.List;

public class TalkRepositoryImpl extends AbstractRepository<Talk> implements TalkRepository {

    private void saveStatementSetter(Talk talk, PreparedStatement statement) throws SQLException {
        statement.setLong(1, talk.getSourceUserId());
        statement.setLong(2, talk.getTargetUserId());
        statement.setString(3, talk.getText());
    }

    private void saveResultSetter(Talk talk, ResultSet generatedKeys) throws SQLException {
        talk.setId(generatedKeys.getLong(1));
        talk.setCreationTime(find(talk.getId()).getCreationTime());
    }

    private void findStatementSetter(Talk talk, PreparedStatement statement) throws SQLException {
        statement.setLong(1, talk.getId());
    }

    private Talk find(long id) {
        Talk talk = new Talk();
        talk.setId(id);
        String findSQLRequest = "SELECT * FROM Talk WHERE id=?";
        return super.findBy(talk, findSQLRequest, this::findStatementSetter);
    }

    @Override
    public void save(Talk talk) {
        String saveSQLRequest = "INSERT INTO `Talk` (`sourceUserId`, `targetUserId`, `text`, `creationTime`) VALUES (?, ?, ?, NOW())";
        super.save(talk, saveSQLRequest, this::saveStatementSetter, this::saveResultSetter);
    }

    @Override
    protected Talk toEntity(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Talk talk = new Talk();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    talk.setId(resultSet.getLong(i));
                    break;
                case "sourceUserId":
                    talk.setSourceUserId(resultSet.getLong(i));
                    break;
                case "targetUserId":
                    talk.setTargetUserId(resultSet.getLong(i));
                    break;
                case "text":
                    talk.setText(resultSet.getString(i));
                    break;
                case "creationTime":
                    talk.setCreationTime(resultSet.getDate(i));
                    break;
                default:
                    // No operations.
            }
        }
        return talk;
    }

    @Override
    public List<Talk> findAll() {
        return super.findAll("SELECT * FROM Talk ORDER BY id DESC");
    }
}
