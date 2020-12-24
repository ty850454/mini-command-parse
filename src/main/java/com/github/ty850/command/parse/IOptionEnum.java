package com.github.ty850.command.parse;


/**
 * @author xy
 */
public interface IOptionEnum extends IEnum {

    String getOpt();
    OptionTypeEnum getType();
    String getDescription();
    Object getDefaultValue();

}
