package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.ui.lawyerService.OnlineEntrustActivity;
import com.lawyee.apppublic.util.BaseCommonToStringUtil;
import com.lawyee.apppublic.util.TextViewUtil;
import com.lawyee.apppublic.vo.JalawLawyerDetailVO;
import com.lawyee.apppublic.vo.JalawLawyerServiceVO;

import java.util.List;

import static com.lawyee.apppublic.ui.lawyerService.LawyerDetailActivity.JALAWLAWYERVO;
import static com.lawyee.apppublic.ui.lawyerService.OnlineEntrustActivity.ONLINEENTRUS;
import static com.lawyee.apppublic.util.ToLoginDialogUtil.alertTiptoLogin;
import static com.lawyee.apppublic.util.ToLoginDialogUtil.alertToLogin;
import static com.lawyee.apppublic.util.ToLoginDialogUtil.alertToPer;
import static com.lawyee.apppublic.util.ToLoginDialogUtil.isFull;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.adapter
 * @Description: ${todo}(律师详情的的服务项目Adpater)
 * @author: czq
 * @date: 2017/5/25 16:07
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class ServiceProjectAdpater extends RecyclerView.Adapter<ServiceProjectAdpater.ViewHolder> {
    private Context mContext;
    private List<JalawLawyerServiceVO> services;
    private JalawLawyerDetailVO mJalawLawyerDetailVO;
    private boolean isPractice;


    public ServiceProjectAdpater(List<JalawLawyerServiceVO> services, Context context, JalawLawyerDetailVO jalawLawyerDetailVO,boolean isPractice) {
        this.services = services;
        this.mContext = context;
        this.mJalawLawyerDetailVO=jalawLawyerDetailVO;
        this.isPractice=isPractice;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_service_pro, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        TextViewUtil.isEmpty( holder.mTvTitle, BaseCommonToStringUtil.toString(services.get(position).getService()));
        if(services.get(position).getIntroduction()==null||services.get(position).getIntroduction().equals("")){
            holder.mTvContext.setVisibility(View.GONE);
        }else {
            holder.mTvContext.setText(services.get(position).getIntroduction());
        }
        if(!isPractice){
            holder.mTvEntrust.setEnabled(false);
        }else {
            holder.mTvEntrust.setEnabled(true);
        }
             holder.mTvEntrust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ApplicationSet.getInstance().getUserVO()!=null) {
                    if(ApplicationSet.getInstance().getUserVO().isPublicUser()) {
                        if(isFull(ApplicationSet.getInstance().getUserVO().getIdCard(),ApplicationSet.getInstance().getUserVO().getRealName())) {
                            Intent intent = new Intent(mContext, OnlineEntrustActivity.class);
                            intent.putExtra(JALAWLAWYERVO, mJalawLawyerDetailVO);
                            intent.putExtra(ONLINEENTRUS, services.get(position));
                            mContext.startActivity(intent);
                        }else{
                            alertToPer(mContext);
                        }
                    }else{
                        alertTiptoLogin(mContext);
                    }
                }else{
                    alertToLogin(mContext);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvTitle;
        private TextView mTvContext;
        private TextView mTvEntrust;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvContext = (TextView) itemView.findViewById(R.id.tv_context1);
            mTvTitle = (TextView) itemView
                    .findViewById(R.id.tv_title1);
            mTvEntrust = (TextView) itemView
                    .findViewById(R.id.tv_entrust);

        }
    }
}
