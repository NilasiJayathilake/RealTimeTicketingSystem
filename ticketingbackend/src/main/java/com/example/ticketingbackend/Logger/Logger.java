package com.example.ticketingbackend.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class Logger {
    private static final String LOG_FILE = "system_log.txt";
    private static boolean isInitialized = false;

    // Method to initialize the log file (clear previous logs)
    private static void initializeLogFile() {
        if (!isInitialized) {
            try {
                Files.write(Paths.get(LOG_FILE), new byte[0]); // Clear the log file
                isInitialized = true; // Ensure it is only initialized once
                System.out.println("Log file initialized. Previous logs cleared.");
            } catch (IOException e) {
                throw new RuntimeException("Failed to initialize log file", e);
            }
        }
    }

    // General logging method
    public static void log(String message) {
        initializeLogFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            String logMessage = "[" + LocalDateTime.now() + "] - " + message;
            writer.write(logMessage);
            writer.newLine();
            System.out.println(logMessage);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save log message", e);
        }
    }

    // Specific logging for ticket operations
    public static void ticketLog(int ticketId, String operation) {
        log("Ticket Operation: " + operation + " | Ticket ID: " + ticketId);
    }

    // Logging for exceptions or faults
    public static void faultLog(String thread, String exception) {
        log("Fault Detected | Thread: " + thread + " | Exception: " + exception);
    }

}

