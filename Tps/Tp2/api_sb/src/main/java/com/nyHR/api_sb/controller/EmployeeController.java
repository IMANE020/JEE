package com.nyHR.api_sb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.nyHR.api_sb.model.Employee;
import com.nyHR.api_sb.service.EmployeeService;

@RestController
//@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // GET all employees
    @GetMapping("/employees")
    public Iterable<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    // GET employee by id
    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable("id") final Long id) {
        return employeeService.getEmployee(id).orElse(null);
    }

    // CREATE new employee
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    // UPDATE employee
    @PutMapping("/employees/{id}")
    public Employee updateEmployee(@PathVariable("id") final Long id, @RequestBody Employee employee) {
        employee.setId(id);
        return employeeService.saveEmployee(employee);
    }

    // DELETE employee
    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable("id") final Long id) {
        employeeService.deleteEmployee(id);
    }

}
