package ru.oogis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.oogis.model.FilterCriterion;
import ru.oogis.model.Predmet;
import ru.oogis.model.Student;
import ru.oogis.model.StudentEntity;
import ru.oogis.model.converter.StudentConverter;
import ru.oogis.model.form.ParametersForFilter;
import ru.oogis.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentServiceBD implements StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentServiceBD(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Optional<Student> getStudById(long studentId) {
        return Optional.ofNullable(StudentConverter.getStudent(getStudentEntityById(studentId)));
    }

    @Override
    public Set<Long> getIdStydens() {
        return readAll().stream().map(StudentEntity::getIdStudent).collect(Collectors.toSet());
    }

    @Override
    public void postStudent(Student student) {
        create(StudentConverter.getStudentEntity(student));
    }

    @Override
    public void setMarksByIdStudentsAndPredmet(long studentId, Predmet predmet, List<Integer> marksList) {

    }

    @Override
    public List<Long> getIdStudentsUsingFilter(ParametersForFilter parametersForFilter, FilterCriterion filterCriterion) {
        return null;
    }


    @Override
    public boolean deletStudentById(long id) {
        return delete(id);
    }

    @Override
    public void updateStudent(Student student) {
        update(StudentConverter.getStudentEntity(student), student.getIdStudent());
    }

    private StudentEntity getStudentEntityById(long idStudrnt) {
        return studentRepository.getOne(idStudrnt);
    }

    private List<StudentEntity> readAll() {
        return studentRepository.findAll();
    }

    private void create(StudentEntity studentEntity) {
        studentRepository.save(studentEntity);
    }

    private boolean delete(long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private boolean update(StudentEntity studentEntity, long idStudent) {
        if (studentRepository.existsById(idStudent)) {
            studentEntity.setIdStudent(idStudent);
            studentRepository.save(studentEntity);
            return true;
        }

        return false;
    }
}
