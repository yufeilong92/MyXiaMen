package com.lawyee.apppublic.ui.frag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.JamedStatuTickAdapter;
import com.lawyee.apppublic.adapter.JamedStatusAdpater;
import com.lawyee.apppublic.vo.JamedApplyDetailVO;
import com.lawyee.apppublic.vo.JamedStatusDetailVO;

import net.lawyee.mobilelib.utils.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import static com.lawyee.apppublic.ui.personalcenter.MyJamedDetailActivity.JAMED;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.ui.frag
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @author: lzh
 * @date: 2017/6/2 17:53
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class JamedStatusFragment  extends Fragment{
    private RecyclerView mRvStatu;
    private ArrayList<String> mDatas = new ArrayList<>();
    private Context mContext;
    private GridLayoutManager mLayoutManager;
    private RecyclerView mRvTickling;
    private JamedStatusAdpater mStatusAdpater;
    private JamedStatuTickAdapter mJamedStatuTickAdapter;
    private JamedApplyDetailVO mJamedApplyDetailVO;
    private ArrayList<JamedStatusDetailVO> mJamedStatusDetailVOs=new ArrayList<>();
    SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        Bundle bundle = getArguments();
        mJamedApplyDetailVO = (JamedApplyDetailVO) bundle.getSerializable(JAMED);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aid_status, null);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        mRvStatu = (RecyclerView) view.findViewById(R.id.rv_statu);
        mRvTickling = (RecyclerView) view.findViewById(R.id.rv_tickling);

    }

    private void initData() {
        mDatas.add(mContext.getString(R.string.submit_jamed_internet));
        mDatas.add(mContext.getString(R.string.being_audited));
        if (mJamedApplyDetailVO.getOrgAcceptFlag() == -1) {
            mDatas.add(mContext.getString(R.string.jamed_no_accept));
            mDatas.add(mContext.getString(R.string.been_finished_2));
        } else {
            mDatas.add(mContext.getString(R.string.jamed_accept));
            mDatas.add(mContext.getString(R.string.jamed_ing));
            mDatas.add(mContext.getString(R.string.jamed_success));
        }

        mStatusAdpater = new JamedStatusAdpater(mDatas, mContext, mJamedApplyDetailVO);
        mRvStatu.setAdapter(mStatusAdpater);
        mLayoutManager = new GridLayoutManager(mContext, 1);
        mRvStatu.setLayoutManager(mLayoutManager);
        try {
            setData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mJamedStatuTickAdapter = new JamedStatuTickAdapter(mJamedStatusDetailVOs, mContext);
        mRvTickling.setAdapter(mJamedStatuTickAdapter);
        GridLayoutManager mLayoutManager2 = new GridLayoutManager(mContext, 1);
        mRvTickling.setLayoutManager(mLayoutManager2);
        mRvTickling.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    private void  setData() throws ParseException {
        if (!StringUtil.isEmpty(mJamedApplyDetailVO.getEndSubmitTime()) ) {
            JamedStatusDetailVO jamedStatusDetailVO=new JamedStatusDetailVO();
            jamedStatusDetailVO.setType(1);
            Date date = sdf.parse(mJamedApplyDetailVO.getEndSubmitTime());
            jamedStatusDetailVO.setDate(date);
            jamedStatusDetailVO.setTime(mJamedApplyDetailVO.getEndSubmitTime());
            if(mJamedApplyDetailVO.getSuccessFlag()==1){
                jamedStatusDetailVO.setResult(true);
            }else if(mJamedApplyDetailVO.getSuccessFlag()==-1){
                jamedStatusDetailVO.setResult(false);
            }
            jamedStatusDetailVO.setOtherTime(mJamedApplyDetailVO.getEndTime());
            mJamedStatusDetailVOs.add(jamedStatusDetailVO);
        }
        if (!StringUtil.isEmpty(mJamedApplyDetailVO.getOrgAcceptTime())){
            JamedStatusDetailVO jamedStatusDetailVO=new JamedStatusDetailVO();
            jamedStatusDetailVO.setType(0);
            Date date = sdf.parse(mJamedApplyDetailVO.getOrgAcceptTime());
            jamedStatusDetailVO.setDate(date);
            jamedStatusDetailVO.setTime(mJamedApplyDetailVO.getOrgAcceptTime());
            if(mJamedApplyDetailVO.getOrgAcceptFlag()==1){
                jamedStatusDetailVO.setResult(true);
            }else if(mJamedApplyDetailVO.getOrgAcceptFlag()==-1){
                jamedStatusDetailVO.setResult(false);
                jamedStatusDetailVO.setReason(mJamedApplyDetailVO.getNoAccpectReason());
            }
            jamedStatusDetailVO.setOtherReason(mJamedApplyDetailVO.getOrgAcceptOpinion());
            mJamedStatusDetailVOs.add(jamedStatusDetailVO);
        }
        if (mJamedApplyDetailVO.getMediaConfirm()==1||mJamedApplyDetailVO.getMediaConfirm()==-1||
                !StringUtil.isEmpty(mJamedApplyDetailVO.getMediaApplyTime())) {
            JamedStatusDetailVO jamedStatusDetailVO=new JamedStatusDetailVO();
            jamedStatusDetailVO.setType(2);
            Date date = sdf.parse(mJamedApplyDetailVO.getMediaApplyTime());
            jamedStatusDetailVO.setDate(date);
            jamedStatusDetailVO.setTime(mJamedApplyDetailVO.getMediaApplyTime());
            if(mJamedApplyDetailVO.getMediaConfirm()==1){
                jamedStatusDetailVO.setResult(true);
            }else if(mJamedApplyDetailVO.getMediaConfirm()==-1){
                jamedStatusDetailVO.setResult(false);
                jamedStatusDetailVO.setReason(mJamedApplyDetailVO.getMediaNoAcceptReason());
            }
            jamedStatusDetailVO.setOtherReason(mJamedApplyDetailVO.getMediaOpinion());
            mJamedStatusDetailVOs.add(jamedStatusDetailVO);
        }
        if (!StringUtil.isEmpty(mJamedApplyDetailVO.getApplySubmitTime())) {
            JamedStatusDetailVO jamedStatusDetailVO=new JamedStatusDetailVO();
            jamedStatusDetailVO.setType(3);
            Date date = sdf.parse(mJamedApplyDetailVO.getApplySubmitTime());
            jamedStatusDetailVO.setDate(date);
            jamedStatusDetailVO.setTime(mJamedApplyDetailVO.getApplySubmitTime());
            if(mJamedApplyDetailVO.getApplyMediaConfirm().equals("1")){
                jamedStatusDetailVO.setResult(true);
            }else if(mJamedApplyDetailVO.getApplyMediaConfirm().equals("-1")){
                jamedStatusDetailVO.setResult(false);
                jamedStatusDetailVO.setReason(mJamedApplyDetailVO.getApplynoAcceptReason());
            }
            jamedStatusDetailVO.setOtherReason(mJamedApplyDetailVO.getApplyOpinion());
            mJamedStatusDetailVOs.add(jamedStatusDetailVO);
        }
        if (!StringUtil.isEmpty(mJamedApplyDetailVO.getRecordSubmitTime()) ) {
            JamedStatusDetailVO jamedStatusDetailVO=new JamedStatusDetailVO();
            jamedStatusDetailVO.setType(4);
            Date date = sdf.parse(mJamedApplyDetailVO.getRecordSubmitTime());
            jamedStatusDetailVO.setDate(date);
            jamedStatusDetailVO.setTime(mJamedApplyDetailVO.getRecordSubmitTime());
            jamedStatusDetailVO.setOtherTime(mJamedApplyDetailVO.getRecordTime());
            jamedStatusDetailVO.setPlace(mJamedApplyDetailVO.getRecordAddress());
            mJamedStatusDetailVOs.add(jamedStatusDetailVO);
        }
        if (!StringUtil.isEmpty(mJamedApplyDetailVO.getPlaySubmitTime()) ) {
            JamedStatusDetailVO jamedStatusDetailVO=new JamedStatusDetailVO();
            jamedStatusDetailVO.setType(5);
            Date date = sdf.parse(mJamedApplyDetailVO.getPlaySubmitTime());
            jamedStatusDetailVO.setDate(date);
            jamedStatusDetailVO.setTime(mJamedApplyDetailVO.getPlaySubmitTime());
            jamedStatusDetailVO.setOtherTime(mJamedApplyDetailVO.getPlaytime());
            jamedStatusDetailVO.setChannel(mJamedApplyDetailVO.getPlayChannel());
            jamedStatusDetailVO.setTitle(mJamedApplyDetailVO.getProgramTitle());
            mJamedStatusDetailVOs.add(jamedStatusDetailVO);
        }

        Collections.sort(mJamedStatusDetailVOs, new MyComparator1());
    }

}
class MyComparator1 implements Comparator {

    public int compare(Object o1,Object o2) {
        JamedStatusDetailVO e1= (JamedStatusDetailVO) o1;
        JamedStatusDetailVO e2=(JamedStatusDetailVO)o2;
        if(e1.getDate().getTime()<e2.getDate().getTime())
            return 1;
        else
            return -1;
    }
}