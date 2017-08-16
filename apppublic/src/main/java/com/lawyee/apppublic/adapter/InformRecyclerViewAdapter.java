package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.util.UrlUtil;
import com.lawyee.apppublic.vo.InfomationVO;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.lawyee.mobilelib.utils.TimeUtil;

import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawerApp
 * @Package com.lawyee.apppublic.adapter
 * @Description: 资讯列表适配器
 * @author: YFL
 * @date: 2017/5/16 8:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class InformRecyclerViewAdapter extends BaseRecyclerAdapter<InformRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {
    private List<InfomationVO> mData;
    private Context mContext;
    private LayoutInflater mInflater;

    public void setOnRecyclerViewClickListener(OnRecyclerViewClickListener onRecyclerViewClickListener) {
        this.onRecyclerViewClickListener = onRecyclerViewClickListener;
    }

    private OnRecyclerViewClickListener onRecyclerViewClickListener = null;

    public  interface OnRecyclerViewClickListener {
        void onItemClickListener(View view,int position);
    }

    /**
     * 用于替换数据
     *
     * @param mData
     * @param mContext
     */
    public InformRecyclerViewAdapter(List<InfomationVO> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
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
        InfomationVO vo = mData.get(position);
        String ymdt = TimeUtil.getYMDT(mData.get(position).getPublishDate());
        holder.mItemTvTime.setText(ymdt);
        holder.mItemTvTitle.setText(mData.get(position).getTitle());
        holder.itemView.setTag(mData.get(position).getTitle());
        String imageUrl = vo.getPhoto();
        if (!TextUtils.isEmpty(imageUrl)){
            holder.mIvInfomUrl.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(UrlUtil.getImageFileUrl(mContext,imageUrl),holder.mIvInfomUrl);
        }else {
            holder.mIvInfomUrl.setVisibility(View.GONE);
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getAdapterItemCount() {
        return mData==null||mData.isEmpty()?0:mData.size();
    }

    @Override
    public void onClick(View v) {
        if (onRecyclerViewClickListener != null) {

            onRecyclerViewClickListener.onItemClickListener(v, (Integer) v.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mItemTvTitle;

        private TextView mItemTvTime;
        private ImageView mIvInfomUrl;
        public ViewHolder(View itemView) {
            super(itemView);
            mIvInfomUrl = (ImageView) itemView.findViewById(R.id.iv_infom_url);
            mItemTvTime = (TextView) itemView.findViewById(R.id.item_tv_time);
            mItemTvTitle = (TextView) itemView.findViewById(R.id.item_tv_title);
        }
    }

}
