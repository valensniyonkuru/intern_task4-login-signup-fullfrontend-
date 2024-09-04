package admin_user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import admin_user.dto.SaleDTO;
import admin_user.model.Customer;
import admin_user.model.Product;
import admin_user.model.Sale;
import admin_user.repositories.CustomerRepository;
import admin_user.repositories.ProductRepository;
import admin_user.repositories.SaleRepository;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // Get all sales
    public List<SaleDTO> getAllSales() {
        return saleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get a sale by ID
    public SaleDTO getSaleById(Long id) {
        Optional<Sale> sale = saleRepository.findById(id);
        return sale.map(this::convertToDTO).orElse(null);
    }

    // Create a new sale
    public SaleDTO createSale(SaleDTO saleDTO) {
        Sale sale = convertToEntity(saleDTO);
        Sale savedSale = saleRepository.save(sale);
        return convertToDTO(savedSale);
    }

    // Update a sale
    public SaleDTO updateSale(Long id, SaleDTO saleDTO) {
        Optional<Sale> saleOpt = saleRepository.findById(id);
        if (saleOpt.isPresent()) {
            Sale sale = saleOpt.get();
            sale.setDate(saleDTO.getDate());
            sale.setQuantity(saleDTO.getQuantity());

            if (saleDTO.getProductId() != null) {
                Optional<Product> product = productRepository.findById(saleDTO.getProductId());
                product.ifPresent(sale::setProduct);
            }

            if (saleDTO.getCustomerId() != null) {
                Optional<Customer> customer = customerRepository.findById(saleDTO.getCustomerId());
                customer.ifPresent(sale::setCustomer);
            }

            Sale updatedSale = saleRepository.save(sale);
            return convertToDTO(updatedSale);
        }
        return null;
    }

    // Delete a sale
    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }

    // Search sales by product name keyword
    public List<SaleDTO> searchSales(String keyword) {
        return saleRepository.findAll().stream()
                .filter(sale -> sale.getProduct() != null && sale.getProduct().getName().toLowerCase().contains(keyword.toLowerCase()))
                .map(sale -> new SaleDTO(
                        sale.getId(),
                        sale.getDate(),
                        sale.getQuantity(),
                        sale.getProduct() != null ? sale.getProduct().getId() : null,
                        sale.getCustomer() != null ? sale.getCustomer().getId() : null,
                        sale.getTotalPrice() // Ensure this matches the new SaleDTO constructor
                ))
                .collect(Collectors.toList());
    }

    // Convert Entity to DTO
    private SaleDTO convertToDTO(Sale sale) {
        return new SaleDTO(
                sale.getId(),
                sale.getDate(),
                sale.getQuantity(),
                sale.getProduct() != null ? sale.getProduct().getId() : null,
                sale.getCustomer() != null ? sale.getCustomer().getId() : null,
                sale.getTotalPrice() // Ensure this matches the new SaleDTO constructor
        );
    }

    // Convert DTO to Entity
    private Sale convertToEntity(SaleDTO saleDTO) {
        Sale sale = new Sale();
        sale.setDate(saleDTO.getDate());
        sale.setQuantity(saleDTO.getQuantity());

        if (saleDTO.getProductId() != null) {
            Optional<Product> product = productRepository.findById(saleDTO.getProductId());
            product.ifPresent(sale::setProduct);
        }

        if (saleDTO.getCustomerId() != null) {
            Optional<Customer> customer = customerRepository.findById(saleDTO.getCustomerId());
            customer.ifPresent(sale::setCustomer);
        }

        return sale;
    }
}
