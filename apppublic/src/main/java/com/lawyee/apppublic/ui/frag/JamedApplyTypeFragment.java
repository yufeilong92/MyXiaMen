package com.lawyee.apppublic.ui.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.ApplyListPopAdapter;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.config.DataManage;
import com.lawyee.apppublic.vo.BaseCommonDataVO;
import com.lawyee.apppublic.vo.JamedApplyDetailVO;
import com.lawyee.apppublic.vo.JamedApplyInfomVo;
import com.lawyee.apppublic.vo.JamedApplyThreeIsNextEven;
import com.lawyee.apppublic.vo.JamedApplyThreeSubmitEven;
import com.lawyee.apppublic.vo.UserVO;

import net.lawyee.mobilelib.utils.StringUtil;
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
 * @Title: JamedApplyTypeFragment.java
 * @Package com.lawyee.apppublic.ui.frag
 * @Description: 人民调解申请填写页
 * @author: YFL
 * @date: 2017/6/12 15:23
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/6/12 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JamedApplyTypeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = JamedApplyTypeFragment.class.getSimpleName();
    /**
     * 申请人民族标示
     */
    public static final String IDNATION = "idNatuin";

    /**
     * 被申请人民族标示
     */
    public static final String QUILTIDNATION = "quiltidNatuin";

    /**
     * 民族集合
     */
    private List<String> mApplyNationLists = new ArrayList<>();
    private JamedApplyInfomVo mInformations;
    private MaterialDialog mShow;
    private EditText mEtJaemdApplyName;
    private RadioButton mRadioJamedGirl;
    private RadioButton mRadioJamedBoy;
    private EditText mEtJamedIdNumber;
    private EditText mEtJamedAge;
    private TextView mTvJamedNation;
    private EditText mEtJamedPhone;
    private EditText mEtJamedHere;
    private EditText mEtJamedNexus;
    private EditText mEtJamedQuiltName;
    private EditText mEtJamedQuiltAge;
    private TextView mTvJamedQuiltNation;
    private EditText mEtJamedQuiltPhone;
    private EditText mEtJamedQuiltHere;
    /**
     * 设置性别
     */
    private String mSelectSex;
    /**
     * 设置被申请人性别
     */
    private String mSelectQuiltSex;
    private RadioButton mRadioJamedQuiltGril;
    private RadioButton mRadioJamedQuiltBoy;

    public String getSelectQuiltSex() {
        return mSelectQuiltSex;
    }

    public void setSelectQuiltSex(String selectQuiltSex) {
        mSelectQuiltSex = selectQuiltSex;
    }

    public String getSelectSex() {
        return mSelectSex;
    }

    public void setSelectSex(String selectSex) {
        mSelectSex = selectSex;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jamed_type, container, false);
        initView(view);
        initNationDatas();
        initUserData();
        initCacheData();
        return view;
    }

    private void initUserData() {
        UserVO userVO = ApplicationSet.getInstance().getUserVO();
        if (userVO == null)
            return;
        String realName = userVO.getRealName();
        if (!isEmpty(realName))
            mEtJaemdApplyName.setText(realName);
        String sex = userVO.getGender();
        if (!StringUtil.isEmpty(sex)) {
            setSelectSex(sex);
            if (sex.equals(Constants.APPLYGIRL)) {
                mRadioJamedGirl.setChecked(true);
            } else if (sex.equals(Constants.APPLYBOY)) {
                mRadioJamedBoy.setChecked(true);
            } else if (sex.equals("男")) {
                mRadioJamedBoy.setChecked(true);
            } else if (sex.equals("女")) {
                mRadioJamedGirl.setChecked(true);
            }
        }
        String mobile = userVO.getMobile();
        if (!isEmpty(mobile))
            mEtJamedPhone.setText(mobile);
        String idCard = userVO.getIdCard();
        if (!isEmpty(idCard))
            mEtJamedIdNumber.setText(idCard);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        handlerData();
    }

    private void handlerData() {
        mRadioJamedGirl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setSelectSex(Constants.APPLYGIRL);
                }
            }
        });
        mRadioJamedBoy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setSelectSex(Constants.APPLYBOY);
                }
            }
        });
        mRadioJamedQuiltBoy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setSelectQuiltSex(Constants.APPLYBOY);
                }
            }
        });
        mRadioJamedQuiltGril.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setSelectQuiltSex(Constants.APPLYGIRL);
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(JamedApplyThreeIsNextEven event) {
        JamedApplyDetailVO vo = event.getJamedApplyDataVo();
        if (event.isNext()) {
            submit(vo);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 初始化民族
     */
    private void initNationDatas() {
        if (mInformations == null)
            mInformations = new JamedApplyInfomVo();
        List<BaseCommonDataVO> applyNationDatas = DataManage.getInstance().getApplyNationDatas();
        if (applyNationDatas != null && !applyNationDatas.isEmpty()) {
            mInformations.setNationDatas(applyNationDatas);
            mInformations.setQuiltNationDatas(applyNationDatas);
            for (BaseCommonDataVO dvo : applyNationDatas
                    ) {
                mApplyNationLists.add(dvo.getName());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_jamed_quilt_nation://被申请人民族
                String quiltnation = getTextStr(mTvJamedQuiltNation);
                handlerPopWindos(mTvJamedQuiltNation, mApplyNationLists, quiltnation, QUILTIDNATION);
                break;
            case R.id.tv_jamed_nation://申请人民族
                String nation = getTextStr(mTvJamedNation);
                handlerPopWindos(mTvJamedNation, mApplyNationLists, nation, IDNATION);
                break;
            default:
                break;
        }
    }

    private String getTextStr(TextView tv) {
        return tv.getText().toString().trim();
    }

    private void handlerPopWindos(final TextView textView, final List<String> mData, String nation, final String type) {
        ApplyListPopAdapter applyPopAdapter = handlerPopWindosAdapter(mData, nation);
        applyPopAdapter.setOnRecyclerItemClickListener(new ApplyListPopAdapter.OnRecyclerItemClickListener() {
            @Override
            public void OnItemClickListener(View view, String item, int position) {
                if (type.equals(IDNATION)) {//申请人民族
                    mInformations.setNowSelApplyNatione(position);
                } else if (type.equals(QUILTIDNATION)) {//被申请人民族
                    mInformations.setNowSelApplyQuiltNation(position);
                }
                textView.setText(item);
                mShow.dismiss();
            }
        });
    }

    private void initView(View view) {
        mEtJaemdApplyName = (EditText) view.findViewById(R.id.et_jaemd_apply_name);
        mRadioJamedGirl = (RadioButton) view.findViewById(R.id.radio_jamed_girl);
        mRadioJamedBoy = (RadioButton) view.findViewById(R.id.radio_jamed_boy);
        mEtJamedIdNumber = (EditText) view.findViewById(R.id.et_jamed_id_number);
        mEtJamedAge = (EditText) view.findViewById(R.id.et_jamed_age);
        mTvJamedNation = (TextView) view.findViewById(R.id.tv_jamed_nation);
        mEtJamedPhone = (EditText) view.findViewById(R.id.et_jamed_phone);
        mEtJamedHere = (EditText) view.findViewById(R.id.et_jamed_here);
        mEtJamedNexus = (EditText) view.findViewById(R.id.et_jamed_nexus);
        mEtJamedQuiltName = (EditText) view.findViewById(R.id.et_jamed_quilt_name);
        mEtJamedQuiltAge = (EditText) view.findViewById(R.id.et_jamed_quilt_age);
        mTvJamedQuiltNation = (TextView) view.findViewById(R.id.tv_jamed_quilt_nation);
        mEtJamedQuiltPhone = (EditText) view.findViewById(R.id.et_jamed_quilt_phone);
        mEtJamedQuiltHere = (EditText) view.findViewById(R.id.et_jamed_quilt_here);
        mRadioJamedQuiltGril = (RadioButton) view.findViewById(R.id.radio_jamed_quilt_gril);
        mRadioJamedQuiltGril.setOnClickListener(this);
        mRadioJamedQuiltBoy = (RadioButton) view.findViewById(R.id.radio_jamed_quilt_boy);
        mRadioJamedQuiltBoy.setOnClickListener(this);
        mTvJamedQuiltNation.setOnClickListener(this);
        mTvJamedNation.setOnClickListener(this);
    }

    /**
     * 从缓存中读取上次填写的数据
     */
    private void initCacheData() {
        JamedApplyDetailVO cachedata = (JamedApplyDetailVO) JamedApplyDetailVO.loadVO(JamedApplyDetailVO.cacheDataFileName(getActivity()));
        if (cachedata == null)
            return;
        if (!StringUtil.isEmpty(cachedata.getApplyName())) {
            mEtJaemdApplyName.setText(cachedata.getApplyName());
        }
        if (!StringUtil.isEmpty(cachedata.getApplyGender())) {
            setSelectSex(cachedata.getApplyGender());
            if (Constants.APPLYBOY.equals(cachedata.getApplyGender())) {
                mRadioJamedGirl.setChecked(false);
                mRadioJamedBoy.setChecked(true);
            } else if (Constants.APPLYGIRL.equals(cachedata.getApplyGender())) {
                mRadioJamedBoy.setChecked(false);
                mRadioJamedGirl.setChecked(true);
            }
        }
        if (!StringUtil.isEmpty(cachedata.getApplyIdCard())) {
            mEtJamedIdNumber.setText(cachedata.getApplyIdCard());
        }
        if (!StringUtil.isEmpty(cachedata.getApplyAge())) {
            mEtJamedAge.setText(cachedata.getApplyAge());
        }
        if (!StringUtil.isEmpty(cachedata.getApplyNation())) {
            //民族
            BaseCommonDataVO nation = BaseCommonDataVO.findDataVOWithOid(mInformations.getNationDatas(),
                    cachedata.getApplyNation());
            if (nation != null) {
                int index = BaseCommonDataVO.findIndexWithOid(mInformations.getNationDatas(), cachedata.getApplyNation());
                mInformations.setNowSelApplyNatione(index);
                mTvJamedNation.setText(nation.getName());
            }
        }
        if (!StringUtil.isEmpty(cachedata.getApplyTelephone())) {
            mEtJamedPhone.setText(cachedata.getApplyTelephone());
        }
        if (!StringUtil.isEmpty(cachedata.getApplyAddress())) {
            mEtJamedHere.setText(cachedata.getApplyAddress());
        }
        //TODO 以下可以后续移除
        /*if(!StringUtil.isEmpty(cachedata.getRelation()))
        {
            mEtJamedNexus.setText(cachedata.getRelation());
        }
        if(!StringUtil.isEmpty(cachedata.getBeApplyName()))
        {
            mEtJamedQuiltName.setText(cachedata.getBeApplyName());
        }

        if(!StringUtil.isEmpty(cachedata.getBeApplyGender()))
        {
            setSelectQuiltSex(cachedata.getBeApplyGender());
            if(Constants.APPLYBOY.equals(cachedata.getBeApplyGender()))
            {
                mRadioJamedQuiltGril.setChecked(false);
                mRadioJamedQuiltBoy.setChecked(true);
            }else if(Constants.APPLYGIRL.equals(cachedata.getBeApplyGender()))
            {
                mRadioJamedQuiltBoy.setChecked(false);
                mRadioJamedQuiltGril.setChecked(true);
            }
        }
        if(!StringUtil.isEmpty(cachedata.getBeApplyAge()))
        {
            mEtJamedQuiltAge.setText(cachedata.getBeApplyAge());
        }
        if(!StringUtil.isEmpty(cachedata.getBeApplyNation()))
        {
            //民族
            BaseCommonDataVO nation = BaseCommonDataVO.findDataVOWithOid(mInformations.getQuiltNationDatas(),
                    cachedata.getBeApplyNation());
            if(nation!=null)
            {
                int index = BaseCommonDataVO.findIndexWithOid(mInformations.getQuiltNationDatas(),cachedata.getBeApplyNation());
                mInformations.setNowSelApplyQuiltNation(index);
                mTvJamedQuiltNation.setText(nation.getName());
            }
        }

        if(!StringUtil.isEmpty(cachedata.getBeApplyTelephone()))
        {
            mEtJamedQuiltPhone.setText(cachedata.getBeApplyTelephone());
        }
        if(!StringUtil.isEmpty(cachedata.getBeApplyAddress()))
        {
            mEtJamedQuiltHere.setText(cachedata.getBeApplyAddress());
        }*/
    }

    private void submit(JamedApplyDetailVO vo) {
        // validate
        String name = mEtJaemdApplyName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getContext(), R.string.nameIsempty, Toast.LENGTH_SHORT).show();
            return;
        }
        vo.setApplyName(name);
        if (TextUtils.isEmpty(getSelectSex())) {
            T.showShort(getContext(), R.string.sexIsempty);
            return;
        }
        vo.setApplyGender(getSelectSex());
        String number = mEtJamedIdNumber.getText().toString().trim();
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(getContext(), R.string.IdCardIsEmpty, Toast.LENGTH_SHORT).show();
            return;
        }

        boolean validIdCard = StringUtil.validateidCard(number);
        if (validIdCard) {
            vo.setApplyIdCard(number);
        } else {
            T.showShort(getContext(), getString(R.string.pleaseIdCardIsRight));
            return;
        }
        String age = mEtJamedAge.getText().toString().trim();
        if (TextUtils.isEmpty(age)) {
            Toast.makeText(getContext(), R.string.ageIsEmpty, Toast.LENGTH_SHORT).show();
            return;
        }
        vo.setApplyAge(age);

        String nation = mTvJamedNation.getText().toString().trim();
        if (TextUtils.isEmpty(nation)) {
            Toast.makeText(getContext(), R.string.nationIsempty, Toast.LENGTH_SHORT).show();
            return;
        }
        String name1 = mInformations.getNationDatas().get(mInformations.getNowSelApplyNatione()).getName();
        Log.d(TAG, "submit: " + name1);
        vo.setApplyNation(mInformations.getNationDatas().get(mInformations.getNowSelApplyNatione()).getOid());
        String phone = mEtJamedPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(getContext(), R.string.contactPhotoIsEmpty, Toast.LENGTH_SHORT).show();
            return;
        }
        boolean numberRight = StringUtil.validateMoblie(phone);
        if (numberRight) {
            vo.setApplyTelephone(phone);
        } else {
            T.showShort(getContext(), R.string.pleasePhotoIsRight);
            return;
        }

        String here = mEtJamedHere.getText().toString().trim();
        if (TextUtils.isEmpty(here)) {
            T.showShort(getContext(), "申请人的单位或住址不能为空");
            return;
        }
        vo.setApplyAddress(here);//申请单位

        String nexus = mEtJamedNexus.getText().toString().trim();
        if (!TextUtils.isEmpty(nexus)) {
            vo.setRelation(nexus);//申请人关系
        }

        String quitlname = mEtJamedQuiltName.getText().toString().trim();
        if (TextUtils.isEmpty(quitlname)) {
            Toast.makeText(getContext(), R.string.beNameIsEmpty, Toast.LENGTH_SHORT).show();
            return;
        }
        vo.setBeApplyName(quitlname);
        if (TextUtils.isEmpty(getSelectQuiltSex())) {
            T.showShort(getContext(), getString(R.string.beSexIsEmpty));
            return;
        }
        vo.setBeApplyGender(getSelectQuiltSex());

        String quitldage = mEtJamedQuiltAge.getText().toString().trim();
        if (TextUtils.isEmpty(quitldage)) {
            Toast.makeText(getContext(), R.string.beAgeIsEmpty, Toast.LENGTH_SHORT).show();
            return;
        }
        vo.setBeApplyAge(quitldage);

        String quiltnation = mTvJamedQuiltNation.getText().toString().trim();
        if (TextUtils.isEmpty(quiltnation)) {
            Toast.makeText(getContext(), R.string.beNationIsEmpty, Toast.LENGTH_SHORT).show();
            return;
        }
        String name2 = mInformations.getQuiltNationDatas().get(mInformations.getNowSelApplyQuiltNation()).getName();
        Log.d(TAG, "submit: " + name2);
        vo.setBeApplyNation(mInformations.getQuiltNationDatas().get(mInformations.getNowSelApplyQuiltNation()).getOid());

        String quitlphone = mEtJamedQuiltPhone.getText().toString().trim();
        if (TextUtils.isEmpty(quitlphone)) {
            Toast.makeText(getContext(), R.string.bePhotoIsEmpty, Toast.LENGTH_SHORT).show();
            return;
        }
        boolean telNumber = StringUtil.validateMoblie(quitlphone);
        if (telNumber) {
            vo.setBeApplyTelephone(quitlphone);
        } else {
            T.showShort(getContext(), R.string.bePhotoIsRight);
            return;
        }

        String quitlhere = mEtJamedQuiltHere.getText().toString().trim();
        if (TextUtils.isEmpty(quitlhere)) {
            T.showShort(getContext(), "被申请人的单位或住址不能为空");
            return;
        }
        vo.setBeApplyAddress(quitlhere);//被申请人地址
        EventBus.getDefault().post(new JamedApplyThreeSubmitEven(vo, true));

    }

    private ApplyListPopAdapter handlerPopWindosAdapter(final List<String> mData, String nation) {
        ApplyListPopAdapter applyPopAdapter = new ApplyListPopAdapter(mData, getActivity());
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        if (mShow == null || !mShow.isShowing()) {
            mShow = new MaterialDialog.Builder(getActivity())
                    .adapter(applyPopAdapter, manager)
                    .show();
            mShow.getRecyclerView().addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        }
        applyPopAdapter.setSeletsStr(nation);
        return applyPopAdapter;
    }

    private boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }
}
