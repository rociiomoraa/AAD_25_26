package com.rocio.aad;

import com.rocio.aad.model.Student;
import com.rocio.aad.repository.ModuleRepository;
import com.rocio.aad.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class StudentService implements Service<Student> {

    private final StudentRepository studentRepository;
    private final ModuleRepository moduleRepository;

    // Los final tiene que estar inicializados en el constructor.
    // esto se sustituye con @RequiredArgsConstructor de lombok

//    public StudentService(Student student) {
//        this.student = student;
//    }

    /**
     * @param entity
     * @return
     */
    @Override
    public boolean validate(Student entity) {
        return !entity.getDni().isBlank() && entity.getName().isBlank();
    }

    public Student createStudent(final Student student, List<Module> modules) {
        if (validate(student)) {
            student.setModules(modules);
            return studentRepository.create(student);
        }
        return null;
    }
}
