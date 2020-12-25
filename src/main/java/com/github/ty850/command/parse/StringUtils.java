package com.github.ty850.command.parse;

/**
 * @author xy
 */
class StringUtils {

    static boolean isBlank(String cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmpty(String cs) {
        return cs == null || cs.length() == 0;
    }
}
