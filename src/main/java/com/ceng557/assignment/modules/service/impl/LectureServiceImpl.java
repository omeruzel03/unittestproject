package com.ceng557.assignment.modules.service.impl;

import com.ceng557.assignment.modules.entity.Lecture;
import com.ceng557.assignment.modules.repository.LectureRepository;
import com.ceng557.assignment.modules.service.LectureService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;

    private final EntityManager entityManager;

    public LectureServiceImpl(LectureRepository lectureRepository, EntityManager entityManager) {
        this.lectureRepository = lectureRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<Lecture> getLectureList() {
        return (List<Lecture>) lectureRepository.findAll();
    }

    @Override
    public Lecture getLectureByCode(String code) {
        return lectureRepository.findByCode(code);
    }

    @Override
    public boolean save(Lecture lecture) {
        lectureRepository.save(lecture);
        return true;
    }

    @Override
    public Lecture getLectureWithStudentsByCode(String code) {
        return lectureRepository.getLectureAndStudentsByCode(code);
    }

    @Override
    public Integer getRemainingQuota(String lectureCode) {
        return lectureRepository.getRemainingQuota(lectureCode);
    }

    @Override
    public List<Lecture> getTeacherLecturesByTeacherNumber(String teacherNumber) {
        return lectureRepository.getTeacherLecturesByTeacherNumber(teacherNumber);
    }
}
