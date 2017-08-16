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
import com.lawyee.apppublic.ui.personalcenter.MyJaglsEntrustDetailActivity;
import com.lawyee.apppublic.vo.JaglsEntrustVO;

import java.util.ArrayList;

import static com.lawyee.apppublic.ui.personalcenter.MyJaglsEntrustDetailActivity.MYJAGLSENTRUST;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.adapter
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @author: lzh
 * @date: 2017/7/31 16:26
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class JaglsEntrustAdpater extends BaseRecyclerAdapter<JaglsEntrustAdpater.ViewHolder> {
    private Context mContext;
    private ArrayList<JaglsEntrustVO> mDatas;
    private boolean isLawyer=false;


    public JaglsEntrustAdpater(ArrayList<JaglsEntrustVO> list, Context context,boolean isLawyer) {
        this.mDatas = list;
        this.mContext = context;
        this.isLawyer=isLawyer;
    }


    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = View.inflate(mContext, R.layout.item_my_entrust, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position, boolean isItem) {
        holder.mListitemBaseTitleTv.setText(mDatas.get(position).getServiceName());
        holder.mTvTime.setText(mDatas.get(position).getEntrustTime());
        if(mDatas.get(position).getEntrustStatus()==1){
            holder.mTvCheckInfo.setText(R.string.already_manage);
            holder.mTvCheckInfo.setTextColor(ContextCompat.getColor(mContext,R.color.pass_color));
        }else{
            holder.mTvCheckInfo.setText(R.string.no_manage);
            holder.mTvCheckInfo.setTextColor(ContextCompat.getColor(mContext,R.color.underway_color));
        }

        holder.mLl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(mContext, MyJaglsEntrustDetailActivity.class);
                    intent.putExtra(MYJAGLSENTRUST, mDatas.get(position).getOid());
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
        private TextView mTvCheckInfo;
        public LinearLayout mLl_item;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mLl_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
            this.mListitemBaseTitleTv = (TextView) itemView
                    .findViewById(R.id.tv_title);
            this.mTvTime = (TextView) itemView
                    .findViewById(R.id.tv_time);
            this.mTvCheckInfo = (TextView) itemView
                    .findViewById(R.id.tv_aid_check_info);



        }
    }
}
