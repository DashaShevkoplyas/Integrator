import entities.RepertoryGrid;
import entities.grid.Grid;
import importer.PreprocessRGInterview;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IntegratorApplication {
    private static final String FILE_PATH = "C:\\";
    private JPanel integrator;
    private JButton importFileButton;
    private JButton createDiagram;
    private JPanel diagram;
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
            Grid proceededRG = selectRGFileAndReturnProcessed().getGrid();
            if (proceededRG == null) {
                createDiagram.setEnabled(false);
                JOptionPane.showMessageDialog(null, "Import error: RG is null", "Error", JOptionPane.ERROR_MESSAGE);
            }else {
                JOptionPane.showMessageDialog(null, "Successfully imported!", "Success", JOptionPane.INFORMATION_MESSAGE);
                createDiagram.setEnabled(true);
            }
        } else messageLabel.setText("File was not selected!");

    }

    private RepertoryGrid selectRGFileAndReturnProcessed() {
        File selectedFile = openFile.getSelectedFile();
        messageLabel.setText("Selected file: " + selectedFile.getName());
        PreprocessRGInterview preprocessRGInterview = new PreprocessRGInterview();
        return preprocessRGInterview.preprocessRG(selectedFile, createDiagram);
    }

    private void setUpFileChooser() {
        openFile = new JFileChooser();
        openFile.setCurrentDirectory(new File(FILE_PATH));
        openFile.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
    }

    private void createDiagramButtonProcessor() {
        createDiagram.addActionListener(this::displayGraph);
    }

    private void displayGraph(ActionEvent e){
        try {
            BufferedImage image = ImageIO.read(new File("/download.png"));
            JLabel label = new JLabel(new ImageIcon(image));
            diagram.add(label);
        }catch (IOException ex){
            JOptionPane.showMessageDialog(null, "Unable to load image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
