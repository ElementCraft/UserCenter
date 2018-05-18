package com.fqfx.UserCenter.tools.utils;

public class ValidateUtil {

    /**
     * 验证字符串存在空格
     *
     * @param str 字符串
     * @return
     */
    public static Boolean existSpace(String str) {
        return str.contains(" ");
    }

    /**
     * 验证字符串长度区间
     *
     * @param str 字符串
     * @param min 最小值（包括）
     * @param max 最大值（包括）
     * @return
     */
    public static Boolean lengthBetween(String str, int min, int max) {
        int len = str.length();
        return len >= min && len <= max;
    }

    /**
     * 验证纯数字
     *
     * @param str 字符串
     * @return
     */
    public static Boolean allNumber(String str) {
        return str.matches("^\\d+$");
    }

    /**
     * 验证存在汉字
     *
     * @param str 字符串
     * @return
     */
    public static Boolean existChinese(String str) {
        return str.matches("^.*[\u4E00-\u9FA5].*$");
    }

    /**
     * 验证纯中文汉字
     *
     * @param str 字符串
     * @return
     */
    public static Boolean allChinese(String str) {
        return str.matches("^[\u4E00-\u9FA5]+$");
    }

    /**
     * 验证空字符串
     *
     * @param str 字符串
     * @return
     */
    public static boolean emptyString(String str) {
        return str.equals("") || str.isEmpty();
    }

    /**
     * 验证整数大小范围
     *
     * @param num 整数
     * @param min 最小值（包括）
     * @param max 最大值（包括）
     * @return
     */
    public static boolean integerRange(Integer num, int min, int max) {
        return num >= min && num < max;
    }

    /**
     * 验证手机号格式
     *
     * @param phone 字符串
     * @return
     */
    public static boolean isPhone(String phone) {
        return phone.matches("^1([34578])\\d{9}$");
    }

    /**
     * 验证是否全为空格
     *
     * @param str 验证字符串
     * @return
     */
    public static boolean allSpace(String str) {
        return str.length() >= 1 && "".equals(str.trim());
    }

    /**
     * 验证是否合法邮箱格式
     *
     * @param str 验证字符串
     * @return
     */
    public static boolean isEmail(String str) {
        return str.matches("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
    }
}
