package com.project.ws.repository.custom;

import java.util.List;

import com.project.ws.domain.Customer;

/**
 * This is a custom repository that will be used in conjunction with spring crud repository.
 * In this custom repository, we specified custom methods to fetch customer information 
 * based on custom queries.
 */
public interface CustomerCustomRepository {
	
	public List<Customer> getCustomersByNamesFirstLetter(String letter);
	public Integer addCustomer(String firstName, String lastName, String email, String password);
	public Integer updateName(Integer customerId, String firstName, String lastName);
	public Integer updateEmail(Integer customerId, String email);
	public Integer updateCustomer(Integer customerId, String firstName, String lastName, String email);
	public Integer updatePassword(Integer customerId, String password);
	public Integer deleteCustomer(Integer customerId);

}
