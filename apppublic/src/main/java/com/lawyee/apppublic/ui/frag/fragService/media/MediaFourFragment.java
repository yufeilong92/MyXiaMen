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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JamedUserService;
import com.lawyee.apppublic.ui.frag.fragService.BaseFragment;
import com.lawyee.apppublic.util.ShowOrHide;
import com.lawyee.apppublic.vo.JamedApplyDetailVO;

import net.lawyee.mobilelib.utils.T;
import net.lawyee.mobilelib.utils.TimeUtil;

import java.util.ArrayList;

public class MediaFourFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mOrgID;
    private EditText mEtMediaFourPlay;
    private TextView mTvMediaFourPlayDate;
    private EditText mEtMediaFourAct;
    private EditText mEtMediaFourVideoHere;
    private RadioButton mRdbMediaFourYes;
    private RadioButton mRdbMediaFourNo;
    private Button mBtnMediaFourSubmit;
    private LinearLayout mLinearMediafourApply;
    private TextView mTvMediafourResultPlayChannel;
    private TextView mTvMediafourResultvplaytime;
    private TextView mTvMediafourResultProgramTitle;
    private TextView mTvMediafourResultmediaPlayUrl;
    private TextView mTvMediafourResultnetflag;
    private LinearLayout mLinearMediafourResult;
    private Context mContext;

    private String mIsNetFlag = ""; //是否挂网
    private String mNetFlagYes = "1";//是
    private String mNetFlagNo = "0";//否
    private JamedApplyDetailVO mJamedDetailVo;

    public MediaFourFragment() {
    }

    public static MediaFourFragment newInstance(String param1, JamedApplyDetailVO param2) {
        MediaFourFragment fragment = new MediaFourFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putSerializable(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOrgID = getArguments().getString(ARG_PARAM1);
            mJamedDetailVo = (JamedApplyDetailVO) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media_four, container, false);
        initView(view);
        return view;
    }


    private void initView(View view) {
        mEtMediaFourPlay = (EditText) view.findViewById(R.id.Et_MediaFour_Play);
        mTvMediaFourPlayDate = (TextView) view.findViewById(R.id.tv_MediaFour_PlayDate);
        mTvMediaFourPlayDate.setOnClickListener(this);
        mEtMediaFourAct = (EditText) view.findViewById(R.id.et_MediaFour_Act);
        mEtMediaFourVideoHere = (EditText) view.findViewById(R.id.Et_MediaFour_VideoHere);
        mRdbMediaFourYes = (RadioButton) view.findViewById(R.id.rdb_MediaFour_Yes);
        mRdbMediaFourNo = (RadioButton) view.findViewById(R.id.rdb_MediaFour_No);
        mBtnMediaFourSubmit = (Button) view.findViewById(R.id.btn_MediaFour_Submit);
        mLinearMediafourApply = (LinearLayout) view.findViewById(R.id.linear_mediafour_apply);
        mTvMediafourResultPlayChannel = (TextView) view.findViewById(R.id.tv_mediafourResultPlayChannel);
        mTvMediafourResultvplaytime = (TextView) view.findViewById(R.id.tv_mediafourResultvplaytime);
        mTvMediafourResultProgramTitle = (TextView) view.findViewById(R.id.tv_mediafourResultProgramTitle);
        mTvMediafourResultmediaPlayUrl = (TextView) view.findViewById(R.id.tv_mediafourResultmediaPlayUrl);
        mTvMediafourResultnetflag = (TextView) view.findViewById(R.id.tv_mediafourResultnetflag);
        mLinearMediafourResult = (LinearLayout) view.findViewById(R.id.linear_mediafour_result);
        mContext = getActivity();
        mBtnMediaFourSubmit.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mJamedDetailVo!=null){
            if (TextUtils.isEmpty(mJamedDetailVo.getPlaytime()) ) {
                isShowResult(false, null);
            } else {
                isShowResult(true, mJamedDetailVo);
            }
        }else {
            requestServiceData();
        }

        handlderData();
    }

    private void requestServiceData() {
        JamedUserService jamedUserService = new JamedUserService(mContext);
        jamedUserService.setProgressShowContent(getString(R.string.get_ing));
        jamedUserService.setShowProgress(true);
        jamedUserService.getApplyDetail(mOrgID, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                if (values == null || values.isEmpty()) {
                    T.showLong(mContext, content);
                    return;
                }
                JamedApplyDetailVO vo = (JamedApplyDetailVO) values.get(0);
                if (vo != null) {

                    if (TextUtils.isEmpty(vo.getNetFlag()) || TextUtils.isEmpty(vo.getPlaytime()) ||
                            TextUtils.isEmpty(vo.getPlayChannel()) || TextUtils.isEmpty(vo.getProgramTitle()) ||
                            TextUtils.isEmpty(vo.getMediaPlayUrl())) {
                        isShowResult(false, null);
                    } else {
                        isShowResult(true, vo);
                    }
                }
            }


            @Override
            public void onError(String msg, String content) {
                T.showShort(mContext, msg);
            }
        });

    }

    private void handlderData() {
        mRdbMediaFourYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mIsNetFlag = mNetFlagYes;
                }
            }
        });
        mRdbMediaFourNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mIsNetFlag = mNetFlagNo;
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_MediaFour_Submit:
                submit();
                break;
            case R.id.tv_MediaFour_PlayDate:
                ShowOrHide.showDataDialog(mContext, mTvMediaFourPlayDate);
                break;
        }
    }

    private void submit() {
        final MediaFourApplyVo vo = new MediaFourApplyVo();

        String play = getTextStr(mEtMediaFourPlay);
        if (TextUtils.isEmpty(play)) {
            T.showShort(mContext, getString(R.string.pleaseplayChannel));
            return;
        }
        vo.setPlayChannel(play);

        String playtime = getTextStr(mTvMediaFourPlayDate);
        if (TextUtils.isEmpty(playtime)) {
            T.showShort(mContext, getString(R.string.pleaseplaytime));
            return;
        }
        vo.setPlaytime(playtime);

        String act = getTextStr(mEtMediaFourAct);
        if (TextUtils.isEmpty(act)) {
            T.showShort(mContext, getString(R.string.pleaseprogramTitle));
            return;
        }
        vo.setProgramTitle(act);

        String viderhere = getTextStr(mEtMediaFourVideoHere);
        if (TextUtils.isEmpty(viderhere)) {
            T.showShort(mContext, getString(R.string.pleasemediaPlayUrl));
            return;
        }
        vo.setMediaPlayUrl(viderhere);

        if (TextUtils.isEmpty(mIsNetFlag)) {
            T.showShort(mContext, getString(R.string.pleasenetFlag));
            return;
        }
        vo.setNetFlag(mIsNetFlag);
        MaterialDialog.Builder showDialog = getShowDialog();
        showDialog.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                submitServiceData(vo);
                materialDialog.dismiss();
            }
        });
        showDialog.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                materialDialog.dismiss();
            }
        });

    }

    private void submitServiceData(MediaFourApplyVo vo) {
        JamedUserService jamedUserService = new JamedUserService(mContext);
        jamedUserService.setShowProgress(true);
        jamedUserService.setProgressShowContent(getString(R.string.submit_ing));
        jamedUserService.postMediaPlay(mOrgID, vo.getPlayChannel(), vo.getPlaytime(), vo.getProgramTitle(), vo.getMediaPlayUrl()
                , vo.getNetFlag(), new BaseJsonService.IResultInfoListener() {
                    @Override
                    public void onComplete(ArrayList<Object> values, String content) {
                        if (values == null || values.isEmpty() || !(values.get(0) instanceof JamedApplyDetailVO)) {
                            T.showShort(mContext, content);
                            return;
                        }
                        T.showShort(mContext, getString(R.string.submit_success));
                        JamedApplyDetailVO vo = (JamedApplyDetailVO) values.get(0);
                        if (vo != null) {
                            isShowResult(true, vo);
                        }
                    }

                    @Override
                    public void onError(String msg, String content) {

                    }
                });
    }

    private void isShowResult(boolean b, JamedApplyDetailVO vo) {
        if (b) {
            mLinearMediafourApply.setVisibility(View.GONE);
            mLinearMediafourResult.setVisibility(View.VISIBLE);
            if (vo != null) {
                String ymdt = TimeUtil.getYMDT(vo.getPlaytime());
                mTvMediafourResultvplaytime.setText(ymdt);
                mTvMediafourResultmediaPlayUrl.setText(vo.getMediaPlayUrl());
                mTvMediafourResultnetflag.setText(getStringWithStr(vo.getNetFlag()));
                mTvMediafourResultPlayChannel.setText(vo.getPlayChannel());
                mTvMediafourResultProgramTitle.setText(vo.getProgramTitle());
            }
        } else {
            mLinearMediafourResult.setVisibility(View.GONE);
            mLinearMediafourApply.setVisibility(View.VISIBLE);
        }

    }

    private String getStringWithStr(String str) {
        String a = "";
        if (str.equals("0")) {
            a = getString(R.string.no);
        } else if (str.equals("1")) {
            a = getString(R.string.yes);
        }
        return a;
    }

    private static class MediaFourApplyVo {
        private String playChannel;
        private String playtime;
        private String programTitle;
        private String mediaPlayUrl;
        private String netFlag;

        public String getPlayChannel() {
            return playChannel;
        }

        public void setPlayChannel(String playChannel) {
            this.playChannel = playChannel;
        }

        public String getPlaytime() {
            return playtime;
        }

        public void setPlaytime(String playtime) {
            this.playtime = playtime;
        }

        public String getProgramTitle() {
            return programTitle;
        }

        public void setProgramTitle(String programTitle) {
            this.programTitle = programTitle;
        }

        public String getMediaPlayUrl() {
            return mediaPlayUrl;
        }

        public void setMediaPlayUrl(String mediaPlayUrl) {
            this.mediaPlayUrl = mediaPlayUrl;
        }

        public String getNetFlag() {
            return netFlag;
        }

        public void setNetFlag(String netFlag) {
            this.netFlag = netFlag;
        }
    }

}
