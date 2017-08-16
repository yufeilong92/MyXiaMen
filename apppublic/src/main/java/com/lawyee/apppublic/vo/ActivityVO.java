package com.lawyee.apppublic.vo;

import android.content.Context;

import net.lawyee.mobilelib.vo.BaseVO;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 法宣活动VO
 * @Package com.lawyee.apppublic.vo
 * @Description:
 * @author:wuzhu
 * @date: 2017/8/2
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class ActivityVO extends BaseVO {
    private static final long serialVersionUID = -8907415193269432410L;

    /**
     * 活动标题
     */
    private String title;

    /**
     * 活动封面
     */
    private String photo;
    /**
     *活动类型id
     */
    private String category;
    /**
     *发布时间，yyyy-MM-dd
     */
    private String publishDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
    public static String dataListFileName(Context c, String filterName) {
        return dataListFileName(c,serialVersionUID,filterName);
    }
}
