package gui;

import elicitation.ElicitVariableElements;
import entities.grid.Grid;
import importer.PreprocessRGInterview;
import ontologyProcessing.CreateOntology;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class IntegratorApplication {
    private static final String FILE_PATH = "C:\\";
    private JPanel integrator;
    private JButton importFileButton;
    private JButton createDiagram;
    private JPanel diagram;
    private JLabel messageLabel;
    private JTextField textField;
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
                createDiagram.setEnabled(false);
                JOptionPane.showMessageDialog(null, "Import error: RG is null", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Successfully imported!", "Success", JOptionPane.INFORMATION_MESSAGE);
                createDiagram.setEnabled(true);
            }
        } else messageLabel.setText("File was not selected!");
    }

    private Grid selectRGFileAndReturnProcessed() {
        File selectedFile = openFile.getSelectedFile();
        messageLabel.setText("Selected file: " + selectedFile.getName());
        PreprocessRGInterview preprocessRGInterview = new PreprocessRGInterview();
        return preprocessRGInterview.preprocessRG(selectedFile, createDiagram).getGrid();
    }

    private void setUpFileChooser() {
        openFile = new JFileChooser();
        openFile.setCurrentDirectory(new File(FILE_PATH));
        openFile.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
    }

    private void createDiagramButtonProcessor() {
        createDiagram.addActionListener(this::getElicitedMapAndDisplayGraph);
    }

    private void getElicitedMapAndDisplayGraph(ActionEvent e) {
        ElicitVariableElements elicitVariableElements = new ElicitVariableElements(grid);
        System.out.println(elicitVariableElements.elicitVariableElementsAndConstructs().values());
        displayGraph(e);
        CreateOntology ontology = new CreateOntology();
        ontology.createOntology();
    }

    private void displayGraph(ActionEvent e) {
        ImagePanel imagePanel = new ImagePanel();
        diagram.add(imagePanel, BorderLayout.CENTER);
        diagram.revalidate();
        diagram.repaint();
    }
}
