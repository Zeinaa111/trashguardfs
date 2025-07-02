package utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static final String LOG_FILE = "trashguard.log";

    public static void log(String message) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            out.write("[" + timestamp + "] " + message);
            out.newLine();
        } catch (IOException e) {
            System.err.println("Log write failed.");
        }
    }

    public static void readLog() {
        try (BufferedReader in = new BufferedReader(new FileReader(LOG_FILE))) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("No logs found.");
        }
    }
}