package ru.itmo.wp.model.domain.event;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {
    private long id;
    private long userId;
    private EventType type;
    private Date creationTime;

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public EventType getType() {
        return type;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setType(EventType type) {
        this.type = type;
    }
}
