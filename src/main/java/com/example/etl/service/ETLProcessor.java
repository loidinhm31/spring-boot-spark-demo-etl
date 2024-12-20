package com.example.etl.service;

import com.example.etl.model.Employee;
import org.apache.spark.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static org.apache.spark.sql.functions.*;

@Component
public class ETLProcessor {
    private final SparkSession sparkSession;
    private final EmployeeService employeeService;

    @Autowired
    public ETLProcessor(SparkSession sparkSession, EmployeeService employeeService) {
        this.sparkSession = sparkSession;
        this.employeeService = employeeService;
    }

    public void processCSV(String csvPath) {
        try {
            // Read CSV file into DataFrame
            Dataset<Row> rawEmployeeData = sparkSession.read()
                    .option("header", "true")
                    .option("inferSchema", "true")
                    .csv(csvPath);

            // Apply transformations
            Dataset<Row> transformedData = rawEmployeeData
                    // Calculate salary with bonus (salary + 1000)
                    .withColumn("salaryWithBonus",
                            col("salary").plus(1000))
                    // Convert department to uppercase
                    .withColumn("departmentUpperCase",
                            upper(col("department")))
                    // Calculate experience based on hiring date
                    .withColumn("experience",
                            months_between(current_date(), col("hiringDate"))
                                    .divide(12).cast("double"))
                    // Round experience to 2 decimal places
                    .withColumn("experience",
                            round(col("experience"), 2));

            // Convert DataFrame to Employee objects
            Dataset<Employee> employeeDataset = transformedData.as(Encoders.bean(Employee.class));

            // Collect the data and save to MongoDB
            java.util.List<Employee> employees = employeeDataset.collectAsList();
            employeeService.saveAllEmployees(employees);

            System.out.println("ETL Process completed successfully. Processed " +
                    employees.size() + " records.");

        } catch (Exception e) {
            System.err.println("Error during ETL process: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("ETL process failed", e);
        }
    }
}