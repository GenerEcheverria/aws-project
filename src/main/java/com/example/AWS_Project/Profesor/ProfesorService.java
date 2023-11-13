package com.example.AWS_Project.Profesor;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfesorService {
    private final ProfesorRepository profesorRepository;

    public List<Profesor> getAllProfesores() {
        return profesorRepository.findAll();
    }

    public Optional<Profesor> getProfesorById(int profesorId) {
        return profesorRepository.findById(profesorId);
    }

    public Profesor createProfesor(Profesor Profesor) {
        return profesorRepository.save(Profesor);
    }

    public Profesor updateProfesor(int profesorId, Profesor Profesor) {
        if (profesorRepository.existsById(profesorId)) {
            Profesor.setId(profesorId);
            return profesorRepository.update(Profesor);
        } else {
            return null;
        }
    }

    public boolean deleteProfesor(int profesorId) {
         if (profesorRepository.existsById(profesorId)) {
            return profesorRepository.deleteById(profesorId);
        } else {
            return false;
        }
    }
}
