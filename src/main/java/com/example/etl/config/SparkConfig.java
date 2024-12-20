package com.example.etl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class SparkConfig {
    @Value("${spring.data.mongodb.host}")
    private String mongoHost;

    @Value("${spring.data.mongodb.port}")
    private String mongoPort;

    @Value("${spring.data.mongodb.database}")
    private String mongoDatabase;

    @Value("${spring.data.mongodb.username}")
    private String username;

    @Value("${spring.data.mongodb.password}")
    private String password;

    @Bean
    public SparkSession sparkSession() {
        return SparkSession.builder()
                .appName("ETLJob")
                .master("local[*]")
                // Add these configurations to fix Java compatibility issues
                .config("spark.driver.host", "localhost")
                .config("spark.driver.bindAddress", "localhost")
                .config("spark.mongodb.input.uri",
                        String.format("mongodb://%s:%s@%s:%s/%s", username, password, mongoHost, mongoPort, mongoDatabase))
                .config("spark.mongodb.output.uri",
                        String.format("mongodb://%s:%s@%s:%s/%s", username, password, mongoHost, mongoPort, mongoDatabase))
                // Add these configurations for better compatibility
                .config("spark.driver.extraJavaOptions", "--add-exports java.base/sun.nio.ch=ALL-UNNAMED")
                .config("spark.executor.extraJavaOptions", "--add-exports java.base/sun.nio.ch=ALL-UNNAMED")
                .getOrCreate();
    }
}