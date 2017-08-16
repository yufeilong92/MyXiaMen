package com.lawyee.apppublic.ui.personalcenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.UserService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.ui.frag.AidDetailFragment;
import com.lawyee.apppublic.ui.frag.AidStatusFragment;
import com.lawyee.apppublic.vo.JaaidApplyDetailVO;
import com.lawyee.apppublic.vo.JaaidApplyVO;

import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  标题
 * @Package com.lawyee.apppublic.ui.personalcenter
 * @Description:    个人中心——法律援助详情
 * @author:lzh
 * @date:   2017/8/9
 * @version
 * @verdescript   2017/8/9  lzh 初建
 * @Copyright: 2017/8/9 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class MyLawAidDetailActivity extends BaseActivity {
    public static String AID = "JaaidApplyVO";
    private TextView mTvStatus;//    状态：0  ；   详情：1
    private TextView mTvDetail;
    private JaaidApplyVO mJaaidApplyVO;
    private int mType;
    Fragment mTempFragment = null;
    private FragmentManager mFm;
    private ArrayList<Fragment> mFragments;
    private Context mContext;
    private JaaidApplyDetailVO mJaaidApplyDetailVO;
    private TextView mTvTitle;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_law_aid_detail);
        mContext = this;
        initView();
        loadData();
    }


    private void initView() {
        mJaaidApplyVO = (JaaidApplyVO) getIntent().getSerializableExtra(AID);
        if (mJaaidApplyVO == null) {
            finish();
        }
        mTvStatus = (TextView) findViewById(R.id.tv_status);
        mTvDetail = (TextView) findViewById(R.id.tv_detail);
        mTvTitle= (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText(R.string.my_law_aid);
        switchTab(0);
        mTvStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mType != 0) {
                    mType = 0;
                    switchTab(0);
                    if (mJaaidApplyDetailVO != null) {
                        switchFragment(mFragments.get(1), mFragments.get(0));
                    }

                }
            }
        });
        mTvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mType != 1) {
                    mType = 1;
                    switchTab(1);
                    if (mJaaidApplyDetailVO != null) {
                        switchFragment(mFragments.get(0), mFragments.get(1));
                    }
                }
            }
        });

    }

    private void initData() {
        mFragments = new ArrayList<>();
        AidStatusFragment aidStatusFragment = new AidStatusFragment();//法援预申请状态
        Bundle bundle = new Bundle();
        bundle.putSerializable(AID, mJaaidApplyDetailVO);
        aidStatusFragment.setArguments(bundle);
        AidDetailFragment aidDetailFragment = new AidDetailFragment();//法援预申请详情
        Bundle bundle1 = new Bundle();
        bundle1.putSerializable(AID, mJaaidApplyDetailVO);
        aidDetailFragment.setArguments(bundle1);
        mFragments.add(aidStatusFragment);
        mFragments.add(aidDetailFragment);
        mFm = getSupportFragmentManager();
        for (int i = 0; i < mFragments.size(); i++) {
            FragmentTransaction beginTransaction = mFm.beginTransaction();
            beginTransaction.addToBackStack(null);
            beginTransaction.add(R.id.fl_content, mFragments.get(i)).hide(mFragments.get(i)).commit();
        }
        mFm.beginTransaction().show(aidStatusFragment).commit();
    }

    //切换页面上方的Tab
    private void switchTab(int type) {
        if (type == 0) {
            mTvStatus.setSelected(true);
            mTvDetail.setSelected(false);
        } else {
            mTvStatus.setSelected(false);
            mTvDetail.setSelected(true);
        }
    }

    /**
     * 使用hide和show方法切换Fragment
     *
     * @param fragmentfrom 隐藏fragment
     * @param fragmentto   需要切换的fragment
     */
    private void switchFragment(Fragment fragmentfrom, Fragment fragmentto) {
        if (fragmentto != mTempFragment) {
            FragmentTransaction bt = mFm.beginTransaction();
            bt.addToBackStack(null);
            if (!fragmentto.isAdded()) {
                bt.hide(fragmentfrom)
                        .add(R.id.fl_content, fragmentto).commit();
            } else {
                bt.hide(fragmentfrom)
                        .show(fragmentto).commit();
            }
            mTempFragment = fragmentto;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            MyLawAidDetailActivity.this.finish();
        }
        return true;
    }

    private void loadData() {
        if (getInProgess())
            return;
        setInProgess(true);
        UserService service = new UserService(this);
        service.setProgressShowContent(mContext.getString(R.string.get_ing));
        service.setShowProgress(true);
        service.getJaaidApplyDetail(mJaaidApplyVO.getOid(), new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                setInProgess(false);
                if (values == null || values.isEmpty() || !(values.get(0) instanceof JaaidApplyDetailVO)) {
                    T.showLong(mContext, getString(R.string.get_error_noeffectdata));
                    return;
                }
                mJaaidApplyDetailVO = (JaaidApplyDetailVO) values.get(0);
                initData();
            }

            @Override
            public void onError(String msg, String content) {
                T.showLong(mContext, msg);
                setInProgess(false);
            }
        });
    }

}
