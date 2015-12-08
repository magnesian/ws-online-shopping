package com.project.ws.representation;

import org.springframework.stereotype.Component;

@Component
public class ProductRequest {

	private Integer productId;
	private String productName;
	private String productDescription;
	private String productType;
	private Integer quantity;
	private Double price;
	private Integer vendorId;
	
	public ProductRequest() {
	}
	
	//setters

	
	
	@Override
	public String toString() {
		return "[" + this.productName + "-" + this.productDescription + "-" + this.productType + "-" +
				this.quantity + "-" + this.price + "]";
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

}
