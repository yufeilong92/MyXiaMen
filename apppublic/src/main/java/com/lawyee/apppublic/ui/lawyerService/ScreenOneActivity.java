package com.lawyee.apppublic.ui.lawyerService;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.PopupAdapter;
import com.lawyee.apppublic.config.DataManage;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.vo.BaseCommonDataVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.TOAREA;

/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  标题
 * @Package com.lawyee.apppublic.ui.lawyerService
 * @Description:    城市列表筛选
 * @author:czq
 * @date:   2017/5/31
 * @version
 * @verdescript   2017/5/31  czq 初建
 * @Copyright: 2017/5/31 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class ScreenOneActivity extends BaseActivity {

    public  static  String AREA="area";
    private RelativeLayout mRlAll;
    private ListView mLvParent;
    private ListView mLvChild;
    private ImageView mIvAll;
    private List<BaseCommonDataVO> mCityParentList=new ArrayList<>();
    private Map<String,List<BaseCommonDataVO>> mCityChild=new HashMap<>();
   private List<BaseCommonDataVO> mCityChildDetailtList=new ArrayList<>();
    private PopupAdapter pAdapter;
    private PopupAdapter cAdapter;
    private BaseCommonDataVO mBaseCommonDataVO;
    private int mPositon=0;
    private Context mContext;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_screen_one);
        mContext=this;
        initView();
        initData();
    }



    private void initView() {
        mRlAll= (RelativeLayout) findViewById(R.id.rl_all);
        mLvParent= (ListView) findViewById(R.id.lv_parent);
        mLvChild= (ListView) findViewById(R.id.lv_child);
        mIvAll= (ImageView) findViewById(R.id.iv_all);
        mRlAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIvAll.getVisibility()!=View.VISIBLE){
                    mIvAll.setVisibility(View.VISIBLE);
                    mBaseCommonDataVO=null;
                    cAdapter.setCheck(true);
                    cAdapter.setPressPostion(-1);
                    cAdapter.notifyDataSetChanged();
                }
            }
        });
    }
    private void initData() {
        mBaseCommonDataVO= (BaseCommonDataVO) getIntent().getSerializableExtra(AREA);
        mCityParentList= DataManage.getInstance().getmCityParentList();
        mCityChild=DataManage.getInstance().getmCityChild();
        BaseCommonDataVO baseCommonDataVO=new BaseCommonDataVO();
        baseCommonDataVO.setName("全部");
        mCityChildDetailtList.add(baseCommonDataVO);
        if(mCityParentList!=null&&!mCityParentList.isEmpty()&&mCityChild!=null)
            mCityChildDetailtList.addAll(mCityChild.get(mCityParentList.get(0).getOid()));
       pAdapter = new PopupAdapter(this,R.layout.popup_item,mCityParentList,R.drawable.selector_screen_list_normal,R.drawable.selector_screen_list_press2,0);
         cAdapter = new PopupAdapter(this,R.layout.popup_item,mCityChildDetailtList,R.drawable.selector_screen_list_normal,R.drawable.selector_screen_list_press,1);
        pAdapter.setPressPostion(0);
        mLvParent.setAdapter(pAdapter);
        mLvChild.setAdapter(cAdapter);
        if(mBaseCommonDataVO!=null){
            int position1 = 0;
            int position2=0;
            if(mCityParentList!=null) {
                for (int i = 0; i < mCityParentList.size(); i++) {
                    if (mBaseCommonDataVO.getParentId().equals(mCityParentList.get(i).getOid())) {
                        position1 = getPosition(mBaseCommonDataVO.getParentId(), 0, 0);
                        position2 = getPosition(mBaseCommonDataVO.getOid(), 1, position1);
                        break;
                    }
                }
            }
            if(position2==0){
                position1= getPosition(mBaseCommonDataVO.getOid(),0,0);
            }
            mIvAll.setVisibility(View.GONE);
            pAdapter.setPressPostion(position1);
            pAdapter.notifyDataSetChanged();
            mCityChildDetailtList.clear();
            BaseCommonDataVO baseCommonDataVO1=new BaseCommonDataVO();
            baseCommonDataVO1.setName("全部");
            mCityChildDetailtList.add(baseCommonDataVO1);
            mCityChildDetailtList.addAll(mCityChild.get(mCityParentList.get(position1).getOid()));
            if(cAdapter.isCheck()){
                cAdapter.setCheck(false);
            }
            cAdapter.notifyDataSetChanged();
            cAdapter.setPressPostion(0);
            mLvChild.setSelection(0);
            if(position2!=0){
                if(cAdapter.isCheck()){
                    cAdapter.setCheck(false);
                }
                cAdapter.setPressPostion(position2);
                cAdapter.notifyDataSetChanged();
            }
        }
        mLvParent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPositon=position;
                mIvAll.setVisibility(View.GONE);
                pAdapter.setPressPostion(position);
                pAdapter.notifyDataSetChanged();
                mCityChildDetailtList.clear();
                BaseCommonDataVO baseCommonDataVO=new BaseCommonDataVO();
                baseCommonDataVO.setName("全部");
                mCityChildDetailtList.add(baseCommonDataVO);
                mCityChildDetailtList.addAll(mCityChild.get(mCityParentList.get(position).getOid()));
                if(cAdapter.isCheck()){
                    cAdapter.setCheck(false);
                }
                cAdapter.notifyDataSetChanged();
                cAdapter.setPressPostion(0);
                mLvChild.setSelection(0);
                mBaseCommonDataVO=mCityParentList.get(position);
            }
        });

        mLvChild.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mIvAll.setVisibility(View.GONE);
                if(cAdapter.isCheck()){
                    cAdapter.setCheck(false);
                }
                cAdapter.setPressPostion(position);
                cAdapter.notifyDataSetChanged();
                if(position==0){
                    mBaseCommonDataVO=mCityParentList.get(mPositon);
                }else {
                    mBaseCommonDataVO = mCityChildDetailtList.get(position);
                }
            }
        });
    }

    private int  getPosition(String oid,int tip ,int parent) {
        int position=0;
        if (tip == 0) {
            for (int i = 0; i < mCityParentList.size(); i++) {
                if (mCityParentList.get(i).getOid().equals(oid)) {
                    position=i;
                    break;
                }
            }
        } else {
             List<BaseCommonDataVO> list=new ArrayList<>();
            list.addAll(mCityChild.get(mCityParentList.get(parent).getOid()));
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getOid().equals(oid)) {
                    position=i+1;
                    break;
                }
            }
        }
        return position;
    }

    public void onToolbarClick(View view)
    {
        Intent intent =new Intent();
        if(mIvAll.getVisibility()==View.GONE){
            intent.putExtra(AREA,mBaseCommonDataVO);
        }
        setResult(TOAREA, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
        finish();//此处一定要调用finish()方法

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
