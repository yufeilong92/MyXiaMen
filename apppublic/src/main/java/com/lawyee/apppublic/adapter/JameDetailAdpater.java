package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.util.BaseCommonToStringUtil;
import com.lawyee.apppublic.util.TextViewUtil;
import com.lawyee.apppublic.vo.JamedApplyDetailVO;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.adapter
 * @Description: ${todo}(个人中心——人民调解)
 * @author: lzh
 * @date: 2017/6/2 18:14
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class JameDetailAdpater extends RecyclerView.Adapter {
    /**
     * 5种类型
     */
    public static final int APPLYINFO = 0;//申请信息
    public static final int APPLYPERSONINFO = 1;//申请人信息
    public static final int RESPONDENTINFO = 2;//被申请人信息
    public static final int JAMEINFO = 3;//调解信息
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    /**
     * 当前类型
     */
    public int currentType = APPLYINFO;

    private JamedApplyDetailVO mJamedApplyDetailVO;



    public JameDetailAdpater(Context context, JamedApplyDetailVO mJamedApplyDetailVO) {
        this.mContext = context;
        this.mJamedApplyDetailVO = mJamedApplyDetailVO;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == APPLYINFO) {
            return new ApplyViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_jamed_detail_apply_info, null));
        } else if (viewType == APPLYPERSONINFO) {
            return new ApplicantViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_jamed_detail_applicant_info, null));
        } else if (viewType == RESPONDENTINFO) {
            return new RespondentViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_jamed_detail_respondent_info, null));
        } else if (viewType == JAMEINFO) {
            return new JamedViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_jamed_detail_jamed_info, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == APPLYINFO) {
            ApplyViewHolder applyViewHolder = (ApplyViewHolder) holder;
            applyViewHolder.setData();
        } else if (getItemViewType(position) == APPLYPERSONINFO) {
            ApplicantViewHolder applicantViewHolder = (ApplicantViewHolder) holder;
            applicantViewHolder.setData();
        } else if (getItemViewType(position) == RESPONDENTINFO) {
            RespondentViewHolder respondentViewHolder = (RespondentViewHolder) holder;
            respondentViewHolder.setData();
        } else if (getItemViewType(position) == JAMEINFO) {
            JamedViewHolder jamedViewHolder = (JamedViewHolder) holder;
            jamedViewHolder.setData();
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
            case APPLYINFO:
                currentType = APPLYINFO;
                break;
            case APPLYPERSONINFO:
                currentType = APPLYPERSONINFO;
                break;
            case RESPONDENTINFO:
                currentType = RESPONDENTINFO;
                break;
            case JAMEINFO:
                currentType = JAMEINFO;
                break;
        }
        return currentType;
    }

    @Override
    public int getItemCount() {
        return 4;
    }


    class ApplyViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private TextView mTvOrgJamedtype;
        private TextView mTvJamedOrg;
        private TextView mTvWainJamed;

        public ApplyViewHolder(Context mContext, View view) {
            super(view);
            this.mContext = mContext;
            this.mTvOrgJamedtype = (TextView) view.findViewById(R.id.tv_org_jamedtype);
            this.mTvJamedOrg = (TextView) view.findViewById(R.id.tv_jamed_org);
            this.mTvWainJamed = (TextView) view.findViewById(R.id.tv_whether_agree_in_media_jamed);
        }

        //设置数据
        public void setData() {
            if(mJamedApplyDetailVO.isTjType()){
                mTvOrgJamedtype.setText(R.string.jamed_profession);
            }else {
                mTvOrgJamedtype.setText(R.string.jamed_commonly);
            }
            TextViewUtil.isEmpty(mTvJamedOrg,mJamedApplyDetailVO.getTjOrgName());
            if(mJamedApplyDetailVO.isMediaFlag()){
                mTvWainJamed.setText(R.string.agree);
            }else {
                mTvWainJamed.setText(R.string.no_agree);
            }

        }
    }

    class ApplicantViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private TextView mTvName;
        private TextView mTvSex;
        private TextView mTvCertificateNum;
        private TextView mTvBirthday2;
        private TextView mTvNation2;
        private TextView mTvPhone2;
        private TextView mTvPlace;
        private TextView mTvRelationBbRespondente;

        public ApplicantViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.mTvName = (TextView) itemView.findViewById(R.id.tv_name);
            this.mTvSex = (TextView) itemView.findViewById(R.id.tv_sex);
            this.mTvCertificateNum = (TextView) itemView.findViewById(R.id.tv_certificate_num);
            this.mTvBirthday2 = (TextView) itemView.findViewById(R.id.tv_birthday2);
            this.mTvNation2 = (TextView) itemView.findViewById(R.id.tv_nation2);
            this.mTvPhone2 = (TextView) itemView.findViewById(R.id.tv_phone2);
            this.mTvPlace = (TextView) itemView.findViewById(R.id.tv_place);
            this.mTvRelationBbRespondente = (TextView) itemView.findViewById(R.id.tv_relation_bb_respondente);

        }

        //设置数据
        public void setData() {

            TextViewUtil.isEmpty(mTvName,mJamedApplyDetailVO.getApplyName());
//            if(mJamedApplyDetailVO.getApplyGender()!=null){
//                        if(mJamedApplyDetailVO.getApplyGender().equals("1")){
//                            mTvSex.setText(R.string.man);
//                        }else {
//                            mTvSex.setText(R.string.woman);
//                        }
//            }
            TextViewUtil.isEmpty(mTvSex,BaseCommonToStringUtil.toString(mJamedApplyDetailVO.getApplyGender()));
            TextViewUtil.isEmpty(mTvCertificateNum,mJamedApplyDetailVO.getApplyIdCard());
            TextViewUtil.isEmpty(mTvBirthday2,mJamedApplyDetailVO.getApplyAge());
            TextViewUtil.isEmpty(mTvNation2, BaseCommonToStringUtil.toString(mJamedApplyDetailVO.getApplyNation()));
            TextViewUtil.isEmpty(mTvPhone2,mJamedApplyDetailVO.getApplyTelephone());
            TextViewUtil.isEmpty(mTvPlace,mJamedApplyDetailVO.getApplyAddress());
            TextViewUtil.isEmpty(mTvRelationBbRespondente,mJamedApplyDetailVO.getRelation());
        }
    }

    class RespondentViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private TextView mTvName;
        private TextView mTvSex;
        private TextView mTvBirthday2;
        private TextView mTvNation2;
        private TextView mTvPhone2;
        private TextView mTvPlace;

        public RespondentViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.mTvName = (TextView) itemView.findViewById(R.id.tv_name);
            this.mTvSex = (TextView) itemView.findViewById(R.id.tv_sex);
            this.mTvBirthday2 = (TextView) itemView.findViewById(R.id.tv_birthday2);
            this.mTvNation2 = (TextView) itemView.findViewById(R.id.tv_nation2);
            this.mTvPhone2 = (TextView) itemView.findViewById(R.id.tv_phone2);
            this.mTvPlace = (TextView) itemView.findViewById(R.id.tv_place);
        }

        //设置数据
        public void setData() {
            TextViewUtil.isEmpty(mTvName,mJamedApplyDetailVO.getBeApplyName());
//            if(mJamedApplyDetailVO.getBeApplyGender()!=null){
//                if(mJamedApplyDetailVO.getBeApplyGender().equals("1")){
//                    mTvSex.setText(R.string.man);
//                }else {
//                    mTvSex.setText(R.string.woman);
//                }
//            }
            TextViewUtil.isEmpty(mTvSex,BaseCommonToStringUtil.toString(mJamedApplyDetailVO.getBeApplyGender()));
            TextViewUtil.isEmpty(mTvBirthday2,mJamedApplyDetailVO.getBeApplyAge());
            TextViewUtil.isEmpty(mTvNation2, BaseCommonToStringUtil.toString(mJamedApplyDetailVO.getBeApplyNation()));
            TextViewUtil.isEmpty(mTvPhone2,mJamedApplyDetailVO.getBeApplyTelephone());
            TextViewUtil.isEmpty(mTvPlace,mJamedApplyDetailVO.getBeApplyAddress());
        }
    }
    class JamedViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private TextView mTvBcpDipute;
        private TextView mTvJAppForApper;

        public JamedViewHolder(Context mContext, View view) {
            super(view);
            this.mContext = mContext;
            this.mTvBcpDipute = (TextView) view.findViewById(R.id.tv_brief_case_of_the_dispute);
            this.mTvJAppForApper = (TextView) view.findViewById(R.id.tv_the_application_for_the_application);
        }

        //设置数据
        public void setData() {
            TextViewUtil.isEmpty(mTvBcpDipute,mJamedApplyDetailVO.getIntroduction());
            TextViewUtil.isEmpty(mTvJAppForApper,mJamedApplyDetailVO.getMatter());
        }
    }
}