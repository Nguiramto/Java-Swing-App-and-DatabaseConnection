package org.example;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Controls the current order, transactions, and logs activities.
 */
public class OrderController {

    private List<OrderItem> currentOrder = new ArrayList<>();
    private List<OrderTransaction> transactions = new ArrayList<>();
    private ActivityLogger logger;

    // Constructor
    public OrderController(ActivityLogger logger, Connection conn) {
        this.logger = logger;
    }

    /**
     * Add a new item to the current order.
     */
    public void addOrderItem(OrderItem item) {
        currentOrder.add(item);
        logger.log("Added to order: " + item.toString());
    }

    /**
     * Remove an item from the current order by index.
     */
    public void removeOrderItem(int index) {
        if (index >= 0 && index < currentOrder.size()) {
            OrderItem removed = currentOrder.remove(index);
            logger.log("Removed from order: " + removed.toString());
        }
    }

    /**
     * Clear all items in the current order.
     */
    public void clearOrder() {
        currentOrder.clear();
        logger.log("Order cleared.");
    }

    /**
     * Finalize the current order, create a transaction, and clear the order.
     */
    public void finalizeOrder(boolean isPaid) {
        int total = currentOrder.stream().mapToInt(OrderItem::getTotalPrice).sum();
        OrderTransaction transaction = new OrderTransaction(new ArrayList<>(currentOrder), isPaid, total);
        transactions.add(transaction);
        logger.log("Order submitted. Total: " + total + " UGX. " + (isPaid ? "Paid." : "Pending Payment."));
        clearOrder(); // Clear the order after submission
    }

    /**
     * Get current order items.
     */
    public List<OrderItem> getCurrentOrder() {
        return currentOrder;
    }

    /**
     * Get all submitted transactions.
     */
    public List<OrderTransaction> getTransactions() {
        return transactions;
    }

    /**
     * Update an existing order item at a given index.
     * Replaces the existing item with the new one (could be different item and quantity).
     */
    public void updateOrderItem(int index, OrderItem updatedItem) {
        if (index >= 0 && index < currentOrder.size()) {
            OrderItem oldItem = currentOrder.set(index, updatedItem);
            logger.log("Updated order item from: " + oldItem.toString() + " to: " + updatedItem.toString());
        } else {
            logger.log("Attempted to update invalid order item index: " + index);
        }
    }

    /**
     * Get the logger associated with this OrderController.
     *
     * @return the logger
     */
    public ActivityLogger getLogger() {
        return logger;
    }
}
