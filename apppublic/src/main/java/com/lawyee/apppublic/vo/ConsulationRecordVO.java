package com.lawyee.apppublic.vo;

import net.lawyee.mobilelib.vo.BaseVO;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 咨询服务聊天记录VO
 * @Package com.lawyee.apppublic.vo
 * @Description:
 * @author:wuzhu
 * @date: 2017/8/1
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class ConsulationRecordVO extends BaseVO {
    private static final long serialVersionUID = 2671773776984568514L;

    /**
     * 发送人id
     */
    private String from;

    /**
     * 接收人id
     */
    private String to;

    /**
     * 内容
     */
    private String content;



    //未读消息数
    private int noReadnum;

    /**
 * 好友名字
 */
    private String friendName;



    /**
     * 好友ID
     */
    private String friendId;
    /**
     * 消息发送时间，格式：yyyy-MM-dd HH:mm:ss
     */
    private String sendTime;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }
    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }
    public int getNoReadnum() {
        return noReadnum;
    }
    public void setNoReadnum(int noReadnum) {
        this.noReadnum = noReadnum;
    }
}
