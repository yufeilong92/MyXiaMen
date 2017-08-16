package com.lawyee.apppublic.ui.personalcenter.lawyer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.FunctionAdpater;
import com.lawyee.apppublic.config.DataManage;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JacstService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.vo.BaseCommonDataVO;
import com.lawyee.apppublic.widget.RecycleViewDivider;

import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
import java.util.List;

import static com.lawyee.apppublic.R.id.tv_submit;
/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  咨询处理界面
 * @Package com.lawyee.apppublic.ui.personalcenter.lawyer
 * @Description:    注释
 * @author:lzh
 * @date:   2017/8/8
 * @version
 * @verdescript   2017/8/8  czq 初建
 * @Copyright: 2017/8/8 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class ConsultDealActivity extends BaseActivity implements View.OnClickListener{

    public static  final String CONSULTBUSINESS="consultBusiness";
    public static  final String CONSULTPERSON="consultPerson";
    public static  final String CONSULTPERSONID="consultPersonId";
    public static  final String CONSULTSUCCESS="consultSuccess";
    private TextView mTvBusinessType;
    private EditText mEtBusinessContent;
    private EditText mEtDealExplain;
    private TextView mTvSubmit;
    private Context mContext;
    private MaterialDialog mPopWindowsShow;
    List<BaseCommonDataVO> mConsultLists = new ArrayList<>();
    private List<String> mDatas=new ArrayList<>();
    private int mPosition;
    private String mbusiness;
    private String mJid;
    private String mJidName;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_consult_deal);
        mContext=this;
        initView();
        initData();
    }

    private void initData() {
        mbusiness=getIntent().getStringExtra(CONSULTBUSINESS);
        mJid=getIntent().getStringExtra(CONSULTPERSONID);
        mJidName=getIntent().getStringExtra(CONSULTPERSON);
        if(mbusiness==null||mbusiness.equals("")){
            finish();
        }
        mConsultLists= DataManage.getInstance().getmConsultTypeLists();
        for(int i = 0; i <mConsultLists.size() ; i++) {
            mDatas.add(mConsultLists.get(i).getName());
        }

    }

    private void initView() {
        mTvBusinessType= (TextView) findViewById(R.id.tv_business_type);
        mEtBusinessContent= (EditText) findViewById(R.id.et_business_content);
        mEtDealExplain= (EditText) findViewById(R.id.et_deal_explain);
        mTvSubmit= (TextView) findViewById(tv_submit);
        mTvBusinessType.setOnClickListener(this);
        mTvSubmit.setOnClickListener(this);

    }
    /**
     * @param mData 数据
     */
    private void handlerPopWindos(final List<String> mData) {
        final FunctionAdpater applyPopAdapter = new FunctionAdpater(mData, this);
        final GridLayoutManager manager = new GridLayoutManager(this, 1);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        if (mPopWindowsShow == null || !mPopWindowsShow.isShowing()) {
            mPopWindowsShow = new MaterialDialog.Builder(this)
                    .adapter(applyPopAdapter, manager)
                    .backgroundColorRes(R.color.activity_content_bg)
                    .show();
            mPopWindowsShow.getRecyclerView().addItemDecoration(new RecycleViewDivider(this, GridLayoutManager.VERTICAL, R.drawable.bg_rlv_diving));
        }
        applyPopAdapter.setOnRecyclerItemClickListener(new FunctionAdpater.OnRecyclerItemClickListener() {
            @Override
            public void OnItemClickListener(View view, String itemVo, int position) {
                mPosition=position;
                mTvBusinessType.setText(itemVo);
                mPopWindowsShow.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_business_type:
                handlerPopWindos(mDatas);
                break;
            case R.id.tv_submit:
                saveBusiness(mConsultLists.get(mPosition).getOid(),mDatas.get(mPosition),
                        mEtBusinessContent.getText().toString(),mEtDealExplain.getText().toString());
                break;
        }
    }

    private void saveBusiness(String consultType,String consultTypeName,
                              String consultCotent,String dealResult) {
        if(StringUtil.isEmpty(consultType)){
            T.showLong(mContext,getString(R.string.please_choose_business));
            return;
        }
        if(StringUtil.isEmpty(consultCotent)){
            T.showLong(mContext,getString(R.string.please_input_content_errorhint));
            mEtBusinessContent.requestFocus();
            return;
        }
        if(StringUtil.isEmpty(dealResult)){
            T.showLong(mContext,getString(R.string.please_input_content_errorhint));
            mEtDealExplain.requestFocus();
            return;
        }
        if (getInProgess())
            return;
        setInProgess(true);
        JacstService service = new JacstService(mContext);
        service.setProgressShowContent(mContext.getString(R.string.submit_ing));
        service.setShowProgress(true);
        service.postJacstConsulationHandle(mbusiness, consultType, consultTypeName, consultCotent,dealResult,
                mJidName,mJid,new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                setInProgess(false);
                T.showShort(mContext, R.string.submit_success);
                Intent intent =new Intent();
                intent.putExtra(CONSULTSUCCESS,"succeed");
              setResult(RESULT_OK, intent);

                finish();
            }

            @Override
            public void onError(String msg, String content) {
                setInProgess(false);
                T.showLong(mContext, msg);
            }
        });
    }
}
