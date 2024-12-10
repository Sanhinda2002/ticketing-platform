package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

// Logger class to handle logging
public class Logger {
    private static final String LOG_FILE = "system.log";

    // Default constructor
    public Logger() {
    }

    public static void logInfo(String message) {
        String var10000 = String.valueOf(LocalDateTime.now());
        String timeStampedMessage = var10000 + ": " + message;

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("system.log", true));

            try {
                writer.write(timeStampedMessage);
                writer.newLine();
            } catch (Throwable var6) {
                try {
                    writer.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }

            writer.close();
        } catch (IOException var7) {
            IOException e = var7;
            e.printStackTrace();
        }

    }
}
