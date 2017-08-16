package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.vo.JalawLawyerEntrustVO;

import java.util.ArrayList;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.adapter
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @author: czq
 * @date: 2017/7/20 09:36
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class LawyerEntrustAdpater extends BaseRecyclerAdapter<LawyerEntrustAdpater.ViewHolder> {
    private Context mContext;
    private ArrayList<JalawLawyerEntrustVO> mDatas;



    public LawyerEntrustAdpater(ArrayList<JalawLawyerEntrustVO> list, Context context) {
        this.mDatas = list;
        this.mContext = context;
    }


    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = View.inflate(mContext, R.layout.item_lawyer_entrust, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position, boolean isItem) {
        holder.mLlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getAdapterItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mListitemBaseTitleTv;
        private TextView mTvTitle;
        private TextView mTvTime;
        private TextView mTvCheckInfo;
        private TextView mTvContext;
        private LinearLayout mLlItem;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mLlItem = (LinearLayout) itemView.findViewById(R.id.ll_item);
            this.mListitemBaseTitleTv = (TextView) itemView
                    .findViewById(R.id.tv_title);
            this.mTvTime = (TextView) itemView
                    .findViewById(R.id.tv_time);
            this.mTvCheckInfo = (TextView) itemView
                    .findViewById(R.id.tv_aid_check_info);
            this.mTvContext= (TextView) itemView
                    .findViewById(R.id.tv_context);

        }
    }
}
