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
import net.lawyee.mobilelib.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import static com.lawyee.apppublic.util.RecyclerSelectItem.MoveToPostion;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: JamedThreeFragment.java
 * @Package com.lawyee.apppublic.ui.frag.fragService
 * @Description: 媒体调解参与信息
 * @author: YFL
 * @date: 2017/8/2 15:55
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/8/2 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JamedThreeFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private TextView mTvJamedThreeJoinMedia;
    private TextView mTvJamedThreeMediaispass;
    private TextView mTvJamedThreeReason;
    private TextView mTvJamedThreeExplain;
    private RadioButton mRdbJamedThreeMediaJoin;
    private RadioButton mRdbJamedThreeMediaNoJoin;
    private TextView mTvJamedThreeMediaNoJoin;
    private ContentEditText mEtJamedThreeOtherexplain;
    private Button mBtnJamedthreeSubmit;
    /**
     * 不同意数据集合
     */
    private List<BaseCommonDataVO> mNoHandlerLists;
    private MaterialDialog mPopWinowsShow;
    private SelectItemVo mSelectNoHandlerItem;
    private LinearLayout mLinearJamedThreeApply;
    private TextView mTvJamedthreeResultjion;
    private TextView mTvJamedthreeResultreason;
    private TextView mTvJamedthreeReasulexplain;
    private LinearLayout mLinearJamedThreeResult;
    private Context mContext;
    /**
     * 是否同意
     */
    private String mApplyMediaConfim;
    private String mApplyMediaConfimPass = "1";
    private String mApplyMediaConfimnoPass = "-1";
    private JamedApplyDetailVO detailVO;
    /**
     * 当事人是否勾选媒体调解
     */
    private boolean mMediaFlag;
    /**
     * 机构id
     */
    private String mOrgId;
    /**
     * 发起人
     */
    private String mediaApplyType;
    /**
     * 审核状态
     */
    private String mMediaStatus;
    private TextView mTvJamedthreeRrecordTime;
    private TextView mTvJamedthreeRrecordAddress;
    private LinearLayout mLinearJamedThreeRecordinfom;
    private TextView mTvJamedthreeRplayChannel;
    private TextView mTvJamedthreeRplaytime;
    private TextView mTvJamedthreeRplayprogramTitle;
    private TextView mTvJamedthreeRplaymediaPlayUrlR;
    private TextView mTvJamedthreeRplaynetFlag;
    private LinearLayout mLinearJamedThreePlayinfom;
    private JamedApplyDetailVO mJamedDetailVo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOrgId = getArguments().getString(ARG_PARAM1);
            mediaApplyType = getArguments().getString(ARG_PARAM2);
            mMediaStatus = getArguments().getString(ARG_PARAM3);
            mMediaFlag = getArguments().getBoolean(ARG_PARAM4);
            mJamedDetailVo = (JamedApplyDetailVO) getArguments().getSerializable(ARG_PARAM5);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jamed_three, container, false);
        initView(view);
        initAcceptSeasons();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        BindViewData();
    }

    public static JamedThreeFragment newInstance(String param1, String param2, String param3, boolean param4, JamedApplyDetailVO mJamedDetailVo) {
        JamedThreeFragment fragment = new JamedThreeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putBoolean(ARG_PARAM4, param4);
        args.putSerializable(ARG_PARAM5, mJamedDetailVo);
        fragment.setArguments(args);
        return fragment;
    }

    private void initData() {
        mRdbJamedThreeMediaJoin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mTvJamedThreeMediaNoJoin.setClickable(false);
                    mTvJamedThreeMediaNoJoin.setFocusable(false);
                    mApplyMediaConfim = mApplyMediaConfimPass;
                    if (mSelectNoHandlerItem != null) {
                        mSelectNoHandlerItem.setItemVo(null);
                        mSelectNoHandlerItem.setSelectPosition(-1);
                        mTvJamedThreeMediaNoJoin.setText(getString(R.string.selectNoJoinMedia));
                    }
                }
            }
        });
        mRdbJamedThreeMediaNoJoin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mApplyMediaConfim = mApplyMediaConfimnoPass;
                    mTvJamedThreeMediaNoJoin.setClickable(true);
                    mTvJamedThreeMediaNoJoin.setFocusable(true);
                }
            }
        });
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

    private void BindViewData() {
        if (!mMediaFlag) {
            if (mediaApplyType == null || TextUtils.isEmpty(mediaApplyType)) {//媒体参与调解
                mTvJamedThreeJoinMedia.setVisibility(View.GONE);
            } else if (mediaApplyType.equals("3")) {
                mTvJamedThreeJoinMedia.setVisibility(View.VISIBLE);
                isShowResult(false, null);
            }
        }
        if (mJamedDetailVo != null) {
            setfilterData(mJamedDetailVo);
        } else {
            requestServiceData();
        }

    }

    /**
     * 显示媒体筛选信息
     *
     * @param detailVO
     */
    private void setfilterData(JamedApplyDetailVO detailVO) {
        mTvJamedThreeMediaispass.setText(getMediaConfirmWithString(detailVO.getMediaConfirm()));
        mTvJamedThreeReason.setText(DataManage.getInstance().getNameWithOid(detailVO.getMediaNoAcceptReason()));
        mTvJamedThreeExplain.setText(detailVO.getMediaOpinion());
        int mediaConfirm = detailVO.getMediaConfirm();
        if (mMediaStatus.equals(ShowInfomActivity.statusTwoOrgAgree)) {//机构已受理
            if (mediaApplyType != null && mediaApplyType.equals("3") && detailVO.getApplyMediaConfirm().equals("1")) {
                isShowResult(true, detailVO);
            } else if (mediaApplyType != null && mediaApplyType.equals("3") && detailVO.getApplyMediaConfirm().equals("-1")) {
                isShowResult(true, detailVO);
            } else {
                isShowResult(false, detailVO);
            }
        } else if (mMediaStatus.equals(ShowInfomActivity.statusFourMediaAgree)) {//媒体同意
            if (mediaConfirm == 1) {
                if (detailVO.getApplyMediaConfirm().equals("1")) {
                    isShowResult(true, detailVO);
                } else if (detailVO.getApplyMediaConfirm().equals("-1")) {
                    isShowResult(true, detailVO);
                } else {
                    isShowResult(false, detailVO);
                }
            } else if (mediaConfirm == -1) {
                isShowResult(true, detailVO);
            }

        } else if (mMediaStatus.equals(ShowInfomActivity.statusFiveMeidaNoAgree)) {//媒体不同
            isShowResult(true, detailVO);
        } else if (mMediaStatus.equals(ShowInfomActivity.statusSixFinish)) {//结束
            if (mediaApplyType != null && mediaApplyType.equals("3") && mediaConfirm == 0) {
                isShowResult(false, detailVO);
            } else if (mediaApplyType != null && mediaApplyType.equals("2") && mediaConfirm == 1 && detailVO.getApplyMediaConfirm().equals("0")) {
                isShowResult(false, detailVO);
            } else if (mediaApplyType != null && mediaApplyType.equals("2") && mediaConfirm == -1 && detailVO.getApplyMediaConfirm().equals("0")) {
                isShowResult(true, detailVO);
            } else if (mediaApplyType != null && mediaApplyType.equals("1") && mediaConfirm == -1 && detailVO.getApplyMediaConfirm().equals("0")) {
                isShowResult(true, detailVO);
            } else if (mediaApplyType != null && mediaApplyType.equals("1") && mediaConfirm == 1 && detailVO.getApplyMediaConfirm().equals("0")) {
                isShowResult(false, detailVO);
            } else {
                if (mediaApplyType != null && mediaApplyType.equals("3") && mediaConfirm == 1 && detailVO.getApplyMediaConfirm().equals("0")) {
                    isShowResult(false, detailVO);
                } else if (mediaApplyType != null && mediaApplyType.equals("3") && mediaConfirm == -1 && detailVO.getApplyMediaConfirm().equals("0")) {
                    isShowResult(true, detailVO);
                } else {
                    isShowResult(true, detailVO);
                }
            }
        }
    }


    private void initView(View view) {
        mContext = getContext();
        mTvJamedThreeJoinMedia = (TextView) view.findViewById(R.id.tv_jamedThree_JoinMedia);
        mTvJamedThreeMediaispass = (TextView) view.findViewById(R.id.tv_jamedThree_Mediaispass);
        mTvJamedThreeReason = (TextView) view.findViewById(R.id.tv_jamedThree_reason);
        mTvJamedThreeExplain = (TextView) view.findViewById(R.id.tv_jamedThree_explain);
        mRdbJamedThreeMediaJoin = (RadioButton) view.findViewById(R.id.rdb_jamedThree_MediaJoin);
        mRdbJamedThreeMediaNoJoin = (RadioButton) view.findViewById(R.id.rdb_jamedThree_MediaNoJoin);
        mTvJamedThreeMediaNoJoin = (TextView) view.findViewById(R.id.tv_jamedThree_mediaNoJoin);
        mEtJamedThreeOtherexplain = (ContentEditText) view.findViewById(R.id.et_jamedThree_otherexplain);
        mBtnJamedthreeSubmit = (Button) view.findViewById(R.id.btn_jamedthree_submit);
        mTvJamedThreeMediaNoJoin.setOnClickListener(this);
        mBtnJamedthreeSubmit.setOnClickListener(this);
        mLinearJamedThreeApply = (LinearLayout) view.findViewById(R.id.linear_jamedThree_apply);
        mLinearJamedThreeApply.setOnClickListener(this);
        mTvJamedthreeResultjion = (TextView) view.findViewById(R.id.tv_jamedthree_resultjion);
        mTvJamedthreeResultjion.setOnClickListener(this);
        mTvJamedthreeResultreason = (TextView) view.findViewById(R.id.tv_jamedthree_resultreason);
        mTvJamedthreeResultreason.setOnClickListener(this);
        mTvJamedthreeReasulexplain = (TextView) view.findViewById(R.id.tv_jamedthree_reasulexplain);
        mTvJamedthreeReasulexplain.setOnClickListener(this);
        mLinearJamedThreeResult = (LinearLayout) view.findViewById(R.id.linear_jamedThree_result);
        mLinearJamedThreeResult.setOnClickListener(this);

        mTvJamedthreeRrecordTime = (TextView) view.findViewById(R.id.tv_jamedthree_Rrecord_Time);
        mTvJamedthreeRrecordTime.setOnClickListener(this);
        mTvJamedthreeRrecordAddress = (TextView) view.findViewById(R.id.tv_jamedthree_Rrecord_Address);
        mTvJamedthreeRrecordAddress.setOnClickListener(this);
        mLinearJamedThreeRecordinfom = (LinearLayout) view.findViewById(R.id.linear_jamedThree_recordinfom);
        mLinearJamedThreeRecordinfom.setOnClickListener(this);
        mTvJamedthreeRplayChannel = (TextView) view.findViewById(R.id.tv_jamedthree_RplayChannel);
        mTvJamedthreeRplayChannel.setOnClickListener(this);
        mTvJamedthreeRplaytime = (TextView) view.findViewById(R.id.tv_jamedthree_Rplaytime);
        mTvJamedthreeRplaytime.setOnClickListener(this);
        mTvJamedthreeRplayprogramTitle = (TextView) view.findViewById(R.id.tv_jamedthree_RplayprogramTitle);
        mTvJamedthreeRplayprogramTitle.setOnClickListener(this);
        mTvJamedthreeRplaymediaPlayUrlR = (TextView) view.findViewById(R.id.tv_jamedthree_RplaymediaPlayUrlR);
        mTvJamedthreeRplaymediaPlayUrlR.setOnClickListener(this);
        mTvJamedthreeRplaynetFlag = (TextView) view.findViewById(R.id.tv_jamedthree_RplaynetFlag);
        mTvJamedthreeRplaynetFlag.setOnClickListener(this);
        mLinearJamedThreePlayinfom = (LinearLayout) view.findViewById(R.id.linear_jamedThree_playinfom);
        mLinearJamedThreePlayinfom.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jamedthree_submit:
                submit();
                break;
            case R.id.tv_jamedThree_mediaNoJoin:
                String noJion = getTextStr(mTvJamedThreeMediaNoJoin);
                if (mNoHandlerLists != null && !mNoHandlerLists.isEmpty())
                    handlerPopWindos(mTvJamedThreeMediaNoJoin, mNoHandlerLists, noJion);
                break;
        }
    }

    /**
     * @param isshowResult 是否显示结果
     * @param vo
     */
    private void isShowResult(boolean isshowResult, JamedApplyDetailVO vo) {
        if (isshowResult) {
            if (vo != null) {
                mLinearJamedThreeApply.setVisibility(View.GONE);
                mLinearJamedThreeResult.setVisibility(View.VISIBLE);
                mTvJamedthreeResultjion.setText(getStrWithString(vo.getApplyMediaConfirm()));
                mTvJamedthreeResultreason.setText(DataManage.getInstance().getNameWithOid(vo.getApplynoAcceptReason()));
                mTvJamedthreeReasulexplain.setText(vo.getApplyOpinion());
                if (!TextUtils.isEmpty(vo.getRecordTime())) {//录制信息
                    mLinearJamedThreeRecordinfom.setVisibility(View.VISIBLE);
                    String ymdt = TimeUtil.getYMDT(vo.getRecordTime());
                    mTvJamedthreeRrecordTime.setText(ymdt);
                    mTvJamedthreeRrecordAddress.setText(vo.getRecordAddress());
                    if (!TextUtils.isEmpty(vo.getPlaytime())) {//播出信息
                        mLinearJamedThreePlayinfom.setVisibility(View.VISIBLE);
                        mTvJamedthreeRplayChannel.setText(vo.getPlayChannel());
                        mTvJamedthreeRplaytime.setText(TimeUtil.getYMDT(vo.getApplyTime()));
                        mTvJamedthreeRplayprogramTitle.setText(vo.getProgramTitle());
                        mTvJamedthreeRplaymediaPlayUrlR.setText(vo.getMediaPlayUrl());
                        mTvJamedthreeRplaynetFlag.setText(getStringWithStirng(vo.getNetFlag()));

                    } else {
                        mLinearJamedThreePlayinfom.setVisibility(View.GONE);
                    }
                } else {
                    mLinearJamedThreeRecordinfom.setVisibility(View.GONE);
                    mLinearJamedThreePlayinfom.setVisibility(View.GONE);
                }
            }
        } else {
            mLinearJamedThreeResult.setVisibility(View.GONE);
            mLinearJamedThreeApply.setVisibility(View.VISIBLE);
            mLinearJamedThreeRecordinfom.setVisibility(View.GONE);
            mLinearJamedThreePlayinfom.setVisibility(View.GONE);
        }
    }

    /**
     * @param tv     显示控件
     * @param mData  数据
     * @param nation 显示文字
     */
    private void handlerPopWindos(final TextView tv, final List<BaseCommonDataVO> mData, final String nation) {
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

    public void requestServiceData() {
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
                if (vo != null)
                    setfilterData(vo);
            }

            @Override
            public void onError(String msg, String content) {
                T.showShort(mContext, msg);
            }
        });
    }

    private void submit() {
        final ApplyMediaThreeVo vo = new ApplyMediaThreeVo();

        if (TextUtils.isEmpty(mApplyMediaConfim)) {
            T.showShort(mContext, getString(R.string.pleaseapplyMediaConfirm));
            return;
        }
        vo.setIsJionMedia(mApplyMediaConfim);

        if (mApplyMediaConfim.equals(mApplyMediaConfimnoPass)) {
            String reason = getTextStr(mTvJamedThreeMediaNoJoin);
            if (TextUtils.isEmpty(reason) || reason.equals(getResources().getString(R.string.selectNoJoinMedia))) {
                T.showShort(mContext, getString(R.string.selectNoJoinMedia));
                return;
            } else {
                vo.setNoPassReason(mSelectNoHandlerItem.getItemVo().getOid());
            }
        }

        String otherInfom = getTextStr(mEtJamedThreeOtherexplain);
        if (!TextUtils.isEmpty(otherInfom)) {
            vo.setExplain(otherInfom);
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

    private void submitService(ApplyMediaThreeVo vo) {
        JamedUserService jamedUserService = new JamedUserService(mContext);
        jamedUserService.setShowProgress(true);
        jamedUserService.setProgressShowContent(getString(R.string.submit_ing));
        jamedUserService.postApply(mOrgId, vo.getIsJionMedia(), vo.getExplain(), vo.getNoPassReason(), new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                Log.e("===", "onComplete: " + values + "//" + content);
                if (values == null || values.isEmpty() || !(values.get(0) instanceof JamedApplyDetailVO)) {
                    T.showShort(getActivity(), content);
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
                Log.e("===", "onError: " + msg + content);
                T.showShort(mContext, msg);
            }
        });
    }

    /**
     * 是否筛选通过
     *
     * @param isJion
     * @return
     */
    private String getStrWithString(String isJion) {
        String str = "";
        if (isJion.equals("1")) {
            str = getString(R.string.yes);
        } else if (isJion.equals("-1")) {
            str = getString(R.string.no);
        } else if (isJion.equals("0")) {
            str = "未确认";
        }
        return str;
    }

    /**
     * 媒体是否同意参加调解
     *
     * @param isJion
     * @return
     */
    private String getMediaConfirmWithString(int isJion) {
        if (mediaApplyType != null && mediaApplyType.equals("3")) {
            return getString(R.string.pass);
        }
        String str = "";
        switch (isJion) {
            case 0:
                str = "电视台未确认";
                break;
            case -1:
                str = getString(R.string.nopass);
                break;
            case 1:
                str = getString(R.string.pass);
                break;
            default:
                break;
        }
        return str;
    }

    /**
     * 否非挂外网
     *
     * @param str
     * @return
     */
    private String getStringWithStirng(String str) {
        String a = "";
        if (str.equals("0")) {
            a = getString(R.string.no);
        } else if (str.equals("1")) {
            a = getString(R.string.yes);
        }
        return a;
    }

    private static final class ApplyMediaThreeVo {
        private String isJionMedia;
        private String noPassReason;
        private String explain;

        public String getIsJionMedia() {
            return isJionMedia;
        }

        public void setIsJionMedia(String isJionMedia) {
            this.isJionMedia = isJionMedia;
        }

        public String getNoPassReason() {
            return noPassReason;
        }

        public void setNoPassReason(String noPassReason) {
            this.noPassReason = noPassReason;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }
    }
}
