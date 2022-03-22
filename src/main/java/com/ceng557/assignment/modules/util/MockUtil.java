package com.ceng557.assignment.modules.util;


import com.ceng557.assignment.modules.entity.Lecture;
import com.ceng557.assignment.modules.entity.Student;
import com.ceng557.assignment.modules.entity.Teacher;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MockUtil {
    private final Faker faker = new Faker();


    public Student generateStudent() {
        return new Student(
                faker.numerify("202071###"), // changes # with a number between 0-9
                faker.name().firstName(),
                faker.name().lastName(),
                Boolean.FALSE
        );
    }

    public List<Student> generateStudent(int size) {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            students.add(generateStudent());
        }
        return students;
    }

    public Teacher generateTeacher() {
        return new Teacher(
                faker.numerify("900100###"),
                faker.name().firstName(),
                faker.name().lastName()
        );
    }

    public Lecture generateLecture() {
        return new Lecture(
                faker.numerify("CENG6##"),
                faker.educator().course()
        );
    }
}
