package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.ui.ViewImageActivity;
import com.lawyee.apppublic.util.UrlUtil;
import com.lawyee.apppublic.vo.AttachmentVO;

import net.lawyee.mobilelib.utils.FileUtil;

import java.util.List;

import static com.lawyee.apppublic.ui.ViewImageActivity.CSTR_EXTRA_IMAGE_STR;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.adapter
 * @Description: ${todo}(材料清单adpater)
 * @author: czq
 * @date: 2017/6/2 10:29
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class AttachmentAdpater extends RecyclerView.Adapter<AttachmentAdpater.ViewHolder> {
    private Context mContext;
    private List<AttachmentVO> mDatas;


    public AttachmentAdpater( Context context,List<AttachmentVO> list) {
        this.mDatas = list;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_attachment, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        int num=position+1;
        if(mDatas.get(position).getName()!=null&&!mDatas.get(position).getName().equals("")){
            holder.mTvAttach.setText(mContext.getString(R.string.space)+num+mContext.getString(R.string.point)+mDatas.get(position).getName());
        }else if(mDatas.get(position).getDescription_()!=null&&!mDatas.get(position).getDescription_().equals("")){
            holder.mTvAttach.setText(mContext.getString(R.string.space)+num+mContext.getString(R.string.point)+mDatas.get(position).getDescription_());
        }else {
            holder.mTvAttach.setText(mContext.getString(R.string.space)+num+mContext.getString(R.string.point)+mDatas.get(position).getSubName());
        }

        holder.mTvAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FileUtil.isImageFile(mDatas.get(position).getName())){
                    Intent intent=new Intent(mContext, ViewImageActivity.class);
                    intent.putExtra(CSTR_EXTRA_IMAGE_STR, UrlUtil.getImageFileUrl(mContext,mDatas.get(position).getOid()));
                    mContext.startActivity(intent);
                }else{
                    BaseActivity.runBrowser(UrlUtil.getFileUrl(mContext,mDatas.get(position).getOid()),mContext);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvAttach;
        public ViewHolder(View itemView) {
            super(itemView);
            this.mTvAttach= (TextView) itemView.findViewById(R.id.tv_attach);

        }
    }
}