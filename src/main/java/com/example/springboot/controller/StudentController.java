package com.example.springboot.controller;

import com.example.springboot.exception.ResourceNotFoundException;
import com.example.springboot.model.Student;
import com.example.springboot.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    //all student
    @GetMapping
    public List<Student> getALlStudents(){
        return studentRepository.findAll();
    }

    //insert
    @PostMapping
    public Student createStudent(@RequestBody Student student){
        return studentRepository.save(student);
    }

    //get student by id
    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id){
        Student student = studentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Student not found: " + id));
        return ResponseEntity.ok(student);
    }

    //update
    @PutMapping("{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails){
        Student updateStudent = studentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Student not found: " + id));
        updateStudent.setName(studentDetails.getName());
        updateStudent.setClassroom(studentDetails.getClassroom());
        updateStudent.setEmail(studentDetails.getEmail());

        studentRepository.save(updateStudent);
        return ResponseEntity.ok(updateStudent);
    }

    //delete
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable Long id){
        Student student = studentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Student not found: " + id));
        studentRepository.delete(student);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
