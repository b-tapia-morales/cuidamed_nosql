package com.bairontapia.projects.cuidamed.pojo;

import com.bairontapia.projects.cuidamed.medicalrecord.MedicalRecord;
import lombok.Getter;
import lombok.Setter;

public class MedicalRecordPOJO {

    @Getter
    @Setter
    private String bloodType;
    @Getter
    @Setter
    private String healthCare;

    public MedicalRecordPOJO() {
    }

    public MedicalRecordPOJO(final MedicalRecord medicalRecord) {
        this.bloodType = medicalRecord.bloodType().toString();
        this.healthCare = medicalRecord.healthCare().toString();
    }

    @Override
    public String toString() {
        return String.format
                ("""
                        Grupo sanguíneo:\t\t\t%s
                        Sistema de salud:\t\t\t%s
                        """, bloodType, healthCare);
    }

}
