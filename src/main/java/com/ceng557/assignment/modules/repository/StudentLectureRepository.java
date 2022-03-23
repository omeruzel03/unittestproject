package com.ceng557.assignment.modules.repository;

import com.ceng557.assignment.modules.entity.StudentLecture;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentLectureRepository extends CrudRepository<StudentLecture, Long> {
    Optional<StudentLecture> findStudentLectureByLectureIdAndStudentId(Long lectureId, Long studentId);

    void deleteStudentLectureByStudentIdAndLectureId(Long student_id, Long lecture_id);

}
