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

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.ui.infom.InfomDetailsActivity;
import com.lawyee.apppublic.util.UrlUtil;
import com.lawyee.apppublic.vo.BaseCommonDataVO;
import com.lawyee.apppublic.vo.InfomationVO;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import static com.lawyee.apppublic.config.Constants.NEWS_ID;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.adapter
 * @Description: 首页最新资讯部分的Adpater
 * @author: czq
 * @date: 2017/5/15 15:53
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class InfoAdpater extends RecyclerView.Adapter<InfoAdpater.ViewHolder>{
    private Context mContext;
    private ArrayList<InfomationVO> mDatas;

    public InfoAdpater(ArrayList<InfomationVO> list,Context context) {
        this.mDatas=list;
        this.mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(mContext, R.layout.item_home_info,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final InfomationVO vo = mDatas.get(position);
        StringBuffer str= new StringBuffer("");
        str.append(mDatas.get(position).getPublishDate()+"|");

        holder.mListitem_base_title_tv.setText(mDatas.get(position).getTitle());
        ArrayList<String> baselist=new ArrayList<>();
        ArrayList<String> baseOidlist=new ArrayList<>();
       BaseCommonDataVO baseCommonDataVO=BaseCommonDataVO.findDataVOWithOid
               (ApplicationSet.getInstance().getDataDictionarys(),mDatas.get(position).getCategory());
       for(int i=0;i<10;i++){
           if(baseCommonDataVO==null){
               break;
           }
           String name=baseCommonDataVO.getName();
           baselist.add(name);
           baseOidlist.add(baseCommonDataVO.getOid());
           if(baseCommonDataVO.getParentId().equals(NEWS_ID)){
               break;
           }
           baseCommonDataVO=BaseCommonDataVO.findDataVOWithOid
                   (ApplicationSet.getInstance().getDataDictionarys(),baseCommonDataVO.getParentId());


       }
       if(baselist.size()>=2){
           str.append(baselist.get(baselist.size()-2));
       }else{
           str.append(mDatas.get(position).getCategoryName());
       }
        holder.mListitem_base_summary_tv.setText(str);
        String imageUrl = vo.getPhoto();
        if (!TextUtils.isEmpty(imageUrl)){
            ImageLoader.getInstance().displayImage(UrlUtil.getImageFileUrl(mContext,imageUrl),holder.mListitem_base_iv);
        }else {
            String [] por=mContext.getResources().getStringArray(R.array.InfoTypeID);
            if(baseOidlist.size()==0){
                holder.mListitem_base_iv.setImageResource(R.mipmap.icon_image_def_min);
            }else {
                if (baseOidlist.get(baseOidlist.size()-1).equals(por[0])) {
                    holder.mListitem_base_iv.setImageResource(R.drawable.ic_jaaid);
                } else if (baseOidlist.get(baseOidlist.size()-1).equals(por[1])) {
                    holder.mListitem_base_iv.setImageResource(R.drawable.ic_note);
                } else if (baseOidlist.get(baseOidlist.size()-1).equals(por[2])) {
                    holder.mListitem_base_iv.setImageResource(R.drawable.ic_jamed);
                } else if (baseOidlist.get(baseOidlist.size()-1).equals(por[3])) {
                    holder.mListitem_base_iv.setImageResource(R.drawable.ic_mediation);
                } else if (baseOidlist.get(baseOidlist.size()-1).equals(por[4])) {
                    holder.mListitem_base_iv.setImageResource(R.drawable.ic_law_broadcast);
                }else{
                    holder.mListitem_base_iv.setImageResource(R.mipmap.icon_image_def_min);
                }
            }
        }
       holder.mLl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, InfomDetailsActivity.class);
                intent.putExtra(InfomDetailsActivity.CSTR_EXTRA_TITLE_STR, mDatas.get(position).getCategoryName());
                intent.putExtra(InfomDetailsActivity.CSTR_EXTRA_INFORMATIONVO_VO,vo);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class  ViewHolder extends RecyclerView.ViewHolder{
        public ImageView mListitem_base_iv;
        public TextView mListitem_base_title_tv;
        public TextView mListitem_base_summary_tv;
        public LinearLayout mLl_item;
        public ViewHolder(View itemView) {
            super(itemView);
            mLl_item= (LinearLayout) itemView.findViewById(R.id.ll_item);
            mListitem_base_iv = (ImageView) itemView
                    .findViewById(R.id.listitem_base_iv);
            mListitem_base_title_tv = (TextView) itemView
                    .findViewById(R.id.listitem_base_title_tv);
            mListitem_base_summary_tv = (TextView) itemView
                    .findViewById(R.id.listitem_base_summary_tv);


        }
    }
}
