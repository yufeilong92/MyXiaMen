package com.lawyee.apppublic.ui.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.config.DataManage;
import com.lawyee.apppublic.ui.HtmlShowActivity;
import com.lawyee.apppublic.ui.org.JaaidAreaSelActivity;
import com.lawyee.apppublic.ui.org.JaaidSelectOrgActivity;
import com.lawyee.apppublic.vo.BaseCommonDataVO;
import com.lawyee.apppublic.vo.JaaidApplyAreaeEven;
import com.lawyee.apppublic.vo.JaaidApplyDetailVO;
import com.lawyee.apppublic.vo.JaaidApplyThreeSubmitEvent;
import com.lawyee.apppublic.vo.JaaidIsNextThreeEvent;

import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.T;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.lawyee.apppublic.ui.BaseActivity.CSTR_EXTRA_TITLE_STR;
import static com.lawyee.apppublic.ui.HtmlShowActivity.TOHTML;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: Apply_OtherInformation_fragment.java
 * @Package com.lawyee.apppublic.ui.frag
 * @Description: 法援申请其它人申请信息页
 * @author: YFL
 * @date: 2017/6/2 15:30
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/6/2 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JaaidApplyOtherInformationFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {
    private static final String APPLYRAGREE = "selorgrule.html";
    private List<String> mApplyCertificatesLists = new ArrayList<>();//证件类型

    private int mSelAreaPosition;
    /**
     * 传递参数-选择机构名称
     */
    public static final String ORGNAME = "orgname";
    /**
     * 传递参数-选择机构oid
     */
    public static final String ORGOID = "orgoid";
    /**
     * 请求码
     */
    private int REQUESTCODE = 1024;
    /**
     * 选中地区数据
     *
     * @return
     */
    private BaseCommonDataVO areaData;
    /**
     * 证件号是否正确
     */
    private boolean isright;

    public boolean isright() {
        return isright;
    }

    public void setIsIdCardRight(boolean isright) {
        this.isright = isright;
    }

    private EditText mEtApplyOtherName;
    private RadioGroup mRdoApplyOtherType;
    private EditText mEtApplyOtherIdcard;
    private EditText mEtApplyNumberPeople;
    private EditText mEtApplyPartyName;
    private EditText mEtApplyPartyHere;
    private EditText mEtApplyOtherCrimeplace;
    private EditText mEtApplyOtherOrgan;
    private TextView mTvApplyOtherApply;
    private TextView mTvApplyOtherArea;
    private TextView mTvApplyOtherOrg;
    private String mSelectOrgoId;
    private RadioButton mRadiobtnLegal;
    private RadioButton mRadiobtnEntrust;
    private String agentType;

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public BaseCommonDataVO getAreaData() {
        return areaData;
    }

    public void setAreaData(BaseCommonDataVO areaData) {
        this.areaData = areaData;
    }

    public int getmSelAreaPosition() {
        return mSelAreaPosition;
    }

    public void setmSelAreaPosition(int mSelAreaPosition) {
        this.mSelAreaPosition = mSelAreaPosition;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apply_otherformation, container, false);
        initCertificatesDatas();
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        handlerData();
    }

    private void handlerData() {
        mRadiobtnLegal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setAgentType(Constants.APPLYJAAID_LEGAL);
                }
            }
        });
        mRadiobtnEntrust.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setAgentType(Constants.APPLYJAAID_ENTRUST);
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
    /**
     * 初始证件类型
     */
    private void initCertificatesDatas() {
        List<BaseCommonDataVO> applyCertificatesDatas = DataManage.getInstance().getApplyCertificatesDatas();
        if (applyCertificatesDatas != null && !applyCertificatesDatas.isEmpty()) {
            for (BaseCommonDataVO dvo : applyCertificatesDatas) {
                mApplyCertificatesLists.add(dvo.getName());
            }
        }
    }
    private void initView(View view) {
        mEtApplyOtherName = (EditText) view.findViewById(R.id.et_apply_other_name);
        mRdoApplyOtherType = (RadioGroup) view.findViewById(R.id.rdo_apply_other_type);
        mEtApplyOtherIdcard = (EditText) view.findViewById(R.id.et_apply_other_idcard);
        mEtApplyNumberPeople = (EditText) view.findViewById(R.id.et_apply_number_people);
        mEtApplyPartyName = (EditText) view.findViewById(R.id.et_apply_party_name);
        mEtApplyPartyHere = (EditText) view.findViewById(R.id.et_apply_party_here);
        mEtApplyOtherCrimeplace = (EditText) view.findViewById(R.id.et_apply_other_crimeplace);
        mEtApplyOtherOrgan = (EditText) view.findViewById(R.id.et_apply_other_organ);
        mTvApplyOtherApply = (TextView) view.findViewById(R.id.tv_apply_other_apply);
        mTvApplyOtherArea = (TextView) view.findViewById(R.id.tv_apply_other_area);
        mTvApplyOtherOrg = (TextView) view.findViewById(R.id.tv_apply_other_org);
        mTvApplyOtherArea.setOnClickListener(this);
        mTvApplyOtherOrg.setOnClickListener(this);
        mTvApplyOtherApply.setOnClickListener(this);
        mRadiobtnLegal = (RadioButton) view.findViewById(R.id.radiobtn_Legal);
        mRadiobtnLegal.setOnClickListener(this);
        mRadiobtnEntrust = (RadioButton) view.findViewById(R.id.radiobtn_entrust);
        mRadiobtnEntrust.setOnClickListener(this);
        mEtApplyOtherIdcard.setOnFocusChangeListener(this);
    }

    /**
     * 地区回调
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(JaaidApplyAreaeEven event) {
        String trim = mTvApplyOtherArea.getText().toString().trim();
        String name = event.getVo().getName();
        if (!trim.equals(name)) {
            mTvApplyOtherOrg.setText(getResources().getString(R.string.apply_sel_org));
        }
        setmSelAreaPosition(event.getId());
        mTvApplyOtherArea.setText(name);
        setAreaData(event.getVo());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(JaaidIsNextThreeEvent event) {
        JaaidApplyDetailVO vo = event.getmData();
        if (event.isNext()) {
            submit(vo);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE) {
            if (data == null)
                return;
            Bundle bundle = data.getExtras();
            mSelectOrgoId = bundle.getString(ORGOID);
            String orgname = bundle.getString(ORGNAME);
            if (orgname != null && !TextUtils.isEmpty(orgname)) {
                mTvApplyOtherOrg.setText(orgname);
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_apply_other_apply:
                Intent intent = new Intent(getActivity(), HtmlShowActivity.class);
                intent.putExtra(HtmlShowActivity.CSTR_EXTRA_TITLE_STR, getActivity().getString(R.string.user_apply_protoceo));
                intent.putExtra(TOHTML, APPLYRAGREE);
                startActivity(intent);
                mTvApplyOtherApply.setTextColor(getResources().getColor(R.color.red_org));
                break;
            case R.id.tv_apply_other_area:
                String strArea = getTextStr(mTvApplyOtherArea);
                if (TextUtils.isEmpty(strArea)){
                    setmSelAreaPosition(-1);
                }
                Intent intent1 = new Intent(getActivity(), JaaidAreaSelActivity.class);
                intent1.putExtra(CSTR_EXTRA_TITLE_STR, getString(R.string.intent_jaaid_area));
                intent1.putExtra(JaaidAreaSelActivity.CSTR_EXTRA_TYPE, getmSelAreaPosition());
                getActivity().startActivity(intent1);
                break;
            case R.id.tv_apply_other_org:
                if (getAreaData() == null) {
                    T.showShort(getActivity(), R.string.apply_sel_org);
                    return;
                }
                Intent intent2 = new Intent(getActivity(), JaaidSelectOrgActivity.class);
                intent2.putExtra(JaaidSelectOrgActivity.SELECTORGOID, getAreaData().getOid());
                startActivityForResult(intent2, REQUESTCODE);
                break;
            default:
                break;
        }
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_apply_other_idcard:
                if (!hasFocus) {
                    String trim = mEtApplyOtherIdcard.getText().toString().trim();
                    if (TextUtils.isEmpty(trim))
                        return;
                    boolean idcard = StringUtil.validateidCard(trim);
                    if (idcard) {
                        setIsIdCardRight(true);
                    } else {
                        setIsIdCardRight(false);
                        T.showShort(getContext(), R.string.pleaseIdCardIsRight);
                    }
                }
                break;
            default:
                break;
        }
    }

    private String getTextStr(TextView view) {
        return view.getText().toString().trim();
    }
    private void submit(JaaidApplyDetailVO vo) {
        // validate
        if (getAgentType() != null || !TextUtils.isEmpty(getAgentType())) {
            vo.setAgentType(getAgentType());//代理人类型
        }
        String name = mEtApplyOtherName.getText().toString().trim();
        if (!TextUtils.isEmpty(name)) {
            vo.setAgentName(name);//代理姓名
        }

        String idcard = mEtApplyOtherIdcard.getText().toString().trim();
        if (!TextUtils.isEmpty(idcard)) {
            vo.setAgentIdCard(idcard);//代理人身份证
        }

        String people = mEtApplyNumberPeople.getText().toString().trim();
        if (!TextUtils.isEmpty(people)) {
            vo.setApplyUserCount(people);//申请人数
        }

        String partyname = mEtApplyPartyName.getText().toString().trim();
        if (!TextUtils.isEmpty(partyname)) {
            vo.setPartiesName(partyname);//当事人姓名
        }

        String here = mEtApplyPartyHere.getText().toString().trim();
        if (!TextUtils.isEmpty(here)) {
            vo.setPartiesLocal(here);//当事人住所地
        }

        String crimeplace = mEtApplyOtherCrimeplace.getText().toString().trim();
        if (!TextUtils.isEmpty(crimeplace)) {
            vo.setCaseHappenLocal(crimeplace);//案发所在地
        }

        String organ = mEtApplyOtherOrgan.getText().toString().trim();
        if (!TextUtils.isEmpty(organ)) {
            vo.setHandleOrgAddress(organ);//办案机关所在地
        }

        String trim = mTvApplyOtherArea.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            Toast.makeText(getContext(),R.string.apply_sel_area, Toast.LENGTH_SHORT).show();
            return;
        } else {
            vo.setSelectcity(getAreaData().getOid());//选择地区
        }

        String org = mTvApplyOtherOrg.getText().toString().trim();
        if (org.equals(getResources().getString(R.string.apply_sel_org))) {
            Toast.makeText(getContext(), R.string.apply_sel_org, Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (mSelectOrgoId == null)
                return;
            vo.setManageOrgName(org);
            vo.setManageOrgId(mSelectOrgoId);//选择的机构
        }
        EventBus.getDefault().post(new JaaidApplyThreeSubmitEvent(vo, true));

    }
}
