package com.ceng557.assignment.modules.service;

import com.ceng557.assignment.modules.entity.Teacher;

public interface TeacherService {
    Teacher getTeacherAndLecturesByNumber(String teacherNumber);

    boolean save(Teacher teacher);

    Teacher getByNumber(String number);

    void deleteById(Long id);
}
