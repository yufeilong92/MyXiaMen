package com.lawyee.apppublic.ui.frag.fragService.jamed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JamedUserService;
import com.lawyee.apppublic.ui.frag.fragService.BaseFragment;
import com.lawyee.apppublic.vo.AttachmentVO;
import com.lawyee.apppublic.vo.JamedApplyDetailVO;

import net.lawyee.mobilelib.utils.FileUtil;
import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.T;
import net.lawyee.mobilelib.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static android.app.Activity.RESULT_OK;
import static com.lawyee.apppublic.util.ShowOrHide.showDataDialog;

public class JamedFourFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private String mOrgId;
    private String mMediaStatus;
    private RadioButton mRdbJamedFbsuccess;
    private RadioButton mRdbJamedFbfail;
    private TextView mTvJaemdFinishData;
    private RadioButton mRdbJamedFbyes;
    private RadioButton mRdbJamedFbno;
    private ArrayList<String> mSelectPathXieYi = new ArrayList<>();//身份证图片地址集合
    private static final int REQUEST_IMAGEAGREEMENT = 1;//上传协议

    private String mIsSuccess = "";
    private String mSuccess = "1";
    private String mFail = "-1";
    private String mIsSure = "";
    private String mSure = "true";
    private String mNo = "false";
    private Context mContext;
    private TextView mTvJamedfourUpload;
    private ImageView mIvJamedFourUploadDelete;
    private Button mBtnJamedfourSumbit;
    private LinearLayout mLinearJamedFourApply;
    private TextView mTvJamdefourResultIsSuccess;
    private TextView mTvJamdefourResultFinsihTime;
    private TextView mTvJamdefourResultIsSure;
    private TextView mTvJamdefourResultXieYi;
    private LinearLayout mLinearJamedFourResult;
    private JamedApplyDetailVO mJamedDetailVo;

    public static JamedFourFragment newInstance(String param1, String param2, JamedApplyDetailVO mJamedDetailVo) {
        JamedFourFragment fragment = new JamedFourFragment();
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
            mOrgId = getArguments().getString(ARG_PARAM1);
            mMediaStatus = getArguments().getString(ARG_PARAM2);
            mJamedDetailVo = (JamedApplyDetailVO) getArguments().getSerializable(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jamed_four, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        handlerData();
    }


    private void initView(View view) {
        mContext = getContext();
        mRdbJamedFbsuccess = (RadioButton) view.findViewById(R.id.rdb_jamedFour_success);
        mRdbJamedFbfail = (RadioButton) view.findViewById(R.id.rdb_jamedFour_fail);
        mTvJaemdFinishData = (TextView) view.findViewById(R.id.tv_jaemdFour_finishData);
        mRdbJamedFbyes = (RadioButton) view.findViewById(R.id.rdb_jamedfour_yes);
        mRdbJamedFbno = (RadioButton) view.findViewById(R.id.rdb_jamedfour_no);
        mTvJaemdFinishData.setOnClickListener(this);
        mTvJamedfourUpload = (TextView) view.findViewById(R.id.tv_jamedfour_upload);
        mTvJamedfourUpload.setOnClickListener(this);
        mIvJamedFourUploadDelete = (ImageView) view.findViewById(R.id.iv_JamedFour_upload_delete);
        mIvJamedFourUploadDelete.setOnClickListener(this);
        mBtnJamedfourSumbit = (Button) view.findViewById(R.id.btn_jamedfour_Sumbit);
        mBtnJamedfourSumbit.setOnClickListener(this);
        mLinearJamedFourApply = (LinearLayout) view.findViewById(R.id.linear_jamedFour_apply);
        mLinearJamedFourApply.setOnClickListener(this);
        mTvJamdefourResultIsSuccess = (TextView) view.findViewById(R.id.tv_jamdefour_resultIsSuccess);
        mTvJamdefourResultIsSuccess.setOnClickListener(this);
        mTvJamdefourResultFinsihTime = (TextView) view.findViewById(R.id.tv_jamdefour_resultFinsihTime);
        mTvJamdefourResultFinsihTime.setOnClickListener(this);
        mTvJamdefourResultIsSure = (TextView) view.findViewById(R.id.tv_jamdefour_resultIsSure);
        mTvJamdefourResultIsSure.setOnClickListener(this);
        mTvJamdefourResultXieYi = (TextView) view.findViewById(R.id.tv_jamdefour_resultXieYi);
        mTvJamdefourResultXieYi.setOnClickListener(this);
        mLinearJamedFourResult = (LinearLayout) view.findViewById(R.id.linear_jamedFour_result);
        mLinearJamedFourResult.setOnClickListener(this);
    }

    private void handlerData() {
        if (mJamedDetailVo == null) {
            requestServiceData();
        } else {
            if (mMediaStatus.equals("3")) {
                    isShowResult(true, mJamedDetailVo);
            } else if (mJamedDetailVo.getSuccessFlag() == 1) {
                isShowResult(true, mJamedDetailVo);
            } else if (mJamedDetailVo.getSuccessFlag() == -1) {
                isShowResult(true, mJamedDetailVo);
            } else {
                isShowResult(false, null);
            }
        }

    }

    private void requestServiceData() {
        JamedUserService jamedUserService = new JamedUserService(mContext);
        jamedUserService.setShowProgress(true);
        jamedUserService.setProgressShowContent(getString(R.string.get_ing));
        jamedUserService.getApplyDetail(mOrgId, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                if (values == null || values.isEmpty()) {
                    T.showShort(mContext, content);
                    return;
                }
                JamedApplyDetailVO vo = (JamedApplyDetailVO) values.get(0);
                if (vo != null)
                    if (mMediaStatus.equals("3")) {
                        isShowResult(true, mJamedDetailVo);
                    } else if (mJamedDetailVo.getSuccessFlag() == 1) {
                        isShowResult(true, mJamedDetailVo);
                    } else if (mJamedDetailVo.getSuccessFlag() == -1) {
                        isShowResult(true, mJamedDetailVo);
                    } else {
                        isShowResult(false, null);
                    }
            }

            @Override
            public void onError(String msg, String content) {
                T.showShort(mContext, msg);
            }
        });

    }


    private void initData() {
        mRdbJamedFbsuccess.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mIsSuccess = mSuccess;
                }
            }
        });
        mRdbJamedFbfail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mIsSuccess = mFail;
                }
            }
        });
        mRdbJamedFbyes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mIsSure = mSure;
                }
            }
        });
        mRdbJamedFbno.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mIsSure = mNo;
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jamedfour_Sumbit:
                submit();
                break;
            case R.id.tv_jaemdFour_finishData:
                showDataDialog(getActivity(), mTvJaemdFinishData);
                break;
            case R.id.tv_jamedfour_upload:
                Intent intent = new Intent(getActivity(), MultiImageSelectorActivity.class);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
                // 默认选择
                if (mSelectPathXieYi != null && mSelectPathXieYi.size() > 0) {
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPathXieYi);
                }
                startActivityForResult(intent, REQUEST_IMAGEAGREEMENT);
                break;
            case R.id.iv_JamedFour_upload_delete:
                if (mSelectPathXieYi != null) {
                    mSelectPathXieYi.clear();
                }
                mTvJamedfourUpload.setText("");
                mTvJamedfourUpload.setBackgroundResource(R.drawable.bg_input_box);
                mIvJamedFourUploadDelete.setVisibility(View.GONE);
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGEAGREEMENT) {//身份证
            if (resultCode == RESULT_OK) {
                mSelectPathXieYi = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                String name = null;
                for (String str : mSelectPathXieYi
                        ) {
                    name = FileUtil.getFileName(str);
                }
                mTvJamedfourUpload.setText(name);
                mTvJamedfourUpload.setBackgroundResource(R.drawable.bg_input_box);
                mIvJamedFourUploadDelete.setVisibility(View.VISIBLE);
            }
        }
    }

    private void submit() {
        final ApplyDetailFourVo vo = new ApplyDetailFourVo();
        if (TextUtils.isEmpty(mIsSuccess)) {
            T.showShort(mContext, getString(R.string.pleasesuccessFlag));
            return;
        }
        vo.setIsSureccess(mIsSuccess);

        String finishData = getTextStr(mTvJaemdFinishData);
        if (TextUtils.isEmpty(finishData)) {
            T.showShort(mContext, getString(R.string.pleaseendTime));
            return;
        }
        vo.setFinishDate(finishData);

        if (TextUtils.isEmpty(mIsSure)) {
            T.showShort(mContext, getString(R.string.pleasejudconfirmFlag));
            return;
        }

        vo.setIsSure(mIsSure);
        if (mIsSure.equals(mSure)) {
            if (mSelectPathXieYi == null || mSelectPathXieYi.isEmpty()) {
                T.showShort(mContext, getString(R.string.please_up_agreement));
                return;
            }
        }

        List<AttachmentVO> vos = new ArrayList<>();
        if (mSelectPathXieYi != null && !mSelectPathXieYi.isEmpty())
            for (String s : mSelectPathXieYi) {
                AttachmentVO attachmentVO = new AttachmentVO();
                attachmentVO.setDescription_(getString(R.string.conditioning_agreement));
                attachmentVO.setLocfilepath(s);
                attachmentVO.setSub(AttachmentVO.CSTR_UPLOADSUB_JAMEDSERVICE_TJXY);
                vos.add(attachmentVO);
            }
        vo.setLists(vos);
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

    private void submitService(ApplyDetailFourVo vo) {
        JamedUserService jamedUserService = new JamedUserService(mContext);
        jamedUserService.setProgressShowContent(getString(R.string.submit_ing));
        jamedUserService.setShowProgress(true);
        jamedUserService.postEndInfo(mOrgId, vo.getIsSureccess(), vo.getFinishDate(), vo.getIsSure(), new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
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
     * 是否展示显示结果
     *
     * @param isShow 是否展示
     * @param vo     数据
     */
    private void isShowResult(boolean isShow, JamedApplyDetailVO vo) {
        if (isShow) {
            if (vo != null) {
                mLinearJamedFourApply.setVisibility(View.GONE);
                mLinearJamedFourResult.setVisibility(View.VISIBLE);
                String ymdt = TimeUtil.getYMDT(vo.getEndTime());
                mTvJamdefourResultFinsihTime.setText(ymdt);
                mTvJamdefourResultIsSuccess.setText(getStringWithInt(vo.getSuccessFlag()));
                mTvJamdefourResultIsSure.setText(getStringWithString(vo.getJudconfirmFlag()));
            }
        } else {
            mLinearJamedFourApply.setVisibility(View.VISIBLE);
            mLinearJamedFourResult.setVisibility(View.GONE);
        }

    }

    private String getStringWithInt(int id) {
        String success = "";
        switch (id) {
            case 0:
                success = getString(R.string.Acting_mediation);
                break;
            case 1:
                success = getString(R.string.success);
                break;
            case -1:
                success = getString(R.string.fail);
                break;
            default:
                break;
        }
        return success;
    }

    private String getStringWithString(String id) {
        String flag = "";
        if (StringUtil.isEmpty(id)) {
            return flag;
        }
        if (id.equals("true")) {
            flag = getString(R.string.yes);
        } else if (id.equals("false")) {
            flag = getString(R.string.no);
        } else {
            flag = getString(R.string.daiSure);
        }
        return flag;
    }

    private static final class ApplyDetailFourVo {
        private String IsSureccess;
        private String finishDate;
        private String isSure;

        public String getIsSureccess() {
            return IsSureccess;
        }

        public void setIsSureccess(String isSureccess) {
            IsSureccess = isSureccess;
        }

        public String getFinishDate() {
            return finishDate;
        }

        public void setFinishDate(String finishDate) {
            this.finishDate = finishDate;
        }

        public String getIsSure() {
            return isSure;
        }

        public void setIsSure(String isSure) {
            this.isSure = isSure;
        }

        public List<AttachmentVO> getLists() {
            return lists;
        }

        public void setLists(List<AttachmentVO> lists) {
            this.lists = lists;
        }

        private List<AttachmentVO> lists;
    }
}
