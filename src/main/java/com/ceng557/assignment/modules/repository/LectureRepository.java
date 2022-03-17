package com.ceng557.assignment.modules.repository;

import com.ceng557.assignment.modules.entity.Lecture;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepository extends CrudRepository<Lecture, Long> {

    Lecture findByCode(String code);

}
