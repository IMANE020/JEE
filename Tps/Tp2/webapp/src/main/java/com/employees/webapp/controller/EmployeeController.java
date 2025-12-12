package com.employees.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.employees.webapp.model.Employee;
import com.employees.webapp.service.EmployeeService;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    //page d acceuil liste des employes URL: http://localhost:9001/
    @GetMapping("/")
    public String home(Model model) {
        System.out.println("Affichage de la liste des employees");

        Iterable<Employee> employees = employeeService.getEmployees();
        model.addAttribute("employees", employees);
        return "home";
    }

    //formulaire pour creer un nvl employee
    @GetMapping("/createEmployee")
    public String createEmployeeForm(Model model) {
        System.out.println("Formulaire de creation des employees");

        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "formEmployee";
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute Employee employee) {
        System.out.println("Sauvegarde employee " + employee.getFirstName() + " " + employee.getLastName());
        employeeService.saveEmployee(employee);
        return "redirect:/";
    }

    @GetMapping("/updateEmployee/{id}")
    public String updateEmployee(@PathVariable int id, Model model) {
        System.out.println("Modification employee " + id);

        Employee employee = employeeService.getEmployees(id);
        model.addAttribute("employee", employee);
        return "formEmployee";
    }

    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable int id) {
        System.out.println("Delete employee " + id);
        employeeService.deleteEmployee(id);
        return "redirect:/";
    }
}
