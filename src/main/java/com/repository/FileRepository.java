package com.repository;

import com.model.FileAud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileAud, Long> {
}
