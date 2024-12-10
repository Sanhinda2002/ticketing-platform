package com.ticketingplatform.ticketing_platform.Controller;

import java.io.IOException;

import com.ticketingplatform.ticketing_platform.Models.Configuration;
import com.ticketingplatform.ticketing_platform.Service.ConfigurationService;
import com.ticketingplatform.ticketing_platform.Models.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// REST Controller for handling configuration-related operations
// It provides endpoints for loading, saving, and retrieving event configurations
// This controller is a cross-origin controller
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api")
public class ConfigurationController {

    // Service layer to handle logics
    private final ConfigurationService configurationService;

    public ConfigurationController(ConfigurationService configurationService){
        this.configurationService = configurationService;
    }

    // Endpoint to load the saved configurations
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/load")
    public ResponseEntity<Configuration[]> loadConfiguration () throws IOException {
        Configuration[] configs = configurationService.loadConfiguration();
        if (configs.length > 0) {
            return ResponseEntity.ok(configs);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Endpoint to save the configuration
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/save")
    public ResponseEntity<String> saveConfiguration (@RequestBody Configuration configuration) throws IOException {
        boolean success = configurationService.saveConfiguration(configuration);
        if (success) {
            Logger.logInfo("Configuration with ID " + configuration.getId() + " saved successfully.");
            return ResponseEntity.ok("Configuration saved successfully");
        } else {
            return ResponseEntity.badRequest().body("Configuration failed to save");
        }
    }

    // Endpoint to fetch events
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/events")
    public Configuration[] getEvents() {
        return configurationService.loadConfiguration();
    }

}