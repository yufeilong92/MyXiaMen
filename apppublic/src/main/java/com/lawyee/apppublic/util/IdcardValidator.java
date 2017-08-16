package com.lawyee.apppublic.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawerApp
 * @Package com.lawyee.apppublic.util
 * @Description: 身份证证件鉴定
 * @author: YFL
 * @date: 2017/6/10 16:49
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class IdcardValidator {
    // 省,直辖市代码表
    private static final String codeAndCity[][] = { { "11", "北京" }, { "12", "天津" },
            { "13", "河北" }, { "14", "山西" }, { "15", "内蒙古" }, { "21", "辽宁" },
            { "22", "吉林" }, { "23", "黑龙江" }, { "31", "上海" }, { "32", "江苏" },
            { "33", "浙江" }, { "34", "安徽" }, { "35", "福建" }, { "36", "江西" },
            { "37", "山东" }, { "41", "河南" }, { "42", "湖北" }, { "43", "湖南" },
            { "44", "广东" }, { "45", "广西" }, { "46", "海南" }, { "50", "重庆" },
            { "51", "四川" }, { "52", "贵州" }, { "53", "云南" }, { "54", "西藏" },
            { "61", "陕西" }, { "62", "甘肃" }, { "63", "青海" }, { "64", "宁夏" },
            { "65", "新疆" }, { "71", "台湾" }, { "81", "香港" }, { "82", "澳门" },
            { "91", "国外" } };

    // 每位加权因子
    private static final int power[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

    // 判断18位身份证号码是否有效
    private static boolean isValidate18Idcard(String idcard) {
        if (idcard.length() != 18) {
            return false;
        }
        String idcard17 = idcard.substring(0, 17);
        String idcard18Code = idcard.substring(17, 18);
        char c[];
        String checkCode;
        if (isDigital(idcard17)) {
            c = idcard17.toCharArray();
        } else {
            return false;
        }

        if (null != c) {
            int bit[] = converCharToInt(c);
            int sum17 = getPowerSum(bit);

            // 将和值与11取模得到余数进行校验码判断
            checkCode = getCheckCodeBySum(sum17);
            if (null == checkCode) {
                return false;
            }
            // 将身份证的第18位与算出来的校码进行匹配，不相等就为假
            if (!idcard18Code.equalsIgnoreCase(checkCode)) {
                return false;
            }
        }

        return true;
    }

    // 将15位的身份证转成18位身份证
    public static String convertIdcarBy15bit(String idcard) {
        String idcard18 = null;
        if (idcard.length() != 15) {
            return null;
        }

        if (isDigital(idcard)) {
            // 获取出生年月日
            String birthday = idcard.substring(6, 12);
            Date birthdate = null;
            try {
                birthdate = new SimpleDateFormat("yyMMdd").parse(birthday);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cday = Calendar.getInstance();
            cday.setTime(birthdate);
            String year = String.valueOf(cday.get(Calendar.YEAR));

            idcard18 = idcard.substring(0, 6) + year + idcard.substring(8);

            char c[] = idcard18.toCharArray();
            String checkCode = "";

            if (null != c) {
                int bit[] = converCharToInt(c);
                int sum17;
                sum17 = getPowerSum(bit);
                // 获取和值与11取模得到余数进行校验码
                checkCode = getCheckCodeBySum(sum17);
                // 获取不到校验位
                if (null == checkCode) {
                    return null;
                }

                // 将前17位与第18位校验码拼接
                idcard18 += checkCode;
            }
        } else {
            return null;
        }

        return idcard18;
    }

    // 是否全部由数字组成
    public static boolean isDigital(String str) {
        return str == null || "".equals(str) ? false : str.matches("^[0-9]*$");
    }

    // 将身份证的每位和对应位的加权因子相乘之后，再得到和值
    public static int getPowerSum(int[] bit) {
        int sum = 0;
        if (power.length != bit.length) {
            return sum;
        }

        for (int i = 0; i < bit.length; i++) {
            for (int j = 0; j < power.length; j++) {
                if (i == j) {
                    sum = sum + bit[i] * power[j];
                }
            }
        }

        return sum;
    }

    // 将和值与11取模得到余数进行校验码判断
    private static String getCheckCodeBySum(int sum17) {
        String checkCode = null;
        switch (sum17 % 11) {
            case 10:
                checkCode = "2";
                break;
            case 9:
                checkCode = "3";
                break;
            case 8:
                checkCode = "4";
                break;
            case 7:
                checkCode = "5";
                break;
            case 6:
                checkCode = "6";
                break;
            case 5:
                checkCode = "7";
                break;
            case 4:
                checkCode = "8";
                break;
            case 3:
                checkCode = "9";
                break;
            case 2:
                checkCode = "x";
                break;
            case 1:
                checkCode = "0";
                break;
            case 0:
                checkCode = "1";
                break;
        }

        return checkCode;
    }

    // 将字符数组转为整型数组
    private static int[] converCharToInt(char[] c) throws NumberFormatException {
        int[] a = new int[c.length];
        int k = 0;
        for (char temp : c) {
            a[k++] = Integer.parseInt(String.valueOf(temp));
        }

        return a;
    }

    // 验证身份证号码是否有效
    public static boolean isValidateIdcard(String idcard) {
        if (!TextUtils.isEmpty(idcard)) {
            if (idcard.length() == 15) {
                return isValidate18Idcard(convertIdcarBy15bit(idcard));
            } else if (idcard.length() == 18) {
                return isValidate18Idcard(idcard);
            }
        }

        return false;
    }
}

