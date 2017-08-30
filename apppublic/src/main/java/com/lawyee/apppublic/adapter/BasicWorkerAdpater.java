package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.ui.basiclaw.ServiceWorkerDetailActivity;
import com.lawyee.apppublic.util.UrlUtil;
import com.lawyee.apppublic.vo.JaglsStaffVO;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import static com.lawyee.apppublic.ui.basiclaw.ServiceWorkerDetailActivity.JAGLSSTAFFIOD;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.adapter
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @author: czq
 * @date: 2017/7/18 09:03
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

    public class BasicWorkerAdpater extends BaseRecyclerAdapter<BasicWorkerAdpater.ViewHolder> {
    private Context mContext;
    private List<JaglsStaffVO> mDatas;

    public BasicWorkerAdpater(Context mContext, List<JaglsStaffVO> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    public void insert(JaglsStaffVO titel, int poistion) {
        insert(mDatas, titel, poistion);
    }

    /**
     * 获取新数据，刷新,
     *
     * @param mData
     */
    public BasicWorkerAdpater(List<JaglsStaffVO> mData) {
        this.mDatas = mData;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = View.inflate(mContext, R.layout.item_basic_worker_list, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, boolean isItem) {
        final JaglsStaffVO jaglsStaffVO=mDatas.get(position);
        holder.mTv_name.setText(jaglsStaffVO.getName());
        holder.mTv_office.setText(jaglsStaffVO.getJaglsOrganizationName());
        holder.mTv_address.setText(jaglsStaffVO.getOrgTelephone());
        String imageUrl = jaglsStaffVO.getPhoto();
        if (!TextUtils.isEmpty(imageUrl)){
            ImageLoader.getInstance().displayImage(UrlUtil.getImageFileUrl(mContext,imageUrl),holder.mIv_place, ApplicationSet.CDIO_LAW);
        }else {
            holder.mIv_place.setImageResource(R.drawable.ic_default_avatar);
        }
        holder.mLl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, ServiceWorkerDetailActivity.class);
                intent.putExtra(JAGLSSTAFFIOD,jaglsStaffVO.getOid());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getAdapterItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIv_place;
        public TextView mTv_name;
        public TextView mTv_office;
        public TextView mTv_address;
        public LinearLayout mLl_item;

        public ViewHolder(View itemView) {
            super(itemView);
            mLl_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
            mIv_place = (ImageView) itemView
                    .findViewById(R.id.iv_place);
            mTv_name = (TextView) itemView
                    .findViewById(R.id.tv_name);
            mTv_office = (TextView) itemView
                    .findViewById(R.id.tv_office);
            mTv_address = (TextView) itemView
                    .findViewById(R.id.tv_address);

        }
    }
}