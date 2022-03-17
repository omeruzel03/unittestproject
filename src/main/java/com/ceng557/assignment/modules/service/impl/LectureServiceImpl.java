package com.ceng557.assignment.modules.service.impl;

import com.ceng557.assignment.modules.entity.Lecture;
import com.ceng557.assignment.modules.repository.LectureRepository;
import com.ceng557.assignment.modules.service.LectureService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;

    public LectureServiceImpl(LectureRepository lectureRepository){
        this.lectureRepository = lectureRepository;
    }

    @Override
    public List<Lecture> getLectureList() {
        return (List<Lecture>) lectureRepository.findAll();
    }

    @Override
    public Lecture getLectureByCode(String code) {
        return lectureRepository.findByCode(code);
    }

}
