package me.baraban4ik.ecolobby.enums;

import lombok.Getter;

@Getter
public enum Language {
    EN(""),
    RU("_ru");

    private final String suffix;

    Language(String suffix) {
        this.suffix = suffix;
    }
}
