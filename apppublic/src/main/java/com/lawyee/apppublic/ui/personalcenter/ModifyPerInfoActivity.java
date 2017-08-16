package com.lawyee.apppublic.ui.personalcenter;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.ApplyListPopAdapter;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.UserService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.util.ShowOrHide;
import com.lawyee.apppublic.vo.BaseCommonDataVO;
import com.lawyee.apppublic.vo.JaaidApplyFilterVo;
import com.lawyee.apppublic.vo.UserVO;

import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
import java.util.List;

public class ModifyPerInfoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvApplyBack;
    private LinearLayout mLiTitle;
    private EditText mEtModifyNickName;
    private EditText mEtModifyRealName;
    private RadioButton mRadioModifyGirl;
    private RadioButton mRadioModifyBoy;
    private EditText mEtModifyId;
    private EditText mEtModifyPhone;
    private TextView mTvModifyProvince;
    private TextView mTvModifyCity;
    private TextView mTvModifyArea;
    private EditText mEtModifyAdress;

    private String mSelectSex = "";
    private List<String> mApplyProvicneLists = null;
    private List<String> mApplyCityLists = null;
    private List<String> mApplyAreaLists = null;
    private JaaidApplyFilterVo mFilterVo = null;
    private String mGirl = Constants.APPLYGIRL;
    private String mBoy = Constants.APPLYBOY;
    private MaterialDialog mShow;
    private Button mSubmit;
    private TextView mTvModifyBrithday;

    private boolean submit = false;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_modify_per_info);
        initView();
        initSelectData();
        initUserData();
        handlerSelect();

    }

    private void initUserData() {
        UserVO userVO = ApplicationSet.getInstance().getUserVO();
        if (userVO != null) {
            String nickName = userVO.getNickName();
            if (!isEmpty(nickName))
                mEtModifyNickName.setText(nickName);
            String realName = userVO.getRealName();
            if (!isEmpty(realName))
                mEtModifyRealName.setText(realName);
            String gender = userVO.getGender();
            if (!isEmpty(gender)) {
                if (gender.equals(mBoy)) {
                    mRadioModifyBoy.setChecked(true);
                } else if (gender.equals(mGirl)) {
                    mRadioModifyGirl.setChecked(true);
                }
            }
            String idCard = userVO.getIdCard();
            if (!isEmpty(idCard))
                mEtModifyId.setText(idCard);

            String birthday = userVO.getBirthday();
            if (!isEmpty(birthday))
                mTvModifyBrithday.setText(birthday);

            String mobile = userVO.getMobile();
            if (!isEmpty(mobile))
                mEtModifyPhone.setText(mobile);
            String address = userVO.getAddress();
            if (!isEmpty(address))
                mEtModifyAdress.setText(address);
            String province = userVO.getProvince();
            if (!TextUtils.isEmpty(province)) {
                BaseCommonDataVO vopovince = BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getAreas(), province);
                if (vopovince != null) {
                    mTvModifyProvince.setText(vopovince.getName());
                    int indexOf = mFilterVo.getApplyProvinceDatas().indexOf(vopovince);
                    mFilterVo.setNowSelApplyProvince(indexOf);
                    selProvince(indexOf);
                    String city = userVO.getCity();
                    if (!TextUtils.isEmpty(city)) {
                        BaseCommonDataVO vocity = BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getAreas(), city);
                        if (vocity != null) {
                            mTvModifyCity.setText(vocity.getName());
                            addCityDatas(vopovince);
                            int indexOfcity = mFilterVo.getApplyCityDatas().indexOf(vocity);
                            mFilterVo.setNowSelApplyCity(indexOfcity);
                            selCity(indexOfcity);
                            String county = userVO.getCounty();
                            if (!TextUtils.isEmpty(county)) {
                                BaseCommonDataVO voarea = BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getAreas(), county);
                                if (voarea != null) {
                                    mTvModifyArea.setText(voarea.getName());
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


    }

    private void initSelectData() {
        if (mFilterVo == null)
            mFilterVo = new JaaidApplyFilterVo();
        if (mApplyProvicneLists == null)
            mApplyProvicneLists = new ArrayList<>();
        if (mApplyCityLists == null)
            mApplyCityLists = new ArrayList<>();
        if (mApplyAreaLists == null)
            mApplyAreaLists = new ArrayList<>();
        addProvicnceDatas();
        addCityDatas(null);
        addAreaDatas(null);

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

    private void handlerSelect() {
        mRadioModifyBoy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mSelectSex = mBoy;
            }
        });
        mRadioModifyGirl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mSelectSex = mGirl;
            }
        });


    }

    private void initView() {
        mIvApplyBack = (ImageView) findViewById(R.id.iv_apply_back);
        mLiTitle = (LinearLayout) findViewById(R.id.li_title);
        mEtModifyNickName = (EditText) findViewById(R.id.et_modify_NickName);
        mEtModifyRealName = (EditText) findViewById(R.id.et_modify_RealName);
        mRadioModifyGirl = (RadioButton) findViewById(R.id.radio_modify_girl);
        mRadioModifyBoy = (RadioButton) findViewById(R.id.radio_modify_boy);
        mEtModifyId = (EditText) findViewById(R.id.et_modify_Id);
        mEtModifyPhone = (EditText) findViewById(R.id.et_modify_phone);
        mTvModifyProvince = (TextView) findViewById(R.id.tv_modify_province);
        mTvModifyCity = (TextView) findViewById(R.id.tv_modify_city);
        mTvModifyArea = (TextView) findViewById(R.id.tv_modify_area);
        mEtModifyAdress = (EditText) findViewById(R.id.et_modify_adress);
        mTvModifyProvince.setOnClickListener(this);
        mTvModifyCity.setOnClickListener(this);
        mTvModifyArea.setOnClickListener(this);
        mSubmit = (Button) findViewById(R.id.submit);
        mTvModifyBrithday = (TextView) findViewById(R.id.tv_modify_brithday);
        mTvModifyBrithday.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_modify_province:
                String province = getTextStr(mTvModifyProvince);
                handlerProvincePopWindos(mTvModifyProvince, mApplyProvicneLists, province);
                break;
            case R.id.tv_modify_city:
                if (!TextUtils.isEmpty(getTextStr(mTvModifyProvince))) {
                    String city = getTextStr(mTvModifyCity);
                    handlerCityPopWindos(mTvModifyCity, mApplyCityLists, city);
                }
                break;
            case R.id.tv_modify_area:
                if (!TextUtils.isEmpty(getTextStr(mTvModifyProvince)) &&
                        !TextUtils.isEmpty(getTextStr(mTvModifyCity))) {
                    String area = getTextStr(mTvModifyArea);
                    handlerAreaPopWindos(mTvModifyArea, mApplyAreaLists, area);
                }
                break;
            case R.id.submit:
                submit();
                break;
            case R.id.tv_modify_brithday:
                ShowOrHide.showDataDialog(this, mTvModifyBrithday);
                break;
            default:
                break;
        }
    }


    private void handlerProvincePopWindos(final TextView textView, final List<String> mData, String nation) {
        ApplyListPopAdapter applyPopAdapter = handlerPopWindosAdapter(mData, nation);
        applyPopAdapter.setOnRecyclerItemClickListener(new ApplyListPopAdapter.OnRecyclerItemClickListener() {
            @Override
            public void OnItemClickListener(View view, String item, int position) {
                selProvince(position);
                textView.setText(item);
                mShow.dismiss();
            }
        });
    }

    private void handlerCityPopWindos(final TextView textView, final List<String> mData, final String nation) {
        ApplyListPopAdapter applyPopAdapter = handlerPopWindosAdapter(mData, nation);
        applyPopAdapter.setOnRecyclerItemClickListener(new ApplyListPopAdapter.OnRecyclerItemClickListener() {
            @Override
            public void OnItemClickListener(View view, String item, int position) {
                selCity(position);
                if (!item.equals(nation)) {
                    mTvModifyArea.setText("区县");
                    textView.setText(item);
                }
                mShow.dismiss();
            }
        });
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

    private void selProvince(int position) {
        mFilterVo.setNowSelApplyProvince(position);
        mTvModifyCity.setText("市");
        mTvModifyArea.setText("区县");
        mFilterVo.setNowSelApplyCity(-1);
        mFilterVo.setNowSelApplyAreas(-1);
        addCityDatas(mFilterVo.getApplyProvinceDatas().get(position));
    }

    private void selCity(int position) {
        mFilterVo.setNowSelApplyCity(position);
        mFilterVo.setNowSelApplyAreas(-1);
        addAreaDatas(mFilterVo.getApplyCityDatas().get(position));
    }

    private ApplyListPopAdapter handlerPopWindosAdapter(final List<String> mData, String nation) {
        ApplyListPopAdapter applyPopAdapter = new ApplyListPopAdapter(mData, ModifyPerInfoActivity.this);
        GridLayoutManager manager = new GridLayoutManager(ModifyPerInfoActivity.this, 1);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        if (mShow == null || !mShow.isShowing()) {
            mShow = new MaterialDialog.Builder(ModifyPerInfoActivity.this)
                    .adapter(applyPopAdapter, manager)
                    .show();
//            mShow.getRecyclerView().addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        }
        applyPopAdapter.setSeletsStr(nation);
        return applyPopAdapter;
    }

    private String getTextStr(TextView tv) {
        return tv.getText().toString().trim();
    }

    private void submit() {
        UserVO vo = new UserVO();

        String nickName = getTextStr(mEtModifyNickName);
        if (!TextUtils.isEmpty(nickName)) {
            vo.setNickName(nickName);
        }

        String realname = getTextStr(mEtModifyRealName);
        if (TextUtils.isEmpty(realname)) {
            T.showShort(ModifyPerInfoActivity.this, "真实姓名不能为空");
            return;
        }
        vo.setRealName(realname);

        if (!TextUtils.isEmpty(mSelectSex)) {
            vo.setGender(mSelectSex);
        }

        String id = getTextStr(mEtModifyId);
        if (TextUtils.isEmpty(id)) {
            T.showShort(ModifyPerInfoActivity.this, getString(R.string.IdCardIsEmpty));
            return;
        }


        boolean isId = StringUtil.validateidCard(id);
        if (isId) {
            vo.setIdCard(id);
        } else {
            T.showShort(ModifyPerInfoActivity.this, getString(R.string.pleaseIdCardIsRight));
            return;
        }

        String brithday = getTextStr(mTvModifyBrithday);
        if (!TextUtils.isEmpty(brithday)) {
            vo.setBirthday(brithday);
        }

        String phone = getTextStr(mEtModifyPhone);
        if (!TextUtils.isEmpty(phone)) {
            boolean isphone = StringUtil.validateMoblie(phone);
            if (isphone) {
                vo.setMobile(phone);
            } else {
                T.showShort(ModifyPerInfoActivity.this, getString(R.string.pleasePhotoIsRight));
                return;
            }
        }
        String province = getTextStr(mTvModifyProvince);
        if (!TextUtils.isEmpty(province)) {
            BaseCommonDataVO dataVO2 = mFilterVo.getApplyProvinceDatas().get(mFilterVo.getNowSelApplyProvince());
            Log.d(TAG, "省名字: " + dataVO2.getName());
            vo.setProvince(dataVO2.getOid());
        }

        final String city = getTextStr(mTvModifyCity);
        if (!TextUtils.isEmpty(city) && !city.equals("市")) {
            BaseCommonDataVO dataVO3 = mFilterVo.getApplyCityDatas().get(mFilterVo.getNowSelApplyCity());
            Log.d(TAG, "市名字: " + dataVO3.getName());
            vo.setCity(dataVO3.getOid());
        }
        String area = getTextStr(mTvModifyArea);
        if (!TextUtils.isEmpty(area) && !area.equals("区县")) {
            BaseCommonDataVO dataVO4 = mFilterVo.getApplyAreasDatas().get(mFilterVo.getNowSelApplyAreas());
            Log.d(TAG, "区县名字: " + dataVO4.getName());
            vo.setCounty(dataVO4.getOid());
        }

        String addreess = getTextStr(mEtModifyAdress);
        if (!TextUtils.isEmpty(addreess)) {
            vo.setAddress(addreess);
        }

        Log.e(TAG, "submit: " + vo);
        SubmitService(vo);
    }

    private void SubmitService(UserVO vo) {
        if (submit) {
            return;
        }
        UserService service = new UserService(this);
        service.setShowProgress(true);
        submit = true;
        service.setProgressShowContent(getResources().getString(R.string.submit_ing));
        service.changeUserInfo(vo.getNickName(), vo.getRealName(), vo.getGender(), vo.getMobile(), vo.getIdCard()
                , vo.getProvince(), vo.getCity(), vo.getCounty(), vo.getAddress(), vo.getBirthday(), new BaseJsonService.IResultInfoListener() {
                    @Override
                    public void onComplete(ArrayList<Object> values, String content) {
//                        Log.e(TAG, "onComplete: " + values + content);
                        submit = false;
                        T.showShort(ModifyPerInfoActivity.this, getString(R.string.submit_success));
                        UserVO o = (UserVO) values.get(0);
                        if (o != null)
                            ApplicationSet.getInstance().setUserVO(o, true);
                        finish();
                    }

                    @Override
                    public void onError(String msg, String content) {
                        submit = false;
                        T.showShort(ModifyPerInfoActivity.this, "服务器繁忙，请稍候修改");
                        Log.e(TAG, "onError: " + msg);
                        Log.e(TAG, "onError: " + content);
                    }
                });
    }

    private boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }
}
