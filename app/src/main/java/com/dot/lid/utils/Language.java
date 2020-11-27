package com.dot.lid.utils;

public enum Language {
    ENGLISH("en"),
    GERMAN("de"),
    ARABIC("ar");

    private final String language;

    Language(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
}
