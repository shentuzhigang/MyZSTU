package club.zstuca.myzstu.utils.core;

import cn.hutool.core.util.HexUtil;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-09-23 13:24
 */
public class HexadecimalUtil extends HexUtil {
    /**
     * 用于建立十六进制字符的输出的小写字符数组
     */
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    /**
     * 用于建立十六进制字符的输出的大写字符数组
     */
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};


    public static String toHexadecimal(char ch) {
        return toHexadecimal(ch, false);
    }

    /**
     * 将指定char值转换为Unicode字符串形式，常用于特殊字符（例如汉字）转Unicode形式<br>
     * 转换的字符串如果不足4位，则前面用0填充，例如：
     *
     * <pre>
     * '我' =4f60
     * </pre>
     *
     * @param ch char值
     * @return Unicode表现形式
     * @since 4.0.1
     */
    public static String toHexadecimal(char ch, Boolean toUpperCase) {
        final char[] DIGITS = toUpperCase ? DIGITS_UPPER : DIGITS_LOWER;
        return "" +
                DIGITS[(ch >> 12) & 15] +
                DIGITS[(ch >> 8) & 15] +
                DIGITS[(ch >> 4) & 15] +
                DIGITS[(ch) & 15];
    }

}
