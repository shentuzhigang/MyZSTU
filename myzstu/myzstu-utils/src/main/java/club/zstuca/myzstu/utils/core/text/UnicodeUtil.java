package club.zstuca.myzstu.utils.core.text;

import club.zstuca.myzstu.utils.core.HexadecimalUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 提供Unicode字符串和普通字符串之间的转换
 *
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-09-22 23:59
 */
public class UnicodeUtil {

    /**
     * Unicode字符串转为普通字符串<br>
     * Unicode字符串的表现方式为：\\uXXXX
     *
     * @param unicode Unicode字符串
     * @return 普通字符串
     */
    public static String toString(String unicode) {
        if (StrUtil.isBlank(unicode)) {
            return unicode;
        }

        final int len = unicode.length();
        StrBuilder sb = StrBuilder.create(len);
        int i;
        int pos = 0;
        while ((i = StrUtil.indexOfIgnoreCase(unicode, "\\u", pos)) != -1) {
            //写入Unicode符之前的部分
            sb.append(unicode, pos, i);
            pos = i;
            if (i + 5 < len) {
                char c;
                try {
                    c = (char) Integer.parseInt(unicode.substring(i + 2, i + 6), 16);
                    sb.append(c);
                    //跳过整个Unicode符
                    pos = i + 6;
                } catch (NumberFormatException e) {
                    //非法Unicode符，跳过
                    //写入"\\u"
                    sb.append(unicode, pos, i + 2);
                    pos = i + 2;
                }
            } else {
                //非Unicode符，结束
                break;
            }
        }

        if (pos < len) {
            sb.append(unicode, pos, len);
        }
        return sb.toString();
    }

    /**
     * 字符串编码为Unicode形式
     *
     * @param str 被编码的字符串
     * @return Unicode字符串
     */
    public static String toUnicode(String str) {
        return toUnicode(str, true);
    }

    /**
     * 字符串编码为Unicode形式
     *
     * @param str    被编码的字符串
     * @param format 16进制Unicode编码格式
     * @return
     */
    public static String toUnicode(String str, UnicodeEncodingFormat format) {
        return toUnicode(str, true, format);
    }

    /**
     * 字符串编码为Unicode形式
     *
     * @param str         被编码的字符串
     * @param isSkipAscii 是否跳过ASCII字符（只跳过可见字符）
     * @return Unicode字符串
     */
    public static String toUnicode(String str, boolean isSkipAscii) {
        return toUnicode(str, isSkipAscii, UnicodeEncodingFormat.BACKSLASH_U);
    }

    /**
     * 字符串编码为Unicode形式
     *
     * @param str         被编码的字符串
     * @param isSkipAscii 是否跳过ASCII字符（只跳过可见字符）
     * @param format      16进制Unicode编码格式
     * @return Unicode字符串
     */
    public static String toUnicode(String str, boolean isSkipAscii, UnicodeEncodingFormat format) {
        if (StrUtil.isEmpty(str)) {
            return str;
        }

        final int len = str.length();
        final StrBuilder unicode = StrBuilder.create(str.length() * 6);
        char c;
        for (int i = 0; i < len; i++) {
            c = str.charAt(i);
            if (isSkipAscii && CharUtil.isAsciiPrintable(c)) {
                unicode.append(c);
            } else {
                unicode.append(String.format(format.format, HexadecimalUtil.toHexadecimal(c), format.toUpperCase));
            }
        }
        return unicode.toString();
    }

    /**
     * 16进制Unicode编码格式
     */
    public enum UnicodeEncodingFormat {
        BACKSLASH_U("\\u%s", false),
        BACKSLASH("\\%s", true),
        BRACKETS("[%s]", false);
        public String format;
        public Boolean toUpperCase;

        UnicodeEncodingFormat(String format, Boolean toUpperCase) {
            this.format = format;
            this.toUpperCase = toUpperCase;
        }

    }
}
