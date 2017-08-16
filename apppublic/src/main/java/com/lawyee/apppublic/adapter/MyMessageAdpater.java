package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.ui.lawyerService.SessionActivity;
import com.lawyee.apppublic.util.TextViewUtil;
import com.lawyee.apppublic.util.XMPPHelper;
import com.lawyee.apppublic.vo.ConsulationRecordVO;

import net.lawyee.mobilelib.utils.TimeUtil;

import java.util.ArrayList;

import static com.lawyee.apppublic.ui.BaseActivity.CSTR_EXTRA_TITLE_STR;
import static com.lawyee.apppublic.ui.lawyerService.SessionActivity.CSTR_EXTRA_SESSION_STR;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.adapter
 * @Description: ${todo}(我的消息Adpater)
 * @author: czq
 * @date: 2017/5/27 14:30
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class MyMessageAdpater extends BaseRecyclerAdapter<MyMessageAdpater.ViewHolder> {
    private Context mContext;
    private ArrayList<ConsulationRecordVO> mDatas;



    public MyMessageAdpater(ArrayList<ConsulationRecordVO> list, Context context) {
        this.mDatas = list;
        this.mContext = context;
    }

    public void setmDatas(ArrayList<ConsulationRecordVO> list) {
        this.mDatas = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = View.inflate(mContext, R.layout.item_my_message, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position, boolean isItem) {
        final ConsulationRecordVO consulationRecordVO = mDatas.get(position);
        TextViewUtil.isEmpty(holder.mTvName, consulationRecordVO.getFriendName());
        TextViewUtil.isEmpty(holder.mTvTime, TimeUtil.getChatTime(Long.parseLong(consulationRecordVO.getSendTime())));
        TextViewUtil.isEmpty(holder.mTvContent, XMPPHelper.generShowMessage(mContext, consulationRecordVO.getContent()) + "");
        if(consulationRecordVO.getNoReadnum()==0){
            holder.mTvTipNum.setVisibility(View.INVISIBLE);
        }else{
            holder.mTvTipNum.setVisibility(View.VISIBLE);
            if(consulationRecordVO.getNoReadnum()>99) {
                holder.mTvTipNum.setText(R.string.num_99);
            }else{
                holder.mTvTipNum.setText(consulationRecordVO.getNoReadnum()+"");
            }
        }
        holder.mLlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SessionActivity.class);
                intent.putExtra(CSTR_EXTRA_SESSION_STR, consulationRecordVO.getFriendId());
                intent.putExtra(CSTR_EXTRA_TITLE_STR, consulationRecordVO.getFriendName());
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getAdapterItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mListitemBaseIv;
        private TextView mTvName;
        private TextView mTvTime;
        private TextView mTvContent;
        private LinearLayout mLlItem;
        private TextView mTvTipNum;
        public ViewHolder(View itemView) {
            super(itemView);
            mLlItem = (LinearLayout) itemView.findViewById(R.id.ll_item);
            mListitemBaseIv = (ImageView) itemView
                    .findViewById(R.id.listitem_base_iv);
            mTvName = (TextView) itemView
                    .findViewById(R.id.tv_name);
            mTvTime = (TextView) itemView
                    .findViewById(R.id.tv_time);
            mTvContent = (TextView) itemView
                    .findViewById(R.id.tv_content);
            mTvTipNum= (TextView) itemView.findViewById(R.id.tv_tip_num);

        }
    }
}
