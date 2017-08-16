package com.lawyee.apppublic.vo;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawerApp
 * @Package com.lawyee.appservice.vo
 * @Description: $todo$
 * @author: YFL
 * @date: 2017/7/4 11:41
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class SelectItemVo {

    private BaseCommonDataVO itemVo;
    private int SelectPosition;//记录业务id

    public SelectItemVo() {
        itemVo=new BaseCommonDataVO();
        SelectPosition = -1;
    }

    public BaseCommonDataVO getItemVo() {
        return itemVo;
    }

    public void setItemVo(BaseCommonDataVO itemVo) {
        this.itemVo = itemVo;
    }

    public int getSelectPosition() {
        return SelectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        SelectPosition = selectPosition;
    }
}
