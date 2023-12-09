package com.example.AWS_Project.Session;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.AWS_Project.Alumno.Alumno;
import com.example.AWS_Project.Alumno.AlumnoService;

@RestController
public class SessionController {
   private SessionRepository sessionRepository;
   private AlumnoService alumnoService;

   @Autowired
   public SessionController(SessionRepository sessionRepository, AlumnoService alumnoService) {
      this.sessionRepository = sessionRepository;
      this.alumnoService = alumnoService;
   }

   @PostMapping(path = "/alumnos/{id}/session/login", consumes = "application/json", produces = "application/json")
   public ResponseEntity<Object> login(@PathVariable("id") int id, @RequestBody Map<String, String> password) {
      try {
         Optional<Alumno> alumnoOptional = alumnoService.getAlumnoById(id);
         if (alumnoOptional.isPresent()) {
            Alumno alumno = alumnoOptional.get();
            if (alumno.getPassword().equals(password.get("password"))) {
               Session session = new Session();
               session.setFecha((int) (System.currentTimeMillis() / 1000L));
               session.setAlumnoId(id);
               session.setActive(true);
               session.setSessionString(generateRandomString(128));
               Session sessionSaved = sessionRepository.save(session);
               return new ResponseEntity<>(sessionSaved, HttpStatus.OK);
            } else {
               return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
            }
         } else {
            return new ResponseEntity<>("Alumno not found", HttpStatus.NOT_FOUND);
         }
      } catch (RuntimeException e) {
         return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
      }
   }

   @PostMapping(path = "/alumnos/{id}/session/verify", consumes = "application/json", produces = "application/json")
   public ResponseEntity<Object> verify(@PathVariable("id") int id, @RequestBody Map<String, String> sessionString) {
      try {
         Optional<Session> optionalSession = sessionRepository.findByAlumnoId(id);
         if (optionalSession.isPresent()) {
            Session session = optionalSession.get();
            if (session.getSessionString().equals(sessionString.get("sessionString")) && session.isActive()) {
               return new ResponseEntity<>("Session is valid", HttpStatus.OK);
            } else {
               return new ResponseEntity<>("Session is invalid", HttpStatus.BAD_REQUEST);
            }
         } else {
            return new ResponseEntity<>("No session found for the specified alumnoId", HttpStatus.NOT_FOUND);
         }
      } catch (RuntimeException e) {
         return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
      }
   }

   @PostMapping(path = "/alumnos/{id}/session/logout", consumes = "application/json", produces = "application/json")
   public ResponseEntity<Object> logout(@PathVariable("id") int id, @RequestBody Map<String, String> sessionString) {
      try {
         Optional<Session> optionalSession = sessionRepository.logout(id, sessionString.get("sessionString"));
         if (optionalSession.isPresent()) {
            Session session = optionalSession.get();
            return new ResponseEntity<>(session, HttpStatus.OK);
         } else {
            return new ResponseEntity<>("No session found for the specified alumnoId", HttpStatus.NOT_FOUND);
         }
      } catch (RuntimeException e) {
         return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
      }
   }

   private String generateRandomString(int length) {
      SecureRandom secureRandom = new SecureRandom();
      byte[] randomBytes = new byte[length / 2];
      secureRandom.nextBytes(randomBytes);
      return new BigInteger(1, randomBytes).toString(16);
   }
}
