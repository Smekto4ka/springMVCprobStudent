package ru.oogis.model;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.*;

/**
 * Student object. Which has a unique id.
 * <p>
 * Also, the student has grades in subjects and their average value.
 */
public class Student {
    private long idStudent;
    @Size(min = 2, max = 20)
    // @NotNull
    private String firstName;
    @Size(min = 2, max = 20)
    //  @NotNull
    private String lastName;
    @Min(0)
    @Max(150)
    private int years;

    private final Map<Predmet, List<Integer>> marksStudentByPredmetMap = new EnumMap<>(Predmet.class);
    private final Map<Predmet, Double> averageMarksMap = new EnumMap<>(Predmet.class);

    public Student(long idStudent, String firstName, String lastName, int years) {
        this.idStudent = idStudent;
        this.firstName = firstName;
        this.lastName = lastName;
        this.years = years;
    }

    public Student() {
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
       this.years = years;
    }

    public void setIdStudent(long idStudent) {
        if (this.idStudent == 0)
            this.idStudent = idStudent;
    }

    /**
     * Adding new grades for a specific subject.
     * If there are already grades on the subject, then the obtained values are added to the already existing sheet.
     *
     * @param predmet   the subject to which grades will be assigned.
     * @param listMarks ist marks.
     */
    public void setMarks(Predmet predmet, List<Integer> listMarks) {
        List<Integer> availableListMarks = marksStudentByPredmetMap.get(predmet);
        if (availableListMarks == null) {
            marksStudentByPredmetMap.put(predmet, listMarks);
        } else {
            availableListMarks.addAll(listMarks);
        }
        availableListMarks = marksStudentByPredmetMap.get(predmet);
        OptionalDouble average = availableListMarks.stream().mapToInt(i -> i).average();
        average.ifPresent(d -> averageMarksMap.put(predmet, d));
    }

    public Double getAverageByPredmet(Predmet predmet) {
        return averageMarksMap.get(predmet);
    }

    public long getIdStudent() {
        return idStudent;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName.trim();
    }

    public void setLastName(String lastName) {

        this.lastName = lastName.trim();
    }

    /**
     * Gives out a grade sheet for a subject.
     * Looks like: Subject [1, 2, 3, 4 ...]
     */
    public List<String> getListStringMarksPredmet() {
        List<String> listValue = new LinkedList<>();
        if (marksStudentByPredmetMap.keySet().size() == 0) {
            listValue.add("null");
            return listValue;
        }

        for (Predmet predmet : marksStudentByPredmetMap.keySet()) {
            listValue.add(predmet.toString() + " : " + marksStudentByPredmetMap.get(predmet));
        }
        return listValue;
    }

    /**
     * Gives out a sheet of average values for a subject.
     * Looks like: Subject [1, 2, 3, 4 ...]
     */
    public List<String> getListStringAveragePredmet() {
        List<String> listAverage = new LinkedList<>();
        if (averageMarksMap.keySet().size() == 0) {
            listAverage.add("null");
            return listAverage;
        }

        for (Predmet predmet : averageMarksMap.keySet()) {
            listAverage.add(predmet.toString() + " : " + averageMarksMap.get(predmet));
        }
        return listAverage;
    }

    public void update(Student student) {

        firstName = student.firstName;
        lastName = student.lastName;
        years = student.years;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nStudent id : " + idStudent);
        stringBuilder.append("\nfirst name: " + firstName);
        stringBuilder.append("\nlast name: " + lastName);
        stringBuilder.append("\n years : " + years);
        for (Predmet predmet : marksStudentByPredmetMap.keySet()) {
            stringBuilder.append("\nozenka" + predmet + " -> " + marksStudentByPredmetMap.get(predmet));
            stringBuilder.append("\naverage -> " + averageMarksMap.get(predmet));
        }
        return stringBuilder.toString();
    }
}
