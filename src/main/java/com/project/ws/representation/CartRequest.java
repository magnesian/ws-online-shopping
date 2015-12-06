package com.project.ws.representation;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class CartRequest {

	private Integer customerId;
	private Integer productId;
	private Integer quantity;
	
	public CartRequest() {}
	
	//setters
	
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
//	public void setPrice(Double price) {
//		this.price = price;
//	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	//getters
	
	public Integer getCustomerId() {
		return this.customerId;
	}
	
	public Integer getProductId() {
		return this.productId;
	}
	
//	public Double getPrice() {
//		return this.price;
//	}
	
	public Integer getQuantity() {
		return this.quantity;
	}
	
	@Override
	public String toString() {
		return this.customerId + "-" + this.productId + "-" + "-" + this.quantity;
	}
}
