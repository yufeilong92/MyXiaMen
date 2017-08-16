package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.ui.personalcenter.MyLawAidDetailActivity;
import com.lawyee.apppublic.vo.JaaidApplyVO;

import java.util.ArrayList;

import static com.lawyee.apppublic.ui.personalcenter.MyLawAidDetailActivity.AID;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.adapter
 * @Description: ${todo}(个人中心——法律援助列表页面的Adpater)
 * @author: czq
 * @date: 2017/5/31 14:26
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class MyLawAidAdpater extends BaseRecyclerAdapter<MyLawAidAdpater.ViewHolder> {
    private Context mContext;
    private ArrayList<JaaidApplyVO> mDatas;


    public MyLawAidAdpater(ArrayList<JaaidApplyVO> list, Context context) {
        this.mDatas = list;
        this.mContext = context;
    }


    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = View.inflate(mContext, R.layout.item_my_law_aid, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position, boolean isItem) {
        holder.mTvTime.setText(mDatas.get(position).getApplyDate());
        if(mDatas.get(position).getAuditStatus()==0){
            holder.mTvAidCheckInfo.setText(mContext.getText(R.string.being_audited));
            holder.mTvAidCheckInfo.setTextColor(mContext.getResources().getColor(R.color.underway_color));
        }else if(mDatas.get(position).getAuditStatus()==1){
            if(mDatas.get(position).getFeedbackStatus()==0){
                holder.mTvAidCheckInfo.setText(mContext.getText(R.string.in_process));
                holder.mTvAidCheckInfo.setTextColor(mContext.getResources().getColor(R.color.underway_color));
            }else {
                holder.mTvAidCheckInfo.setText(mContext.getText(R.string.been_finished_3));
                holder.mTvAidCheckInfo.setTextColor(mContext.getResources().getColor(R.color.pass_color));
            }
        }else if(mDatas.get(position).getAuditStatus()==-1){
            holder.mTvAidCheckInfo.setText(mContext.getText(R.string.audited_fail));
            holder.mTvAidCheckInfo.setTextColor(mContext.getResources().getColor(R.color.lose_color));
        }

        holder.mLl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,MyLawAidDetailActivity.class);
                intent.putExtra(AID,mDatas.get(position));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getAdapterItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mListitemBaseTitleTv;
        private TextView mTvTime;
        private TextView mTvAidCheckInfo;
        public LinearLayout mLl_item;

        public ViewHolder(View itemView) {
            super(itemView);
            mLl_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
            mListitemBaseTitleTv = (TextView) itemView
                    .findViewById(R.id.listitem_base_title_tv);
            mTvTime = (TextView) itemView
                    .findViewById(R.id.tv_time);
            mTvAidCheckInfo = (TextView) itemView
                    .findViewById(R.id.tv_aid_check_info);


        }
    }
}