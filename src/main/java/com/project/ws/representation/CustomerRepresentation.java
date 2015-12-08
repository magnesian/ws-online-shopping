package com.project.ws.representation;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.stereotype.Component;

@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class CustomerRepresentation extends AbstractRepresentation {

	private Integer custId;
	private String custFirstname;
	private String custLastName;
	private String custEmail;
	private String message;
	private String activeFlag;
	
	public CustomerRepresentation() {
	}

	
	public String getCustFirstname() {
		return custFirstname;
	}
	
	public void setCustFirstname(String custFirstname) {
		this.custFirstname = custFirstname;
	}
	
	public String getCustLastName() {
		return custLastName;
	}
	public void setCustLastName(String custLastName) {
		this.custLastName = custLastName;
	}
	
	public String getCustEmail() {
		return custEmail;
	}
	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}
	
	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
	
	public String getActiveFlag() {
		return activeFlag;
	}


	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}


	@Override
	public String toString() {
		return this.custId + "-" + this.custFirstname + "-" + this.custLastName + "-" + this.custEmail;
	}
	
}
