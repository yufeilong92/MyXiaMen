package com.lawyee.apppublic.ui.basiclaw;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.DataManage;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.ui.lawyerService.ScreenOneActivity;
import com.lawyee.apppublic.ui.lawyerService.ScreenOneOtherActivity;
import com.lawyee.apppublic.util.SerializableHashMap;
import com.lawyee.apppublic.vo.BaseCommonDataVO;
import com.lawyee.apppublic.vo.JalawFilterVO;

import net.lawyee.mobilelib.vo.BaseVO;

import java.util.ArrayList;
import java.util.HashMap;

import static com.lawyee.apppublic.R.id.tv_area;
import static com.lawyee.apppublic.ui.basiclaw.BasicLawServiceActivity.BASICTY;
import static com.lawyee.apppublic.ui.lawyerService.LawServiceActivity.TOSCREEN;
import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.TOAREA;
import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.TOFIELD;
import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.TOONLINE;
import static com.lawyee.apppublic.ui.lawyerService.ScreenOneActivity.AREA;
/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  标题
 * @Package com.lawyee.apppublic.ui.basiclaw
 * @Description:    基层法律服务的筛选页
 * @author:lzh
 * @date:   2017/8/9
 * @version
 * @verdescript   2017/8/9  lzh 初建
 * @Copyright: 2017/8/9 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class BasicScreenMuchActivity extends BaseActivity  implements View.OnClickListener{
    /**
     * 跳转标识
     */
    /**
     * 跳转标识(to lawServiceActivity)
     */
    public static final String SEARCHWOEKERNAME="searchworkername";
    public static final String SEARCHWOEKERMAP="searchworkermap";
    /**
     * 缓存标示
     */

    //工作者
    public static final String SEARCHWORKERNAME="searchworkername";
    public static final String SAVEWORKERAREA="workerarea";
    public static final String SAVEWORKERFIELD="workerfiled";
    public static final String SAVEWORKERONLINE="workerOnline";
    //服务所
    public static final String SEARCHSERVICEOFFICENAME="searchserviceofficename";
    public static final String SAVESERVICEOFFICEAREA="serviceofficearea";
    public static final String SAVESERVICEOFFICEFIELD="serviceofficefiled";
    private Context mContext;
    private int mType;// 工作者：1；服务所 ：0

    //筛选条件VO
    private BaseCommonDataVO mNameData=new BaseCommonDataVO();//名字
    private BaseCommonDataVO mAreaData;//地区
    private BaseCommonDataVO mFieldData;//专业领域
    private BaseCommonDataVO mOnlineData;//在线情况
    private TextView mTextView;
    private TextView mTvSelect;
    private LinearLayout mActivityTitleContainer;
    private EditText mEtScreen;
    private ImageView mIvClearScreen;
    private ImageView mIvRight1;
    private TextView mTvArea;
    private RelativeLayout mRlArea;
    private ImageView mIvRight2;
    private ImageView mIvRight3;
    private TextView mTvProfessionalField;
    private RelativeLayout mRlProfessionalField;
    private View mViewEpoaOperatione;
    private ImageView mIvRight5;
    private TextView mTvIsOnline;
    private RelativeLayout mRlIsOnline;
    private View mViewIsOnline;
    private TextView mTvClearChoose;
        private ArrayList<BaseCommonDataVO> mSreenArrayList=new ArrayList<>();
        @Override
        protected void initContentView(Bundle savedInstanceState) {
            setContentView(R.layout.activity_basic_screen_much);
            mContext = this;
            initData();
            initView();

        }

        private void initData() {
            mType = getIntent().getIntExtra(BASICTY, 0);
            if(mType==1){
                mNameData=getSave(SEARCHWORKERNAME);
                mAreaData= getSave(SAVEWORKERAREA);
                mFieldData=getSave(SAVEWORKERFIELD);
                mOnlineData=getSave(SAVEWORKERONLINE);
            }else {
                mNameData=getSave(SEARCHSERVICEOFFICENAME);
                mAreaData= getSave(SAVESERVICEOFFICEAREA);
                mFieldData=getSave(SAVESERVICEOFFICEFIELD);
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
            mTvProfessionalField = (TextView) findViewById(R.id.tv_professional_field);
            mRlProfessionalField = (RelativeLayout) findViewById(R.id.rl_professional_field);
            mTvIsOnline = (TextView) findViewById(R.id.tv_isOnline);
            mRlIsOnline = (RelativeLayout) findViewById(R.id.rl_isOnline);
            mTvClearChoose = (TextView) findViewById(R.id.tv_clear_choose);
            mViewEpoaOperatione=findViewById(R.id.view_epoa_operatione);
            mViewIsOnline=findViewById(R.id.view_isOnline);
            mIvClearScreen.setOnClickListener(this);
            mRlArea.setOnClickListener(this);
            mRlProfessionalField.setOnClickListener(this);
            mRlIsOnline.setOnClickListener(this);
            mTvClearChoose.setOnClickListener(this);
            if(mNameData!=null&&mNameData.getName()!=null&&!mNameData.getName().equals("")){
                mEtScreen.setText(mNameData.getName());
            }
            if(mAreaData!=null){
                StringBuffer stringBuffer=new StringBuffer("");
                for(int i = 0; i< DataManage.getInstance().getmCityParentList().size(); i++){
                    if(mAreaData.getParentId().equals(DataManage.getInstance().getmCityParentList().get(i).getOid())){
                        stringBuffer.append(DataManage.getInstance().getmCityParentList().get(i).getName()+"  ");
                    }
                }
                stringBuffer.append(mAreaData.getName());
                mTvArea.setText(stringBuffer);
                mTvArea.setTextColor(getResources().getColor(R.color.bg_color));
            }
            if(mFieldData!=null){
                mTvProfessionalField.setText(mFieldData.getName());
                mTvProfessionalField.setTextColor(getResources().getColor(R.color.bg_color));
            }
            if (mType == 0) {
                mEtScreen.setHint(R.string.please_input_service_office);
                mRlIsOnline.setVisibility(View.GONE);
                mViewIsOnline.setVisibility(View.GONE);
            }else{
                mEtScreen.setHint(R.string.please_input_worker);
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
            map.put(SEARCHWORKERNAME,mEtScreen.getText().toString().trim());
            if(mAreaData!=null){
                map.put(SAVEWORKERAREA,mAreaData.getOid());
            }
            if(mFieldData!=null){
                map.put(SAVEWORKERFIELD,mFieldData.getOid());
            }
            if(mOnlineData!=null){
                map.put(SAVEWORKERONLINE,mOnlineData.getOid());
            }
            SerializableHashMap myMap=new SerializableHashMap(map);
            Bundle bundle=new Bundle();
            bundle.putSerializable(SEARCHWOEKERMAP, myMap);
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
                case R.id.rl_professional_field:
                    intent= new Intent(mContext, ScreenOneOtherActivity.class);
                    if(mFieldData!=null){
                        intent.putExtra(ScreenOneOtherActivity.CONTENT,mFieldData);
                    }
                    intent.putExtra(ScreenOneOtherActivity.TYPE,2);
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
            mTvProfessionalField.setText(R.string.all);
            mTvProfessionalField.setTextColor(getResources().getColor(R.color.pack_up_text));
            mTvIsOnline.setText(R.string.all);
            mTvIsOnline.setTextColor(getResources().getColor(R.color.pack_up_text));
            mAreaData=null;
            mFieldData=null;
            mOnlineData=null;
            if(mType==0){
                BaseVO.saveVO(mAreaData, JalawFilterVO.dataFileName(mContext, SAVESERVICEOFFICEAREA));
                BaseVO.saveVO(mFieldData, JalawFilterVO.dataFileName(mContext, SAVESERVICEOFFICEFIELD));
            }else {
                BaseVO.saveVO(mAreaData, JalawFilterVO.dataFileName(mContext, SAVEWORKERAREA));
                BaseVO.saveVO(mFieldData, JalawFilterVO.dataFileName(mContext, SAVEWORKERFIELD));
                BaseVO.saveVO(mOnlineData, JalawFilterVO.dataFileName(mContext, SAVEWORKERONLINE));
            }

        }

        @Override
        public void finish() {
            super.finish();
        }
        //先缓存查询条件
        private void saveSearch() {
            if(mType==0) {//服务所
                mNameData=new BaseCommonDataVO();
                mNameData.setName(mEtScreen.getText().toString().trim());
                BaseVO.saveVO(mNameData, JalawFilterVO.dataFileName(mContext, SEARCHSERVICEOFFICENAME));
                BaseVO.saveVO(mAreaData, JalawFilterVO.dataFileName(mContext, SAVESERVICEOFFICEAREA));
                BaseVO.saveVO(mFieldData, JalawFilterVO.dataFileName(mContext, SAVESERVICEOFFICEFIELD));
            }else {//工作者{
                mNameData=new BaseCommonDataVO();
                mNameData.setName(mEtScreen.getText().toString().trim());
                BaseVO.saveVO(mNameData, JalawFilterVO.dataFileName(mContext, SEARCHWORKERNAME));
                BaseVO.saveVO(mAreaData, JalawFilterVO.dataFileName(mContext, SAVEWORKERAREA));
                BaseVO.saveVO(mFieldData, JalawFilterVO.dataFileName(mContext, SAVEWORKERFIELD));
                BaseVO.saveVO(mOnlineData, JalawFilterVO.dataFileName(mContext, SAVEWORKERONLINE));

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
