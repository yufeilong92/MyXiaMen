package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.vo.JaglsEvaluateVO;

import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.adapter
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @author: lzh
 * @date: 2017/7/28 09:15
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */



public class JaglsorgEvaluateAdpater extends RecyclerView.Adapter<JaglsorgEvaluateAdpater.ViewHolder> {
    private Context mContext;
    private List<JaglsEvaluateVO> mDatas;
    private Boolean isStaff;

    public JaglsorgEvaluateAdpater(List<JaglsEvaluateVO> list, Context context,boolean isStaff) {
        this.mDatas = list;
        this.mContext = context;
        this.isStaff=isStaff;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_evaluate, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(position==mDatas.size()-1){
            holder.mDivider.setVisibility(View.INVISIBLE);
        }
        holder.mTvName.setText(mDatas.get(position).getEntrustPersonName());
        if(isStaff) {
            holder.mTvContent.setText(mDatas.get(position).getEvaluateStaffDescribe());
        }else{
            holder.mTvContent.setText(mDatas.get(position).getEvaluateOrgDescribe());
        }
        holder.mTvTime.setText(mDatas.get(position).getEvaluateTime());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvAvatar;
        private TextView mTvName;
        private TextView mTvTime;
        private TextView mTvContent;
        private View mDivider;
        public ViewHolder(View itemView) {
            super(itemView);
            mTvContent = (TextView) itemView.findViewById(R.id.tv_content2);
            mIvAvatar = (ImageView) itemView
                    .findViewById(R.id.iv_avatar);
            mTvName = (TextView) itemView
                    .findViewById(R.id.tv_name1);
            mTvTime = (TextView) itemView
                    .findViewById(R.id.tv_time1);
            mDivider=itemView.findViewById(R.id.divider);

        }
    }
}
