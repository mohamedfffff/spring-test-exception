package com.example.spring_boot_testing;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class ErrorDetails {

    private LocalDateTime timestamp;
    private String message;
    private String details;

    public ErrorDetails(String message, String details) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.details = details;
    }
}
