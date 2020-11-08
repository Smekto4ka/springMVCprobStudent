package ru.oogis.service;

import ru.oogis.model.FilterCriterion;
import ru.oogis.model.Predmet;
import ru.oogis.model.Student;
import ru.oogis.model.form.ParametersForFilter;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StudentService {
    /**
     * Returns the student object by id.
     */
    Optional<Student> getStudById(long studentId);

    /**
     * Returns the id of all available students.
     */
    Set<Long> getIdStydens();

    /**
     * Gets the student object to add to the server.
     */
    public void postStudent(Student student);

    /**
     * adding new grades for a student
     *
     * @param studentId student id
     * @param predmet   the item that is in the enum
     * @param marksList list marks
     */
    void setMarksByIdStudentsAndPredmet(long studentId, Predmet predmet, List<Integer> marksList);

    List<Long> getIdStudentsUsingFilter(ParametersForFilter parametersForFilter , FilterCriterion filterCriterion);



    boolean deletStudentById(long id);

    void updateStudent(Student student);
}
