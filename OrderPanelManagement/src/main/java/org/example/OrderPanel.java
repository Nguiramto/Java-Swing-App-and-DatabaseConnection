package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class OrderPanel extends JPanel {

    private JTable orderTable;
    private DefaultTableModel tableModel;
    private JButton deleteButton, updateButton, submitButton;
    private OrderController orderController;

    // Menu items for updating (assuming these are the same as in MenuPanel)
    private final String[] menuItems = {
            "Rice + Sauce Chicken", "Rice + Sauce Meat", "Piece of Fried Chicken",
            "All Fried Chicken", "Small Coca", "Medium Coca", "Big Coca",
            "Orange Juice", "Mango Juice", "Banana Juice", "Mix Juice (Orange + Mango + Banana)"
    };

    public OrderPanel(OrderController controller) {
        this.orderController = controller;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Your Orders", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        String[] columns = {"Item", "Unit Price", "Quantity", "Total"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        orderTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(orderTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        deleteButton = new JButton("Delete Selected");
        updateButton = new JButton("Edit Item / Quantity");
        submitButton = new JButton("Submit Order");

        deleteButton.addActionListener(this::deleteOrder);
        updateButton.addActionListener(this::updateOrderItem);
        submitButton.addActionListener(this::submitOrder);

        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(submitButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Refresh the order table
    public void refreshTable() {
        tableModel.setRowCount(0); // Clear table
        for (OrderItem item : orderController.getCurrentOrder()) {
            tableModel.addRow(new Object[]{
                    item.getName(),
                    item.getPrice(),
                    item.getQuantity(),
                    item.getTotalPrice()
            });
        }
    }

    // Delete selected order item
    private void deleteOrder(ActionEvent e) {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow != -1) {
            orderController.removeOrderItem(selectedRow);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Update order item (change name and quantity)
    private void updateOrderItem(ActionEvent e) {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow != -1) {
            // Step 1: Select new item (optional)
            String currentItemName = (String) tableModel.getValueAt(selectedRow, 0);
            String newItemName = (String) JOptionPane.showInputDialog(this, "Select new item (or same):",
                    "Edit Item", JOptionPane.PLAIN_MESSAGE, null, menuItems, currentItemName);

            if (newItemName == null) return; // User cancelled

            // Step 2: Enter new quantity
            String quantityStr = JOptionPane.showInputDialog(this, "Enter quantity:");
            try {
                int quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(this, "Quantity must be greater than zero.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Step 3: Find price for new item (you may want to map names to prices more dynamically)
                int price = findPriceForItem(newItemName);
                if (price == -1) {
                    JOptionPane.showMessageDialog(this, "Item not found in menu.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Step 4: Update the order item
                OrderItem updatedItem = new OrderItem(newItemName, price, quantity);
                orderController.updateOrderItem(selectedRow, updatedItem);
                refreshTable();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity entered.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to update.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Submit order and finalize transaction
    private void submitOrder(ActionEvent e) {
        List<OrderItem> currentOrder = orderController.getCurrentOrder();

        if (currentOrder.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No items to submit!", "Empty Order", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Do you want to pay now?", "Payment Confirmation", JOptionPane.YES_NO_OPTION);
        boolean isPaid = (confirm == JOptionPane.YES_OPTION);

        orderController.finalizeOrder(isPaid); // This will add to transactions
        JOptionPane.showMessageDialog(this, "Order submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        refreshTable(); // Clear the order table since order is submitted
    }

    // Helper method to find price for item name
    private int findPriceForItem(String itemName) {
        Object[][] menuItems = {
                {"Rice + Sauce Chicken", 10000},
                {"Rice + Sauce Meat", 10000},
                {"Piece of Fried Chicken", 5000},
                {"All Fried Chicken", 25000},
                {"Small Coca", 1000},
                {"Medium Coca", 1500},
                {"Big Coca", 5000},
                {"Orange Juice", 5000},
                {"Mango Juice", 5000},
                {"Banana Juice", 5000},
                {"Mix Juice (Orange + Mango + Banana)", 5000}
        };
        for (Object[] item : menuItems) {
            if (item[0].equals(itemName)) return (int) item[1];
        }
        return -1; // Not found
    }
}
