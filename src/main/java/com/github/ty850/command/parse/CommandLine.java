package com.github.ty850.command.parse;

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
                    throw new RuntimeException("类型" + optionEnum.getType() + "的默认值类型只能为：" + optionEnum.getType().getDefaultValueClass());
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

    public Object getValue(IOptionEnum optionEnum) {
        Object o = options.get(optionEnum);
        if (o == null) {
            o = optionEnum.getDefaultValue();
        }
        return o;
    }

    public boolean getBoolean(IOptionEnum optionEnum) {
        if (!OptionTypeEnum.BOOLEAN.equals(optionEnum.getType())) {
            throw new RuntimeException("非boolean类型");
        }
        return (Boolean) getValue(optionEnum);
    }

    public int getInteger(IOptionEnum optionEnum) {
        if (!OptionTypeEnum.INTEGER.equals(optionEnum.getType())) {
            throw new RuntimeException("非INTEGER类型");
        }
        return (Integer) getValue(optionEnum);
    }

    public String getString(IOptionEnum optionEnum) {
        if (!OptionTypeEnum.STRING.equals(optionEnum.getType())) {
            throw new RuntimeException("非STRING类型");
        }
        return (String) getValue(optionEnum);
    }
}
