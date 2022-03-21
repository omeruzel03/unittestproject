package com.ceng557.assignment.modules.repository;

import com.ceng557.assignment.modules.entity.Teacher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher, Long> {
    @Query("SELECT t FROM Teacher t " +
            "LEFT JOIN FETCH t.teacherLectures tl " +
            "LEFT JOIN FETCH tl.lecture l " +
            "WHERE t.number = :teacherNumber")
    Teacher getTeacherAndLecturesByNumber(String teacherNumber);
}
