package com.ceng557.assignment.modules.service.impl;

import com.ceng557.assignment.modules.entity.Teacher;
import com.ceng557.assignment.modules.repository.TeacherRepository;
import com.ceng557.assignment.modules.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public boolean save(Teacher teacher) {
        if (teacher.getId() == null) {
            teacherRepository.save(teacher);
            return true;
        } else {
            Teacher saved = teacherRepository.save(teacher);
            return saved.getId().equals(teacher.getId());
        }
    }

    @Override
    public Teacher getByNumber(String number) {
        return teacherRepository.getTeacherByNumber(number);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        teacherRepository.deleteById(id);
    }
}
