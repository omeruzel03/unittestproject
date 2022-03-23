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

    @Before
    public void setup() {
        savedStudents = new ArrayList<>();
        savedStudentLectures = new ArrayList<>();
    }

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

    @Test
    // Tests a student can be registered to an existing lecture
    public void studentCanRegisterCourse() {
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

    @Test
    public void studentCanDropCourse() {
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

    @Test
    // The StudentLectureService must throw exception if lecture is out of quota.
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


        try {
            StudentLecture sl1 = studentLectureService.registerStudentToLecture(s1.getNumber(), l.getCode());
            savedStudentLectures.add(sl1);
            StudentLecture sl2 = studentLectureService.registerStudentToLecture(s1.getNumber(), l.getCode());
            Assert.fail("QuotaFullException must be thrown.");
            savedStudentLectures.add(sl2);
        } catch (QuotaFullException e) {
            Assert.assertEquals("Quota is full for lecture " + l.getCode(), e.getMessage());
        }
    }


}
