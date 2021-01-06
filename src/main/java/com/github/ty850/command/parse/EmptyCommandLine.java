package com.github.ty850.command.parse;

import java.util.Optional;

/**
 * @author xy
 */
public class EmptyCommandLine implements CommandLine {

    @Override
    public boolean hasOption(IOptionEnum optionEnum) {
        return false;
    }

    @Override
    public Optional<Object> getValueOpt(IOptionEnum optionEnum) {
        return Optional.ofNullable(optionEnum.getDefaultValue());
    }

}
