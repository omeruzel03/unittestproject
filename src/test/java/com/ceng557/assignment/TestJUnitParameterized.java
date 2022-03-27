package com.ceng557.assignment;

import com.ceng557.assignment.modules.entity.Lecture;
import com.ceng557.assignment.modules.service.LectureService;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(Parameterized.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ContextConfiguration(classes = AssignmentApplication.class, loader = AnnotationConfigContextLoader.class)
public class TestJUnitParameterized {
    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();
    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Autowired
    private LectureService lectureService;

    private final String teacherNumber;
    private final String[] lectureCodes;

    /**
     * Constructor method for the parameterized test. This constructor is used in testCases method below.
     *
     * @param teacherNumber is the number of the teacher saved in the database.
     * @param lectureCodes  is the String array of lecture codes that we believe is correct. The array must be ordered lexicographically.
     */
    public TestJUnitParameterized(String teacherNumber, String[] lectureCodes) {
        this.teacherNumber = teacherNumber.trim();
        this.lectureCodes = lectureCodes;
    }

    /**
     * Method to create tests cases
     *
     * @return the object array to be used in class constructor.
     */
    @Parameterized.Parameters
    public static Collection<Object[]> testCases() {
        return Arrays.asList(new Object[][]{
                {"900100001", new String[]{"CENG501"}},
                {"900100002", new String[]{"CENG502"}},
                {"900100003", new String[]{"CENG503", "CENG504"}},
                {"900100004", new String[]{"CENG505"}},
                {"900100006", new String[]{"CENG506", "CENG507"}},
                {"900100007", new String[]{"CENG508"}},
                {"900100009", new String[]{"CENG509", "CENG510", "CENG511"}},
                {"900100010", new String[]{"CENG512", "CENG513", "CENG514"}},

        });
    }

    /**
     * Test method for the parameterized test class.
     * LectureService returns an array of lecture codes of the teacher in lexicographic order.
     * This method compares the returned array and the passed array in the constructor.
     */
    @Test
    public void testTeacherLectures() {
        List<String> lectureCodeList = lectureService
                .getTeacherLecturesByTeacherNumber(this.teacherNumber)
                .stream().map(Lecture::getCode).collect(Collectors.toList());

        String[] lectureCodeArr = new String[lectureCodes.length];
        lectureCodeList.toArray(lectureCodeArr);

        Assert.assertArrayEquals(lectureCodes, this.lectureCodes);
    }
}
