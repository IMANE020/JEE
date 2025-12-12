package com.employees.webapp;

import com.employees.webapp.model.Employee;
import com.employees.webapp.proxy.EmployeeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Iterator;
import java.util.List;

@SpringBootApplication
public class WebappApplication {

	//hydih
	@Autowired
	private EmployeeProxy employeeProxy;

	public static void main(String[] args) {
		SpringApplication.run(WebappApplication.class, args);
	}

	//hydih
	@Bean
	CommandLineRunner run() {
		return args -> {
			Iterable<Employee> employees = employeeProxy.getEmployees();
			for (Employee e : employees) {
				System.out.println(e.getFirstName() + " " + e.getLastName());
			}
		};
	}

}
