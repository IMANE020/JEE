package com.nyHR.api_sb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.nyHR.api_sb.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
