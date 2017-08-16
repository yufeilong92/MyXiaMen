/**
 * Project:RLSClient
 * File:ServerInfoVO.java
 * Copyright 2013 WUZHUPC Co., Ltd. All rights reserved.
 */
package com.lawyee.apppublic.vo;

import android.content.Context;

import net.lawyee.mobilelib.vo.BaseVO;

/**
 * @author wuzhu
 * @date 2013-7-8 下午1:59:58
 * @version $id$
 */
public class ServerInfoVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6621296682000250859L;

	private String mServerIP;
	private String mContextPath;

	public String getContextPath() {
		return mContextPath;
	}

	public void setContextPath(String mContextPath) {
		this.mContextPath = mContextPath;
	}

	public String getServerIP() {
		return mServerIP;
	}

	public void setServerIP(String mServerIP) {
		this.mServerIP = mServerIP;
	}

	/**
	 * 数据存储文件(注意，这个是读取单个对象的名称，读取列表请使用dataListFileName)
	 * 
	 * @return
	 */
	public static String dataFileName(Context c) {
		return dataFileName(c,serialVersionUID);
	}
}
