package com.service;

import com.model.FileAud;
import com.model.Tag;
import com.repository.FileRepository;
import com.repository.TagRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.io.File;
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

    public Tag foundTagById(long id) throws NotFoundException {
        if (repository.findById(id).isPresent()) {
            return repository.findById(id).get();
        } else {
            throw new NotFoundException("Тэг не найден");
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
