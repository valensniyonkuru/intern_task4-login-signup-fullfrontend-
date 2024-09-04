package admin_user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import admin_user.dto.PurchaseDTO;
import admin_user.model.Product;
import admin_user.model.Purchase;
import admin_user.model.Supplier;
import admin_user.repositories.ProductRepository;
import admin_user.repositories.PurchaseRepository;
import admin_user.repositories.SupplierRepository;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    // Get all purchases
    public List<PurchaseDTO> getAllPurchases() {
        return purchaseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get a purchase by ID
    public PurchaseDTO getPurchaseById(Long id) {
        Optional<Purchase> purchase = purchaseRepository.findById(id);
        return purchase.map(this::convertToDTO).orElse(null);
    }

    // Create a new purchase
    public PurchaseDTO createPurchase(PurchaseDTO purchaseDTO) {
        Purchase purchase = convertToEntity(purchaseDTO);
        Purchase savedPurchase = purchaseRepository.save(purchase);
        return convertToDTO(savedPurchase);
    }

    // Update a purchase
    public PurchaseDTO updatePurchase(Long id, PurchaseDTO purchaseDTO) {
        Optional<Purchase> purchaseOpt = purchaseRepository.findById(id);
        if (purchaseOpt.isPresent()) {
            Purchase purchase = purchaseOpt.get();
            purchase.setDate(purchaseDTO.getDate());
            purchase.setQuantity(purchaseDTO.getQuantity());

            if (purchaseDTO.getProductId() != null) {
                Optional<Product> product = productRepository.findById(purchaseDTO.getProductId());
                product.ifPresent(purchase::setProduct);
            }

            if (purchaseDTO.getSupplierId() != null) {
                Optional<Supplier> supplier = supplierRepository.findById(purchaseDTO.getSupplierId());
                supplier.ifPresent(purchase::setSupplier);
            }

            Purchase updatedPurchase = purchaseRepository.save(purchase);
            return convertToDTO(updatedPurchase);
        }
        return null;
    }

    // Delete a purchase
    public void deletePurchase(Long id) {
        purchaseRepository.deleteById(id);
    }

    // Search purchases by keyword in product name
    public List<PurchaseDTO> searchPurchases(String keyword) {
        return purchaseRepository.findAll().stream()
                .filter(purchase -> purchase.getProduct() != null &&
                        purchase.getProduct().getName().toLowerCase().contains(keyword.toLowerCase()))
                .map(purchase -> new PurchaseDTO(purchase.getId(), purchase.getDate(), purchase.getQuantity(),
                        purchase.getProduct() != null ? purchase.getProduct().getId() : null,
                        purchase.getSupplier() != null ? purchase.getSupplier().getId() : null))
                .collect(Collectors.toList());
    }

    // Convert Entity to DTO
    private PurchaseDTO convertToDTO(Purchase purchase) {
        return new PurchaseDTO(purchase.getId(), purchase.getDate(), purchase.getQuantity(),
                purchase.getProduct() != null ? purchase.getProduct().getId() : null,
                purchase.getSupplier() != null ? purchase.getSupplier().getId() : null);
    }

    // Convert DTO to Entity
    private Purchase convertToEntity(PurchaseDTO purchaseDTO) {
        Purchase purchase = new Purchase();
        purchase.setDate(purchaseDTO.getDate());
        purchase.setQuantity(purchaseDTO.getQuantity());

        if (purchaseDTO.getProductId() != null) {
            Optional<Product> product = productRepository.findById(purchaseDTO.getProductId());
            product.ifPresent(purchase::setProduct);
        }

        if (purchaseDTO.getSupplierId() != null) {
            Optional<Supplier> supplier = supplierRepository.findById(purchaseDTO.getSupplierId());
            supplier.ifPresent(purchase::setSupplier);
        }

        return purchase;
    }
}
