package com.lawyee.apppublic.ui.frag.fragService.media;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.DataManage;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JamedUserService;
import com.lawyee.apppublic.ui.frag.fragService.BaseFragment;
import com.lawyee.apppublic.ui.lawAdministration.ShowInfomActivity;
import com.lawyee.apppublic.vo.JamedApplyDetailVO;

import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;

public class MediaOneFragment extends BaseFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String mSecrecy = "保密";
    private String mOrgOid;
    private TextView mTvMediaOneApplyType;
    private TextView mTvMediaOneLookOrg;
    private TextView mTvMediaOneLookAgree;
    private TextView mTvMediaOneLookName;
    private TextView mTvMediaOneLookSex;
    private TextView mTvMediaOneLookCardType;
    private TextView mTvMediaOneLookAge;
    private TextView mTvMediaOneLookNation;
    private TextView mTvMediaOneLookTelephone;
    private TextView mTvMediaOneLookAddress;
    private TextView mTvMediaOneLookRelation;
    private TextView mTvMediaOneLookBeiName;
    private TextView mTvMediaOneLookBeiSex;
    private TextView mTvMediaOneLookBeiAge;
    private TextView mTvMediaOneLookBeiNation;
    private TextView mTvMediaOneLookBeiTelephone;
    private TextView mTvMediaOneLookBeiAddress;
    private TextView mTvMediaOneLookCase;
    private TextView mTvMediaOneLookItem;
    private Context mContext;
    private JamedApplyDetailVO mJamedDetailVo;
    private Button mBtnMediaOneSubmit;
    private String mStutas;

    public static MediaOneFragment newInstance(String param1, JamedApplyDetailVO param2, String mMediaStatus) {
        MediaOneFragment fragment = new MediaOneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putSerializable(ARG_PARAM2, param2);
        args.putSerializable(ARG_PARAM2, param2);
        args.putSerializable(ARG_PARAM3, mMediaStatus);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOrgOid = getArguments().getString(ARG_PARAM1);
            mJamedDetailVo = (JamedApplyDetailVO) getArguments().getSerializable(ARG_PARAM2);
            mStutas = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media_one, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mContext = getActivity();
        mTvMediaOneApplyType = (TextView) view.findViewById(R.id.tv_MediaOne_ApplyType);
        mTvMediaOneLookOrg = (TextView) view.findViewById(R.id.tv_MediaOne_LookOrg);
        mTvMediaOneLookAgree = (TextView) view.findViewById(R.id.tv_MediaOne_LookAgree);
        mTvMediaOneLookName = (TextView) view.findViewById(R.id.tv_MediaOne_LookName);
        mTvMediaOneLookSex = (TextView) view.findViewById(R.id.tv_MediaOne_LookSex);
        mTvMediaOneLookCardType = (TextView) view.findViewById(R.id.tv_MediaOne_LookCardType);
        mTvMediaOneLookAge = (TextView) view.findViewById(R.id.tv_MediaOne_LookAge);
        mTvMediaOneLookNation = (TextView) view.findViewById(R.id.tv_MediaOne_LookNation);
        mTvMediaOneLookTelephone = (TextView) view.findViewById(R.id.tv_MediaOne_LookTelephone);
        mTvMediaOneLookAddress = (TextView) view.findViewById(R.id.tv_MediaOne_LookAddress);
        mTvMediaOneLookRelation = (TextView) view.findViewById(R.id.tv_MediaOne_LookRelation);
        mTvMediaOneLookBeiName = (TextView) view.findViewById(R.id.tv_MediaOne_LookBeiName);
        mTvMediaOneLookBeiSex = (TextView) view.findViewById(R.id.tv_MediaOne_LookBeiSex);
        mTvMediaOneLookBeiAge = (TextView) view.findViewById(R.id.tv_MediaOne_LookBeiAge);
        mTvMediaOneLookBeiNation = (TextView) view.findViewById(R.id.tv_MediaOne_LookBeiNation);
        mTvMediaOneLookBeiTelephone = (TextView) view.findViewById(R.id.tv_MediaOne_LookBeiTelephone);
        mTvMediaOneLookBeiAddress = (TextView) view.findViewById(R.id.tv_MediaOne_LookBeiAddress);
        mTvMediaOneLookCase = (TextView) view.findViewById(R.id.tv_MediaOne_LookCase);
        mTvMediaOneLookItem = (TextView) view.findViewById(R.id.tv_MediaOne_LookItem);
        mBtnMediaOneSubmit = (Button) view.findViewById(R.id.btn_mediaOne_submit);
        mBtnMediaOneSubmit.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mJamedDetailVo != null) {
            boolean mediaFlag = mJamedDetailVo.isMediaFlag();
            handlerSelectShowData(mJamedDetailVo, mediaFlag);
        } else {
            requestServiceData();
        }
    }

    private void requestServiceData() {
        JamedUserService jamedUserService = new JamedUserService(mContext);
        jamedUserService.setShowProgress(true);
        jamedUserService.setProgressShowContent(getString(R.string.get_ing));
        jamedUserService.getApplyDetail(mOrgOid, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                if (values == null || values.isEmpty()) {
                    T.showShort(mContext, content);
                    return;
                }

                JamedApplyDetailVO vo = (JamedApplyDetailVO) values.get(0);
                if (vo != null) {
                    boolean mediaFlag = vo.isMediaFlag();
                    handlerSelectShowData(vo, mediaFlag);
                }
            }

            @Override
            public void onError(String msg, String content) {
                T.showShort(mContext, msg);
            }
        });
    }

    private void handlerSelectShowData(JamedApplyDetailVO vo, boolean isShow) {
        if (isShow) {
            mTvMediaOneLookCardType.setText(vo.getApplyIdCard());
            mTvMediaOneLookTelephone.setText(vo.getApplyTelephone());
            mTvMediaOneLookAddress.setText(vo.getApplyAddress());
            mTvMediaOneLookBeiTelephone.setText(vo.getBeApplyTelephone());
            mTvMediaOneLookBeiAddress.setText(vo.getBeApplyAddress());
        } else {
            mTvMediaOneLookCardType.setText(mSecrecy);
            mTvMediaOneLookTelephone.setText(mSecrecy);
            mTvMediaOneLookAddress.setText(mSecrecy);
            mTvMediaOneLookBeiTelephone.setText(mSecrecy);
            mTvMediaOneLookBeiAddress.setText(mSecrecy);
        }
        //当事人是否同意
        if (vo.isMediaFlag()) {
            mBtnMediaOneSubmit.setVisibility(View.GONE);
        } else {
            if (!mStutas.equals(ShowInfomActivity.statusOneBegin) && !mStutas.equals(ShowInfomActivity.statusThreeOrgNoAgree))
                if (vo.getMediaApplyType()!=null&&vo.getMediaApplyType().equals("2")) {
                    mBtnMediaOneSubmit.setVisibility(View.GONE);
                } else {
                    mBtnMediaOneSubmit.setVisibility(View.VISIBLE);
                }
        }
        mTvMediaOneApplyType.setText(getStrWithBoolean(vo.isTjType()));
        mTvMediaOneLookOrg.setText(vo.getTjOrgName());
        mTvMediaOneLookAgree.setText(getStringWithBoolean(vo.isMediaFlag()));
        mTvMediaOneLookName.setText(vo.getApplyName());
        mTvMediaOneLookSex.setText(DataManage.getInstance().getNameWithOid(vo.getApplyGender()));
        mTvMediaOneLookAge.setText(vo.getApplyAge());
        mTvMediaOneLookNation.setText(DataManage.getInstance().getNameWithOid(vo.getApplyNation()));
        mTvMediaOneLookRelation.setText(vo.getRelation());
        mTvMediaOneLookBeiName.setText(vo.getBeApplyName());
        mTvMediaOneLookBeiSex.setText(DataManage.getInstance().getNameWithOid(vo.getBeApplyGender()));
        mTvMediaOneLookBeiAge.setText(vo.getBeApplyAge());
        mTvMediaOneLookBeiNation.setText(DataManage.getInstance().getNameWithOid(vo.getBeApplyNation()));
        mTvMediaOneLookCase.setText(vo.getIntroduction());
        mTvMediaOneLookItem.setText(vo.getMatter());

    }

    protected String getStringWithBoolean(boolean str) {
        String type = "";
        if (str) {
            type = getString(R.string.agree);
        } else {
            type = getString(R.string.no_agree);
        }
        return type;
    }

    protected String getStrWithBoolean(boolean str) {
        String type = "";
        if (str) {
            type = "专业调解";
        } else {
            type = "一般调解";
        }
        return type;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mediaOne_submit:
                MaterialDialog.Builder showDialog = getShowDialog();
                showDialog.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        submitServiceData();
                        materialDialog.dismiss();
                    }
                });
                showDialog.onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        materialDialog.dismiss();
                    }
                });
                break;
            default:
                break;
        }

    }

    private void submitServiceData() {
        JamedUserService jamedUserService = new JamedUserService(mContext);
        jamedUserService.setShowProgress(true);
        jamedUserService.setProgressShowContent(getString(R.string.submit_success));
        jamedUserService.postMediaApply(mOrgOid, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                if (values == null || values.isEmpty()) {
                    T.showShort(mContext, content);
                    return;
                }
                T.showShort(mContext, getString(R.string.submit_ok));
            }

            @Override
            public void onError(String msg, String content) {
                T.showShort(mContext, msg);
            }
        });

    }
}
