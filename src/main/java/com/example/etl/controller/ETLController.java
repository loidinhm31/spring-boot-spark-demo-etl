package com.example.etl.controller;

import com.example.etl.service.ETLProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/etl")
public class ETLController {

    @Autowired
    private ETLProcessor etlProcessor;

    @PostMapping("/process")
    public ResponseEntity<String> processCSV(@RequestParam("file") MultipartFile file) {
        try {
            // Create a temporary directory if it doesn't exist
            Path tempDir = Paths.get("temp");
            if (!Files.exists(tempDir)) {
                Files.createDirectory(tempDir);
            }

            // Save the uploaded file
            String fileName = file.getOriginalFilename();
            Path filePath = tempDir.resolve(fileName);
            Files.write(filePath, file.getBytes());

            // Process the file
            etlProcessor.processCSV(filePath.toString());

            // Clean up
            Files.deleteIfExists(filePath);

            return ResponseEntity.ok("ETL process completed successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error during ETL process: " + e.getMessage());
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("ETL Service is running");
    }
}