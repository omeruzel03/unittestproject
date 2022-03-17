package com.ceng557.assignment.modules.service.impl;

import com.ceng557.assignment.modules.entity.Department;
import com.ceng557.assignment.modules.repository.DepartmentRepository;
import com.ceng557.assignment.modules.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository){
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Department> getDepartmentList() {
        return (List<Department>) departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentByName(String name) {
        return departmentRepository.findByName(name);
    }

}
