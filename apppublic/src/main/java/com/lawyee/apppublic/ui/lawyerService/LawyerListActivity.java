package com.lawyee.apppublic.ui.lawyerService;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.LawyerListAdpater;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JalawService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.vo.JalawLawfirmVO;
import com.lawyee.apppublic.vo.JalawLawyerVO;

import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.lawyee.apppublic.ui.lawyerService.LawFirmDetailActivity.JALAWFIRMVO;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @Title: 标题
 * @Package com.lawyee.apppublic.ui.lawyerService
 * @Description: 律师列表页面
 * @author:czq
 * @date: 2017/5/31
 * @verdescript 2017/5/31  czq 初建
 * @Copyright: 2017/5/31 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class LawyerListActivity extends BaseActivity {


    private RecyclerView mRvContent;
    private List<JalawLawyerVO> mJalawLawyerVOs = new ArrayList<>();
    private LawyerListAdpater mLawyerAdpater;
    private GridLayoutManager mLayoutManager;
    private Context mContext;
    private JalawLawfirmVO mJalawLawfirmVO;
    private TextView mLawyerListEmptyTv;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lawyer_list);
        mContext = this;
        initView();
        loadData(mJalawLawfirmVO.getOid());
    }

    private void loadData(String id) {
        if (getInProgess())
            return;
        setInProgess(true);
        JalawService service = new JalawService(mContext);
        service.setProgressShowContent(getString(R.string.get_ing));
        service.setShowProgress(true);
        service.getLawyersWithLawfirm(id, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                setInProgess(false);
                if (values == null || values.isEmpty()) {
                    T.showShort(mContext, content);
                    return;
                }
                ArrayList list = (ArrayList) values
                        .get(0);
                if(list==null||list.size()==0){
                    mRvContent.setVisibility(View.GONE);
                    mLawyerListEmptyTv.setVisibility(View.VISIBLE);
                    return;
                }
                clearDataList();
                if (list != null && !list.isEmpty()) {
                    addDataList(list);
                }
                mLawyerAdpater.notifyDataSetChanged();
            }

            @Override
            public void onError(String msg, String content) {
                T.showLong(mContext, msg);
                setInProgess(false);
            }
        });
    }

    /**
     * 增加列表数据
     *
     * @param list
     */
    @SuppressWarnings({"unchecked"})
    private void addDataList(List<?> list) {
        if (mJalawLawyerVOs == null)
            clearDataList();
        if (list == null || list.isEmpty())
            return;
        mJalawLawyerVOs.addAll((Collection<? extends JalawLawyerVO>) list);
    }

    /**
     * 清除数据
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void clearDataList() {
        if (mJalawLawyerVOs == null) {
            mJalawLawyerVOs = new ArrayList();
        } else
            mJalawLawyerVOs.clear();
    }


    private void initView() {
        mJalawLawfirmVO = (JalawLawfirmVO) getIntent().getSerializableExtra(JALAWFIRMVO);
        if (mJalawLawfirmVO == null) {
            finish();
        }
        mRvContent = (RecyclerView) findViewById(R.id.lawyer_list_rv);
        mLawyerListEmptyTv= (TextView) findViewById(R.id.lawyer_list_empty_tv);
        initRecycleView();
    }

    private void initRecycleView() {
        mLawyerAdpater = new LawyerListAdpater(mContext, mJalawLawyerVOs, 0);
        mRvContent.setAdapter(mLawyerAdpater);
        mLayoutManager = new GridLayoutManager(mContext, 1);
        mRvContent.setLayoutManager(mLayoutManager);
        mRvContent.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

    }


}


