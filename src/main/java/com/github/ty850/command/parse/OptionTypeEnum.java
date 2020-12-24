package com.github.ty850.command.parse;

import lombok.Getter;

/**
 * @author xy
 */
@Getter
public enum OptionTypeEnum {
    BOOLEAN(Boolean.class, "布尔"),
    INTEGER(Integer.class, "整数"),
    STRING(String.class, "文本"),
    Long(Long.class, "长整数"),

    ;

    private final Class<?> defaultValueClass;
    private final String description;

    OptionTypeEnum(Class<?> defaultValueClass, String description) {
        this.defaultValueClass = defaultValueClass;
        this.description = description;
    }
}
