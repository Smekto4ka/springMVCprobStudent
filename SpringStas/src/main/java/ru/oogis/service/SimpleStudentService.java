package ru.oogis.service;

import org.springframework.stereotype.Service;
import ru.oogis.model.Filter;
import ru.oogis.model.Predmet;
import ru.oogis.model.Student;
import ru.oogis.model.form.FormParametersForFilter;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class SimpleStudentService implements StudentService {
    private static Map<Long, Student> studentMap = new HashMap<>();
    private static long maxIdStudent;

    static {
        studentMap.put(1l, new Student(1, "Stas", "last name", 5));
        studentMap.put(2l, new Student(2, "Sasa", "last name", 15));
        studentMap.put(3l, new Student(3, "Misa", "last name", 5));
        studentMap.put(4l, new Student(4, "Petya", "stepanov", 25));
        studentMap.put(5l, new Student(5, "Kiril", "stepanov", 25));
        studentMap.put(6l, new Student(6, "Misa", "Antonov", 35));
        studentMap.put(7l, new Student(7, "Oleg", "Antonov", 2555));
        studentMap.put(8l, new Student(8, "Lilya", "Alexeev", 25));
        List<Integer> marksList = new LinkedList<>();
        marksList.add(5);
        marksList.add(7);
        marksList.add(3);
        studentMap.get(1L).setMarks(Predmet.HISTORY, marksList);
        studentMap.get(1L).setMarks(Predmet.PHYSIC, marksList);
        studentMap.get(3L).setMarks(Predmet.PHYSIC, marksList);
        maxIdStudent = 8;
    }

    @Override
    public Optional<Student> getStudById(long studentId) {
        return Optional.ofNullable(studentMap.get(studentId));
    }

    @Override
    public Set<Long> getIdStydens() {
        return studentMap.keySet();
    }

    @Override
    public void postStudent(Student student) {
        long id = getNewIdStudents();
        student.setIdStudent(id);
        studentMap.put(id, student);
    }

    @Override
    public void setMarksByIdStudentsAndPredmet(long studentId, Predmet predmet, List<Integer> marksList) throws NullPointerException {
        Student student = studentMap.get(studentId);
        student.setMarks(predmet, marksList);
    }

    @Override
    public List<Long> getIdStudentsUsingFilter(FormParametersForFilter formParametersForFilter, Filter filter) {

        Predicate<Double> predicate = getPredicateFilter(formParametersForFilter);

        switch (filter) {
            case AVERAGE_MARKS: {
                Predmet predmet = formParametersForFilter.getPredmet();
                return studentMap.values().stream()
                        .filter(student -> predicate.test(student.getAverageByPredmet(predmet)))
                        .map(student -> student.getIdStudent())
                        .collect(Collectors.toCollection(LinkedList::new));
            }
            case YEARS: {
                return studentMap.values().stream()
                        .filter(student -> predicate.test((double) student.getYears()))
                        .map(student -> student.getIdStudent())
                        .collect(Collectors.toCollection(LinkedList::new));
            }
            default:
                return new ArrayList<>(studentMap.keySet());
            // return studentMap.keySet().stream().collect(Collectors.toList());

        }
    }

    private Predicate<Double> getPredicateFilter(FormParametersForFilter formParametersForFilter) {

        Predicate<Double> predicate = a -> a != null;
        if (formParametersForFilter.getMinimumBorder() != null && formParametersForFilter.getMaximumBorder() != null) {
            predicate = predicate.and(a -> a > formParametersForFilter.getValueMin() && a <= formParametersForFilter.getValueMax());
            if (formParametersForFilter.isChek())
                predicate = predicate.and(a -> a < formParametersForFilter.getValueMin() || a >= formParametersForFilter.getValueMax());
        }
        if (formParametersForFilter.getMinimumBorder() == null && formParametersForFilter.getMaximumBorder() != null)
            predicate = predicate.and(a -> a < formParametersForFilter.getValueMax());
        if (formParametersForFilter.getMinimumBorder() != null && formParametersForFilter.getMaximumBorder() == null)
            predicate = predicate.and(a -> a > formParametersForFilter.getValueMin());
        return predicate;
    }

    @Override
    public long getMaxIdStudents() {
        return maxIdStudent;
    }

    @Override
    public long getNewIdStudents() {
        return ++maxIdStudent;
    }

    @Override
    public boolean deletStudentById(long id) {
        if (studentMap.remove(id) != null)
            return true;
        return false;
    }

    @Override
    public void updateStudent(Student student) {
        studentMap.get(student.getIdStudent()).update(student);
    }

    @Override
    public String toString() {
        if (studentMap.isEmpty()) return "not stud";
        return studentMap.values().stream().map(Student::toString).collect(Collectors.joining("\n"));
    }
}
