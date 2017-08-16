package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.config.DataManage;
import com.lawyee.apppublic.util.UrlUtil;
import com.lawyee.apppublic.vo.BaseCommonDataVO;
import com.lawyee.apppublic.vo.JaauthStaffVO;
import com.lawyee.apppublic.vo.JamedStaffVO;
import com.lawyee.apppublic.vo.JanotaStaffVO;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawerApp
 * @Package com.lawyee.apppublic.adapter
 * @Description: 解说员适配器
 * @author: YFL
 * @date: 2017/5/24 15:19
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class NarratorAdapter extends BaseRecyclerAdapter<NarratorAdapter.ViewHodler> implements View.OnClickListener {


    private List<?> mData;
    private Context mContext;
    private String mType;
    private final LayoutInflater mInflater;
    /**
     * 调解员
     */
    public static final String JAMEDSTAFF = "jamed";
    /**
     * 公证员
     */
    public static final String JANOTASTAFF = "janota";
    /**
     * 鉴定员
     */
    public static final String JAAUTHSTAFF = "jaauth";


    public NarratorAdapter(List<?> mData, Context mContext, String type) {
        this.mData = mData;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        this.mType = type;
    }


    public OnRecyclerItemViewClickListener getOnRecyclerViewClickListener() {
        return onRecyclerViewClickListener;
    }

    public void setOnRecyclerViewClickListener(OnRecyclerItemViewClickListener onRecyclerViewClickListener) {
        this.onRecyclerViewClickListener = onRecyclerViewClickListener;
    }

    private OnRecyclerItemViewClickListener onRecyclerViewClickListener;

    @Override
    public void onClick(View v) {
        if (onRecyclerViewClickListener != null) {
            onRecyclerViewClickListener.onItemClickListener(v, (JamedStaffVO) v.getTag());
        }
    }

    public interface OnRecyclerItemViewClickListener {
        void onItemClickListener(View view, JamedStaffVO vo);
    }

    @Override
    public ViewHodler getViewHolder(View view) {
        return new ViewHodler(view);
    }

    @Override
    public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = mInflater.inflate(R.layout.item_jamednarrtor, null);
        ViewHodler hodler = new ViewHodler(view);
        view.setOnClickListener(this);
        return hodler;
    }

    @Override
    public void onBindViewHolder(ViewHodler holder, int position, boolean isItem) {
        ImageLoader imagerLoader = ImageLoader.getInstance();

        if (mType.equals(JAAUTHSTAFF)) {//鉴定员
            JaauthStaffVO staffVO = (JaauthStaffVO) mData.get(position);
            holder.mTvJamedName.setText(staffVO.getName());
            holder.mTvJamedPhone.setText(gettelePhone(staffVO.getMobile(),staffVO.getTelephone()));
            holder.mTvJamedSex.setText(DataManage.getInstance().getSex(staffVO.getGender()));
            holder.mTvServiceBusiness.setText(getServiceArea(staffVO.getCertificateType()));
            holder.mTvCertificateNumber.setText(staffVO.getLicenseNumber());
            holder.itemView.setTag(staffVO);
            ImageLoader.getInstance().displayImage(UrlUtil.getImageFileUrl(mContext,staffVO.getPhoto()), holder.mIvHead);
        } else if (mType.equals(JAMEDSTAFF)) {//调解员
            JamedStaffVO staffVO = (JamedStaffVO) mData.get(position);
            holder.mLiService.setVisibility(View.GONE);
            holder.mLiNumber.setVisibility(View.GONE);
            holder.mTvJamedName.setText(staffVO.getName());
            holder.mTvJamedPhone.setText(gettelePhone(staffVO.getMobile(),staffVO.getTelephone()));
            holder.mTvJamedSex.setText(DataManage.getInstance().getSex(staffVO.getGender()));
            holder.itemView.setTag(staffVO);
            ImageLoader.getInstance().displayImage(UrlUtil.getImageFileUrl(mContext,staffVO.getPhoto()), holder.mIvHead);

        } else if (mType.equals(JANOTASTAFF)) {//公证员
            JanotaStaffVO staffVO = (JanotaStaffVO) mData.get(position);
            holder.mTvJamedName.setText(staffVO.getName());
            holder.mTvJamedPhone.setText(gettelePhone(staffVO.getMobile(),staffVO.getTelephone()));
            holder.mTvJamedSex.setText(DataManage.getInstance().getSex(staffVO.getGender()));
            holder.mTvServiceBusiness.setText(getServiceArea(staffVO.getServiceScope()));
            holder.mTvCertificateNumber.setText(staffVO.getLicenseNumber());
            holder.itemView.setTag(staffVO);
            ImageLoader.getInstance().displayImage(UrlUtil.getImageFileUrl(mContext,staffVO.getPhoto()), holder.mIvHead);
        }

    }

    @Override
    public int getAdapterItemCount() {
        return mData.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        public ImageView mIvHead;
        public TextView mTvJamedName;
        public TextView mTvJamedSex;
        public TextView mTvJamedPhone;
        public TextView mTvServiceBusiness;
        public LinearLayout mLiService;
        public TextView mTvCertificateNumber;
        public LinearLayout mLiNumber;

        public ViewHodler(View itemView) {
            super(itemView);
            this.mIvHead = (ImageView) itemView.findViewById(R.id.iv_head);
            this.mTvJamedName = (TextView) itemView.findViewById(R.id.tv_jamed_name);
            this.mTvJamedSex = (TextView) itemView.findViewById(R.id.tv_jamed_sex);
            this.mTvJamedPhone = (TextView) itemView.findViewById(R.id.tv_jamed_phone);
            this.mTvServiceBusiness = (TextView) itemView.findViewById(R.id.tv_service_business);
            this.mLiService = (LinearLayout) itemView.findViewById(R.id.li_service);
            this.mTvCertificateNumber = (TextView) itemView.findViewById(R.id.tv_certificate_number);
            this.mLiNumber = (LinearLayout) itemView.findViewById(R.id.li_number);

        }
    }
    /**
     * 获取服务范围
     *
     * @param address
     * @return
     */
    private String getServiceArea(String address) {
        if (TextUtils.isEmpty(address))
            return "";

        List<String> list = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(address, ",");
        while (tokenizer.hasMoreTokens()) {
            String s = tokenizer.nextToken();
            BaseCommonDataVO vo = BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getDataDictionarys(), s);
            list.add(vo.getName());
        }
        if (list == null || list.isEmpty()) {
            BaseCommonDataVO vo = BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getDataDictionarys(), address);
            if (vo == null)
                return "";
            return vo.getName();
        }
        return listToString(list);
    }

    private String listToString(List<String> lists) {

        StringBuilder sb = new StringBuilder();
        int size = lists.size();
        for (int i = 0; i < size; i++) {
            if ((size - 1) == i) {
                sb.append(lists.get(i));
            } else {
                sb.append(lists.get(i) + ",");
            }
        }
        return sb.toString();
    }
    private String gettelePhone(String mobile,String telephone){
         if (!TextUtils.isEmpty(telephone))
             return telephone;
        if (!TextUtils.isEmpty(mobile))
            return mobile;
        return "";
    }
}
