package com.ht.airline.db.repository;

import com.ht.airline.db.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

  Customer findByEmail(String email);

  Customer findByEmailAndPwdHash(String email, String pwdHash);

  Customer findByEmailAndAdmin(String email, Boolean admin);
}
