package com.lawyee.apppublic.util;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawerApp
 * @Package com.lawyee.apppublic.util
 * @Description:
 * @author: YFL
 * @date: 2017/6/10 16:49
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class VerificationUtil {
    // 判断手机号码是否有效
    public static boolean isValidTelNumber(String telNumber) {
        if (!TextUtils.isEmpty(telNumber)) {
            String regex = "(\\+\\d+)?1[34578]\\d{9}$";
            return Pattern.matches(regex, telNumber);
        }

        return false;
    }

    // 判断邮箱地址是否有效
    public static boolean isValidEmailAddress(String emailAddress) {

        if (!TextUtils.isEmpty(emailAddress)) {
            String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
            return Pattern.matches(regex, emailAddress);
        }

        return false;
    }

    // 判断内容是否由字母，数字，下划线组成
    public static boolean isValidContent(String content) {

        if (!TextUtils.isEmpty(content)) {
            String regex = "^[\\w\\u4e00-\\u9fa5]+$";
            return Pattern.matches(regex, content);
        }

        return false;
    }

    // 判断身份证号码是否有效
    public static boolean isValidIdCard(String idCard) {
        return IdcardValidator.isValidateIdcard(idCard);
    }

    // 校验身份证的基本组成
    public boolean isIdcard(String idCard) {
        if (!TextUtils.isEmpty(idCard)) {
            String regex = "(^\\d{15}$)|(\\d{17}(?:\\d|x|X)$)";
            return Pattern.matches(regex, idCard);
        }

        return false;
    }

    // 校验15身份证的基本组成
    public boolean is15Idcard(String idCard) {
        if (!TextUtils.isEmpty(idCard)) {
            String regex = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
            return Pattern.matches(regex, idCard);
        }

        return false;
    }

    // 校验18身份证的基本组成
    public boolean is18Idcard(String idCard) {
        if (!TextUtils.isEmpty(idCard)) {
            String regex = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$";
            return Pattern.matches(regex, idCard);
        }

        return false;
    }
    //  军官证或士兵证 校验
    public static boolean isOfficerCard(String idCard){
         if (!TextUtils.isEmpty(idCard)){
             String regex="^[a-zA-Z0-9]{7,21}$";
             return Pattern.matches(regex,idCard);
         }
         return false;
    }
}
