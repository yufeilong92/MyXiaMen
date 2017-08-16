package com.lawyee.apppublic.ui.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.ApplyListPopAdapter;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.config.DataManage;
import com.lawyee.apppublic.vo.BaseCommonDataVO;
import com.lawyee.apppublic.vo.JaaidApplyDetailVO;
import com.lawyee.apppublic.vo.JaaidApplyFilterVo;
import com.lawyee.apppublic.vo.JaaidApplyTwoSubmitEven;
import com.lawyee.apppublic.vo.JaaidIsNextTwoEvent;
import com.lawyee.apppublic.vo.UserVO;

import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.T;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.lawyee.apppublic.util.ShowOrHide.showDataDialog;


/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: Apply_Information_fragment.java
 * @Package com.lawyee.apppublic.ui.frag
 * @Description:法援申请申请人信息页
 * @author: YFL
 * @date: 2017/6/2 15:30
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/6/2 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JaaidApplyInformationFragment extends Fragment implements View.OnClickListener {
    private static String TAG = JaaidApplyInformationFragment.class.getSimpleName();

    private List<String> mApplyProvicneLists = new ArrayList<>();//省
    private List<String> mApplyCityLists = new ArrayList<>();//市
    private List<String> mApplyAreaLists = new ArrayList<>();//区域
    private List<String> mApplyNationLists = new ArrayList<>();//民族
    private List<String> mApplyCertificatesLists = new ArrayList<>();//证件类型
    /**
     * 证件类型标示
     */
    public static final String IDCARD = "idcard";
    /**
     * 民族标示
     */
    public static final String IDNATION = "idNatuin";
    /**
     * 省标示
     */
    public static final String IDPROVINCE = "idprovince";
    /**
     * 市标示
     */
    public static final String IDCITY = "idcity";
    /**
     * 区县标示
     */
    public static final String IDAREA = "idarea";
    private EditText mEtApplyName;
    private RadioGroup mRdoApply;
    private TextView mTvApplyIdType;
    private TextView mEtApplyData;
    private TextView mTvApplyNation;
    private EditText mEtApplyPhone;
    private TextView mTvApplyProvince;
    private TextView mTvApplyCity;
    private TextView mTvApplyArea;
    private EditText mEtApplyHere;
    private EditText mEtApplyCompany;
    private EditText mEtApplyReason;
    private JaaidApplyFilterVo mFilterVo = null;
    private EditText mEtApplyIdNumber;
    private MaterialDialog mShow;
    private String mSex;
    private RadioButton mRadioGirl;
    private RadioButton mRidioBoy;

    public String getSex() {
        return mSex;
    }

    public void setSex(String sex) {
        this.mSex = sex;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apply_infomation, container, false);
        //初始化数据
        initView(view);
        initSelectData();
        initUserData();
        initCacheData();
        return view;
    }

    private void initUserData() {
        UserVO userVO = ApplicationSet.getInstance().getUserVO();
        if (userVO == null)
            return;
        String realName = userVO.getRealName();
        if (!TextUtils.isEmpty(realName))
            mEtApplyName.setText(realName);
        String sex = userVO.getGender();
        if (!StringUtil.isEmpty(sex)) {
            setSex(sex);
            if (sex.equals(Constants.APPLYGIRL)) {
                mRadioGirl.setChecked(true);
            } else if (sex.equals(Constants.APPLYBOY)) {
                mRidioBoy.setChecked(true);
            } else if (sex.equals("男")) {
                mRidioBoy.setChecked(true);
            } else if (sex.equals("女")) {
                mRadioGirl.setChecked(true);
            }
        }
        String birthday = userVO.getBirthday();
        if (!TextUtils.isEmpty(birthday)) {
            mEtApplyData.setText(birthday);
        }


        String idCard = userVO.getIdCard();
        if (!TextUtils.isEmpty(idCard)) {
            mFilterVo.setmNowSelApplyCertificaters(0);
            mTvApplyIdType.setText(mApplyCertificatesLists.get(0));
            mEtApplyIdNumber.setText(idCard);
        }
        String mobile = userVO.getMobile();
        if (!TextUtils.isEmpty(mobile))
            mEtApplyPhone.setText(mobile);

        String province = userVO.getProvince();
        if (!TextUtils.isEmpty(province)) {
            BaseCommonDataVO vopovince = BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getAreas(), province);
            if (vopovince != null) {
                mTvApplyProvince.setText(vopovince.getName());
                int indexOf = mFilterVo.getApplyProvinceDatas().indexOf(vopovince);
                mFilterVo.setNowSelApplyProvince(indexOf);
                selProvince(indexOf);
                String city = userVO.getCity();
                if (!TextUtils.isEmpty(city)) {
                    BaseCommonDataVO vocity = BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getAreas(), city);
                    if (vocity != null) {
                        mTvApplyCity.setText(vocity.getName());
                        addCityDatas(vopovince);
                        int indexOfcity = mFilterVo.getApplyCityDatas().indexOf(vocity);
                        mFilterVo.setNowSelApplyCity(indexOfcity);
                        selCity(indexOfcity);
                        String county = userVO.getCounty();
                        if (!TextUtils.isEmpty(county)) {
                            BaseCommonDataVO voarea = BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getAreas(), county);
                            if (voarea != null) {
                                mTvApplyArea.setText(voarea.getName());
                                addAreaDatas(vocity);
                                int indexOfarea = mFilterVo.getApplyAreasDatas().indexOf(voarea);
                                mFilterVo.setNowSelApplyAreas(indexOfarea);
                            }
                        }

                    }
                }
            }
        }


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        handlerDatas();
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 初始化选择框相关数据
     */
    private void initSelectData() {
        if (mFilterVo == null)
            mFilterVo = new JaaidApplyFilterVo();
        addProvicnceDatas();
        addCityDatas(null);
        addAreaDatas(null);
        addNationDatas();
        addCertificatesDatas();
    }

    /**
     * 初始化省
     */
    private void addProvicnceDatas() {
        if (mFilterVo.getApplyProvinceDatas() == null || mFilterVo.getApplyProvinceDatas().isEmpty()) {
            List<BaseCommonDataVO> dataVOs = BaseCommonDataVO.getDataVOListWithParentId(ApplicationSet.getInstance().getAreas(), Constants.ROOTPROVINCE);
            if (dataVOs != null) {
                mFilterVo.setApplyProvinceDatas(dataVOs);
                for (BaseCommonDataVO vo : dataVOs
                        ) {
                    mApplyProvicneLists.add(vo.getName());
                }
            }
        }
    }

    /**
     * 初始化市
     */
    private void addCityDatas(BaseCommonDataVO vo) {
        mApplyCityLists.clear();
        if (mFilterVo.getApplyCityDatas() != null)
            mFilterVo.getApplyCityDatas().clear();
        if (vo == null)
            return;
        List<BaseCommonDataVO> dataVOs = BaseCommonDataVO.getDataVOListWithParentId(ApplicationSet.getInstance().getAreas(), vo.getOid());
        mFilterVo.setApplyCityDatas(dataVOs);
        if (mFilterVo.getApplyCityDatas() == null || mFilterVo.getApplyCityDatas().isEmpty()) {
            return;
        }
        for (BaseCommonDataVO bcvo : mFilterVo.getApplyCityDatas()) {
            mApplyCityLists.add(bcvo.getName());
        }
    }

    /**
     * 初始化区域
     */

    private void addAreaDatas(BaseCommonDataVO dvo) {
        if (mFilterVo.getApplyAreasDatas() != null)
            mFilterVo.getApplyAreasDatas().clear();
        mApplyAreaLists.clear();
        if (dvo == null) {
            return;
        } else {
            List<BaseCommonDataVO> dcvo = BaseCommonDataVO.getDataVOListWithParentId(ApplicationSet.getInstance().getAreas(), dvo.getOid());
            mFilterVo.setApplyAreasDatas(dcvo);
            if (mFilterVo.getApplyAreasDatas() == null || mFilterVo.getApplyAreasDatas().isEmpty()) {
                return;
            }
            for (BaseCommonDataVO bcvo : mFilterVo.getApplyAreasDatas()
                    ) {
                mApplyAreaLists.add(bcvo.getName());
            }
        }

    }

    /**
     * 初始化民族
     */
    private void addNationDatas() {
        List<BaseCommonDataVO> applyNationDatas = DataManage.getInstance().getApplyNationDatas();
        if (applyNationDatas != null && !applyNationDatas.isEmpty()) {
            mFilterVo.setmApplyNationDatas(applyNationDatas);
            for (BaseCommonDataVO dvo : applyNationDatas
                    ) {
                mApplyNationLists.add(dvo.getName());
            }
        }
    }

    /**
     * 初始证件类型
     */
    private void addCertificatesDatas() {
        List<BaseCommonDataVO> applyCertificatesDatas = DataManage.getInstance().getApplyCertificatesDatas();
        if (applyCertificatesDatas != null && !applyCertificatesDatas.isEmpty()) {
            mFilterVo.setmApplyCertificatersDatas(applyCertificatesDatas);
            for (BaseCommonDataVO dvo : applyCertificatesDatas) {
                mApplyCertificatesLists.add(dvo.getName());
            }
        }
    }


    private void handlerDatas() {
        mRidioBoy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setSex(Constants.APPLYBOY);
                }
            }
        });
        mRadioGirl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setSex(Constants.APPLYGIRL);
                }
            }
        });

    }

    /**
     * 处理是否填写数据
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(JaaidIsNextTwoEvent event) {
        if (event.isNext()) {//否询问是否下一步
            submit();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_applydata://申请日期
                showDataDialog(getActivity(), mEtApplyData);
                break;
            case R.id.tv_apply_idtype://申请类型
                String idtype = getTextStr(mTvApplyIdType);
                handlerPopWindos(mTvApplyIdType, mApplyCertificatesLists, idtype, IDCARD);
                break;
            case R.id.tv_applynation://申请民族
                String nation = getTextStr(mTvApplyNation);
                handlerPopWindos(mTvApplyNation, mApplyNationLists, nation, IDNATION);
                break;
            case R.id.tv_applyprovince://申请省
                String province = getTextStr(mTvApplyProvince);
                handlerPopWindos(mTvApplyProvince, mApplyProvicneLists, province, IDPROVINCE);
                break;
            case R.id.tv_applycity://申请市
                if (!TextUtils.isEmpty(getTextStr(mTvApplyProvince))) {
                    String city = getTextStr(mTvApplyCity);
                    handlerCityPopWindos(mTvApplyCity, mApplyCityLists, city);
                }
                break;
            case R.id.tv_applyarea://申请区县
                if (!TextUtils.isEmpty(getTextStr(mTvApplyCity)) &&
                        !TextUtils.isEmpty(getTextStr(mTvApplyProvince)))

                {
                    String area = getTextStr(mTvApplyArea);
                    handlerAreaPopWindos(mTvApplyArea, mApplyAreaLists, area);
                }
                break;
        }
    }

    private void initView(View view) {
        mEtApplyName = (EditText) view.findViewById(R.id.et_apply_name);
        mTvApplyIdType = (TextView) view.findViewById(R.id.tv_apply_idtype);
        mEtApplyData = (TextView) view.findViewById(R.id.et_applydata);
        mTvApplyNation = (TextView) view.findViewById(R.id.tv_applynation);
        mEtApplyPhone = (EditText) view.findViewById(R.id.et_applyphone);
        mTvApplyProvince = (TextView) view.findViewById(R.id.tv_applyprovince);
        mTvApplyCity = (TextView) view.findViewById(R.id.tv_applycity);
        mTvApplyArea = (TextView) view.findViewById(R.id.tv_applyarea);
        mEtApplyHere = (EditText) view.findViewById(R.id.et_apply_here);
        mEtApplyCompany = (EditText) view.findViewById(R.id.et_apply_company);
        mEtApplyReason = (EditText) view.findViewById(R.id.et_apply_reason);
        mEtApplyIdNumber = (EditText) view.findViewById(R.id.et_apply_id_number);
        mTvApplyNation.setOnClickListener(this);
        mEtApplyName.setOnClickListener(this);
        mTvApplyIdType.setOnClickListener(this);
        mEtApplyData.setOnClickListener(this);
        mEtApplyPhone.setOnClickListener(this);
        mTvApplyProvince.setOnClickListener(this);
        mTvApplyCity.setOnClickListener(this);
        mTvApplyArea.setOnClickListener(this);
        mEtApplyHere.setOnClickListener(this);
        mEtApplyCompany.setOnClickListener(this);
        mEtApplyReason.setOnClickListener(this);
        mEtApplyIdNumber.setOnClickListener(this);
        mRadioGirl = (RadioButton) view.findViewById(R.id.radio_girl);
        mRadioGirl.setOnClickListener(this);
        mRidioBoy = (RadioButton) view.findViewById(R.id.ridio_boy);
        mRidioBoy.setOnClickListener(this);


    }


    private void handlerPopWindos(final TextView textView, final List<String> mData, String nation, final String type) {
        ApplyListPopAdapter applyPopAdapter = handlerPopWindosAdapter(mData, nation);
        applyPopAdapter.setOnRecyclerItemClickListener(new ApplyListPopAdapter.OnRecyclerItemClickListener() {
            @Override
            public void OnItemClickListener(View view, String item, int position) {
                if (type.equals(IDCARD)) {//证件类型
                    mFilterVo.setmNowSelApplyCertificaters(position);
                } else if (type.equals(IDNATION)) {//民族
                    mFilterVo.setmNowSelApplyNation(position);
                } else if (type.equals(IDPROVINCE)) {//省
                    selProvince(position);
                }
                textView.setText(item);
                mShow.dismiss();
            }
        });
    }

    private void selProvince(int position) {
        mFilterVo.setNowSelApplyProvince(position);
        mTvApplyCity.setText("市州");
        mTvApplyArea.setText("区县");
        mFilterVo.setNowSelApplyCity(-1);
        mFilterVo.setNowSelApplyAreas(-1);
        addCityDatas(mFilterVo.getApplyProvinceDatas().get(position));
    }

    private void handlerCityPopWindos(final TextView textView, final List<String> mData, final String nation) {
        ApplyListPopAdapter applyPopAdapter = handlerPopWindosAdapter(mData, nation);
        applyPopAdapter.setOnRecyclerItemClickListener(new ApplyListPopAdapter.OnRecyclerItemClickListener() {
            @Override
            public void OnItemClickListener(View view, String item, int position) {
                selCity(position);
                if (!item.equals(nation)) {
                    mTvApplyArea.setText("区县");
                    textView.setText(item);
                }
                mShow.dismiss();
            }
        });
    }

    private void selCity(int position) {
        mFilterVo.setNowSelApplyCity(position);
        mFilterVo.setNowSelApplyAreas(-1);
        addAreaDatas(mFilterVo.getApplyCityDatas().get(position));
    }

    private void handlerAreaPopWindos(final TextView textView, final List<String> mData, String nation) {
        ApplyListPopAdapter applyPopAdapter = handlerPopWindosAdapter(mData, nation);
        applyPopAdapter.setOnRecyclerItemClickListener(new ApplyListPopAdapter.OnRecyclerItemClickListener() {
            @Override
            public void OnItemClickListener(View view, String item, int position) {
                mFilterVo.setNowSelApplyAreas(position);
                textView.setText(item);
                mShow.dismiss();
            }
        });
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 从缓存中读取上次填写的数据
     */
    private void initCacheData() {
        JaaidApplyDetailVO cachedata = (JaaidApplyDetailVO) JaaidApplyDetailVO.loadVO(JaaidApplyDetailVO.cacheDataFileName(getActivity()));
        if (cachedata == null)
            return;
        if (!StringUtil.isEmpty(cachedata.getName())) {
            mEtApplyName.setText(cachedata.getName());
        }
        if (!StringUtil.isEmpty(cachedata.getGender())) {
            setSex(cachedata.getGender());
            if (Constants.APPLYBOY.equals(cachedata.getGender())) {
                mRadioGirl.setChecked(false);
                mRidioBoy.setChecked(true);
            } else if (Constants.APPLYGIRL.equals(cachedata.getGender())) {
                mRidioBoy.setChecked(false);
                mRadioGirl.setChecked(true);
            }
        }

        if (!StringUtil.isEmpty(cachedata.getIdType())) {
            //证件类型
            BaseCommonDataVO certificaters = BaseCommonDataVO.findDataVOWithOid(mFilterVo.getmApplyCertificatersDatas(),
                    cachedata.getIdType());
            if (certificaters != null) {
                int index = BaseCommonDataVO.findIndexWithOid(mFilterVo.getmApplyCertificatersDatas(),
                        cachedata.getIdType());
                mFilterVo.setmNowSelApplyCertificaters(index);
                mTvApplyIdType.setText(certificaters.getName());
            }
        }
        if (!StringUtil.isEmpty(cachedata.getIdCard())) {
            mEtApplyIdNumber.setText(cachedata.getIdCard());
        }
        if (!StringUtil.isEmpty(cachedata.getBirthday())) {
            mEtApplyData.setText(cachedata.getBirthday());
        }

        if (!StringUtil.isEmpty(cachedata.getNation())) {
            //民族
            BaseCommonDataVO nation = BaseCommonDataVO.findDataVOWithOid(mFilterVo.getmApplyNationDatas(),
                    cachedata.getNation());
            if (nation != null) {
                int index = BaseCommonDataVO.findIndexWithOid(mFilterVo.getmApplyNationDatas(),
                        cachedata.getNation());
                mFilterVo.setmNowSelApplyNation(index);
                mTvApplyNation.setText(nation.getName());
            }
        }
        if (!StringUtil.isEmpty(cachedata.getTelephone())) {
            mEtApplyPhone.setText(cachedata.getTelephone());
        }

        if (!StringUtil.isEmpty(cachedata.getProvince())) {
            //省
            BaseCommonDataVO province = BaseCommonDataVO.findDataVOWithOid(mFilterVo.getApplyProvinceDatas(),
                    cachedata.getProvince());
            if (province != null) {
                int index = BaseCommonDataVO.findIndexWithOid(mFilterVo.getApplyProvinceDatas(),
                        cachedata.getProvince());
                selProvince(index);
                mTvApplyProvince.setText(province.getName());
                if (!StringUtil.isEmpty(cachedata.getCity())) {
                    //市
                    BaseCommonDataVO city = BaseCommonDataVO.findDataVOWithOid(mFilterVo.getApplyCityDatas(),
                            cachedata.getCity());
                    if (city != null) {
                        int cityindex = BaseCommonDataVO.findIndexWithOid(mFilterVo.getApplyCityDatas(),
                                cachedata.getCity());
                        selCity(cityindex);
                        mTvApplyCity.setText(city.getName());
                        //区县
                        if (!StringUtil.isEmpty(cachedata.getCounty())) {
                            BaseCommonDataVO areas = BaseCommonDataVO.findDataVOWithOid(mFilterVo.getApplyAreasDatas(),
                                    cachedata.getCounty());
                            if (areas != null) {
                                int areasindex = BaseCommonDataVO.findIndexWithOid(mFilterVo.getApplyAreasDatas(),
                                        cachedata.getCounty());
                                mFilterVo.setNowSelApplyAreas(areasindex);
                                mTvApplyArea.setText(areas.getName());
                            }
                        }
                    }
                }
            }
        }

        if (!StringUtil.isEmpty(cachedata.getAddress())) {
            mEtApplyHere.setText(cachedata.getAddress());
        }

        if (!StringUtil.isEmpty(cachedata.getWorkUnit())) {
            mEtApplyCompany.setText(cachedata.getWorkUnit());
        }
    }

    private void submit() {
        // validate
        JaaidApplyDetailVO vo = new JaaidApplyDetailVO();
        vo.setOid(StringUtil.getUUID());
        String name = mEtApplyName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            T.showShort(getContext(), getString(R.string.nameIsempty));
            return;
        }
        vo.setName(name);

        if (TextUtils.isEmpty(getSex())) {
            T.showShort(getContext(), getString(R.string.sexIsempty));
            return;
        }
        vo.setGender(getSex());

        String type = mTvApplyIdType.getText().toString().trim();
        if (TextUtils.isEmpty(type)) {
            Toast.makeText(getContext(), R.string.selectIdcard, Toast.LENGTH_SHORT).show();
            return;
        }
        BaseCommonDataVO dataVO = mFilterVo.getmApplyCertificatersDatas().get(mFilterVo.getmNowSelApplyCertificaters());
        Log.d(TAG, "证件类型" + dataVO.getName());
        vo.setIdType(dataVO.getOid());
        String IdNumber = mEtApplyIdNumber.getText().toString().trim();
        if (TextUtils.isEmpty(IdNumber)) {
            T.showShort(getContext(), getString(R.string.IdNumberIsempty));
            return;
        }
        if (type.equals(getResources().getString(R.string.apply_id_card))) {
            boolean card = StringUtil.validateidCard(IdNumber);
            if (card) {
                vo.setIdCard(IdNumber);
            } else {
                T.showShort(getContext(), R.string.pleaseIdCardIsRight);
                return;
            }
        } else if (type.equals("军官证")) {
            boolean JG = StringUtil.validateidJGCard(IdNumber);
            if (JG) {
                vo.setIdCard(IdNumber);
            } else {
                T.showShort(getContext(), R.string.pleaseIdTypeIsRight);
                return;
            }

        }
        String data = mEtApplyData.getText().toString().trim();
        if (TextUtils.isEmpty(data)) {
            Toast.makeText(getContext(), R.string.BirthdayIsempty, Toast.LENGTH_SHORT).show();
            return;
        }
        vo.setBirthday(data);


        String nation = mTvApplyNation.getText().toString().trim();
        if (TextUtils.isEmpty(nation)) {
            Toast.makeText(getContext(), R.string.nationIsempty, Toast.LENGTH_SHORT).show();
            return;
        }
        BaseCommonDataVO dataVO1 = mFilterVo.getmApplyNationDatas().get(mFilterVo.getmNowSelApplyNation());
        Log.d(TAG, "民族" + dataVO1.getName());
        vo.setNation(dataVO1.getOid());


        String phone = mEtApplyPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(getContext(), R.string.photoIsempty, Toast.LENGTH_SHORT).show();
            return;
        }

        boolean telNumber = StringUtil.validateMoblie(phone);
        if (telNumber) {
            vo.setTelephone(phone);
        } else {
            T.showShort(getContext(), R.string.pleasePhotoIsRight);
            return;
        }
        String province = mTvApplyProvince.getText().toString().trim();
        if (TextUtils.isEmpty(province)) {
            Toast.makeText(getContext(), R.string.provinceIsEmpty, Toast.LENGTH_SHORT).show();
            return;
        }
        BaseCommonDataVO dataVO2 = mFilterVo.getApplyProvinceDatas().get(mFilterVo.getNowSelApplyProvince());
        Log.d(TAG, "省名字: " + dataVO2.getName());
        vo.setProvince(dataVO2.getOid());


        String city = mTvApplyCity.getText().toString().trim();
        if (!TextUtils.isEmpty(city) && !city.equals("市州")) {
            BaseCommonDataVO dataVO3 = mFilterVo.getApplyCityDatas().get(mFilterVo.getNowSelApplyCity());
            Log.d(TAG, "市名字: " + dataVO3.getName());
            vo.setCity(dataVO3.getOid());
        }
        String area = mTvApplyArea.getText().toString().trim();
        if (!TextUtils.isEmpty(area) && !area.equals("区县")) {
            BaseCommonDataVO dataVO4 = mFilterVo.getApplyAreasDatas().get(mFilterVo.getNowSelApplyAreas());
            Log.d(TAG, "区县名字: " + dataVO4.getName());
            vo.setCounty(dataVO4.getOid());
        }
        String here = mEtApplyHere.getText().toString().trim();
        if (TextUtils.isEmpty(here)) {
            Toast.makeText(getContext(), R.string.hereIsEmpty, Toast.LENGTH_SHORT).show();
            return;
        }
        vo.setAddress(here);

        String company = mEtApplyCompany.getText().toString().trim();
        if (!TextUtils.isEmpty(company)) {//工作单位
            vo.setWorkUnit(company);
        }

        String reason = mEtApplyReason.getText().toString().trim();
        if (TextUtils.isEmpty(reason)) {
            Toast.makeText(getContext(), R.string.reasonIsempty, Toast.LENGTH_SHORT).show();
            return;
        }
        vo.setCaseDescription(reason);

        //缓存用户输入信息
        JaaidApplyDetailVO.saveVO(vo, JaaidApplyDetailVO.cacheDataFileName(getActivity()));
        EventBus.getDefault().post(new JaaidApplyTwoSubmitEven(vo, true));
    }

    private String getTextStr(TextView view) {
        return view.getText().toString().trim();
    }

    private ApplyListPopAdapter handlerPopWindosAdapter(final List<String> mData, String nation) {
        ApplyListPopAdapter applyPopAdapter = new ApplyListPopAdapter(mData, getActivity());
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        if (mShow == null || !mShow.isShowing()) {
            mShow = new MaterialDialog.Builder(getActivity())
                    .adapter(applyPopAdapter, manager)
                    .show();
//            mShow.getRecyclerView().addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        }
        applyPopAdapter.setSeletsStr(nation);
        return applyPopAdapter;
    }
}
