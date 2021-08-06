package com.example.test.controller;

import com.example.test.model.Program;
import com.example.test.service.ServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProgramController {

    @Autowired
    private ServiceHandler serviceHandler;

    // get all programs
    @GetMapping("/program")
    public ResponseEntity<List<Program>> getAllPrograms() {
        try {

            List<Program> programs = new ArrayList<>(serviceHandler.findAllProgram());

            if (programs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(programs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get a single program
    @GetMapping("/program/{id}")
    public ResponseEntity<Program> getProgramById(@PathVariable("id") long id) {
        Optional<Program> programData = serviceHandler.findByProgramId(id);

        return programData.map(program -> new ResponseEntity<>(program, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/program")
    public ResponseEntity<Program> createProgram(@RequestBody Program program) {
        try {
            Program programSaved = serviceHandler.creatNewProgram(program);
            return new ResponseEntity<>(programSaved, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/program/{id}")
    public ResponseEntity<HttpStatus> deleteProgram(@PathVariable("id") long id) {
        try {
            serviceHandler.deleteExistingProgram(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
