package com.example.AWS_Project.Profesor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class ProfesorRepository {
    private final List<Profesor> profesores = new ArrayList<>();

    public List<Profesor> findAll() {
        return new ArrayList<>(profesores);
    }

    public Optional<Profesor> findById(int id) {
        return profesores.stream().filter(profesor -> profesor.getId() == id).findFirst();
    }

    public Profesor save(Profesor profesor) {
        // if (profesores.size() > 0) {
        //     Profesor.setId(profesores.get(profesores.size() - 1).getId() + 1);
        // } else {
        //     Profesor.setId(1);
        // }
        profesores.add(profesor);
        return profesor;
    }
 
    public Profesor update(Profesor updatedProfesor) {
        int index = -1;
        for (int i = 0; i < profesores.size(); i++) {
            if (profesores.get(i).getId() == updatedProfesor.getId()) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            profesores.set(index, updatedProfesor);
        }
        return updatedProfesor;
    }

    public boolean deleteById(int id) {
       return profesores.removeIf(profesor -> profesor.getId() == id);
    }

    public boolean existsById(int id) {
        return profesores.stream().anyMatch(profesor -> profesor.getId() == id);
    }

}
