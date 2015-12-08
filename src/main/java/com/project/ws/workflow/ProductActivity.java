package com.project.ws.workflow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.ws.domain.Cart;
import com.project.ws.domain.Link;
import com.project.ws.domain.Product;
import com.project.ws.domain.Vendor;
import com.project.ws.repository.CartRepository;
import com.project.ws.repository.ProductRepository;
import com.project.ws.repository.VendorRepository;
import com.project.ws.representation.CartRepresentation;
import com.project.ws.representation.CartRequest;
import com.project.ws.representation.ProductRepresentation;
import com.project.ws.representation.ProductRequest;
import com.project.ws.representation.StringRepresentation;

@Transactional
@Service
@Component
public class ProductActivity {

	private final ProductRepository prodRepo;
	private final VendorRepository vendorRepo;
	private final String baseUrl = "http://localhost:8080";
	private final String mediaType = "application/json";
	
	@Autowired
	Product product;
	
	@Autowired
	ProductRepresentation prodRepresentation;
	
	@Autowired
	CartRepresentation cartRepresentation;
	
	@Autowired
	public ProductActivity(ProductRepository prodRepo, VendorRepository vendorRepo) {
		this.prodRepo = prodRepo; 
		this.vendorRepo = vendorRepo;
	}
	
	public List<ProductRepresentation> allProducts() {
		List<Product> productList = prodRepo.findAll();
		List<ProductRepresentation> resultList = new ArrayList<ProductRepresentation>();
		Vendor vendor;
		String vendorName;
		for(Product p:productList) {
			vendor = vendorRepo.findOne(p.getVendorId());
			vendorName = vendor.getVendorName();
			resultList.add(mapProductRepresentation(p, vendorName, false));
		}
		return resultList;
	}
	
	public List<ProductRepresentation> findAllProductsByVendorId(Integer vendorId) {
		List<Product> productList = prodRepo.findByVendorId(vendorId);
		List<ProductRepresentation> resultList = new ArrayList<ProductRepresentation>();
		Vendor vendor;
		String vendorName;
		for(Product p:productList) {
			vendor = vendorRepo.findOne(p.getVendorId());
			vendorName = vendor.getVendorName();
			resultList.add(mapProductRepresentation(p, vendorName, true));
		}
		return resultList;
	}
	
	public List<ProductRepresentation> searchProduct(String name) {
		List<Product> productList = prodRepo.readByProductName(name);
		if(productList.isEmpty()) return null;
		List<ProductRepresentation> resultList = new ArrayList<ProductRepresentation>();
		Vendor vendor;
		String vendorName;
		for(Product p:productList) {
			System.out.println(p.getProductId() + p.getName() + p.getPrice());
			vendor = vendorRepo.findOne(p.getVendorId());
			vendorName = vendor.getVendorName();
			resultList.add(mapProductRepresentation(p, vendorName, false));
		}
		return resultList;
	}
	
	public ProductRepresentation addProduct(ProductRequest productRequest) {
		mapRequest(productRequest);
		Integer count = prodRepo.addProduct(product);
		Vendor vendor = new Vendor();
		String vendorName = "";
		if(count == 1) {
			vendor = vendorRepo.findOne(productRequest.getVendorId());
			vendorName = vendor.getVendorName();
		}
		return mapProductRepresentation(product, vendorName, true);
	}

	public StringRepresentation updateProduct(ProductRequest productRequest) {
		StringRepresentation stringRepresentation = new StringRepresentation();
		Integer count = prodRepo.updateProductPrice(productRequest.getProductId(), productRequest.getPrice());
		Integer count1 = prodRepo.updateProductQuantity(productRequest.getProductId(), productRequest.getQuantity(), "add");
		if(count == 1 && count1 == 1)
			stringRepresentation.setMessage("Product updated Successfully");
		else 
			stringRepresentation.setMessage("Error Updating Product");
		setLinks(stringRepresentation, productRequest.getVendorId());
		return stringRepresentation;
	}
	
	public StringRepresentation deleteProduct(Integer productId) {
		StringRepresentation stringRepresentation = new StringRepresentation();
		Integer count = prodRepo.deleteProduct(productId);
		if(count == 1)
			stringRepresentation.setMessage("Product deleted Successfully");
		else 
			stringRepresentation.setMessage("Error Deleting Product");
		return stringRepresentation;
	}
	
	public Boolean validateProduct(Integer productId) {
		Product p = prodRepo.findOne(productId);
		if(p == null)
			return false;
		else
			return true;
	}
	
	public Product mapRequest(ProductRequest request) {
		product = new Product();
		product.setName(request.getProductName());
		product.setPrice(request.getPrice());
		product.setQuantity(request.getQuantity());
		product.setVendorId(request.getVendorId());
		product.setType(request.getProductType());
		product.setDescription(request.getProductDescription());
		return product;
	}
	
	public ProductRepresentation mapProductRepresentation(Product product, String vendorName, boolean showLinksForVendor) {
		prodRepresentation = new ProductRepresentation();
		prodRepresentation.setProductName(product.getName());
		prodRepresentation.setPrice(product.getPrice());
		prodRepresentation.setQuantity(product.getQuantity());
		prodRepresentation.setVendorName(vendorName);
		prodRepresentation.setProductType(product.getType());
		prodRepresentation.setProductId(product.getProductId());
		if (showLinksForVendor) {
			setLinksForVendor(prodRepresentation);
		} else {
			setLinksForCustomer(prodRepresentation);
		}
		return prodRepresentation;
	}

	private void setLinks(StringRepresentation stringRepresentation, Integer vendorId) {
		Link showVendorProducts = new Link("get", baseUrl + "/products/vendor?vendorId=" + vendorId, "showVendorProducts", mediaType);
		stringRepresentation.setLinks(showVendorProducts);
	}
	
	private void setLinksForCustomer(ProductRepresentation prodRepresentation) {
		Link addCart = new Link("post", baseUrl + "/cart/add", "addCart", mediaType);
		Link reviewsToShow = new Link("get", baseUrl + "/review/view?productId=" + prodRepresentation.getProductId(), "showReviews", mediaType);
		Link reviewToAdd = new Link("post", baseUrl + "/review/add", "addReview", mediaType);
		prodRepresentation.setLinks(addCart, reviewsToShow, reviewToAdd);
	}
	
	private void setLinksForVendor(ProductRepresentation prodRepresentation) {
		Link addProduct = new Link("post", baseUrl + "/product/add", "addProduct", mediaType);
		Link reviewsToShow = new Link("get", baseUrl + "/review/view?productId=" + prodRepresentation.getProductId(), "showReviews", mediaType);
		Link deleteProduct = new Link("delete", baseUrl + "/product/delete?productId=" + prodRepresentation.getProductId(), "deleteProduct", mediaType);
		Link updateProduct = new Link("put", baseUrl + "/product?productId=" + prodRepresentation.getProductId(), "updateProduct", mediaType);
		prodRepresentation.setLinks(addProduct, deleteProduct, updateProduct, reviewsToShow);
	}
}
