package com.ubi.beautyClinic.application.core.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ServiceEnum {


    FACIAL_HARMONIZATION("Facial Harmonization", "Facial Procedures"),
    BOTOX("Botox", "Facial Procedures"),
    FACIAL_FILLER("Facial Filler", "Facial Procedures"),
    LIP_FILLER("Lip Filler", "Facial Procedures"),
    BUCHECTOMY("Buccal Fat Removal", "Facial Procedures"),
    SKIN_BOOSTER("Skin Booster", "Facial Procedures"),
    LYMPHATIC_DRAINAGE("Lymphatic Drainage", "Body Procedures"),
    MODELING_MASSAGE("Modeling Massage", "Body Procedures"),
    TECA_THERAPY("Teca Therapy", "Body Procedures"),
    INTIMATE_AESTHETICS("Intimate Aesthetics", "Body Procedures"),
    PRP("PRP", "Facial Procedures"),
    LOW_LEVEL_LASER_THERAPY("Low-Level Laser Therapy", "Skin Treatments"),
    CRYOLIPOLYSIS("Cryolipolysis", "Body Contouring"),
    RADIOFREQUENCY("Radiofrequency", "Body Contouring"),
    CRYSTAL_PEELING("Crystal Peeling", "Skin Treatments"),
    DIAMOND_PEELING("Diamond Peeling", "Skin Treatments"),
    PORCELAIN_PEELING("Porcelain Peeling", "Skin Treatments"),
    ULTRASONIC_PEELING("Ultrasonic Peeling", "Skin Treatments"),
    CHEMICAL_PEELING("Chemical Peeling", "Skin Treatments"),
    NEOSENSITIVE_PEELING("Neosensitive Peeling", "Skin Treatments"),
    CRYOGENIC_PEELING("Cryogenic Peeling", "Skin Treatments");

    private final String service;
    private final String category;

    ServiceEnum(String service, String category) {

        this.service = service;
        this.category = category;
    }

    public String getService() {

        return service;
    }
    @JsonValue
    @Override
    public String toString() {

        return service;
    }
}
