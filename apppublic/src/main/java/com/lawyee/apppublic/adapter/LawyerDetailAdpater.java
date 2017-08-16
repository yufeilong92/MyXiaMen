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
import com.lawyee.apppublic.vo.JalawLawyerDetailVO;
import com.lawyee.apppublic.vo.JalawLawyerServiceVO;

import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.TimeUtil;

import java.util.Calendar;
import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.adapter
 * @Description: ${todo}(律师详细信息的Adpater)
 * @author: czq
 * @date: 2017/5/24 14:08
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class LawyerDetailAdpater extends RecyclerView.Adapter {
    /**
     * 5种类型
     */
    public static final int INFO1 = 0;//律师信息
    public static final int ACCOUNT = 1;//律师自述
    public static final int SERVICE = 2;//服务项目
    public static final int EVALUATE = 3;//客户评价
    public static final int CONTACT = 4;//请联系我
    private Context mContext;
    private JalawLawyerDetailVO mJalawLawyerDetailVO;
    private LayoutInflater mLayoutInflater;
    /**
     * 当前类型
     */
    public int currentType = INFO1;

   private  boolean isPractice=true;

    public LawyerDetailAdpater(Context context,  JalawLawyerDetailVO mJalawLawyerDetailVO) {
        this.mContext = context;
        this.mJalawLawyerDetailVO = mJalawLawyerDetailVO;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == INFO1) {
            return new InfoViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_lawyer_info, null));
        } else if (viewType == ACCOUNT) {
            return new AccountViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_lawyer_account, null));
        } else if (viewType == SERVICE) {
            return new ServiceViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_service_project, null));
        } else if (viewType == EVALUATE) {
            return new EvaluateViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_client_evaluation, null));
        } else if (viewType == CONTACT) {
            return new ContactViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_contact_me, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == INFO1) {
            InfoViewHolder infoViewHolder = (InfoViewHolder) holder;
            infoViewHolder.setData();
        } else if (getItemViewType(position) == ACCOUNT) {
            AccountViewHolder accountViewHolder = (AccountViewHolder) holder;
            accountViewHolder.setData();
        } else if (getItemViewType(position) == SERVICE) {
            ServiceViewHolder serviceViewHolder = (ServiceViewHolder) holder;
            serviceViewHolder.setData(mJalawLawyerDetailVO.getServices());
        } else if (getItemViewType(position) == EVALUATE) {
            EvaluateViewHolder evaluateViewHolder = (EvaluateViewHolder) holder;
           evaluateViewHolder.setData(mJalawLawyerDetailVO.getEvaluates());
        } else if (getItemViewType(position) == CONTACT) {
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
            case INFO1:
                currentType = INFO1;
                break;
            case ACCOUNT:
                currentType = ACCOUNT;
                break;
            case SERVICE:
                currentType = SERVICE;
                break;
            case EVALUATE:
                currentType = EVALUATE;
                break;
            case CONTACT:
                currentType = CONTACT;
                break;
        }
        return currentType;
    }

    @Override
    public int getItemCount() {
        return 5;
    }


    class InfoViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ImageView mIvBottom;
        private TextView mTvLawyerInfo;
        private TextView mTvName;
        private TextView mTvSex;
        private TextView mTvAge;
        private TextView mTvPoliticsStatus;
        private TextView mTvLawyerTitle;
        private TextView mTvPracticeType;
        private TextView mTvPracticeStatus;
        private TextView mTvPracticeOrganization;
        private TextView mTvPracticeCertificateNumber;
        private TextView mTvPracticeWaterCertificateNumber;
        private TextView mTvFirstPractice;
        private TextView mTvJudicialAdministrative;
        private TextView mTvAdministrativePenalty;
        private TextView mTvIndustryDispose;
        private TextView mTvAnnualAssessment;
        private TextView mTvReward;
        private TextView mTvPackUp;
        private LinearLayout mLlBottomDetail;

        public InfoViewHolder(Context mContext, View view) {
            super(view);
            this.mContext = mContext;
            this.mIvBottom = (ImageView) itemView.findViewById(R.id.iv_bottom);
            this.mTvLawyerInfo = (TextView) itemView.findViewById(R.id.tv_lawyer_info);
            this.mTvName = (TextView) itemView.findViewById(R.id.tv_name);
            this.mTvSex = (TextView) itemView.findViewById(R.id.tv_sex);
            this.mTvAge = (TextView) itemView.findViewById(R.id.tv_age);
            this.mTvPoliticsStatus = (TextView) itemView.findViewById(R.id.tv_politics_status);
            this.mTvLawyerTitle = (TextView) itemView.findViewById(R.id.tv_lawyer_title);
            this.mTvPracticeType = (TextView) itemView.findViewById(R.id.tv_practice_type);
            this.mTvPracticeStatus = (TextView) itemView.findViewById(R.id.tv_practice_status);
            this.mTvPracticeOrganization = (TextView) itemView.findViewById(R.id.tv_practice_organization);
            this.mTvPracticeCertificateNumber = (TextView) itemView.findViewById(R.id.tv_practice_certificate_number);
            this.mTvPracticeWaterCertificateNumber = (TextView) itemView.findViewById(R.id.tv_practice_water_certificate_number);
            this.mTvFirstPractice = (TextView) itemView.findViewById(R.id.tv_first_practice);
            this.mTvJudicialAdministrative = (TextView) itemView.findViewById(R.id.tv_judicial_administrative);
            this.mTvAdministrativePenalty = (TextView) itemView.findViewById(R.id.tv_administrative_penalty);
            this.mTvIndustryDispose = (TextView) itemView.findViewById(R.id.tv_Industry_dispose);
            this.mTvAnnualAssessment = (TextView) itemView.findViewById(R.id.tv_annual_assessment);
            this.mTvReward = (TextView) itemView.findViewById(R.id.tv_reward);
            this.mTvPackUp = (TextView) itemView.findViewById(R.id.tv_pack_up);
            this.mLlBottomDetail = (LinearLayout) itemView.findViewById(R.id.ll_bottom_detail);

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
            TextViewUtil.isEmpty(mTvName,mJalawLawyerDetailVO.getName());
//            if(mJalawLawyerDetailVO.getGender()!=null) {
//                if (mJalawLawyerDetailVO.getGender().equals("0")) {
//                    mTvSex.setText(R.string.woman);
//                } else if (mJalawLawyerDetailVO.getGender().equals("1")) {
//                    mTvSex.setText(R.string.man);
//                }
//            }
            TextViewUtil.isEmpty(mTvSex,BaseCommonToStringUtil.toString(mJalawLawyerDetailVO.getGender()));
                String age="";
            if(TimeUtil.getYearTime(mJalawLawyerDetailVO.getBirthday())!=0){
                int x=Calendar.getInstance().get(Calendar.YEAR)-TimeUtil.getYearTime(mJalawLawyerDetailVO.getBirthday());
                age=x+"";
            }
           mTvAge.setText(age);
            TextViewUtil.isEmpty(mTvPoliticsStatus,BaseCommonToStringUtil.toString(mJalawLawyerDetailVO.getPolitic()));
            TextViewUtil.isEmpty(mTvLawyerTitle,BaseCommonToStringUtil.toString(mJalawLawyerDetailVO.getLawyerTitle()));
            TextViewUtil.isEmpty(mTvPracticeType,BaseCommonToStringUtil.toString(mJalawLawyerDetailVO.getPracticeType()));
            TextViewUtil.isEmpty(mTvPracticeStatus,BaseCommonToStringUtil.toString(mJalawLawyerDetailVO.getPracticeStatus()));
            if(mTvPracticeStatus.getText().toString().trim()!=null&&mTvPracticeStatus.getText().toString().trim().equals("正常")){
                isPractice=true;
            }else {
                isPractice=false;
            }
            TextViewUtil.isEmpty(mTvPracticeOrganization,mJalawLawyerDetailVO.getLawfirmName());
            TextViewUtil.isEmpty(mTvPracticeCertificateNumber,mJalawLawyerDetailVO.getLicenseNumber());
            TextViewUtil.isEmpty(mTvPracticeWaterCertificateNumber,mJalawLawyerDetailVO.getPractisingCertificateSerialNumber());
            TextViewUtil.isEmpty(mTvFirstPractice,TimeUtil.getYMDT(mJalawLawyerDetailVO.getFirstPracticeDate()));
            TextViewUtil.isEmpty(mTvJudicialAdministrative,mJalawLawyerDetailVO.getJusticeBureauName());
            TextViewUtil.isEmpty(mTvAnnualAssessment,BaseCommonToStringUtil.toString(mJalawLawyerDetailVO.getAnnualAssessment()));
            if(mJalawLawyerDetailVO.getPunishs()==null||mJalawLawyerDetailVO.getPunishs().size()==0){
                mTvAdministrativePenalty.setText(R.string.nothing);
                mTvIndustryDispose.setText(R.string.nothing);
            }else {
                StringBuffer string1=new StringBuffer("");
                StringBuffer string2=new StringBuffer("");
                for(int i=0;i<mJalawLawyerDetailVO.getPunishs().size();i++){
                  String str=  BaseCommonToStringUtil.toString(mJalawLawyerDetailVO.getPunishs().get(i).getCategory());
                    String result=  BaseCommonToStringUtil.toString(mJalawLawyerDetailVO.getPunishs().get(i).getResult());
                        if(str!=null&&str.equals("行政处罚")){
                            string1.append(TimeUtil.getYMDT(mJalawLawyerDetailVO.getPunishs().get(i).getPunishTimeStart())+mContext.getString(R.string.to)+
                                    TimeUtil.getYMDT(mJalawLawyerDetailVO.getPunishs().get(i).getPunishTimeEnd())+"  "+result+"\n");

                        }else if(str!=null&&str.equals("行业处分")){
                            string2.append(TimeUtil.getYMDT(mJalawLawyerDetailVO.getPunishs().get(i).getPunishTimeStart())+mContext.getString(R.string.to)+
                                    TimeUtil.getYMDT(mJalawLawyerDetailVO.getPunishs().get(i).getPunishTimeEnd())+"  "+result+"\n");

                        }
                }
                mTvAdministrativePenalty.setText(string1);
                mTvIndustryDispose.setText(string2);
            }


            if(mJalawLawyerDetailVO.getRewards()==null||mJalawLawyerDetailVO.getRewards().size()==0){
                mTvReward.setText(R.string.nothing);
            }else{
                StringBuffer stringBuffer=new StringBuffer("");
                for(int i=0;i<mJalawLawyerDetailVO.getRewards().size();i++){
                    String level=  BaseCommonToStringUtil.toString(mJalawLawyerDetailVO.getRewards().get(i).getLevel());
                    stringBuffer.append(StringUtil.getStringEmpty(mJalawLawyerDetailVO.getRewards().get(i).getYear())+mContext.getString(R.string.year)+"  "+
                            StringUtil.getStringEmpty(mJalawLawyerDetailVO.getRewards().get(i).getAwardOrganization())
                            +mContext.getString(R.string.clothing)+ mJalawLawyerDetailVO.getRewards().get(i).getName()+
                            "("+level+")"+"\n");
                }
                mTvReward.setText(stringBuffer);
            }

        }
    }

    class AccountViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvBottom;
        private TextView mTvPackUp;
        private LinearLayout mLlBottomDetail;
        private TextView mTvLawyerInfo;
        private TextView mTvInfo;
        private Context mContext;

        public AccountViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.mIvBottom = (ImageView) itemView.findViewById(R.id.iv_bottom);
            this.mTvPackUp = (TextView) itemView.findViewById(R.id.tv_pack_up);
            this.mLlBottomDetail = (LinearLayout) itemView.findViewById(R.id.ll_bottom_detail);
            this.mTvLawyerInfo = (TextView) itemView.findViewById(R.id.tv_lawyer_info);
            this.mTvInfo= (TextView) itemView.findViewById(R.id.tv_info);
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
            if(mJalawLawyerDetailVO.getIntroduction()==null||mJalawLawyerDetailVO.getIntroduction().equals("")){
                mTvInfo.setGravity(Gravity.CENTER);
                mTvInfo.setText(R.string.nothing);
            }else{
                mTvInfo.setText(mJalawLawyerDetailVO.getIntroduction());
            }

        }
    }

    class ServiceViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ImageView mIvBottom;
        private RecyclerView mRvServiceProject;
        private TextView mTvPackUp;
        private LinearLayout mLlBottomDetail;
        private TextView mTvLawyerInfo;
        private ServiceProjectAdpater mAdpater;
        private GridLayoutManager mLayoutManager;
        private TextView mTvNull;

        public ServiceViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.mIvBottom = (ImageView) itemView.findViewById(R.id.iv_bottom);
            this.mTvPackUp = (TextView) itemView.findViewById(R.id.tv_pack_up);
            this.mLlBottomDetail = (LinearLayout) itemView.findViewById(R.id.ll_bottom_detail);
            this.mTvLawyerInfo = (TextView) itemView.findViewById(R.id.tv_lawyer_info);
            this.mRvServiceProject = (RecyclerView) itemView.findViewById(R.id.rv_service_project);
            this.mTvNull= (TextView) itemView.findViewById(R.id.tv_null);
        }

        //设置数据
        public void setData(List<JalawLawyerServiceVO> services) {
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
            if(services==null||services.size()==0){
                mTvNull.setVisibility(View.VISIBLE);
                mRvServiceProject.setVisibility(View.GONE);
            }else {
                mTvNull.setVisibility(View.GONE);
                mRvServiceProject.setVisibility(View.VISIBLE);
                mAdpater = new ServiceProjectAdpater(services, mContext,mJalawLawyerDetailVO,isPractice);
                mRvServiceProject.setAdapter(mAdpater);
                mLayoutManager = new GridLayoutManager(mContext, 1);
                mRvServiceProject.setLayoutManager(mLayoutManager);
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
                mAdpater = new EvaluateAdpater(list, mContext,true);
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
            TextViewUtil.isEmpty(mTvLawIrmsAddress,mJalawLawyerDetailVO.getLawfirmAddress());
            TextViewUtil.isEmpty(mTvLawServiceCalls,mJalawLawyerDetailVO.getLawfirmTelephone());
            TextViewUtil.isEmpty(mTvLawServesMailbox,mJalawLawyerDetailVO.getLawfirmEmail());
            if(mJalawLawyerDetailVO.getLawfirmAxis()==null||mJalawLawyerDetailVO.getLawfirmAxis().equals("")){
                mIvMap.setVisibility(View.GONE);
                mTvNull.setVisibility(View.VISIBLE);
            }else {
                mIvMap.setVisibility(View.VISIBLE);
                mTvNull.setVisibility(View.GONE);
                String lawfirmAxis = mJalawLawyerDetailVO.getLawfirmAxis();
                int i = lawfirmAxis.indexOf(",");
                final String longitude = lawfirmAxis.substring(0, i);
                final String latitude = lawfirmAxis.substring(i + 1, lawfirmAxis.length());
                mIvMap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, WalkingRouteActivity.class);
                        intent.putExtra(WalkingRouteActivity.LATITUDE, Double.parseDouble(latitude));
                        intent.putExtra(WalkingRouteActivity.LONGITUDE, Double.parseDouble(longitude));
                        intent.putExtra(WalkingRouteActivity.ADDRESS, mJalawLawyerDetailVO.getLawfirmAddress());
                        intent.putExtra(WalkingRouteActivity.SERCESCALL, mJalawLawyerDetailVO.getLawfirmTelephone());
                        intent.putExtra(BaseActivity.CSTR_EXTRA_TITLE_STR, mJalawLawyerDetailVO.getName());
                        mContext.startActivity(intent);
                    }
                });

                String map_addresss = UrlUtil.getStaticMapImgUrl(mContext,longitude,latitude);
                ImageLoaderManager.LoadNetImage(map_addresss, mIvMap);
            }
        }
    }
}
