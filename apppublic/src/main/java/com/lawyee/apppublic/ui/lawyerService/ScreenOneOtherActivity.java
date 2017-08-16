package com.lawyee.apppublic.ui.lawyerService;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.PopupAdapter;
import com.lawyee.apppublic.config.DataManage;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.vo.BaseCommonDataVO;

import java.util.ArrayList;
import java.util.List;

import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.TOFIELD;
import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.TOONLINE;
import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.TOOPERATION;
import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.TOSERVICE;

/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  标题
 * @Package com.lawyee.apppublic.ui.lawyerService
 * @Description:    其他具体筛选条件页面
 * @author:czq
 * @date:   2017/5/31
 * @version
 * @verdescript   2017/5/31  czq 初建
 * @Copyright: 2017/5/31 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class ScreenOneOtherActivity extends BaseActivity {

    public  static  String CONTENT="content";
    public static String TYPE="type";
    private ListView mLvChild;
    private PopupAdapter cAdapter;
    private List<BaseCommonDataVO> mContentList=new ArrayList<>();
    public BaseCommonDataVO mBaseCommonDataVO;
    private int mType;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_screen_one_other);
        mLvChild= (ListView) findViewById(R.id.lv_child);
        initData();
    }

    private void initData() {
        mType=getIntent().getIntExtra(TYPE,1);
        mBaseCommonDataVO= (BaseCommonDataVO) getIntent().getSerializableExtra(CONTENT);
        BaseCommonDataVO baseCommonDataVO=new BaseCommonDataVO();
        baseCommonDataVO.setName("全部");
        mContentList.add(baseCommonDataVO);
        switch (mType){
            case 1:
                mContentList.addAll(DataManage.getInstance().getmServiceContentList());
                break;
            case 2:
                mContentList.addAll(DataManage.getInstance().getmProfessionalFieldtList());
                break;
            case 3:
                mContentList.addAll(DataManage.getInstance().getmEpoaPeratioList());
                break;
            case 4:
                mContentList.addAll(DataManage.getInstance().getmIsOnlineList());
                break;
        }

        cAdapter = new PopupAdapter(this,R.layout.popup_item,mContentList,R.drawable.selector_screen_list_normal,R.drawable.selector_screen_list_press,1);
        cAdapter.setPressPostion(0);
        mLvChild.setAdapter(cAdapter);
        if(mBaseCommonDataVO!=null){
            int position1=0;
            for(int i=1;i<mContentList.size();i++){
                if(mBaseCommonDataVO.getOid().equals(mContentList.get(i).getOid())){
                    position1=i;
                    break;
                }
            }
            cAdapter.setPressPostion(position1);
            cAdapter.notifyDataSetChanged();
        }
        mLvChild.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cAdapter.setPressPostion(position);
                cAdapter.notifyDataSetChanged();
                mBaseCommonDataVO = mContentList.get(position);
            }
        });
    }
    public void onToolbarClick(View view)
    {
        Intent intent =new Intent();
        intent.putExtra(CONTENT,mBaseCommonDataVO);
        switch (mType){
            case 1:
                setResult(TOSERVICE, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
                break;
            case 2:
                setResult(TOFIELD, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
                break;
            case 3:
                setResult(TOOPERATION, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
                break;
            case 4:
                setResult(TOONLINE, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
                break;
        }

        finish();//此处一定要调用finish()方法
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
