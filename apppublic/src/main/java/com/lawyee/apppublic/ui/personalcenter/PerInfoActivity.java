package com.lawyee.apppublic.ui.personalcenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.util.BaseCommonToStringUtil;
import com.lawyee.apppublic.util.TextViewUtil;
import com.lawyee.apppublic.util.UrlUtil;
import com.lawyee.apppublic.vo.BaseCommonDataVO;
import com.lawyee.apppublic.vo.UserVO;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.lawyee.mobilelib.utils.StringUtil;
/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  标题
 * @Package com.lawyee.apppublic.ui.personalcenter
 * @Description:    公众端-个人信息
 * @author:lzh
 * @date:   2017/8/9
 * @version
 * @verdescript   2017/8/9  lzh 初建
 * @Copyright: 2017/8/9 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class PerInfoActivity extends BaseActivity {


    private TextView mTvModify;
    private ImageView mIvHead;
    private RelativeLayout mRlHead;
    private TextView mTvNickname;
    private TextView mTvName;
    private TextView mTvSex;
    private TextView mTvCerNum;
    private TextView mTvPhone;
    private TextView mTvArea;
    private TextView mTvSpecificAddress;
    private Context mContext;
    private UserVO mUserVO;
    private TextView mTvApplyJaaidData;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_per_info);
        mContext = this;
        initView();
    }

    @Override
    protected void onResume() {
        mUserVO = ApplicationSet.getInstance().getUserVO();
        if (mUserVO == null || StringUtil.isEmpty(mUserVO.getLoginId())) {
            finish();
        }
        setData();
        super.onResume();

    }

    public void onToolbarClick(View view) {
        Intent intent = new Intent(mContext, ModifyPerInfoActivity.class);
        intent.putExtra(ModifyPerInfoActivity.CSTR_EXTRA_TITLE_STR, "用户信息修改");
        startActivity(intent);
    }

    private void setData() {
        TextViewUtil.isEmpty(mTvNickname, mUserVO.getNickName());
        TextViewUtil.isEmpty(mTvName, mUserVO.getRealName());
        TextViewUtil.isEmpty(mTvApplyJaaidData, mUserVO.getBirthday());
        TextViewUtil.isEmpty(mTvSex, BaseCommonToStringUtil.toString(mUserVO.getGender()));
        TextViewUtil.isEmpty(mTvCerNum, mUserVO.getIdCard());
        TextViewUtil.isEmpty(mTvPhone, mUserVO.getMobile());
        BaseCommonDataVO baseCommonDataVO2 = BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getAreas(), mUserVO.getProvince());
        String province = "";
        if (baseCommonDataVO2 != null) {
            province = baseCommonDataVO2.getName();
        }
        BaseCommonDataVO baseCommonDataVO = BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getAreas(), mUserVO.getCity());
        String city = "";
        if (baseCommonDataVO != null) {
            city = baseCommonDataVO.getName();
        }
        BaseCommonDataVO baseCommonDataVO1 = BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getAreas(), mUserVO.getCounty());
        String country = "";
        if (baseCommonDataVO1 != null) {
            country = baseCommonDataVO1.getName();
        }
        TextViewUtil.isEmpty(mTvArea, province + city + country);
        TextViewUtil.isEmpty(mTvSpecificAddress, mUserVO.getAddress());
        String imageUrl = mUserVO.getPhoto();
        if (!TextUtils.isEmpty(imageUrl)) {
            ImageLoader.getInstance().displayImage(UrlUtil.getImageFileUrl(mContext, imageUrl), mIvHead, ApplicationSet.CDIO_LAW);
        } else {
            mIvHead.setImageResource(R.drawable.ic_default_avatar);
        }
    }

    private void initView() {
        mIvHead = (ImageView) findViewById(R.id.iv_head);
        mRlHead = (RelativeLayout) findViewById(R.id.rl_head);
        mTvNickname = (TextView) findViewById(R.id.tv_nickname);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvApplyJaaidData= (TextView) findViewById(R.id.tv_apply_jaaid_data);
        mTvSex = (TextView) findViewById(R.id.tv_sex);
        mTvCerNum = (TextView) findViewById(R.id.tv_cer_num);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mTvArea = (TextView) findViewById(R.id.tv_area);
        mTvSpecificAddress = (TextView) findViewById(R.id.tv_specific_address);
    }


}
