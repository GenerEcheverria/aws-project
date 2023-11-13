package com.example.AWS_Project.Alumno;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class Alumno {
    @Id
    @GeneratedValue
    private int id;
    @Basic
    private String nombres;
    private String apellidos;
    private String matricula;
    private double promedio;
}
