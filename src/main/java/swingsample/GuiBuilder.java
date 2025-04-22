package swingsample;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.lang.reflect.Field;

public class GuiBuilder {
    private Container container;

    public GuiBuilder(Container container) {
        this.container = container;
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
    }

    public JButton createButton(String buttonName, int width, int height) {
        JButton jButton = new JButton(buttonName);
        jButton.setSize(width, height);
        return jButton;
    }

    public void addComponent(Component component) {
        container.add(component);
    }

    private JPanel createHorizontalJPanel() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
        jPanel.setBorder(new EmptyBorder(10, 5, 5, 10));
        return jPanel;
    }

    public Container addInput(String label, InputValues inputValues) {
        JPanel horizontalJPanel = createHorizontalJPanel();
        JTextField jTextField = getjTextField(label, inputValues);
        JLabel jLabel = getjLabel(label, jTextField);

        horizontalJPanel.add(jLabel);
        horizontalJPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        horizontalJPanel.add(jTextField);

        container.add(horizontalJPanel);
        return container;
    }

    private static JLabel getjLabel(String label, JTextField jTextField) {
        JLabel jLabel = new JLabel(label + ":");
        jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        jLabel.setLabelFor(jTextField);
        return jLabel;
    }

    private static JTextField getjTextField(String label, InputValues inputValues) {
        JTextField jTextField = populateDefaultTextValueIfExists(label, inputValues);
        jTextField.setEditable(true);
        jTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, jTextField.getPreferredSize().height));
        jTextField.setHorizontalAlignment(JTextField.LEFT);
        return jTextField;
    }

    private static JTextField populateDefaultTextValueIfExists(String label, InputValues inputValues) {
        JTextField jTextField = new JTextField(15);
        if (inputValues != null) {
            Field[] fields = inputValues.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals(label)) {
                    try {
                        Field declaredField = inputValues.getClass().getDeclaredField(label);
                        declaredField.setAccessible(true);
                        Object fieldValue = declaredField.get(inputValues);
                        String finalFieldValue;
                        if (declaredField.get(inputValues) instanceof Integer) {
                            finalFieldValue = String.valueOf(fieldValue);
                        } else {
                            finalFieldValue = (String)fieldValue;
                        }
                        jTextField = new JTextField(finalFieldValue, 15);
                    } catch (IllegalAccessException | NoSuchFieldException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
        return jTextField;
    }
}
