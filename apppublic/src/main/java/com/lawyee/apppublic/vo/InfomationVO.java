package com.lawyee.apppublic.vo;

import android.content.Context;

import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.util.UrlUtil;

import net.lawyee.mobilelib.Constants;
import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.TimeUtil;
import net.lawyee.mobilelib.vo.BaseVO;

import java.util.Date;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 资讯VO
 * @Package com.lawyee.apppublic.vo
 * @Description: 用于列表项
 * @author:wuzhu
 * @date: 2017/5/16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class InfomationVO extends BaseVO {

    private static final long serialVersionUID = 8002261118755852221L;

    /**
     * 资讯信息图片
     */
    private String photo;

    /**
     * 标题
     */
    private String title;

    /**
     * 分类ID
     */
    private String category;

    /**
     * 分类名称（法律援助、司法考试、公证服务、人民调解、鉴定机构）
     */
    private String categoryName;

    /**
     * 发布时间(yyyy-MM-dd)
     */
    private String publishDate;

    public String getPhoto() {
        return photo;
    }
    /**
     * 发布机构名称
     */
    private String publishOrganizationName;
    /**
     * 来源
     */
    private String resource;

    /**
     * 获取图片读取地址
     * @param c
     * @return
     */
    public String getPhotoUrl(Context c) {
        return UrlUtil.getImageFileUrl(c,photo);
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPublishOrganizationName() {
        return publishOrganizationName;
    }

    public void setPublishOrganizationName(String publishOrganizationName) {
        this.publishOrganizationName = publishOrganizationName;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getCategoryName() {
        if(StringUtil.isEmpty(categoryName)&&!StringUtil.isEmpty(getCategory()))
        {
            categoryName = DataDictionaryVO.getNameWithOid(ApplicationSet.getInstance().getDataDictionarys(),getCategory());
        }
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publicTime) {
        this.publishDate = publicTime;
    }


    public String getDataFileName(Context c) {
        return Constants.getDataStoreDir(c)
                + Constants.CSTR_DETAILDIR
                + getOid()
                + TimeUtil.dateToString(
                TimeUtil.strToDate(getPublishDate(), new Date()),
                "yyyyMMddHHmmss") + ".pd";
    }

    /**
     * 数据存储文件名(注意，这个是读取单个对象的名称，读取列表请使用dataListFileName)
     *
     * @return
     */
    public static String dataFileName(Context c,String suf) {

        return dataFileName(c,serialVersionUID,suf);
    }

    /**
     * 数据列表存储文件名
     *
     * @return
     */
    public static String dataListFileName(Context c,String suf) {
        return dataListFileName(c,serialVersionUID,suf);
    }



    /**
     * 返回详情HTML标题
     *
     * @return
     */
    public String getHtmlTitle() {
        String result = "<br/><div align=\"center\" style=\"font-size: 16pt;\"><font color=\"#333333\"><strong>"
                + title + "</strong></font></div><br/>";
        return result;
    }

    /**
     * 返回详情HTML子标题
     *
     * @return
     */
    public String getHtmlSubTitle() {
        String result = "<div align=\"center\" style=\"margin-top: 8px;margin-bottom: 8px;font-size: 10.5pt;\">" +
                "<font color=\"#666666\">发布机构:</font>" +
                "<font color=\"#999999\">"+((StringUtil.isEmpty(getPublishOrganizationName())) ? ""
                : getPublishOrganizationName())
                +"&nbsp;&nbsp;</font>" +
                "<font color=\"#666666\">发布时间:</font>" +
                "<font color=\"#999999\">"+ TimeUtil.dateToString(TimeUtil.strToDate(getPublishDate(),null),"yyyy-MM-dd")+"</font>" +
                "</div>" +
                "<div style=\"height:0;border-bottom:1px solid #999999;margin-bottom: 8px;margin-right: 8px;margin-left: 8px\"></div>";
        return result;
    }
}
