package com.example.etl.service;

import com.example.etl.model.Employee;
import com.example.etl.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public void saveAllEmployees(List<Employee> employees) {
        // Pre-process employees before saving
        employees.forEach(employee -> {
            // Set uppercase department
            employee.setDepartmentUpperCase();
            // Add any additional pre-processing logic here
        });

        employeeRepository.saveAll(employees);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }

    public void deleteAllEmployees() {
        employeeRepository.deleteAll();
    }
}