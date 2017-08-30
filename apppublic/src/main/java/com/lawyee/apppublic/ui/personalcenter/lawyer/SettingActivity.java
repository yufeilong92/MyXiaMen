package com.lawyee.apppublic.ui.personalcenter.lawyer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.util.BaseCommonToStringUtil;
import com.lawyee.apppublic.util.TextViewUtil;
import com.lawyee.apppublic.util.UrlUtil;
import com.lawyee.apppublic.vo.UserVO;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static com.lawyee.apppublic.ui.lawyerService.SessionActivity.IMAGE_PICKER;
/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  标题
 * @Package com.lawyee.apppublic.ui.personalcenter.lawyer
 * @Description:    律师端-个人信息
 * @author:lzh
 * @date:   2017/8/9
 * @version
 * @verdescript   2017/8/9  lzh 初建
 * @Copyright: 2017/8/9 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class SettingActivity extends BaseActivity {


    private TextView mTvSave;
    private ImageView mIvHead;
    private RelativeLayout mRlHead;
    private TextView mTvName;
    private TextView mTvSex;
    private TextView mTvPhone;
    private TextView mTvLawfirm;
    private TextView mTvSpecialityTip1;
    private TextView mTvSpecialityTip2;
    private TextView mTvSpecialityTip3;
    private LinearLayout mLlSpeciality;
    private TextView mTvSpecialityTip4;
    private TextView mTvSpecialityTip5;
    private Context mContext;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
        mContext=this;
        initView();
        initData();
    }

    private void initData() {
        UserVO userVO= ApplicationSet.getInstance().getUserVO();
        TextViewUtil.isEmpty(mTvName,userVO.getRealName());
        TextViewUtil.isEmpty(mTvPhone,userVO.getMobile());
        TextViewUtil.isEmpty(mTvSex, BaseCommonToStringUtil.toString(userVO.getGender()));
        TextViewUtil.isEmpty(mTvLawfirm, userVO.getOrgName());
        String imageUrl = userVO.getPhoto();
        if (!TextUtils.isEmpty(imageUrl)) {
            ImageLoader.getInstance().displayImage(UrlUtil.getImageFileUrl(mContext, imageUrl), mIvHead, ApplicationSet.CDIO_LAW);
        } else {
            mIvHead.setImageResource(R.drawable.ic_default_avatar);
        }
        if (userVO.getBusiness() != null && userVO.getBusiness().size() > 0) {
            for (int i = 0; i < userVO.getBusiness().size(); i++) {
                switch (i) {
                    case 0:
                        String str1 = BaseCommonToStringUtil.toString(userVO.getBusiness().get(0).getBusiness());
                        if (str1 == null || str1.equals("")) {
                            mTvSpecialityTip1.setVisibility(View.GONE);
                        } else {
                            mTvSpecialityTip1.setVisibility(View.VISIBLE);
                            TextViewUtil.isEmpty(mTvSpecialityTip1, str1);
                        }

                        break;
                    case 1:
                        String str2 = BaseCommonToStringUtil.toString(userVO.getBusiness().get(1).getBusiness());
                        if (str2 == null || str2.equals("")) {
                            mTvSpecialityTip2.setVisibility(View.GONE);
                        } else {
                            mTvSpecialityTip2.setVisibility(View.VISIBLE);
                            TextViewUtil.isEmpty(mTvSpecialityTip2, str2);
                        }
                        break;
                    case 2:
                        String str3 = BaseCommonToStringUtil.toString(userVO.getBusiness().get(2).getBusiness());
                        if (str3 == null || str3.equals("")) {
                            mTvSpecialityTip3.setVisibility(View.GONE);
                        } else {
                            mTvSpecialityTip3.setVisibility(View.VISIBLE);
                            TextViewUtil.isEmpty(mTvSpecialityTip3, str3);
                        }
                        break;
                    case 3:
                        String str4 = BaseCommonToStringUtil.toString(userVO.getBusiness().get(3).getBusiness());
                        if (str4 == null || str4.equals("")) {
                            mTvSpecialityTip4.setVisibility(View.GONE);
                        } else {
                            mTvSpecialityTip4.setVisibility(View.VISIBLE);
                            TextViewUtil.isEmpty(mTvSpecialityTip4, str4);
                        }
                        break;
                    case 4:
                        String str5 = BaseCommonToStringUtil.toString(userVO.getBusiness().get(4).getBusiness());
                        if (str5 == null || str5.equals("")) {
                            mTvSpecialityTip5.setVisibility(View.GONE);
                        } else {
                            mTvSpecialityTip5.setVisibility(View.VISIBLE);
                            TextViewUtil.isEmpty(mTvSpecialityTip5, str5);
                        }
                        break;
                }
            }
        }
    }

    private void initView() {
        mTvSave= (TextView) findViewById(R.id.tv_save);
        mIvHead= (ImageView) findViewById(R.id.iv_head);
        mRlHead= (RelativeLayout) findViewById(R.id.rl_head);
        mTvName= (TextView) findViewById(R.id.tv_name);
        mTvSex= (TextView) findViewById(R.id.tv_sex);
        mTvPhone= (TextView) findViewById(R.id.tv_phone);
        mTvLawfirm= (TextView) findViewById(R.id.tv_lawfirm);
        mTvSpecialityTip1= (TextView) findViewById(R.id.tv_speciality_tip1);
        mTvSpecialityTip2= (TextView) findViewById(R.id.tv_speciality_tip2);
        mTvSpecialityTip3= (TextView) findViewById(R.id.tv_speciality_tip3);
        mLlSpeciality= (LinearLayout) findViewById(R.id.ll_speciality);
        mTvSpecialityTip4= (TextView) findViewById(R.id.tv_speciality_tip4);
        mTvSpecialityTip5= (TextView) findViewById(R.id.tv_speciality_tip5);
        mTvSave.setVisibility(View.INVISIBLE);
        mIvHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, MultiImageSelectorActivity.class);
//                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
//                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
//                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
//                startActivityForResult(intent, IMAGE_PICKER);
            }
        });



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case IMAGE_PICKER://图片回调
                if(data==null||data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT)==null){
                    return;
                }
                ArrayList<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (path != null||path.size()>0) {
                    //是否发送原图
                    for (String name : path
                            ) {
                        //   sendImagesMsg(name,false);
                        ImageLoader loader = ImageLoader.getInstance();
                        loader.displayImage(Uri.parse("file://" + name).toString() ,mIvHead);
                    }
                }
                break;
        }
    }
}
