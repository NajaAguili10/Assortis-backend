package com.backend.assorttis.enums;

import java.util.Arrays;

public enum TemplateFormat {
    WB("World Bank Reference"),
    DANIDA("Danida CV/Reference"),
    EUROPASS("Europass"),
    UNDP("UNDP"),
    WB_CV("World Bank CV");

    private final String label;

    TemplateFormat(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static TemplateFormat fromRequest(String value) {
        return Arrays.stream(values())
                .filter(format -> format.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new TemplateNotFoundException("Template not found"));
    }

    public static TemplateFormat fromFileName(String fileName) {
        String normalized = fileName.toLowerCase()
                .replace("_", " ")
                .replace("-", " ");

        if (normalized.contains("wb cv") || normalized.contains("world bank cv")) {
            return WB_CV;
        }
        if (normalized.contains("danida")) {
            return DANIDA;
        }
        if (normalized.contains("europass") || normalized.contains("eurepass")) {
            return EUROPASS;
        }
        if (normalized.contains("undp")) {
            return UNDP;
        }
        if (normalized.contains("wb") || normalized.contains("world bank")) {
            return WB;
        }

        throw new TemplateNotFoundException("Template not found");
    }
}
