package com.github.ty850.command.parse;

import java.util.Optional;

/**
 * @author xy
 */
public class EnumUtil {

    public static <T extends Enum<?>> Optional<T> byOrdinal(Class<T> enumClass, int ordinal) {
        T[] enumConstants = enumClass.getEnumConstants();
        if (ordinal > enumConstants.length) {
            return Optional.empty();
        }
        return Optional.of(enumConstants[ordinal]);
    }

    public static <T extends IOptionEnum> Optional<T> byOpt(Class<T> enumClass, String opt) {
        if (StringUtils.isEmpty(opt)) {
            return Optional.empty();
        }
        if (!enumClass.isEnum()) {
            return Optional.empty();
        }
        T[] enumConstants = enumClass.getEnumConstants();
        for (T enumConstant : enumConstants) {
            if (opt.equals(enumConstant.getOpt())) {
                return Optional.of(enumConstant);
            }
        }
        return Optional.empty();
    }


}
