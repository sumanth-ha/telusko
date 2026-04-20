package com.sumanth.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sumanth.Entity.Customer;

public interface ICustomerRepo extends JpaRepository<Customer, Integer> {

}
