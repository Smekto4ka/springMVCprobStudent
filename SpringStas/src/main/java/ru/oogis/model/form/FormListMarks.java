package ru.oogis.model.form;


import ru.oogis.model.Predmet;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class FormListMarks {

    public FormListMarks() {

    }


    private Predmet predmet;
    @NotNull(message = "not predmet oder null")
    private String namePredmet;

    private Integer[] marks;

    public Integer[] getMarks() {
        return marks;
    }

    public void setMarks(Integer... mass) {
        this.marks = mass;
    }

    public List<Integer> getList() {
        return Arrays.stream(marks).filter(value -> value != null)
                //  .filter(value -> value >= 0 && value <= 5)
                .collect(Collectors.toCollection(() -> new LinkedList<>()));

    }

    public Predmet getPredmet() {

        return predmet;
    }

    public String getNamePredmet() {
        return namePredmet;
    }

    public void setNamePredmet(String namePredmet) {
        if (namePredmet.equals("")) {
            this.namePredmet = null;
            return;
        }
        this.namePredmet = namePredmet.toUpperCase().trim();

        try {
            this.predmet = Predmet.valueOf(this.namePredmet);

        } catch (IllegalArgumentException e) {
            System.out.println(this + " " + e);
            this.namePredmet = null;

        }

    }

    public int getLengthArraysMarks() {
        return marks.length;
    }

    @Override
    public String toString() {
        return "FormListMarks{" +
                "namePredmet='" + namePredmet + '\'' +
                ", vall=" + Arrays.toString(marks) +
                '}';
    }
}
