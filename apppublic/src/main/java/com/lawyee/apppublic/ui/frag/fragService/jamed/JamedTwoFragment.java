package com.lawyee.apppublic.ui.frag.fragService.jamed;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
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

public class JamedTwoFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    /**
     * 当事人是否勾选媒体调解
     */
    private boolean mMediaFlag;
    private String mOrgId;
    private String mIsAccept;
    private String mAccept = "1";
    private String mNoAccept = "-1";

    private String mIsJoinMedia;
    private String mJoinMedia = "yes";
    private String mNoJoinMedia = "no";

    private List<BaseCommonDataVO> mNoHandlerLists;
    private MaterialDialog mPopWinowsShow;
    private SelectItemVo mSelectNoHandlerItem;
    private Context mContext;
    private RadioButton mRdbJamedTwoAccept;
    private RadioButton mRdbJemedTwoNoaccept;
    private TextView mTvJamedTwoReason;
    private ContentEditText mEtJamedTwoOtherexplain;
    private RadioButton mRdbJamedTwoYes;
    private RadioButton mRdbJamedTwoNo;
    private LinearLayout mLinearMediaTwo;
    private TextView mTvJamedTwoResult;
    private TextView mTvJamedtwoResultreason;
    private TextView mTvJamedtwoResultexplain;
    private TextView mTvJamedtwoResultjion;
    private LinearLayout mLinearJamedtwoResultMedia;

    private Button mBtnJamedTwoSumbit;
    /**
     * 审核界面
     */
    private LinearLayout mLinearJamedTwoApply;
    /***
     * 显示审核结果界面
     */
    private LinearLayout mLinearJamedTwoResult;
    private String mMediaStatus;
    private JamedApplyDetailVO mJamedDetailVo;

    /**
     * @param param1         是否参与媒体调解
     * @param param2         调解oid
     * @param param3         调解状态
     * @param mJamedDetailVo
     * @return
     */
    public static JamedTwoFragment newInstance(boolean param1, String param2, String param3, JamedApplyDetailVO mJamedDetailVo) {
        JamedTwoFragment fragment = new JamedTwoFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putSerializable(ARG_PARAM4, mJamedDetailVo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMediaFlag = getArguments().getBoolean(ARG_PARAM1);
            mOrgId = getArguments().getString(ARG_PARAM2);
            mMediaStatus = getArguments().getString(ARG_PARAM3);
            mJamedDetailVo = (JamedApplyDetailVO) getArguments().getSerializable(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jamed_two, container, false);
        initAcceptSeasons();
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        handlerData();
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

    private void initView(View view) {
        mContext = getContext();
        mRdbJamedTwoAccept = (RadioButton) view.findViewById(R.id.rdb_jamedTwo_accept);
        mRdbJamedTwoAccept.setOnClickListener(this);
        mRdbJemedTwoNoaccept = (RadioButton) view.findViewById(R.id.rdb_jemedTwo_Noaccept);
        mRdbJemedTwoNoaccept.setOnClickListener(this);
        mTvJamedTwoReason = (TextView) view.findViewById(R.id.tv_jamedTwo_reason);
        mTvJamedTwoReason.setOnClickListener(this);
        mEtJamedTwoOtherexplain = (ContentEditText) view.findViewById(R.id.et_jamedTwo_otherexplain);
        mEtJamedTwoOtherexplain.setOnClickListener(this);
        mRdbJamedTwoYes = (RadioButton) view.findViewById(R.id.rdb_jamedTwo_Yes);
        mRdbJamedTwoYes.setOnClickListener(this);
        mRdbJamedTwoNo = (RadioButton) view.findViewById(R.id.rdb_jamedTwo_No);
        mRdbJamedTwoNo.setOnClickListener(this);
        mLinearMediaTwo = (LinearLayout) view.findViewById(R.id.linearMediaTwo);
        mLinearMediaTwo.setOnClickListener(this);
        mTvJamedTwoResult = (TextView) view.findViewById(R.id.tv_jamedTwo_result);
        mTvJamedTwoResult.setOnClickListener(this);
        mTvJamedtwoResultreason = (TextView) view.findViewById(R.id.tv_jamedtwo_resultreason);
        mTvJamedtwoResultreason.setOnClickListener(this);
        mTvJamedtwoResultexplain = (TextView) view.findViewById(R.id.tv_jamedtwo_resultexplain);
        mTvJamedtwoResultexplain.setOnClickListener(this);
        mTvJamedtwoResultjion = (TextView) view.findViewById(R.id.tv_jamedtwo_resultjion);
        mTvJamedtwoResultjion.setOnClickListener(this);
        mLinearJamedtwoResultMedia = (LinearLayout) view.findViewById(R.id.linear_jamedtwo_result_media);
        mLinearJamedtwoResultMedia.setOnClickListener(this);
        mLinearJamedTwoResult = (LinearLayout) view.findViewById(R.id.linear_JamedTwo_result);
        mLinearJamedTwoApply = (LinearLayout) view.findViewById(R.id.linear_jamedtwo_apply);
        mLinearJamedTwoApply.setOnClickListener(this);
        mBtnJamedTwoSumbit = (Button) view.findViewById(R.id.btn_jamedTwo_sumbit);
        mBtnJamedTwoSumbit.setOnClickListener(this);
    }

    /**
     * 处理不同情况
     */
    private void handlerData() {
        if (mMediaFlag) {//当事人是否勾选媒体调解
            mLinearMediaTwo.setVisibility(View.GONE);
        } else {
            mLinearMediaTwo.setVisibility(View.VISIBLE);
        }
        if (mMediaStatus.equals(ShowInfomActivity.statusOneBegin)) {//未审核过
            isShowResult(false, null);
        } else {
            if (mJamedDetailVo != null) {
                isShowResult(true, mJamedDetailVo);
            } else {
                requestSericeData();
            }
        }
    }

    /**
     * 是否显示结果
     *
     * @param mIsShowResult true 显示审核结果 false 未审核，
     * @param vo            结果vo
     */
    private void isShowResult(boolean mIsShowResult, JamedApplyDetailVO vo) {
        if (mIsShowResult) {
            if (vo == null)
                return;
            mLinearJamedTwoApply.setVisibility(View.GONE);
            mLinearJamedTwoResult.setVisibility(View.VISIBLE);
            if (!mMediaFlag) {
                mLinearJamedtwoResultMedia.setVisibility(View.VISIBLE);
                if (vo.getMediaApplyType() == null || !vo.getMediaApplyType().equals("2")) {
                    mTvJamedtwoResultjion.setText(getString(R.string.no));
                } else if (vo.getMediaApplyType().equals("2")) {
                    mTvJamedtwoResultjion.setText(getString(R.string.yes));
                }
            } else {
                mLinearJamedtwoResultMedia.setVisibility(View.GONE);
            }

//         ：未受理 1：受理 -1：不受理
            String orgAcceptFlag = "";
            switch (vo.getOrgAcceptFlag()) {
                case 0:
                    orgAcceptFlag = "未受理";
                    break;
                case 1:
                    orgAcceptFlag = "受理";
                    break;
                case -1:
                    orgAcceptFlag = "不予受理";
                    mTvJamedtwoResultreason.setText(DataManage.getInstance().getNameWithOid(vo.getNoAccpectReason()));
                    break;
                default:
                    break;
            }
            mTvJamedTwoResult.setText(orgAcceptFlag);
            mTvJamedtwoResultexplain.setText(vo.getOrgAcceptOpinion());


        } else {
            mLinearJamedTwoApply.setVisibility(View.VISIBLE);
            mLinearJamedTwoResult.setVisibility(View.GONE);
        }
    }

    private void initData() {
        mRdbJamedTwoAccept.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mTvJamedTwoReason.setClickable(false);
                    mTvJamedTwoReason.setFocusable(false);
                    if (mMediaFlag){
                        mLinearMediaTwo.setVisibility(View.GONE);
                    }else {
                        mLinearMediaTwo.setVisibility(View.VISIBLE);
                    }
                    mIsAccept = mAccept;
                    if (mSelectNoHandlerItem != null) {
                        mSelectNoHandlerItem.setItemVo(null);
                        mSelectNoHandlerItem.setSelectPosition(-1);
                        mTvJamedTwoReason.setText(getResources().getString(R.string.jamed_nohandleseason));

                    }
                }
            }
        });
        mRdbJemedTwoNoaccept.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mIsAccept = mNoAccept;
                    mTvJamedTwoReason.setClickable(true);
                    mTvJamedTwoReason.setFocusable(true);
                    if (mMediaFlag){
                        mLinearMediaTwo.setVisibility(View.GONE);
                    }else {
                        mLinearMediaTwo.setVisibility(View.GONE);
                    }
                }
            }
        });
        mRdbJamedTwoYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mIsJoinMedia = mJoinMedia;
                }
            }
        });
        mRdbJamedTwoNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mIsJoinMedia = mNoJoinMedia;
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jamedTwo_sumbit:
                submit();
                break;
            case R.id.tv_jamedTwo_reason:
                String str = getTextStr(mTvJamedTwoReason);
                handlerPopWindos(mTvJamedTwoReason, mNoHandlerLists, str, null);
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
        if (mSelectNoHandlerItem == null) {
            mSelectNoHandlerItem = new SelectItemVo();
        }
        int selectPosition = mSelectNoHandlerItem.getSelectPosition();
        if (selectPosition != -1) {
            applyPopAdapter.setSeletsPosition(selectPosition);
            MoveToPostion(manager, mPopWinowsShow.getRecyclerView(), selectPosition);
        }

        applyPopAdapter.setOnRecyclerItemClickListener(new ApplySeasonListPopAdapter.OnRecyclerItemClickListener() {
            @Override
            public void OnItemClickListener(View view, BaseCommonDataVO itemVo, int position) {
                mSelectNoHandlerItem.setItemVo(itemVo);
                mSelectNoHandlerItem.setSelectPosition(position);
                tv.setText(itemVo.getName());
                mPopWinowsShow.dismiss();
            }
        });

    }

    private void submit() {
        final JamedApplyVo vo = new JamedApplyVo();

        if (TextUtils.isEmpty(mIsAccept)) {
            T.showShort(getContext(), getString(R.string.tos_pleaseAuditing));
            return;
        }
        vo.setAccept(mIsAccept);
        if (mIsAccept.equals(mNoAccept)) {
            String season = getTextStr(mTvJamedTwoReason);
            if (TextUtils.isEmpty(season) || season.equals(getString(R.string.jamed_nohandleseason))) {
                T.showShort(getContext(), getString(R.string.jamed_nohandleseason));
                return;
            } else {
                //填写不通过原因
                vo.setNoSearson(mSelectNoHandlerItem.getItemVo().getOid());
            }
        }

        String otherInfom = getTextStr(mEtJamedTwoOtherexplain);
        if (!TextUtils.isEmpty(otherInfom)) {
            vo.setOthearExplain(otherInfom);
        }

        if (!TextUtils.isEmpty(mIsJoinMedia)) {
            if (mIsJoinMedia.equals(mJoinMedia)) {
                vo.setEdiaApplyType(true);
            } else if (mIsJoinMedia.equals(mNoJoinMedia)) {
                vo.setEdiaApplyType(false);
            }
        } else {
            vo.setEdiaApplyType(false);
        }

        final MaterialDialog.Builder builder = getShowDialog();
        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                submitService(vo);
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

    private void submitService(JamedApplyVo vo) {
        ShowInfomActivity activity = (ShowInfomActivity) getActivity();
        activity.selectBtnSet(true, false, false);
        JamedUserService jamedUserService = new JamedUserService(mContext);
        jamedUserService.setShowProgress(true);
        jamedUserService.setProgressShowContent(getString(R.string.submit_loading));
        jamedUserService.postAccept(mOrgId, vo.getEdiaApplyType(), vo.getAccept(), vo.getOthearExplain(), vo.getNoSearson(), new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                if (values == null || values.isEmpty() || !(values.get(0) instanceof JamedApplyDetailVO)) {
                    T.showShort(getActivity(), content);
                    return;
                }
                T.showShort(mContext, getString(R.string.submit_success));
                JamedApplyDetailVO vo = (JamedApplyDetailVO) values.get(0);
                if (vo != null) {
                    int orgAcceptFlag = vo.getOrgAcceptFlag();
                    if (orgAcceptFlag == 1) {
                        ShowInfomActivity showInfomActivity = (ShowInfomActivity) getActivity();
                        showInfomActivity.selectBtnSet(true, false, true);
                        isShowResult(true, vo);
                    } else {
                        ShowInfomActivity showInfomActivity = (ShowInfomActivity) getActivity();
                        showInfomActivity.selectBtnSet(true, false, false);
                        isShowResult(true, vo);
                    }
                }

            }

            @Override
            public void onError(String msg, String content) {
                Log.e("===", "onError: " + msg + content);
            }
        });
    }

    public void requestSericeData() {
        JamedUserService jamedUserService = new JamedUserService(mContext);
        jamedUserService.setProgressShowContent(getString(R.string.get_ing));
        jamedUserService.setShowProgress(true);
        jamedUserService.getApplyDetail(mOrgId, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                if (values == null || values.isEmpty() || !(values.get(0) instanceof JamedApplyDetailVO)) {
                    T.showShort(mContext, content);
                    return;
                }
                JamedApplyDetailVO vo = (JamedApplyDetailVO) values.get(0);
                if (vo != null)
                    isShowResult(true, vo);
            }

            @Override
            public void onError(String msg, String content) {
                T.showShort(mContext, msg);
            }
        });
    }

    private static final class JamedApplyVo {
        String Accept;
        String NoSearson;
        String othearExplain;
        Boolean ediaApplyType;

        public String getAccept() {
            return Accept;
        }

        public void setAccept(String accept) {
            Accept = accept;
        }

        public String getNoSearson() {
            return NoSearson;
        }

        public void setNoSearson(String noSearson) {
            NoSearson = noSearson;
        }

        public String getOthearExplain() {
            return othearExplain;
        }

        public void setOthearExplain(String othearExplain) {
            this.othearExplain = othearExplain;
        }

        public Boolean getEdiaApplyType() {
            return ediaApplyType;
        }

        public void setEdiaApplyType(Boolean ediaApplyType) {
            this.ediaApplyType = ediaApplyType;
        }
    }
}
