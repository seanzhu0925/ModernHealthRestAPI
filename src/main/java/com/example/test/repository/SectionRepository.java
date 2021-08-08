package com.example.test.repository;

import com.example.test.model.Program;
import com.example.test.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findByProgram(Program programId);
}