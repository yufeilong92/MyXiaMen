package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.util.UrlUtil;
import com.lawyee.apppublic.vo.AttachmentVO;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawerApp
 * @Package com.lawyee.apppublic.adapter
 * @Description: $todo$
 * @author: YFL
 * @date: 2017/7/11 16:04
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class LawLookActivityPictureAdapter extends RecyclerView.Adapter<LawLookActivityPictureAdapter.ViewHolder> implements View.OnClickListener {


    private List<?> mData;
    private Context mContext;
    private final LayoutInflater inflater;

    public interface OnRecyclerItemOnClickListener {
        void OnItemClickListener(AttachmentVO vo,int position);
    }

    private OnRecyclerItemOnClickListener itemOnClickListener = null;

    public OnRecyclerItemOnClickListener getItemOnClickListener() {
        return itemOnClickListener;
    }

    public void setItemOnClickListener(OnRecyclerItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    public LawLookActivityPictureAdapter(List<?> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public LawLookActivityPictureAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_lawlookpicture, null);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LawLookActivityPictureAdapter.ViewHolder holder, int position) {
        AttachmentVO attachmentVO= (AttachmentVO) mData.get(position);
        String imageUrl = attachmentVO.getOid();
        if (!TextUtils.isEmpty(imageUrl)){
            ImageLoader.getInstance().displayImage(UrlUtil.getImageFileUrl(mContext,imageUrl),holder.ivPicture);
        }else {
            holder.ivPicture.setImageResource(R.mipmap.icon_image_def_min);
        }
        holder.itemView.setTag(attachmentVO);
        holder.itemView.setId(position);

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        if (itemOnClickListener != null) {
            itemOnClickListener.OnItemClickListener((AttachmentVO) v.getTag(),v.getId());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivPicture;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPicture = (ImageView) itemView.findViewById(R.id.iv_picture);
        }
    }
}
