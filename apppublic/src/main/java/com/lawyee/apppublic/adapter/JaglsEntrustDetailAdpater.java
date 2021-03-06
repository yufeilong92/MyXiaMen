package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.util.TextViewUtil;
import com.lawyee.apppublic.vo.JaglsEntrustDetailVO;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.adapter
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @author: lzh
 * @date: 2017/8/1 09:11
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class JaglsEntrustDetailAdpater extends RecyclerView.Adapter {
    /**
     * 5种类型
     */
    public static final int ENTRUSTDETAIL = 0;//委托详情
    public static final int MANAGEDETAIL = 1;//委托办理详情
    public static final int EVALUATEDETAIL = 2;//评价详情
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    /**
     * 当前类型
     */
    public int currentType = ENTRUSTDETAIL;
    private int length;

    private JaglsEntrustDetailVO mJaglsEntrustDetailVO;



    public JaglsEntrustDetailAdpater(Context context, JaglsEntrustDetailVO mJaglsEntrustDetailVO, int length) {
        this.mContext = context;
        this.mJaglsEntrustDetailVO = mJaglsEntrustDetailVO;
        this.length = length;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ENTRUSTDETAIL) {
            return new EntrustDetailViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_my_entrust_detail, null));
        } else if (viewType == MANAGEDETAIL) {
            return new ManageDetailViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_my_manage_detail, null));
        } else if (viewType == EVALUATEDETAIL) {
            return new EvaluateDetailViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_my_evaluate_detail, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ENTRUSTDETAIL) {
            EntrustDetailViewHolder entrustDetailViewHolder = (EntrustDetailViewHolder) holder;
            entrustDetailViewHolder.setData();
        } else if (getItemViewType(position) == MANAGEDETAIL) {
            ManageDetailViewHolder manageDetailViewHolder = (ManageDetailViewHolder) holder;
            manageDetailViewHolder.setData();
        } else if (getItemViewType(position) == EVALUATEDETAIL) {
            EvaluateDetailViewHolder evaluateDetailViewHolder = (EvaluateDetailViewHolder) holder;
            evaluateDetailViewHolder.setData();
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
            case ENTRUSTDETAIL:
                currentType = ENTRUSTDETAIL;
                break;
            case MANAGEDETAIL:
                currentType = MANAGEDETAIL;
                break;
            case EVALUATEDETAIL:
                currentType = EVALUATEDETAIL;
                break;
        }
        return currentType;
    }

    @Override
    public int getItemCount() {
        return length;
    }


    class EntrustDetailViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private TextView mTvManageStatus;
        private TextView mTvEntrustPeople;
        private TextView mTvEntrustMatter;
        private TextView mTvEntrustTime;
        private TextView mTvEntrustDetail;

        public EntrustDetailViewHolder(Context mContext, View view) {
            super(view);
            this.mContext = mContext;
            this.mTvManageStatus = (TextView) view.findViewById(R.id.tv_manage_status);
            this.mTvEntrustPeople = (TextView) view.findViewById(R.id.tv_entrust_people);
            this.mTvEntrustMatter = (TextView) view.findViewById(R.id.tv_entrust_matter);
            this.mTvEntrustTime = (TextView) view.findViewById(R.id.tv_entrust_time);
            this.mTvEntrustDetail = (TextView) view.findViewById(R.id.tv_entrust_detail);
        }

        //设置数据
        public void setData() {
            if (mJaglsEntrustDetailVO.getEntrustStatus() == 0) {
                mTvManageStatus.setText(R.string.no_manage);
            } else {
                mTvManageStatus.setText(R.string.already_manage);
            }

            TextViewUtil.isEmpty(mTvEntrustPeople, mJaglsEntrustDetailVO.getEntrustPersonName());
            TextViewUtil.isEmpty(mTvEntrustMatter, mJaglsEntrustDetailVO.getServiceName());
            TextViewUtil.isEmpty(mTvEntrustTime, mJaglsEntrustDetailVO.getEntrustTime());
            TextViewUtil.isEmpty(mTvEntrustDetail, mJaglsEntrustDetailVO.getEntrustContent());
        }
    }

    class ManageDetailViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private TextView mTvEntrustReply;
        private TextView mTvPhone;
        private TextView mTvSpecialLane;

        public ManageDetailViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.mTvEntrustReply = (TextView) itemView.findViewById(R.id.tv_entrust_reply);
            this.mTvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            this.mTvSpecialLane = (TextView) itemView.findViewById(R.id.tv_special_lane);

        }

        //设置数据
        public void setData() {
            TextViewUtil.isEmpty(mTvEntrustReply, mJaglsEntrustDetailVO.getEntrustStaffAnswer());
            TextViewUtil.isEmpty(mTvPhone, mJaglsEntrustDetailVO.getEntrustStaffMobilePhone());
            TextViewUtil.isEmpty(mTvSpecialLane, mJaglsEntrustDetailVO.getEntrustStaffFixedPhone());
        }
    }

    class EvaluateDetailViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private TextView mTvLawyer;
        private TextView mTvLawyerSuggest;
        private TextView mTvLawfirm;
        private TextView mTvLawfirmSuggest;
        private TextView mTvWorker1;
        private TextView mTvOrg;
        public EvaluateDetailViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.mTvLawyer = (TextView) itemView.findViewById(R.id.tv_lawyer);
            this.mTvLawyerSuggest = (TextView) itemView.findViewById(R.id.tv_lawyer_suggest);
            this.mTvLawfirm = (TextView) itemView.findViewById(R.id.tv_lawfirm);
            this.mTvLawfirmSuggest = (TextView) itemView.findViewById(R.id.tv_lawfirm_suggest);
            this.mTvWorker1= (TextView) itemView.findViewById(R.id.tv_worker_1);
            this.mTvOrg= (TextView) itemView.findViewById(R.id.tv_org);
            this. mTvWorker1.setText(R.string.evaluation_stuff);
            this.mTvOrg.setText(R.string.evaluation_org);
        }

        //设置数据
        public void setData() {
            TextViewUtil.isEmpty(mTvLawyer, toNum(mJaglsEntrustDetailVO.getEvaluateStaffScore()));
            TextViewUtil.isEmpty(mTvLawyerSuggest, mJaglsEntrustDetailVO.getEvaluateStaffDescribe());
            TextViewUtil.isEmpty(mTvLawfirm, toNum(mJaglsEntrustDetailVO.getEvaluateOrgScore()));
            TextViewUtil.isEmpty(mTvLawfirmSuggest, mJaglsEntrustDetailVO.getEvaluateOrgDescribe());
        }
    }

    private String toNum(String num) {
        String sore = mContext.getString(R.string.great_satisfaction);
        if (num.equals("1")) {
            sore = mContext.getString(R.string.unsatisfactory);
        } else if (num.equals("2")) {
            sore = mContext.getString(R.string.satisfied);
        }
        return sore;
    }
}