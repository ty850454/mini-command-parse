package com.github.ty850454.command.parse;

import lombok.Getter;

/**
 * @author xy
 */
@Getter
public enum OptionTypeEnum {
    BOOLEAN(Boolean.class),
    INTEGER(Integer.class),
    STRING(String.class),

    ;

    private final Class<?> defaultValueClass;

    OptionTypeEnum(Class<?> defaultValueClass) {
        this.defaultValueClass = defaultValueClass;
    }
}
