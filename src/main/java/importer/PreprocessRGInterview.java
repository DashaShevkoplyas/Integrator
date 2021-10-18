package importer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.RepertoryGrid;
import org.json.JSONObject;
import org.json.XML;

import javax.swing.*;
import java.io.*;

public class PreprocessRGInterview {

    public RepertoryGrid preprocessRG(File file, JButton createDiagram) {
        return readFromJson(file, createDiagram);
    }

    private RepertoryGrid readFromJson(File file, JButton createDiagram) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = covertXmlToJson(readFromFile(file, createDiagram));
        try {
            return objectMapper.readValue(jsonData, RepertoryGrid.class);
        } catch (JsonProcessingException e) {
            createDiagram.setEnabled(false);
            JOptionPane.showMessageDialog(null, "Import error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    //it's easier to preprocess json file
    private String covertXmlToJson(String xmlData) {
        JSONObject jsonObject = XML.toJSONObject(xmlData);
        return jsonObject.toString(4);
    }

    private String readFromFile(File file, JButton createDiagram) {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            createDiagram.setEnabled(false);
            JOptionPane.showMessageDialog(null, "Import error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return resultStringBuilder.toString();
    }
}
