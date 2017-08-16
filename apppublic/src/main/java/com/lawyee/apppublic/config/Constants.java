package com.lawyee.apppublic.config;

import net.lawyee.mobilelib.utils.TimeUtil;

/**
 * 常量
 * 
 * @author wuzhu
 * 
 */
public class Constants extends net.lawyee.mobilelib.Constants {

	/**
	 * 是否可以设置服务器信息，如果可以，则在启动时会检测是否能连接上服务器，不能连接就弹出设置框，并且在设置里增加一个服务器设置的选项
	 */
	public static final Boolean CBOOL_CANCHANGESERVER = false;

	/**
	 * 默认请求记录数
	 */
	public static final int CINT_PAGE_SIZE = 10;
	/**
	 * 资讯数据有效时间
	 */
	public static final long CINT_EFFECTIVE_NEWS_TIME = 30l * TimeUtil.CINT_TIME_MINUTE;

	/**
	 * 请求标识
	 */
	public static final String CSTR_STAFFID = "dev01";

	/**
	 * 加密所需的向量iv
	 */
	public static final String CSTR_IVS = "gzlawservice2017";

	/**
	 * 文件上传接收参数名称
	 */
	public static final String CSTR_UPLOADFILE_PAREAMNAME = "data";

	public static final String CSTR_PAGECODE_DEFAULT = "utf-8";
	/**
	 * 密码长度最少要6位
	 */
	public static final int CINT_PASSWORD_LEN_MIN = 6;
	public static final String NEWS_ID ="7528a99c512c4bd890631c427ab78a21";//新闻资讯oid
	public static final String PROVINCE_GUIZHOU_ID ="520000";//贵州省oid
	public static final String SERVICE_CONTENT_OID="2af6a3f6d9854b4cb3f2cc7b112112af";//服务内容oid
	public static final String  PROFESSIONAL_FIELD_OID="616818ec929f42d08049e100fe4a86a0";//专业领域oid
	public static final String  EPOAPERATION="95bc8ca17f844df9888d14b59094d7ac";//从业年限oid
	public static final String  ISONLINE="7045b14b824048d69aa6c09cb3005a12";//在线oid
	public static final String  JAUUTH_SERVICEAREA_OID="955d1a3fd1194e4e8392d362a9ff64ec";//（鉴定）业务范围oid
	public static final String  JANOTA_SERVICE_AREA_OID="6f2bc7aabe414758aa80fafaac69a48e";//（公证）服务范围oid
    public static final String  APPLY_NATION_OID="98c19b3055cc46158dfe8dccf547475b";//民族oid
    public static final String  APPLY_CERTIFICATES_TYPE_OID="c2c7f943b45e4abf9fe8ce55ac81fa53";//证件类型oid
    public static final String  APPLYGIRL="e10d702b89d849e789039de65cd268cd";//女
    public static final String  APPLYBOY="0f33f0f8cdaf48d0b0cd9522756db346";//男cd1782c287a340b3bcbd44e4e24d42d1
    public static final String  APPLYJAAID_LEGAL="cd1782c287a340b3bcbd44e4e24d42d1";//法定代理人
    public static final String  APPLYJAAID_ENTRUST="c2187a26d5354bcb8f3e40f3ea6f5689";//委托代理人
    public static final String  ROOTPROVINCE="root";//全国

	public static final String APPLYNOPASS="364a21ba87fb4510b28aea3350df3d85";//审核不通过
	public static final String APPLYPASS="7bb86d08acd845c2b23c003353ecc772";//审核通过
	public static final String PERSONAPPLY="5c8c9e991b60413a9174bb38fd5344c7";//个人申请
	public static final String ORGWEITUO="929867e6ffd04d0e9b4e7f9e6067636a";//机构委托
	public static final String APPLYNOTAOID="1d9cca148020439381272d9fb990c703";//公证类型
	public static final String APPLYAILITY="68665233a40c472aa1c19805bb49e3d6";//申请人行为能力
	public static final String APPLYJAMEDNOHANDLE="7f8e719f5288426388efe9c1d9a9019a";//人民调解不予处理原因
	public static final String ACTIVITYOID="a4a4a5a819dd46dc963dcfd1afb1e637";//活动类型
	public static final String CONSULTTYPE="01c689a6162f405c8e0847f12c1b2b8b";//咨询分类
	public static final String VOTEOID="2c0c75c991ba41c7ac8850e7b5c8f74f";//投票类oid

}
