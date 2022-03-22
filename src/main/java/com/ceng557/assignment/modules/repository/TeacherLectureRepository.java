package com.ceng557.assignment.modules.repository;

import com.ceng557.assignment.modules.entity.Teacher;
import com.ceng557.assignment.modules.entity.TeacherLecture;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TeacherLectureRepository extends CrudRepository<TeacherLecture, Long> {

    TeacherLecture getTeacherLectureByLectureCodeAndTeacherNumber(String lectureCode, String teacherNumber);

    void delete(@NotNull TeacherLecture teacherLecture);

    void deleteTeacherLectureByTeacherNumber(String teacherNumber);
}
