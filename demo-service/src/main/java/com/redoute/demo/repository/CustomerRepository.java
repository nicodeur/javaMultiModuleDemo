package com.redoute.demo.repository;

import org.springframework.stereotype.Repository;

import com.redoute.demo.entity.Customer;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long>{

}
