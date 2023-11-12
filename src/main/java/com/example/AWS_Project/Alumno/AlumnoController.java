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
        System.out.println("controller");
        Optional<Alumno> alumno = alumnoService.getAlumnoById(id);
        if (alumno.isPresent()) {
            return new ResponseEntity<>(alumno, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Alumno not found", HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> createAlumno(@RequestBody Alumno alumno) {
        Alumno createdAlumno = alumnoService.createAlumno(alumno);
        return new ResponseEntity<>(createdAlumno, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json" )
    public ResponseEntity<Object> updateAlumno(@PathVariable("id") int id, @RequestBody Alumno alumno) {
        Alumno updatedAlumno = alumnoService.updateAlumno(id, alumno);
        if (updatedAlumno != null) {
            return new ResponseEntity<>(updatedAlumno, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Alumno not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Object> deleteAlumno(@PathVariable int id) {
        alumnoService.deleteAlumno(id);
        return new ResponseEntity<>("Alumno deleted successfully", HttpStatus.OK);
    }
}
