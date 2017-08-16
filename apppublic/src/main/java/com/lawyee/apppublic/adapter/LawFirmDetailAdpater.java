package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.ui.WalkingRouteActivity;
import com.lawyee.apppublic.util.BaseCommonToStringUtil;
import com.lawyee.apppublic.util.ImageLoaderManager;
import com.lawyee.apppublic.util.TextViewUtil;
import com.lawyee.apppublic.util.UrlUtil;
import com.lawyee.apppublic.vo.JalawEvaluateVO;
import com.lawyee.apppublic.vo.JalawLawfirmDetailVO;

import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.TimeUtil;

import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.adapter
 * @Description: ${todo}(律所详细信息的adpater)
 * @author: czq
 * @date: 2017/5/25 18:39
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class LawFirmDetailAdpater extends RecyclerView.Adapter {
    /**
     * 5种类型
     */
    public static final int INFO2 = 0;//律所信息
    public static final int INTRO = 1;//律所简介
    public static final int  EVALUATE2= 2;//[评价信息
    public static final int CONTACT1 = 3;//请联系我
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    /**
     * 当前类型
     */
    public int currentType = INFO2;

    private JalawLawfirmDetailVO mJalawLawfirmDetailVO;

    public LawFirmDetailAdpater(Context context,JalawLawfirmDetailVO mJalawLawfirmDetailVO) {
        this.mContext = context;
        this.mJalawLawfirmDetailVO=mJalawLawfirmDetailVO;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == INFO2) {
            return new InfoViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_law_firm_info, null));
        } else if (viewType == INTRO) {
            return new AccountViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_law_firm_intro, null));
        } else if (viewType == EVALUATE2) {
            return new EvaluateViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_client_evaluation, null));
        }else if (viewType == CONTACT1) {
            return new ContactViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_contact_me, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == INFO2) {
            InfoViewHolder infoViewHolder = (InfoViewHolder) holder;
            infoViewHolder.setData();
        } else if (getItemViewType(position) == INTRO) {
            AccountViewHolder accountViewHolder = (AccountViewHolder) holder;
            accountViewHolder.setData();
        } else if (getItemViewType(position) == EVALUATE2) {
            EvaluateViewHolder evaluateViewHolder = (EvaluateViewHolder) holder;
            evaluateViewHolder.setData(mJalawLawfirmDetailVO.getEvaluates());
        }else if (getItemViewType(position) == CONTACT1) {
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
            case INFO2:
                currentType = INFO2;
                break;
            case INTRO:
                currentType = INTRO;
                break;
            case EVALUATE2:
                currentType = EVALUATE2;
                break;
            case CONTACT1:
                currentType = CONTACT1;
                break;
        }
        return currentType;
    }

    @Override
    public int getItemCount() {
        return 4;
    }


    class InfoViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ImageView mIvBottom;
        private TextView mTvTeach;
        private TextView mTvLfPcNum;
        private TextView mTvLfAdmin;
        private TextView mTvOrgForm;
        private TextView mTvFoundTime;
        private TextView mTvPrincipal;
        private TextView mTvLawyerNum;
        private TextView mTvAffiliatingArea;
        private TextView mTvProfessionalField;
        private TextView mTvAdministrativePenalty;
        private TextView mTvIndustryDispose;
        private TextView mTvAnnualAssessment;
        private TextView mTvReward;
        private TextView mTvPackUp;
        private LinearLayout mLlBottomDetail;
        private TextView mTvLawFirmInfo;

        public InfoViewHolder(Context mContext, View view) {
            super(view);
            this.mContext = mContext;
            this.mIvBottom = (ImageView) itemView.findViewById(R.id.iv_bottom);
            this.mTvLawFirmInfo = (TextView) itemView.findViewById(R.id.tv_law_firm_info);
            this.mTvTeach = (TextView) itemView.findViewById(R.id.tv_teach);
            this.mTvLfPcNum = (TextView) itemView.findViewById(R.id.tv_lf_pc_num);
            this.mTvLfAdmin = (TextView) itemView.findViewById(R.id.tv_lf_admin);
            this.mTvOrgForm = (TextView) itemView.findViewById(R.id.tv_org_form);
            this.mTvFoundTime = (TextView) itemView.findViewById(R.id.tv_found_time);
            this.mTvPrincipal = (TextView) itemView.findViewById(R.id.tv_principal);
            this.mTvLawyerNum = (TextView) itemView.findViewById(R.id.tv_lawyer_num);
            this.mTvAffiliatingArea = (TextView) itemView.findViewById(R.id.tv_affiliating_area);
            this.mTvProfessionalField = (TextView) itemView.findViewById(R.id.tv_professional_field);
            this.mTvAdministrativePenalty = (TextView) itemView.findViewById(R.id.tv_administrative_penalty);
            this.mTvIndustryDispose = (TextView) itemView.findViewById(R.id.tv_Industry_dispose);
            this.mTvAnnualAssessment = (TextView) itemView.findViewById(R.id.tv_annual_assessment);
            this.mTvReward = (TextView) itemView.findViewById(R.id.tv_reward);
            this.mTvPackUp = (TextView) itemView.findViewById(R.id.tv_pack_up);
            this.mLlBottomDetail = (LinearLayout) itemView.findViewById(R.id.ll_bottom_detail);

        }

        //设置数据
        public void setData() {
            mTvLawFirmInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLlBottomDetail.getVisibility() == View.GONE) {
                        mIvBottom.setVisibility(View.GONE);
                        mLlBottomDetail.setVisibility(View.VISIBLE);
                    } else {
                        mIvBottom.setVisibility(View.VISIBLE);
                        mLlBottomDetail.setVisibility(View.GONE);
                    }
                }
            });
            mTvPackUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLlBottomDetail.setVisibility(View.GONE);
                    mIvBottom.setVisibility(View.VISIBLE);
                }
            });
            TextViewUtil.isEmpty(mTvTeach,mJalawLawfirmDetailVO.getDoctrine());
            TextViewUtil.isEmpty(mTvLfPcNum,mJalawLawfirmDetailVO.getLicenseNumber());
            TextViewUtil.isEmpty(mTvLfAdmin,mJalawLawfirmDetailVO.getJusticeBureauName());
            TextViewUtil.isEmpty(mTvOrgForm,BaseCommonToStringUtil.toString(mJalawLawfirmDetailVO.getOrganizationForms()));
            TextViewUtil.isEmpty(mTvFoundTime,TimeUtil.getYMDT(mJalawLawfirmDetailVO.getIssueDate()));
            TextViewUtil.isEmpty(mTvPrincipal,mJalawLawfirmDetailVO.getDirectorName());
            TextViewUtil.isEmpty(mTvLawyerNum,mJalawLawfirmDetailVO.getLawyerNumber());
            TextViewUtil.isEmpty(mTvAffiliatingArea,mJalawLawfirmDetailVO.getProvinceName()+" "+mJalawLawfirmDetailVO.getCityName());
            StringBuffer str=new StringBuffer("");
            if(mJalawLawfirmDetailVO.getBusiness()!=null&&mJalawLawfirmDetailVO.getBusiness().size()>0){
                for(int i = 0; i <mJalawLawfirmDetailVO.getBusiness().size() ; i++) {
                    str.append(BaseCommonToStringUtil.toString(mJalawLawfirmDetailVO.getBusiness().get(i).getBusiness())+" ");
                }
            }else {
                str.append(mContext.getString(R.string.nothing));
            }
            mTvProfessionalField.setText(str);
            if(mJalawLawfirmDetailVO.getPunishs()==null||mJalawLawfirmDetailVO.getPunishs().size()==0){
                mTvAdministrativePenalty.setText(R.string.nothing);
                mTvIndustryDispose.setText(R.string.nothing);
            }else {
                StringBuffer string1=new StringBuffer("");
                StringBuffer string2=new StringBuffer("");
                for(int i=0;i<mJalawLawfirmDetailVO.getPunishs().size();i++){
                    String str1=  BaseCommonToStringUtil.toString(mJalawLawfirmDetailVO.getPunishs().get(i).getCategory());
                    String result=  BaseCommonToStringUtil.toString(mJalawLawfirmDetailVO.getPunishs().get(i).getResult());
                    if(str1!=null&&str1.equals("行政处罚")){
                        string1.append(TimeUtil.getYMDT(mJalawLawfirmDetailVO.getPunishs().get(i).getPunishTimeStart())+mContext.getString(R.string.to)+
                                TimeUtil.getYMDT(mJalawLawfirmDetailVO.getPunishs().get(i).getPunishTimeEnd())+"  "+result+"\n");

                    }else if(str1!=null&&str1.equals("行业处分")){
                        string2.append(TimeUtil.getYMDT(mJalawLawfirmDetailVO.getPunishs().get(i).getPunishTimeStart())+mContext.getString(R.string.to)+
                                TimeUtil.getYMDT(mJalawLawfirmDetailVO.getPunishs().get(i).getPunishTimeEnd())+"  "+result+"\n");

                    }
                }
                mTvAdministrativePenalty.setText(string1);
                mTvIndustryDispose.setText(string2);
            }
            TextViewUtil.isEmpty(mTvAnnualAssessment,BaseCommonToStringUtil.toString(mJalawLawfirmDetailVO.getAnnualAssessment()));
            if(mJalawLawfirmDetailVO.getRewards()==null||mJalawLawfirmDetailVO.getRewards().size()==0){
                mTvReward.setText(R.string.nothing);
            }else{
                StringBuffer stringBuffer=new StringBuffer("");
                for(int i=0;i<mJalawLawfirmDetailVO.getRewards().size();i++){
                    String level=  BaseCommonToStringUtil.toString(mJalawLawfirmDetailVO.getRewards().get(i).getLevel());
                    stringBuffer.append(StringUtil.getStringEmpty(mJalawLawfirmDetailVO.getRewards().get(i).getYear())+mContext.getString(R.string.year)+"  "+
                            StringUtil.getStringEmpty(mJalawLawfirmDetailVO.getRewards().get(i).getAwardOrganization())
                            +mContext.getString(R.string.clothing)+ mJalawLawfirmDetailVO.getRewards().get(i).getName()+
                            "("+level+")"+"\n");
                }
                mTvReward.setText(stringBuffer);
            }
        }
    }

    class AccountViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvBottom;
        private TextView mTvInfo;
        private TextView mTvPackUp;
        private LinearLayout mLlBottomDetail;
        private TextView mTvLawFirmInfo;
        private Context mContext;

        public AccountViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.mIvBottom = (ImageView) itemView.findViewById(R.id.iv_bottom);
            this.mTvPackUp = (TextView) itemView.findViewById(R.id.tv_pack_up);
            this.mLlBottomDetail = (LinearLayout) itemView.findViewById(R.id.ll_bottom_detail);
            this.mTvLawFirmInfo = (TextView) itemView.findViewById(R.id.tv_law_firm_info);
            this.mTvInfo = (TextView) itemView.findViewById(R.id.tv_info);
        }

        //设置数据
        public void setData() {
            mTvLawFirmInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLlBottomDetail.getVisibility() == View.GONE) {
                        mIvBottom.setVisibility(View.GONE);
                        mLlBottomDetail.setVisibility(View.VISIBLE);
                    } else {
                        mIvBottom.setVisibility(View.VISIBLE);
                        mLlBottomDetail.setVisibility(View.GONE);
                    }
                }
            });
            mTvPackUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLlBottomDetail.setVisibility(View.GONE);
                    mIvBottom.setVisibility(View.VISIBLE);
                }
            });
            if(mJalawLawfirmDetailVO.getIntroduction()==null||mJalawLawfirmDetailVO.getIntroduction().equals("")){
                mTvInfo.setGravity(Gravity.CENTER);
                mTvInfo.setText(R.string.nothing);
            }else{
                mTvInfo.setText(mJalawLawfirmDetailVO.getIntroduction());
            }
        }
    }
    class EvaluateViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ImageView mIvBottom;
        private RecyclerView mRvCeval;
        private TextView mTvPackUp;
        private TextView mTvNull;
        private LinearLayout mLlBottomDetail;
        private TextView mTvLawyerInfo;
        private EvaluateAdpater mAdpater;
        private GridLayoutManager mLayoutManager;

        public EvaluateViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.mIvBottom = (ImageView) itemView.findViewById(R.id.iv_bottom);
            this.mTvPackUp = (TextView) itemView.findViewById(R.id.tv_pack_up);
            this.mLlBottomDetail = (LinearLayout) itemView.findViewById(R.id.ll_bottom_detail);
            this.mTvLawyerInfo = (TextView) itemView.findViewById(R.id.tv_lawyer_info);
            this.mRvCeval = (RecyclerView) itemView.findViewById(R.id.rv_ceval);
            this.mTvNull= (TextView) itemView.findViewById(R.id.tv_null);
        }

        //设置数据
        public void setData(List<JalawEvaluateVO> list) {
            mTvLawyerInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLlBottomDetail.getVisibility() == View.GONE) {
                        mIvBottom.setVisibility(View.GONE);
                        mLlBottomDetail.setVisibility(View.VISIBLE);
                    } else {
                        mIvBottom.setVisibility(View.VISIBLE);
                        mLlBottomDetail.setVisibility(View.GONE);
                    }
                }
            });
            mTvPackUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLlBottomDetail.setVisibility(View.GONE);
                    mIvBottom.setVisibility(View.VISIBLE);
                }
            });
            if(list==null||list.size()==0){
                mTvNull.setVisibility(View.VISIBLE);
                mRvCeval.setVisibility(View.GONE);
            }else {
                mTvNull.setVisibility(View.GONE);
                mRvCeval.setVisibility(View.VISIBLE);
                mAdpater = new EvaluateAdpater(list, mContext,false);
                mRvCeval.setAdapter(mAdpater);
                mLayoutManager = new GridLayoutManager(mContext, 1);
                mRvCeval.setLayoutManager(mLayoutManager);
            }
        }
    }
    class ContactViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ImageView mIvBottom;
        private TextView mTvLawIrmsAddress;
        private TextView mTvLawServiceCalls;
        private TextView mTvLawServesMailbox;
        private ImageView mIvMap;
        private TextView mTvPackUp;
        private LinearLayout mLlBottomDetail;
        private TextView mTvLawyerInfo;
        private TextView mTvNull;

        public ContactViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.mIvBottom = (ImageView) itemView.findViewById(R.id.iv_bottom);
            this.mTvPackUp = (TextView) itemView.findViewById(R.id.tv_pack_up);
            this.mLlBottomDetail = (LinearLayout) itemView.findViewById(R.id.ll_bottom_detail);
            this.mTvLawyerInfo = (TextView) itemView.findViewById(R.id.tv_lawyer_info);
            this.mTvLawIrmsAddress = (TextView) itemView.findViewById(R.id.tv_law_irms_address);
            this.mTvLawServiceCalls = (TextView) itemView.findViewById(R.id.tv_law_service_calls);
            this.mTvLawServesMailbox = (TextView) itemView.findViewById(R.id.tv_law_serves_mailbox);
            this.mIvMap = (ImageView) itemView.findViewById(R.id.iv_map);
            this.mTvNull= (TextView) itemView.findViewById(R.id.tv_null);
        }

        //设置数据
        public void setData() {
            mTvLawyerInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLlBottomDetail.getVisibility() == View.GONE) {
                        mIvBottom.setVisibility(View.GONE);
                        mLlBottomDetail.setVisibility(View.VISIBLE);
                    } else {
                        mIvBottom.setVisibility(View.VISIBLE);
                        mLlBottomDetail.setVisibility(View.GONE);
                    }
                }
            });
            mTvPackUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLlBottomDetail.setVisibility(View.GONE);
                    mIvBottom.setVisibility(View.VISIBLE);
                }
            });
            TextViewUtil.isEmpty(mTvLawIrmsAddress,mJalawLawfirmDetailVO.getProvinceName()+mJalawLawfirmDetailVO.getCityName()+mJalawLawfirmDetailVO.getAddress());
            TextViewUtil.isEmpty(mTvLawServiceCalls,mJalawLawfirmDetailVO.getTelephone());
            TextViewUtil.isEmpty(mTvLawServesMailbox,mJalawLawfirmDetailVO.getEmail());
            if(mJalawLawfirmDetailVO.getAxis()==null||mJalawLawfirmDetailVO.getAxis().equals("")){
                mIvMap.setVisibility(View.GONE);
                mTvNull.setVisibility(View.VISIBLE);
            }else {
                mIvMap.setVisibility(View.VISIBLE);
                mTvNull.setVisibility(View.GONE);
                String lawfirmAxis = mJalawLawfirmDetailVO.getAxis();
                int i = lawfirmAxis.indexOf(",");
                final String longitude= lawfirmAxis.substring(0, i);
                final String latitude= lawfirmAxis.substring(i + 1, lawfirmAxis.length());
                mIvMap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, WalkingRouteActivity.class);
                        intent.putExtra(WalkingRouteActivity.LATITUDE, Double.parseDouble(latitude));
                        intent.putExtra(WalkingRouteActivity.LONGITUDE, Double.parseDouble(longitude));
                        intent.putExtra(WalkingRouteActivity.ADDRESS, mJalawLawfirmDetailVO.getAddress());
                        intent.putExtra(WalkingRouteActivity.SERCESCALL, mJalawLawfirmDetailVO.getTelephone());
                        intent.putExtra(BaseActivity.CSTR_EXTRA_TITLE_STR, mJalawLawfirmDetailVO.getName());
                        mContext.startActivity(intent);
                    }
                });

                String map_addresss = UrlUtil.getStaticMapImgUrl(mContext,longitude,latitude);
                ImageLoaderManager.LoadNetImage(map_addresss, mIvMap);
            }
        }
    }
}
