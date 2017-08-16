package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.vo.JamedApplyDetailVO;

import java.util.ArrayList;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.adapter
 * @Description: ${todo}(个人中心人民调解状态信息)
 * @author: czq
 * @date: 2017/6/5 11:56
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class JamedStatusAdpater extends RecyclerView.Adapter<JamedStatusAdpater.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mDatas;
    private JamedApplyDetailVO mJamedApplyVO;
    private boolean isCheck=false;
    private boolean ising=false;



    public JamedStatusAdpater(ArrayList<String> list, Context context, JamedApplyDetailVO jamedApplyVO) {
        this.mDatas = list;
        this.mContext = context;
        this.mJamedApplyVO=jamedApplyVO;
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
            case 0://第一位置的
                holder.mTvStatus1.setText(mDatas.get(0));
                holder.mTvStatus1.setTextColor(ContextCompat.getColor(mContext, R.color.pass_color));
                holder.mIvStatus.setImageResource(R.drawable.ic_status_pass);
                break;
            case 1:
                 isCheck=false;
                if(mJamedApplyVO.getOrgAcceptFlag()==0) {
                    //当前状态在第二个位置
                    isCheck=true;
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
                if(!isCheck) {
                        if (mJamedApplyVO.getOrgAcceptFlag() == -1) {
                            holder.mIvStatus.setImageResource(R.drawable.ic_status_fail);
                            holder.mTvStatus1.setTextColor(ContextCompat.getColor(mContext, R.color.lose_color));
                        } else if (mJamedApplyVO.getOrgAcceptFlag() == 1) {
                            holder.mIvStatus.setImageResource(R.drawable.ic_status_pass);
                            holder.mTvStatus1.setTextColor(ContextCompat.getColor(mContext, R.color.pass_color));
                        }
                }
                break;
            case 3:
                if(!isCheck) {
                    ising=false;
                        if (mJamedApplyVO.getOrgAcceptFlag() == -1) {
                            holder.mTvStatus1.setVisibility(View.GONE);
                            holder.mTvStatus2.setVisibility(View.VISIBLE);
                            holder.mTvStatus2.setText(mDatas.get(3));
                            holder.mIvStatus.setImageResource(R.drawable.ic_status_pass);
                        } else if (mJamedApplyVO.getOrgAcceptFlag() == 1) {
                            if (mJamedApplyVO.getSuccessFlag() == 1 || mJamedApplyVO.getSuccessFlag() == -1) {
                                holder.mTvStatus1.setText(mDatas.get(3));
                                holder.mTvStatus1.setTextColor(ContextCompat.getColor(mContext, R.color.pass_color));
                                holder.mIvStatus.setImageResource(R.drawable.ic_status_pass);
                            } else if (mJamedApplyVO.getSuccessFlag() == 0) {
                                ising=true;
                                holder.mTvStatus1.setVisibility(View.GONE);
                                holder.mTvStatus2.setVisibility(View.VISIBLE);
                                holder.mTvStatus2.setText(mDatas.get(3));
                                holder.mIvStatus.setImageResource(R.drawable.ic_status_ing);
                            }
                        }

                }else {
                    holder.mTvStatus1.setText(mDatas.get(3));
                }
                break;
            case 4:
                if(!isCheck&&!ising) {
                    if (mJamedApplyVO.getSuccessFlag() == 1 || mJamedApplyVO.getSuccessFlag() == -1) {
                        holder.mTvStatus1.setVisibility(View.GONE);
                        holder.mTvStatus2.setVisibility(View.VISIBLE);
                        holder.mTvStatus2.setText(mDatas.get(4));
                        holder.mIvStatus.setImageResource(R.drawable.ic_status_pass);
                    }
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