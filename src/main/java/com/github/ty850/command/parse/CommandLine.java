package com.github.ty850.command.parse;

import java.util.Optional;

/**
 * @author xy
 */
public class CommandLine {

    public final IEnumMap<IOptionEnum, Object> options;

    public <T extends IOptionEnum> CommandLine(Class<T> enumClass) {
        options = new IEnumMap<>(enumClass);
        IOptionEnum[] keys = options.keys();

        for (IOptionEnum optionEnum : keys) {
            if (optionEnum.getDefaultValue() != null) {
                if (optionEnum.getDefaultValue().getClass() != optionEnum.getType().getDefaultValueClass()) {
                    throw new RuntimeException("参数" + optionEnum.getOpt() + "的类型" + optionEnum.getType() + "默认值类型只能为：" + optionEnum.getType().getDefaultValueClass());
                }
            }
        }
    }

    public static CommandLine empty() {
        return null;
    }

    public void addOption(IOptionEnum optionEnum, String value) {
        switch (optionEnum.getType()) {
            case BOOLEAN:
                options.put(optionEnum, Boolean.valueOf(value));
                break;
            case INTEGER:
                options.put(optionEnum, Integer.valueOf(value));
                break;
            case STRING:
                options.put(optionEnum, value);
                break;
            case Long:
                options.put(optionEnum, Long.valueOf(value));
                break;
            default:
        }
    }

    public void addOptionTrue(IOptionEnum optionEnum) {
        if (!OptionTypeEnum.BOOLEAN.equals(optionEnum.getType())) {
            throw new RuntimeException(optionEnum.getOpt() + "参数类型不是boolean类型");
        }
        options.put(optionEnum, Boolean.TRUE);
    }

    public boolean hasOption(IOptionEnum optionEnum) {
        return options.containsKey(optionEnum);
    }

    public Optional<Object> getValueOpt(IOptionEnum optionEnum) {
        Object o = options.get(optionEnum);
        if (o == null) {
            o = optionEnum.getDefaultValue();
        }
        return Optional.ofNullable(o);
    }

    private OptionTypeEnum getType(IOptionEnum optionEnum) {
        OptionTypeEnum type = optionEnum.getType();
        return type == null ? OptionTypeEnum.STRING : type;
    }

    public <T> Optional<T> getValueOpt(IOptionEnum optionEnum, Class<T> tClass) {
        OptionTypeEnum type = getType(optionEnum);
        if (type.getDefaultValueClass() != tClass) {
            throw new RuntimeException(optionEnum.getOpt() + "的类型为" + type + "，无法取" + tClass + "类型的值");
        }
        // noinspection unchecked
        return (Optional<T>) getValueOpt(optionEnum);
    }

    public Optional<Boolean> getBooleanOpt(IOptionEnum optionEnum) {
        return getValueOpt(optionEnum, Boolean.class);
    }

    public boolean getBool(IOptionEnum optionEnum) {
        return getBooleanOpt(optionEnum).orElseThrow(() -> new RuntimeException("返回值为null且未定义默认值"));
    }

    public boolean getBool(IOptionEnum optionEnum, boolean defaultValue) {
        return getBooleanOpt(optionEnum).orElse(defaultValue);
    }

    public Optional<Integer> getIntegerOpt(IOptionEnum optionEnum) {
        return getValueOpt(optionEnum, Integer.class);
    }

    public int getInt(IOptionEnum optionEnum) {
        return getIntegerOpt(optionEnum).orElseThrow(() -> new RuntimeException("返回值为null且未定义默认值"));
    }

    public int getInt(IOptionEnum optionEnum, int defaultValue) {
        return getIntegerOpt(optionEnum).orElse(defaultValue);
    }

    public Optional<Long> getLongOpt(IOptionEnum optionEnum) {
        return getValueOpt(optionEnum, Long.class);
    }

    public long getLong(IOptionEnum optionEnum) {
        return getLongOpt(optionEnum).orElseThrow(() -> new RuntimeException("返回值为null且未定义默认值"));
    }

    public long getLong(IOptionEnum optionEnum, long defaultValue) {
        return getLongOpt(optionEnum).orElse(defaultValue);
    }

    public Optional<String> getStringOpt(IOptionEnum optionEnum) {
        return getValueOpt(optionEnum, String.class);
    }

    public String getString(IOptionEnum optionEnum) {
        return getStringOpt(optionEnum).orElseThrow(() -> new RuntimeException("返回值为null且未定义默认值"));
    }

    public String getString(IOptionEnum optionEnum, String defaultValue) {
        return getStringOpt(optionEnum).orElse(defaultValue);
    }
}
