package com.service;

import com.exceptions.NotFoundDataBaseException;
import com.model.Tag;
import com.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private TagRepository repository;

    public TagService(TagRepository repository) {
        this.repository = repository;
    }

    public boolean addTag(String name) {
        Tag newTag = new Tag(name);
        repository.save(newTag);
        return true;
    }

    public List<Tag> getAll() {
        return repository.findAll();
    }

    public Tag foundTagById(long id) {
        if (repository.findById(id).isPresent()) {
            return repository.findById(id).get();
        } else {
            throw new NotFoundDataBaseException("Тэг не найден");
        }
    }

    public boolean deleteTag(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
