package com.ceng557.assignment.modules.service;


import com.ceng557.assignment.modules.entity.StudentLecture;
import com.ceng557.assignment.modules.error.QuotaFullException;

public interface StudentLectureService {
    StudentLecture registerStudentToLecture(String studentNumber, String lectureCode) throws QuotaFullException;

    void deleteStudentFromLecture(String studentNumber, String lectureCode);

    void updateStudentGrade(Long studentID, Long lectureID, int grade);

    void updateStudentGrade(String studentNumber, String lectureCode, int grade);

    void delete(StudentLecture studentLecture);
}
