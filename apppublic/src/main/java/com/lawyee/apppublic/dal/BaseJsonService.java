/**
 * Project:newsreader2
 * File:BaseJsonService.java
 * Copyright 2013 WUZHUPC Co., Ltd. All rights reserved.
 */
package com.lawyee.apppublic.dal;

import android.content.Context;
import android.util.Log;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.util.UrlUtil;
import com.lawyee.apppublic.util.net.NetTask;

import net.lawyee.mobilelib.json.JsonParser;
import net.lawyee.mobilelib.utils.FileUtil;
import net.lawyee.mobilelib.utils.NetUtil;
import net.lawyee.mobilelib.utils.PhoneInfoUtil;
import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.vo.ResponseVO;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * 基础数据接口service
 * 
 * @author wuzhu
 * @date 2013-4-25 下午9:00:54
 * @version $id$
 * @note 注意以下参数： DEBUG true时输出报文内容 LOCAL_DEBUG true时读取本地文件(assets文件夹中的文件)
 */
public class BaseJsonService {
	private static final String TAG = BaseJsonService.class.getSimpleName();
	/**
	 * true时输出报文内容
	 */
	private static final Boolean DEBUG = true;

	/**************************** 服务器相关读取参数 *****************************/
	/**
	 * 手机服务端接收参数接口
	 */
	private static final String CSTR_REQUEST_MOBILE = "request";

	/**************************** 用于测试读取本地还是直接从服务器获取相关参数 *****************************/
	/**
	 * true时读取本地文件(assets文件夹中的文件)
	 */
	private Boolean mLocalDebug = false;

	public Boolean getLocalDebug() {
		return mLocalDebug;
	}

	public void setLocalDebug(Boolean localDebug) {
		this.mLocalDebug = localDebug;
	}

	/**
	 * 当前执行命令，，LOCAL_DEBUG为true时生效，此变量值得为读取assets中的文件名开头
	 * 规则：mCommandName+"_response_data"
	 * +(StringUtil.isEmpty(mSuffixStr)?"":"_"+mSuffixStr)+".json"
	 */
	protected String mCommandName;
	/**
	 * 辅助文件名，LOCAL_DEBUG为true时生效，此变量值得为读取assets中的文件名一部分
	 * 规则：mCommandName+"_response_data"
	 * +(StringUtil.isEmpty(mSuffixStr)?"":"_"+mSuffixStr)+".json"
	 */
	protected String mSuffixStr;

	/**
	 * 是否启用随机，LOCAL_DEBUG为true时生效，此变量值得为读取assets中的文件名一部分 规则： int random = (int)
	 * (Math.random()*mRandomMax); datafilename =
	 * mCommandName+"_response_data_"+Integer.toString(random)
	 * +(StringUtil.isEmpty(mSuffixStr)?"":"_"+mSuffixStr)+".json";
	 */
	protected boolean mRandomReadData;
	/**
	 * 是否启用随机，LOCAL_DEBUG为true时生效，此变量值得为读取assets中的文件名一部分 规则： int random = (int)
	 * (Math.random()*mRandomMax); datafilename =
	 * mCommandName+"_response_data_"+Integer.toString(random)
	 * +(StringUtil.isEmpty(mSuffixStr)?"":"_"+mSuffixStr)+".json";
	 */
	protected int mRandomMax;

	/*********************************************************/
	protected Context mContext;

	private IBaseReceiver mBaseReceiver;

	private Boolean mShowProgress;
	private String mProgressShowContent;

	/**************************** 自动解析参数 *****************************/
	public static final int CINT_VALUETYPE_HASHMAP = 0;
	public static final int CINT_VALUETYPE_ENTITY = 1;
	public static final int CINT_VALUETYPE_LIST = 2;

	private ArrayList<Integer> mValueTypes;
	private ArrayList<String> mRefs;

	private IResultInfoListener mResultInfoListener;

	/**
	 * 
	 * @param c
	 */
	public BaseJsonService(Context c) {
		mContext = c;
		mSuffixStr = "";
		mCommandName = "";
		mRandomMax = 10;
		mRandomReadData = false;
		mShowProgress = false;
		mProgressShowContent = null;
	}

	protected String getDevID() {
		return PhoneInfoUtil.GetIMEI(mContext);
	}


	protected boolean checkRequired(IResultInfoListener il,String value,String hint)
	{
		if(StringUtil.isEmpty(value))
		{
			il.onError(hint,"");
			return false;
		}
		return true;
	}

	/**
	 * 获取数据
	 * 
	 * @param json
	 *            　请求报文
	 * @param url
	 *            　地址
	 */
	protected void getData(String json, String url) {

		if (StringUtil.isEmpty(json)) {
			errorresult("缺少请求数据");
			return;
		}

		if (DEBUG)
			Log.d(TAG, " json = " + json);

		//
		if (mLocalDebug) {

			String datafilename = mCommandName + "_response_data"
					+ (StringUtil.isEmpty(mSuffixStr) ? "" : "_" + mSuffixStr)
					+ ".json";

			/**
			 * 随机值，目前用于测试推送信息时随机读取
			 */
			if (mRandomReadData) {
				int random = (int) (Math.random() * mRandomMax);
				datafilename = mCommandName
						+ "_response_data_"
						+ Integer.toString(random)
						+ (StringUtil.isEmpty(mSuffixStr) ? "" : "_"
								+ mSuffixStr) + ".json";

			}
			String localjsoncontent = FileUtil.readFileFromAssetsFile(mContext,
					datafilename);
			if (StringUtil.isEmpty(localjsoncontent))
				errorresult("缺少内容数据");
			else {
				completeresult(true, localjsoncontent);
			}
			return;
		}
		if (NetUtil.getNetWorkType(mContext) == NetUtil.CINT_CONNECTTYPE_NOTCONNECT) {
			if (DEBUG)
				Log.d(TAG, "当前无可用网络");
			errorresult("当前无可用网络");
			return;
		}

		Hashtable<String, String> param = new Hashtable<String, String>();
		param.put(CSTR_REQUEST_MOBILE, json);
		NetTask netTask = new NetTask(param, "utf-8", Constants.METHOD_POST,
				new NetTask.INetComplete() {
					@Override
					public void complete(boolean isSuc, String content) {
						//content = content.replace("\\\"","\"");
						if (DEBUG)
							Log.d(TAG, " content = " + content);
						/*String jsoncontent = isSuc ? content : mContext
								.getString(R.string.prompt_network_error);*/

						completeresult(isSuc, content);
					}
				});

		netTask.setContext(mContext);
		netTask.setProgressContent(getProgressShowContent());
		netTask.setShowProgress(getShowProgress());
		netTask.execute(StringUtil.isEmpty(url) ? UrlUtil.getUrl(mContext,
				R.string.url_json) : url);
		Log.e("url", UrlUtil.getUrl(mContext,
				R.string.url_json)+"~");
	} 

	private void completeresult(boolean issuc, String content) {
		if (!issuc)
			errorresult(content);
		else {
			if (getBaseReceiver() != null)
				getBaseReceiver().receiveCompleted(true, content);
			if (getResultInfoListener() != null)
				this.parserContent(content);

		}

	}

	private void errorresult(String msg) {
		if (getBaseReceiver() != null)
			getBaseReceiver().receiveCompleted(false, msg);
		if (getResultInfoListener() != null)
			getResultInfoListener().onError(msg, msg);
	}

	/**
	 * 从给定的某个url地址读取内容
	 *
	 */
	public void getURLData(String url) {
		if (StringUtil.isEmpty(url)) {
			errorresult("缺少数据获取地址");
			return;
		}
		// 判断链接地址是文件还是网络地址
		if (FileUtil.isAssetsFileStr(url)) {
			String localcontent = FileUtil.readFileFromAssetsFullFile(mContext,
					url);
			if (StringUtil.isEmpty(localcontent))
				errorresult("缺少数据内容");
			else
				completeresult(true, localcontent);
			return;
		}
		if (NetUtil.getNetWorkType(mContext) == NetUtil.CINT_CONNECTTYPE_NOTCONNECT) {
			if (DEBUG)
				Log.d(TAG, "当前无可用网络");
			errorresult("当前无可用网络");
			return;
		}
		NetTask netTask = new NetTask("utf-8", new NetTask.INetComplete() {
			@Override
			public void complete(boolean isSuc, String content) {
				if (DEBUG)
					Log.d(TAG, " content = " + content);
				String jsoncontent = isSuc ? content
						: mContext.getString(R.string.prompt_network_error);
				complete(isSuc, jsoncontent);
			}
		});
		netTask.setContext(mContext);
		netTask.setProgressContent(getProgressShowContent());
		netTask.setShowProgress(getShowProgress());
		netTask.execute(url);
	}

	/**
	 * 信息接收接口
	 * 
	 * @author wuzhu
	 * 
	 */
	public interface IBaseReceiver {

		/**
		 * 接收完成
		 * 
		 * @param isSuc
		 *            是否成功
		 * @param content
		 *            获取成功，返回内容，失败，返回失败原因
		 */
		public void receiveCompleted(boolean isSuc, String content);
	}

	/**
	 * 自动解析参数接口
	 * 
	 * @author wuzhu
	 * @date 2013-6-20 上午9:58:05
	 * @version $id$
	 */
	public interface IResultInfoListener {
		/**
		 * 成功，并解析成结果数据
		 * 
		 * @param values
		 *            对应ValueTypes。如果只判断结果是否正确，则这个值为空
		 * @param content
		 *            获取到的原始返回报文
		 */
		public void onComplete(ArrayList<Object> values, String content);

		/**
		 * 失败
		 * 
		 * @param msg
		 *            失败提醒信息
		 * @param content
		 *            获取到的原始返回报文或失败信息
		 */
		public void onError(String msg, String content);
	}

	public void parserContent(String content) {
		if (getResultInfoListener() == null)
			return;
		if (StringUtil.isEmpty(content)) {
			getResultInfoListener().onError("缺少数据内容", content);
			return;
		}
		if (getValueTypes() == null || getValueTypes().isEmpty()) {
			// 只检查成功或失败
			ResponseVO respVO = JsonParser.parseJsonToResponse(content);
			if (respVO.isSucess())
				getResultInfoListener().onComplete(null, content);
			else
				getResultInfoListener().onError(respVO.getMsg(), content);
			return;
		}
		ArrayList<Object> result = new ArrayList<Object>();
		ResponseVO respVO = new ResponseVO();
		for (int i = 0; i < getValueTypes().size(); i++) {
			String ref = null;
			if (getRefs() != null && getRefs().size() > i) {
				ref = getRefs().get(i);
			}
			// boolean isnull = true;
			switch (getValueTypes().get(i)) {
			case CINT_VALUETYPE_HASHMAP:
				Map<String, String> tmp = JsonParser.parseJsonToMap(content,
						respVO);
				if (tmp != null) {
					// isnull = false;
					result.add(tmp);
				}
				break;
			case CINT_VALUETYPE_ENTITY:
				Object o = JsonParser.parseJsonToEntity(content, respVO, ref);
				if (o != null) {
					// isnull = false;
					result.add(o);
				}
				break;
			case CINT_VALUETYPE_LIST:
				List<?> tmplist = JsonParser.parseJsonToList(content, respVO,
						ref);
				if (tmplist != null) {
					// isnull = false;
					result.add(tmplist);
				}
				break;
			default:
				break;
			}
			if (!respVO.isSucess()) {
				getResultInfoListener().onError(respVO.getMsg(), content);
				return;
			}
			// else if (isnull)
			// {
			// getResultInfoListener().onError("无效的数据内容", content);
			// return;
			// }
		}
		getResultInfoListener().onComplete(result, content);
	}

	public ArrayList<String> getRefs() {
		return mRefs;
	}

	public void setRefs(ArrayList<String> refs) {
		this.mRefs = refs;
	}

	public void setRef(String ref) {
		this.mRefs = new ArrayList<String>();
		mRefs.add(ref);
	}

	public ArrayList<Integer> getValueTypes() {
		return mValueTypes;
	}

	public void setValueType(ArrayList<Integer> valueTypes) {
		this.mValueTypes = valueTypes;
	}

	public void setValueType(int valueType) {
		this.mValueTypes = new ArrayList<Integer>();
		mValueTypes.add(valueType);
	}

	public IResultInfoListener getResultInfoListener() {
		return mResultInfoListener;
	}

	public void setResultInfoListener(IResultInfoListener resultInfoListener) {
		this.mResultInfoListener = resultInfoListener;
	}

	public IBaseReceiver getBaseReceiver() {
		return mBaseReceiver;
	}

	public void setBaseReceiver(IBaseReceiver baseReceiver) {
		this.mBaseReceiver = baseReceiver;
	}

	public Boolean getShowProgress() {
		return mShowProgress;
	}

	public void setShowProgress(Boolean showProgress) {
		this.mShowProgress = showProgress;
	}

	public String getProgressShowContent() {
		return mProgressShowContent;
	}

	public void setProgressShowContent(String progressShowContent) {
		this.mProgressShowContent = progressShowContent;
	}
}
