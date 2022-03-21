package com.ceng557.assignment;

import com.ceng557.assignment.modules.entity.Teacher;
import com.ceng557.assignment.modules.service.TeacherService;
import com.ceng557.assignment.modules.service.impl.LectureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class AssignmentApplication {
    public static void main(String[] args) {
        SpringApplication.run(AssignmentApplication.class, args);
    }

}
