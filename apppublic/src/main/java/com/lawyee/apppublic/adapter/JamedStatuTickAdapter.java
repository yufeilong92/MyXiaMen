package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.util.BaseCommonToStringUtil;
import com.lawyee.apppublic.util.TextViewUtil;
import com.lawyee.apppublic.vo.JamedStatusDetailVO;

import net.lawyee.mobilelib.utils.TimeUtil;

import java.util.ArrayList;


/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.adapter
 * @Description: ${todo}(个人中心人民调解反馈信息Adpater)
 * @author: lzh
 * @date: 2017/6/5 14:46
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class JamedStatuTickAdapter extends RecyclerView.Adapter<JamedStatuTickAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<JamedStatusDetailVO> mJamedStatusDetailVOs=new ArrayList<>();



    public JamedStatuTickAdapter(ArrayList<JamedStatusDetailVO> mJamedStatusDetailVOs, Context context) {
        this.mContext = context;
        this.mJamedStatusDetailVOs = mJamedStatusDetailVOs;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_jamed_stutas_tick, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTvNull.setVisibility(View.GONE);
        holder.mLlAssign.setVisibility(View.VISIBLE);
        if(mJamedStatusDetailVOs==null||mJamedStatusDetailVOs.size()==0){
            holder.mTvNull.setVisibility(View.VISIBLE);
            holder.mLlAssign.setVisibility(View.GONE);
        }else{
            JamedStatusDetailVO  jamedStatusDetailVO=mJamedStatusDetailVOs.get(position);
            switch (jamedStatusDetailVO.getType()){
                case 0:
                    jamedOrgAccept(holder,jamedStatusDetailVO);
                    break;
                case 1:
                    jamedIsOver(holder,jamedStatusDetailVO);
                    break;
                case 2:
                    jamedScreen(holder,jamedStatusDetailVO);
                    break;
                case 3:
                    jamedMediaPass(holder,jamedStatusDetailVO);
                    break;
                case 4:
                    jamedMediaRecording(holder,jamedStatusDetailVO);
                    break;
                case 5:
                    jamedMediaPlaying(holder,jamedStatusDetailVO);
                    break;

            }
        }
//        holder.mTvNull.setVisibility(View.GONE);
//        holder.mLlAssign.setVisibility(View.VISIBLE);
//        switch (position) {
//            case 0:
//                if(mLength==0){
//                    holder.mTvNull.setVisibility(View.VISIBLE);
//                    holder.mLlAssign.setVisibility(View.GONE);
//                }else if(mLength==1){
//                    jamedOrgAccept(holder);
//                }else if(mLength==2){
//                        if(!StringUtil.isEmpty(mJamedApplyDetailVO.getEndSubmitTime())){
//                            jamedIsOver(holder);
//                        }else {
//                            jamedScreen(holder);
//                        }
//                }else if(mLength==3){
//                    if(!StringUtil.isEmpty(mJamedApplyDetailVO.getEndSubmitTime())){
//                        jamedIsOver(holder);
//                    }else {
//                        jamedMediaPass(holder);
//                    }
//                }else if(mLength==4){
//                    if(!StringUtil.isEmpty(mJamedApplyDetailVO.getEndSubmitTime())){
//                        jamedIsOver(holder);
//                    }else {
//                        jamedMediaRecording(holder);
//                    }
//                }else if(mLength==5){
//                    jamedMediaPlaying(holder);
//                }else {
//                    jamedIsOver(holder);
//                }
//                break;
//            case 1:
//                if(mLength==2){
//                    jamedOrgAccept(holder);
//                }else if(mLength==3){
//                    jamedScreen(holder);
//                }else if(mLength==4){
//                    jamedMediaPass(holder);
//                }else if(mLength==5){
//                    jamedMediaRecording(holder);
//                }else{
//                    jamedMediaPlaying(holder);
//                }
//                break;
//            case 2:
//                if(mLength==3){
//                    jamedOrgAccept(holder);
//                }else if(mLength==4){
//                    jamedScreen(holder);
//                }else if(mLength==5){
//                    jamedMediaPass(holder);
//                }else {
//                    jamedMediaRecording(holder);
//                }
//                break;
//            case 3:
//                if(mLength==4){
//                    jamedOrgAccept(holder);
//                }else if(mLength==5){
//                    jamedScreen(holder);
//                }else {
//                    jamedMediaPass(holder);
//                }
//                break;
//            case 4:
//                if(mLength==5){
//                    jamedOrgAccept(holder);
//                }else {
//                    jamedScreen(holder);
//                }
//                break;
//            case 5:
//                jamedOrgAccept(holder);
//                break;
//        }


    }

    @Override
    public int getItemCount() {

        return mJamedStatusDetailVOs.size()==0?1:mJamedStatusDetailVOs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvNull;
        private TextView mTvTime;
        private TextView mTv1;
        private TextView mTv2;
        private TextView mTv22;
        private TextView mTv3;
        private TextView mTv33;
        private TextView mTv4;
        private TextView mTv44;
        private LinearLayout mLlAssign;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvNull = (TextView) itemView.findViewById(R.id.tv_null);
            mTvTime = (TextView) itemView.findViewById(R.id.tv_time);
            mTv1 = (TextView) itemView.findViewById(R.id.tv_1);
            mTv2 = (TextView) itemView.findViewById(R.id.tv_2);
            mTv22 = (TextView) itemView.findViewById(R.id.tv_22);
            mLlAssign = (LinearLayout) itemView.findViewById(R.id.ll_assign);
            mTv3 = (TextView) itemView.findViewById(R.id.tv_3);
            mTv33 = (TextView) itemView.findViewById(R.id.tv_33);
            mTv4 = (TextView) itemView.findViewById(R.id.tv_4);
            mTv44 = (TextView) itemView.findViewById(R.id.tv_44);

        }
    }
    //调解结束
    private void jamedIsOver(ViewHolder holder,JamedStatusDetailVO jamedStatusDetailVO){
        holder.mTv4.setVisibility(View.GONE);
        holder.mTv44.setVisibility(View.GONE);

        holder.mTvTime.setText(jamedStatusDetailVO.getTime());
        holder.mTv1.setText(R.string.jamed_for_you_jamed);
        holder.mTv2.setText(R.string.jamed_result);
        holder.mTv3.setText(R.string.endTime);
        if(jamedStatusDetailVO.isResult()){
            TextViewUtil.isEmpty(holder.mTv22,mContext.getString(R.string.jamed_success2));
        }else {
            TextViewUtil.isEmpty(holder.mTv22,mContext.getString(R.string.jamed_fail));
        }
        TextViewUtil.isEmpty(holder.mTv33, TimeUtil.getYMDT(jamedStatusDetailVO.getOtherTime()));

    }
    //调解审核
    private void jamedOrgAccept(ViewHolder holder,JamedStatusDetailVO jamedStatusDetailVO){
        holder.mTvTime.setText(jamedStatusDetailVO.getTime());
        holder.mTv1.setText(R.string.jamed_for_you_audit);
        holder.mTv2.setText(R.string.aid_audit_result);
        holder.mTv3.setText(R.string.reason2);
        holder.mTv4.setText(R.string.other_explain);
        if(jamedStatusDetailVO.isResult()){
            TextViewUtil.isEmpty(holder.mTv22,mContext.getString(R.string.accept));
            holder.mTv3.setVisibility(View.GONE);
            holder.mTv33.setVisibility(View.GONE);
        }else {
            TextViewUtil.isEmpty(holder.mTv22,mContext.getString(R.string.no_accept));
            holder.mTv3.setText(R.string.reason2);
            TextViewUtil.isEmpty(holder.mTv33,BaseCommonToStringUtil.toString(jamedStatusDetailVO.getReason()));
        }
        TextViewUtil.isEmpty(holder.mTv44,jamedStatusDetailVO.getOtherReason());
    }
    //筛选原因
    private void jamedScreen(ViewHolder holder,JamedStatusDetailVO jamedStatusDetailVO){
        holder.mTvTime.setText(jamedStatusDetailVO.getTime());
        holder.mTv1.setText(R.string.jamed_for_yourmedia_screen);
        holder.mTv2.setText(R.string.select_result1);
        holder.mTv4.setText(R.string.other_explain);
        if(jamedStatusDetailVO.isResult()){
            TextViewUtil.isEmpty(holder.mTv22,mContext.getString(R.string.select_pass));
            holder.mTv3.setVisibility(View.GONE);
            holder.mTv33.setVisibility(View.GONE);
        }else {
            TextViewUtil.isEmpty(holder.mTv22,mContext.getString(R.string.select_no_pass));
            holder.mTv3.setText(R.string.reason2);
            TextViewUtil.isEmpty(holder.mTv33,BaseCommonToStringUtil.toString(jamedStatusDetailVO.getReason()));
        }
        TextViewUtil.isEmpty(holder.mTv44,jamedStatusDetailVO.getOtherReason());
    }
    //媒体通不通过
    private void jamedMediaPass(ViewHolder holder,JamedStatusDetailVO jamedStatusDetailVO){
        holder.mTvTime.setText(jamedStatusDetailVO.getTime());
        holder.mTv1.setText(R.string.jamed_for_yourmedia_join);
        holder.mTv2.setText(R.string.isMediaJoin);
        holder.mTv4.setText(R.string.other_explain);
        if(jamedStatusDetailVO.isResult()){
            TextViewUtil.isEmpty(holder.mTv22,mContext.getString(R.string.join));
            holder.mTv3.setVisibility(View.GONE);
            holder.mTv33.setVisibility(View.GONE);
        }else {
            TextViewUtil.isEmpty(holder.mTv22,mContext.getString(R.string.no_join));
            holder.mTv3.setText(R.string.reason2);
            TextViewUtil.isEmpty(holder.mTv33,BaseCommonToStringUtil.toString(jamedStatusDetailVO.getReason()));
        }
        TextViewUtil.isEmpty(holder.mTv44,jamedStatusDetailVO.getOtherReason());
    }
    //媒体录制信息
    private void jamedMediaRecording(ViewHolder holder,JamedStatusDetailVO jamedStatusDetailVO){
        holder.mTvTime.setVisibility(View.GONE);
        holder.mTv1.setText(R.string.jamed_for_yourmedia_record);
        holder.mTv2.setVisibility(View.GONE);
        holder.mTv22.setVisibility(View.GONE);
        holder.mTv3.setText(R.string.record_time);
        holder.mTv4.setText(R.string.record_place);
        TextViewUtil.isEmpty(holder.mTv33,jamedStatusDetailVO.getOtherTime());
        TextViewUtil.isEmpty(holder.mTv44,jamedStatusDetailVO.getPlace());
    }
    //媒体播放信息
    private void jamedMediaPlaying(ViewHolder holder,JamedStatusDetailVO jamedStatusDetailVO){
        holder.mTvTime.setVisibility(View.GONE);
        holder.mTv1.setText(R.string.jamed_for_yourmedia_play);
        holder.mTv2.setText(R.string.play_time);
        holder.mTv3.setText(R.string.play_channel);
        holder.mTv4.setText(R.string.show_title);
        TextViewUtil.isEmpty(holder.mTv22, jamedStatusDetailVO.getOtherTime());
        TextViewUtil.isEmpty(holder.mTv33,jamedStatusDetailVO.getChannel());
        TextViewUtil.isEmpty(holder.mTv44,jamedStatusDetailVO.getTitle());
    }
}
