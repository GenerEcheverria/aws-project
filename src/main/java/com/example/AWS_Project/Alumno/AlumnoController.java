package com.example.AWS_Project.Alumno;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/alumnos")
@RequiredArgsConstructor
public class AlumnoController {
    @Autowired
    private final AlumnoService alumnoService;
    private FilesService amazonS3Service;

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

    @PostMapping(path="/{id}/fotoPerfil", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json")
    public ResponseEntity<Object> subirFotoPerfil(@RequestParam("fotoPerfilUrl") MultipartFile foto, @PathVariable int id){
        try{
            Optional<Alumno> alumno = alumnoService.getAlumnoById(id);
            if(alumno.isPresent()){
                Alumno alumnoEncontrado = alumno.get();
                amazonS3Service = new FilesService();
                File convertedFile = convertMultiPartToFile(foto);
                String key = "foto-" + alumnoEncontrado.getMatricula() + "-" + alumnoEncontrado.getApellidos() + "-" + alumnoEncontrado.getNombres();
                amazonS3Service.getS3Client().putObject(PutObjectRequest.builder()
                    .bucket(amazonS3Service.getBucketName())
                    .key(key)
                    .build(), convertedFile.toPath());
                
                alumnoEncontrado.setFotoPerfilUrl("https://" + amazonS3Service.getBucketName() + ".s3.amazonaws.com/" + key);
                Alumno alumnoUpdated = alumnoService.updateAlumno(id, alumnoEncontrado);
                return new ResponseEntity<>(alumnoUpdated, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Image not uploaded", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            return new ResponseEntity<>("Server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path="/{id}/email", produces = "application/json")
    public ResponseEntity<Object> notificar(@PathVariable int id){
        try{
            Optional<Alumno> alumno = alumnoService.getAlumnoById(id);
            if(alumno.isPresent()){
                Alumno alumnoEncontrado = alumno.get();
                SnsService snsService = new SnsService();
                String message = "La calificación del alumno " + alumnoEncontrado.getNombres() + " " + alumnoEncontrado.getApellidos() + " es " + alumnoEncontrado.getPromedio();
                snsService.getSnsClient().publish(PublishRequest.builder()
                    .topicArn(snsService.getTopicARN())
                    .message(message)
                    .build());
                return new ResponseEntity<>(alumnoEncontrado, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Alumno not found", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            return new ResponseEntity<>("Server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        file.transferTo(convertedFile);
        return convertedFile;
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
