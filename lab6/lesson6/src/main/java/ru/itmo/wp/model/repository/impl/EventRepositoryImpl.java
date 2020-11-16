package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.event.Event;
import ru.itmo.wp.model.domain.event.EventType;
import ru.itmo.wp.model.repository.EventRepository;

import java.sql.*;

public class EventRepositoryImpl extends AbstractRepository<Event> implements EventRepository {

    private void saveStatementSetter(Event event, PreparedStatement statement) throws SQLException {
        statement.setLong(1, event.getUserId());
        statement.setString(2, event.getType().toString());
    }

    private void saveResultSetter(Event event, ResultSet generatedKeys) throws SQLException {
        event.setId(generatedKeys.getLong(1));
        event.setCreationTime(find(event.getId()).getCreationTime());
    }

    @Override
    public void save(Event event) {
        String saveSQLRequest = generateInsertSQL(event, " (`userId`, `type`, `creationTime`) VALUES (?, ?, NOW())");
        super.save(event, saveSQLRequest, this::saveStatementSetter, this::saveResultSetter);
    }

    private void findStatementSetter(Event event, PreparedStatement statement) throws SQLException {
        statement.setLong(1, event.getId());
    }

    public Event find(long id) {
        Event event = new Event();
        event.setId(id);
        String findSQLRequest = generateSelectSQL(event, " id=?");
        return super.findBy(event, findSQLRequest, this::findStatementSetter);
    }

    @Override
    protected Event toEntity(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Event event = new Event();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    event.setId(resultSet.getLong(i));
                    break;
                case "userId":
                    event.setUserId(resultSet.getLong(i));
                    break;
                case "creationTime":
                    event.setCreationTime(resultSet.getDate(i));
                    break;
                case "type":
                    event.setType(EventType.valueOf(resultSet.getString(i)));
                    break;
                default:
                    // No operations.
            }
        }

        return event;
    }
}
