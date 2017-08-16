package com.lawyee.apppublic.ui.lawyerService;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.DataManage;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.util.SerializableHashMap;
import com.lawyee.apppublic.vo.BaseCommonDataVO;
import com.lawyee.apppublic.vo.JalawFilterVO;

import net.lawyee.mobilelib.vo.BaseVO;

import java.util.ArrayList;
import java.util.HashMap;

import static com.lawyee.apppublic.R.id.tv_area;
import static com.lawyee.apppublic.ui.lawyerService.LawServiceActivity.TOSCREEN;
import static com.lawyee.apppublic.ui.lawyerService.LawServiceActivity.TY;
import static com.lawyee.apppublic.ui.lawyerService.ScreenOneActivity.AREA;

/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  标题
 * @Package com.lawyee.apppublic.ui.lawyerService
 * @Description:    筛选页面
 * @author:czq
 * @date:   2017/5/31
 * @version
 * @verdescript   2017/5/31  czq 初建
 * @Copyright: 2017/5/31 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class ScreenMuchActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 跳转标识
     */
    public  static final int  TOAREA=10001;
    public static final int  TOSERVICE=10002;
    public static final int  TOFIELD=10003;
    public static final int  TOOPERATION=10004;
    public static final int  TOONLINE=10005;
    /**
     * 跳转标识(to lawServiceActivity)
     */
    public static final String SEARCHNAME="searchname";
    public static final String SEARCHMAP="searchmap";
    /**
     * 缓存标示
     */

    //律师
    public static final String SEARCHLAWNAME="searchlawname";
    public static final String SAVEAREA="area";
    public static final String SAVESERVICE="service";
    public static final String SAVEFIELD="filed";
    public static final String SAVEOPERATION="Operation";
    public static final String SAVEONLINE="Online";
    //律所
    public static final String SEARCHLAWFIRMNAME="searchlawfirmname";
    public static final String SAVELAWFIRMAREA="lawfirmarea";
    public static final String SAVELAWFIRMSERVICE="lawfirmservice";
    public static final String SAVELAWFIRMFIELD="lawfirmfiled";
    private EditText mEtScreen;
    private ImageView mIvClearScreen;
    private TextView mTvArea;
    private RelativeLayout mRlArea;
    private TextView mTvServiceContent;
    private RelativeLayout mRlServiceContent;
    private TextView mTvProfessionalField;
    private RelativeLayout mRlProfessionalField;
    private TextView mTvEpoaOperation;
    private RelativeLayout mRlEpoaOperation;
    private TextView mTvIsOnline;
    private RelativeLayout mRlIsOnline;
    private TextView mTvClearChoose;
    private Context mContext;
    private int mType;// 律师：0；律所 ：1
    private View mViewEpoaOperatione;
    private View mViewIsOnline;
    //筛选条件VO
    private BaseCommonDataVO mNameData=new BaseCommonDataVO();//名字
    private BaseCommonDataVO mAreaData;//地区
    private BaseCommonDataVO mServiceData;//服务内容
    private BaseCommonDataVO mFieldData;//专业领域
    private BaseCommonDataVO mOperationData;//从业年限
    private BaseCommonDataVO mOnlineData;//在线情况

    private ArrayList<BaseCommonDataVO> mSreenArrayList=new ArrayList<>();
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_screen_much);
        mContext = this;
        initData();
        initView();

    }

    private void initData() {
        mType = getIntent().getIntExtra(TY, 0);
        if(mType==0){
            mNameData=getSave(SEARCHLAWNAME);
            mAreaData= getSave(SAVEAREA);
            mServiceData=  getSave(SAVESERVICE);
            mFieldData=getSave(SAVEFIELD);
            mOperationData=getSave(SAVEOPERATION);
            mOnlineData=getSave(SAVEONLINE);
        }else {
            mNameData=getSave(SEARCHLAWFIRMNAME);
            mAreaData= getSave(SAVELAWFIRMAREA);
            mServiceData=  getSave(SAVELAWFIRMSERVICE);
            mFieldData=getSave(SAVELAWFIRMFIELD);
        }

    }

    /**
     *
     * @param filename  文件名（所属类型）
     * @return 筛选VO
     */
    private BaseCommonDataVO getSave(String filename){
        Object o = BaseVO.loadVO(JalawFilterVO.dataFileName(this,filename));
        if(o!=null&&(o instanceof BaseCommonDataVO))
        {
           return (BaseCommonDataVO) o;
        }else {
            return null;
        }
    }

    private void initView() {
        mEtScreen = (EditText) findViewById(R.id.et_screen);
        mIvClearScreen = (ImageView) findViewById(R.id.iv_clear_screen);
        mTvArea = (TextView) findViewById(tv_area);
        mRlArea = (RelativeLayout) findViewById(R.id.rl_area);
        mTvServiceContent = (TextView) findViewById(R.id.tv_service_content);
        mRlServiceContent = (RelativeLayout) findViewById(R.id.rl_service_content);
        mTvProfessionalField = (TextView) findViewById(R.id.tv_professional_field);
        mRlProfessionalField = (RelativeLayout) findViewById(R.id.rl_professional_field);
        mTvEpoaOperation = (TextView) findViewById(R.id.tv_epoa_operation);
        mRlEpoaOperation = (RelativeLayout) findViewById(R.id.rl_epoa_operation);
        mTvIsOnline = (TextView) findViewById(R.id.tv_isOnline);
        mRlIsOnline = (RelativeLayout) findViewById(R.id.rl_isOnline);
        mTvClearChoose = (TextView) findViewById(R.id.tv_clear_choose);
        mViewEpoaOperatione=findViewById(R.id.view_epoa_operatione);
        mViewIsOnline=findViewById(R.id.view_isOnline);
        mIvClearScreen.setOnClickListener(this);
        mRlArea.setOnClickListener(this);
        mRlServiceContent.setOnClickListener(this);
        mRlProfessionalField.setOnClickListener(this);
        mRlEpoaOperation.setOnClickListener(this);
        mRlIsOnline.setOnClickListener(this);
        mTvClearChoose.setOnClickListener(this);
        if(mNameData!=null&&mNameData.getName()!=null&&!mNameData.getName().equals("")){
            mEtScreen.setText(mNameData.getName());
        }
        if(mAreaData!=null){
            StringBuffer stringBuffer=new StringBuffer("");
            for(int i=0;i< DataManage.getInstance().getmCityParentList().size();i++){
                if(mAreaData.getParentId().equals(DataManage.getInstance().getmCityParentList().get(i).getOid())){
                    stringBuffer.append(DataManage.getInstance().getmCityParentList().get(i).getName()+"  ");
                }
            }
            stringBuffer.append(mAreaData.getName());
            mTvArea.setText(stringBuffer);
            mTvArea.setTextColor(getResources().getColor(R.color.bg_color));
        }
        if (mServiceData != null) {
            mTvServiceContent.setText(mServiceData.getName());
            mTvServiceContent.setTextColor(getResources().getColor(R.color.bg_color));
        }
        if(mFieldData!=null){
            mTvProfessionalField.setText(mFieldData.getName());
            mTvProfessionalField.setTextColor(getResources().getColor(R.color.bg_color));
        }
        if (mType == 1) {
            mEtScreen.setHint(R.string.please_input_law_firm);
            mRlEpoaOperation.setVisibility(View.GONE);
            mViewEpoaOperatione.setVisibility(View.GONE);
            mRlIsOnline.setVisibility(View.GONE);
            mViewIsOnline.setVisibility(View.GONE);
        }else{
            if(mOperationData!=null){
                mTvEpoaOperation.setText(mOperationData.getName());
                mTvEpoaOperation.setTextColor(getResources().getColor(R.color.bg_color));
            }
            if (mOnlineData != null) {
                mTvIsOnline.setText(mOnlineData.getName());
                mTvIsOnline.setTextColor(getResources().getColor(R.color.bg_color));
            }
        }
    }

    public void onToolbarClick(View view) {
        saveSearch();
        Intent intent =new Intent();
        HashMap<String ,String > map=new HashMap<>();
        map.put(SEARCHNAME,mEtScreen.getText().toString().trim());
        if(mAreaData!=null){
            map.put(SAVEAREA,mAreaData.getOid());
    }
        if(mServiceData!=null){
            map.put(SAVESERVICE,mServiceData.getOid());
        }
        if(mFieldData!=null){
            map.put(SAVEFIELD,mFieldData.getOid());
        }
        if(mOperationData!=null){
            map.put(SAVEOPERATION,mOperationData.getOid());
        }
        if(mOnlineData!=null){
            map.put(SAVEONLINE,mOnlineData.getOid());
        }
        SerializableHashMap myMap=new SerializableHashMap(map);
        Bundle bundle=new Bundle();
        bundle.putSerializable(SEARCHMAP, myMap);
        intent.putExtras(bundle);
        setResult(TOSCREEN, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
        finish();//此处一定要调用finish()方法
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_clear_screen:
                mEtScreen.setText("");
                break;
            case R.id.rl_area:
               intent = new Intent(mContext, ScreenOneActivity.class);
                if(mAreaData!=null){
                    intent.putExtra(AREA,mAreaData);
                }
                startActivityForResult(intent, 10001);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.rl_service_content:
                intent= new Intent(mContext, ScreenOneOtherActivity.class);
                if(mServiceData!=null){
                    intent.putExtra(ScreenOneOtherActivity.CONTENT,mServiceData);
                }
                intent.putExtra(ScreenOneOtherActivity.TYPE,1);
                startActivityForResult(intent, 10002);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.rl_professional_field:
                intent= new Intent(mContext, ScreenOneOtherActivity.class);
                if(mFieldData!=null){
                    intent.putExtra(ScreenOneOtherActivity.CONTENT,mFieldData);
                }
                intent.putExtra(ScreenOneOtherActivity.TYPE,2);
                startActivityForResult(intent, 10002);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.rl_epoa_operation:
                intent= new Intent(mContext, ScreenOneOtherActivity.class);
                if(mOperationData!=null){
                    intent.putExtra(ScreenOneOtherActivity.CONTENT,mOperationData);
                }
                intent.putExtra(ScreenOneOtherActivity.TYPE,3);
                startActivityForResult(intent, 10002);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.rl_isOnline:
                intent= new Intent(mContext, ScreenOneOtherActivity.class);
                if(mOnlineData!=null){
                    intent.putExtra(ScreenOneOtherActivity.CONTENT,mOnlineData);
                }
                intent.putExtra(ScreenOneOtherActivity.TYPE,4);
                startActivityForResult(intent, 10002);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.tv_clear_choose:
                    clearChoose();
                break;
        }
    }

    private void clearChoose() {
        mTvArea.setText(R.string.all);
        mTvArea.setTextColor(getResources().getColor(R.color.pack_up_text));
        mTvServiceContent.setText(R.string.all);
        mTvServiceContent.setTextColor(getResources().getColor(R.color.pack_up_text));
        mTvProfessionalField.setText(R.string.all);
        mTvProfessionalField.setTextColor(getResources().getColor(R.color.pack_up_text));
        mTvEpoaOperation.setText(R.string.all);
        mTvEpoaOperation.setTextColor(getResources().getColor(R.color.pack_up_text));
        mTvIsOnline.setText(R.string.all);
        mTvIsOnline.setTextColor(getResources().getColor(R.color.pack_up_text));
        mAreaData=null;
        mServiceData=null;
        mFieldData=null;
        mOperationData=null;
        mOnlineData=null;
        if(mType==1){
                BaseVO.saveVO(mAreaData, JalawFilterVO.dataFileName(mContext, SAVELAWFIRMAREA));
                BaseVO.saveVO(mServiceData, JalawFilterVO.dataFileName(mContext, SAVELAWFIRMSERVICE));
                BaseVO.saveVO(mFieldData, JalawFilterVO.dataFileName(mContext, SAVELAWFIRMFIELD));
        }else {
                BaseVO.saveVO(mAreaData, JalawFilterVO.dataFileName(mContext, SAVEAREA));
                BaseVO.saveVO(mServiceData, JalawFilterVO.dataFileName(mContext, SAVESERVICE));
                BaseVO.saveVO(mFieldData, JalawFilterVO.dataFileName(mContext, SAVEFIELD));
                BaseVO.saveVO(mOperationData, JalawFilterVO.dataFileName(mContext, SAVEOPERATION));
                BaseVO.saveVO(mOnlineData, JalawFilterVO.dataFileName(mContext, SAVEONLINE));
        }

    }

    @Override
    public void finish() {
        super.finish();
    }
    //先缓存查询条件
    private void saveSearch() {
        if(mType==1) {//律所
                mNameData=new BaseCommonDataVO();
                mNameData.setName(mEtScreen.getText().toString().trim());
                BaseVO.saveVO(mNameData, JalawFilterVO.dataFileName(mContext, SEARCHLAWFIRMNAME));
                BaseVO.saveVO(mAreaData, JalawFilterVO.dataFileName(mContext, SAVELAWFIRMAREA));
                BaseVO.saveVO(mServiceData, JalawFilterVO.dataFileName(mContext, SAVELAWFIRMSERVICE));
                BaseVO.saveVO(mFieldData, JalawFilterVO.dataFileName(mContext, SAVELAWFIRMFIELD));
        }else {//律师{
                mNameData=new BaseCommonDataVO();
                mNameData.setName(mEtScreen.getText().toString().trim());
                 BaseVO.saveVO(mNameData, JalawFilterVO.dataFileName(mContext, SEARCHLAWNAME));
                BaseVO.saveVO(mAreaData, JalawFilterVO.dataFileName(mContext, SAVEAREA));
                BaseVO.saveVO(mServiceData, JalawFilterVO.dataFileName(mContext, SAVESERVICE));
                BaseVO.saveVO(mFieldData, JalawFilterVO.dataFileName(mContext, SAVEFIELD));
                BaseVO.saveVO(mOperationData, JalawFilterVO.dataFileName(mContext, SAVEOPERATION));
                BaseVO.saveVO(mOnlineData, JalawFilterVO.dataFileName(mContext, SAVEONLINE));

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 根据上面发送过去的请求吗来区别
        switch (resultCode) {
            case TOAREA:
                    BaseCommonDataVO baseCommonDataVO1 = (BaseCommonDataVO) data.getSerializableExtra(AREA);
                    if (baseCommonDataVO1 != null) {
                        StringBuffer stringBuffer=new StringBuffer("");
                        for(int i=0;i< DataManage.getInstance().getmCityParentList().size();i++){
                            if(baseCommonDataVO1.getParentId().equals(DataManage.getInstance().getmCityParentList().get(i).getOid())){
                                stringBuffer.append(DataManage.getInstance().getmCityParentList().get(i).getName()+"  ");
                            }
                        }
                        mAreaData=baseCommonDataVO1;
                        stringBuffer.append(baseCommonDataVO1.getName());
                        mTvArea.setText(stringBuffer);
                        mTvArea.setTextColor(getResources().getColor(R.color.bg_color));
                    } else {
                        mAreaData=null;
                        mTvArea.setText(R.string.all);
                        mTvArea.setTextColor(getResources().getColor(R.color.pack_up_text));
                    }
                break;
            case TOSERVICE:
                    BaseCommonDataVO baseCommonDataVO2 = (BaseCommonDataVO) data.getSerializableExtra(ScreenOneOtherActivity.CONTENT);
                    if (baseCommonDataVO2 != null&&baseCommonDataVO2.getOid()!=null&&baseCommonDataVO2.getOid().length()!=0) {
                        mServiceData=baseCommonDataVO2;
                        mTvServiceContent.setText(baseCommonDataVO2.getName());
                        mTvServiceContent.setTextColor(getResources().getColor(R.color.bg_color));
                    } else {
                        mServiceData=null;
                        mTvServiceContent.setText(R.string.all);
                        mTvServiceContent.setTextColor(getResources().getColor(R.color.pack_up_text));
                    }
                break;
            case TOFIELD:
                BaseCommonDataVO baseCommonDataVO3 = (BaseCommonDataVO) data.getSerializableExtra(ScreenOneOtherActivity.CONTENT);
                if (baseCommonDataVO3 != null&&baseCommonDataVO3.getOid()!=null&&baseCommonDataVO3.getOid().length()!=0) {
                    mFieldData=baseCommonDataVO3;
                    mTvProfessionalField.setText(baseCommonDataVO3.getName());
                    mTvProfessionalField.setTextColor(getResources().getColor(R.color.bg_color));
                } else {
                    mFieldData=null;
                    mTvProfessionalField.setText(R.string.all);
                    mTvProfessionalField.setTextColor(getResources().getColor(R.color.pack_up_text));
                }
                break;
            case TOOPERATION:
                BaseCommonDataVO baseCommonDataVO4 = (BaseCommonDataVO) data.getSerializableExtra(ScreenOneOtherActivity.CONTENT);
                if (baseCommonDataVO4 != null&&baseCommonDataVO4.getOid()!=null&&baseCommonDataVO4.getOid().length()!=0) {
                    mOperationData=baseCommonDataVO4;
                    mTvEpoaOperation.setText(baseCommonDataVO4.getName());
                    mTvEpoaOperation.setTextColor(getResources().getColor(R.color.bg_color));
                } else {
                    mOperationData=null;
                    mTvEpoaOperation.setText(R.string.all);
                    mTvEpoaOperation.setTextColor(getResources().getColor(R.color.pack_up_text));
                }
                break;
            case TOONLINE:
                BaseCommonDataVO baseCommonDataVO5 = (BaseCommonDataVO) data.getSerializableExtra(ScreenOneOtherActivity.CONTENT);
                if (baseCommonDataVO5 != null&&baseCommonDataVO5.getOid()!=null&&baseCommonDataVO5.getOid().length()!=0) {
                    mOnlineData=baseCommonDataVO5;
                    mTvIsOnline.setText(baseCommonDataVO5.getName());
                    mTvIsOnline.setTextColor(getResources().getColor(R.color.bg_color));
                } else {
                    mOnlineData=null;
                    mTvIsOnline.setText(R.string.all);
                    mTvIsOnline.setTextColor(getResources().getColor(R.color.pack_up_text));
                }
                break;
            default:
                break;
        }
    }

}
