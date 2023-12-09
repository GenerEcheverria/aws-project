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
        Profesor existingProfesor = profesorRepository.findById(profesorId).orElse(null);
        if (existingProfesor != null) {
            existingProfesor.setNumeroEmpleado(Profesor.getNumeroEmpleado());
            existingProfesor.setNombres(Profesor.getNombres());
            existingProfesor.setApellidos(Profesor.getApellidos());
            existingProfesor.setHorasClase(Profesor.getHorasClase());
            return profesorRepository.save(existingProfesor);
        } else {
            return null;
        }
    }

    public boolean deleteProfesor(int profesorId) {
        if (profesorRepository.existsById(profesorId)) {
            profesorRepository.deleteById(profesorId);
            return true;
        } else {
            return false;
        }
    }
}
