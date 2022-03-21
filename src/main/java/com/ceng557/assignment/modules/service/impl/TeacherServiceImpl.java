package com.ceng557.assignment.modules.service.impl;

import com.ceng557.assignment.modules.entity.Teacher;
import com.ceng557.assignment.modules.repository.TeacherRepository;
import com.ceng557.assignment.modules.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Teacher getTeacherAndLecturesByNumber(String teacherNumber) {
        return teacherRepository.getTeacherAndLecturesByNumber(teacherNumber);
    }
}
