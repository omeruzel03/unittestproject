package com.ceng557.assignment.modules.service.impl;

import com.ceng557.assignment.modules.entity.Lecture;
import com.ceng557.assignment.modules.entity.Teacher;
import com.ceng557.assignment.modules.entity.TeacherLecture;
import com.ceng557.assignment.modules.repository.LectureRepository;
import com.ceng557.assignment.modules.repository.TeacherLectureRepository;
import com.ceng557.assignment.modules.repository.TeacherRepository;
import com.ceng557.assignment.modules.service.TeacherLectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeacherLectureServiceImpl implements TeacherLectureService {
    @Autowired
    private TeacherLectureRepository teacherLectureRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private TeacherRepository teacherRepository;


    @Override
    @Transactional
    public TeacherLecture assignTeacherToLecture(String lectureCode, String teacherNumber) {
        Lecture l = lectureRepository.findByCode(lectureCode);
        if (l == null) return null;

        Teacher t = teacherRepository.getTeacherByNumber(teacherNumber);
        if (t == null) return null;
        if (!t.getActive()) return null;

        return teacherLectureRepository.save(new TeacherLecture(t, l));
    }

    @Override
    public boolean isTeacherAssignedToLecture(String lectureCode, String teacherNumber) {
        TeacherLecture tl = teacherLectureRepository
                .getTeacherLectureByLectureCodeAndTeacherNumber(lectureCode, teacherNumber);
        return tl != null;
    }

    @Override
    public void deleteById(Long id) {
        teacherLectureRepository.deleteById(id);
    }


    @Override
    public void deleteByTeacherNumber(String teacherNumber) {
        teacherLectureRepository.deleteTeacherLectureByTeacherNumber(teacherNumber);
    }

    @Override
    public void delete(TeacherLecture teacherLecture) {
        teacherLectureRepository.delete(teacherLecture);
    }
}
