package com.example.test.repository;

import com.example.test.model.Activity;
import com.example.test.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findBySection(Section section);

}