import java.io.*;
import java.time.LocalDateTime;

    public class Logger {
        private static final String Log_file = "logSystem.log";

        public static void log(String message){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(Log_file, true))) {
                writer.write(LocalDateTime.now() + " " + message);
                writer.newLine();
            }
            catch (IOException e) {
                System.out.println("Error" + e.getMessage());
            }
            }



    }

