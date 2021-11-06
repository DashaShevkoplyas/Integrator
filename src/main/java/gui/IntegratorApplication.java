package gui;

import elicitation.ElicitVariableElements;
import entities.grid.Grid;
import importer.PreprocessRGInterview;
import translation.TranslateToOWL;
import utils.Constants;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;

public class IntegratorApplication {
    private static final String FILE_PATH = "C:\\";

    private JPanel integrator;
    private JButton importFileButton;
    private JButton createFODADescription;
    private JPanel descriptionPanel;
    private JLabel messageLabel;
    private JTextArea fodaDescription;
    private JFileChooser openFile;
    private Grid grid;

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
            grid = selectRGFileAndReturnProcessed();
            if (grid == null) {
                createFODADescription.setEnabled(false);
                JOptionPane.showMessageDialog(null, "Import error: RG is null", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Successfully imported!", "Success", JOptionPane.INFORMATION_MESSAGE);
                createFODADescription.setEnabled(true);
            }
        } else messageLabel.setText("File was not selected!");
    }

    private Grid selectRGFileAndReturnProcessed() {
        File selectedFile = openFile.getSelectedFile();
        messageLabel.setText("Selected file: " + selectedFile.getName());
        PreprocessRGInterview preprocessRGInterview = new PreprocessRGInterview();
        return preprocessRGInterview.preprocessRG(selectedFile, createFODADescription).getGrid();
    }

    private void setUpFileChooser() {
        openFile = new JFileChooser();
        openFile.setCurrentDirectory(new File(FILE_PATH));
        openFile.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
    }

    private void createDiagramButtonProcessor() {
        createFODADescription.addActionListener(this::getElicitedVariableElementsAndDisplayGraph);
    }

    private void getElicitedVariableElementsAndDisplayGraph(ActionEvent e) {
        String fodaModelDescription = translateToOWLBasedOnVariableElements();
        fodaDescription.setText(fodaModelDescription);
        fodaDescription.update(fodaDescription.getGraphics());
        addScrollToTextArea();

        JOptionPane.showMessageDialog(null, "Ontology is created and saved into " + Constants.FODA_MODEL_DESCRIPTION_OUTPUT_PATH,
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private String translateToOWLBasedOnVariableElements() {
        ElicitVariableElements elicitVariableElements = new ElicitVariableElements(grid);
        TranslateToOWL owl = new TranslateToOWL();
        return owl.translateToOWL(grid.getHeader().getTopic(), elicitVariableElements.elicitVariableElementsAndConstructs());
    }

    private void addScrollToTextArea() {
        JScrollPane scroll = new JScrollPane(fodaDescription, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        descriptionPanel.add(scroll);
    }
}
