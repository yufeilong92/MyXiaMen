package com.lawyee.apppublic.vo;

import net.lawyee.mobilelib.vo.BaseVO;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 服务人员消息VO
 * @Package com.lawyee.apppublic.vo
 * @Description:
 * @author:wuzhu
 * @date: 2017/5/21
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class UserMessageVO extends BaseVO {
    /**
     * 消息来源
     */
    private String source;
    /**
     *消息发起人id
     */
    private String person;
    /**
     *消息发起人姓名
     */
    private String personName;
    /**
     *消息发起人头像
     */
    private String photo;
    /**
     *消息类别id
     */
    private String messageType;
    /**
     *消息类别名称
     */
    private String messageTypeName;
    /**
     *消息时间，yyyy-MM-dd HH:mm:ss
     */
    private String messageTime;
    /**
     * 消息对应的内容ID，如援助预申请ID
     */
    private String messageId;
    /**
     *消息内容
     */
    private String messageContent;

    /**
     * 是否已读 0 未读 1已读
     */
    private String isRead;
}
