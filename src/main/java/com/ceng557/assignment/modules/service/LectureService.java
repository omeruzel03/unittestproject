package com.ceng557.assignment.modules.service;

import com.ceng557.assignment.modules.entity.Lecture;

import java.util.List;

public interface LectureService {

    List<Lecture> getLectureList();

    Lecture getLectureByCode(String code);

    Lecture getLectureWithStudentsByCode(String code);

    boolean save(Lecture lecture);

    Integer getRemainingQuota(String lectureCode);

    List<String> getTeacherLecturesByTeacherNumber(String teacherNumber);

    void deleteById(Long id);
}
