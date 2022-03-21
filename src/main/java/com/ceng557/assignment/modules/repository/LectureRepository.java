package com.ceng557.assignment.modules.repository;

import com.ceng557.assignment.modules.entity.Lecture;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface LectureRepository extends CrudRepository<Lecture, Long> {

    Lecture findByCode(String code);


    @Query("SELECT (l.quota - COUNT(sl.student.id)) " +
            "FROM Lecture l " +
            "LEFT JOIN StudentLecture sl " +
            "ON l.id = sl.lecture.id " +
            "WHERE l.code = :lectureCode " +
            "GROUP BY l.id")
    Integer getRemainingQuota(String lectureCode);

    @Query("SELECT l FROM Lecture l " +
            "LEFT JOIN FETCH l.teacherLectures tl " +
            "LEFT JOIN FETCH tl.teacher t " +
            "WHERE t.number = :teacherNumber ORDER BY l.code ASC")
    List<Lecture> getTeacherLecturesByTeacherNumber(String teacherNumber);


    @Query("SELECT l FROM Lecture l LEFT JOIN FETCH l.studentLectures sl LEFT JOIN FETCH sl.student WHERE l.code=:code")
    Lecture getLectureAndStudentsByCode(String code);


}
