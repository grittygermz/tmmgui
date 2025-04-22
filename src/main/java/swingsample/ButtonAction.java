package swingsample;

import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ButtonAction implements ActionListener {

    private final Container container;
    private final OutputArea outputArea;
    @Setter
    private String fileName;
    @Setter
    private String command;
    private final JButton button;

    public ButtonAction(Container container, OutputArea outputArea, JButton button) {
        this.container = container;
        this.outputArea = outputArea;
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        button.setEnabled(false);
        List<TextFieldLabelCombination> textFieldLabelCombinationList = new ArrayList<>();
        System.out.println("clicked");
        for (Component vertComponent : container.getComponents()) {
            if (vertComponent instanceof JPanel) {
                TextFieldLabelCombination textFieldLabelCombination = extractLabelAndValue((JPanel) vertComponent);
                textFieldLabelCombinationList.add(textFieldLabelCombination);
            }
        }
        createJobInputJsonFile(textFieldLabelCombinationList, fileName);

        ProcessExecutor.executeCommand(command, outputArea.jTextArea());
    }

    private TextFieldLabelCombination extractLabelAndValue(JPanel vertComponent) {
        TextFieldLabelCombination textFieldLabelCombination = new TextFieldLabelCombination();
        for (Component horizComponent : vertComponent.getComponents())
            if (horizComponent instanceof JLabel) {
                JLabel jLabel = (JLabel) horizComponent;
                String replace = jLabel.getText().replace(":", "");
                textFieldLabelCombination.setLabel(replace);
                System.out.println(replace);
            } else if (horizComponent instanceof JTextField) {
                JTextField jTextField = (JTextField) horizComponent;
                System.out.println(jTextField.getText());
                textFieldLabelCombination.setValue(jTextField.getText());
            }
        return textFieldLabelCombination;
    }

    private void createJobInputJsonFile(List<TextFieldLabelCombination> textFieldLabelCombinationList, String fileName) {
        Map<String, Object> jobInputMap = new HashMap<>();
        for (TextFieldLabelCombination textFieldLabelCombination : textFieldLabelCombinationList) {
            if (isNumericInput(textFieldLabelCombination.getLabel())) {
                jobInputMap.put(textFieldLabelCombination.getLabel(), Integer.parseInt(textFieldLabelCombination.getValue()));
            } else {
                jobInputMap.put(textFieldLabelCombination.getLabel(), textFieldLabelCombination.getValue());
            }
        }
        JsonHandler.writeJsonToFile(fileName, jobInputMap);
    }

    private boolean isNumericInput(String labelName) {
        List<String> numericLabels = List.of("versionNum", "seqNumStart", "seqNumEnd", "maxFolderSizeMB", "maxFolderSizeGB", "maxDataFiles");
        return numericLabels.contains(labelName);
    }
}
