package com.ticketingplatform.ticketing_platform.Models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Logger {
    private static final String LOG_FILE = "system.log";

    // Logs an INFO-level message
    public static void logInfo(String message) {
        log("INFO", message);
    }

    // Logs a WARNING-level message
    public static void logWarning(String message) {
        log("WARNING", message);
    }

    // Logs an ERROR-level message
    public static void logError(String message) {
        log("ERROR", message);
    }

    // Method to write log messages with a specific log level
    private static void log(String level, String message) {
        String timeStampedMessage = String.format("%s [%s]: %s", LocalDateTime.now(), level, message);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(timeStampedMessage);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}