package com.ceng557.assignment;

import com.ceng557.assignment.modules.entity.Lecture;
import com.ceng557.assignment.modules.entity.Student;
import com.ceng557.assignment.modules.entity.StudentLecture;
import com.ceng557.assignment.modules.error.QuotaFullException;
import com.ceng557.assignment.modules.service.LectureService;
import com.ceng557.assignment.modules.service.StudentLectureService;
import com.ceng557.assignment.modules.service.StudentService;
import com.ceng557.assignment.modules.util.MockUtil;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@ContextConfiguration(classes = AssignmentApplication.class, loader = AnnotationConfigContextLoader.class)
public class TestJUnit2 {
    @Autowired
    private StudentService studentService;

    @Autowired
    private LectureService lectureService;

    @Autowired
    private StudentLectureService studentLectureService;

    @Autowired
    private MockUtil mockUtil;


    private List<Student> savedStudents = new ArrayList<>();
    private List<StudentLecture> savedStudentLectures = new ArrayList<>();

    /**
     * Fields are instantiated
     */
    @Before
    public void setup() {
        savedStudents = new ArrayList<>();
        savedStudentLectures = new ArrayList<>();
    }

    /**
     * Database rollback operations are handled in this method.
     */
    @After
    public void tearDown() {
        cleanUpStudentLectures();
        cleanUpStudents();
    }

    private void cleanUpStudentLectures() {
        for (StudentLecture sl : savedStudentLectures) {
            studentLectureService.delete(sl);
        }
    }

    private void cleanUpStudents() {
        for (Student s : savedStudents) {
            studentService.deleteById(s.getId());
        }
    }

    /**
     * This method tests if a student can be registered to a lecture.
     * LectureService's getLectureWithStudentsByCode returns all students registered to the lecture.
     */
    @Test
    public void studentCanRegisterToLecture() {
        // SETUP
        Student s = mockUtil.generateStudent();
        Assert.assertNull(s.getId());
        boolean addStatus = studentService.save(s);
        savedStudents.add(s);
        Assert.assertTrue(addStatus);
        Assert.assertNotNull(s.getId()); // newly created student id must be assigned by the database

        Lecture l = lectureService.getLectureByCode("CENG501");
        Assert.assertNotNull(l);

        // TEST
        StudentLecture sl = studentLectureService.registerStudentToLecture(s.getNumber(), l.getCode());
        savedStudentLectures.add(sl);

        Lecture dbL = lectureService.getLectureWithStudentsByCode("CENG501");
        Assert.assertNotNull(dbL);
        Assert.assertTrue(dbL.isStudentRegistered(s));
    }

    /**
     * This method tests if a student can drop the lecture.
     * LectureService's getLectureWithStudentsByCode returns all students registered to the lecture.
     */
    @Test
    public void studentCanDropLecture() {
        //SETUP
        String lectureCode = "CENG503";
        Student s = mockUtil.generateStudent();
        studentService.save(s);
        savedStudents.add(s);
        StudentLecture sl = studentLectureService.registerStudentToLecture(s.getNumber(), lectureCode);
        savedStudentLectures.add(sl);

        //TEST
        Lecture l = lectureService.getLectureWithStudentsByCode(lectureCode);
        Assert.assertTrue(l.isStudentRegistered(s));

        studentLectureService.deleteStudentFromLecture(s.getNumber(), lectureCode);
        l = lectureService.getLectureWithStudentsByCode(lectureCode);
        Assert.assertFalse(l.isStudentRegistered(s));
    }

    /**
     * This method tests a student cannot register to the lecture if its quota is full.
     * The program must throw an exception.
     */
    @Test(expected = QuotaFullException.class)
    public void fullLectureThrowsOutOfQuotaException() {
        // SETUP
        Lecture l = lectureService.getLectureByCode("CENG502");
        l.setQuota(1);
        lectureService.save(l);

        Integer quota = lectureService.getRemainingQuota(l.getCode());
        Assert.assertEquals(1, (int) quota);

        Student s1 = mockUtil.generateStudent();
        Student s2 = mockUtil.generateStudent();
        if (studentService.save(s1))
            savedStudents.add(s1);
        if (studentService.save(s2))
            savedStudents.add(s2);

        StudentLecture sl1 = studentLectureService.registerStudentToLecture(s1.getNumber(), l.getCode());
        savedStudentLectures.add(sl1);
        studentLectureService.registerStudentToLecture(s1.getNumber(), l.getCode());
    }


}
