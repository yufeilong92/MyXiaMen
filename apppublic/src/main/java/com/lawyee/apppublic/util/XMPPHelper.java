package com.lawyee.apppublic.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.vo.FileMessageVO;
import com.lawyee.apppublic.vo.GeolocationVO;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.vo.BaseVO;

import java.util.regex.Pattern;

/**
 * 聊天信息处理辅助工具类
 */
public class XMPPHelper {
    private static final Pattern EMOTION_URL = Pattern.compile("#face#(\\S+?)#face#");
    //#img#fileId#img#
    private static final Pattern IMG_URL = Pattern.compile("#img#(\\S+?)#img#");
    //#file#fileId#fileName(下载)#file# fileName未附件名称
    private static final Pattern FILE_URL = Pattern.compile("#file#(\\S+?)#file#");
    //map#lng#lat#map
    private static final Pattern MAP_URL = Pattern.compile("map#(\\S+?)#map");

    public static String splitJidAndServer(String account) {
        if (!account.contains("@"))
            return account;
        String[] res = account.split("@");
        String userName = res[0];
        return userName;
    }

    public static String getImageMessage(String fileid)
    {
        if(StringUtil.isEmpty(fileid))
            return fileid;
        return "#img#"+fileid+"#img#";
    }

    public static String getFileMessage(String fileid, String fileName)
    {
        if(StringUtil.isEmpty(fileid))
            return fileid;
        return "#file#"+fileid+"#file#"+fileName+"(下载)#file#";
    }

    public static String getMapMessage(GeolocationVO vo)
    {
        if(vo==null||!vo.isEffect())
            return "";
        return "map#"+vo.getLng()+"#"+vo.getLat()+"#map";
    }


    /**
     * 判断是否是图片消息记录
     * @param message
     * @return
     */
    public static boolean isImageMessage(String message)
    {
        if(StringUtil.isEmpty(message))
            return false;
        if(!message.startsWith("#img#")||!message.endsWith("#img#"))
            return false;

        if(message.length()<11)
            return false;

        return true;
    }

    /**
     * 获取图片消息的文件ID
     * @param message
     * @return 不是图片消息返回空
     */
    public static FileMessageVO getImageMessageFileInfo(String message)
    {
        if(!isImageMessage(message))
            return null;
        FileMessageVO fmvo = new FileMessageVO();
        fmvo.setIsImg(true);
        fmvo.setFileid(message.replace("#img#",""));
        return fmvo;
    }

    public static boolean isFileMessage(String message)
    {
        if(StringUtil.isEmpty(message))
            return false;
        if(!message.startsWith("#file#")||!message.endsWith("#file#"))
            return false;

        if(message.length()<19)
            return false;

        return true;
    }

    /**
     * 获取文件消息的文件信息
     * @param message
     * @return 不是文件消息返回空
     * #file#fileId#fileName(下载)#file# fileName未附件名称
     */
    public static FileMessageVO getFileMessageFileInfo(String message)
    {
        if(!isFileMessage(message))
            return null;

        String fileinfo = message.substring(6);
        fileinfo = fileinfo.substring(0,fileinfo.length()-6);
        int index = fileinfo.indexOf("#file#");
        FileMessageVO fmvo = new FileMessageVO();
        if(index==-1)
            fmvo.setFileid(fileinfo);
        else {
            fmvo.setFileid(fileinfo.substring(0, index));
            fmvo.setFilename(fileinfo.substring(index+6).replace("(下载)",""));
        }
        return fmvo;
    }

    /**
     * 解析消息的信息
     * @param message
     * @return 如果是图片信息或文件信息，返回FileMessageVO，用返回FileMessageVO.isIMG()区分
     * 如果是地图信息，返回GeolocationVO
     */
    public static BaseVO getMessageInfo(String message)
    {
        if(isImageMessage(message))
            return getImageMessageFileInfo(message);
        if(isFileMessage(message))
            return getFileMessageFileInfo(message);
        if(isMapMessage(message))
            return getMapMessageInfo(message);

        return null;
    }

    public static CharSequence generShowMessage(Context c, String message)
    {
        BaseVO messagevo = XMPPHelper.getMessageInfo(message);
        if(messagevo==null)
        {
            //TODO 表情处理
            return  message;
            /*return XMPPHelper
                    .convertNormalStringToSpannableString(c, message, true);*/
        }
        if (messagevo instanceof GeolocationVO) {
            message="[位置信息]";
        }else if(messagevo instanceof FileMessageVO)
        {
            FileMessageVO fmvo = (FileMessageVO)messagevo;

            message=fmvo.isImg()?"[图片]":"[文件:"+fmvo.getFilename()+"]";
        }
        return message;

    }

    /**
     *
     * @param message
     * @return
     * //map#lng#lat#map
     */
    public  static  boolean isMapMessage(String message)
    {
        if(StringUtil.isEmpty(message))
            return false;
        if(!message.startsWith("map#")||!message.endsWith("#map"))
            return false;

        if(message.length()<10)
            return false;

        return true;
    }

    /**
     *
     * @param message
     * @return
     */
    public  static GeolocationVO getMapMessageInfo(String message)
    {
        if(!isMapMessage(message))
            return null;
        String tmpinfo = message.replace("map#","").replace("#map","");
        int index = tmpinfo.indexOf('#');
        if(index==-1||index>=tmpinfo.length()-1)
            return  null;
        GeolocationVO gvo = new GeolocationVO();
        gvo.setLng(tmpinfo.substring(0,index));
        gvo.setLat(tmpinfo.substring(index+1));
        return  gvo;

    }
}
