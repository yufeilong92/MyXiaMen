package com.lawyee.apppublic.vo;

import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.vo.BaseVO;

import java.util.ArrayList;
import java.util.List;

import static com.lawyee.apppublic.config.Constants.NEWS_ID;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 基础数据通用VO
 * @Package com.lawyee.apppublic.vo
 * @Description:
 * @author:wuzhu
 * @date: 2017/5/16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class BaseCommonDataVO extends BaseVO {

    private static final long serialVersionUID = -8162104665511779514L;
    /**
     * 名称
     */
    private String name;
    /**
     *描述
     */
    private String description;
    /**
     *编码
     */
    private String enCode;

    /**
     * 排序
     */
    private String index;

    /**
     * 父级id
     */
    private String parentId;

    /**
     * 拼音
     */
    private String pinyin;
    /**
     * 根据oid查找数据VO 递归查找
     * @param datas 全部数据列表
     * @param oid 需要查找的oid
     * @return 找到返回数据VO，未找到返回null
     */
    public static BaseCommonDataVO findParentDataVOWithOid(List datas,String oid)
    {
        if(datas==null||datas.isEmpty()||StringUtil.isEmpty(oid)||
                !(datas.get(0) instanceof BaseCommonDataVO))
            return null;
        List<BaseCommonDataVO> cdvos = datas;
        for (BaseCommonDataVO dvo:cdvos
                ) {
            if(oid.equals(dvo.oid)){
                if(dvo.getParentId().equals(NEWS_ID)){
                    return dvo;
                }else{
                    return findParentDataVOWithOid(datas,dvo.getParentId());
                }

            }

        }
        return null;
    }

    /**
     * 根据oid查找数据VO
     * @param datas 全部数据列表
     * @param oid 需要查找的oid
     * @return 找到返回数据VO，未找到返回null
     */
    public static BaseCommonDataVO findDataVOWithOid(List datas,String oid)
    {
        if(datas==null||datas.isEmpty()||StringUtil.isEmpty(oid)||
            !(datas.get(0) instanceof BaseCommonDataVO))
            return null;
        List<BaseCommonDataVO> cdvos = datas;
        for (BaseCommonDataVO dvo:cdvos
                ) {
            if(oid.equals(dvo.oid))
                return dvo;
        }
        return null;
    }
    /**
     * 根据oid查找数据VO索引
     * @param datas 全部数据列表
     * @param oid 需要查找的oid
     * @return 找到返回数据VO索引，未找到返回-1
     */
    public static int findIndexWithOid(List datas,String oid)
    {
        if(datas==null||datas.isEmpty()||StringUtil.isEmpty(oid)||
                !(datas.get(0) instanceof BaseCommonDataVO))
            return -1;
        List<BaseCommonDataVO> cdvos = datas;
        for(int i =0;i<cdvos.size();i++)
        {
            if(oid.equals(cdvos.get(i).oid))
                return i;
        }
        return -1;
    }

    /**
     * 根据name返回oid
     * @param datas 全部数据列表
     * @param oid
     * @return 返回找到的name，未找到返回空字符串
     */
    public static String getNameWithOid(List datas,String oid)
    {
        BaseCommonDataVO dataVO = findDataVOWithOid(datas,oid);
        if(dataVO==null)
            return StringUtil.STR_EMPTY;
        return dataVO.getName();
    } /**
     * 根据name查找数据VO
     * @param datas 全部数据列表
     * @param name 需要查找的name
     * @return 返回找到第一个name的数据VO，未找到返回null
     */
    public static BaseCommonDataVO findDataVOWithName(List datas,String name)
    {
        if(datas==null||datas.isEmpty()||StringUtil.isEmpty(name)||
                !(datas.get(0) instanceof BaseCommonDataVO))
            return null;
        List<BaseCommonDataVO> cdvos = datas;
        for (BaseCommonDataVO dvo:cdvos
                ) {
            if(name.equals(dvo.name))
                return dvo;
        }
        return null;
    }

    /**
     * 根据name返回oid
     * @param datas 全部数据列表
     * @param name
     * @return 返回找到第一个name的oid，未找到返回空字符串
     */
    public static String getOidWithName(List datas, String name)
    {
        BaseCommonDataVO dataVO = findDataVOWithName(datas,name);
        if(dataVO==null)
            return StringUtil.STR_EMPTY;
        return dataVO.getOid();
    }

    /**
     * 获取所有子级节点数据
     * @param datas 全部数据列表
     * @param parentId 父id
     * @return
     */
    public static List<BaseCommonDataVO> getDataVOListWithParentId(List datas,String parentId)
    {
        if(datas==null||datas.isEmpty()||StringUtil.isEmpty(parentId)||
                !(datas.get(0) instanceof BaseCommonDataVO))
            return null;
        List<BaseCommonDataVO> cdvos = datas;
        List<BaseCommonDataVO> result = new ArrayList();
        for (BaseCommonDataVO dvo:cdvos
             ) {
            if(parentId.equals(dvo.getParentId()))
            {
                result.add(dvo);
            }
        }
        if(result.isEmpty())
            return null;
        return result;
    }

    /**
     * 获取所有子级的子节点数据
     * @param datas 全部数据列表
     * @param parentId 父id
     * @return
     */
    public static List<BaseCommonDataVO> getDataVOListWithSuperParentId(List datas,String parentId)
    {
        if(datas==null||datas.isEmpty()||StringUtil.isEmpty(parentId)||
                !(datas.get(0) instanceof BaseCommonDataVO))
            return null;
        List<BaseCommonDataVO> parents = new ArrayList();
        List<BaseCommonDataVO> cdvos = datas;
        for (BaseCommonDataVO dvo:cdvos
                ) {
            if(parentId.equals(dvo.getParentId()))
            {
                parents.add(dvo);
            }
        }
        if(parents.isEmpty())
            return null;
        List<BaseCommonDataVO> result = new ArrayList();
        for (BaseCommonDataVO dvo:parents
             ) {
            List<BaseCommonDataVO> subs = getDataVOListWithParentId(datas,dvo.getOid());
            if(subs!=null)
                for (BaseCommonDataVO sub:subs
                     ) {
                    result.add(sub);
                }
        }
        if(result.isEmpty())
            return null;
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnCode() {
        return enCode;
    }

    public void setEnCode(String enCode) {
        this.enCode = enCode;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
