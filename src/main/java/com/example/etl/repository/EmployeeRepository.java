package com.example.etl.repository;

import com.example.etl.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {

    // Custom query methods
    List<Employee> findByDepartment(String department);
    List<Employee> findBySalaryGreaterThan(int salary);
    List<Employee> findByExperienceGreaterThanEqual(double experience);
    List<Employee> findByDepartmentUpperCase(String departmentUpperCase);

    // Complex query method combining multiple criteria
    List<Employee> findByDepartmentAndSalaryGreaterThan(String department, int salary);
}