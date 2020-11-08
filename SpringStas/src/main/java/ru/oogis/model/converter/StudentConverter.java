package ru.oogis.model.converter;

import ru.oogis.model.Student;
import ru.oogis.model.StudentEntity;

public class StudentConverter {

    public static Student getStudent(StudentEntity studentEntity) {
        return new Student(studentEntity.getIdStudent()
                , studentEntity.getFirstName()
                , studentEntity.getLastName()
                , studentEntity.getYears());
    }

    public static StudentEntity getStudentEntity(Student student) {
        return new StudentEntity(student.getIdStudent()
                , student.getFirstName()
                , student.getLastName()
                , student.getYears());
    }
}
