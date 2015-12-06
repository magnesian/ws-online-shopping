package com.project.ws.representation;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class CartRepresentation extends AbstractRepresentation {

	private Integer productId;
	
	private String productName;
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	private Integer quantity;
	
	private Double price;
	
	public CartRepresentation() {
		
	}
	
	//setters
	
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	//getters
		
	public Integer getProductId() {
		return this.productId;
	}
	
	public Integer getQuantity() {
		return this.quantity;
	}
	
	public Double getPrice() {
		return this.price;
	}
}
