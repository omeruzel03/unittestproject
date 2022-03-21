package com.ceng557.assignment;

import com.ceng557.assignment.modules.entity.Lecture;
import com.ceng557.assignment.modules.entity.Student;
import com.ceng557.assignment.modules.service.LectureService;
import com.ceng557.assignment.modules.service.StudentLectureService;
import com.ceng557.assignment.modules.service.StudentService;
import com.ceng557.assignment.modules.util.MockUtil;
import org.hibernate.annotations.common.util.impl.Log;
import org.junit.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@ContextConfiguration(classes = AssignmentApplication.class, loader = AnnotationConfigContextLoader.class)
public class TestJUnit1 {

    @Autowired
    private StudentService studentService;

    @Autowired
    private MockUtil mockUtil;

    private List<Student> savedStudents;

    @Before
    public void setup() {
        savedStudents = new ArrayList<>();
    }

    @After
    public void tearDown() {
        try {
            savedStudents.forEach(studentService::delete);
        } catch (Exception e) {
        }
    }


    @Test
    public void test1InsertStudent() {
        Student student = new Student("202070001", "Ahmet", "Veli", Boolean.FALSE);
        boolean trueStatus = studentService.save(student);
        Assert.assertTrue(trueStatus);
        savedStudents.add(student);

        student = new Student();
        student.setNumber("202070001");
        student.setName("Cem");
        student.setSurname("Derin");
        student.setGraduated(Boolean.FALSE);
        boolean falseStatus = studentService.save(student); // Has the same id with first student
        Assert.assertFalse(falseStatus);

        student.setNumber("202070002");
        trueStatus = studentService.save(student);
        Assert.assertTrue(trueStatus);
        savedStudents.add(student);
    }

    @Test
    public void test2StudentCount() {
        int STUDENT_COUNT = 20;

        mockUtil.generateStudent(STUDENT_COUNT).forEach((s) -> {
            Assert.assertTrue(studentService.save(s));
            savedStudents.add(s);
        });

        List<Student> list = studentService.getStudentList();
        Assert.assertTrue(list.size() == STUDENT_COUNT);
    }

    @Test
    public void test3UpdateStudent() {
        String STUDENT_NUMBER = "202070002";
        String NEW_STUDENT_NUMBER = "202070003";

        Student student = mockUtil.generateStudent();
        student.setNumber(STUDENT_NUMBER);
        studentService.save(student);
        savedStudents.add(student);

        student = studentService.getStudentByNumber(STUDENT_NUMBER);
        Assert.assertNotNull(student);

        student.setNumber(NEW_STUDENT_NUMBER);
        studentService.save(student);
        savedStudents.add(student);

        Student oldStudent = studentService.getStudentByNumber(STUDENT_NUMBER);
        Assert.assertNull(oldStudent);

        Student currentStudent = studentService.getStudentByNumber(NEW_STUDENT_NUMBER);
        Assert.assertNotNull(currentStudent);
    }

    @Test
    public void test4DeleteStudent() {
        Student student = mockUtil.generateStudent();
        student.setNumber("202070003");
        studentService.save(student);
        savedStudents.add(student);

        student = studentService.getStudentByNumber("202070003");
        Assert.assertNotNull(student);

        studentService.deleteById(student.getId());
        Student deletedStudent = studentService.getStudentByNumber("202070003");
        Assert.assertNull(deletedStudent);
    }

}
