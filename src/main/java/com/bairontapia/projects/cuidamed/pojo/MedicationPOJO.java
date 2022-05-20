package com.bairontapia.projects.cuidamed.pojo;

import com.bairontapia.projects.cuidamed.disease.medication.Medication;
import lombok.Getter;
import lombok.Setter;

public class MedicationPOJO {

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String administrationRoute;
    @Getter
    @Setter
    private String measureUnit;
    @Getter
    @Setter
    private String pharmaceuticalForm;

    public MedicationPOJO() {
    }

    public MedicationPOJO(final Medication medication) {
        this.name = medication.name();
        this.administrationRoute = medication.administrationRoute().toString();
        this.measureUnit = medication.measureUnit().toString();
        this.pharmaceuticalForm = medication.pharmaceuticalForm().toString();
    }
}
