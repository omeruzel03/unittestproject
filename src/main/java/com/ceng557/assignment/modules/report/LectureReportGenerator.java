package com.ceng557.assignment.modules.report;

import com.ceng557.assignment.modules.entity.Lecture;
import com.ceng557.assignment.modules.entity.Student;
import com.ceng557.assignment.modules.entity.StudentLecture;

import java.util.List;
import java.util.stream.Collectors;


public class LectureReportGenerator implements ReportGenerator<String> {
    private final Lecture lecture;
    private List<Student> passStudents;
    private List<Student> failStudents;


    public LectureReportGenerator(Lecture lecture) {
        this.lecture = lecture;
    }

    private void setup() {
        passStudents = lecture
                .getStudentLectures()
                .stream()
                .filter((sl) -> sl.getGrade() >= lecture.getPassGrade())
                .map(StudentLecture::getStudent)
                .collect(Collectors.toList());

        failStudents = lecture
                .getStudentLectures()
                .stream()
                .filter((sl) -> sl.getGrade() < lecture.getPassGrade())
                .map(StudentLecture::getStudent)
                .collect(Collectors.toList());
    }

    public List<Student> getPassedStudents() {
        return passStudents;
    }

    public List<Student> getFailStudents() {
        return failStudents;
    }

    public Double getAverage() {
        if (lecture.getStudentLectures().isEmpty()) return 0D;
        int total = lecture.
                getStudentLectures().
                stream().
                map(StudentLecture::getGrade).
                reduce(0, Integer::sum);
        return ((double) total) / lecture.getStudentLectures().size();
    }

    public boolean isPassed(Student student) {
        for (Student s : passStudents) {
            if (s.equals(student)) return true;
        }
        return false;
    }

    @Override
    public String generateReport() {
        if (passStudents == null || failStudents == null) setup();

        StringBuilder builder = new StringBuilder();
        builder.append("Lecture report for lecture with code ").append(lecture.getCode()).append("\n");
        builder.append("-----PASSED STUDENTS----\n");
        for (Student s : passStudents) {
            builder.append("Name: ").append(s.getName()).append("\tLast Name: ").append(s.getSurname()).append("\n");
        }
        builder.append("-----FAILED STUDENTS----\n");
        for (Student s : passStudents) {
            builder.append("Name: ").append(s.getName()).append("\tLast Name: ").append(s.getSurname()).append("\n");
        }
        builder.append("AVERAGE GRADE: ").append(getAverage());
        return builder.toString();
    }
}
