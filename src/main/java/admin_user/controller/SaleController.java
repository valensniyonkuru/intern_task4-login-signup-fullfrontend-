package admin_user.controller;

import admin_user.dto.SaleDTO;
import admin_user.dto.ProductDTO;
import admin_user.dto.CustomerDTO;
import admin_user.service.SaleService;
import admin_user.service.ProductService;
import admin_user.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/sales")
public class SaleController {

	@Autowired
	private SaleService saleService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerService customerService;

	// Display list of sales
	@GetMapping
	public String listSales(Model model) {
	    List<SaleDTO> sales = saleService.getAllSales();
	    model.addAttribute("sales", sales);
	    return "sale_list";  // Corresponds to sale_list.html
	}

	// Show form to add a new sale
	@GetMapping("/new")
	public String showAddSaleForm(Model model) {
	    List<ProductDTO> products = productService.getAllProducts();
	    List<CustomerDTO> customers = customerService.getAllCustomers();
	    model.addAttribute("sale", new SaleDTO());
	    model.addAttribute("products", products);
	    model.addAttribute("customers", customers);
	    return "sale_form";  // Corresponds to sale_form.html
	}

	// Handle the form submission for creating a new sale
	@PostMapping
	public String addSale(@ModelAttribute SaleDTO saleDTO) {
	    saleService.createSale(saleDTO);
	    return "redirect:/admin/sales";
	}

	// Show form to edit an existing sale
	@GetMapping("/edit/{id}")
	public String showEditSaleForm(@PathVariable Long id, Model model) {
	    SaleDTO sale = saleService.getSaleById(id);
	    List<ProductDTO> products = productService.getAllProducts();
	    List<CustomerDTO> customers = customerService.getAllCustomers();
	    model.addAttribute("sale", sale);
	    model.addAttribute("products", products);
	    model.addAttribute("customers", customers);
	    return "sale_form";  // Corresponds to sale_form.html
	}

	// Handle the form submission for updating a sale
	@PostMapping("/update/{id}")
	public String updateSale(@PathVariable Long id, @ModelAttribute SaleDTO saleDTO) {
	    saleService.updateSale(id, saleDTO);
	    return "redirect:/admin/sales";
	}

	// Delete a sale
	@GetMapping("/delete/{id}")
	public String deleteSale(@PathVariable Long id) {
	    saleService.deleteSale(id);
	    return "redirect:/admin/sales";
	}

	// Search sales
	@GetMapping("/search")
	public String searchSales(@RequestParam String keyword, Model model) {
	    List<SaleDTO> sales = saleService.searchSales(keyword);
	    model.addAttribute("sales", sales);
	    return "sale_list";  // Corresponds to sale_list.html
	}}