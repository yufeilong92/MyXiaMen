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
import com.lawyee.apppublic.ui.lawyerService.LawyerDetailActivity;
import com.lawyee.apppublic.util.BaseCommonToStringUtil;
import com.lawyee.apppublic.util.TextViewUtil;
import com.lawyee.apppublic.util.UrlUtil;
import com.lawyee.apppublic.vo.BaseCommonDataVO;
import com.lawyee.apppublic.vo.JalawLawyerVO;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.adapter
 * @Description: ${todo}(律师列表的Adpater)
 * @author: lzh
 * @date: 2017/5/22 15:35
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class LawyerListAdpater extends BaseRecyclerAdapter<LawyerListAdpater.ViewHolder> {
    private Context mContext;
    private List<JalawLawyerVO> mDatas;
    private int mFromTo;

    private boolean mInProgess;


    public LawyerListAdpater(Context mContext, List<JalawLawyerVO> mDatas,int fromTo) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mFromTo=fromTo;
    }


    public void insert(JalawLawyerVO titel, int poistion) {
        insert(mDatas, titel, poistion);
    }

    /**
     * 获取新数据，刷新,
     *
     * @param mData
     */
    public LawyerListAdpater(List<JalawLawyerVO> mData) {
        this.mDatas =  mData;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view=View.inflate(mContext, R.layout.item_laywer_list,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position, boolean isItem) {
        JalawLawyerVO jalawLawyerVO=mDatas.get(position);
        holder.mTv_name.setText(jalawLawyerVO.getName());
        holder.mTv_office.setText(jalawLawyerVO.getLawfirmName());
        BaseCommonDataVO baseCommonDataVO= BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getAreas(),jalawLawyerVO.getCity());
        String city= "";
        if(baseCommonDataVO!=null){
            city=baseCommonDataVO.getName();
        }
        BaseCommonDataVO baseCommonDataVO1= BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getAreas(),jalawLawyerVO.getCounty());
        String country="";
        if(baseCommonDataVO1!=null){
            country=baseCommonDataVO1.getName();
        }
        holder.mTv_place.setText(city+" "+country);
        holder.mTv_speciality_tip1.setVisibility(View.GONE);
        holder.mTv_speciality_tip2.setVisibility(View.GONE);
        holder.mTv_speciality_tip3.setVisibility(View.GONE);
        if(jalawLawyerVO.getBusiness()!=null&&jalawLawyerVO.getBusiness().size()>0){
            for(int i=0;i<jalawLawyerVO.getBusiness().size();i++){
                switch (i){
                    case 0:
                        String str1=BaseCommonToStringUtil.toString((jalawLawyerVO.getBusiness().get(0).getBusiness()));
                        if(str1==null||str1.equals("")){
                            holder.mTv_speciality_tip1.setVisibility(View.GONE);
                        }else {
                            holder.mTv_speciality_tip1.setVisibility(View.VISIBLE);
                            TextViewUtil.isEmpty(holder.mTv_speciality_tip1, str1);
                        }
                        break;
                    case 1:
                        String str2=BaseCommonToStringUtil.toString((jalawLawyerVO.getBusiness().get(1).getBusiness()));
                        if(str2==null||str2.equals("")){
                            holder.mTv_speciality_tip2.setVisibility(View.GONE);
                        }else {
                            holder.mTv_speciality_tip2.setVisibility(View.VISIBLE);
                            TextViewUtil.isEmpty(holder.mTv_speciality_tip2, str2);
                        }
                        break;
                    case 2:
                        String str3=BaseCommonToStringUtil.toString((jalawLawyerVO.getBusiness().get(2).getBusiness()));
                        if(str3==null||str3.equals("")){
                            holder.mTv_speciality_tip3.setVisibility(View.GONE);
                        }else {
                            holder.mTv_speciality_tip3.setVisibility(View.VISIBLE);
                            TextViewUtil.isEmpty(holder.mTv_speciality_tip3, str3);
                        }
                        break;
                }
            }
        }
        String imageUrl = jalawLawyerVO.getPhoto();
        if (!TextUtils.isEmpty(imageUrl)){
            ImageLoader.getInstance().displayImage(UrlUtil.getImageFileUrl(mContext,imageUrl),holder.mIv_avatar,ApplicationSet.CDIO_LAW);
        }else {
            holder.mIv_avatar.setImageResource(R.drawable.ic_default_avatar);
        }
        holder.mLl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, LawyerDetailActivity.class);
                intent.putExtra(LawyerDetailActivity.JALAWLAWYERVO,mDatas.get(position));
                mContext.startActivity(intent);
            }
        });
    }



    @Override
    public int getAdapterItemCount() {
        return mDatas.size();
    }

    class  ViewHolder extends RecyclerView.ViewHolder{
        public ImageView mIv_avatar;
        public TextView mTv_name;
        public TextView mTv_office;
        public TextView mTv_place;
        public TextView mTv_speciality_tip1;
        public TextView mTv_speciality_tip2;
        public TextView mTv_speciality_tip3;
        public LinearLayout mLl_item;
        public ViewHolder(View itemView) {
            super(itemView);
            mLl_item= (LinearLayout) itemView.findViewById(R.id.ll_item);
            mIv_avatar = (ImageView) itemView
                    .findViewById(R.id.iv_avatar);
            mTv_name = (TextView) itemView
                    .findViewById(R.id.tv_name);
            mTv_office = (TextView) itemView
                    .findViewById(R.id.tv_office);
            mTv_place = (TextView) itemView
                    .findViewById(R.id.tv_place);
            mTv_speciality_tip1 = (TextView) itemView
                    .findViewById(R.id.tv_speciality_tip1);
            mTv_speciality_tip2 = (TextView) itemView
                    .findViewById(R.id.tv_speciality_tip2);
            mTv_speciality_tip3 = (TextView) itemView
                    .findViewById(R.id.tv_speciality_tip3);
        }
    }
}
