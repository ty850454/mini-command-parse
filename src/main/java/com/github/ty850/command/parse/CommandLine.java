package com.github.ty850.command.parse;

import java.util.Optional;

/**
 * @author xy
 */
public interface CommandLine {

    boolean hasOption(IOptionEnum optionEnum);

    Optional<Object> getValueOpt(IOptionEnum optionEnum);

    static CommandLine empty() {
        return new EmptyCommandLine();
    }

    default <T> Optional<T> getValueOpt(IOptionEnum optionEnum, Class<T> tClass) {
        OptionTypeEnum type = getType(optionEnum);
        if (type.getDefaultValueClass() != tClass) {
            throw new RuntimeException(optionEnum.getOpt() + "的类型为" + type + "，无法取" + tClass + "类型的值");
        }
        // noinspection unchecked
        return (Optional<T>) getValueOpt(optionEnum);
    }

    default OptionTypeEnum getType(IOptionEnum optionEnum) {
        OptionTypeEnum type = optionEnum.getType();
        return type == null ? OptionTypeEnum.STRING : type;
    }

    default Optional<Boolean> getBooleanOpt(IOptionEnum optionEnum) {
        return getValueOpt(optionEnum, Boolean.class);
    }
    default boolean getBool(IOptionEnum optionEnum) {
        return getBooleanOpt(optionEnum).orElseThrow(() -> new RuntimeException("返回值为null且未定义默认值"));
    }
    default boolean getBool(IOptionEnum optionEnum, boolean defaultValue) {
        return getBooleanOpt(optionEnum).orElse(defaultValue);
    }

    default Optional<Integer> getIntegerOpt(IOptionEnum optionEnum) {
        return getValueOpt(optionEnum, Integer.class);
    }
    default int getInt(IOptionEnum optionEnum) {
        return getIntegerOpt(optionEnum).orElseThrow(() -> new RuntimeException("返回值为null且未定义默认值"));
    }
    default int getInt(IOptionEnum optionEnum, int defaultValue) {
        return getIntegerOpt(optionEnum).orElse(defaultValue);
    }

    default Optional<Long> getLongOpt(IOptionEnum optionEnum) {
        return getValueOpt(optionEnum, Long.class);
    }
    default long getLong(IOptionEnum optionEnum) {
        return getLongOpt(optionEnum).orElseThrow(() -> new RuntimeException("返回值为null且未定义默认值"));
    }
    default long getLong(IOptionEnum optionEnum, long defaultValue) {
        return getLongOpt(optionEnum).orElse(defaultValue);
    }

    default Optional<String> getStringOpt(IOptionEnum optionEnum) {
        return getValueOpt(optionEnum, String.class);
    }
    default String getString(IOptionEnum optionEnum) {
        return getStringOpt(optionEnum).orElseThrow(() -> new RuntimeException("返回值为null且未定义默认值"));
    }
    default String getString(IOptionEnum optionEnum, String defaultValue) {
        return getStringOpt(optionEnum).orElse(defaultValue);
    }

}
