package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.example.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/employee")
@Slf4j
public class EmployeeController {
    @Produce(uri = "direct:employee-direct")
    private ProducerTemplate producerTemplate;

    @GetMapping("/health")
    public ResponseEntity<String> getHealth(){
        log.info("Health success");
        return ResponseEntity.ok("Success");
    }
    @GetMapping("/{id}")
    public ResponseEntity<Employee> findEmployeeById(@PathVariable(value = "id") int id) {
        Employee employee = Employee.builder().id(id).build();
        producerTemplate.asyncSendBody(producerTemplate.getDefaultEndpoint(), employee);
        log.info("message sent successfully....");
        return ResponseEntity.ok().body(employee);
    }
}
