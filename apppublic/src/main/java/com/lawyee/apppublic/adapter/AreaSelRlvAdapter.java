package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.vo.BaseCommonDataVO;

import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawerApp
 * @Package com.lawyee.apppublic.adapter
 * @Description: 筛选地区
 * @author: YFL
 * @date: 2017/6/4 17:16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class AreaSelRlvAdapter extends RecyclerView.Adapter<AreaSelRlvAdapter.ViewHolder> implements View.OnClickListener {
    private List<BaseCommonDataVO> mData;
    private Context mContext;
    private final LayoutInflater mInflater;
    private OnRecycleViewListener onItemListener = null;
    private int mSelPosition = -1;
    private boolean isCheck;

    public OnRecycleViewListener getOnItemListener() {
        return onItemListener;
    }

    public void setOnItemListener(OnRecycleViewListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    public AreaSelRlvAdapter(List<BaseCommonDataVO> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 设置刷新数据
     *
     * @param mData
     */
    public void setNowData(List<BaseCommonDataVO> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    /**
     * 设置选中项
     *
     * @param position
     * @param ischick  是否选中
     */
    public void setNowSelItem(int position, boolean ischick) {
        this.mSelPosition = position;
        this.isCheck = ischick;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (onItemListener != null) {
            onItemListener.onItemListener(v, (BaseCommonDataVO) v.getTag(), v.getId());
        }
    }

    public interface OnRecycleViewListener {
        void onItemListener(View view, BaseCommonDataVO vo, int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_select_area, null);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mChbArea.setText(mData.get(position).getName());
        if (mSelPosition > -1) {
            if (isCheck) {//是否选中 true 为选中
                holder.mIvSelect.setBackgroundResource(R.drawable.btn_check_normal);
            } else {
                if (position == mSelPosition) {
                    holder.mIvSelect.setBackgroundResource(R.drawable.btn_check_pressed_2);
                } else {
                    holder.mIvSelect.setBackgroundResource(R.drawable.btn_check_normal);
                }
            }
        } else {
            holder.mIvSelect.setBackgroundResource(R.drawable.btn_check_normal);
        }
        holder.itemView.setId(position);
        holder.itemView.setTag(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mChbArea;
        private ImageView mIvSelect;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mChbArea = (TextView) itemView.findViewById(R.id.chb_area);
            this.mIvSelect = (ImageView) itemView.findViewById(R.id.iv_select);
        }
    }
}
