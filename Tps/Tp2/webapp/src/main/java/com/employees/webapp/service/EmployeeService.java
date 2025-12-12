package com.employees.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employees.webapp.model.Employee;
import com.employees.webapp.proxy.EmployeeProxy;

import lombok.Data;

@Data
@Service
public class EmployeeService {

    @Autowired
    private EmployeeProxy employeeProxy;

    public Iterable<Employee> getEmployees() {
        return employeeProxy.getEmployees();
    }

    public Employee getEmployees(final int id) {
        return employeeProxy.getEmployee(id);
    }

    public Iterable<Employee> getEmployee() {
        return employeeProxy.getEmployees();
    }

    public void deleteEmployee(final int id) {
        employeeProxy.deleteEmployee(id);
    }

    public Employee saveEmployee(final Employee employee) {
        Employee savedEmployee;

        employee.setLastName(employee.getLastName().toUpperCase());

        if(employee.getId() == null) {
            savedEmployee = employeeProxy.createEmployee(employee);
        }else {
            savedEmployee = employeeProxy.updateEmployee(employee);
        }

        return savedEmployee;
    }


}
