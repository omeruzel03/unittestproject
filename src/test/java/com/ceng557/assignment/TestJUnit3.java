package com.ceng557.assignment;

import com.ceng557.assignment.modules.entity.Lecture;
import com.ceng557.assignment.modules.entity.Teacher;
import com.ceng557.assignment.modules.entity.TeacherLecture;
import com.ceng557.assignment.modules.service.LectureService;
import com.ceng557.assignment.modules.service.TeacherLectureService;
import com.ceng557.assignment.modules.service.TeacherService;
import com.ceng557.assignment.modules.util.MockUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
public class TestJUnit3 {

    private List<Teacher> savedTeachers;
    private List<TeacherLecture> savedTeacherLectures;
    private List<Lecture> savedLectures;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherLectureService teacherLectureService;

    @Autowired
    private LectureService lectureService;

    @Autowired
    private MockUtil mockUtil;

    @Before
    public void setup() {
        savedTeachers = new ArrayList<>();
        savedTeacherLectures = new ArrayList<>();
        savedLectures = new ArrayList<>();
    }

    @After
    public void teardown() {
        savedTeacherLectures.forEach(teacherLectureService::delete);

        savedTeachers.stream()
                .map(Teacher::getId)
                .forEach((id) -> {
                    if (id != null) teacherService.deleteById(id);
                });

        savedLectures.stream()
                .map(Lecture::getId)
                .forEach((id) -> {
                    if (id != null) lectureService.deleteById(id);
                });
    }

    @Test
    public void updateTeacher() {
        Teacher t = mockUtil.generateTeacher();
        t.setActive(Boolean.FALSE);
        Assert.assertTrue(teacherService.save(t));
        savedTeachers.add(t);

        t = teacherService.getByNumber(t.getNumber());
        Assert.assertFalse(t.getActive());

        t.setActive(Boolean.TRUE);
        Assert.assertTrue(teacherService.save(t));

        t = teacherService.getByNumber(t.getNumber());
        Assert.assertTrue(t.getActive());
    }

    @Test
    public void onlyActiveTeacherCanGiveCourse() {
        Lecture l = mockUtil.generateLecture();
        Assert.assertTrue(lectureService.save(l));
        savedLectures.add(l);

        Teacher t1 = mockUtil.generateTeacher();
        t1.setActive(Boolean.TRUE);
        Assert.assertTrue(teacherService.save(t1));
        savedTeachers.add(t1);

        TeacherLecture tl1 = teacherLectureService.assignTeacherToLecture(l.getCode(), t1.getNumber());
        Assert.assertNotNull(tl1);
        savedTeacherLectures.add(tl1);

        Assert.assertTrue(teacherLectureService.isTeacherAssignedToLecture(l.getCode(), t1.getNumber()));

        Teacher t2 = mockUtil.generateTeacher();
        t2.setActive(Boolean.FALSE);
        savedTeachers.add(t2);

        TeacherLecture tl2 = teacherLectureService.assignTeacherToLecture(l.getCode(), t2.getNumber());
        Assert.assertNull(tl2);
    }


}
