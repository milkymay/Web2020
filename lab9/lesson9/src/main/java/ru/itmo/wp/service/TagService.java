package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.repository.TagRepository;

import java.util.Optional;
import java.util.Set;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag findByName(String name) {
        return tagRepository.findByName(name);
    }

    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }
}
