package com.lawyee.apppublic.vo;

import android.content.Context;

import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.util.UrlUtil;

import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.vo.BaseVO;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 视频资讯VO
 * @Package com.lawyee.apppublic.vo
 * @Description: 用于列表项
 * @author:wuzhu
 * @date: 2017/5/16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class VideoInformationVO extends InfomationVO {
    private static final long serialVersionUID = -2403073023096104552L;
    private static final String CSTR_VIDEOCODE_URLSTART="src='";
    private static final String CSTR_VIDEOCODE_URLEND="'";

    /**
     * 视频url，用于法治宣传-法宣视频
     */
    private String videoUrl;

    /**
     * 视频code,如：<iframe height=498 width=510 src='http://player.youku.com/embed/XMjgzNDg4ODE4OA==' frameborder=0 'allowfullscreen'></iframe>
     */
    private String videoCode;
    /**
     *视频简介
     */
    private String introduction;

    public String getVideoCode() {
        return videoCode;
    }

    public void setVideoCode(String videoCode) {
        this.videoCode = videoCode;
    }

    public String getVideoUrl() {
        if(StringUtil.isEmpty(videoUrl)&&!(StringUtil.isEmpty(videoCode)))
        {
            //从videoCode里解析视频地址
            int index = videoCode.indexOf(CSTR_VIDEOCODE_URLSTART);
            if (index==-1)
                return videoCode;
            String tmp = videoCode.substring(index+CSTR_VIDEOCODE_URLSTART.length());
            index = tmp.indexOf(CSTR_VIDEOCODE_URLEND);
            if(index==-1)
                return tmp;
            return tmp.substring(0,index);
        }
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
