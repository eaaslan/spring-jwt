package com.jwt.demo.model.dto;

public record EmployeeDto(
        Long id,
        String firstName,
        String lastName,
        DepartmentDto department

) {


}
