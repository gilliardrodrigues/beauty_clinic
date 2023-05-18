package com.ubi.beautyClinic.application.core.domain;

public enum ServiceType {

    HARMONIZACAO_FACIAL("Harmonização Facial"),
    BOTOX("Botox"),
    PREENCHIMENTO_FACIAL("Preenchimento Facial"),
    PREENCHIMENTO_LABIAL("Preenchimento Labial"),
    BICHECTOMIA("Bichectomia"),
    SKINBOOSTER("Skinbooster"),
    DRENAGEM_LINFATICA("Drenagem Linfática"),
    MASSAGEM_MODELADORA("Massagem modeladora"),
    TECARTERAPIA("Tecarterapia"),
    ESTETICA_INTIMA("Estética íntima"),
    PRP("PRP"),
    TERAPIA_LASER_BAIXO_NIVEL("Terapia à laser de baixo nível"),
    CRIOLIPOLISE("Criolipólise"),
    RADIOFREQUENCIA("Radiofrequência"),
    PEELING_CRISTAL("Peeling de Cristal"),
    PEELING_DIAMANTE("Peeling de Diamante"),
    PEELING_PORCELANA("Peeling de Porcelana"),
    PEELING_ULTRASSONICO("Peeling Ultrasônico"),
    PEELING_QUIMICO("Peeling Químico"),
    PEELING_NEOSENSITIVE("Peeling Neosensitive"),
    PEELING_CRIOGENICO("Peeling Criogênico");

    private final String description;

    ServiceType(String description) {

        this.description = description;
    }
}
