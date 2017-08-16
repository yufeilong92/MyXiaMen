package com.lawyee.apppublic.ui.lawAdministration.jamed;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.ApplySeasonListPopAdapter;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.config.DataManage;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JamedUserService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.vo.BaseCommonDataVO;
import com.lawyee.apppublic.vo.JamedApplyDetailVO;
import com.lawyee.apppublic.vo.SelectItemVo;
import com.lawyee.apppublic.vo.UserVO;
import com.lawyee.apppublic.widget.RecycleViewDivider;

import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
import java.util.List;

import static com.lawyee.apppublic.R.id.et_jamedService_applyNumber;
import static com.lawyee.apppublic.util.RecyclerSelectItem.MoveToPostion;


public class JamedApplyNBVActivity extends BaseActivity implements View.OnClickListener {

    private MaterialDialog mPopWindowsShow;

    private SelectItemVo mSelectType;

    private SelectItemVo mSelectNation;

    private SelectItemVo mBeiSelectType;

    private SelectItemVo mBeiSelectNation;


    private String CARDNATION = "nation";


    private String BEICARDNATION = "beination";

    private String mSex = "";

    private String mBeiSex = "";

    private List<BaseCommonDataVO> mCardTypeLists;

    private List<BaseCommonDataVO> mNationLists;

    private String Officer = "军官证";

    private String IdCard = "身份证";
    private EditText mEtJamedServiceApplyNumber;
    private TextView mTvJamedServiceApplyOrg;
    private EditText mEtJamedServiceApplyName;
    private RadioButton mRadioJamedServiceGirl;
    private RadioButton mRadioJamedServiceBoy;
    private RadioGroup mRdoJamedApplySex;
    private EditText mEtJamedServiceApplyID;
    private TextView mEtJamedServiceApplyBrithday;
    private TextView mTvJamedServiceApplyNation;
    private EditText mEtJamedServiceApplyTelephone;
    private EditText mEtJamedServcieApplyHere;
    private EditText mEtJamedServiceApplyNexus;
    private EditText mEtBeijamedServiceApplyName;
    private RadioButton mRadioBeijamedServiceGril;
    private RadioButton mRadioBeijamedServcieBoy;
    private RadioGroup mRdoJamedQuiltSex;
    private TextView mEtBeijamedServiceApplyBirthday;
    private TextView mTvBeijamedServiceApplyNation;
    private EditText mEtBeijamedServiceApplyTelephone;
    private EditText mEtBeijamedServiceApplyHere;
    private EditText mEtJamedServiceApplyCase;
    private EditText mEtJamedServiceApplyItem;
    private Context mContext;
    private Button mBtnJamedServiceSubmit;

    private String mCheckBox = "";
    private String mCheckBoxTrue = "true";
    private String mCheckBoxFalse = "false";
    private CheckBox mChbJamedServiceMedia;
    private String mOrgOid = "";


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_jamed_apply_nbv);
        initView();
        initCardType();
        initNation();
        handlerDate();

    }


    private void handlerDate() {
        UserVO userVO = ApplicationSet.getInstance().getUserVO();
        if (userVO != null) {
            String orgName = userVO.getOrgName();
            String orgId = userVO.getOrgId();
            if (orgId != null && !TextUtils.isEmpty(orgId)) {
                mOrgOid = orgId;
            }
            mTvJamedServiceApplyOrg.setText(getOrgName(orgName));

        }
        mRadioJamedServiceBoy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mSex = Constants.APPLYBOY;
            }
        });
        mRadioJamedServiceGirl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mSex = Constants.APPLYGIRL;
            }
        });
        mRadioBeijamedServcieBoy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mBeiSex = Constants.APPLYBOY;
            }
        });
        mRadioBeijamedServiceGril.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mBeiSex = Constants.APPLYGIRL;
            }
        });
        mChbJamedServiceMedia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckBox = mCheckBoxTrue;
                } else {
                    mCheckBox = mCheckBoxFalse;
                }
            }
        });
        mEtJamedServiceApplyID.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String Id = getTextStr(mEtJamedServiceApplyID);
                    if (TextUtils.isEmpty(Id)) {
                        return;
                    }
                    boolean b = StringUtil.validateidCard(Id);
                    if (!b) {
                        T.showShort(mContext, getString(R.string.pleaseIdCardIsRight));
                    }
                }
            }
        });
        mEtJamedServiceApplyTelephone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String telephone = getTextStr(mEtJamedServiceApplyTelephone);
                    if (TextUtils.isEmpty(telephone)) {
                        return;
                    }
                    boolean phone = StringUtil.validateMoblie(telephone);
                    if (!phone) {
                        T.showShort(mContext, getString(R.string.pleasePhotoIsRight));
                    }
                }
            }
        });
        mEtBeijamedServiceApplyTelephone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String telephone = getTextStr(mEtBeijamedServiceApplyTelephone);
                    if (TextUtils.isEmpty(telephone)) {
                        return;
                    }
                    boolean phone = StringUtil.validateMoblie(telephone);
                    if (!phone) {
                        T.showShort(mContext, getString(R.string.bePhotoIsRight));
                    }
                }
            }
        });

    }

    private String getOrgName(String orgName) {
        if (!TextUtils.isEmpty(orgName)){
            return orgName;
        }
        if (mOrgOid != null) {
            String nameWithOid = DataManage.getInstance().getNameWithOid(mOrgOid);
            if (!TextUtils.isEmpty(nameWithOid)) {
                return nameWithOid;
            }
        }
        return "";


    }

    private void initNation() {
        if (mNationLists == null || mNationLists.isEmpty()) {
            mNationLists = new ArrayList<>();
            List<BaseCommonDataVO> nationDatas = DataManage.getInstance().getApplyNationDatas();
            if (nationDatas != null && !nationDatas.isEmpty()) {
                mNationLists = nationDatas;
            }
        }
    }

    private void initCardType() {
        if (mCardTypeLists == null || mCardTypeLists.isEmpty()) {
            mCardTypeLists = new ArrayList<>();
            List<BaseCommonDataVO> cardlists = DataManage.getInstance().getApplyCertificatesDatas();
            if (cardlists != null && !cardlists.isEmpty()) {
                mCardTypeLists = cardlists;
            }
        }
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_jamedService_ApplyNation:
                String nation = getTextStr(mTvJamedServiceApplyNation);
                if (mNationLists != null && !mNationLists.isEmpty()) {
                    handlerPopWindos(mTvJamedServiceApplyNation, mNationLists, nation, CARDNATION);
                }
                break;
            case R.id.tv_BeijamedService_ApplyNation:
                String beination = getTextStr(mTvBeijamedServiceApplyNation);
                if (mNationLists != null && !mNationLists.isEmpty()) {
                    handlerPopWindos(mTvBeijamedServiceApplyNation, mNationLists, beination, BEICARDNATION);
                }
                break;
            case R.id.btn_jamedService_Submit:
                submit();
                break;
        }
    }

    /**
     * @param tv     显示控件
     * @param mData  数据
     * @param nation 显示文字
     * @param type   类型
     */
    private void handlerPopWindos(final TextView tv, final List<BaseCommonDataVO> mData, final String nation, final String type) {
        final ApplySeasonListPopAdapter applyPopAdapter = new ApplySeasonListPopAdapter(mData, this);
        final GridLayoutManager manager = new GridLayoutManager(this, 1);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        if (mPopWindowsShow == null || !mPopWindowsShow.isShowing()) {
            mPopWindowsShow = new MaterialDialog.Builder(this)
                    .adapter(applyPopAdapter, manager)
                    .show();
            mPopWindowsShow.getRecyclerView().addItemDecoration(new RecycleViewDivider(this, GridLayoutManager.VERTICAL, R.drawable.bg_rlv_diving));
        }

        applyPopAdapter.setSeletsStr(nation);
        selectItem(applyPopAdapter, type, manager);
        applyPopAdapter.setOnRecyclerItemClickListener(new ApplySeasonListPopAdapter.OnRecyclerItemClickListener() {
            @Override
            public void OnItemClickListener(View view, BaseCommonDataVO itemVo, int position) {
                if (type.equals(CARDNATION)) {//申请人民族
                    mSelectNation.setSelectPosition(position);
                    mSelectNation.setItemVo(itemVo);
                    tv.setText(itemVo.getName());
                    mPopWindowsShow.dismiss();
                } else if (type.equals(BEICARDNATION)) {//被申请人民族
                    mBeiSelectNation.setSelectPosition(position);
                    mBeiSelectNation.setItemVo(itemVo);
                    tv.setText(itemVo.getName());
                    mPopWindowsShow.dismiss();
                }
            }
        });

    }

    private void selectItem(ApplySeasonListPopAdapter applyPopAdapter, String type, GridLayoutManager manager) {

        if (mSelectNation == null) {
            mSelectNation = new SelectItemVo();
        }
        if (mBeiSelectNation == null) {
            mBeiSelectNation = new SelectItemVo();
        }
        if (type.equals(CARDNATION)) {
            int selectPosition = mSelectNation.getSelectPosition();
            if (selectPosition != -1) {
                applyPopAdapter.setSeletsPosition(selectPosition);
                MoveToPostion(manager, mPopWindowsShow.getRecyclerView(), selectPosition);
            }
        } else if (type.equals(BEICARDNATION)) {
            int selectPosition = mBeiSelectNation.getSelectPosition();
            if (selectPosition != -1) {
                applyPopAdapter.setSeletsPosition(selectPosition);
                MoveToPostion(manager, mPopWindowsShow.getRecyclerView(), selectPosition);
            }
        }
    }

    private String getTextStr(TextView o) {
        String trim = "";
        trim = o.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            return "";
        }
        return trim;
    }

    private void submit() {
        final JamedApplyDetailVO vo = new JamedApplyDetailVO();
        String serialno = getTextStr(mEtJamedServiceApplyNumber);
        if (!TextUtils.isEmpty(serialno)) {
            //调解编号
            vo.setSerialNo(serialno);
        }
        if (!TextUtils.isEmpty(mOrgOid)) {
            //机构
            vo.setTjOrgId(mOrgOid);
        }

        if (!TextUtils.isEmpty(mCheckBox)) {

            if (mCheckBox.equals(mCheckBoxTrue)) {
                //选中
                vo.setMediaFlag(true);

            } else if (mCheckBox.equals(mCheckBoxFalse)) {
                //未选择
                vo.setMediaFlag(false);
            }
        }


        String name = getTextStr(mEtJamedServiceApplyName);
        if (TextUtils.isEmpty(name)) {
            //申请名字
            T.showShort(mContext, getString(R.string.applyNameIsEmpty));
            return;
        }
        vo.setApplyName(name);

        if (TextUtils.isEmpty(mSex)) {
//           性别
            T.showShort(mContext, getString(R.string.sexIsempty));
            return;
        }
        vo.setApplyGender(mSex);

        String id = getTextStr(mEtJamedServiceApplyID);
        if (!TextUtils.isEmpty(id)) {
            //身份证号码
            boolean idcard = StringUtil.validateidCard(id);
            if (idcard) {
                vo.setApplyIdCard(id);
            } else {
                T.showShort(mContext, getString(R.string.pleaseIdCardIsRight));
                return;
            }
        }

        String brithday = getTextStr(mEtJamedServiceApplyBrithday);
        if (!TextUtils.isEmpty(brithday)) {
            //年龄日期
            vo.setApplyAge(brithday);
        }

        String nation = getTextStr(mTvJamedServiceApplyNation);
        if (!TextUtils.isEmpty(nation)) {
            //民族
            vo.setApplyNation(mSelectNation.getItemVo().getOid());
        }

        String telephone = getTextStr(mEtJamedServiceApplyTelephone);
        if (TextUtils.isEmpty(telephone)) {
            //联系电话
            T.showShort(mContext, getString(R.string.contactPhotoIsEmpty));
            return;
        }
        boolean phone = StringUtil.validateMoblie(telephone);
        if (phone) {
            vo.setApplyTelephone(telephone);
        } else {
            T.showShort(mContext, getString(R.string.pleasePhotoIsRight));
            return;
        }
        String here = getTextStr(mEtJamedServcieApplyHere);
        if (TextUtils.isEmpty(here)) {
            //单位住址
            T.showShort(mContext, getString(R.string.wordisEmpty));
            return;
        }
        vo.setApplyAddress(here);

        String nexus = getTextStr(mEtJamedServiceApplyNexus);
        if (!TextUtils.isEmpty(nexus)) {
            //关系
            vo.setRelation(nexus);
        }

        String beiName = getTextStr(mEtBeijamedServiceApplyName);
        if (TextUtils.isEmpty(beiName)) {
            //被申请人
            T.showShort(mContext, getString(R.string.beNameIsEmpty));
            return;
        }
        vo.setBeApplyName(beiName);

        if (!TextUtils.isEmpty(mBeiSex)) {
            //被申请人性别
            vo.setBeApplyGender(mBeiSex);
        }

        String beiBirthday = getTextStr(mEtBeijamedServiceApplyBirthday);
        if (!TextUtils.isEmpty(beiBirthday)) {
            //被申请年龄
            vo.setBeApplyAge(beiBirthday);
        }

        String beiNation = getTextStr(mTvBeijamedServiceApplyNation);
        if (!TextUtils.isEmpty(beiNation)) {
            //被申请民族
            vo.setBeApplyNation(mBeiSelectNation.getItemVo().getOid());
        }

        String beiTelephone = getTextStr(mEtBeijamedServiceApplyTelephone);
        if (TextUtils.isEmpty(beiTelephone)) {
            //被电话
            T.showShort(mContext, getString(R.string.bePhotoIsEmpty));
            return;
        }
        boolean moblie = StringUtil.validateMoblie(beiTelephone);
        if (moblie) {
            vo.setBeApplyTelephone(beiTelephone);
        } else {
            T.showShort(mContext, getString(R.string.bePhotoIsRight));
            return;
        }

        String beiHere = getTextStr(mEtBeijamedServiceApplyHere);
        if (TextUtils.isEmpty(beiHere)) {
            //被单位住址
            T.showShort(mContext, "被申请人单位或住址不能为空");
            return;
        }
        vo.setBeApplyAddress(beiHere);

        String applyCase = getTextStr(mEtJamedServiceApplyCase);
        if (TextUtils.isEmpty(applyCase)) {
            //案情
            T.showShort(mContext, getString(R.string.jamed_disputeisempty));
            return;
        }
        vo.setIntroduction(applyCase);

        String item = getTextStr(mEtJamedServiceApplyItem);
        if (TextUtils.isEmpty(item)) {
            //事项
            T.showShort(mContext, getString(R.string.jamed_partyisempty));
            return;
        }
        vo.setMatter(item);


        Log.d(TAG, "submit: " + vo);
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
                        submitService(vo);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();

    }

    private void submitService(JamedApplyDetailVO vo) {
        JamedUserService jamedUserService = new JamedUserService(mContext);
        jamedUserService.setProgressShowContent(getString(R.string.submit_ing));
        jamedUserService.setShowProgress(true);
        jamedUserService.postApplyInfo(vo, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                T.showShort(mContext, getString(R.string.submit_ok));
                finish();
            }

            @Override
            public void onError(String msg, String content) {
                T.showShort(mContext, msg);
            }
        });

    }


    private void initView() {
        mContext = this;
        mEtJamedServiceApplyNumber = (EditText) findViewById(et_jamedService_applyNumber);
        mTvJamedServiceApplyOrg = (TextView) findViewById(R.id.tv_jamedService_ApplyOrg);
        mEtJamedServiceApplyName = (EditText) findViewById(R.id.et_jamedService_ApplyName);
        mRadioJamedServiceGirl = (RadioButton) findViewById(R.id.radio_jamedService_girl);
        mRadioJamedServiceBoy = (RadioButton) findViewById(R.id.radio_jamedService_boy);
        mRdoJamedApplySex = (RadioGroup) findViewById(R.id.rdo_jamed_apply_sex);
        mEtJamedServiceApplyID = (EditText) findViewById(R.id.et_jamedService_ApplyID);
        mEtJamedServiceApplyBrithday = (TextView) findViewById(R.id.et_jamedService_ApplyBrithday);
        mEtJamedServiceApplyBrithday.setOnClickListener(this);
        mTvJamedServiceApplyNation = (TextView) findViewById(R.id.tv_jamedService_ApplyNation);
        mTvJamedServiceApplyNation.setOnClickListener(this);
        mEtJamedServiceApplyTelephone = (EditText) findViewById(R.id.et_jamedService_ApplyTelephone);
        mEtJamedServcieApplyHere = (EditText) findViewById(R.id.et_jamedServcie_ApplyHere);
        mEtJamedServiceApplyNexus = (EditText) findViewById(R.id.et_jamedService_ApplyNexus);
        mEtBeijamedServiceApplyName = (EditText) findViewById(R.id.et_BeijamedService_ApplyName);
        mRadioBeijamedServiceGril = (RadioButton) findViewById(R.id.radio_BeijamedServiceGril);
        mRadioBeijamedServcieBoy = (RadioButton) findViewById(R.id.radio_BeijamedServcieBoy);
        mRdoJamedQuiltSex = (RadioGroup) findViewById(R.id.rdo_jamed_quilt_sex);
        mEtBeijamedServiceApplyBirthday = (TextView) findViewById(R.id.et_BeijamedService_ApplyBirthday);
        mEtBeijamedServiceApplyBirthday.setOnClickListener(this);
        mTvBeijamedServiceApplyNation = (TextView) findViewById(R.id.tv_BeijamedService_ApplyNation);
        mEtBeijamedServiceApplyTelephone = (EditText) findViewById(R.id.et_BeijamedService_ApplyTelephone);
        mEtBeijamedServiceApplyHere = (EditText) findViewById(R.id.et_BeijamedService_ApplyHere);
        mEtJamedServiceApplyCase = (EditText) findViewById(R.id.et_jamedService_ApplyCase);
        mEtJamedServiceApplyItem = (EditText) findViewById(R.id.et_jamedService_ApplyItem);
        mBtnJamedServiceSubmit = (Button) findViewById(R.id.btn_jamedService_Submit);
        mChbJamedServiceMedia = (CheckBox) findViewById(R.id.chb_jamedService_media);
        mBtnJamedServiceSubmit.setOnClickListener(this);
        mTvBeijamedServiceApplyNation.setOnClickListener(this);
    }

}
