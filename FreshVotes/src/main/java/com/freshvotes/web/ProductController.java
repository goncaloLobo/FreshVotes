package com.freshvotes.web;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.repositories.ProductRepository;

@Controller
public class ProductController {

	@Autowired
	private ProductRepository productRepo;
	
	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public String getProducts(ModelMap model) {
		return "product";
	}
	
	@RequestMapping(value = "/products/{productId}", method = RequestMethod.GET)
	public String getProduct(@PathVariable Long productId, ModelMap model, HttpServletResponse response) throws IOException {
		Optional<Product> productOpt = productRepo.findById(productId);
		
		if (productOpt.isPresent()) {
			Product product = productOpt.get();
			model.put("product", product);
		} else {
			response.sendError(HttpStatus.NOT_FOUND.value(), "Product with id " + productId + " was not found.");
			return "product";
		}
		
		return "product";
	}
	
	// quando é feito post para o /products (create product)
	// é feito um redirect para o get products
	// @AuthenticationPrincipal injeta o user que fez login, mas tenho de ir à bd na mesma ir buscar o user
	// para verificar que estou com o user certo
	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public String createProduct(@AuthenticationPrincipal User user) {
		Product product = new Product();
		
		product.setPublished(false);
		product.setUser(user);
		
		product = productRepo.save(product);
		
		return "redirect:/products/"+ product.getId();
	}
}
