package com.lawyee.apppublic.ui.org;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.AreaSelRlvAdapter;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.vo.BaseCommonDataVO;
import com.lawyee.apppublic.vo.JaaidApplyAreaeEven;

import net.lawyee.mobilelib.utils.T;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawerApp
 * @Package com.lawyee.apppublic.ui
 * @Description: 地区选择界面
 * @author: YFL
 * @date: 2017/6/4 16:57
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JaaidAreaSelActivity extends BaseActivity {
    /**
     * 传递 listdata 参数
     */
    public static final String CSTR_EXTRA_TYPE = "type";


    private EditText mEtArea;
    private ImageView mIvClearArea;
    private RecyclerView mRvArea;
    private AreaSelRlvAdapter mAdapter;
    private int mNowSelArea = -1;//未选择

    private int mAgainSelArea = -1;//有输入未选择
    private List<BaseCommonDataVO> mData = new ArrayList<>();//筛选数据集合
    private List<BaseCommonDataVO> mAreaLists = new ArrayList<>();//全部数据集合
    private Button mBtnApplySure;

    private boolean mFiterClick = true;//首次点击

    public int getmNowSelArea() {
        return mNowSelArea;
    }

    public void setmNowSelArea(int mNowSelArea) {
        this.mNowSelArea = mNowSelArea;
    }

    public int getmAgainSelArea() {
        return mAgainSelArea;
    }

    public void setmAgainSelArea(int mAgainSelArea) {
        this.mAgainSelArea = mAgainSelArea;
    }

    public List<BaseCommonDataVO> getmAreaLists() {
        return mAreaLists;
    }

    public void setmAreaLists(List<BaseCommonDataVO> mAreaLists) {
        this.mAreaLists = mAreaLists;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_areasel);
        Intent intent = getIntent();
        initView();
        initCityData();
        if (mAreaLists.isEmpty() || mAreaLists == null) {
            T.showLong(this,"缺少无区域数据信息");
            return;
        }
        setReclcyerAdapter(mAreaLists);
        handlerSelFilter();
        int intExtra = intent.getIntExtra(CSTR_EXTRA_TYPE, -1);
        if (getmNowSelArea() == -1) {
            mAdapter.setNowSelItem(intExtra, false);
            setmNowSelArea(intExtra);
        }


    }

    /**
     * 初始化所有区县
     */
    private void initCityData() {
        List<BaseCommonDataVO> dataVOs = BaseCommonDataVO.getDataVOListWithParentId(ApplicationSet.getInstance().getAreas(), Constants.PROVINCE_GUIZHOU_ID);
        if (dataVOs!=null&&!dataVOs.isEmpty()){
            setmAreaLists(dataVOs);
        }
    }

    private void handlerSelFilter() {
        mEtArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String trim = s.toString().trim();
                setFilter(trim);
            }
        });
    }

    /**
     * 筛选处理
     *
     * @param trim
     */
    private void setFilter(String trim) {
        if(mData==null||mAreaLists==null)
            return;
        if (mData.size() > 0) {
            mData.clear();
        }
        for (BaseCommonDataVO str : mAreaLists) {
            boolean isEquest = str.getName().contains(trim);
            if (isEquest) {
                mData.add(str);
            }
        }
        setmNowSelArea(-1);
        mAdapter.setNowSelItem(getmNowSelArea(), false);
        mAdapter.setNowData(mData);
    }


    private void setReclcyerAdapter(List<BaseCommonDataVO> list) {
        mAdapter = new AreaSelRlvAdapter(list, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvArea.setLayoutManager(gridLayoutManager);
        mRvArea.setAdapter(mAdapter);
        mAdapter.setOnItemListener(new AreaSelRlvAdapter.OnRecycleViewListener() {
            @Override
            public void onItemListener(View view, BaseCommonDataVO vo, int position) {
                Log.d(TAG, "onItemListener: " + position + "///" + getmNowSelArea() + mFiterClick);
                if (mFiterClick) {//首次点击
                    if (mEtArea.getText().toString().isEmpty()) {//输入无内容
                        if (position == getmNowSelArea()) {
                            mAdapter.setNowSelItem(position, true);
                            mFiterClick = false;
                        } else {
                            mAdapter.setNowSelItem(position, false);
                        }
                    } else {//输有内容
                        if (position == getmAgainSelArea()) {
                            mAdapter.setNowSelItem(position, true);
                            mFiterClick = false;
                        } else {
                            mAdapter.setNowSelItem(position, false);
                        }
                        handlerAgainSel(position);
                    }
                } else {//再次次点击
                    mAdapter.setNowSelItem(position, false);
                    mFiterClick = true;
                }
                handlerDatas(vo);
            }
        });

    }
    private void handlerAgainSel(int position) {
        setmAgainSelArea(position);
    }

    /**
     * 处理选中的数据
     *
     * @param vo
     */
    private void handlerDatas(BaseCommonDataVO vo) {
        for (int i = 0; i < mAreaLists.size(); i++) {
            if (mAreaLists.get(i).getOid().equals(vo.getOid())) {
                setmNowSelArea(i);
            }
        }
    }


    private void initView() {
        mEtArea = (EditText) findViewById(R.id.et_area);
        mIvClearArea = (ImageView) findViewById(R.id.iv_clear_area);
        mRvArea = (RecyclerView) findViewById(R.id.rv_area);
        mBtnApplySure = (Button) findViewById(R.id.btn_apply_sure);
        mIvClearArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mEtArea.getText().toString().isEmpty()) {
                    mEtArea.setText("");
                }
            }
        });
        mBtnApplySure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getmNowSelArea() == -1) {
                    T.showShort(getApplicationContext(), "" + getResources().getString(R.string.apply_sel_area));
                } else {
                    EventBus.getDefault().post(new JaaidApplyAreaeEven(getmNowSelArea(), getmAreaLists().get(getmNowSelArea())));
                    finish();
                }
            }
        });
    }



}
