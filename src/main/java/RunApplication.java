import gui.IntegratorApplication;

import javax.swing.*;
import java.awt.*;

public class RunApplication {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Integrator");
        IntegratorApplication integratorApplication = new IntegratorApplication();
        setUpFrame(frame, integratorApplication);
    }
    private static void setUpFrame(JFrame frame, IntegratorApplication integratorApplication) {
        frame.setContentPane(integratorApplication.getIntegrator());
        frame.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame.pack();
        frame.setVisible(true);
    }
}
