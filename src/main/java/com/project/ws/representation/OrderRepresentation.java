package com.project.ws.representation;

import java.util.Date;
import java.util.List;



import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class OrderRepresentation extends AbstractRepresentation {

	private Integer orderId;
	private Date orderDate;
	private Date orderDeliveryDate;
	private Double orderAmount;
	private String orderShipMethod;
	private String orderStatus;
	private List<OrderLineRepresentation> lineItems;

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public List<OrderLineRepresentation> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<OrderLineRepresentation> lineItems) {
		this.lineItems = lineItems;
	}
	
	public OrderRepresentation() {
		
	}
	
	//setters
	
	public void setOrderDate(Date date) {
		this.orderDate = date;
	}
	
	public void setOrderDeliveryDate(Date date) {
		this.orderDeliveryDate = date;
	}
	
	public void setOrderShipMethod(String shipMethod) {
		this.orderShipMethod = shipMethod;
	}
	
	public void setOrderAmount(Double amount) {
		this.orderAmount = amount;
	}
		
	public void setOrderStatus(String status) {
		this.orderStatus =status;
	}

	
	//getters
	
	public Integer getOrderId() {
		return this.orderId;
	}

	public Date getOrderDate() {
		return this.orderDate;
	}
	
	public Date getOrderDeliveryDate() {
		return this.orderDeliveryDate;
	}
	
	public String getOrderShipMethod() {
		return this.orderShipMethod;
	}
	
	public Double getOrderAmount() {
		return this.orderAmount;
	}
	
	public String getOrderStatus() {
		return this.orderStatus;
	}
	
	@Override
	public String toString() {
		return this.orderId + "-" + this.orderShipMethod + "-" + this.orderStatus + "-" + this.orderAmount;
	}
}
