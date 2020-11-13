package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.event.Event;

public interface EventRepository {
    void save(Event event);
}
