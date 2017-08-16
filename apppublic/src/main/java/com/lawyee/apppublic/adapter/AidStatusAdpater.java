package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.vo.JaaidApplyVO;

import java.util.ArrayList;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.adapter
 * @Description: ${todo}(个人中心法律援助状态信息)
 * @author: czq
 * @date: 2017/5/31 16:42
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class AidStatusAdpater extends RecyclerView.Adapter<AidStatusAdpater.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mDatas;
    private JaaidApplyVO mJaaidApplyVO;



    public AidStatusAdpater(ArrayList<String> list, Context context, JaaidApplyVO jaaidApplyVO) {
        this.mDatas = list;
        this.mContext = context;
        this.mJaaidApplyVO=jaaidApplyVO;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_aid_stutas, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
            if(position==mDatas.size()-1){
                holder.mViewDiviler.setVisibility(View.GONE);
            }
            switch (position){
                case 0:
                    holder.mTvStatus1.setText(mDatas.get(0));
                    holder.mTvStatus1.setTextColor(ContextCompat.getColor(mContext, R.color.pass_color));
                    holder.mIvStatus.setImageResource(R.drawable.ic_status_pass);
                    break;
                case 1:
                    if(mJaaidApplyVO.getAuditStatus()==0){
                        //当前状态在第二个位置
                        holder.mTvStatus1.setVisibility(View.GONE);
                        holder.mTvStatus2.setVisibility(View.VISIBLE);
                        holder.mTvStatus2.setText(mDatas.get(1));
                        holder.mIvStatus.setImageResource(R.drawable.ic_status_ing);
                    }else {
                        //当前状态不在第二个位置
                        holder.mTvStatus1.setText(mDatas.get(1));
                        holder.mTvStatus1.setTextColor(ContextCompat.getColor(mContext, R.color.pass_color));
                        holder.mIvStatus.setImageResource(R.drawable.ic_status_pass);
                    }
                    break;
                case 2:
                    holder.mTvStatus1.setText(mDatas.get(2));
                    if(mJaaidApplyVO.getAuditStatus()==-1){
                        //审核失败的情况
                        holder.mIvStatus.setImageResource(R.drawable.ic_status_fail);
                        holder.mTvStatus1.setTextColor(ContextCompat.getColor(mContext, R.color.lose_color));
                    }else if(mJaaidApplyVO.getAuditStatus()==1){
                        //审核成功的情况
                        holder.mIvStatus.setImageResource(R.drawable.ic_status_pass);
                        holder.mTvStatus1.setTextColor(ContextCompat.getColor(mContext, R.color.pass_color));
                    }

                    break;
                case 3:
                    if(mJaaidApplyVO.getAuditStatus()==-1){
                        //审核失败当前位置在4
                        holder.mTvStatus1.setVisibility(View.GONE);
                        holder.mTvStatus2.setVisibility(View.VISIBLE);
                        holder.mTvStatus2.setText(mDatas.get(3));
                        holder.mIvStatus.setImageResource(R.drawable.ic_status_pass);
                    }else if(mJaaidApplyVO.getAuditStatus()==1){
                        if(mJaaidApplyVO.getFeedbackStatus()==1){
                            //审核成功  指派成功
                            holder.mTvStatus1.setText(mDatas.get(3));
                            holder.mTvStatus1.setTextColor(ContextCompat.getColor(mContext, R.color.pass_color));
                            holder.mIvStatus.setImageResource(R.drawable.ic_status_pass);
                        }else {
                            //审核成功  指派中当前位置在4
                            holder.mTvStatus1.setVisibility(View.GONE);
                            holder.mTvStatus2.setVisibility(View.VISIBLE);
                            holder.mTvStatus2.setText(mDatas.get(3));
                            holder.mIvStatus.setImageResource(R.drawable.ic_status_ing);
                        }
                    }
                    break;
                case 4:
                    if(mJaaidApplyVO.getFeedbackStatus()==1){
                        //审核成功  指派了当前位置在5
                        holder.mTvStatus1.setVisibility(View.GONE);
                        holder.mTvStatus2.setVisibility(View.VISIBLE);
                        holder.mTvStatus2.setText(mDatas.get(4));
                        holder.mIvStatus.setImageResource(R.drawable.ic_status_pass);
                    }else {
                        holder.mTvStatus1.setText(mDatas.get(4));
                    }
                    break;

            }


    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvStatus;
        private TextView mTvStatus2;
        private TextView mTvStatus1;
        private View mViewDiviler;

        public ViewHolder(View itemView) {
            super(itemView);
            mIvStatus = (ImageView) itemView.findViewById(R.id.iv_status);
            mTvStatus2 = (TextView) itemView
                    .findViewById(R.id.tv_status_2);
            mTvStatus1 = (TextView) itemView
                    .findViewById(R.id.tv_status_1);
            mViewDiviler = itemView
                    .findViewById(R.id.view_diviler);


        }
    }
}