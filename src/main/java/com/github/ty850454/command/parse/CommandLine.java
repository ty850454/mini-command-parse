package com.github.ty850454.command.parse;


import java.util.Map;
import java.util.Set;

/**
 * @author xy
 */
public class CommandLine {

    public static final Integer ZERO_INTEGER = 0;
    public static final String ZERO_STRING = "";

    public final IEnumMap<IOptionEnum, Object> options;

    public <T extends IOptionEnum> CommandLine(Class<T> enumClass) {
        options = new IEnumMap<>(enumClass);

        Set<Map.Entry<IOptionEnum, Object>> entries = options.entrySet();
        for (Map.Entry<IOptionEnum, Object> entry : entries) {
            IOptionEnum key = entry.getKey();
            if (key.getDefaultValue() == null) {
                entry.setValue(key.getType().getDefaultValue());
            }else {
                entry.setValue(key.getDefaultValue());
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

    public Object getOptionValue(IOptionEnum optionEnum) {
        return options.get(optionEnum);
    }

    // public boolean getBoolean(IOptionEnum optionEnum) {
    //     return options.get(optionEnum);
    // }
}
