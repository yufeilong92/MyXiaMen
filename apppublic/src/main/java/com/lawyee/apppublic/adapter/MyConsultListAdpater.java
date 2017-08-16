package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.ui.personalcenter.lawyer.ConsuluDetailActivity;
import com.lawyee.apppublic.util.TextViewUtil;
import com.lawyee.apppublic.util.XMPPHelper;
import com.lawyee.apppublic.vo.JacstConsulationVO;

import net.lawyee.mobilelib.utils.TimeUtil;

import java.util.ArrayList;

import static com.lawyee.apppublic.ui.personalcenter.lawyer.ConsuluDetailActivity.CONSULUDETAIL;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.adapter
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @author: lzh
 * @date: 2017/8/3 14:22
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class MyConsultListAdpater extends BaseRecyclerAdapter<MyConsultListAdpater.ViewHolder> {
    private Context mContext;
    private ArrayList<JacstConsulationVO> mDatas;


    public MyConsultListAdpater(ArrayList<JacstConsulationVO> list, Context context) {
        this.mDatas = list;
        this.mContext = context;
    }

    public void setmDatas(ArrayList<JacstConsulationVO> list) {
        this.mDatas = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = View.inflate(mContext, R.layout.item_my_consult, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position, boolean isItem) {
        final JacstConsulationVO jacstConsulationVO = mDatas.get(position);
        if(ApplicationSet.getInstance().getUserVO().isPublicUser()){
            TextViewUtil.isEmpty(holder.mTvName, jacstConsulationVO.getAnswerPerson());
        }else{
            TextViewUtil.isEmpty(holder.mTvName, jacstConsulationVO.getConsultPerson());
        }
        TextViewUtil.isEmpty(holder.mTvConsultType, jacstConsulationVO.getConsultTypeName());

        TextViewUtil.isEmpty(holder.mTvTime, TimeUtil.getYMDT(jacstConsulationVO.getConsultDate()));
        TextViewUtil.isEmpty(holder.mTvContent, XMPPHelper.generShowMessage(mContext, jacstConsulationVO.getConsultCotent()) + "");
        holder.mLlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, ConsuluDetailActivity.class);
                intent.putExtra(CONSULUDETAIL,jacstConsulationVO.getOid());
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getAdapterItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvConsultType;
        private TextView mTvName;
        private TextView mTvTime;
        private TextView mTvContent;
        private LinearLayout mLlItem;

        public ViewHolder(View itemView) {
            super(itemView);
            mLlItem = (LinearLayout) itemView.findViewById(R.id.ll_item);
            mTvConsultType = (TextView) itemView
                    .findViewById(R.id.tv_consultType);
            mTvName = (TextView) itemView
                    .findViewById(R.id.tv_name);
            mTvTime = (TextView) itemView
                    .findViewById(R.id.tv_time);
            mTvContent = (TextView) itemView
                    .findViewById(R.id.tv_content);

        }
    }
}
