package com.project.ws.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.project.ws.domain.Product;
import com.project.ws.representation.ProductRepresentation;
import com.project.ws.representation.ProductRequest;
import com.project.ws.representation.StringRepresentation;
import com.project.ws.workflow.ProductActivity;
import com.project.ws.workflow.VendorActivity;

@RestController
public class ProductController {
	
	@Autowired
    private ProductActivity productActivity;
	
	@Autowired
    private VendorActivity vendorActivity;
	
	/*
	 * GET to retrieve all product details
	 */
	@RequestMapping(value="/products", method=RequestMethod.GET)
    public List<ProductRepresentation> getAllProducts(HttpServletRequest request) {
		List<ProductRepresentation> productRepresentations = new ArrayList<ProductRepresentation>();
			productRepresentations = productActivity.allProducts();
    	return productRepresentations;
    }
	
	/*
	 * GET to search a product using name
	 */
	@RequestMapping(value="/product", method=RequestMethod.GET, params="name")
    public List<ProductRepresentation> readByProductName(HttpServletRequest request) {
		List<ProductRepresentation> productRepresentations = new ArrayList<ProductRepresentation>();
			String productName = request.getParameter("name");
			productRepresentations = productActivity.searchProduct(productName);
			if(productRepresentations == null) {
				throw new ProductNotFoundException(productName);
			}
    	return productRepresentations;
    }
	
	/*
	 * GET to search all products for a particular vendor id 
	 */
	@RequestMapping(value="/products/vendor", method=RequestMethod.GET, params="vendorId")
    public List<ProductRepresentation> viewAllProductsForVendor(HttpServletRequest request) {
		List<ProductRepresentation> productRepresentations = new ArrayList<ProductRepresentation>();
			String vendorId = request.getParameter("vendorId");
			productRepresentations = productActivity.findAllProductsByVendorId(Integer.parseInt(vendorId));
			if(productRepresentations == null) {
				throw new VendorNotFoundException(vendorId);
			}
    	return productRepresentations;
    }
	
	/*
	 * POST to add a new product
	 */
	@RequestMapping(value="/product/add", method=RequestMethod.POST)
    public StringRepresentation addProduct(@RequestBody ProductRequest productRequest) {
		StringRepresentation stringRepresentation = new StringRepresentation();
		if(vendorActivity.validateVendor(productRequest.getVendorId()) == false)
			throw new VendorNotFoundException(productRequest.getVendorId());
		stringRepresentation = productActivity.addProduct(productRequest);
		return stringRepresentation;
    }

	/*
	 * Update a product
	 */
	@RequestMapping(value="/product", method=RequestMethod.PUT)
    public StringRepresentation updateProduct(@RequestBody ProductRequest productRequest) {
		StringRepresentation stringRepresentation = new StringRepresentation();
		if(productActivity.validateProduct(productRequest.getProductId()) == false) 
			throw new ProductNotFoundException(productRequest.getProductId());
		stringRepresentation = productActivity.updateProduct(productRequest);
		return stringRepresentation;
    }
	
	/*
	 * DELETE to delete a product
	 */
	@RequestMapping(value="/product/delete", method=RequestMethod.DELETE, params="productId")
    public StringRepresentation deleteProduct(HttpServletRequest request) {
		StringRepresentation stringRepresentation = new StringRepresentation();
		Integer productId = Integer.parseInt(request.getParameter("productId"));
		if(productActivity.validateProduct(productId) == false) 
			throw new ProductNotFoundException(productId);
		stringRepresentation = productActivity.deleteProduct(productId);
		return stringRepresentation;
    }

}

@ResponseStatus(HttpStatus.NOT_FOUND)
class ProductNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1357644244197546536L;

	public ProductNotFoundException(Object product) {
		super("Could not find product - " + product);
	}
}
