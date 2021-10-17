
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.RepertoryGrid;
import entities.grid.elements.Element;
import org.json.JSONObject;
import org.json.XML;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class IntegratorApplication {
    private static final String FILE_PATH = "C:\\RG";
    private JPanel integrator;
    private JButton importFileButton;
    private JButton createDiagram;
    private JPanel diagram;
    private JProgressBar progressBar1;
    private JLabel messageLabel;
    private JTextField textField;
    private JFileChooser openFile;

    public IntegratorApplication() {
        importFileButtonProcessor();
        createDiagramButtonProcessor();
    }

    public JPanel getIntegrator() {
        return integrator;
    }

    private void importFileButtonProcessor() {
        importFileButton.addActionListener(this::importFile);
    }

    private void importFile(ActionEvent e) {
        setUpFileChooser();
        int returnValue = openFile.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = openFile.getSelectedFile();
            messageLabel.setText("Selected file: " + selectedFile.getName());
            preprocessImport(selectedFile);
            createDiagram.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Import error.", "Error", JOptionPane.ERROR_MESSAGE);
            messageLabel.setText("File was not selected!");
        }
    }

    private void setUpFileChooser() {
        openFile = new JFileChooser();
        openFile.setCurrentDirectory(new File(FILE_PATH));
        openFile.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
    }

    private void preprocessImport(File file) {
        int character;
        StringBuilder builder = new StringBuilder();
        try {
            FileInputStream inputStream = new FileInputStream(file);
            while ((character = inputStream.read()) != -1) {
                builder.append((char) character);
            }
            inputStream.close();

            List<String> elementIds = mapToRepertoryGridClass(builder.toString()).getGrid().getElements().getElements().stream().map(Element::getContent).collect(Collectors.toList());
            textField.setText(elementIds.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(null, "Successfully imported!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private RepertoryGrid mapToRepertoryGridClass(String xmlData) {
        try {
            ObjectMapper objectMapper = configureObjectMapper();
            return objectMapper.readValue(covertXmlToJson(xmlData), RepertoryGrid.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    //it's easier to preprocess json file
    private String covertXmlToJson(String xmlData) {
        JSONObject jsonObject = XML.toJSONObject(xmlData);
        return jsonObject.toString(4);
    }

    private ObjectMapper configureObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    private void createDiagramButtonProcessor() {
        createDiagram.addActionListener(e -> {});
    }
}
