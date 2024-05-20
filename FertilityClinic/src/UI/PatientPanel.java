package UI;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

class PatientPanel extends JPanel {
    public PatientPanel() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Patient Panel");
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
