package com.lawyee.apppublic.vo;

import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.vo.BaseVO;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 附件信息VO
 * @Package com.lawyee.apppublic.vo
 * @Description:
 * @author:wuzhu
 * @date: 2017/5/20
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class AttachmentVO extends BaseVO {

    /**
     * 上传子类别名称-法援预申请-身份证
     */
    public static final String CSTR_UPLOADSUB_JAAIDAPPLY_SFZ = "sfz";

    public static final String CSTR_UPLOADSUB_JAAIDAPPLY_SFZ_NAME = "身份证";
    /**
     * 上传子类别名称-法援预申请-经济情况证明材料
     */
    public static final String CSTR_UPLOADSUB_JAAIDAPPLY_JJ = "jj";
    public static final String CSTR_UPLOADSUB_JAAIDAPPLY_JJ_NAME = "经济情况证明材料";
    /**
     * 上传子类别名称-法援预申请-其它与申请有关的案件材料
     */
    public static final String CSTR_UPLOADSUB_JAAIDAPPLY_QT = "qt";
    public static final String CSTR_UPLOADSUB_JAAIDAPPLY_QT_NAME = "其它与申请有关的案件材料";
    /**
     * 上传子类别名称-法援预申请-法律援助委托书
     */
    public static final String CSTR_UPLOADSUB_JAAIDAPPLY_WT = "wt";
    public static final String CSTR_UPLOADSUB_JAAIDAPPLY_WT_NAME = "法律援助委托书";

    /**
     * 上传子类别名称-人民调解-视频
     */
    public static final String CSTR_UPLOADSUB_JAMED_SP = "sp";
    public static final String CSTR_UPLOADSUB_JAMED_SP_NAME = "人民调解视频";
    /**
     * 上传子类别名称-人民调解-调解协议
     */
    public static final String CSTR_UPLOADSUB_JAMED_TJXY = "tjxy";
    public static final String CSTR_UPLOADSUB_JAMED_TJXY_NAME = "调解协议";
    /**
     * 上传子类别名称-人民调解-调解协议
     */
    public static final String CSTR_UPLOADSUB_JAMEDSERVICE_TJXY = "tjsxy";
    public static final String CSTR_UPLOADSUB_JAMEDSERVICE_TJXY_NAME = "调解协议";
    /**
     * 文件大小，单位B
     */
    private String fileSize;
    /**
     * 文件名
     */
    private String name;
    /**
     * 文件描述
     */
    private String description_;

    /**
     * 本地文件存储路径
     */
    private String locfilepath;

    /**
     * 上传子类别，类别详见CSTR_UPLOADSUB_*定义
     */
    private String sub;

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocfilepath() {
        return locfilepath;
    }

    public void setLocfilepath(String locfilepath) {
        this.locfilepath = locfilepath;
    }

    public String getDescription_() {
        return description_;
    }

    public void setDescription_(String description_) {
        this.description_ = description_;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    /**
     * 返回子类别名
     * @return
     */
    public String getSubName()
    {
        if(StringUtil.isEmpty(getSub()))
            return "";

        if(CSTR_UPLOADSUB_JAAIDAPPLY_SFZ.equals(getSub()))
            return CSTR_UPLOADSUB_JAAIDAPPLY_SFZ_NAME;

        if(CSTR_UPLOADSUB_JAAIDAPPLY_QT.equals(getSub()))
            return CSTR_UPLOADSUB_JAAIDAPPLY_QT_NAME;

        if(CSTR_UPLOADSUB_JAAIDAPPLY_WT.equals(getSub()))
            return CSTR_UPLOADSUB_JAAIDAPPLY_WT_NAME;
        if(CSTR_UPLOADSUB_JAAIDAPPLY_JJ.equals(getSub()))
            return CSTR_UPLOADSUB_JAAIDAPPLY_JJ_NAME;
        if(CSTR_UPLOADSUB_JAMED_SP.equals(getSub()))
            return CSTR_UPLOADSUB_JAMED_SP_NAME;
        if(CSTR_UPLOADSUB_JAMED_TJXY.equals(getSub()))
            return CSTR_UPLOADSUB_JAMED_TJXY_NAME;
        if (CSTR_UPLOADSUB_JAMEDSERVICE_TJXY.equals(getSub()))
            return CSTR_UPLOADSUB_JAMEDSERVICE_TJXY_NAME;
        return getSub();
    }
}
