package org.example;

import javax.swing.*;
import java.awt.*;

public class ActivityPanel extends JPanel {

    private JTextArea activityTextArea;
    private ActivityLogger logger;

    public ActivityPanel(ActivityLogger logger) {
        this.logger = logger;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("System Activity Log", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        activityTextArea = new JTextArea();
        activityTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(activityTextArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Refresh the activity log display
    public void refreshActivityLog() {
        StringBuilder logBuilder = new StringBuilder();
        for (String log : logger.getActivityLog()) {
            logBuilder.append(log).append("\n");
        }
        activityTextArea.setText(logBuilder.toString());
    }
}
