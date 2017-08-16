package com.lawyee.apppublic.vo;

import android.content.Context;

import net.lawyee.mobilelib.vo.BaseVO;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 用于记录businessid
 * @Package com.lawyee.apppublic.vo
 * @Description:
 * @author:wuzhu
 * @date: 2017/8/2
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class IMBusinessIdVO extends BaseVO {

    private static final long serialVersionUID = -403423186019963388L;
    private String userid;
    private String jid;
    private String businessId;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public static String dataFileName(Context c,String userid,String jid) {
        return dataFileName(c,serialVersionUID,userid+"_"+jid);
    }
    //生成新的业务Id
    public static void setNewBusinessId(Context context,String businessid,String chatOid,String openfireId){
        IMBusinessIdVO vo=new IMBusinessIdVO();
        vo = new IMBusinessIdVO();
        vo.setBusinessId(businessid);
        vo.setJid(chatOid);
        vo.setUserid(openfireId);
        IMBusinessIdVO.saveVO(vo, IMBusinessIdVO.dataFileName(context, openfireId, chatOid));
    }
}
