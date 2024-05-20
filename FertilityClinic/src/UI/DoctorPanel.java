package UI;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class DoctorPanel extends JPanel {
    public DoctorPanel() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Doctor Panel");
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, BorderLayout.CENTER);
    }
}

