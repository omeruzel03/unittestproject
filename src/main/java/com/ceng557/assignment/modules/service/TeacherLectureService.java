package com.ceng557.assignment.modules.service;

import com.ceng557.assignment.modules.entity.TeacherLecture;

public interface TeacherLectureService {
    TeacherLecture assignTeacherToLecture(String lectureCode, String teacherNumber);

    boolean isTeacherAssignedToLecture(String lectureCode, String teacherNumber);

    void delete(TeacherLecture teacherLecture);

    void deleteById(Long id);

    void deleteByTeacherNumber(String teacherNumber);
}
