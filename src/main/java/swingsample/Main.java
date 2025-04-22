package swingsample;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * creates a json file for existing jar to pick it up
 * streams the output from that jar onto the gui
 * checks current directory for configuration file for gui so that it will have default value
 * disables button if all fields are not filled
 */
public class Main {

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatDarkLaf());

        JFrame jFrame = new JFrame();
        jFrame.setTitle("Archiving");
        jFrame.setSize(700, 900);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = jFrame.getContentPane();
        GuiBuilder guiBuilder = new GuiBuilder(contentPane);

        InputValues inputValues = loadDefaultValuesFromConfigFileIfExists("config.json");

        List<JTextField> jTextFields = addInputsToGui(guiBuilder, inputValues);

        OutputArea outputArea = createOutputArea();

        JButton executeButton = createExecuteButton(contentPane, outputArea, guiBuilder);

        addDocumentListenerForButtonEnabling(jTextFields, executeButton);


        guiBuilder.addComponent(executeButton);
        guiBuilder.addComponent(outputArea.jScrollPane());
        jFrame.setVisible(true);
    }

    private static JButton createExecuteButton(Container contentPane, OutputArea outputArea, GuiBuilder guiBuilder) {
        JButton executeButton = guiBuilder.createButton("Execute!", 200, 40);
        ButtonAction buttonAction = new ButtonAction(contentPane, outputArea, executeButton);
        //buttonAction.setCommand("ping localhost");
        buttonAction.setCommand("");
        buttonAction.setFileName("jobInput.json");
        executeButton.addActionListener(buttonAction);
        return executeButton;
    }

    private static List<JTextField> addInputsToGui(GuiBuilder guiBuilder, InputValues inputValues) {

        List<JTextField> jTextFields = new ArrayList<>();

        jTextFields.add(guiBuilder.addInput("archiveFilesPath", inputValues));
        jTextFields.add(guiBuilder.addInput("docType", inputValues));
        jTextFields.add(guiBuilder.addInput("archiveId", inputValues));
        jTextFields.add(guiBuilder.addInput("bookingCenter", inputValues));
        jTextFields.add(guiBuilder.addInput("archivalSys", inputValues));
        jTextFields.add(guiBuilder.addInput("date", inputValues));
        jTextFields.add(guiBuilder.addInput("versionNum", inputValues));
        jTextFields.add(guiBuilder.addInput("seqNumStart", inputValues));
        jTextFields.add(guiBuilder.addInput("seqNumEnd", inputValues));
        jTextFields.add(guiBuilder.addInput("maxFolderSizeGB", inputValues));
        jTextFields.add(guiBuilder.addInput("maxDataFiles", inputValues));
        jTextFields.add(guiBuilder.addInput("docId", inputValues));
        jTextFields.add(guiBuilder.addInput("typeOfMailing", inputValues));
        jTextFields.add(guiBuilder.addInput("title", inputValues));
        jTextFields.add(guiBuilder.addInput("jobId", inputValues));

        return jTextFields;
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

    static void addDocumentListenerForButtonEnabling(List<JTextField> textFields, JButton executeButton) {

        for (JTextField textField : textFields) {

            //enable button if all fields are filled
            boolean allFilled = textFields.stream()
                    .noneMatch(tf -> tf.getText().trim().isEmpty());
            executeButton.setEnabled(allFilled);

            textField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    toggleButton();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    toggleButton();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    toggleButton();
                }

                private void toggleButton() {
                    // Enable button only if all text fields are filled
                    boolean allFilled = textFields.stream()
                            .noneMatch(tf -> tf.getText().trim().isEmpty());
                    executeButton.setEnabled(allFilled);

                    if(textField.getText().trim().isEmpty()) {
                        textField.setBackground(Color.white);
                        //executeButton.putClientProperty("FlatLaf.style", "background: red;");
                    } else {
                        textField.setBackground(UIManager.getColor("TextField.background"));
                        //executeButton.putClientProperty("FlatLaf.style", "background: none;");
                    }
                }
            });
        }
    }
}
