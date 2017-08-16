package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.ui.personalcenter.MyJamedDetailActivity;
import com.lawyee.apppublic.vo.JamedApplyVO;

import java.util.ArrayList;

import static com.lawyee.apppublic.R.id.listitem_base_title_tv;
import static com.lawyee.apppublic.ui.personalcenter.MyJamedDetailActivity.JAMED;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.adapter
 * @Description: ${todo}(个人中心-人民调解列表页)
 * @author: lzh
 * @date: 2017/6/2 17:04
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class MyJamedAdpater extends BaseRecyclerAdapter<MyJamedAdpater.ViewHolder> {
    private Context mContext;
    private ArrayList<JamedApplyVO> mDatas;


    public MyJamedAdpater(ArrayList<JamedApplyVO> list, Context context) {
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
    public void onBindViewHolder(ViewHolder holder, int position, boolean isItem) {
        final JamedApplyVO jamedApplyVO=mDatas.get(position);
        holder.mListitemBaseTitleTv.setText(R.string.jamed_apply2);
        holder.mTvTime.setText(jamedApplyVO.getApplyTime());
            if(jamedApplyVO.getOrgAcceptFlag()==0){
                holder.mTvAidCheckInfo.setText(mContext.getText(R.string.being_audited));
                holder.mTvAidCheckInfo.setTextColor(ContextCompat.getColor(mContext,R.color.underway_color));
            }else if(jamedApplyVO.getOrgAcceptFlag()==-1){
                holder.mTvAidCheckInfo.setText(mContext.getText(R.string.jamed_no_accept));
                holder.mTvAidCheckInfo.setTextColor(ContextCompat.getColor(mContext,R.color.lose_color));
            }else if(jamedApplyVO.getOrgAcceptFlag()==1){
                switch (jamedApplyVO.getSuccessFlag()){
                    case 0:
                        holder.mTvAidCheckInfo.setText(mContext.getText(R.string.jamed_ing));
                        holder.mTvAidCheckInfo.setTextColor(ContextCompat.getColor(mContext,R.color.underway_color));
                        break;
                    case 1:
                        holder.mTvAidCheckInfo.setText(mContext.getText(R.string.jamed_success));
                        holder.mTvAidCheckInfo.setTextColor(ContextCompat.getColor(mContext,R.color.pass_color));
                        break;
                    case -1:
                        holder.mTvAidCheckInfo.setText(mContext.getText(R.string.jamed_fail));
                        holder.mTvAidCheckInfo.setTextColor(ContextCompat.getColor(mContext,R.color.lose_color));
                        break;
                }
            }



        holder.mLl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, MyJamedDetailActivity.class);
                intent.putExtra(JAMED,jamedApplyVO);
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
                    .findViewById(listitem_base_title_tv);
            mTvTime = (TextView) itemView
                    .findViewById(R.id.tv_time);
            mTvAidCheckInfo = (TextView) itemView
                    .findViewById(R.id.tv_aid_check_info);


        }
    }
}