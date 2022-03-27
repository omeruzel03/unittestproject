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

    /**
     * Fields are instantiated
     */
    @Before
    public void setup() {
        savedStudents = new ArrayList<>();
    }

    /**
     * Database rollback operations are handled in this method.
     */
    @After
    public void tearDown() {
        savedStudents.forEach((it) -> {
            if (it != null && it.getId() != null)
                studentService.delete(it);
        });
    }

    /**
     * The student can be inserted to the database if it has a unique number.
     * If the number is not unique, studentService.save(Student) will return false.
     */
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

    /**
     * Student service has a method to get all the students in the database.
     * We can compare the returned list size to check the student count.
     */
    @Test
    public void test2StudentCount() {
        int STUDENT_COUNT = 5;

        mockUtil.generateStudent(STUDENT_COUNT).forEach((s) -> {
            Assert.assertTrue(studentService.save(s));
            savedStudents.add(s);
        });

        List<Student> list = studentService.getStudentList();
        Assert.assertTrue(list.size() == STUDENT_COUNT);
    }

    /**
     * We can update the student's number with a new number.
     */
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

    /**
     * We can delete the student from the database.
     */
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
