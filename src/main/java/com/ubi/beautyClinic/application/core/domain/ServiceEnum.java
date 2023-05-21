package com.ubi.beautyClinic.application.core.domain;

public enum ServiceEnum {


    HARMONIZACAO_FACIAL("Harmonização Facial", "Procedimentos Faciais"),
    BOTOX("Botox", "Procedimentos Faciais"),
    PREENCHIMENTO_FACIAL("Preenchimento Facial", "Procedimentos Faciais"),
    PREENCHIMENTO_LABIAL("Preenchimento Labial", "Procedimentos Faciais"),
    BICHECTOMIA("Bichectomia", "Procedimentos Faciais"),
    SKINBOOSTER("Skinbooster", "Procedimentos Faciais"),
    DRENAGEM_LINFATICA("Drenagem Linfática", "Procedimentos Corporais"),
    MASSAGEM_MODELADORA("Massagem modeladora", "Procedimentos Corporais"),
    TECARTERAPIA("Tecarterapia", "Procedimentos Corporais"),
    ESTETICA_INTIMA("Estética íntima", "Procedimentos Corporais"),
    PRP("PRP", "Procedimentos Faciais"),
    TERAPIA_LASER_BAIXO_NIVEL("Terapia à laser de baixo nível", "Tratamentos da Pele"),
    CRIOLIPOLISE("Criolipólise", "Contorno Corporal"),
    RADIOFREQUENCIA("Radiofrequência", "Contorno Corporal"),
    PEELING_CRISTAL("Peeling de Cristal", "Tratamentos da Pele"),
    PEELING_DIAMANTE("Peeling de Diamante", "Tratamentos da Pele"),
    PEELING_PORCELANA("Peeling de Porcelana", "Tratamentos da Pele"),
    PEELING_ULTRASSONICO("Peeling Ultrasônico", "Tratamentos da Pele"),
    PEELING_QUIMICO("Peeling Químico", "Tratamentos da Pele"),
    PEELING_NEOSENSITIVE("Peeling Neosensitive", "Tratamentos da Pele"),
    PEELING_CRIOGENICO("Peeling Criogênico", "Tratamentos da Pele");

    private final String service;
    private final String category;

    ServiceEnum(String service, String category) {

        this.service = service;
        this.category = category;
    }
}
