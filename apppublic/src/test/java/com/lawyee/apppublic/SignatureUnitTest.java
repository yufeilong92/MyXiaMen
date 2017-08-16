package com.lawyee.apppublic;

import com.lawyee.apppublic.config.Constants;

import net.lawyee.mobilelib.utils.RandomStrUtil;
import net.lawyee.mobilelib.utils.SignatureUtil;
import net.lawyee.mobilelib.utils.TimeUtil;

import org.junit.Test;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 标题
 * @Package com.lawyee.apppublic
 * @Description:
 * @author:wuzhu
 * @date: 2017/5/23
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class SignatureUnitTest {
    @Test
    public void signatureTest()
    {
        //添加验证消息相关
        String timespan = TimeUtil.dateToString(null,"yyyyMMddHHmmss");
        System.out.println(timespan);
        String nonce = RandomStrUtil.getRandomString(4);
        System.out.println(nonce);
        String requestStr = "request{\"中文内容\"}";
        System.out.println(requestStr);
        String signature = SignatureUtil.generateSignature(timespan,nonce, Constants.CSTR_STAFFID,requestStr.toString());
        System.out.println(signature);
    }
}
