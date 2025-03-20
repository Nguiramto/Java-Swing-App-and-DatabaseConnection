package org.example;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import com.mysql.cj.jdbc.MysqlDataSource;

public class Main extends JFrame {
    private static Connection conn;

    public Main() {
        setTitle("Restaurant Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        // Initialize Database Connection
        conn = getDatabaseConnection();

        ActivityLogger logger = new ActivityLogger();
        OrderController controller = new OrderController(logger, conn); // Pass DB connection to controllers

        JTabbedPane tabbedPane = new JTabbedPane();

        MenuPanel menuPanel = new MenuPanel(controller);
        OrderPanel orderPanel = new OrderPanel(controller);
        TransactionPanel transactionPanel = new TransactionPanel(controller);
        ActivityPanel activityPanel = new ActivityPanel(logger);
        RevenuePanel revenuePanel = new RevenuePanel(controller);

        tabbedPane.addTab("Menu", menuPanel);
        tabbedPane.addTab("Orders", orderPanel);
        tabbedPane.addTab("Transaction", transactionPanel);
        tabbedPane.addTab("Activities", activityPanel);
        tabbedPane.addTab("Revenue", revenuePanel);

        // Refresh respective panels when selected
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            if (selectedIndex == 1) orderPanel.refreshTable();
            if (selectedIndex == 2) transactionPanel.refreshTransactionTable();
            if (selectedIndex == 3) activityPanel.refreshActivityLog();
            if (selectedIndex == 4) revenuePanel.refreshRevenueData();
        });

        add(tabbedPane);
    }

    // Updated method to use MysqlDataSource for MySQL Connector/J 9.x
    private static Connection getDatabaseConnection() {
        try {
            // Initialize MysqlDataSource
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setURL("jdbc:mysql://localhost:3306/restaurantmanagement");  // Change database name, host, port if necessary
            dataSource.setUser("root");  // Set your database username
            dataSource.setPassword("#FOcus2710##");  // Set your database password

            // Try to get the connection
            conn = dataSource.getConnection();

            // If successful, show a success message
            JOptionPane.showMessageDialog(null, "Connection to database was successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            return conn;

        } catch (SQLException e) {
            // If connection fails, show an error message
            JOptionPane.showMessageDialog(null, "Failed to connect to the database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
