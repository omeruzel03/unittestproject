package com.ceng557.assignment.modules.repository;

import com.ceng557.assignment.modules.entity.TeacherLecture;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherLectureRepository extends CrudRepository<TeacherLecture, Long> {

    TeacherLecture getTeacherLectureByLectureCodeAndTeacherNumber(String lectureCode, String teacherNumber);

    void delete(@NotNull TeacherLecture teacherLecture);

    void deleteTeacherLectureByTeacherNumber(String teacherNumber);
}
