package com.service;

import com.exceptions.NotFoundDataBaseException;
import com.model.Tag;
import com.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TagService {

    private TagRepository repository;


    public void addTag(String name) {
        repository.save(new Tag(name));
    }

    public List<Tag> getAll() {
        return repository.findAll();
    }

    public Tag foundTagById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundDataBaseException("Тэг не найден"));
    }

    public void deleteTag(Long id) {
        repository.deleteById(id);
    }
}
