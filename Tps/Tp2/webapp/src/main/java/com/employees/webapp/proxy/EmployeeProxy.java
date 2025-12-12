package com.employees.webapp.proxy;

import com.employees.webapp.Configuration.CustomProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.employees.webapp.model.Employee;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// indique que cette classe est un bean Spring
@Component
public class EmployeeProxy {

    //recupere l'objet CustomProperties cree pour obtenir l'URL d api
    @Autowired
    private CustomProperties props;

    // recupere tous les employees
    public Iterable<Employee> getEmployees() {
        String baseApiUrl = props.getApiUrl();
        String getEmployeeUrl = baseApiUrl + "/employees";

        System.out.println("EmployeeProxy*getEmployees getEmployeeUrl: " + getEmployeeUrl);

        //la classe Spring qui permet de faire des requetes HTTP
        RestTemplate restTemplate = new RestTemplate();

        //exchange() envoie une requete GET vers l api /employees et recupere la reponse
        ResponseEntity<Iterable<Employee>> response = restTemplate.exchange(
                getEmployeeUrl,
                HttpMethod.GET,
                null,

                //la reponse sera une liste
                new ParameterizedTypeReference<Iterable<Employee>>() {}
        );
        System.out.println("EmployeeProxy*getEmployees getEmployees: " + response.getBody());

        //afficher dans la console le code de statut HTTP
        log.debug("Get Employees call" + response.getStatusCode().toString());
        return response.getBody();
    }

    //recupere les employees par id
    public Employee getEmployee(final int id) {
        String baseApiUrl = props.getApiUrl();
        String getEmployeeUrl = baseApiUrl + "/employees/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Employee> response = restTemplate.exchange(
                getEmployeeUrl,
                HttpMethod.GET,
                null,
                Employee.class
        );

        log.debug("Get Employees call" + response.getStatusCode().toString());
        return response.getBody();
    }

    //supprimer par id
    public void deleteEmployee(final int id) {
        String baseApiUrl = props.getApiUrl();
        String deleteEmployeeUrl = baseApiUrl + "/employees/" + id;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(
                deleteEmployeeUrl,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        log.debug("Delete Employees call" + id);
    }

    //cree nvl employee
    public Employee createEmployee(final Employee employee) {
        String baseApiUrl = props.getApiUrl();
        String createEmployeeUrl = baseApiUrl + "/employees";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Employee> response = restTemplate.exchange(
                createEmployeeUrl,
                HttpMethod.POST,
                new HttpEntity<>(employee),
                Employee.class
        );
        log.debug("Create Employees call" + response.getStatusCode().toString());
        return response.getBody();
    }

    //modifier un employee
    public Employee updateEmployee(final Employee employee) {
        String baseApiUrl = props.getApiUrl();

        //verification d exesitance d empl
        if (employee.getId() == null) {
            log.error("Cannot update employee with null ID");
            throw new IllegalArgumentException("Employee ID cannot be null for update");
        }
        String updateEmployeeUrl = baseApiUrl + "/employees/" + employee.getId();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Employee> response = restTemplate.exchange(
                updateEmployeeUrl,
                HttpMethod.PUT,
                new HttpEntity<>(employee),
                Employee.class
        );
        log.debug("Update Employees call" + response.getStatusCode().toString());
        return response.getBody();
    }

}
