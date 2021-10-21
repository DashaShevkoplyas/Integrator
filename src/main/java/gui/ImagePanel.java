package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel {
    private static final String PATH = "C:\\Users\\shevk\\IdeaProjects\\Integrator\\src\\main\\resources\\images\\";
    private BufferedImage image;

    public ImagePanel() {
        getImage();
    }

    private void getImage() {
        try {
            image = ImageIO.read(new File(PATH + "download.png"));
            setBorder(BorderFactory.createLineBorder(Color.black, 2));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Unable to load image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
}
