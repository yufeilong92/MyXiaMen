package com.lawyee.apppublic.ui.lawyerService;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.ui.frag.LawyerListFragment;
import com.lawyee.apppublic.util.LawFirmMap;
import com.lawyee.apppublic.util.SerializableHashMap;
import com.lawyee.apppublic.vo.JalawFilterVO;

import net.lawyee.mobilelib.vo.BaseVO;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.SAVEAREA;
import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.SAVEFIELD;
import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.SAVELAWFIRMAREA;
import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.SAVELAWFIRMSERVICE;
import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.SAVEONLINE;
import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.SAVEOPERATION;
import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.SAVESERVICE;
import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.SEARCHLAWFIRMNAME;
import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.SEARCHLAWNAME;
import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.SEARCHMAP;

/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  标题
 * @Package com.lawyee.apppublic.ui.lawyerService
 * @Description:    律师服务页面
 * @author:czq
 * @date:   2017/5/31
 * @version
 * @verdescript   2017/5/31  czq 初建
 * @Copyright: 2017/5/31 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class LawServiceActivity extends BaseActivity {
    public static String TY = "mType";
    public static String FTO = "mFromTo";
    public static final int TOSCREEN = 10006;
    private Context mContext;
    private TextView mTv_lawyer;
    private TextView mTv_law_office;
    private FrameLayout mFl_content;
    Fragment mTempFragment = null;
    private FragmentManager mFm;
    private ArrayList<Fragment> mFragments;
    private int mType = 0;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_law_service);

        mContext = this;
        mTv_lawyer = (TextView) findViewById(R.id.tv_lawyer);
        mTv_law_office = (TextView) findViewById(R.id.tv_lawyer_office);
        mFl_content = (FrameLayout) findViewById(R.id.fl_content);
        switchTab(0);
        initData();
        mTv_lawyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mType != 0) {
                    mType = 0;
                    switchTab(0);
                    switchFragment(mFragments.get(1), mFragments.get(0));
                }
            }
        });
        mTv_law_office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mType != 1) {
                    mType = 1;
                    switchTab(1);
                    switchFragment(mFragments.get(0), mFragments.get(1));
                }
            }
        });
    }

    private void initData() {
        mFragments = new ArrayList<>();
        LawyerListFragment lawyerlist = new LawyerListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TY, 0);
        bundle.putInt(FTO, 0);
        lawyerlist.setArguments(bundle);
        LawyerListFragment lawofficelist = new LawyerListFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt(TY, 1);
        bundle1.putInt(FTO, 0);
        lawofficelist.setArguments(bundle1);
        mFragments.add(lawyerlist);
        mFragments.add(lawofficelist);
        mFm = getSupportFragmentManager();
        for (int i = 0; i < mFragments.size(); i++) {
            FragmentTransaction beginTransaction = mFm.beginTransaction();
            beginTransaction.addToBackStack(null);
            beginTransaction.add(R.id.fl_content, mFragments.get(i)).hide(mFragments.get(i)).commit();
        }
        mFm.beginTransaction().show(lawyerlist).commit();
    }


    private void switchTab(int type) {
        if (type == 0) {
            mTv_lawyer.setSelected(true);
            mTv_law_office.setSelected(false);
        } else {
            mTv_lawyer.setSelected(false);
            mTv_law_office.setSelected(true);
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
    protected void onResume() {
        super.onResume();
    }

    public void onToolbarClick(View view) {
        Intent intent = new Intent(mContext, ScreenMuchActivity.class);
        intent.putExtra(TY, mType);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == TOSCREEN) {
            Bundle bundle = data.getExtras();
            SerializableHashMap serializableHashMap = (SerializableHashMap) bundle.get(SEARCHMAP);
            if (mType == 0) {
                EventBus.getDefault().post(new SerializableHashMap(serializableHashMap.getMap()));
            } else if (mType == 1) {
                EventBus.getDefault().post(new LawFirmMap(serializableHashMap.getMap()));
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            LawServiceActivity.this.finish();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        BaseVO.saveVO(null, JalawFilterVO.dataFileName(mContext, SEARCHLAWFIRMNAME));
        BaseVO.saveVO(null, JalawFilterVO.dataFileName(mContext, SEARCHLAWNAME));
        BaseVO.saveVO(null, JalawFilterVO.dataFileName(mContext, SAVELAWFIRMAREA));
        BaseVO.saveVO(null, JalawFilterVO.dataFileName(mContext, SAVELAWFIRMSERVICE));
        BaseVO.saveVO(null, JalawFilterVO.dataFileName(mContext, SAVEAREA));
        BaseVO.saveVO(null, JalawFilterVO.dataFileName(mContext, SAVESERVICE));
        BaseVO.saveVO(null, JalawFilterVO.dataFileName(mContext, SAVEFIELD));
        BaseVO.saveVO(null, JalawFilterVO.dataFileName(mContext, SAVEOPERATION));
        BaseVO.saveVO(null, JalawFilterVO.dataFileName(mContext, SAVEONLINE));
        super.onDestroy();
    }


}
