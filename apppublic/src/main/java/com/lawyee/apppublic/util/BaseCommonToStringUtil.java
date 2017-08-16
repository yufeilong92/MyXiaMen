package com.lawyee.apppublic.util;

import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.vo.BaseCommonDataVO;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.util
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @author: lzh
 * @date: 2017/6/7 17:25
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class BaseCommonToStringUtil {
    /**
     * 得到BaseCommonDataVO的name
     */
    public static String  toString(String oid){
        BaseCommonDataVO baseCommonDataVO2=
                BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getDataDictionarys(),oid);
        String str= "";
        if(baseCommonDataVO2!=null){
            str=baseCommonDataVO2.getName();
        }
        return str;
    }
    /**
     * 得到BaseCommonDataVO的name
     */
    public static String  toAreaString(String oid){
        BaseCommonDataVO baseCommonDataVO2=
                BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getAreas(),oid);
        String str= "";
        if(baseCommonDataVO2!=null){
            str=baseCommonDataVO2.getName();
        }
        return str;
    }
}
