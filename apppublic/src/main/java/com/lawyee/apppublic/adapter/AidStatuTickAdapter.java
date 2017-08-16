package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.util.TextViewUtil;
import com.lawyee.apppublic.vo.JaaidApplyDetailVO;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.adapter
 * @Description: ${todo}(个人中心法律援助反馈信息Adpater)
 * @author: czq
 * @date: 2017/6/1 11:50
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class AidStatuTickAdapter extends RecyclerView.Adapter<AidStatuTickAdapter.ViewHolder> {
    private Context mContext;
    private JaaidApplyDetailVO mjaaidJaaidApplyDetailVO;
    private int mLength=0;


    public AidStatuTickAdapter(JaaidApplyDetailVO mjaaidJaaidApplyDetailVO, Context context) {
        this.mContext = context;
        this.mjaaidJaaidApplyDetailVO = mjaaidJaaidApplyDetailVO;
        checkLength();
    }
//判断长度
    private void checkLength() {
        if((mjaaidJaaidApplyDetailVO.getAuditStatus()==1&&mjaaidJaaidApplyDetailVO.getFeedbackStatus()==0)
                ||mjaaidJaaidApplyDetailVO.getAuditStatus()==-1){
            this.mLength=1;
        }else if(mjaaidJaaidApplyDetailVO.getAuditStatus()==1&&mjaaidJaaidApplyDetailVO.getFeedbackStatus()==1){
            this.mLength=2;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_aid_stutas_tick, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
                switch (position){
                    case 0:
                        if(mLength==0){//没有反馈数据
                            holder.mLlAssign.setVisibility(View.GONE);
                            holder.mLlAudit.setVisibility(View.GONE);
                            holder.mTvNull.setVisibility(View.VISIBLE);
                        }else if(mLength==1){//有审核数据
                            holder.mLlAssign.setVisibility(View.GONE);
                            holder.mLlAudit.setVisibility(View.VISIBLE);
                            holder.mTvNull.setVisibility(View.GONE);
                            holder.mTvTime2.setText(mjaaidJaaidApplyDetailVO.getAuditTime());
                            if(mjaaidJaaidApplyDetailVO.getAuditStatus()==1){
                                holder.mTvAuditResult.setText(R.string.pass);
                            }else{
                                holder.mTvAuditResult.setText(R.string.no_pass);
                            }
                            holder.mTvAuditExplain.setText(mjaaidJaaidApplyDetailVO.getAuditOpinion());
                        }else {//有指派数据
                            holder.mLlAssign.setVisibility(View.VISIBLE);
                            holder.mLlAudit.setVisibility(View.GONE);
                            holder.mTvNull.setVisibility(View.GONE);
                            holder.mTvTime.setText(mjaaidJaaidApplyDetailVO.getAssignTime());
                            holder.mTvAssign.setText(mjaaidJaaidApplyDetailVO.getAssignLawyerName()+mContext.getString(R.string.left_bracket)+
                                    mjaaidJaaidApplyDetailVO.getAssignOrgName()+mContext.getString(R.string.right_bracket));
                             TextViewUtil.isEmpty(holder.mTvPhone,mjaaidJaaidApplyDetailVO.getAssignTel());
                        }
                        break;
                    case 1:
                        //有审核数据
                        holder.mLlAssign.setVisibility(View.GONE);
                        holder.mLlAudit.setVisibility(View.VISIBLE);
                        holder.mTvNull.setVisibility(View.GONE);
                        holder.mTvTime2.setText(mjaaidJaaidApplyDetailVO.getAuditTime());
                        holder.mTvAuditResult.setText(R.string.pass);
                        holder.mTvAuditExplain.setText(mjaaidJaaidApplyDetailVO.getAuditOpinion());
                        break;
                }
    }

    @Override
    public int getItemCount() {

        return mLength==0? 1:mLength;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvNull;
        private TextView mTvTime;
        private TextView mTvAssign;
        private TextView mTvPhone;
        private LinearLayout mLlAssign;
        private TextView mTvTime2;
        private TextView mTvAuditResult;
        private TextView mTvAuditExplain;
        private LinearLayout mLlAudit;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvNull = (TextView) itemView.findViewById(R.id.tv_null);
            mTvTime = (TextView) itemView
                    .findViewById(R.id.tv_time);
            mTvAssign = (TextView) itemView.findViewById(R.id.tv_assign);
            mTvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            mLlAssign = (LinearLayout) itemView.findViewById(R.id.ll_assign);
            mTvTime2 = (TextView) itemView.findViewById(R.id.tv_time_2);
            mTvAuditResult= (TextView) itemView.findViewById(R.id.tv_audit_result);
            mTvAuditExplain= (TextView) itemView.findViewById(R.id.tv_audit_explain);
            mLlAudit=(LinearLayout) itemView.findViewById(R.id.ll_audit);
        }
    }
}
