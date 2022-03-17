package com.ceng557.assignment.modules.repository;

import com.ceng557.assignment.modules.entity.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

    int countByNumber(String number);
    Student findByNumber(String number);

}
