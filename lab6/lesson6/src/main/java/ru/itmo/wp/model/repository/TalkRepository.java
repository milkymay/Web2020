package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.talk.Talk;

import java.util.List;

public interface TalkRepository {
    void save(Talk talk);
    List<Talk> findAll();
}
