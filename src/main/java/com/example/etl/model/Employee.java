package com.example.etl.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Data
@Document(collection = "employees")
public class Employee {
    @Id
    private String employeeID;

    private String firstName;
    private String lastName;
    private int salary;
    private String department;
    private LocalDate hiringDate;
    private int salaryWithBonus;
    private String departmentUpperCase;
    private double experience;

    // Default constructor required by MongoDB
    public Employee() {}

    // Constructor with all fields
    public Employee(String employeeID, String firstName, String lastName,
                    int salary, String department, LocalDate hiringDate,
                    int salaryWithBonus, String departmentUpperCase, double experience) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.department = department;
        this.hiringDate = hiringDate;
        this.salaryWithBonus = salaryWithBonus;
        this.departmentUpperCase = departmentUpperCase;
        this.experience = experience;
    }

    // Additional method to calculate salary with bonus
    public void calculateSalaryWithBonus(int bonusAmount) {
        this.salaryWithBonus = this.salary + bonusAmount;
    }

    // Method to set department in uppercase
    public void setDepartmentUpperCase() {
        this.departmentUpperCase = this.department != null ? this.department.toUpperCase() : null;
    }
}