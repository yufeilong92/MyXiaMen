package com.lawyee.apppublic.ui.frag.fragService.media;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JamedUserService;
import com.lawyee.apppublic.ui.frag.fragService.BaseFragment;
import com.lawyee.apppublic.ui.lawAdministration.ShowInfomActivity;
import com.lawyee.apppublic.util.ShowOrHide;
import com.lawyee.apppublic.vo.JamedApplyDetailVO;

import net.lawyee.mobilelib.utils.T;
import net.lawyee.mobilelib.utils.TimeUtil;

import java.util.ArrayList;

public class MediaThreeFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private String mOrgID;
    private String mMediaStutas;
    private TextView mTvMediaThreeBeginTime;
    private EditText mEtMediaThreeAddress;
    private Button mBtnMediaThreeSubmit;
    private LinearLayout mLinearMediathreeApply;
    private TextView mTvMediathreeResulttime;
    private TextView mTvMediathreeResultaddress;
    private LinearLayout mLiearMediathreeResult;
    private Context mContext;
    private JamedApplyDetailVO mJamedDetailVo;

    public MediaThreeFragment() {
    }

    public static MediaThreeFragment newInstance(String param1, String param2, JamedApplyDetailVO mJamedDetailVo) {
        MediaThreeFragment fragment = new MediaThreeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putSerializable(ARG_PARAM3, mJamedDetailVo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOrgID = getArguments().getString(ARG_PARAM1);
            mMediaStutas = getArguments().getString(ARG_PARAM2);
            mJamedDetailVo = (JamedApplyDetailVO) getArguments().getSerializable(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media_three, container, false);
        initView(view);
        return view;
    }


    private void initView(View view) {
        mTvMediaThreeBeginTime = (TextView) view.findViewById(R.id.tv_MediaThree_BeginDate);
        mTvMediaThreeBeginTime.setOnClickListener(this);
        mEtMediaThreeAddress = (EditText) view.findViewById(R.id.et_MediaThree_Address);
        mBtnMediaThreeSubmit = (Button) view.findViewById(R.id.btn_MediaThree_Submit);
        mLinearMediathreeApply = (LinearLayout) view.findViewById(R.id.linear_mediathree_apply);
        mTvMediathreeResulttime = (TextView) view.findViewById(R.id.tv_mediathree_resulttime);
        mTvMediathreeResultaddress = (TextView) view.findViewById(R.id.tv_mediathree_resultaddress);
        mLiearMediathreeResult = (LinearLayout) view.findViewById(R.id.liear_mediathree_result);
        mContext = getActivity();
        mBtnMediaThreeSubmit.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mJamedDetailVo!=null){
            String recordTime = mJamedDetailVo.getRecordTime();
            String recordAddress = mJamedDetailVo.getRecordAddress();
            if (TextUtils.isEmpty(recordTime) || TextUtils.isEmpty(recordAddress)) {
                isShowResultData(false, null);
            } else {
                isShowResultData(true, mJamedDetailVo);
            }
        }else {
        requestServiceData();
        }
    }


    private void requestServiceData() {
        JamedUserService jamedUserService = new JamedUserService(mContext);
        jamedUserService.setProgressShowContent(getString(R.string.get_ing));
        jamedUserService.setShowProgress(true);
        jamedUserService.getApplyDetail(mOrgID, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                if (values == null || values.isEmpty()) {
                    T.showShort(mContext, content);
                    return;
                }
                JamedApplyDetailVO vo = (JamedApplyDetailVO) values.get(0);
                if (vo != null) {
                    String recordTime = vo.getRecordTime();
                    String recordAddress = vo.getRecordAddress();
                    if (TextUtils.isEmpty(recordTime) || TextUtils.isEmpty(recordAddress)) {
                        isShowResultData(false, null);
                    } else {
                        isShowResultData(true, vo);
                    }
                }
            }

            @Override
            public void onError(String msg, String content) {
                T.showShort(mContext, msg);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_MediaThree_Submit:
                submit();
                break;
            case R.id.tv_MediaThree_BeginDate:
                ShowOrHide.showDataDialog(mContext, mTvMediaThreeBeginTime);
                break;
        }
    }

    private void submit() {
        final String begintime = getTextStr(mTvMediaThreeBeginTime);
        if (TextUtils.isEmpty(begintime)) {
            T.showShort(mContext, getString(R.string.pleaserecordTime));
            return;
        }
        final String address = getTextStr(mEtMediaThreeAddress);
        if (TextUtils.isEmpty(address)) {
            T.showShort(mContext, getString(R.string.pleaserecordAddress));
            return;
        }

        MaterialDialog.Builder builder = getShowDialog();
        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                submitServcie(begintime, address);
                materialDialog.dismiss();
            }
        });
        builder.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                materialDialog.dismiss();
            }
        });
    }

    private void submitServcie(String begintime, String address) {
        JamedUserService jamedUserService = new JamedUserService(mContext);
        jamedUserService.setShowProgress(true);
        jamedUserService.setProgressShowContent(getString(R.string.submit_ing));
        jamedUserService.postMediaRecord(mOrgID, begintime, address, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                if (values == null ||values.isEmpty()) {
                    T.showShort(mContext, content);
                    return;
                }
                T.showShort(mContext, getString(R.string.submit_success));
                JamedApplyDetailVO vo = (JamedApplyDetailVO) values.get(0);
                if (vo != null) {
                    ShowInfomActivity activity = (ShowInfomActivity) getActivity();
                    activity.selectNextFourBtnSet(true);
                    isShowResultData(true, vo);
                }
            }

            @Override
            public void onError(String msg, String content) {
                T.showShort(mContext, msg);
            }
        });

    }

    private void isShowResultData(boolean b, JamedApplyDetailVO vo) {
        if (b) {
            mLiearMediathreeResult.setVisibility(View.VISIBLE);
            mLinearMediathreeApply.setVisibility(View.GONE);
            if (vo != null) {
                String ymdt = TimeUtil.getYMDT(vo.getRecordTime());
                mTvMediathreeResulttime.setText(ymdt);
                mTvMediathreeResultaddress.setText(vo.getRecordAddress());
            }
        } else {
            mLiearMediathreeResult.setVisibility(View.GONE);
            mLinearMediathreeApply.setVisibility(View.VISIBLE);
        }

    }
}
