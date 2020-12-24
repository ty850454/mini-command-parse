package com.github.ty850.command.parse;

import java.util.Arrays;
import java.util.List;

/**
 * @author xy
 */
public class HelpFormatter {

    private final List<IOptionEnum> options;
    private final int maxlength;

    public <T extends IOptionEnum> HelpFormatter(Class<T> enumClass) {
        if (!enumClass.isEnum()) {
            throw new RuntimeException("请确保" + enumClass + "是个Enum");
        }
        options = Arrays.asList(enumClass.getEnumConstants());
        checkOptions();
        maxlength = getMaxlength() + 4;
    }

    public String getFormatHelp() {
        StringBuilder builder = new StringBuilder();
        builder.append("使用方式：");
        for (IOptionEnum option : options) {
            builder.append(option.getOpt());
            OptionTypeEnum type = getType(option);
            if (!type.equals(OptionTypeEnum.BOOLEAN)) {
                builder.append('=').append(type.getDescription());
            }
            builder.append(',');
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(System.lineSeparator());
        builder.append("所有参数均可不填，参数说明：");
        builder.append(System.lineSeparator());
        for (IOptionEnum option : options) {
            builder.append(fillBlank(option.getOpt(), maxlength))
                    .append(getType(option).getDescription())
                    .append('\t');
            if (option.getDescription() != null && option.getDefaultValue() != null) {
                builder.append(option.getDescription()).append("，默认值：").append(option.getDefaultValue());
            } else if (option.getDescription() != null) {
                builder.append(option.getDescription());
            } else if (option.getDefaultValue() != null) {
                builder.append("默认值：").append(option.getDefaultValue());
            }
            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }

    private OptionTypeEnum getType(IOptionEnum optionEnum) {
        OptionTypeEnum type = optionEnum.getType();
        return type == null ? OptionTypeEnum.STRING : type;
    }

    private void checkOptions() {
        for (IOptionEnum option : options) {
            if (option.getOpt() == null) {
                throw new RuntimeException("必须定义opt");
            }
        }
    }

    private int getMaxlength() {
        int maxLength = 0;
        for (IOptionEnum option : options) {
            if (maxLength < option.getOpt().length()) {
                maxLength = option.getOpt().length();
            }
        }
        return maxLength;
    }

    private String fillBlank(String opt, int length) {
        if (opt.length() >= length) {
            return opt;
        }
        StringBuilder builder = new StringBuilder(length);
        builder.append(opt);
        int frequency = length - opt.length();
        for (int i = 0; i < frequency; i++) {
            builder.append(' ');
        }
        return builder.toString();
    }

}
