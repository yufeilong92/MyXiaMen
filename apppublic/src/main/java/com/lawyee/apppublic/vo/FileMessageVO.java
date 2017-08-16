package com.lawyee.apppublic.vo;

import android.content.Context;

import com.lawyee.apppublic.util.UrlUtil;

import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.vo.BaseVO;

/**
 * 文件信息类
 * Created by wuzhu on 16/3/21.
 */
public class FileMessageVO extends BaseVO {
    private boolean bimg;//是否是图片信息
    private String fileid;//文件id
    private String filename;//文件名称
    private String fileurl;//文件访问地址
    private String filelocurl;//本地地址

    public boolean isImg() {
        return bimg;
    }

    public void setIsImg(boolean bimg) {
        this.bimg = bimg;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String getFilelocurl() {
        return filelocurl;
    }

    public void setFilelocurl(String filelocurl) {
        this.filelocurl = filelocurl;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileurl(Context c) {
        if(StringUtil.isEmpty(fileurl)|| !StringUtil.isEmpty(fileid))
        {
            if(isImg())
                return UrlUtil.getImageFileUrl(c,fileid);

            return UrlUtil.getFileUrl(c,fileid);
        }
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }
}
