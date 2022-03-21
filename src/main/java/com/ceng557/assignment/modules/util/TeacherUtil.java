package com.ceng557.assignment.modules.util;

import com.ceng557.assignment.modules.entity.Lecture;
import com.ceng557.assignment.modules.entity.Teacher;
import com.ceng557.assignment.modules.entity.TeacherLecture;

import java.util.*;
import java.util.stream.Collectors;

// A class created to test teacher class' database records.
public class TeacherUtil {
    // Compares teacher's lecture with lectures param, order is not considered.
    // Only, lecture code is considered in comparison
    public static boolean lecturesAreSame(Teacher teacher, String[] lectures) {
        Set<String> teachersLecturesCodes = teacher.getTeacherLectures()
                .stream()
                .map(TeacherLecture::getLecture)
                .map(Lecture::getCode).collect(Collectors.toSet());

        System.out.println(teachersLecturesCodes);

        for (String l : lectures) {
            if (!teachersLecturesCodes.contains(l)) return false;
        }

        return true;
    }
}
