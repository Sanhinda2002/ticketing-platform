package com.ticketingplatform.ticketing_platform.Service;

import com.ticketingplatform.ticketing_platform.Models.Configuration;
import com.ticketingplatform.ticketing_platform.Repository.ConfigurationRepository;
import com.ticketingplatform.ticketing_platform.Models.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Service class for handling operations related to configurations
@Service
public class ConfigurationService {
    private final ConfigurationRepository configurationRepository;

    @Autowired
    // Constructor-based dependency injection
    public ConfigurationService(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    @Transactional
    // Saves a given configuration to the database
    public boolean saveConfiguration(Configuration configuration) {
        try {
            // Save the configuration object to the database
            configurationRepository.save(configuration);
            return true;

        } catch (Exception e) {
            // Handle exceptions during the save operation
            System.err.println("Failed to save configuration: " + e.getMessage());
            Logger.logError("Failed to save configuration");
            return false;
        }
    }

    @Transactional(readOnly = true)
    // Loads all configurations from the database
    public Configuration[] loadConfiguration() {
        List<Configuration> configs = configurationRepository.findAll();
        Logger.logInfo("Configuration loaded");
        return configs.toArray(new Configuration[0]);
    }

    @Transactional(readOnly = true)
    // Finds a configuration by its event name
    public Configuration findByEventName(String eventName) {
        return configurationRepository.findFirstByEventName(eventName);
    }

}


