package com.lawyee.apppublic.ui.org.apply;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JamedService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.ui.frag.JamedApplyInformationFragment;
import com.lawyee.apppublic.ui.frag.JamedApplyNoticeFragment;
import com.lawyee.apppublic.ui.frag.JamedApplySubmitFragment;
import com.lawyee.apppublic.ui.frag.JamedApplyTypeFragment;
import com.lawyee.apppublic.vo.JaaidApplyEvent;
import com.lawyee.apppublic.vo.JamedApplyDetailVO;
import com.lawyee.apppublic.vo.JamedApplyFourIsNextEven;
import com.lawyee.apppublic.vo.JamedApplyFourSubmitEven;
import com.lawyee.apppublic.vo.JamedApplyThreeIsNextEven;
import com.lawyee.apppublic.vo.JamedApplyThreeSubmitEven;
import com.lawyee.apppublic.vo.JamedApplyTwoSubmitEven;
import com.lawyee.apppublic.vo.JamedIsNextTwoEvent;
import com.lawyee.apppublic.vo.JamedOpenLineEvent;

import net.lawyee.mobilelib.utils.T;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawerApp
 * @Package com.lawyee.apppublic.ui.org
 * @Description: 人民调解申请页
 * @author: YFL
 * @date: 2017/6/6 8:31
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JamedOrgApplyActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvApplyJamedBack;
    private LinearLayout mLiJamedTitle;
    private TextView mTvJaemdApplyOne;
    private ImageView mIvJamedApplyOne;
    private TextView mTvJaemdApplyTwo;
    private ImageView mIvJamedApplyTwo;
    private TextView mTvJamedApplyThree;
    private ImageView mIvJamedApplyThree;
    private TextView mTvJamedApplyeFour;
    private ImageView mIvJamedApplyFour;
    private LinearLayout mLiJamedPrompt;
    private FrameLayout mFlJamedLayuot;
    private Button mBtnJamedApplyBack;
    private Button mBtnJamedApplyNext;
    private LinearLayout mLiJamedApply;
    Fragment mTtemFragment;
    private List<Fragment> fragments;
    private FragmentManager fm;
    private int mMark;
    private boolean mCheckBos;
    /**
     * 申请数据Vo
     *
     * @return
     */
    private JamedApplyDetailVO mJamedApplyData;
    /**
     * 是否跳转第2页
     */
    private boolean isNextTwo;

    /**
     * 是否跳转第三页
     *
     * @return
     */
    private boolean isNextThree;
    /**
     * 是否跳转第四页
     *
     * @return
     */
    private boolean isNextFour;
    /**
     * 是否开通网上申请
     *
     * @return
     */
    private boolean isOpenLine;

    public boolean getOpenLine() {
        return isOpenLine;
    }

    public void setOpenLine(boolean openLine) {
        isOpenLine = openLine;
    }

    public boolean getisNextFour() {
        return isNextFour;
    }

    public void setNextFour(boolean nextFour) {
        isNextFour = nextFour;
    }

    public boolean getisNextThree() {
        return isNextThree;
    }

    public void setNextThree(boolean nextThree) {
        isNextThree = nextThree;
    }

    public boolean getisNextTwo() {
        return isNextTwo;
    }

    public void setNextTwo(boolean nextTwo) {
        isNextTwo = nextTwo;
    }


    public JamedApplyDetailVO getJamedApplyData() {
        return mJamedApplyData;
    }

    public void setJamedApplyData(JamedApplyDetailVO mJamedApplyData) {
        this.mJamedApplyData = mJamedApplyData;
    }

    public boolean getmCheckBos() {
        return mCheckBos;
    }

    public void setmCheckBos(boolean mCheckBos) {
        this.mCheckBos = mCheckBos;
    }

    public int getmMark() {
        return mMark;
    }

    public void setmMark(int mMark) {
        this.mMark = mMark;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_jamed_org_apply);
        EventBus.getDefault().register(this);
        initView();
//        handlerView(screenHeight);
        initAddFragmentView();
    }

    private void handlerView(int screenHeight) {
        int height = mLiJamedApply.getLayoutParams().height;
        int height1 = mLiJamedPrompt.getLayoutParams().height;
        int height2 = mLiJamedTitle.getLayoutParams().height;
        int i = screenHeight - height - height1 - 16 - height2;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, i);
        mFlJamedLayuot.setLayoutParams(params);
    }

    /**
     * 处理须知是否阅读
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(JaaidApplyEvent event) {
        setmCheckBos(event.getisCheck());
    }

    /**
     * 处理类型选择页
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(JamedApplyTwoSubmitEven event) {
        setJamedApplyData(event.getJamedApplyDataVo());
        setNextTwo(event.isNext());
        String trim = mBtnJamedApplyNext.getText().toString().trim();
        switchFragment(trim, getmMark());
    }

    /**
     * 处理调解填写页
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(JamedApplyThreeSubmitEven event) {
        setJamedApplyData(event.getJamedApplyDataVo());
        setNextThree(event.isNext());
        String trim = mBtnJamedApplyNext.getText().toString().trim();
        switchFragment(trim, getmMark());
    }

    /**
     * 处理提交填写写页
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(JamedApplyFourSubmitEven event) {
        setJamedApplyData(event.getJamedApplyDataVo());
        //缓存填写的数据
        JamedApplyDetailVO applyDetailVO = event.getJamedApplyDataVo();
        if(applyDetailVO!=null)
        {
            applyDetailVO.saveVO(applyDetailVO,
                    JamedApplyDetailVO.cacheDataFileName(JamedOrgApplyActivity.this));
        }
        setNextFour(event.isNext());
        String trim = mBtnJamedApplyNext.getText().toString().trim();
        switchFragment(trim, getmMark());
    }

    /**
     * 处理是否开通网上申请
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(JamedOpenLineEvent event) {
        setOpenLine(event.isOpenLine());
    }

    private void initView() {

        mIvApplyJamedBack = (ImageView) findViewById(R.id.iv_apply_jamed_back);
        mIvApplyJamedBack.setOnClickListener(this);
        mLiJamedTitle = (LinearLayout) findViewById(R.id.li_jamed_title);
        mLiJamedTitle.setOnClickListener(this);
        mTvJaemdApplyOne = (TextView) findViewById(R.id.tv_jaemd_apply_one);
        mTvJaemdApplyOne.setOnClickListener(this);
        mIvJamedApplyOne = (ImageView) findViewById(R.id.iv_jamed_apply_one);
        mIvJamedApplyOne.setOnClickListener(this);
        mTvJaemdApplyTwo = (TextView) findViewById(R.id.tv_jaemd_apply_two);
        mTvJaemdApplyTwo.setOnClickListener(this);
        mIvJamedApplyTwo = (ImageView) findViewById(R.id.iv_jamed_apply_two);
        mIvJamedApplyTwo.setOnClickListener(this);
        mTvJamedApplyThree = (TextView) findViewById(R.id.tv_jamed_apply_three);
        mTvJamedApplyThree.setOnClickListener(this);
        mIvJamedApplyThree = (ImageView) findViewById(R.id.iv_jamed_apply_three);
        mIvJamedApplyThree.setOnClickListener(this);
        mTvJamedApplyeFour = (TextView) findViewById(R.id.tv_jamed_applye_four);
        mTvJamedApplyeFour.setOnClickListener(this);
        mIvJamedApplyFour = (ImageView) findViewById(R.id.iv_jamed_apply_four);
        mIvJamedApplyFour.setOnClickListener(this);
        mLiJamedPrompt = (LinearLayout) findViewById(R.id.li_jamed_prompt);
        mLiJamedPrompt.setOnClickListener(this);
        mFlJamedLayuot = (FrameLayout) findViewById(R.id.fl_jamed_layuot);
        mFlJamedLayuot.setOnClickListener(this);
        mBtnJamedApplyBack = (Button) findViewById(R.id.btn_jamed_apply_back);
        mBtnJamedApplyBack.setOnClickListener(this);
        mBtnJamedApplyNext = (Button) findViewById(R.id.btn_jamed_apply_next);
        mBtnJamedApplyNext.setOnClickListener(this);
        mLiJamedApply = (LinearLayout) findViewById(R.id.li_jamed_apply);
        mLiJamedApply.setOnClickListener(this);
    }

    /**
     * 处理流程显示
     *
     * @param id
     */
    private void handlerProcess_background(int id) {
        switch (id) {
            case 1:
                mTvJaemdApplyOne.setBackgroundResource(R.mipmap.bg_apply_green);
                mTvJaemdApplyTwo.setBackgroundResource(R.mipmap.bg_apply_yellow);
                mTvJamedApplyThree.setBackgroundResource(R.mipmap.bg_apply_gray);
                mTvJamedApplyeFour.setBackgroundResource(R.mipmap.bg_apply_gray);
                mIvJamedApplyOne.setVisibility(View.GONE);
                mIvJamedApplyTwo.setVisibility(View.VISIBLE);
                mIvJamedApplyThree.setVisibility(View.GONE);
                mIvJamedApplyFour.setVisibility(View.GONE);
                break;
            case 2:
                mTvJaemdApplyOne.setBackgroundResource(R.mipmap.bg_apply_green);
                mTvJaemdApplyTwo.setBackgroundResource(R.mipmap.bg_apply_green);
                mTvJamedApplyThree.setBackgroundResource(R.mipmap.bg_apply_yellow);
                mTvJamedApplyeFour.setBackgroundResource(R.mipmap.bg_apply_gray);
                mIvJamedApplyOne.setVisibility(View.GONE);
                mIvJamedApplyTwo.setVisibility(View.GONE);
                mIvJamedApplyThree.setVisibility(View.VISIBLE);
                mIvJamedApplyFour.setVisibility(View.GONE);
                break;
            case 3:
                mTvJaemdApplyOne.setBackgroundResource(R.mipmap.bg_apply_green);
                mTvJaemdApplyTwo.setBackgroundResource(R.mipmap.bg_apply_green);
                mTvJamedApplyThree.setBackgroundResource(R.mipmap.bg_apply_green);
                mTvJamedApplyeFour.setBackgroundResource(R.mipmap.bg_apply_yellow);
                mIvJamedApplyOne.setVisibility(View.GONE);
                mIvJamedApplyTwo.setVisibility(View.GONE);
                mIvJamedApplyThree.setVisibility(View.GONE);
                mIvJamedApplyFour.setVisibility(View.VISIBLE);
                break;
            default:
                mTvJaemdApplyOne.setBackgroundResource(R.mipmap.bg_apply_yellow);
                mTvJaemdApplyTwo.setBackgroundResource(R.mipmap.bg_apply_gray);
                mTvJamedApplyThree.setBackgroundResource(R.mipmap.bg_apply_gray);
                mTvJamedApplyeFour.setBackgroundResource(R.mipmap.bg_apply_gray);
                mIvJamedApplyOne.setVisibility(View.VISIBLE);
                mIvJamedApplyTwo.setVisibility(View.GONE);
                mIvJamedApplyThree.setVisibility(View.GONE);
                mIvJamedApplyFour.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    /**
     * 动态添加 fragmen
     */
    private void initAddFragmentView() {
        fragments = new ArrayList<>();
        JamedApplyNoticeFragment apply_notice_fragment = new JamedApplyNoticeFragment();
        JamedApplyInformationFragment applyInfromationFragment = new JamedApplyInformationFragment();
        JamedApplyTypeFragment applytypeFragment = new JamedApplyTypeFragment();
        JamedApplySubmitFragment apply_submit_fragment = new JamedApplySubmitFragment();
        fragments.add(apply_notice_fragment);
        fragments.add(applyInfromationFragment);
        fragments.add(applytypeFragment);
        fragments.add(apply_submit_fragment);
        fm = getSupportFragmentManager();
        for (int i = 0; i < fragments.size(); i++) {
            fm.beginTransaction().add(R.id.fl_jamed_layuot, fragments.get(i)).hide(fragments.get(i)).commit();
        }
        fm.beginTransaction().show(fragments.get(0)).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jamed_apply_back:
                onBack();
                break;
            case R.id.btn_jamed_apply_next:
                String trim = mBtnJamedApplyNext.getText().toString().trim();
                switchFragment(trim, getmMark());
                break;
            case R.id.iv_apply_jamed_back:
                new MaterialDialog.Builder(this)
                        .limitIconToDefaultSize() // limits the displayed icon size to 48dp
                        .title(R.string.dl_title_backIsconfirm)
                        .positiveText(R.string.dl_btn_ok)
                        .negativeText(R.string.cancel)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                finish();
                            }
                        })
                        .show();
                break;
        }
    }

    /**
     * 处理返回按钮
     */
    private void onBack() {
        switch (getmMark()) {
            case 3:
                switchFragment(fragments.get(3), fragments.get(2));
                mBtnJamedApplyNext.setText(R.string.apply_btn_next);
                handlerProcess_background(2);
                setmMark(2);
                setNextThree(false);
                break;
            case 2:
                switchFragment(fragments.get(2), fragments.get(1));
                mBtnJamedApplyNext.setText(R.string.apply_btn_next);
                handlerProcess_background(1);
                setmMark(1);
                setNextTwo(false);
                break;
            case 1:
                switchFragment(fragments.get(1), fragments.get(0));
                handlerProcess_background(0);
                setmMark(0);

                mBtnJamedApplyNext.setText(R.string.apply_btn_apply);
                mBtnJamedApplyBack.setText(R.string.cancel);
                break;
            case 0:
                finish();
                break;
            default:
                break;
        }
    }

    /***
     * 切换fragment
     * @param trim
     * @param mark
     */
    private void switchFragment(String trim, int mark) {
        if (TextUtils.isEmpty(trim))
            return;
        switch (mark) {
            case 0://须知页
                if (!getmCheckBos()) {
                    T.showShort(this, getString(R.string.pleaseReader));
                    return;
                }
                if (trim.equals(getResources().getString(R.string.apply_btn_apply))) {
                    switchFragment(fragments.get(0), fragments.get(1));
                    mBtnJamedApplyNext.setText(R.string.apply_btn_next);
                    mBtnJamedApplyBack.setText(R.string.apply_btn_back);
                    handlerProcess_background(1);
                    setmMark(1);
                }
                break;
            case 1://调解选择页
                if (!getisNextTwo()) {
                    EventBus.getDefault().post(new JamedIsNextTwoEvent(true));
                    return;
                }
                if (!getOpenLine()){
                    T.showShort(this,getString(R.string.jamed_openline));
                    return;
                }
                if (trim.equals(getResources().getString(R.string.apply_btn_next))) {
                    switchFragment(fragments.get(1), fragments.get(2));
                    mBtnJamedApplyNext.setText(R.string.apply_btn_next);
                    handlerProcess_background(2);
                    setmMark(2);
                }
                break;
            case 2://调解双方信息
                if (!getisNextThree()) {
                    EventBus.getDefault().post(new JamedApplyThreeIsNextEven(getJamedApplyData(), true));
                    return;
                }
                if (trim.equals(getResources().getString(R.string.apply_btn_next))) {
                    switchFragment(fragments.get(2), fragments.get(3));
                    mBtnJamedApplyNext.setText(R.string.apply_btn_next);
                    handlerProcess_background(3);
                    mBtnJamedApplyNext.setText(R.string.apply_btn_submit);
                    setmMark(3);

                }
                break;
            case 3://提交页
                if (!getisNextFour()) {
                    EventBus.getDefault().post(new JamedApplyFourIsNextEven(getJamedApplyData(), true));
                    return;
                }
                new MaterialDialog.Builder(this)
                        .limitIconToDefaultSize()
                        .title(R.string.dl_title_pleasesubmit)
                        .positiveText(R.string.dl_btn_ok)
                        .negativeText(R.string.dl_btn_cancel)
                        .cancelable(false)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                handlerRequestData();
//                                finish();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                setNextFour(false);
                            }
                        })
                        .show();
                break;
            default:
                break;
        }

    }

    private void handlerRequestData() {
        JamedService jamedService = new JamedService(this);
        jamedService.setProgressShowContent(getString(R.string.submit_loading));
        jamedService.setShowProgress(true);
        jamedService.postApply(getJamedApplyData(), new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                if (values == null || values.isEmpty()) {
                    T.showShort(getApplicationContext(),getString(R.string.submit_ok));
                    finish();
                    return;
                }
            }

            @Override
            public void onError(String msg, String content) {
            T.showShort(getApplicationContext(),msg);
            }
        });
    }

    @Override
    public void onBackPressed() {
        new MaterialDialog.Builder(this)
                .limitIconToDefaultSize() // limits the displayed icon size to 48dp
                .title(R.string.dl_title_backIsconfirm)
                .positiveText(R.string.dl_btn_ok)
                .negativeText(R.string.dl_btn_cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .show();
    }

    /**
     * 使用hide和show方法切换Fragment
     *
     * @param fragmentfrom 隐藏fragment
     * @param fragmentto   需要切换的fragment
     */
    private void switchFragment(Fragment fragmentfrom, Fragment fragmentto) {

        if (fragmentto != mTtemFragment) {
            FragmentTransaction bt = fm.beginTransaction();
            if (!fragmentto.isAdded()) {
                bt.hide(fragmentfrom)
                        .add(R.id.fl_jamed_layuot, fragmentto).commit();
            } else {
                bt.hide(fragmentfrom)
                        .show(fragmentto).commit();
            }
            mTtemFragment = fragmentto;
        }
    }
}
