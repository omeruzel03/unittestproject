package com.ceng557.assignment;

import com.ceng557.assignment.modules.entity.Student;
import com.ceng557.assignment.modules.service.StudentService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestPropertySource(locations="classpath:application-test.properties")
@ContextConfiguration(classes = AssignmentApplication.class, loader = AnnotationConfigContextLoader.class)
public class TestJunit1 {

    @Autowired
    private StudentService studentService;

    @Test
    public void test1InsertStudent(){
        Student student = new Student();
        student.setNumber("202070001");
        student.setName("Cem");
        student.setSurname("Derin");
        student.setGraduated(Boolean.FALSE);
        boolean falseStatus = studentService.save(student);
        Assert.assertFalse(falseStatus);

        student.setNumber("202070002");
        boolean trueStatus = studentService.save(student);
        Assert.assertTrue(trueStatus);
    }

    @Test
    public void test2StudentCount(){
        List<Student> list = studentService.getStudentList();
        Assert.assertFalse(list.size() == 1);
    }

    @Test
    public void test3UpdateStudent(){
        Student student = studentService.getStudentByNumber("202070002");
        Assert.assertNotNull(student);
        student.setNumber("202070003");

        studentService.save(student);
        Student oldStudent = studentService.getStudentByNumber("202070002");
        Assert.assertNull(oldStudent);

        Student currentStudent = studentService.getStudentByNumber("202070003");
        Assert.assertNotNull(currentStudent);
    }

    @Test
    public void test4DeleteStudent(){
        Student student = studentService.getStudentByNumber("202070003");
        Assert.assertNotNull(student);

        studentService.deleteById(student.getId());
        Student deletedStudent = studentService.getStudentByNumber("202070003");
        Assert.assertNull(deletedStudent);
    }

}
