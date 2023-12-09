package com.example.AWS_Project.Alumno;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Integer>{
    // private final List<Alumno> alumnos = new ArrayList<>();

    // public List<Alumno> findAll() {
    //     return new ArrayList<>(alumnos);
    // }

    // public Optional<Alumno> findById(int id) {
    //     return alumnos.stream().filter(alumno -> alumno.getId() == id).findFirst();
    // }

    // public Alumno save(Alumno alumno) {
    //     alumnos.add(alumno);
    //     return alumno;
    // }
 
    // public Alumno update(Alumno updatedAlumno) {
    //     int index = -1;
    //     for (int i = 0; i < alumnos.size(); i++) {
    //         if (alumnos.get(i).getId() == updatedAlumno.getId()) {
    //             index = i;
    //             break;
    //         }
    //     }
    //     if (index != -1) {
    //         alumnos.set(index, updatedAlumno);
    //     }
    //     return updatedAlumno;
    // }

    // public boolean deleteById(int id) {
    //    return alumnos.removeIf(alumno -> alumno.getId() == id);
    // }

    // public boolean existsById(int id) {
    //     return alumnos.stream().anyMatch(alumno -> alumno.getId() == id);
    // }

}
