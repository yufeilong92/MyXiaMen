package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lawyee.apppublic.R;

import java.util.ArrayList;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawerApp
 * @Package com.lawyee.apppublic.adapter
 * @Description: 其它材料提交适配器
 * @author: YFL
 * @date: 2017/6/5 16:37
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class ApplyOtherStuffrAdapter extends BaseAdapter {

    private ArrayList<String> mData;
    private Context mContext;
    private final LayoutInflater mInflater;

    public ApplyOtherStuffrAdapter(ArrayList<String> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    private OnClickItemDelete itemDelete = null;

    public OnClickItemDelete getItemDelete() {
        return itemDelete;
    }

    public void setItemDelete(OnClickItemDelete itemDelete) {
        this.itemDelete = itemDelete;
    }

    public interface OnClickItemDelete {
        void onClickdelete(int position);
    }


    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData == null ? null : mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_submit_stuff, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTvSubmitStuff.setText(mData.get(position));
        holder.mIvSubmitStuffDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemDelete.onClickdelete(position);
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView mTvSubmitStuff;
        public ImageView mIvSubmitStuffDelete;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mTvSubmitStuff = (TextView) rootView.findViewById(R.id.tv_submit_stuff);
            this.mIvSubmitStuffDelete = (ImageView) rootView.findViewById(R.id.iv_submit_stuff_delete);
        }

    }
}
