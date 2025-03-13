package com.jwt.demo.controller;

import com.jwt.demo.model.dto.EmployeeDto;
import com.jwt.demo.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDto getEmployeeById (@PathVariable(value = "employeeId") Long id, String location, String name) {
        return employeeService.getEmployeeById(id);
    }
}
