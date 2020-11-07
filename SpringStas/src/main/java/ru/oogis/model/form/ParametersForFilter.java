package ru.oogis.model.form;

import ru.oogis.model.Predmet;

public interface ParametersForFilter {

    Predmet getPredmet();

    String getMinimumBorder();

    String getMaximumBorder();

    double getValueMin();

    double getValueMax();

    boolean isChek();
}
