package UI;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

class ManagerPanel extends JPanel {
    public ManagerPanel() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Manager Panel");
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
