package swingsample;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessExecutor {
    public static void executeCommand(String command, JTextArea outputArea) {

        Thread thread = new Thread(() -> {
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            try {
                Process process = processBuilder.start();

                try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                     BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

                    // Read the output from the command
                    SwingUtilities.invokeLater(() -> {
                        outputArea.append("Here is the standard output of the command:\n");
                    });
                    //System.out.println("Here is the standard output of the command:\n");
                    String line;
                    while ((line = stdInput.readLine()) != null) {
                        String finalS = line;
                        SwingUtilities.invokeLater(() -> {
                            outputArea.append(finalS + "\n");
                            outputArea.setCaretPosition(outputArea.getDocument().getLength());
                        });
                        //System.out.println(s);
                    }

// Read any errors from the attempted command
                    SwingUtilities.invokeLater(() -> {
                        outputArea.append("Here is the standard error of the command (if any):\n");
                    });
                    //System.out.println("Here is the standard error of the command (if any):\n");
                    while ((line = stdError.readLine()) != null) {
                        String finalS = line;
                        SwingUtilities.invokeLater(() -> {
                            outputArea.append(finalS + "\n");
                            outputArea.setCaretPosition(outputArea.getDocument().getLength());
                        });
                        //System.out.println(line);
                    }
                }
            } catch (IOException exception) {
                throw new RuntimeException(exception.getMessage());
            }

        });
        thread.start();
    }
}
