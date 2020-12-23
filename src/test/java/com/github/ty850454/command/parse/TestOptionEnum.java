package com.github.ty850454.command.parse;


import lombok.Getter;

/**
 * @author xy
 */
@Getter
public enum TestOptionEnum implements IOptionEnum {
    A("a", OptionTypeEnum.BOOLEAN, "哈哈", null),
    B("b", OptionTypeEnum.INTEGER, "哈哈", null),
    C("c", OptionTypeEnum.INTEGER, "哈哈", null),

    ;

    private final String opt;
    private final OptionTypeEnum type;
    private final String description;
    private final Object defaultValue;

    TestOptionEnum(String opt, OptionTypeEnum type, String description, Object defaultValue) {
        this.opt = opt;
        this.type = type;
        this.description = description;
        this.defaultValue = defaultValue;
    }

}
