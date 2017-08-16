package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.util.BaseCommonToStringUtil;
import com.lawyee.apppublic.util.TextViewUtil;
import com.lawyee.apppublic.vo.JaaidApplyDetailVO;

import net.lawyee.mobilelib.utils.TimeUtil;


/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.adapter
 * @Description: ${todo}(个人中心法律援助预申请详情)
 * @author: czq
 * @date: 2017/6/1 17:43
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class AidDetailAdpater extends RecyclerView.Adapter {
    /**
     * 3种类型
     */
    public static final int APPLICANTINFO = 0;//申请人信息
    public static final int OTHERINFO = 1;//其他信息
    public static final int MATERIAL = 2;//材料
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    /**
     * 当前类型
     */
    public int currentType = APPLICANTINFO;

    private JaaidApplyDetailVO mJaaidJaaidApplyDetailVO;



    public AidDetailAdpater(Context context, JaaidApplyDetailVO mJaaidJaaidApplyDetailVO) {
        this.mContext = context;
        this.mJaaidJaaidApplyDetailVO = mJaaidJaaidApplyDetailVO;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == APPLICANTINFO) {
            return new ApplicantInfoViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_aid_detail_apply_info, null));
        } else if (viewType == OTHERINFO) {
            return new OtherInfoViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_aid_detail_other_info, null));
        } else if (viewType == MATERIAL) {
            return new ContactViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_aid_detail_material, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == APPLICANTINFO) {
            ApplicantInfoViewHolder infoViewHolder = (ApplicantInfoViewHolder) holder;
            infoViewHolder.setData();
        } else if (getItemViewType(position) == OTHERINFO) {
            OtherInfoViewHolder otherInfoViewHolder = (OtherInfoViewHolder) holder;
            otherInfoViewHolder.setData();
        } else if (getItemViewType(position) == MATERIAL) {
            ContactViewHolder contactViewHolder = (ContactViewHolder) holder;
            contactViewHolder.setData();
        }
    }

    /**
     * 根据位置得到类型-系统调用
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case APPLICANTINFO:
                currentType = APPLICANTINFO;
                break;
            case OTHERINFO:
                currentType = OTHERINFO;
                break;
            case MATERIAL:
                currentType = MATERIAL;
                break;
        }
        return currentType;
    }

    @Override
    public int getItemCount() {
        return 3;
    }


    class ApplicantInfoViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private TextView mTvName;
        private TextView mTvSex;
        private TextView mTvCertificateType;
        private TextView mTvCertificateNum;
        private TextView mTvCaseReasonSummarize;
        private TextView mTvBirthday2;
        private TextView mTvNation2;
        private TextView mTvPhone2;
        private TextView mTvApplyDomicile2;
        private TextView mTvApplyResidence2;
        private TextView mTvApplyWorkUnit2;

        public ApplicantInfoViewHolder(Context mContext, View view) {
            super(view);
            this.mContext = mContext;
            this.mTvName = (TextView) view.findViewById(R.id.tv_name);
            this.mTvSex = (TextView) view.findViewById(R.id.tv_sex);
            this.mTvCertificateType = (TextView) view.findViewById(R.id.tv_certificate_type);
            this.mTvCertificateNum = (TextView) view.findViewById(R.id.tv_certificate_num);
            this.mTvBirthday2 = (TextView) view.findViewById(R.id.tv_birthday2);
            this.mTvNation2 = (TextView) view.findViewById(R.id.tv_nation2);
            this.mTvPhone2 = (TextView) view.findViewById(R.id.tv_phone2);
            this.mTvApplyDomicile2 = (TextView) view.findViewById(R.id.tv_apply_domicile2);
            this.mTvApplyResidence2 = (TextView) view.findViewById(R.id.tv_apply_residence2);
            this.mTvApplyWorkUnit2 = (TextView) view.findViewById(R.id.tv_apply_work_unit2);
            this.mTvCaseReasonSummarize = (TextView) view.findViewById(R.id.tv_case_reason_summarize);
        }

        //设置数据
        public void setData() {
            TextViewUtil.isEmpty(mTvName, mJaaidJaaidApplyDetailVO.getName());

//            if(mJaaidJaaidApplyDetailVO.getGender().equals("1")){
//                mTvSex.setText(R.string.man);
//            }else {
//                mTvSex.setText(R.string.woman);
//            }
            TextViewUtil.isEmpty(mTvSex, BaseCommonToStringUtil.toString(mJaaidJaaidApplyDetailVO.getGender()));
            TextViewUtil.isEmpty(mTvCertificateType, BaseCommonToStringUtil.toString(mJaaidJaaidApplyDetailVO.getIdType()));
            TextViewUtil.isEmpty(mTvCertificateNum, mJaaidJaaidApplyDetailVO.getIdCard());
            TextViewUtil.isEmpty(mTvBirthday2, TimeUtil.getYMDT( mJaaidJaaidApplyDetailVO.getBirthday()));
            TextViewUtil.isEmpty(mTvNation2,BaseCommonToStringUtil.toString( mJaaidJaaidApplyDetailVO.getNation()));
            TextViewUtil.isEmpty(mTvPhone2, mJaaidJaaidApplyDetailVO.getTelephone());
            StringBuffer stringBuffer=new StringBuffer("");
            stringBuffer.append(BaseCommonToStringUtil.toAreaString( mJaaidJaaidApplyDetailVO.getProvince()));
            stringBuffer.append(BaseCommonToStringUtil.toAreaString( mJaaidJaaidApplyDetailVO.getCity()));
            stringBuffer.append(BaseCommonToStringUtil.toAreaString( mJaaidJaaidApplyDetailVO.getCounty()));
            TextViewUtil.isEmpty(mTvApplyDomicile2, stringBuffer.toString());
            TextViewUtil.isEmpty(mTvApplyResidence2, mJaaidJaaidApplyDetailVO.getAddress());
            TextViewUtil.isEmpty(mTvApplyWorkUnit2, mJaaidJaaidApplyDetailVO.getWorkUnit());
            TextViewUtil.isEmpty(mTvCaseReasonSummarize, mJaaidJaaidApplyDetailVO.getCaseDescription());
        }
    }

    class OtherInfoViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private TextView mTvAgentName;
        private TextView mTvAgentType;
        private TextView mTvAgentIdNumber;
        private TextView mTvAgencyLocation;
        private TextView mTvApplyOtherName;
        private TextView mTvApplyOtherHere;
        private TextView mTvApplyCaseHere2;
        private TextView mTvHandlerOrg;
        private  TextView mTvApplyNumberPeple2;
        public OtherInfoViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.mTvAgentName = (TextView) itemView.findViewById(R.id.tv_agent_name);
            this.mTvAgentType = (TextView) itemView.findViewById(R.id.tv_agent_type);
            this.mTvAgentIdNumber = (TextView) itemView.findViewById(R.id.tv_agent_id_number);
            this.mTvApplyNumberPeple2= (TextView) itemView.findViewById(R.id.tv_apply_numberpeple2);
            this.mTvApplyOtherName = (TextView) itemView.findViewById(R.id.tv_apply_other_name);
            this.mTvApplyOtherHere = (TextView) itemView.findViewById(R.id.tv_apply_other_here);
            this.mTvApplyCaseHere2 = (TextView) itemView.findViewById(R.id.tv_apply_case_here2);
            this.mTvHandlerOrg = (TextView) itemView.findViewById(R.id.tv_handler_org);
            this.mTvAgencyLocation = (TextView) itemView.findViewById(R.id.tv_agency_location);

        }

        //设置数据
        public void setData() {
            TextViewUtil.isEmpty(mTvAgentName, mJaaidJaaidApplyDetailVO.getAgentName());
            TextViewUtil.isEmpty(mTvAgentType, BaseCommonToStringUtil.toString(mJaaidJaaidApplyDetailVO.getAgentType()));
            TextViewUtil.isEmpty(mTvAgentIdNumber, mJaaidJaaidApplyDetailVO.getAgentIdCard());
            TextViewUtil.isEmpty(mTvAgencyLocation, mJaaidJaaidApplyDetailVO.getHandleOrgAddress());
            TextViewUtil.isEmpty(mTvApplyNumberPeple2, mJaaidJaaidApplyDetailVO.getApplyUserCount());
            TextViewUtil.isEmpty(mTvApplyOtherName, mJaaidJaaidApplyDetailVO.getPartiesName());
            TextViewUtil.isEmpty(mTvApplyOtherHere, mJaaidJaaidApplyDetailVO.getPartiesLocal());
            TextViewUtil.isEmpty(mTvApplyCaseHere2, mJaaidJaaidApplyDetailVO.getCaseHappenLocal());
            TextViewUtil.isEmpty(mTvHandlerOrg, mJaaidJaaidApplyDetailVO.getManageOrgName());
        }
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private RecyclerView mRvMaterial;
        private AttachmentAdpater mAdpater;

        public ContactViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.mRvMaterial = (RecyclerView) itemView.findViewById(R.id.rv_material);
        }

        //设置数据
        public void setData() {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
            mRvMaterial.setLayoutManager(gridLayoutManager);
            mAdpater = new AttachmentAdpater(mContext, mJaaidJaaidApplyDetailVO.getAttachments());
            mRvMaterial.setAdapter(mAdpater);
        }
    }
}