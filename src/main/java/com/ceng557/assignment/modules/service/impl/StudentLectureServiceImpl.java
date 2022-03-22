package com.ceng557.assignment.modules.service.impl;

import com.ceng557.assignment.modules.entity.Lecture;
import com.ceng557.assignment.modules.entity.Student;
import com.ceng557.assignment.modules.entity.StudentLecture;
import com.ceng557.assignment.modules.error.QuotaFullException;
import com.ceng557.assignment.modules.error.StudentLectureException;
import com.ceng557.assignment.modules.repository.LectureRepository;
import com.ceng557.assignment.modules.repository.StudentLectureRepository;
import com.ceng557.assignment.modules.repository.StudentRepository;
import com.ceng557.assignment.modules.service.StudentLectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class StudentLectureServiceImpl implements StudentLectureService {
    @Autowired
    private StudentLectureRepository studentLectureRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Override
    @Transactional
    public StudentLecture registerStudentToLecture(String studentNumber, String lectureCode) throws QuotaFullException {
        Lecture l = lectureRepository.findByCode(lectureCode);
        Student s = studentRepository.findByNumber(studentNumber);

        Integer remainingQuota = lectureRepository.getRemainingQuota(lectureCode);
        if (remainingQuota < 1) throw new QuotaFullException("Quota is full for lecture " + lectureCode);

        return studentLectureRepository.save(new StudentLecture(s, l));
    }

    @Override
    @Transactional
    public void updateStudentGrade(Long studentID, Long lectureID, int grade) throws StudentLectureException {
        if (grade < 0 || grade > 100) throw new StudentLectureException("Grade must be between 0 and 100");
        Optional<StudentLecture> dbSL = studentLectureRepository
                .findStudentLectureByLectureIdAndStudentId(lectureID, studentID);
        if (dbSL.isEmpty())
            throw new StudentLectureException(String.format("Student with id %s is not assigned to lecture with id %s", studentID, lectureID));
        StudentLecture studentLecture = dbSL.get();
        studentLectureRepository.save(studentLecture);
    }

    @Override
    @Transactional
    public void updateStudentGrade(String studentNumber, String lectureCode, int grade) {
        Student s = studentRepository.findByNumber(studentNumber);
        Lecture l = lectureRepository.findByCode(lectureCode);
        if (s == null || l == null) return;

        updateStudentGrade(s.getId(), l.getId(), grade);
    }

    @Override
    @Transactional
    public void deleteStudentFromLecture(String studentNumber, String lectureCode) {
        Student s = studentRepository.findByNumber(studentNumber);
        Lecture l = lectureRepository.findByCode(lectureCode);

        if (s != null && l != null) {
            studentLectureRepository.deleteStudentLectureByStudentIdAndLectureId(s.getId(), l.getId());
        }
    }

    @Override
    @Transactional
    public void delete(StudentLecture studentLecture) {
        if (studentLecture != null)
            studentLectureRepository.delete(studentLecture);
    }


}
