package ru.oogis.model.form;

import ru.oogis.model.Predmet;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class FormParametersForFilter {
    private boolean chek;
    @Pattern(regexp = "[0-9.]{0,20}")
    private String minimumBorder;
    @Pattern(regexp = "[0-9.]{0,20}")
    private String maximumBorder;
    @NotNull(message = "not predmet or null")
    private String namePredmet = "predmet"; // defolt
    private Predmet predmet;


    public FormParametersForFilter() {
    }

    public boolean isChek() {
        return chek;
    }

    public void setChek(boolean chek) {
        this.chek = chek;
    }

    public String getMinimumBorder() {
        return minimumBorder;
    }

    public void setMinimumBorder(String valueMinString) {
        if (valueMinString.equals(""))
            return;
        valueMinString = valueMinString.trim();
        if (valueMinString.charAt(0) == '.') {
            this.minimumBorder = "0" + valueMinString;
            return;
        }

        this.minimumBorder = valueMinString;
    }

    public String getMaximumBorder() {
        return maximumBorder;
    }

    public void setMaximumBorder(String valueMaxString) {
        if (valueMaxString.equals(""))
            return;
        valueMaxString = valueMaxString.trim();
        if (valueMaxString.charAt(0) == '.') {
            this.maximumBorder = "0" + valueMaxString;
            return;
        }
        this.maximumBorder = valueMaxString;
        System.out.println(maximumBorder);
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
        }

        this.namePredmet = namePredmet.toUpperCase().trim();
        try {
            this.predmet = Predmet.valueOf(this.namePredmet);
        } catch (IllegalArgumentException e) {
            System.out.println(this + " " + e);
            this.namePredmet = null;

        }
    }

    public double getValueMin() {
        return Double.parseDouble(minimumBorder);
    }

    public double getValueMax() {
        return Double.parseDouble(maximumBorder);
    }

    @Override
    public String toString() {
        return "ParametersForFilter{" +
                "chek=" + chek +
                ", min=" + minimumBorder +
                ", max=" + maximumBorder +
                ", predmet=" + predmet +
                ", name predmet=" + namePredmet +
                '}';
    }
}
