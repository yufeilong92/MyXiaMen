package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.util.TextViewUtil;
import com.lawyee.apppublic.vo.JapubHandleVO;

import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawerApp
 * @Package com.lawyee.apppublic.adapter
 * @Description: $todo$
 * @author: YFL
 * @date: 2017/7/11 17:14
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class LawOrganizerAdapter extends RecyclerView.Adapter<LawOrganizerAdapter.ViewHolder> implements View.OnClickListener {


    private List<?> mData;
    private Context mContext;
    private final LayoutInflater inflater;


    public LawOrganizerAdapter(List<?> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }
    private OnItemTitleCilickListener itemTitleCilickListener=null;

    public interface OnItemTitleCilickListener {
        void OnTitleCilickListener(JapubHandleVO vo, int position);
    }

    public OnItemTitleCilickListener getItemTitleCilickListener() {
        return itemTitleCilickListener;
    }

    public void setItemTitleCilickListener(OnItemTitleCilickListener itemTitleCilickListener) {
        this.itemTitleCilickListener = itemTitleCilickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_organizer, null);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final JapubHandleVO vo = (JapubHandleVO)mData.get(position);
        TextViewUtil.isEmpty(holder.mTvLawvoteOranizerTitle,vo.getName());
        TextViewUtil.isEmpty(holder.mTvOrganizerInfom,vo.getIntroduce());
        holder.itemView.setTag(vo);
        holder.itemView.setId(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        if (itemTitleCilickListener!=null){
            itemTitleCilickListener.OnTitleCilickListener((JapubHandleVO) v.getTag(),v.getId());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvLawvoteOranizerTitle;
        public TextView mTvOrganizerInfom;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvLawvoteOranizerTitle = (TextView) itemView.findViewById(R.id.tv_lawvote_OranizerTitle);
            mTvOrganizerInfom = (TextView) itemView.findViewById(R.id.tv_OrganizerInfom);

        }
    }


}
