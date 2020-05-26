package com.service;

import com.exceptions.NotFoundDataBaseException;
import com.model.DTO.TagTo;
import com.model.Tag;
import com.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TagService {

    private TagRepository repository;


    public void addTag(String name) {
        repository.save(new Tag(name));
    }

    public List<TagTo> getAll() {
        return repository.findAll().stream()
                .map(TagService::tagToTagTo)
                .collect(Collectors.toList());
    }

    public TagTo findTagToById(Long id) {
        return TagService.tagToTagTo(findTagById(id));
    }

    Tag findTagById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundDataBaseException("Тэг не найден"));
    }

    public void deleteTag(Long id) {
        repository.deleteById(id);
    }

    public static TagTo tagToTagTo(Tag tag) {
        TagTo tagTo = new TagTo();
        tagTo.setId(tag.getId());
        tagTo.setCountListen(tag.getCountListen());
        tagTo.setName(tag.getName());
        return tagTo;
    }

    public void tagsIsListen(Set<Tag> tags) {
        tags.stream().forEach((tag) -> {
            tag.setCountListen(tag.getCountListen() + 1);
            repository.save(tag);
        });
    }
}
