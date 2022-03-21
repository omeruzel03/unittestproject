package com.ceng557.assignment.modules.service;

import com.ceng557.assignment.modules.entity.Student;

import java.util.List;

public interface StudentService {

    boolean existStudentByNumber(String number);

    Student getStudentByNumber(String number);

    List<Student> getStudentList();

    boolean save(Student student);

    void deleteById(Long id);

    void delete(Student student);

}
