package com.example.AWS_Project.Alumno;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlumnoService {
    private final AlumnoRepository alumnoRepository;

    public List<Alumno> getAllAlumnos() {
        return alumnoRepository.findAll();
    }

    public Optional<Alumno> getAlumnoById(int alumnoId) {
        return alumnoRepository.findById(alumnoId);
    }

    public Alumno createAlumno(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    public Alumno updateAlumno(int alumnoId, Alumno alumno) {
        if (alumnoRepository.existsById(alumnoId)) {
            alumno.setId(alumnoId);
            return alumnoRepository.update(alumno);
        } else {
            return null;
        }
    }

    public boolean deleteAlumno(int alumnoId) {
         if (alumnoRepository.existsById(alumnoId)) {
            return alumnoRepository.deleteById(alumnoId);
        } else {
            return false;
        }
    }
}
