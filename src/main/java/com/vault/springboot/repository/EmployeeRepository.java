package com.vault.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vault.springboot.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
