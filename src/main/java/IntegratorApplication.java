import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class IntegratorApplication {
    private JPanel integrator;
    private JButton importButton;
    private JButton createDiagram;
    private JPanel diagram;
    private JProgressBar progressBar1;

    public IntegratorApplication() {
        importButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JOptionPane.showMessageDialog(null,"Import is successful.");
                createDiagram.setEnabled(true);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Integrator");
        IntegratorApplication integratorApplication = new IntegratorApplication();
        frame.setContentPane(integratorApplication.integrator);
        frame.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
