package ru.oogis.model;


import javax.persistence.*;

@Entity
@Table(name = "Student")
public class StudentEntity {
    @Id
    @Column(name = "idStudent")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStudent;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;


    @Column(name = "years")
    private Integer years;

    public StudentEntity(){

    }

    public StudentEntity(Long idStudent, String firstName, String lastName, Integer years) {
        this.idStudent = idStudent;
        this.firstName = firstName;
        this.lastName = lastName;
        this.years = years;
    }

    public Long getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(Long idStudent) {
        this.idStudent = idStudent;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }


}
