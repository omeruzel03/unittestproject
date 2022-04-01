package com.ceng557.assignment.modules.service.impl;

import com.ceng557.assignment.modules.dao.LectureDao;
import com.ceng557.assignment.modules.entity.Lecture;
import com.ceng557.assignment.modules.repository.LectureRepository;
import com.ceng557.assignment.modules.service.LectureService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;

    private final LectureDao lectureDao;

    @Override
    public List<Lecture> getLectureList() {
        return (List<Lecture>) lectureRepository.findAll();
    }

    @Override
    public Lecture getLectureByCode(String code) {
        return lectureRepository.findByCode(code);
    }

    @Override
    @Transactional
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
    public List<String> getTeacherLecturesByTeacherNumber(String teacherNumber) {
        return lectureDao.getTeacherLecturesByTeacherNumber(teacherNumber);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        lectureRepository.deleteById(id);
    }
}
