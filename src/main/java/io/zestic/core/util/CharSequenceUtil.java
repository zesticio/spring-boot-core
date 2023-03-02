package io.zestic.core.util;

public class CharSequenceUtil implements CharConstant {

    public static final int INDEX_NOT_FOUND = -1;

    public static final String NULL = "null";

    public static final String EMPTY = "";

    public static final String SPACE = " ";

    public static boolean isBlank(CharSequence str) {
        int length;
        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }
        for (int i = 0; i < length; i++) {
            if (false == CharUtil.isBlankChar(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(CharSequence str) {
        return false == isBlank(str);
    }
}
