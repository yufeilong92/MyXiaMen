package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.util.UrlUtil;
import com.lawyee.apppublic.vo.ActivityVO;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.lawyee.mobilelib.utils.StringUtil;

import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawerApp
 * @Package com.lawyee.apppublic.adapter
 * @Description: 人民机构列表
 * @author: YFL
 * @date: 2017/5/22 14:59
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class LegalActivitiesRlvAdapter extends BaseRecyclerAdapter<LegalActivitiesRlvAdapter.ViewHolder> implements View.OnClickListener {


    private List<ActivityVO> mData;
    private Context mContext;
    private LayoutInflater mInflater;

    public LegalActivitiesRlvAdapter(List<ActivityVO> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void insent(ActivityVO orgVO, int postion) {
        insert(mData, orgVO, postion);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    private OnRecyclerViewItemClickListener itemClickListener = null;

    public OnRecyclerViewItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(OnRecyclerViewItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener != null) {
            itemClickListener.ItemClickListenet(v, (ActivityVO) v.getTag(), v.getId());
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void ItemClickListenet(View view, ActivityVO vo, int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.item_inform, null);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, boolean isItem) {
        ActivityVO vo = mData.get(position);
        holder.mItemTvTitle.setText(vo.getTitle());
        holder.mItemTvTime.setText(vo.getPublishDate());
        String photourl = vo.getPhoto();
        if (!StringUtil.isEmpty(photourl)){
            ImageLoader.getInstance().displayImage(UrlUtil.getImageFileUrl(mContext,photourl),holder.mIvInfomUrl);
        }
        holder.itemView.setTag(mData.get(position));
        holder.itemView.setId(position);
    }


    @Override
    public int getAdapterItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mItemTvTitle;
        public TextView mItemTvTime;
        public LinearLayout mTitle;
        public ImageView mIvInfomUrl;
        public ViewHolder(View itemView) {
            super(itemView);
            this.mItemTvTitle = (TextView) itemView.findViewById(R.id.item_tv_title);
            this.mItemTvTime = (TextView) itemView.findViewById(R.id.item_tv_time);
            this.mTitle = (LinearLayout) itemView.findViewById(R.id.title);
            this.mIvInfomUrl = (ImageView) itemView.findViewById(R.id.iv_infom_url);
        }
    }


}
