package com.lawyee.apppublic.ui.basiclaw;

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
import com.lawyee.apppublic.ui.frag.BasicOfficeFragment;
import com.lawyee.apppublic.ui.frag.BasicWorkerFragment;
import com.lawyee.apppublic.util.LawFirmMap;
import com.lawyee.apppublic.util.SerializableHashMap;
import com.lawyee.apppublic.vo.JalawFilterVO;

import net.lawyee.mobilelib.vo.BaseVO;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import static com.lawyee.apppublic.ui.basiclaw.BasicScreenMuchActivity.SAVESERVICEOFFICEAREA;
import static com.lawyee.apppublic.ui.basiclaw.BasicScreenMuchActivity.SAVESERVICEOFFICEFIELD;
import static com.lawyee.apppublic.ui.basiclaw.BasicScreenMuchActivity.SAVEWORKERAREA;
import static com.lawyee.apppublic.ui.basiclaw.BasicScreenMuchActivity.SAVEWORKERFIELD;
import static com.lawyee.apppublic.ui.basiclaw.BasicScreenMuchActivity.SAVEWORKERONLINE;
import static com.lawyee.apppublic.ui.basiclaw.BasicScreenMuchActivity.SEARCHSERVICEOFFICENAME;
import static com.lawyee.apppublic.ui.basiclaw.BasicScreenMuchActivity.SEARCHWOEKERMAP;
import static com.lawyee.apppublic.ui.basiclaw.BasicScreenMuchActivity.SEARCHWORKERNAME;
import static com.lawyee.apppublic.ui.lawyerService.LawServiceActivity.TOSCREEN;

/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  标题
 * @Package com.lawyee.apppublic.ui.basiclaw
 * @Description:    基层法律服务主页
 * @author:lzh
 * @date:   2017/8/9
 * @version
 * @verdescript   2017/8/9  lzh 初建
 * @Copyright: 2017/8/9 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class BasicLawServiceActivity extends BaseActivity {


    private TextView mTvSelect;
    private TextView mBasicLawfirm;
    private TextView mTvBasicLawyer;
    private FrameLayout mFlContent;
    public static String BASICTY = "BasicType";
    public static final int BASICTOSCREEN = 20006;
    Fragment mTempFragment = null;
    private FragmentManager mFm;
    private ArrayList<Fragment> mFragments;
    private int mType = 0;//选择的类型  0：服务所 1：工作者
    private Context mContext;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_basic_law_service);
        mContext = this;
        mTvBasicLawyer = (TextView) findViewById(R.id.tv_basic_lawyer);
        mBasicLawfirm = (TextView) findViewById(R.id.tv_basic_lawfirm);
        mFlContent = (FrameLayout) findViewById(R.id.fl_content);
        switchTab(0);
        initData();
        mBasicLawfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mType != 0) {
                    mType = 0;
                    switchTab(0);
                    switchFragment(mFragments.get(1), mFragments.get(0));
                }
            }
        });
        mTvBasicLawyer.setOnClickListener(new View.OnClickListener() {
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
    //切换TAB
    private void switchTab(int type) {
        if (type == 0) {
            mBasicLawfirm.setSelected(true);
            mTvBasicLawyer.setSelected(false);
        } else {
            mBasicLawfirm.setSelected(false);
            mTvBasicLawyer.setSelected(true);
        }
    }
    //初始化数据
    private void initData() {
        mFragments = new ArrayList<>();
        BasicOfficeFragment basicOfficeFragment = new BasicOfficeFragment();
        BasicWorkerFragment basicWorkerFragment= new BasicWorkerFragment();
        mFragments.add(basicOfficeFragment);
        mFragments.add(basicWorkerFragment);
        mFm = getSupportFragmentManager();
        for (int i = 0; i < mFragments.size(); i++) {
            FragmentTransaction beginTransaction = mFm.beginTransaction();
            beginTransaction.addToBackStack(null);
            beginTransaction.add(R.id.fl_content, mFragments.get(i)).hide(mFragments.get(i)).commit();
        }
        mFm.beginTransaction().show(basicOfficeFragment).commit();
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
    public void onToolbarClick(View view) {
        Intent intent = new Intent(mContext, BasicScreenMuchActivity.class);
        intent.putExtra(BASICTY, mType);
        startActivityForResult(intent, 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            BasicLawServiceActivity.this.finish();
        }
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == TOSCREEN) {
            Bundle bundle = data.getExtras();
            SerializableHashMap serializableHashMap = (SerializableHashMap) bundle.get(SEARCHWOEKERMAP);
            if (mType == 1) {
                EventBus.getDefault().post(new SerializableHashMap(serializableHashMap.getMap()));
            } else if (mType == 0) {
                EventBus.getDefault().post(new LawFirmMap(serializableHashMap.getMap()));
            }
        }
    }
    @Override
    protected void onDestroy() {
        //退出时清除缓存
            BaseVO.saveVO(null, JalawFilterVO.dataFileName(mContext, SEARCHWORKERNAME));
            BaseVO.saveVO(null, JalawFilterVO.dataFileName(mContext, SEARCHSERVICEOFFICENAME));
            BaseVO.saveVO(null, JalawFilterVO.dataFileName(mContext, SAVESERVICEOFFICEAREA));
            BaseVO.saveVO(null, JalawFilterVO.dataFileName(mContext, SAVESERVICEOFFICEFIELD));
            BaseVO.saveVO(null, JalawFilterVO.dataFileName(mContext, SAVEWORKERAREA));
            BaseVO.saveVO(null, JalawFilterVO.dataFileName(mContext, SAVEWORKERFIELD));
            BaseVO.saveVO(null, JalawFilterVO.dataFileName(mContext, SAVEWORKERONLINE));

        super.onDestroy();
    }
}
