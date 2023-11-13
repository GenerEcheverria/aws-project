package com.example.AWS_Project.Profesor;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/profesores")
@RequiredArgsConstructor
public class ProfesorController {
    private final ProfesorService ProfesorService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Profesor>> getAllProfesores() {
        List<Profesor> Profesors = ProfesorService.getAllProfesores();
        return new ResponseEntity<>(Profesors, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Object> getProfesorById(@PathVariable("id") int id) {
        try {

            Optional<Profesor> Profesor = ProfesorService.getProfesorById(id);
            if (Profesor.isPresent()) {
                return new ResponseEntity<>(Profesor, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Profesor not found", HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> createProfesor(@RequestBody Profesor Profesor) {
        try {
            if (validateProfesor(Profesor)) {
                Profesor createdProfesor = ProfesorService.createProfesor(Profesor);
                return new ResponseEntity<>(createdProfesor, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Can not create Profesor", HttpStatus.BAD_REQUEST);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> updateProfesor(@PathVariable("id") int id, @RequestBody Profesor Profesor) {
        try {

            if (!validateProfesor(Profesor)) {
                return new ResponseEntity<>("Can not update Profesor", HttpStatus.BAD_REQUEST);
            }
            Profesor updatedProfesor = ProfesorService.updateProfesor(id, Profesor);
            if (updatedProfesor != null) {
                return new ResponseEntity<>(updatedProfesor, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Profesor not found", HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Object> deleteProfesor(@PathVariable int id) {
        try {

            if (ProfesorService.deleteProfesor(id)) {
                return new ResponseEntity<>("Profesor deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Profesor not found", HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean validateProfesor(Profesor Profesor) {
        if (Profesor == null) {
            return false;
        }
        if (Profesor.getNombres() == null || Profesor.getNombres().isEmpty()) {
            return false;
        }
        if (Profesor.getApellidos() == null || Profesor.getApellidos().isEmpty()) {
            return false;
        }
        if (Profesor.getNumeroEmpleado() < 0) {
            return false;
        }
        if (Profesor.getHorasClase() < 0) {
            return false;
        }
        return true;
    }
}
