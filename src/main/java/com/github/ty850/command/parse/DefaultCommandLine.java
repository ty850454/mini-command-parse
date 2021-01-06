package com.github.ty850.command.parse;

import java.util.Optional;

/**
 * @author xy
 */
public class DefaultCommandLine implements CommandLine {

    public final IEnumMap<IOptionEnum, Object> options;

    public <T extends IOptionEnum> DefaultCommandLine(Class<T> enumClass) {
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
            case LONG:
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

    @Override
    public boolean hasOption(IOptionEnum optionEnum) {
        return options.containsKey(optionEnum);
    }

    @Override
    public Optional<Object> getValueOpt(IOptionEnum optionEnum) {
        Object o = options.get(optionEnum);
        if (o == null) {
            o = optionEnum.getDefaultValue();
        }
        return Optional.ofNullable(o);
    }

}
