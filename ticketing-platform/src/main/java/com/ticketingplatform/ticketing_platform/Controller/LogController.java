package com.ticketingplatform.ticketing_platform.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

// REST controller for logger operations
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class LogController {
    @GetMapping("/logs")
    public ResponseEntity<List<String>> getLogs() {
        try {
            Path logFile = Paths.get("system.log");
            List<String> logs = Files.readAllLines(logFile);
            return ResponseEntity.ok(logs);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}