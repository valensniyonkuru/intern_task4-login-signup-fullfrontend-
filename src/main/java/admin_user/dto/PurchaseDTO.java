package admin_user.dto;

import java.time.LocalDate;

public class PurchaseDTO {

    private Long id;
    private LocalDate date;
    private int quantity;
    private Long productId;
    private Long supplierId;

    // Default constructor
    public PurchaseDTO() {
    }

    // Parameterized constructor
    public PurchaseDTO(Long id, LocalDate date, int quantity, Long productId, Long supplierId) {
        this.id = id;
        this.date = date;
        this.quantity = quantity;
        this.productId = productId;
        this.supplierId = supplierId;
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

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
}
