package org.example;

import java.util.List;

public class OrderTransaction {
    private List<OrderItem> items;
    private boolean isPaid;
    private int totalPrice;
    private String paymentMethod; // NEW: To store how the order was paid (Cash, Mobile Money, etc.)

    public OrderTransaction(List<OrderItem> items, boolean isPaid, int totalPrice) {
        this.items = items;
        this.isPaid = isPaid;
        this.totalPrice = totalPrice;
        this.paymentMethod = "Not Paid"; // Default until payment is made
    }

    // Getters
    public List<OrderItem> getItems() {
        return items;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    // Add new method getTotalAmount() as an alias for getTotalPrice()
    public int getTotalAmount() {
        return totalPrice;  // Returns the total price (same as getTotalPrice)
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    // Setters
    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "OrderTransaction{" +
                "items=" + items +
                ", isPaid=" + isPaid +
                ", totalPrice=" + totalPrice +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}
