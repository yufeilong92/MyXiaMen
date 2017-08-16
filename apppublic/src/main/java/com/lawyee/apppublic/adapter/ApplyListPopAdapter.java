package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lawyee.apppublic.R;

import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawerApp
 * @Package com.lawyee.apppublic.adapter
 * @Description: S申请页弹框显示
 * @author: YFL
 * @date: 2017/6/3 17:27
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class ApplyListPopAdapter extends RecyclerView.Adapter<ApplyListPopAdapter.ViewHolder> implements View.OnClickListener {

    private List<String> mData;
    private Context mContext;
    private final LayoutInflater mInflater;

    private String content=null;
    public ApplyListPopAdapter(List<String> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }
    public void setSeletsStr(String str){
        this.content=str;

        notifyDataSetChanged();
    }

    public OnRecyclerItemClickListener getOnRecyclerItemClickListener() {
        return onRecyclerItemClickListener;
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    private OnRecyclerItemClickListener onRecyclerItemClickListener=null;
    @Override
    public void onClick(View v) {
        if (onRecyclerItemClickListener!=null){
            onRecyclerItemClickListener.OnItemClickListener(v, (String) v.getTag(),v.getId());
        }
    }

    public interface OnRecyclerItemClickListener{
        void OnItemClickListener(View view,String item,int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_apply_poplayout, null);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(mData.get(position));
        holder.itemView.setId(position);
        if (content.equals(mData.get(position))){
            holder.mTvApplyItem.setText(mData.get(position));
            holder.mTvApplyItem.setTextColor(mContext.getResources().getColor(R.color.red_org));
        }else {
            holder.mTvApplyItem.setText(mData.get(position));
            holder.mTvApplyItem.setTextColor(mContext.getResources().getColor(R.color.grey_org));
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class ViewHolder  extends RecyclerView.ViewHolder{
        public TextView mTvApplyItem;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mTvApplyItem = (TextView) itemView.findViewById(R.id.tv_apply_item);
        }
    }
}
