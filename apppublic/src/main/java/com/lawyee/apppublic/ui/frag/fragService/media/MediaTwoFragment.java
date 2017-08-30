package com.lawyee.apppublic.ui.frag.fragService.media;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.ApplySeasonListPopAdapter;
import com.lawyee.apppublic.config.DataManage;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JamedUserService;
import com.lawyee.apppublic.ui.frag.fragService.BaseFragment;
import com.lawyee.apppublic.ui.lawAdministration.ShowInfomActivity;
import com.lawyee.apppublic.vo.BaseCommonDataVO;
import com.lawyee.apppublic.vo.JamedApplyDetailVO;
import com.lawyee.apppublic.vo.SelectItemVo;
import com.lawyee.apppublic.widget.ContentEditText;
import com.lawyee.apppublic.widget.RecycleViewDivider;

import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
import java.util.List;

import static com.lawyee.apppublic.util.RecyclerSelectItem.MoveToPostion;

public class MediaTwoFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    /**
     * 机构id
     */
    private String mOrgId;
    /**
     * 审核状态
     */
    private String mMediaStutas;
    private RadioButton mRdbMediaTwoPass;
    private RadioButton mRdbMediaTwoNopass;
    private ContentEditText mEtMediaTwoOtherExplain;
    private Button mBtnMediaTwoSubmit;
    private String mAgree = "";
    private String mPass = "1";//同意
    private String mNoPass = "-1";//不同意
    private String mJmaedPeople = "2";//调解员
    private TextView mTvMediaTwoNoJoinPass;
    private TextView mTvMediatwoResultagree;
    private TextView mTvMediatwoResultreason;
    private TextView mTvMediatwoResultexplain;
    private TextView mTvMediaTwoMresultagree;
    private TextView mTvMediaTwoMreasultReason;
    private TextView mTvMediaTwoMreasultExplain;
    private LinearLayout mLinearMediaTwoResult;
    private List<BaseCommonDataVO> mNoHandlerLists;
    private MaterialDialog mPopWinowsShow;
    private SelectItemVo mSelectNoAgreeItem;
    private Context mContext;
    private LinearLayout mLinearMediaTwoApply;
    /**
     * 发起人
     */
    private String mMediaApplyType;
    private TextView mTvMediaTwoJamedAgree;
    /**
     * 是否勾选媒体调解
     */
    private boolean mMediaFlag;
    private LinearLayout mLinearMediaTwoMResult;
    private JamedApplyDetailVO mJamedDetailVo;


    public static MediaTwoFragment newInstance(String param1, String param2, String param3, boolean param4, JamedApplyDetailVO mJamedDetailVo) {
        MediaTwoFragment fragment = new MediaTwoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putBoolean(ARG_PARAM4, param4);
        args.putSerializable(ARG_PARAM5, mJamedDetailVo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOrgId = getArguments().getString(ARG_PARAM1);
            mMediaStutas = getArguments().getString(ARG_PARAM2);
            mMediaApplyType = getArguments().getString(ARG_PARAM3);
            mMediaFlag = getArguments().getBoolean(ARG_PARAM4);
            mJamedDetailVo = (JamedApplyDetailVO) getArguments().getSerializable(ARG_PARAM5);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media_two, container, false);
        initView(view);
        initAcceptSeasons();
        return view;
    }

    private void initAcceptSeasons() {
        if (mNoHandlerLists == null || mNoHandlerLists.isEmpty()) {
            mNoHandlerLists = new ArrayList<>();
            List<BaseCommonDataVO> mNoHandlers = DataManage.getInstance().getmApplyJamedNoHandler();
            if (mNoHandlers != null && !mNoHandlers.isEmpty()) {
                mNoHandlerLists = mNoHandlers;
            }
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        handlerShowData();
        handlerData();
    }

    private void handlerShowData() {
        if (!mMediaFlag) {
            if (mMediaApplyType == null || !mMediaApplyType.equals(mJmaedPeople)) {
                mTvMediaTwoJamedAgree.setVisibility(View.GONE);
            } else if (mMediaApplyType.equals(mJmaedPeople)) {
                mTvMediaTwoJamedAgree.setVisibility(View.VISIBLE);
            }
        }
        if (mMediaStutas.equals(ShowInfomActivity.statusTwoOrgAgree)) {//机构受理
            if (mJamedDetailVo != null) {
                String applyMediaConfirm = mJamedDetailVo.getApplyMediaConfirm();
                String mediaApplyType = mJamedDetailVo.getMediaApplyType();
                int mediaConfirm = mJamedDetailVo.getMediaConfirm();
                if (mediaApplyType != null && mediaApplyType.equals("3") && applyMediaConfirm.equals("1")) {//最终确认媒体参与
                    requestServiceData(false, true);
                } else if (mediaApplyType != null && mediaApplyType.equals("3") && applyMediaConfirm.equals("-1")) {
                    requestServiceData(false, true);
                } else if (mediaConfirm == 1) {
                    requestServiceData(false, true);
                } else if (mediaConfirm == -1) {
                    requestServiceData(false, true);
                } else {
                    requestServiceData(true, false);
                }
            }
        } else if (mMediaStutas.equals(ShowInfomActivity.statusThreeOrgNoAgree)) {//机构不受理
            requestServiceData(false, false);
        } else if (mMediaStutas.equals(ShowInfomActivity.statusFourMediaAgree)) {//媒体已受理
            requestServiceData(false, true);
        } else if (mMediaStutas.equals(ShowInfomActivity.statusFiveMeidaNoAgree)) {//媒体不受理
            requestServiceData(false, true);
        } else if (mMediaStutas.equals(ShowInfomActivity.statusSixFinish)) {//调解结束
            requestServiceData(false, true);
        } else {
            isShowResult(false, false, null);
        }

    }

    /**
     * @param isShowApply 是否显示申请页
     * @param isShow      是否显示筛选结果
     */
    private void requestServiceData(final boolean isShowApply, final boolean isShow) {
        if (mJamedDetailVo != null) {
            if (isShowApply) {
                isShowResult(false, isShow, mJamedDetailVo);
            } else {
                isShowResult(true, isShow, mJamedDetailVo);
            }
        } else {
            JamedUserService jamedUserService = new JamedUserService(mContext);
            jamedUserService.setProgressShowContent(getString(R.string.get_ing));
            jamedUserService.setShowProgress(true);
            jamedUserService.getApplyDetail(mOrgId, new BaseJsonService.IResultInfoListener() {
                @Override
                public void onComplete(ArrayList<Object> values, String content) {
                    if (values == null || values.isEmpty()) {
                        T.showShort(mContext, content);
                        return;
                    }
                    JamedApplyDetailVO vo = (JamedApplyDetailVO) values.get(0);
                    if (vo != null) {
                        if (isShowApply) {
                            isShowResult(false, isShow, vo);
                        } else {
                            isShowResult(true, isShow, vo);
                        }
                    }
                }

                @Override
                public void onError(String msg, String content) {
                    T.showShort(mContext, msg);
                }
            });
        }
    }

    private void handlerData() {
        mRdbMediaTwoPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mAgree = mPass;
                    mTvMediaTwoNoJoinPass.setClickable(false);
                    if (mSelectNoAgreeItem != null) {
                        mSelectNoAgreeItem.setItemVo(null);
                        mSelectNoAgreeItem.setSelectPosition(-1);
                        mTvMediaTwoNoJoinPass.setText(getResources().getString(R.string.please_select_nopassreason));
                    }
                }
            }
        });
        mRdbMediaTwoNopass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mAgree = mNoPass;
                    mTvMediaTwoNoJoinPass.setClickable(true);
                }
            }
        });


    }

    private void initView(View view) {
        mContext = getActivity();
        mRdbMediaTwoPass = (RadioButton) view.findViewById(R.id.rdb_MediaTwo_Pass);
        mRdbMediaTwoNopass = (RadioButton) view.findViewById(R.id.rdb_MediaTwo_Nopass);
        mEtMediaTwoOtherExplain = (ContentEditText) view.findViewById(R.id.et_MediaTwo_OtherExplain);
        mBtnMediaTwoSubmit = (Button) view.findViewById(R.id.btn_mediaTwo_submit);
        mTvMediaTwoJamedAgree = (TextView) view.findViewById(R.id.tv_mediatwo_jamedagree);

        mBtnMediaTwoSubmit.setOnClickListener(this);
        mRdbMediaTwoNopass.setOnClickListener(this);
        mRdbMediaTwoPass.setOnClickListener(this);
        mTvMediaTwoNoJoinPass = (TextView) view.findViewById(R.id.tv_MediaTwo_NoJoinPass);
        mTvMediaTwoNoJoinPass.setOnClickListener(this);
        mTvMediatwoResultagree = (TextView) view.findViewById(R.id.tv_mediatwo_resultagree);
        mTvMediatwoResultagree.setOnClickListener(this);
        mTvMediatwoResultreason = (TextView) view.findViewById(R.id.tv_mediatwo_resultreason);
        mTvMediatwoResultreason.setOnClickListener(this);
        mTvMediatwoResultexplain = (TextView) view.findViewById(R.id.tv_mediatwo_resultexplain);
        mTvMediatwoResultexplain.setOnClickListener(this);
        mTvMediaTwoMresultagree = (TextView) view.findViewById(R.id.tv_MediaTwo_Mresultagree);
        mTvMediaTwoMresultagree.setOnClickListener(this);
        mTvMediaTwoMreasultReason = (TextView) view.findViewById(R.id.tv_MediaTwo_MreasultReason);
        mTvMediaTwoMreasultReason.setOnClickListener(this);
        mTvMediaTwoMreasultExplain = (TextView) view.findViewById(R.id.tv_MediaTwo_MreasultExplain);
        mTvMediaTwoMreasultExplain.setOnClickListener(this);
        mLinearMediaTwoResult = (LinearLayout) view.findViewById(R.id.linear_MediaTwo_Result);
        mLinearMediaTwoApply = (LinearLayout) view.findViewById(R.id.linear_mediatwo_apply);
        mLinearMediaTwoMResult = (LinearLayout) view.findViewById(R.id.linear_mediatwo_Mreasult);
        mLinearMediaTwoResult.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mediaTwo_submit:
                submit();
                break;
            case R.id.tv_MediaTwo_NoJoinPass:

                String str = getTextStr(mTvMediaTwoNoJoinPass);
                if (mNoHandlerLists != null && !mNoHandlerLists.isEmpty())
                    handlerPopWindos(mTvMediaTwoNoJoinPass, mNoHandlerLists, str, null);
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
        final ApplySeasonListPopAdapter applyPopAdapter = new ApplySeasonListPopAdapter(mData, getContext());
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        if (mPopWinowsShow == null || !mPopWinowsShow.isShowing()) {
            mPopWinowsShow = new MaterialDialog.Builder(getContext())
                    .adapter(applyPopAdapter, manager)
                    .show();
            mPopWinowsShow.getRecyclerView().addItemDecoration(new RecycleViewDivider(getContext(), GridLayoutManager.VERTICAL, R.drawable.bg_rlv_diving));
        }
        if (!TextUtils.isEmpty(nation))
            applyPopAdapter.setSeletsStr(nation);
        if (mSelectNoAgreeItem == null) {
            mSelectNoAgreeItem = new SelectItemVo();
        }
        int selectPosition = mSelectNoAgreeItem.getSelectPosition();
        if (selectPosition != -1) {
            applyPopAdapter.setSeletsPosition(selectPosition);
            MoveToPostion(manager, mPopWinowsShow.getRecyclerView(), selectPosition);
        }

        applyPopAdapter.setOnRecyclerItemClickListener(new ApplySeasonListPopAdapter.OnRecyclerItemClickListener() {
            @Override
            public void OnItemClickListener(View view, BaseCommonDataVO itemVo, int position) {
                mSelectNoAgreeItem.setItemVo(itemVo);
                mSelectNoAgreeItem.setSelectPosition(position);
                tv.setText(itemVo.getName());
                mPopWinowsShow.dismiss();
            }
        });

    }


    private void submit() {
        final MediaTwoApplyVo vo = new MediaTwoApplyVo();
        if (TextUtils.isEmpty(mAgree)) {
            T.showShort(mContext, getString(R.string.pleaseSelectResult));
            return;
        }
        vo.setIsAgree(mAgree);
        if (mAgree.equals(mNoPass)) {
            String reason = getTextStr(mTvMediaTwoNoJoinPass);
            if (TextUtils.isEmpty(reason) || reason.equals(getResources().getString(R.string.please_select_nopassreason))) {
                T.showShort(mContext, getString(R.string.please_select_nopassreason));
                return;
            } else {
                vo.setReason(mSelectNoAgreeItem.getItemVo().getOid());
            }
        }
        String explain = getTextStr(mEtMediaTwoOtherExplain);
        if (!TextUtils.isEmpty(explain)) {
            vo.setExplain(explain);
        }

        MaterialDialog.Builder showDialog = getShowDialog();
        showDialog.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                submitService(vo);
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

    private void submitService(MediaTwoApplyVo vo) {
        JamedUserService jamedUserService = new JamedUserService(mContext);
        jamedUserService.setProgressShowContent(getString(R.string.submit_ing));
        jamedUserService.setShowProgress(true);
        jamedUserService.postMediaAccept(mOrgId, vo.getIsAgree(), vo.getExplain(), vo.getReason(), new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                if (values == null || values.isEmpty()) {
                    T.showShort(mContext, content);
                    return;
                }
                JamedApplyDetailVO vo = (JamedApplyDetailVO) values.get(0);
                if (vo != null) {
                    int mediaConfirm = vo.getMediaConfirm();
                    ShowInfomActivity activity = (ShowInfomActivity) getActivity();
                    activity.selectNextBtnSet(false, false);
                    isShowResult(true, true, vo);
                }
            }

            @Override
            public void onError(String msg, String content) {
                T.showShort(mContext, msg);
            }
        });
    }

    /**
     * @param ishow          是否显示结果
     * @param isShowMreasult 显示筛选结果
     * @param vo
     */
    private void isShowResult(boolean ishow, boolean isShowMreasult, JamedApplyDetailVO vo) {
        if (ishow) {
            mLinearMediaTwoResult.setVisibility(View.VISIBLE);//媒体参与信息结果
            mLinearMediaTwoApply.setVisibility(View.GONE);//媒体筛选
            if (isShowMreasult) {
                mLinearMediaTwoMResult.setVisibility(View.VISIBLE);//媒体筛选结果
            } else {
                mLinearMediaTwoMResult.setVisibility(View.GONE);
            }
            String mediaApplyType = vo.getMediaApplyType();
            String applyMediaConfirm = vo.getApplyMediaConfirm();
            if (mediaApplyType != null && mediaApplyType.equals("3") && applyMediaConfirm.equals("1")) {//最终确认媒体参与
                mTvMediatwoResultagree.setText(getStringWithInt(1));
                mTvMediatwoResultreason.setText(DataManage.getInstance().getNameWithOid(vo.getMediaNoAcceptReason()));
                mTvMediatwoResultexplain.setText(vo.getMediaOpinion());
                mTvMediaTwoMresultagree.setText(getMediaStringWithString(vo.getApplyMediaConfirm()));
                mTvMediaTwoMreasultReason.setText(DataManage.getInstance().getNameWithOid(vo.getApplynoAcceptReason()));
                mTvMediaTwoMreasultExplain.setText(vo.getApplyOpinion());
            } else if (mediaApplyType != null && mediaApplyType.equals("3") && applyMediaConfirm.equals("-1")) {
                mTvMediatwoResultagree.setText(getStringWithInt(1));
                mTvMediatwoResultreason.setText(DataManage.getInstance().getNameWithOid(vo.getMediaNoAcceptReason()));
                mTvMediatwoResultexplain.setText(vo.getMediaOpinion());
                mTvMediaTwoMresultagree.setText(getMediaStringWithString(vo.getApplyMediaConfirm()));
                mTvMediaTwoMreasultReason.setText(DataManage.getInstance().getNameWithOid(vo.getApplynoAcceptReason()));
                mTvMediaTwoMreasultExplain.setText(vo.getApplyOpinion());
            } else if (vo != null) {//正常的筛选
                mTvMediatwoResultagree.setText(getStringWithInt(vo.getMediaConfirm()));
                mTvMediatwoResultreason.setText(DataManage.getInstance().getNameWithOid(vo.getMediaNoAcceptReason()));
                mTvMediatwoResultexplain.setText(vo.getMediaOpinion());
                mTvMediaTwoMresultagree.setText(getMediaStringWithString(vo.getApplyMediaConfirm()));
                mTvMediaTwoMreasultReason.setText(DataManage.getInstance().getNameWithOid(vo.getApplynoAcceptReason()));
                mTvMediaTwoMreasultExplain.setText(vo.getApplyOpinion());
            }

        } else {
            mLinearMediaTwoApply.setVisibility(View.VISIBLE);
            mLinearMediaTwoResult.setVisibility(View.GONE);
            mLinearMediaTwoMResult.setVisibility(View.GONE);
        }
    }

    private static final class MediaTwoApplyVo {
        private String isAgree;
        private String reason;
        private String explain;

        public String getIsAgree() {
            return isAgree;
        }

        public void setIsAgree(String isAgree) {
            this.isAgree = isAgree;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }
    }

    /**
     * 媒体筛选结果
     *
     * @param str
     * @return
     */
    private String getMediaStringWithString(String str) {
        String type = "";
        if (str.equals("1")) {
            type = getString(R.string.yes);
        } else if (str.equals("-1")) {
            type = getString(R.string.no);
        } else if (str.equals("0")) {
            type = getString(R.string.isconfirm);
        }
        return type;
    }

    /**
     * 筛选通过
     *
     * @param str
     * @return
     */
    private String getStringWithInt(int str) {
        String type = "";
        switch (str) {
            case 1:
                type = getString(R.string.yes);
                break;
            case -1:
                type = getString(R.string.no);
                break;
            default:
                break;
        }
        return type;
    }

}
