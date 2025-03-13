package com.jwt.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "department")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @Column
    private String location;

}
