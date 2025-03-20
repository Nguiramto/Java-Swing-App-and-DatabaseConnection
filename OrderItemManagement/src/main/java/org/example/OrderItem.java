package org.example;

/**
 * Represents a single item that a customer can order in the restaurant.
 */
public class OrderItem {
    private String name;
    private int price;
    private int quantity;

    public OrderItem(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
        }
    }

    public int getTotalPrice() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return name + " x" + quantity + " (" + getTotalPrice() + " UGX)";
    }
}
