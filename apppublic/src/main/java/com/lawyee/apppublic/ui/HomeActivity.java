package com.lawyee.apppublic.ui;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.andview.refreshview.XRefreshView;
import com.jauker.widget.BadgeView;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.HomeAdpater;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.InfoService;
import com.lawyee.apppublic.ui.lawAdministration.PersionActivity;
import com.lawyee.apppublic.ui.personalcenter.LoginActivity;
import com.lawyee.apppublic.ui.personalcenter.PersonCenterActivity;
import com.lawyee.apppublic.ui.personalcenter.lawyer.LawyerPCenterActivity;
import com.lawyee.apppublic.util.db.ChatProvider;
import com.lawyee.apppublic.util.db.IMDBHelper;
import com.lawyee.apppublic.vo.InfomationVO;
import com.lawyee.apppublic.vo.UserVO;
import com.lawyee.apppublic.widget.XRefreshViewLayout;

import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  标题
 * @Package com.lawyee.apppublic.ui
 * @Description:    首页Activity
 * @author:czq
 * @date:   2017/5/15
 * @version
 * @verdescript   2017/5/15  czq 初建
 * @Copyright: 2017/5/15 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class HomeActivity extends BaseActivity {
    private final static String HOMEID="homenews";
    private RecyclerView mRecyclerView;
    private HomeAdpater mAdpater;
    private Context mContext;
    private XRefreshViewLayout mXrefreshview;
    /**
     * 数据是否处理中，用于服务端请求数据时标识，防止重复申请
     */
    private Boolean mInProgess= false;
    /**
     * 数据列表
     */
    @SuppressWarnings("rawtypes")
    protected ArrayList mDataList;
    private String[] mId;
    private BadgeView badgeView;
    private Handler mChatHandler = new Handler();
    private ImageView iv_per;
    private int mNum;
    private ChatObserver mChatObserver = new ChatObserver();
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        mContext=this;
        initView();
        loadData();
        getContentResolver().registerContentObserver(
                ChatProvider.CONTENT_URI, true, mChatObserver);// 开始聊天数据库
    }

    @Override
    protected void onResume() {
        super.onResume();
       loadMessageNum();
    }

    private void loadMessageNum() {
        if(ApplicationSet.getInstance().getUserVO()!=null) {
            int mNum = IMDBHelper.getInstance().getNoReadMessage(ApplicationSet.getInstance().getUserVO().getOpenfireLoginId());
            setBadgView(badgeView,mNum);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(mChatObserver);
    }

    private void initView() {
        mRecyclerView= (RecyclerView) findViewById(R.id.rv_home);
        mXrefreshview= (XRefreshViewLayout) findViewById(R.id.xrefreshview);
        iv_per= (ImageView) findViewById(R.id.iv_per);
        // 设置是否可以下拉刷新
        mXrefreshview.setPullRefreshEnable(true);
        mXrefreshview.restoreLastRefreshTime(0l);
        badgeView = new BadgeView(this);
        badgeView.setTargetView(iv_per);
        badgeView.setBackground(12, Color.parseColor("#FFFF00"));
        badgeView.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
        badgeView.setBadgeMargin(0, 5 , 0, 0 );
        badgeView.setTypeface(Typeface.create(Typeface.SANS_SERIF,
               Typeface.ITALIC));
        badgeView.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
     //   badgeView.setShadowLayer(2, -1, -1, Color.BLACK);
        badgeView.setVisibility(View.GONE);
    }

    public void onToolbarClick(View view)
    {
        UserVO userVO = ApplicationSet.getInstance().getUserVO();
        if(userVO==null|| StringUtil.isEmpty(userVO.getLoginId())) {
            startActivity(new Intent(mContext, LoginActivity.class));
        }else
        {
            if(userVO.isPublicUser()){
                startActivity(new Intent(mContext,PersonCenterActivity.class));
            }else{
                       if(userVO.getRole().equals(UserVO.CSTR_USERROLE_JALAW)){
                           startActivity(new Intent(mContext,LawyerPCenterActivity.class));
                       }else if(userVO.getRole().equals(UserVO.CSTR_USERROLE_JAMED)){//人民调解服务
                           Intent intent = new Intent(mContext, PersionActivity.class);
                           intent.putExtra(PersionActivity.CONTENT_PARAMETER_TYPE, PersionActivity.CONTENT_PARAMETER_JAMED);
                           startActivity(intent);
                       }else if (userVO.getRole().equals(UserVO.CSTR_USERROLE_MEDIAWORKER)){//媒体调解服务
                           Intent intent = new Intent(mContext, PersionActivity.class);
                           intent.putExtra(PersionActivity.CONTENT_PARAMETER_TYPE, PersionActivity.CONTENT_PARAMETER_MEDIA);
                           startActivity(intent);

                       }
            }

        }
    }

private void setAdapetData() {
    GridLayoutManager gridLayoutManager=new GridLayoutManager(mContext,1);
    mRecyclerView.setLayoutManager(gridLayoutManager);
    mAdpater=new HomeAdpater(mContext,mDataList);
    mRecyclerView.setAdapter(mAdpater);


    mXrefreshview.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
        @Override
        public void onRefresh(boolean isPullDown) {
            loadNewData();
        }


        @Override
        public void onRelease(float direction) {
            super.onRelease(direction);
        }
    });
}
    /**
     * 读取数据
     */
    private void loadData()
    {
        mId=mContext.getResources().getStringArray(R.array.homeInfoID);
        clearDataList();
        //读取缓存
        List list = InfomationVO.loadVOList(InfomationVO.dataListFileName(mContext,HOMEID));
        if(list!=null&&!list.isEmpty())
        {
            addDataList(list);
        }
        setAdapetData();
        Boolean mustRefresh = true;
        //判断是否在有效期内
        if(mDataList!=null&&!mDataList.isEmpty())
        {
            InfomationVO vo;
            Object o = mDataList.get(0);
            if(o instanceof  InfomationVO)
            {
                vo = (InfomationVO)o;
                mXrefreshview.restoreLastRefreshTime(vo.getVoCreateDate().getTime());
                if(vo.isEffectiveTimeData(Constants.CINT_EFFECTIVE_NEWS_TIME))
                    mustRefresh =false;
            }
//            if(mDataList.size()%Constants.CINT_PAGE_SIZE==0)
//                // 设置是否可以上拉加载
//                mXrefreshview.setPullLoadEnable(true);
        }
        if(mustRefresh) {
            mXrefreshview.startRefresh();
        }
    }

    /**
     * 增加列表数据
     *
     * @param list
     */
    @SuppressWarnings({ "unchecked" })
    private void addDataList(List<?> list) {
        if (mDataList == null)
            clearDataList();
        if (list == null || list.isEmpty())
            return;
        mDataList.addAll(list);
    }

    /**
     * 清除数据
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void clearDataList() {
        if (mDataList == null) {
            mDataList = new ArrayList();
        } else
            mDataList.clear();
    }
    /**
     * 刷新数据
     */
    protected void loadNewData()
    {
        if(mInProgess)
            return;
        mInProgess=true;
        InfoService service = new InfoService(mContext);
        service.getList(mId[0], 1, null, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                mInProgess = false;
                mXrefreshview.stopRefresh();
                if(values==null||values.isEmpty()) {
                    T.showShort(mContext,content);
                    return;
                }

                ArrayList list = (ArrayList) values
                        .get(0);

                clearDataList();
                if (list != null && !list.isEmpty()) {
                    addDataList(list);
                }
                Log.e("czq",mDataList.size()+"");
                //缓存数据
                InfomationVO.saveVOList(mDataList,InfomationVO.dataListFileName(mContext,HOMEID));
                    mXrefreshview.setLoadComplete(true);
                mAdpater.notifyDataSetChanged();
            }

            @Override
            public void onError(String msg, String content) {
                mInProgess = false;
                mXrefreshview.stopRefresh();
                T.showLong(mContext,msg);
            }
        });
    }
    /**
     * 聊天数据库变化监听
     */
    private class ChatObserver extends ContentObserver {
        public ChatObserver() {
            super(mChatHandler);
        }

        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            updateChatStatus();
        }
    }

    private void updateChatStatus() {
        Observable<Boolean> observable = Observable.create(
                new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                       int num  = IMDBHelper.getInstance().getNoReadMessage(ApplicationSet.getInstance().getUserVO().getOpenfireLoginId());
                        if(mNum!=num){
                            mNum=num;
                            e.onNext(true);
                        }

                    }
                }
        );
        Observer<Boolean> subscriber = new Observer<Boolean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Boolean b) {
                if (b) {
                    setBadgView(badgeView,mNum);
                }
            }

            @Override
            public void onError(Throwable t) {
                T.showLong(mContext, t.getMessage());
            }

            @Override
            public void onComplete() {
            }
        };
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }
}
