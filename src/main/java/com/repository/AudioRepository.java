package com.repository;

import com.model.Audio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;

public interface AudioRepository extends JpaRepository<Audio, Long> {
    Audio findByName(String name);

    Audio findAudioByFile_Id(Long fileId);

    List<Audio> findAllByName(String name);
}
