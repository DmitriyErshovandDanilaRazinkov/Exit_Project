package com.repository;

import com.model.Audio;
import com.model.FileAud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileAud, Long> {
    FileAud findByName(String name);
}
