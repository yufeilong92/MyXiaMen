package com.lawyee.apppublic.ui.frag.fragService.jamed;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.DataManage;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JamedUserService;
import com.lawyee.apppublic.ui.frag.fragService.BaseFragment;
import com.lawyee.apppublic.vo.JamedApplyDetailVO;

import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;

public class JamedOneFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mOid;
    private JamedApplyDetailVO mJamedDetialVo;
    private TextView mTvJamedOneApplytype;
    private TextView mTvJamedOneLookOrg;
    private TextView mTvJamedOneLookMedia;
    private TextView mTvJamedOneLookName;
    private TextView mTvJamedOneLookSex;
    private TextView mTvJamedOneLookCardType;
    private TextView mTvJamedOneLookBrithday;
    private TextView mTvJamedOneLookNation;
    private TextView mTvJamedOneLookTelephone;
    private TextView mTvJamedOneLookWork;
    private TextView mTvJamedOneLookRelation;
    private TextView mTvJamedOneLookBeiName;
    private TextView mTvJamedOneLookBeiSex;
    private TextView mTvJamedOneLookBeiBrithday;
    private TextView mTvJamedOneLookBeiNation;
    private TextView mTvJamedOneLookBeiTelephone;
    private TextView mTvJamedOneLookBeiWork;
    private TextView mTvJamedOneLookCase;
    private TextView mTvJamedOneLookItem;
    private Context mContext;

    public static JamedOneFragment newInstance(String param1, JamedApplyDetailVO param2) {
        JamedOneFragment fragment = new JamedOneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putSerializable(ARG_PARAM2,  param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOid = getArguments().getString(ARG_PARAM1);
            mJamedDetialVo = (JamedApplyDetailVO) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jamed_one, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mJamedDetialVo!=null){
            showData(mJamedDetialVo);
        }else {
            requestServiceData();
        }
    }

    private void requestServiceData() {
        JamedUserService jamedUserService = new JamedUserService(mContext);
        jamedUserService.setShowProgress(true);
        jamedUserService.setProgressShowContent(getString(R.string.get_ing));
        jamedUserService.getApplyDetail(mOid, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                if (values == null || values.isEmpty()) {
                    T.showShort(mContext, content);
                    return;
                }
                JamedApplyDetailVO vo = (JamedApplyDetailVO) values.get(0);
                if (vo != null)
                    showData(vo);
            }

            @Override
            public void onError(String msg, String content) {
                T.showShort(mContext, msg);
            }
        });


    }

    private void showData(JamedApplyDetailVO vo) {
        mTvJamedOneApplytype.setText(getStrWithBoolean(vo.isTjType()));
        mTvJamedOneLookOrg.setText(vo.getTjOrgName());
        mTvJamedOneLookMedia.setText(getStringWithBoolean(vo.isMediaFlag()));
        mTvJamedOneLookName.setText(vo.getApplyName());
        mTvJamedOneLookSex.setText(DataManage.getInstance().getNameWithOid(vo.getApplyGender()));
        mTvJamedOneLookCardType.setText(vo.getApplyIdCard());
        mTvJamedOneLookBrithday.setText(vo.getApplyAge());
        mTvJamedOneLookNation.setText(DataManage.getInstance().getNameWithOid(vo.getApplyNation()));
        mTvJamedOneLookTelephone.setText(vo.getApplyTelephone());
        mTvJamedOneLookWork.setText(vo.getApplyAddress());
        mTvJamedOneLookRelation.setText(vo.getRelation());
        mTvJamedOneLookBeiName.setText(vo.getBeApplyName());
        mTvJamedOneLookBeiSex.setText(DataManage.getInstance().getNameWithOid(vo.getBeApplyGender()));
        mTvJamedOneLookBeiBrithday.setText(vo.getBeApplyAge());
        mTvJamedOneLookBeiNation.setText(DataManage.getInstance().getNameWithOid(vo.getBeApplyNation()));
        mTvJamedOneLookBeiTelephone.setText(vo.getBeApplyTelephone());
        mTvJamedOneLookBeiWork.setText(vo.getBeApplyAddress());
        mTvJamedOneLookCase.setText(vo.getIntroduction());
        mTvJamedOneLookItem.setText(vo.getMatter());
    }

    private void initView(View view) {
        mContext = getContext();
        mTvJamedOneApplytype = (TextView) view.findViewById(R.id.tv_JamedOne_applytype);
        mTvJamedOneLookOrg = (TextView) view.findViewById(R.id.tv_jamedOne_LookOrg);
        mTvJamedOneLookMedia = (TextView) view.findViewById(R.id.tv_jamedOne_LookMedia);
        mTvJamedOneLookName = (TextView) view.findViewById(R.id.tv_jamedOne_LookName);
        mTvJamedOneLookSex = (TextView) view.findViewById(R.id.tv_jamedOne_LookSex);
        mTvJamedOneLookCardType = (TextView) view.findViewById(R.id.tv_jamedOne_LookCardType);
        mTvJamedOneLookBrithday = (TextView) view.findViewById(R.id.tv_jamedOne_LookBrithday);
        mTvJamedOneLookNation = (TextView) view.findViewById(R.id.tv_jamedOne_LookNation);
        mTvJamedOneLookTelephone = (TextView) view.findViewById(R.id.tv_jamedOne_LookTelephone);
        mTvJamedOneLookWork = (TextView) view.findViewById(R.id.tv_jamedOne_LookWork);
        mTvJamedOneLookRelation = (TextView) view.findViewById(R.id.tv_jamedOne_LookRelation);
        mTvJamedOneLookBeiName = (TextView) view.findViewById(R.id.tv_jamedOne_LookBeiName);
        mTvJamedOneLookBeiSex = (TextView) view.findViewById(R.id.tv_jamedOne_LookBeiSex);
        mTvJamedOneLookBeiBrithday = (TextView) view.findViewById(R.id.tv_jamedOne_LookBeiBrithday);
        mTvJamedOneLookBeiNation = (TextView) view.findViewById(R.id.tv_jamedOne_LookBeiNation);
        mTvJamedOneLookBeiTelephone = (TextView) view.findViewById(R.id.tv_jamedOne_LookBeiTelephone);
        mTvJamedOneLookBeiWork = (TextView) view.findViewById(R.id.tv_jamedOne_LookBeiWork);
        mTvJamedOneLookCase = (TextView) view.findViewById(R.id.tv_jamedOne_LookCase);
        mTvJamedOneLookItem = (TextView) view.findViewById(R.id.tv_jamedOne_LookItem);
    }

    private String getStringWithBoolean(boolean str) {
        String type = "";
        if (str) {
            type = "同意";
        } else {
            type = "不同意";
        }
        return type;
    }

    private String getStrWithBoolean(boolean str) {
        String type = "";
        if (str) {
            type = "专业调解";
        } else {
            type = "一般调解";
        }
        return type;
    }


}
