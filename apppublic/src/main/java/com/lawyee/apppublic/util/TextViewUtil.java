package com.lawyee.apppublic.util;

import android.widget.EditText;
import android.widget.TextView;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.util
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @author: lzh
 * @date: 2017/6/7 16:55
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class TextViewUtil {
    /**
     * 字符串是否为空，包括空字符串或null
     *TextView
     * @param str
     * @return
     */
    public static void  isEmpty(TextView view,String str)
    {
        if(str == null || "".equals(str.trim())){
            view.setText("");
        }else{
            view.setText(str);
        }
    }
    /**
     * 字符串是否为空，包括空字符串或null
     *EditText
     * @param str
     * @return
     */
    public static void  isEmpty(EditText view, String str)
    {
        if(str == null || "".equals(str.trim())){
            view.setText("");
        }else{
            view.setText(str);
        }
    }
}
