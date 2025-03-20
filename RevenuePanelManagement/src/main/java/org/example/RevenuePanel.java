package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RevenuePanel extends JPanel {

    private JTable revenueTable;
    private DefaultTableModel tableModel;
    private JLabel totalRevenueLabel, unpaidLabel;
    private OrderController orderController;

    public RevenuePanel(OrderController controller) {
        this.orderController = controller;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Daily Revenue Summary", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        String[] columns = {"Order Items", "Total Amount (UGX)", "Status (Paid/Unpaid)"};
        tableModel = new DefaultTableModel(columns, 0);
        revenueTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(revenueTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new GridLayout(2, 1));
        totalRevenueLabel = new JLabel("Total Revenue: 0 UGX", JLabel.RIGHT);
        unpaidLabel = new JLabel("Unpaid Amount: 0 UGX", JLabel.RIGHT);
        totalRevenueLabel.setFont(new Font("Arial", Font.BOLD, 18));
        unpaidLabel.setFont(new Font("Arial", Font.BOLD, 16));

        footerPanel.add(totalRevenueLabel);
        footerPanel.add(unpaidLabel);
        add(footerPanel, BorderLayout.SOUTH);
    }

    // Refresh table and summaries
    public void refreshRevenueData() {
        tableModel.setRowCount(0);
        int totalRevenue = 0;
        int unpaidAmount = 0;

        for (OrderTransaction transaction : orderController.getTransactions()) {
            StringBuilder itemList = new StringBuilder();
            for (OrderItem item : transaction.getItems()) {
                itemList.append(item.getName()).append(" x").append(item.getQuantity()).append(", ");
            }
            String status = transaction.isPaid() ? "Paid" : "Unpaid";
            if (transaction.isPaid()) totalRevenue += transaction.getTotalAmount();
            else unpaidAmount += transaction.getTotalAmount();

            tableModel.addRow(new Object[]{itemList.toString(), transaction.getTotalAmount(), status});
        }

        totalRevenueLabel.setText("Total Revenue: " + totalRevenue + " UGX");
        unpaidLabel.setText("Unpaid Amount: " + unpaidAmount + " UGX");
    }
}
