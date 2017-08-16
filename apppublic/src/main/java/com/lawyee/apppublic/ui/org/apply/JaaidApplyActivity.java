package com.lawyee.apppublic.ui.org.apply;

import android.content.Intent;
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
import com.lawyee.apppublic.services.JaaidApplyService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.ui.frag.JaaidApplyInformationFragment;
import com.lawyee.apppublic.ui.frag.JaaidApplyNoticeFragment;
import com.lawyee.apppublic.ui.frag.JaaidApplyOtherInformationFragment;
import com.lawyee.apppublic.ui.frag.JaaidApplySubmitFragment;
import com.lawyee.apppublic.vo.JaaidApplyDetailVO;
import com.lawyee.apppublic.vo.JaaidApplyEvent;
import com.lawyee.apppublic.vo.JaaidApplyFourSubmitEvent;
import com.lawyee.apppublic.vo.JaaidApplyThreeSubmitEvent;
import com.lawyee.apppublic.vo.JaaidApplyTwoSubmitEven;
import com.lawyee.apppublic.vo.JaaidIsNextFourEvent;
import com.lawyee.apppublic.vo.JaaidIsNextThreeEvent;
import com.lawyee.apppublic.vo.JaaidIsNextTwoEvent;

import net.lawyee.mobilelib.utils.ActivityUtil;
import net.lawyee.mobilelib.utils.ScreenUtils;
import net.lawyee.mobilelib.utils.T;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LegalAIdApplyActivity.java
 * @Package com.lawyee.apppublic.ui
 * @Description: 法援申请页
 * @author: YFL
 * @date: 2017/5/20 9:47
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/5/20 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class JaaidApplyActivity extends BaseActivity implements View.OnClickListener {
    private FrameLayout mFlApply;
    private FragmentManager fm;
    private ArrayList<Fragment> fragments;
    private TextView mTvApplyOne;
    private ImageView mIvApplyOne;
    private TextView mTvApplyTwo;
    private ImageView mIvApplyTwo;
    private TextView mTvApplyThree;
    private ImageView mIvApplyThree;
    private TextView mTvApplyeFour;
    private ImageView mIvApplyFour;
    private Button mBtnApplyBack;
    private Button mBtnApplyNext;
    private ImageView mIvApplyBack;
    private LinearLayout mLiApply;
    private LinearLayout mLiPrompt;
    private LinearLayout li_title;
    Fragment mTtemFragment;
    /**
     * 申请数据
     */
    private JaaidApplyDetailVO mJaaidDetailData;
    /**
     * 处理跳转fragment 序列
     */
    private int mMark;
    /**
     * 处理是否确认点击
     */
    private boolean mCheckBos;
    /**
     * 是否下二步
     */
    private boolean isNextTwo;
    /**
     * 是否跳转第三页
     */
    private boolean isNextThree;
    /**
     * 是否跳转第四页
     */
    private boolean isNextFour;

    public boolean getNextFour() {
        return isNextFour;
    }

    public void setNextFour(boolean nextFour) {
        isNextFour = nextFour;
    }

    public boolean getIsNextThree() {
        return isNextThree;
    }

    public void setNextThree(boolean nextThree) {
        isNextThree = nextThree;
    }

    public boolean getIsNextTwo() {
        return isNextTwo;
    }

    public void setNextTwo(boolean nextTwo) {
        isNextTwo = nextTwo;
    }

    public int getmMark() {
        return mMark;
    }

    public void setmMark(int mMark) {
        this.mMark = mMark;
    }

    public void setmCheckBos(boolean mCheckBos) {
        this.mCheckBos = mCheckBos;
    }

    public boolean getmCheckBos() {
        return mCheckBos;
    }

    public JaaidApplyDetailVO getJaaidDetailData() {
        return mJaaidDetailData;
    }

    public void setmJaaidDetailData(JaaidApplyDetailVO mJaaidDetailData) {
        this.mJaaidDetailData = mJaaidDetailData;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_legal_aid_apply);
        EventBus.getDefault().register(this);
        int screenHeight = ScreenUtils.getScreenHeight(this);
        initView();
//        handlerView(screenHeight);
        initAddFragmentView();
    }

    /**
     * 动态添加布局
     *
     * @param screenHeight
     */
    private void handlerView(int screenHeight) {
        int height = mLiApply.getLayoutParams().height;
        int height1 = mLiPrompt.getLayoutParams().height;
        int height2 = li_title.getLayoutParams().height;
        int i = screenHeight - height - height1 - 16 - height2;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, i);
        mFlApply.setLayoutParams(params);
    }

    private void initView() {
        li_title = (LinearLayout) findViewById(R.id.li_title);
        mLiPrompt = (LinearLayout) findViewById(R.id.li_prompt);
        mLiApply = (LinearLayout) findViewById(R.id.li_apply);
        mFlApply = (FrameLayout) findViewById(R.id.fl_content);
        mTvApplyOne = (TextView) findViewById(R.id.tv_apply_one);
        mIvApplyOne = (ImageView) findViewById(R.id.iv_apply_one);
        mTvApplyTwo = (TextView) findViewById(R.id.tv_apply_two);
        mIvApplyTwo = (ImageView) findViewById(R.id.iv_apply_two);
        mTvApplyThree = (TextView) findViewById(R.id.tv_apply_three);
        mIvApplyThree = (ImageView) findViewById(R.id.iv_apply_three);
        mTvApplyeFour = (TextView) findViewById(R.id.tv_applye_four);
        mIvApplyFour = (ImageView) findViewById(R.id.iv_apply_four);
        mBtnApplyBack = (Button) findViewById(R.id.btn_apply_back);
        mBtnApplyBack.setOnClickListener(this);
        mBtnApplyNext = (Button) findViewById(R.id.btn_apply_next);
        mBtnApplyNext.setOnClickListener(this);
        mIvApplyBack = (ImageView) findViewById(R.id.iv_apply_back);
        mIvApplyBack.setOnClickListener(this);
        li_title.setOnClickListener(this);
    }

    private void initAddFragmentView() {
        fragments = new ArrayList<>();
        JaaidApplyNoticeFragment apply_notice_fragment = new JaaidApplyNoticeFragment();
        JaaidApplyOtherInformationFragment apply_otherInformation_fragment = new JaaidApplyOtherInformationFragment();
        JaaidApplyInformationFragment applyInfromationFragment = new JaaidApplyInformationFragment();
        JaaidApplySubmitFragment apply_submit_fragment = new JaaidApplySubmitFragment();
        fragments.add(apply_notice_fragment);
        fragments.add(applyInfromationFragment);
        fragments.add(apply_otherInformation_fragment);
        fragments.add(apply_submit_fragment);
        fm = getSupportFragmentManager();
        for (int i = 0; i < fragments.size(); i++) {
            fm.beginTransaction().add(R.id.fl_content, fragments.get(i)).hide(fragments.get(i)).commit();
        }
        fm.beginTransaction().show(fragments.get(0)).commit();
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
     * 处理信息页填写数据
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(JaaidApplyTwoSubmitEven event) {
        setNextTwo(event.isNext());
        setmJaaidDetailData(event.getJaaidApplyDetailVO());
        String trim = mBtnApplyNext.getText().toString().trim();
        switchFragment(trim, getmMark());
    }

    /**
     * 处理其它信息页填写数据
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(JaaidApplyThreeSubmitEvent event) {
        setNextThree(event.isNext());
        setmJaaidDetailData(event.getJaaidApplyDetailVO());
        String trim = mBtnApplyNext.getText().toString().trim();
        switchFragment(trim, getmMark());
    }

    /**
     * 提交页填写数据
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(JaaidApplyFourSubmitEvent event) {
        setNextFour(event.isNext());
        setmJaaidDetailData(event.getJaaidApplyDetailVO());
        String trim = mBtnApplyNext.getText().toString().trim();
        switchFragment(trim, getmMark());
    }

    /**
     * @param id
     */
    private void handlerProcess_background(int id) {
        switch (id) {
            case 1:
                mTvApplyOne.setBackgroundResource(R.mipmap.bg_apply_green);
                mTvApplyTwo.setBackgroundResource(R.mipmap.bg_apply_yellow);
                mTvApplyThree.setBackgroundResource(R.mipmap.bg_apply_gray);
                mTvApplyeFour.setBackgroundResource(R.mipmap.bg_apply_gray);
                mIvApplyOne.setVisibility(View.GONE);
                mIvApplyTwo.setVisibility(View.VISIBLE);
                mIvApplyThree.setVisibility(View.GONE);
                mIvApplyFour.setVisibility(View.GONE);
                break;
            case 2:
                mTvApplyOne.setBackgroundResource(R.mipmap.bg_apply_green);
                mTvApplyTwo.setBackgroundResource(R.mipmap.bg_apply_green);
                mTvApplyThree.setBackgroundResource(R.mipmap.bg_apply_yellow);
                mTvApplyeFour.setBackgroundResource(R.mipmap.bg_apply_gray);
                mIvApplyOne.setVisibility(View.GONE);
                mIvApplyTwo.setVisibility(View.GONE);
                mIvApplyThree.setVisibility(View.VISIBLE);
                mIvApplyFour.setVisibility(View.GONE);
                break;
            case 3:
                mTvApplyOne.setBackgroundResource(R.mipmap.bg_apply_green);
                mTvApplyTwo.setBackgroundResource(R.mipmap.bg_apply_green);
                mTvApplyThree.setBackgroundResource(R.mipmap.bg_apply_green);
                mTvApplyeFour.setBackgroundResource(R.mipmap.bg_apply_yellow);
                mIvApplyOne.setVisibility(View.GONE);
                mIvApplyTwo.setVisibility(View.GONE);
                mIvApplyThree.setVisibility(View.GONE);
                mIvApplyFour.setVisibility(View.VISIBLE);
                break;
            default:
                mTvApplyOne.setBackgroundResource(R.mipmap.bg_apply_yellow);
                mTvApplyTwo.setBackgroundResource(R.mipmap.bg_apply_gray);
                mTvApplyThree.setBackgroundResource(R.mipmap.bg_apply_gray);
                mTvApplyeFour.setBackgroundResource(R.mipmap.bg_apply_gray);
                mIvApplyOne.setVisibility(View.VISIBLE);
                mIvApplyTwo.setVisibility(View.GONE);
                mIvApplyThree.setVisibility(View.GONE);
                mIvApplyFour.setVisibility(View.GONE);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_apply_back:
                onBack();
                break;
            case R.id.btn_apply_next:
                String trim = mBtnApplyNext.getText().toString().trim();
                switchFragment(trim, getmMark());
                break;
            case R.id.iv_apply_back:
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
                break;
        }
    }

    /**
     * 处理返回按钮
     */
    private void onBack() {
        switch (getmMark()) {
            case 3:
                setNextThree(false);
                switchFragment(fragments.get(3), fragments.get(2));
                mBtnApplyNext.setText(R.string.apply_btn_next);
                handlerProcess_background(2);
                setmMark(2);

                break;
            case 2:
                setNextTwo(false);
                switchFragment(fragments.get(2), fragments.get(1));
                handlerProcess_background(1);
                mBtnApplyNext.setText(R.string.apply_btn_next);
                setmMark(1);
                break;
            case 1:
                switchFragment(fragments.get(1), fragments.get(0));
                handlerProcess_background(0);
                mBtnApplyNext.setText(R.string.apply_btn_apply);
                mBtnApplyBack.setText(R.string.cancel);
                setmMark(0);
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
                    mBtnApplyNext.setText(R.string.apply_btn_next);
                    mBtnApplyBack.setText(R.string.apply_btn_back);
                    handlerProcess_background(1);
                    setmMark(1);
                }
                break;
            case 1://信息页
                if (!getIsNextTwo()) {
                    EventBus.getDefault().post(new JaaidIsNextTwoEvent(true));
                    return;
                }
                if (trim.equals(getResources().getString(R.string.apply_btn_next))) {
                    switchFragment(fragments.get(1), fragments.get(2));
                    mBtnApplyNext.setText(R.string.apply_btn_next);
                    handlerProcess_background(2);
                    setmMark(2);
                }
                break;
            case 2://其它信息页
                if (!getIsNextThree()) {
                    EventBus.getDefault().post(new JaaidIsNextThreeEvent(getJaaidDetailData(), true));
                    return;
                }
                if (trim.equals(getResources().getString(R.string.apply_btn_next))) {
                    switchFragment(fragments.get(2), fragments.get(3));
                    mBtnApplyNext.setText(R.string.apply_btn_next);
                    handlerProcess_background(3);
                    setmMark(3);
                    mBtnApplyNext.setText(R.string.apply_btn_submit);
                }
                break;
            case 3://提交页
                if (!getNextFour()) {
                    EventBus.getDefault().post(new JaaidIsNextFourEvent(getJaaidDetailData(), true));
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
                                handlerPoseRequest();
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
        }

    }

    private void handlerPoseRequest() {
        if(ActivityUtil.isServiceRunning(this,JaaidApplyService.class.getName()))
        {
            T.showLong(this,"您有法援预申请信息还在提交中，请在提交完成后再提交");
            return;
        }
        T.showLong(this,"预申请信息已经开始提交，请注意状态条信息提示");
        Intent intent = new Intent(this,JaaidApplyService.class);
        JaaidApplyDetailVO detailVO =getJaaidDetailData();
        intent.putExtra(JaaidApplyService.CSTR_EXTRA_JAAIDAPPLYDETAIL_VO,detailVO);
        startService(intent);
        this.finish();
        //JaaidApplyDetailVO.saveVO(getJaaidDetailData(),JaaidApplyDetailVO.dataFileName(this,"apply"));
        /*JaaidService jaaidService = new JaaidService(this);
        jaaidService.setShowProgress(true);
        jaaidService.setProgressShowContent(getString(R.string.submit_loading));
        jaaidService.postApply(getJaaidDetailData(), new BaseJsonService.IResultInfoListener() {
             @Override
             public void onComplete(ArrayList<Object> values, String content) {
                 T.showShort(getApplicationContext(),getString(R.string.submit_ok));
                 finish();
             }

             @Override
             public void onError(String msg, String content) {
                  T.showShort(getApplicationContext(),msg);
             }
         });*/
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
                        .add(R.id.fl_content, fragmentto).commit();
            } else {
                bt.hide(fragmentfrom)
                        .show(fragmentto).commit();
            }
            mTtemFragment = fragmentto;
        }
    }
}
