package com.sumanth.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sumanth.Entity.Customer;
import com.sumanth.Repo.ICustomerRepo;

@Service
public class CustomerService implements ICustomerService {

	@Autowired
	private ICustomerRepo repo;

	@Override
	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public void saveCustomer(Customer customer) {
		repo.save(customer);

	}

	@Override
	public void deleteCustomer(Integer id) {
		repo.deleteById(id);

	}

	@Override
	public Customer getCustomerByid(Integer id) {
		return repo.getById(id);
	}

}
