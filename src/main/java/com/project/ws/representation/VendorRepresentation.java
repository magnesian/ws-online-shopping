package com.project.ws.representation;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class VendorRepresentation extends AbstractRepresentation {
	
	private Integer vendorId;
	private String vendorName;
	private String vendorCity;
	private String vendorState;
	private String vendorCountry;

	public VendorRepresentation() {
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorCity() {
		return vendorCity;
	}
	
	public void setVendorCity(String vendorCity) {
		this.vendorCity = vendorCity;
	}
	
	public String getVendorState() {
		return vendorState;
	}
	
	public void setVendorState(String vendorState) {
		this.vendorState = vendorState;
	}

	public String getVendorCountry() {
		return vendorCountry;
	}
	public void setVendorCountry(String vendorCountry) {
		this.vendorCountry = vendorCountry;
	}
	
	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	
}