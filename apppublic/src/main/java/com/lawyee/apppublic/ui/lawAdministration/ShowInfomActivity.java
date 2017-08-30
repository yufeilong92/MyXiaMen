package com.lawyee.apppublic.ui.lawAdministration;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JamedUserService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.ui.frag.fragService.jamed.JamedFourFragment;
import com.lawyee.apppublic.ui.frag.fragService.jamed.JamedOneFragment;
import com.lawyee.apppublic.ui.frag.fragService.jamed.JamedThreeFragment;
import com.lawyee.apppublic.ui.frag.fragService.jamed.JamedTwoFragment;
import com.lawyee.apppublic.ui.frag.fragService.media.MediaFourFragment;
import com.lawyee.apppublic.ui.frag.fragService.media.MediaOneFragment;
import com.lawyee.apppublic.ui.frag.fragService.media.MediaThreeFragment;
import com.lawyee.apppublic.ui.frag.fragService.media.MediaTwoFragment;
import com.lawyee.apppublic.util.ObjectToList;
import com.lawyee.apppublic.vo.JamedApplyDetailVO;

import net.lawyee.mobilelib.utils.ScreenUtils;
import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
import java.util.List;

public class ShowInfomActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 传入参数(机构id)
     */
    public static final String CSTR_EXTRA_ORGID_ID = "orgid";
    /**
     * 媒体调解类型发起人1 当事人 2调解员 3 电视台
     */
    public static final String CONTENT_PARAMETER_MEDIAAPPLYTYPE = "mediaapplytype";
    private String mMediaApplyType;

    /**
     * 是否参与媒体调解
     */
    public static final String CONTENT_PARAMETER_MEDIAFLAG = "mediaFlag";
    private boolean mMediaFlag;
    /**
     * 是否可以参加媒体调解
     */
    public static final String CONTENT_PARAMETER_MEDIACONFIRM = "mMediaConfirm";
    private int mMediaConfirm;
    /**
     * 调解状态
     */
    public static final String CONTENT_PARAMETER_STATUS = "status";


    public static final String CONTENT_PARAMETER_RECORDTIME = "recordTime";
    public static final String CONTENT_PARAMETER_PLAYTIME = "playtime";

    public static final String CONTENT_PARAMETER_TYPE = "type";
    public static final String CONTENT_PARAMETER_JAMED = "jamed";
    public static final String CONTENT_PARAMETER_MEDIA = "media";
    public static final String CONTENT_PARAMETER_JAMEDAPPLYVO = "amedapplyvo";


    private Button mBtnOne;
    private Button mBtnTwo;
    private Button mBtnThree;
    private Button mBtnFour;
    private FrameLayout mFlJamedLayuot;
    private Fragment mTtemFragment;
    private List<Fragment> mFragments;
    private List<String> mTabList;
    private Context mContext;
    private FragmentManager fm;
    private LinearLayout mLinearTab;

    private int Zero = 0;
    private int One = 1;
    private int Two = 2;
    private int Three = 3;

    public static final String statusOneBegin = "0";//未审核
    public static final String statusTwoOrgAgree = "1";//机构受理
    public static final String statusThreeOrgNoAgree = "-1";//机构不受理
    public static final String statusFourMediaAgree = "2";//媒体已受理
    public static final String statusFiveMeidaNoAgree = "-2";//媒体不受理
    public static final String statusSixFinish = "3";//调解结束
    /**
     * 按下的显示字体大小
     */
    private float PressSize = 18;
    /**
     * 正常的字体大小
     */
    private float NormalSize = 12;
    private LinearLayout mLiJamedTitle;
    private TextView mIndicatorView;
    /**
     * 默认显示提示线宽度
     */
    private int i;
    private String mOrgId;
    private String mType;
    private String mMediaStatus;
    private static boolean mTwoIsChick = false;//true 能点击 ，false 不能点击
    private static boolean mThreeIsChick = false;//true 能点击 ，false 不能点击
    private static boolean mFourIsChick = false;//true 能点击 ，false 不能点击

    private int screenWidth;
    private String mPlayTime;
    private String mRecordTime;
    private JamedApplyDetailVO mJamedDetailVo;
    private String mApplyMediaConfirm;
    private int mOrgAcceptFlag;
    private int mSuccessFlag;


    enum JamedStutas {
        One, Two, Three, Four, Five, Six;

        public static void SelectStutas(JamedStutas jamedStutas) {
            switch (jamedStutas) {
                case One://未审核状体
                    mTwoIsChick = true;
                    mThreeIsChick = false;
                    mFourIsChick = false;
                    break;
                case Two://机构已受理
                    mTwoIsChick = true;
                    mThreeIsChick = false;
                    mFourIsChick = true;
                    break;
                case Three://机构不受理
                    mTwoIsChick = true;
                    mThreeIsChick = false;
                    mFourIsChick = false;
                    break;
                case Four://媒体已受理
                    mTwoIsChick = true;
                    mThreeIsChick = true;
                    mFourIsChick = true;
                    break;
                case Five://媒体不受理
                    mTwoIsChick = true;
                    mThreeIsChick = true;
                    mFourIsChick = true;
                    break;
                case Six://调解结束
                    mTwoIsChick = true;
                    mThreeIsChick = true;
                    mFourIsChick = true;
                    break;

                default:
                    break;
            }
        }
    }

    enum MediaStutas {
        One, Two, Three, Four, Five, Six, Service;

        public static void SelectStutas(MediaStutas mediaStutas) {
            switch (mediaStutas) {
                case One://未审核状体
                    mTwoIsChick = false;
                    mThreeIsChick = false;
                    mFourIsChick = false;
                    break;
                case Two://机构已受理
                    mTwoIsChick = true;
                    mThreeIsChick = false;
                    mFourIsChick = false;
                    break;
                case Three://机构不受理
                    mTwoIsChick = false;
                    mThreeIsChick = false;
                    mFourIsChick = false;
                    break;
                case Four://媒体已受理
                    mTwoIsChick = true;
                    mThreeIsChick = true;
                    mFourIsChick = true;
                    break;
                case Five://媒体不受理
                    mTwoIsChick = true;
                    mThreeIsChick = false;
                    mFourIsChick = false;
                    break;
                case Six://调解结束
                    mTwoIsChick = true;
                    mThreeIsChick = true;
                    mFourIsChick = true;
                    break;
                case Service://无录制信息
                    mTwoIsChick = true;
                    mThreeIsChick = true;
                    mFourIsChick = false;
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_show_infom);
        initView();
        HanderData();
        requestApplyDetialData();
    }

    private void HanderData() {
        Intent intent = getIntent();
        mType = intent.getStringExtra(CONTENT_PARAMETER_TYPE);
        mOrgId = intent.getStringExtra(CSTR_EXTRA_ORGID_ID);
    }

    private void initView() {
        mContext = this;
        mBtnOne = (Button) findViewById(R.id.btn_one);
        mBtnTwo = (Button) findViewById(R.id.btn_two);
        mBtnThree = (Button) findViewById(R.id.btn_three);
        mBtnFour = (Button) findViewById(R.id.btn_four);
        mFlJamedLayuot = (FrameLayout) findViewById(R.id.fl_jamed_layuot);
        mBtnOne.setOnClickListener(this);
        mBtnTwo.setOnClickListener(this);
        mBtnThree.setOnClickListener(this);
        mBtnFour.setOnClickListener(this);
        mLinearTab = (LinearLayout) findViewById(R.id.linear_tab);
        mLinearTab.setOnClickListener(this);
        mLiJamedTitle = (LinearLayout) findViewById(R.id.li_jamed_title);
        mLiJamedTitle.setOnClickListener(this);
        mIndicatorView = (TextView) findViewById(R.id.indicator_view);
        mIndicatorView.setOnClickListener(this);
    }

    private void requestApplyDetialData() {
        JamedUserService jamedUserService = new JamedUserService(mContext);
        jamedUserService.setProgressShowContent(getString(R.string.get_ing));
        jamedUserService.setShowProgress(true);
        jamedUserService.getApplyDetail(mOrgId, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                setInProgess(false);
                if (values == null || values.isEmpty()) {
                    T.showLong(mContext, getString(R.string.get_error_noeffectdata));
                    mLinearTab.setVisibility(View.GONE);
                    return;
                }
                mJamedDetailVo = (JamedApplyDetailVO) values.get(0);
                if (mJamedDetailVo == null) {
                    mLinearTab.setVisibility(View.GONE);
                    return;
                }
                mRecordTime = mJamedDetailVo.getRecordTime();
                mPlayTime = mJamedDetailVo.getPlaytime();
                mMediaStatus = mJamedDetailVo.getStatus();
                mMediaConfirm = mJamedDetailVo.getMediaConfirm();
                mMediaFlag = mJamedDetailVo.isMediaFlag();
                mMediaApplyType = mJamedDetailVo.getMediaApplyType();
                mApplyMediaConfirm = mJamedDetailVo.getApplyMediaConfirm();
                mOrgAcceptFlag = mJamedDetailVo.getOrgAcceptFlag();
                mSuccessFlag = mJamedDetailVo.getSuccessFlag();
                bindViewData();
                mLinearTab.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(String msg, String content) {
                T.showShort(mContext, msg);
                setInProgess(false);
                mLinearTab.setVisibility(View.GONE);
            }
        });
    }


    private void bindViewData() {
        if (mType.equals(CONTENT_PARAMETER_MEDIA)) {//媒体
            selectShowMediaFragment();
            initMediaServiceData();
        } else if (mType.equals(CONTENT_PARAMETER_JAMED)) {//调解员
            selectShowJamedFragment();
            initJamedServieData();
        }
        selectBtnSet(mTwoIsChick, mThreeIsChick, mFourIsChick);
    }

    private void selectShowMediaFragment() {
        if (mMediaStatus.equals(statusOneBegin)) {//未审核
            MediaStutas.SelectStutas(MediaStutas.One);
        } else if (mMediaStatus.equals(statusTwoOrgAgree)) {//机构已受理
            if (mMediaApplyType == null) {
                MediaStutas.SelectStutas(MediaStutas.One);//无任何人发起媒体参与（包括媒体）
            } else {
                MediaStutas.SelectStutas(MediaStutas.Two);
            }
        } else if (mMediaStatus.equals(statusThreeOrgNoAgree)) {//机构不受理
            MediaStutas.SelectStutas(MediaStutas.Three);
        } else if (mMediaStatus.equals(statusFourMediaAgree)) {//媒体已受理
            //最终是否调解成功
            if (mApplyMediaConfirm != null && mApplyMediaConfirm.equals("1")) {
                if (TextUtils.isEmpty(mRecordTime)) {
                    MediaStutas.SelectStutas(MediaStutas.Service);
                } else if (!TextUtils.isEmpty(mRecordTime) && TextUtils.isEmpty(mPlayTime)) {
                    MediaStutas.SelectStutas(MediaStutas.Four);
                } else {
                    MediaStutas.SelectStutas(MediaStutas.Four);
                }
            } else if (mApplyMediaConfirm != null && mApplyMediaConfirm.equals("-1")) {
                MediaStutas.SelectStutas(MediaStutas.Five);
            } else {
                MediaStutas.SelectStutas(MediaStutas.Four);
            }
        } else if (mMediaStatus.equals(statusFiveMeidaNoAgree)) {//媒体不受理
            MediaStutas.SelectStutas(MediaStutas.Five);
        } else if (mMediaStatus.equals(statusSixFinish)) {//调解结束
            if (mOrgAcceptFlag == 1 && mMediaConfirm == 0) {//机构受理
                mMediaStatus = statusTwoOrgAgree;
                if (mMediaApplyType == null) {
                    MediaStutas.SelectStutas(MediaStutas.One);//无任何人发起媒体参与（包括媒体）
                } else {
                    MediaStutas.SelectStutas(MediaStutas.Two);
                }
            } else if (mOrgAcceptFlag == -1 && mMediaConfirm == 0) {//机构不受理
                mMediaStatus = statusThreeOrgNoAgree;
                MediaStutas.SelectStutas(MediaStutas.Three);
            } else if (mMediaConfirm == 1) {//媒体受理
                //最终是否调解成功
                mMediaStatus = statusFourMediaAgree;
                if (mApplyMediaConfirm != null && mApplyMediaConfirm.equals("1")) {
                    if (TextUtils.isEmpty(mRecordTime)) {
                        MediaStutas.SelectStutas(MediaStutas.Service);
                    } else if (!TextUtils.isEmpty(mRecordTime) && TextUtils.isEmpty(mPlayTime)) {
                        MediaStutas.SelectStutas(MediaStutas.Four);
                    } else {
                        MediaStutas.SelectStutas(MediaStutas.Six);
                    }
                } else if (mApplyMediaConfirm != null && mApplyMediaConfirm.equals("-1")) {
                    MediaStutas.SelectStutas(MediaStutas.Five);
                } else {
                    MediaStutas.SelectStutas(MediaStutas.Four);
                }
            } else if (mMediaConfirm == -1) {//媒体不受理
                mMediaStatus = statusFiveMeidaNoAgree;
                MediaStutas.SelectStutas(MediaStutas.Five);
            } else if (mApplyMediaConfirm.equals("-1")) {//最终没有同意媒体调解
                MediaStutas.SelectStutas(MediaStutas.Five);
            } else {
                MediaStutas.SelectStutas(MediaStutas.Six);
            }
        }
    }

    private void selectShowJamedFragment() {
        if (mMediaStatus.equals(statusOneBegin)) {//未审核
            JamedStutas.SelectStutas(JamedStutas.One);
        } else if (mMediaStatus.equals(statusTwoOrgAgree)) {//机构已受理
            if (mMediaApplyType != null && mMediaApplyType.equals("3")) {
                JamedStutas.SelectStutas(JamedStutas.Four);
            } else {
                JamedStutas.SelectStutas(JamedStutas.Two);
            }
        } else if (mMediaStatus.equals(statusThreeOrgNoAgree)) {//机构不受理

            JamedStutas.SelectStutas(JamedStutas.Three);
        } else if (mMediaStatus.equals(statusFourMediaAgree)) {//媒体已受理
            if (mSuccessFlag == 1 || mSuccessFlag == -1) {
                mMediaStatus = statusSixFinish;
                JamedStutas.SelectStutas(JamedStutas.Four);
            } else if (mSuccessFlag == 0) {
                JamedStutas.SelectStutas(JamedStutas.Four);
            }
        } else if (mMediaStatus.equals(statusFiveMeidaNoAgree)) {//媒体不受理
            JamedStutas.SelectStutas(JamedStutas.Five);
        } else if (mMediaStatus.equals(statusSixFinish)) {//调解结束
            if (mMediaConfirm == 0) {
                JamedStutas.SelectStutas(JamedStutas.Two);
            } else
                JamedStutas.SelectStutas(JamedStutas.Six);
        }
    }

    private void initMediaServiceData() {
        if (mTabList == null) {
            mTabList = new ArrayList<>();
            mTabList = ObjectToList.ArrayToList(mContext, R.array.MediaServicetab);
        } else {
            mTabList.clear();
            mTabList = ObjectToList.ArrayToList(mContext, R.array.jamedServicetab);
        }
        setTabName();
        if (mFragments == null) {
            mFragments = new ArrayList<>();
        } else {
            mFragments.clear();
        }
        MediaOneFragment oneFragment = MediaOneFragment.newInstance(mOrgId, mJamedDetailVo, mMediaStatus);

        MediaTwoFragment twoFragment = MediaTwoFragment.newInstance(mOrgId, mMediaStatus, mMediaApplyType, mMediaFlag, mJamedDetailVo);

        MediaThreeFragment threeFragment = MediaThreeFragment.newInstance(mOrgId, mMediaStatus, mJamedDetailVo);

        MediaFourFragment fourFragment = MediaFourFragment.newInstance(mOrgId, mJamedDetailVo);
        mFragments.add(oneFragment);
        mFragments.add(twoFragment);
        mFragments.add(threeFragment);
        mFragments.add(fourFragment);
        addFragment();
    }


    private void initJamedServieData() {
        if (mTabList == null) {
            mTabList = new ArrayList<>();
            mTabList = ObjectToList.ArrayToList(mContext, R.array.jamedServicetab);
        } else {
            mTabList.clear();
            mTabList = ObjectToList.ArrayToList(mContext, R.array.jamedServicetab);
        }
        setTabName();
        if (mFragments == null) {
            mFragments = new ArrayList<>();
        } else {
            mFragments.clear();
        }
        JamedOneFragment oneFragment = JamedOneFragment.newInstance(mOrgId, mJamedDetailVo);

        JamedTwoFragment twoFragment = JamedTwoFragment.newInstance(mMediaFlag, mOrgId, mMediaStatus, mJamedDetailVo);

        JamedThreeFragment threeFragment = JamedThreeFragment.newInstance(mOrgId, mMediaApplyType, mMediaStatus, mMediaFlag, mJamedDetailVo);

        JamedFourFragment fourFragment = JamedFourFragment.newInstance(mOrgId, mMediaStatus, mJamedDetailVo);
        mFragments.add(oneFragment);
        mFragments.add(twoFragment);
        mFragments.add(threeFragment);
        mFragments.add(fourFragment);
        addFragment();


    }


    private void addFragment() {
        fm = getSupportFragmentManager();

        for (int i = 0; i < mFragments.size(); i++) {
            fm.beginTransaction().add(R.id.fl_jamed_layuot, mFragments.get(i)).hide(mFragments.get(i)).commit();
        }
        fm.beginTransaction().show(mFragments.get(0)).commit();
        selectFragment(0);
        screenWidth = ScreenUtils.getScreenWidth(mContext);
        i = screenWidth / 4;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mIndicatorView.getLayoutParams();
        params.width = i;
        mIndicatorView.setLayoutParams(params);
    }

    private void setTabName() {
        for (int i = 0; i < mLinearTab.getChildCount(); i++) {
            Button btn = (Button) mLinearTab.getChildAt(i);
            btn.setText(mTabList.get(i));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_one:
                selectFragment(0);
                break;
            case R.id.btn_two:
                selectFragment(1);
                break;
            case R.id.btn_three:
                selectFragment(2);
                break;
            case R.id.btn_four:
                selectFragment(3);
                break;
        }
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

    private void selectFragment(int position) {
        for (int i = 0; i < mFragments.size(); i++) {
            Fragment fragment = mFragments.get(i);
            boolean visible = fragment.isVisible();
            if (visible) {
                switchFragment(fragment, mFragments.get(position));
            }
        }
        setAnimator(position);

    }

    private void setAnimator(int position) {
        int press = R.color.color_hei;
        int normal = R.color.red_org;
        mBtnOne.setTextSize(position == Zero ? PressSize : NormalSize);
        mBtnTwo.setTextSize(position == One ? PressSize : NormalSize);
        mBtnThree.setTextSize(position == Two ? PressSize : NormalSize);
        mBtnFour.setTextSize(position == Three ? PressSize : NormalSize);
        int width = i;
        float two = mBtnTwo.getX();
        int twoWith = mBtnTwo.getWidth();
        float three = mBtnThree.getX();
        int threeWidth = mBtnThree.getWidth();
        float four = mBtnFour.getX();
        int fourWith = mBtnFour.getWidth();
        float mover = 0;
        switch (position) {
            case 0:
                mover = 0;
                break;
            case 1:
                mover = two;
                width = twoWith;
                break;
            case 2:
                mover = three;
                width = (int) threeWidth;
                break;
            case 3:
                mover = four;
                width = (int) (screenWidth - four);
                break;
            default:
                mover = width;
                break;
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mIndicatorView.getLayoutParams();
        params.width = (int) width;
        mIndicatorView.setLayoutParams(params);
        float translationX = mIndicatorView.getTranslationX();
        ObjectAnimator animator = ObjectAnimator.ofFloat(mIndicatorView, "translationX", translationX, mover);
        animator.setDuration(300);
        animator.start();
        final float finalMover = mover;
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIndicatorView.setX(finalMover);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mBtnOne.setTextColor(position == Zero ? getResources().getColor(normal) : getResources().getColor(press));
        if (mTwoIsChick) {
            mBtnTwo.setTextColor(position == One ? getResources().getColor(normal) : getResources().getColor(press));
        }
        if (mThreeIsChick) {
            mBtnThree.setTextColor(position == Two ? getResources().getColor(normal) : getResources().getColor(press));
        }
        if (mFourIsChick) {
            mBtnFour.setTextColor(position == Three ? getResources().getColor(normal) : getResources().getColor(press));
        }
    }

    /**
     * @param two
     * @param three
     * @param four
     */
    public void selectBtnSet(boolean two, boolean three, boolean four) {
        mBtnTwo.setClickable(two);
        mBtnThree.setClickable(three);
        mBtnFour.setClickable(four);
        mBtnTwo.setTextColor(two ? getResources().getColor(R.color.color_hei) : getResources().getColor(R.color.btn_focused_false));
        mBtnThree.setTextColor(three ? getResources().getColor(R.color.color_hei) : getResources().getColor(R.color.btn_focused_false));
        mBtnFour.setTextColor(four ? getResources().getColor(R.color.color_hei) : getResources().getColor(R.color.btn_focused_false));
        mTwoIsChick = two;
        mThreeIsChick = three;
        mFourIsChick = four;
    }

    /**
     * @param three
     * @param four
     */
    public void selectNextBtnSet(boolean three, boolean four) {
        mBtnThree.setClickable(three);
        mBtnFour.setClickable(four);
        mBtnThree.setTextColor(three ? getResources().getColor(R.color.color_hei) : getResources().getColor(R.color.btn_focused_false));
        mBtnFour.setTextColor(four ? getResources().getColor(R.color.color_hei) : getResources().getColor(R.color.btn_focused_false));
        mThreeIsChick = three;
        mFourIsChick = four;
    }

    /**
     * @param four
     */
    public void selectNextFourBtnSet(boolean four) {
        mBtnFour.setClickable(four);
        mBtnFour.setTextColor(four ? getResources().getColor(R.color.color_hei) : getResources().getColor(R.color.btn_focused_false));
        mFourIsChick = four;
    }

}
