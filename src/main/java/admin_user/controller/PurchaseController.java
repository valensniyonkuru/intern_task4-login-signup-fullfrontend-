package admin_user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import admin_user.dto.ProductDTO;
import admin_user.dto.PurchaseDTO;
import admin_user.dto.SupplierDTO;
import admin_user.service.ProductService;
import admin_user.service.PurchaseService;
import admin_user.service.SupplierService;

@Controller
@RequestMapping("/admin/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private SupplierService supplierService;

    // Display list of purchases
    @GetMapping
    public String listPurchases(Model model) {
        List<PurchaseDTO> purchases = purchaseService.getAllPurchases();
        model.addAttribute("purchases", purchases);
        return "purchase_list";  // Corresponds to purchase_list.html
    }

    // Show form to add a new purchase
    @GetMapping("/new")
    public String showAddPurchaseForm(Model model) {
        List<ProductDTO> products = productService.getAllProducts();
        List<SupplierDTO> suppliers = supplierService.getAllSuppliers();
        model.addAttribute("purchase", new PurchaseDTO());
        model.addAttribute("products", products);
        model.addAttribute("suppliers", suppliers);
        return "purchase_form";  // Corresponds to purchase_form.html
    }

    // Handle the form submission for creating a new purchase
    @PostMapping
    public String addPurchase(@ModelAttribute PurchaseDTO purchaseDTO) {
        purchaseService.createPurchase(purchaseDTO);
        return "redirect:/admin/purchases";
    }

    // Show form to edit an existing purchase
    @GetMapping("/edit/{id}")
    public String showEditPurchaseForm(@PathVariable Long id, Model model) {
        PurchaseDTO purchase = purchaseService.getPurchaseById(id);
        List<ProductDTO> products = productService.getAllProducts();
        List<SupplierDTO> suppliers = supplierService.getAllSuppliers();
        model.addAttribute("purchase", purchase);
        model.addAttribute("products", products);
        model.addAttribute("suppliers", suppliers);
        return "purchase_form";  // Corresponds to purchase_form.html
    }

    // Handle the form submission for updating a purchase
    @PostMapping("/update/{id}")
    public String updatePurchase(@PathVariable Long id, @ModelAttribute PurchaseDTO purchaseDTO) {
        purchaseService.updatePurchase(id, purchaseDTO);
        return "redirect:/admin/purchases";
    }

    // Delete a purchase
    @GetMapping("/delete/{id}")
    public String deletePurchase(@PathVariable Long id) {
        purchaseService.deletePurchase(id);
        return "redirect:/admin/purchases";
    }

    // Search purchases
    @GetMapping("/search")
    public String searchPurchases(@RequestParam String keyword, Model model) {
        List<PurchaseDTO> purchases = purchaseService.searchPurchases(keyword);
        model.addAttribute("purchases", purchases);
        return "purchase_list";  // Corresponds to purchase_list.html
    }
}
