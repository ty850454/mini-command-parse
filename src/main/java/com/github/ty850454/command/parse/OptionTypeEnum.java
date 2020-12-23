package com.github.ty850454.command.parse;

import lombok.Getter;

/**
 * @author xy
 */
@Getter
public enum OptionTypeEnum {
    BOOLEAN(Boolean.FALSE),
    INTEGER(0),
    STRING(""),

    ;

    private final Object defaultValue;

    OptionTypeEnum(Object defaultValue) {
        this.defaultValue = defaultValue;
    }
}
