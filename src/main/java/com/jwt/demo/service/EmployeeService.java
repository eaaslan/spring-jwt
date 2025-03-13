package com.jwt.demo.service;

import com.jwt.demo.model.dto.DepartmentDto;
import com.jwt.demo.model.dto.EmployeeDto;
import com.jwt.demo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

   public EmployeeDto getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(employee -> new EmployeeDto(
                        employee.getId(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        new DepartmentDto(employee.getDepartment().getId(), employee.getDepartment().getLocation(), employee.getDepartment().getName())
                )).orElse(null);
    }


}
