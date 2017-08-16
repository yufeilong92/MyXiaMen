/**
 * Project:RLSClient
 * File:UrlUtil.java
 * Copyright 2013 WUZHUPC Co., Ltd. All rights reserved.
 */
package com.lawyee.apppublic.util;

import android.content.Context;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.vo.ServerInfoVO;

import net.lawyee.mobilelib.utils.JavaLangUtil;
import net.lawyee.mobilelib.utils.StringUtil;

/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  服务地址处理类
 * @Package com.lawyee.apppublic.util
 * @Description:    处理服务端相关的请求地址
 * @author:wuzhu
 * @date:   2017/4/28
 * @version 
 * @verdescript   2017/4/28  wuzhu 初建
 * @Copyright: 2017/4/28 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class UrlUtil {
	private static final String CSTR_BASE = "$base";
	private static final String CSTR_PRO = "$pro";
	private static final String CSTR_IMAGEID = "[oid]";
	private static final String CSTR_FILEID = "[oid]";
	private static final String CSTR_LNG = "[lng]";
	private static final String CSTR_LAT = "[lat]";


	private static ServerInfoVO mServerInfoVO;

	public static String getUrl(Context c, String url) {
		if (StringUtil.isEmpty(url))
			return url;
		if (mServerInfoVO == null)
			mServerInfoVO = getServerInfoVO(c);
		if (mServerInfoVO != null) {
			return getUrl(c, url, mServerInfoVO);
		}

		return url.replace(CSTR_BASE, c.getString(R.string.url_base))
				.replace(CSTR_PRO, c.getString(R.string.url_base_pro));
	
	}

	public static String getUrl(Context c, String url, ServerInfoVO sivo) {
		if (StringUtil.isEmpty(url))
			return url;
		if (sivo != null) {
			return url.replace(CSTR_BASE, sivo.getServerIP()).replace(CSTR_PRO,
					sivo.getContextPath());
		}

		return url.replace(CSTR_BASE, c.getString(R.string.url_base))
				.replace(CSTR_PRO, c.getString(R.string.url_base_pro));
	}

	public static String getUrl(Context c, int urlresid) {
		return getUrl(c, c.getString(urlresid));
	}

	public static void setServerInfo(Context c,ServerInfoVO sivo, boolean bsave) {
		mServerInfoVO = sivo;
		if (bsave)
			ServerInfoVO.saveVO(mServerInfoVO, ServerInfoVO.dataFileName(c));
	}

	public static ServerInfoVO getServerInfoVO(Context c) {
		ServerInfoVO serverInfoVO = (ServerInfoVO) ServerInfoVO
				.loadVO(ServerInfoVO.dataFileName(c));
		if (serverInfoVO == null || !Constants.CBOOL_CANCHANGESERVER) {
			
			
			serverInfoVO = new ServerInfoVO();
			serverInfoVO.setServerIP(c.getString(R.string.url_base));
			serverInfoVO.setContextPath(c.getString(R.string.url_base_pro));
			setServerInfo(c,serverInfoVO, true);
		}
		return serverInfoVO;
	}

	/**
	 * 根据图片文件文件id返回图片附件地址
	 * @param photo 图片id
	 * @return
     */
	public static String getImageFileUrl(Context c,String photo)
	{
		if(StringUtil.isEmpty(photo))
			return photo;
		String result = getUrl(c,c.getString(R.string.url_imagefileurl)).replace(CSTR_IMAGEID,photo);
		return  result;
	}
	/**
	 * 根据一般文件文件id返回图片附件地址
	 * @param fileid 图片id
	 * @return
	 */
	public static String getFileUrl(Context c,String fileid)
	{
		if(StringUtil.isEmpty(fileid))
			return fileid;
		String result = getUrl(c,c.getString(R.string.url_fileurl)).replace(CSTR_FILEID,fileid);
		return  result;
	}

	/**
	 * 文件上传地址
	 * @return
	 */
	public static String getUploadFileUrl(Context c)
	{
		String result = getUrl(c,c.getString(R.string.url_uploadfileurl));
		return  result;
	}

	/**
	 * 根据经纬度获取静态图地址
	 * @param c
	 * @param lng 经度
	 * @param lat 纬度
	 * @return 如果非法的则返回空字符串
	 */
	public static String getStaticMapImgUrl(Context c,String lng,String lat)
	{
		if(StringUtil.isEmpty(lng)||StringUtil.isEmpty(lat))
			return "";
		double dlng = JavaLangUtil.StrToDouble(lng,0.0d);
		double dlat = JavaLangUtil.StrToDouble(lat,0.0d);
		if(dlng==0.0d||dlat==0.0d)
			return "";
		return getStaticMapImgUrl(c,dlng,dlat);
	}

	/**
	 * 根据经纬度获取静态图地址
	 * @param c
	 * @param lng 经度
	 * @param lat 纬度
	 * @return
	 */
	public static String getStaticMapImgUrl(Context c,double lng,double lat)
	{
		String baseurl = "http://api.map.baidu.com/staticimage?center=[lng]%2C[lat]&zoom=15&width=480&height=320&markers=[lng]%2C[lat]&markerStyles=l%2CA";
		String result = baseurl
				.replace(CSTR_LNG,String.valueOf(lng))
				.replace(CSTR_LAT,String.valueOf(lat));
		return  result;
	}
}
