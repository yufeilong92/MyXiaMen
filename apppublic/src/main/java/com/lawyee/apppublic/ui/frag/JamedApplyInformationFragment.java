package com.lawyee.apppublic.ui.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.ui.org.JamedOrgListActivity;
import com.lawyee.apppublic.ui.org.JamedOrgTypeSelActivity;
import com.lawyee.apppublic.vo.JamedApplyDetailVO;
import com.lawyee.apppublic.vo.JamedApplyTwoSubmitEven;
import com.lawyee.apppublic.vo.JamedIsNextTwoEvent;
import com.lawyee.apppublic.vo.JamedOpenLineEvent;

import net.lawyee.mobilelib.utils.T;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: JamedApplyInformationFragment.java
 * @Package com.lawyee.apppublic.ui.frag
 * @Description: 人民调解申请信息页
 * @author: YFL
 * @date: 2017/6/12 15:22
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/6/12 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class JamedApplyInformationFragment extends Fragment implements View.OnClickListener {
    private static  String TAG = JamedApplyInformationFragment.class.getSimpleName();
    /**
     * 选择的调解类型  1 一般调解 2 专业调解
     */
    private String mMediateType;
    /**
     * 传递参数--选中机构名字
     */
    public static final String SELECTCALLBACKNAME = "orgname";
    /**
     * 传递参数--选中机构oid
     */
    public static final String SELECTCALLBACKOID = "orgoid";
    /**
     * 传递参数--选中机构是否开通网上申请
     */
    public static final String SELECTCALLBACKAPPLY = "orgapply";
    /**
     * 传递参数--请求码
     */
    public static final int REQUESTCODE = 1100;
    private RadioGroup mRdgJamedApply;
    private TextView mTvJamedSelectorg;
    private CheckBox mChbSelectMedia;
    private RadioButton mRadiobtnJamedSame;
    private RadioButton mRadiobtnJamedSpecialty;
    private TextView mTvJamedWarning;

    /**
     * 调解机构oid
     */
    private String mSelectOrgOid;
    private String mOpenLine;
    /**
     * 是否同意大篷车 //true 同意 false 不同意
     */
    private String mCheckbok;
    private String mSelectOrgName;

    private String mFalse="false";
    private String mTrue="true";

    private String mSame="1";//一般调解
    private String mSpecialty="2";//专业调解

    public String getmCheckbok() {
        return mCheckbok;
    }

    public void setmCheckbok(String mCheckbok) {
        this.mCheckbok = mCheckbok;
    }

    public String getMediateType() {
        return mMediateType;
    }

    public void setMediateType(String mMediateType) {
        this.mMediateType = mMediateType;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jamed_information, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        handlerData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void handlerData() {
        mRadiobtnJamedSame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {//一般调解
                    setMediateType(mSame);
                }
            }
        });
        mRadiobtnJamedSpecialty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {//专业调解
                    setMediateType(mSpecialty);
                }

            }
        });
        mChbSelectMedia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setmCheckbok(mTrue);
                } else {
                    setmCheckbok(mFalse);
                }

            }
        });
    }

    private void initView(View view) {
        mRdgJamedApply = (RadioGroup) view.findViewById(R.id.rdg_jamed_apply);
        mTvJamedSelectorg = (TextView) view.findViewById(R.id.tv_jamed_selectorg);
        mChbSelectMedia = (CheckBox) view.findViewById(R.id.chb_select_media);
        mRadiobtnJamedSame = (RadioButton) view.findViewById(R.id.radiobtn_jamed_same);
        mRadiobtnJamedSame.setOnClickListener(this);
        mRadiobtnJamedSpecialty = (RadioButton) view.findViewById(R.id.radiobtn_jamed_specialty);
        mRadiobtnJamedSpecialty.setOnClickListener(this);
        mTvJamedWarning = (TextView) view.findViewById(R.id.tv_jamed_warning);
        mTvJamedWarning.setOnClickListener(this);
        mTvJamedSelectorg.setOnClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(JamedIsNextTwoEvent event) {
        if (event.isNext()) {
            submit();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE) {
            if (data == null) {
                return;
            }
            Bundle bundle = data.getExtras();
            mSelectOrgName = bundle.getString(SELECTCALLBACKNAME);
            mSelectOrgOid = bundle.getString(SELECTCALLBACKOID);
            mOpenLine = bundle.getString(SELECTCALLBACKAPPLY);
            mTvJamedSelectorg.setText(mSelectOrgName);
            if (TextUtils.isEmpty(mOpenLine)) {
                mTvJamedWarning.setVisibility(View.VISIBLE);
                EventBus.getDefault().post(new JamedOpenLineEvent(false));
            } else if (mOpenLine.equals(mFalse)) {
                mTvJamedWarning.setVisibility(View.VISIBLE);
                EventBus.getDefault().post(new JamedOpenLineEvent(false));
            } else if (mOpenLine.equals(mTrue)) {
                mTvJamedWarning.setVisibility(View.GONE);
                EventBus.getDefault().post(new JamedOpenLineEvent(true));
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_jamed_selectorg:
                if (getMediateType() == null || TextUtils.isEmpty(getMediateType())) {
                    T.showShort(getContext(), getString(R.string.jamed_pleaseSelectJameType));
                    return;
                }
                Intent intent = new Intent(getActivity(), JamedOrgTypeSelActivity.class);
                intent.putExtra(JamedOrgListActivity.CSTR_EXTRA_TITLE_STR,getString(R.string.intent_jamed_type));
                if (getMediateType().equals(mSame)) {
                    intent.putExtra(JamedOrgTypeSelActivity.SELECTORGSELECTTYPE,getResources().getString(R.string.jamed_commonly));
                } else if (getMediateType().equals(mSpecialty)) {
                    intent.putExtra(JamedOrgTypeSelActivity.SELECTORGSELECTTYPE,getResources().getString(R.string.jamed_profession));
                }
                startActivityForResult(intent, REQUESTCODE);
                break;
            default:
                break;
        }
    }


    private void submit() {
        JamedApplyDetailVO vo = new JamedApplyDetailVO();
        if (getMediateType() == null || TextUtils.isEmpty(getMediateType())) {
            T.showShort(getContext(), getString(R.string.jamed_pleaseSelectJameType));
            return;
        }

        if (getMediateType().equals(mSame)) {
            vo.setTjType(false);//一般调解
        } else if (getMediateType().equals(mSpecialty)) {
            vo.setTjType(true);//专业调解
        }
        String orgname = mTvJamedSelectorg.getText().toString().trim();
        if (orgname.equals(getResources().getString(R.string.jamed_select))) {
            T.showShort(getContext(), getString(R.string.jamed_pleaseSelectJamedOrg));
            return;
        }

        if (TextUtils.isEmpty(mSelectOrgOid) || mSelectOrgOid == null)
            return;
        vo.setTjOrgId(mSelectOrgOid);//调解机构id
        vo.setTjOrgName(mSelectOrgName);


        if (getmCheckbok() != null && !TextUtils.isEmpty(getmCheckbok())) {
            if (getmCheckbok().equals(mTrue)) {
                vo.setMediaFlag(true); //同意参加大篷车
            }else {
                vo.setMediaFlag(false);
            }
            Log.d(TAG, "submit: "+vo.isMediaFlag());
        }
        EventBus.getDefault().post(new JamedApplyTwoSubmitEven(vo, true));
    }
}
