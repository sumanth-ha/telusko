package com.sumanth.Service;

import java.util.List;

import com.sumanth.Entity.Customer;

public interface ICustomerService {
	public List<Customer> getAllCustomers();

	public void saveCustomer(Customer customer);

	public void deleteCustomer(Integer id);

	public Customer getCustomerByid(Integer id);
}
