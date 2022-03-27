package com.ceng557.assignment.modules.service.impl;

import com.ceng557.assignment.modules.entity.Student;
import com.ceng557.assignment.modules.repository.StudentRepository;
import com.ceng557.assignment.modules.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public boolean existStudentByNumber(String number) {
        int count = studentRepository.countByNumber(number);
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Student getStudentByNumber(String number) {
        return studentRepository.findByNumber(number);
    }

    @Override
    public List<Student> getStudentList() {
        return (List<Student>) studentRepository.findAll();
    }

    @Override
    @Transactional
    public boolean save(Student student) {
        boolean existStudent = existStudentByNumber(student.getNumber());
        if (existStudent) {
            return false;
        }
        studentRepository.save(student);
        return true;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }


    @Override
    @Transactional
    public void delete(Student student) {
        studentRepository.delete(student);
    }
}
