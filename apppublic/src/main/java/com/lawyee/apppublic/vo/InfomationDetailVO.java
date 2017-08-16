package com.lawyee.apppublic.vo;

import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.TimeUtil;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 资讯详情VO
 * @Package com.lawyee.apppublic.vo
 * @Description:
 * @author:wuzhu
 * @date: 2017/5/16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class InfomationDetailVO extends InfomationVO {
    private static final long serialVersionUID = -7065955131920402260L;

    /**
     * 资讯内容
     */
    private String content;

    public String getContent() {
       // return testData()+content+testData2();
        if(StringUtil.isEmpty(getResource()))
            return content;
        //在底部添加来源显示
        return String.format("%s<div align=\"right\" style=\"margin-right: 16px;" +
                "\">来源：%s</div>",content,getResource());
    }

    public void setContent(String content) {
        this.content = content;
    }
}
