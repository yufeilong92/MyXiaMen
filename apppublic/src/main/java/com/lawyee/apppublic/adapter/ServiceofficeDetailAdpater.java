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
import com.lawyee.apppublic.vo.JaglsEvaluateVO;
import com.lawyee.apppublic.vo.JaglsOrgDetailVO;
import com.lawyee.apppublic.vo.JaglsServiceVO;

import net.lawyee.mobilelib.utils.TimeUtil;

import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.adapter
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @author: lzh
 * @date: 2017/7/14 10:24
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class ServiceofficeDetailAdpater extends RecyclerView.Adapter {
    /**
     * 5种类型
     */
    public static final int INFO2 = 0;//服务所信息
    public static final int ACCOUNT2 = 1;//服务所简介
    public static final int SERVICE2 = 2;//专业领域
    public static final int MAP1 = 4;//地图信息
    public static final int EVALUATE2 = 3;//评价信息

    private Context mContext;
    private JaglsOrgDetailVO mJaglsOrgDetailVO;
    private LayoutInflater mLayoutInflater;
    /**
     * 当前类型
     */
    public int currentType = INFO2;

    private boolean isPractice = true;

    private String mAddress;

    public ServiceofficeDetailAdpater(Context context, JaglsOrgDetailVO mJalawLawyerDetailVO,String address) {
        this.mContext = context;
        this.mJaglsOrgDetailVO = mJalawLawyerDetailVO;
        this.mAddress=address;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == INFO2) {
            return new InfoViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_lawoffice_info, null));
        } else if (viewType == ACCOUNT2) {
            return new AccountViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_lawyer_account, null));
        } else if (viewType == SERVICE2) {
            return new ServiceViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_service_project, null));
        } else if (viewType == MAP1) {
            return new MapViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_contact_me, null));
        } else if (viewType == EVALUATE2) {
            return new EvaluateViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_client_evaluation, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == INFO2) {
            InfoViewHolder infoViewHolder = (InfoViewHolder) holder;
            infoViewHolder.setData();
        } else if (getItemViewType(position) == ACCOUNT2) {
            AccountViewHolder accountViewHolder = (AccountViewHolder) holder;
            accountViewHolder.setData();
        } else if (getItemViewType(position) == SERVICE2) {
            ServiceViewHolder serviceViewHolder = (ServiceViewHolder) holder;
            serviceViewHolder.setData(mJaglsOrgDetailVO.getServices());
        } else if (getItemViewType(position) == MAP1) {
            MapViewHolder contactViewHolder = (MapViewHolder) holder;
            contactViewHolder.setData();
        } else if (getItemViewType(position) == EVALUATE2) {
            EvaluateViewHolder evaluateViewHolder = (EvaluateViewHolder) holder;
            evaluateViewHolder.setData(mJaglsOrgDetailVO.getEvaluates());
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
            case ACCOUNT2:
                currentType = ACCOUNT2;
                break;
            case SERVICE2:
                currentType = SERVICE2;
                break;
            case MAP1:
                currentType = MAP1;
                break;
            case EVALUATE2:
                currentType = EVALUATE2;
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
        private TextView mTvOrganizationName;
        private TextView mTvLawFirmAdministrative;
        private TextView mTvRegionType;
        private TextView mTvFoundingTime;
        private TextView mTextView11;
        private TextView mTvOrganizationalForm;
        private TextView mTvWorkerNum;
        private TextView mTvPrincipal;
        private TextView mTvAnnualAppraisal;
        private TextView mTvPackUp;
        private LinearLayout mLlBottomDetail;
        private TextView mTvServiceOfficeInfo;

        public InfoViewHolder(Context mContext, View view) {
            super(view);
            this.mContext = mContext;
            this.mIvBottom = (ImageView) itemView.findViewById(R.id.iv_bottom);
            this.mTvOrganizationName = (TextView) itemView.findViewById(R.id.tv_organization_name);
            this.mTvRegionType = (TextView) itemView.findViewById(R.id.tv_region_type);
            this.mTvFoundingTime = (TextView) itemView.findViewById(R.id.tv_founding_time);
            this.mTvPrincipal = (TextView) itemView.findViewById(R.id.tv_principal);
            this.mTvOrganizationalForm= (TextView) itemView.findViewById(R.id.tv_organizational_form);
            this.mTvLawFirmAdministrative = (TextView) itemView.findViewById(R.id.tv_law_firm_administrative);
            this.mTvAnnualAppraisal= (TextView) itemView.findViewById(R.id.tv_annual_appraisal);
            this.mTvWorkerNum = (TextView) itemView.findViewById(R.id.tv_worker_num);
            this.mTvPackUp = (TextView) itemView.findViewById(R.id.tv_pack_up);
            this.mLlBottomDetail = (LinearLayout) itemView.findViewById(R.id.ll_bottom_detail);
            this.mTvServiceOfficeInfo = (TextView) itemView.findViewById(R.id.tv_service_office_info);

        }

        //设置数据
        public void setData() {
            mTvServiceOfficeInfo.setOnClickListener(new View.OnClickListener() {
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
            TextViewUtil.isEmpty(mTvOrganizationName, mJaglsOrgDetailVO.getName());
            TextViewUtil.isEmpty(mTvLawFirmAdministrative, mJaglsOrgDetailVO.getJusticeBureauName());
            TextViewUtil.isEmpty(mTvRegionType, BaseCommonToStringUtil.toString(mJaglsOrgDetailVO.getAdmLevel()));
            TextViewUtil.isEmpty(mTvFoundingTime,   TimeUtil.getYMDT( mJaglsOrgDetailVO.getSetupDate()));
            TextViewUtil.isEmpty(mTvOrganizationalForm,mJaglsOrgDetailVO.getOrganizationForms());
            TextViewUtil.isEmpty(mTvWorkerNum, mJaglsOrgDetailVO.getStaffNumber());
            TextViewUtil.isEmpty(mTvPrincipal, mJaglsOrgDetailVO.getDirectorName());
            TextViewUtil.isEmpty(mTvAnnualAppraisal, mJaglsOrgDetailVO.getCurrentAssessmentResult());
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
            this.mTvInfo = (TextView) itemView.findViewById(R.id.tv_info);
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
            mTvLawyerInfo.setText(R.string.serviceoffice_intro);
            if (mJaglsOrgDetailVO.getIntroduction() == null || mJaglsOrgDetailVO.getIntroduction().equals("")) {
                mTvInfo.setGravity(Gravity.CENTER);
                mTvInfo.setText(R.string.nothing);
            } else {
                mTvInfo.setText(mJaglsOrgDetailVO.getIntroduction());
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
        private JaglsOrgDetailFieldAdpater mAdpater;
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
            this.mTvNull = (TextView) itemView.findViewById(R.id.tv_null);
            mTvLawyerInfo.setText(R.string.professional_field2);
        }

        //设置数据
        public void setData(List<JaglsServiceVO> services) {
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
            if (services == null || services.size() == 0) {
                mTvNull.setVisibility(View.VISIBLE);
                mRvServiceProject.setVisibility(View.GONE);
            } else {
                mTvNull.setVisibility(View.GONE);
                mRvServiceProject.setVisibility(View.VISIBLE);
                 mAdpater = new JaglsOrgDetailFieldAdpater(services, mContext,false);
                mRvServiceProject.setAdapter(mAdpater);
                mLayoutManager = new GridLayoutManager(mContext, 1);
                mRvServiceProject.setLayoutManager(mLayoutManager);
            }
        }
    }


    class MapViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ImageView mIvBottom;
        private ImageView mIvMap;
        private TextView mTvPackUp;
        private LinearLayout mLlBottomDetail;
        private TextView mTvLawyerInfo;
        private TextView mTvNull;
        private LinearLayout mLlContent;
        private View mViewTop;

        public MapViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.mIvBottom = (ImageView) itemView.findViewById(R.id.iv_bottom);
            this.mTvPackUp = (TextView) itemView.findViewById(R.id.tv_pack_up);
            this.mLlBottomDetail = (LinearLayout) itemView.findViewById(R.id.ll_bottom_detail);
            this.mTvLawyerInfo = (TextView) itemView.findViewById(R.id.tv_lawyer_info);
            this.mIvMap = (ImageView) itemView.findViewById(R.id.iv_map);
            this.mTvNull = (TextView) itemView.findViewById(R.id.tv_null2);
            this.mLlContent= (LinearLayout) itemView.findViewById(R.id.ll_content);
            this.mViewTop=itemView.findViewById(R.id.view_top);
            mLlContent.setVisibility(View.GONE);
            mViewTop.setVisibility(View.VISIBLE);
            mTvLawyerInfo.setText(R.string.map_info);
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

            if (mJaglsOrgDetailVO.getAxis() == null || mJaglsOrgDetailVO.getAxis().equals("")) {
                mIvMap.setVisibility(View.GONE);
                mTvNull.setVisibility(View.VISIBLE);
            } else {
                mIvMap.setVisibility(View.VISIBLE);
                mTvNull.setVisibility(View.GONE);
                String lawfirmAxis = mJaglsOrgDetailVO.getAxis();
                int i = lawfirmAxis.indexOf(",");
                final String longitude = lawfirmAxis.substring(0, i);
                final String latitude = lawfirmAxis.substring(i + 1, lawfirmAxis.length());
                mIvMap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, WalkingRouteActivity.class);
                        intent.putExtra(WalkingRouteActivity.LATITUDE, Double.parseDouble(latitude));
                        intent.putExtra(WalkingRouteActivity.LONGITUDE, Double.parseDouble(longitude));
                         intent.putExtra(WalkingRouteActivity.ADDRESS, mAddress);
                        intent.putExtra(WalkingRouteActivity.SERCESCALL, mJaglsOrgDetailVO.getTelephone());
                        intent.putExtra(BaseActivity.CSTR_EXTRA_TITLE_STR, mJaglsOrgDetailVO.getName());
                        mContext.startActivity(intent);
                    }
                });

                String map_addresss = UrlUtil.getStaticMapImgUrl(mContext, longitude, latitude);
                ImageLoaderManager.LoadNetImage(map_addresss, mIvMap);
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
        private JaglsorgEvaluateAdpater mAdpater;
        private GridLayoutManager mLayoutManager;

        public EvaluateViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.mIvBottom = (ImageView) itemView.findViewById(R.id.iv_bottom);
            this.mTvPackUp = (TextView) itemView.findViewById(R.id.tv_pack_up);
            this.mLlBottomDetail = (LinearLayout) itemView.findViewById(R.id.ll_bottom_detail);
            this.mTvLawyerInfo = (TextView) itemView.findViewById(R.id.tv_lawyer_info);
            this.mRvCeval = (RecyclerView) itemView.findViewById(R.id.rv_ceval);
            this.mTvNull = (TextView) itemView.findViewById(R.id.tv_null);
        }

        //设置数据
        public void setData(List<JaglsEvaluateVO> list) {
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
            if (list == null || list.size() == 0) {
                mTvNull.setVisibility(View.VISIBLE);
                mRvCeval.setVisibility(View.GONE);
            } else {
                mTvNull.setVisibility(View.GONE);
                mRvCeval.setVisibility(View.VISIBLE);
                 mAdpater = new JaglsorgEvaluateAdpater(list, mContext,false);
                mRvCeval.setAdapter(mAdpater);
                mLayoutManager = new GridLayoutManager(mContext, 1);
                mRvCeval.setLayoutManager(mLayoutManager);
            }
        }
    }
}
