package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.ui.personalcenter.lawyer.CheckChatActivity;
import com.lawyee.apppublic.util.TextViewUtil;
import com.lawyee.apppublic.vo.JacstConsulationDetailVO;

import net.lawyee.mobilelib.utils.TimeUtil;

import static com.lawyee.apppublic.ui.BaseActivity.CSTR_EXTRA_TITLE_STR;
import static com.lawyee.apppublic.ui.lawyerService.SessionActivity.CSTR_EXTRA_SESSION_STR;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.adapter
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @author: lzh
 * @date: 2017/8/3 15:26
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class MyConsultDetailAdpater extends RecyclerView.Adapter {
    /**
     * 5种类型
     */
    public static final int CONSULTDETAIL = 0;//咨询详情详情
    public static final int CONSULTEVALUATE = 1;//咨询评价详情
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    /**
     * 当前类型
     */
    public int currentType = CONSULTDETAIL;
    private int length;

    private JacstConsulationDetailVO mJacstConsulationDetailVO;



    public MyConsultDetailAdpater(Context context, JacstConsulationDetailVO mJacstConsulationDetailVO, int length) {
        this.mContext = context;
        this.mJacstConsulationDetailVO = mJacstConsulationDetailVO;
        this.length = length;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == CONSULTDETAIL) {
            return new EntrustDetailViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_my_consult_detail, null));
        } else if (viewType == CONSULTEVALUATE) {
            return new EvaluateDetailViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_my_consult_evaluate, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == CONSULTDETAIL) {
            EntrustDetailViewHolder entrustDetailViewHolder = (EntrustDetailViewHolder) holder;
            entrustDetailViewHolder.setData();
        } else if (getItemViewType(position) == CONSULTEVALUATE) {
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
            case CONSULTDETAIL:
                currentType = CONSULTDETAIL;
                break;
            case CONSULTEVALUATE:
                currentType = CONSULTEVALUATE;
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
        private TextView mTvConsultPeople;
        private TextView mTvConsultTime;
        private TextView mTvBusinessType;
        private TextView mTvConsultContent;
        private TextView mTvConsultDealExplain;
        private TextView mTvCheck;
        private TextView mTv1;
        private TextView mTvConsultPeopleOrg;
        private LinearLayout mLlOrg;

        public EntrustDetailViewHolder(Context mContext, View view) {
            super(view);
            this.mContext = mContext;
            this.mTvConsultPeople = (TextView) view.findViewById(R.id.tv_consult_people);
            this.mTvConsultTime = (TextView) view.findViewById(R.id.tv_consult_time);
            this.mTvBusinessType = (TextView) view.findViewById(R.id.tv_business_type);
            this.mTvConsultContent = (TextView) view.findViewById(R.id.tv_consult_content);
            this.mTvConsultDealExplain = (TextView) view.findViewById(R.id.tv_consult_deal_explain);
            this.mTvCheck = (TextView) view.findViewById(R.id.tv_check);
            this.mTv1 = (TextView) view.findViewById(R.id.tv_1);
            this.mTvConsultPeopleOrg = (TextView) view.findViewById(R.id.tv_consult_people_org);
            this.mLlOrg = (LinearLayout) view.findViewById(R.id.ll_org);
        }

        //设置数据
        public void setData() {
            if (ApplicationSet.getInstance().getUserVO().isPublicUser()) {
                mLlOrg.setVisibility(View.VISIBLE);
                mTv1.setText(R.string.consult_bepeopled);
                TextViewUtil.isEmpty(mTvConsultPeople, mJacstConsulationDetailVO.getAnswerPerson());
                TextViewUtil.isEmpty(mTvConsultPeopleOrg, mJacstConsulationDetailVO.getAcceptUnitName());
            } else {
                mLlOrg.setVisibility(View.GONE);
                TextViewUtil.isEmpty(mTvConsultPeople, mJacstConsulationDetailVO.getConsultPerson());
            }
            TextViewUtil.isEmpty(mTvConsultTime, TimeUtil.getYMDT(mJacstConsulationDetailVO.getConsultDate()));
            TextViewUtil.isEmpty(mTvBusinessType, mJacstConsulationDetailVO.getConsultTypeName());
            TextViewUtil.isEmpty(mTvConsultContent, mJacstConsulationDetailVO.getConsultCotent());
            TextViewUtil.isEmpty(mTvConsultDealExplain, mJacstConsulationDetailVO.getDealResult());
            mTvCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(mContext, CheckChatActivity.class);
                    intent.putExtra(CSTR_EXTRA_SESSION_STR,mJacstConsulationDetailVO.getOid());
                    if (ApplicationSet.getInstance().getUserVO().isPublicUser()) {
                        intent.putExtra(CSTR_EXTRA_TITLE_STR,mJacstConsulationDetailVO.getAnswerPerson());
                    } else {
                        intent.putExtra(CSTR_EXTRA_TITLE_STR,mJacstConsulationDetailVO.getConsultPerson());
                    }

                    mContext.startActivity(intent);
                }
            });
        }
    }


    class EvaluateDetailViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private TextView mTvEvaluate2;
        private TextView mTvSuggest2;

        public EvaluateDetailViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.mTvEvaluate2 = (TextView) itemView.findViewById(R.id.tv_evaluate2);
            this.mTvSuggest2 = (TextView) itemView.findViewById(R.id.tv_suggest2);
        }

        //设置数据
        public void setData() {
            TextViewUtil.isEmpty(mTvEvaluate2, toNum(mJacstConsulationDetailVO.getEvaluateScore()));
           TextViewUtil.isEmpty(mTvSuggest2, mJacstConsulationDetailVO.getEvaluateDescribe());
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