package com.nyHR.api_sb.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nyHR.api_sb.model.Employee;
import com.nyHR.api_sb.repository.EmployeeRepository;
import lombok.Data;

@Data
@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    public Optional<Employee> getEmployee(final Long id) {
        return employeeRepository.findById(id);
    }

    public Iterable<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Employee deleteEmployee(final Long id) {
        employeeRepository.deleteById(id);
        return null;
    }
    public Employee saveEmployee(final Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        return savedEmployee;
    }

}
