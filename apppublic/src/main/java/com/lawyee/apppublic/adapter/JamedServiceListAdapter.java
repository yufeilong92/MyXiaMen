package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.DataManage;
import com.lawyee.apppublic.vo.JamedApplyVO;

import net.lawyee.mobilelib.utils.TimeUtil;

import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawerApp
 * @Package com.lawyee.appservice.adpater
 * @Description: $todo$
 * @author: YFL
 * @date: 2017/6/28 12:36
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JamedServiceListAdapter extends BaseRecyclerAdapter<JamedServiceListAdapter.ViewHolder> implements View.OnClickListener {


    private List<?> mData;
    private Context mContext;
    private final LayoutInflater mInflater;

    private OnRecyclerViewItemChickListener onRecyclerViewItemChickListener = null;

    public static final String CONTENT_PARAMETER_JAMED = "jamed";
    public static final String CONTENT_PARAMETER_MEDIA = "media";


    private String mType;
    private String mStatusType;

    public JamedServiceListAdapter(List<?> mData, Context mContext, String type) {
        this.mData = mData;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        mType = type;
    }

    @Override
    public void onClick(View v) {
        if (onRecyclerViewItemChickListener != null) {
            onRecyclerViewItemChickListener.OnItemChickListener(v, (JamedApplyVO) v.getTag());
        }
    }

    public interface OnRecyclerViewItemChickListener {
        void OnItemChickListener(View view, JamedApplyVO vo);
    }

    public OnRecyclerViewItemChickListener getOnRecyclerViewItemChickListener() {
        return onRecyclerViewItemChickListener;
    }

    public void setOnRecyclerViewItemChickListener(OnRecyclerViewItemChickListener onRecyclerViewItemChickListener) {
        this.onRecyclerViewItemChickListener = onRecyclerViewItemChickListener;
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i, boolean b) {
        View view = mInflater.inflate(R.layout.item_jamedservice, null);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i, boolean b) {
        final JamedApplyVO vo = (JamedApplyVO) mData.get(i);
        jamedData(viewHolder, i, vo);
        viewHolder.itemView.setTag(vo);
        viewHolder.itemView.setId(i);
    }

    private void jamedData(ViewHolder holder, int i, JamedApplyVO vo) {
        holder.mTvJamedApplyTitle.setText(vo.getApplyName());
        String ymdt = TimeUtil.getYMDT(vo.getApplyTime());
        holder.mTvJamedApplyTime.setText(ymdt);
        holder.mTvJamedApplyStatus.setText(DataManage.getInstance().getNameWithOid(vo.getSource()));
        int successFlag = vo.getSuccessFlag();
        String status = vo.getStatus();
        switch (status) {
            case "0":
                mStatusType = "未审核";
                break;
            case "1":
                mStatusType = "机构已受理";
                break;
            case "-1":
                mStatusType = "机构不受理";
                break;
            case "2":
                if (successFlag == 1 || successFlag == -1) {
                    mStatusType = "调解结束";
                } else {
                    mStatusType = "媒体已受理";
                }
                break;
            case "-2":
                if (successFlag == 1 || successFlag == -1) {
                    mStatusType = "调解结束";
                } else {
                    mStatusType = "媒体不受理";
                }
                break;
            case "3":
                mStatusType = "调解结束";
                break;
            default:
                break;
        }
        holder.mTvJamedApplyType.setText(mStatusType);

    }


    @Override
    public int getAdapterItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvJamedApplyTitle;
        public TextView mTvJamedApplyStatus;
        public TextView mTvJamedApplyType;
        public TextView mTvJamedApplyTime;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mTvJamedApplyTitle = (TextView) itemView.findViewById(R.id.tv_jamedApply_title);
            this.mTvJamedApplyStatus = (TextView) itemView.findViewById(R.id.tv_jamedApply_Status);
            this.mTvJamedApplyType = (TextView) itemView.findViewById(R.id.tv_jamedApply_type);
            this.mTvJamedApplyTime = (TextView) itemView.findViewById(R.id.tv_jamedApply_time);
        }
    }

}
