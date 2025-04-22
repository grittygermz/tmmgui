package swingsample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * creates a json file for existing jar to pick it up
 * streams the output from that jar onto the gui
 * checks current directory for configuration file for gui so that it will have default value
 */
public class Main {

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatDarkLaf());

        JFrame jFrame = new JFrame();
        jFrame.setSize(700, 900);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = jFrame.getContentPane();
        GuiBuilder guiBuilder = new GuiBuilder(contentPane);

        InputValues inputValues = loadDefaultValuesFromConfigFileIfExists("config.json");

        addInputsToGui(guiBuilder, inputValues);

        OutputArea outputArea = createOutputArea();

        JButton executeButton = createExecuteButton(contentPane, outputArea, guiBuilder);

        guiBuilder.addComponent(executeButton);
        guiBuilder.addComponent(outputArea.jScrollPane());
        jFrame.setVisible(true);
    }

    private static JButton createExecuteButton(Container contentPane, OutputArea outputArea, GuiBuilder guiBuilder) {
        JButton executeButton = guiBuilder.createButton("test", 100, 40);
        ButtonAction buttonAction = new ButtonAction(contentPane, outputArea, executeButton);
        buttonAction.setCommand("ping localhost");
        buttonAction.setFileName("jobInput.json");
        executeButton.addActionListener(buttonAction);
        return executeButton;
    }

    private static void addInputsToGui(GuiBuilder guiBuilder, InputValues inputValues) {
        guiBuilder.addInput("archiveFilesPath", inputValues);
        guiBuilder.addInput("docType", inputValues);
        guiBuilder.addInput("archiveId", inputValues);
        guiBuilder.addInput("bookingCenter", inputValues);
        guiBuilder.addInput("archivalSys", inputValues);
        guiBuilder.addInput("date", inputValues);
        guiBuilder.addInput("versionNum", inputValues);
        guiBuilder.addInput("seqNumStart", inputValues);
        guiBuilder.addInput("seqNumEnd", inputValues);
        guiBuilder.addInput("maxFolderSizeGB", inputValues);
        guiBuilder.addInput("maxDataFiles", inputValues);
        guiBuilder.addInput("docId", inputValues);
        guiBuilder.addInput("typeOfMailing", inputValues);
        guiBuilder.addInput("title", inputValues);
        guiBuilder.addInput("jobId", inputValues);
    }

    private static OutputArea createOutputArea() {
        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        OutputArea result = new OutputArea(jTextArea, jScrollPane);
        return result;
    }

    static InputValues loadDefaultValuesFromConfigFileIfExists(String fileName) {
        return JsonHandler.readJsonFromFile(fileName, InputValues.class);
    }
}
