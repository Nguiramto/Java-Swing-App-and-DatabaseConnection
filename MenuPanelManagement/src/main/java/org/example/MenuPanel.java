package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MenuPanel extends JPanel {

    private JTable menuTable;
    private DefaultTableModel tableModel;
    private JButton confirmOrderButton;
    private OrderController orderController;

    // Menu items
    private Object[][] menuItems = {
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

    public MenuPanel(OrderController controller) {
        this.orderController = controller;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Menu of Foods and Drinks", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        String[] columns = {"Select", "Item", "Price (UGX)"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Boolean.class;
                if (columnIndex == 2) return Integer.class;
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // Only checkbox column is editable
            }
        };

        // Add menu items to the table
        for (Object[] item : menuItems) {
            tableModel.addRow(new Object[]{false, item[0], item[1]});
        }

        menuTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(menuTable);
        add(scrollPane, BorderLayout.CENTER);

        // Confirm order button
        confirmOrderButton = new JButton("Add to Order");
        confirmOrderButton.addActionListener(this::confirmOrder);
        add(confirmOrderButton, BorderLayout.SOUTH);
    }

    /**
     * Action listener to handle order confirmation
     */
    private void confirmOrder(ActionEvent e) {
        boolean hasSelection = false;

        // Loop through rows to collect selected items
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            boolean selected = (Boolean) tableModel.getValueAt(i, 0);
            if (selected) {
                hasSelection = true;
                String itemName = (String) tableModel.getValueAt(i, 1);
                int price = (Integer) tableModel.getValueAt(i, 2);
                orderController.addOrderItem(new OrderItem(itemName, price, 1));
            }
        }

        // Show message only if at least one item was selected
        if (hasSelection) {
            JOptionPane.showMessageDialog(this, "Items added to your order! Check Order Tab to review.", "Order Added", JOptionPane.INFORMATION_MESSAGE);
            resetMenuSelections(); // Reset checkboxes to initial state
        } else {
            JOptionPane.showMessageDialog(this, "Please select at least one item to add to order.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Reset all checkboxes to unselected state (false)
     */
    private void resetMenuSelections() {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            tableModel.setValueAt(false, i, 0); // Uncheck checkbox
        }
    }
}
