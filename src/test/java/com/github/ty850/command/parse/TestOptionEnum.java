package com.github.ty850.command.parse;


import com.github.ty850.command.parse.IOptionEnum;
import com.github.ty850.command.parse.OptionTypeEnum;
import lombok.Getter;

/**
 * @author xy
 */
@Getter
public enum TestOptionEnum implements IOptionEnum {
    A("a", OptionTypeEnum.BOOLEAN, "哈哈", null),
    B("b", OptionTypeEnum.INTEGER, "哈哈", 23),
    C("c", OptionTypeEnum.STRING, "哈哈", "w"),
    D("deltaStart", OptionTypeEnum.STRING, "哈哈", "w"),
    E("deltaStartdeltaStart", OptionTypeEnum.STRING, "哈哈", "w"),
    F("deltaStadeltaStartdeltaStartrtdeltaStart", OptionTypeEnum.STRING, "哈哈", "w"),

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
