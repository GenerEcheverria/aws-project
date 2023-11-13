package com.example.AWS_Project.Profesor;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class Profesor {
    @Id
    @GeneratedValue
    private int id;
    @Basic
    private int numeroEmpleado;
    private String nombres;
    private String apellidos;
    private int horasClase;
}
