package com.example.AWS_Project.Alumno;

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
@RequestMapping("/alumnos")
@RequiredArgsConstructor
public class AlumnoController {
    private final AlumnoService alumnoService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Alumno>> getAllAlumnos() {
        List<Alumno> alumnos = alumnoService.getAllAlumnos();
        return new ResponseEntity<>(alumnos, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Object> getAlumnoById(@PathVariable("id") int id) {
        try {

            Optional<Alumno> alumno = alumnoService.getAlumnoById(id);
            if (alumno.isPresent()) {
                return new ResponseEntity<>(alumno, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Alumno not found", HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> createAlumno( @RequestBody Alumno alumno) {
        try {

            if (validateAlumno(alumno)) {
                Alumno createdAlumno = alumnoService.createAlumno(alumno);
                return new ResponseEntity<>(createdAlumno, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Can not create Alumno", HttpStatus.BAD_REQUEST);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json" )
    public ResponseEntity<Object> updateAlumno(@PathVariable("id") int id, @RequestBody Alumno alumno) {
        try {

            if (!validateAlumno(alumno)) {
                return new ResponseEntity<>("Can not update Alumno", HttpStatus.BAD_REQUEST);
            }
            Alumno updatedAlumno = alumnoService.updateAlumno(id, alumno);
            if (updatedAlumno != null) {
                return new ResponseEntity<>(updatedAlumno, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Alumno not found", HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Object> deleteAlumno(@PathVariable int id) {
        try {

            if(alumnoService.deleteAlumno(id)) {
                return new ResponseEntity<>("Alumno deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Alumno not found", HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean validateAlumno(Alumno alumno) {
        if (alumno == null) {
            return false;
        }
        if (alumno.getNombres() == null || alumno.getNombres().isEmpty()) {
            return false;
        }
        if (alumno.getApellidos() == null || alumno.getApellidos().isEmpty()) {
            return false;
        }
        if (alumno.getMatricula() == null || alumno.getMatricula().isEmpty()) {
            return false;
        }
        if (alumno.getPromedio() < 0) {
            return false;
        }
        return true;
    }    
}
