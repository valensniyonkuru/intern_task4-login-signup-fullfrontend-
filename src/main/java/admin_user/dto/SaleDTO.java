package admin_user.dto;

import java.time.LocalDate;

public class SaleDTO {
    private Long id;
    private LocalDate date;
    private int quantity;
    private Long productId;
    private Long customerId;
    private double totalPrice;

    // Default constructor
    public SaleDTO() {
    }

    // Constructor with all fields
    public SaleDTO(Long id, LocalDate date, int quantity, Long productId, Long customerId, double totalPrice) {
        this.id = id;
        this.date = date;
        this.quantity = quantity;
        this.productId = productId;
        this.customerId = customerId;
        this.totalPrice = totalPrice;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
