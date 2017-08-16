package com.lawyee.apppublic.ui.personalcenter;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.MyMessageAdpater;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.util.db.ChatProvider;
import com.lawyee.apppublic.util.db.IMDBHelper;
import com.lawyee.apppublic.vo.ConsulationRecordVO;
import com.lawyee.apppublic.vo.UserVO;

import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @Title: 标题
 * @Package com.lawyee.apppublic.ui.personalcenter
 * @Description: 我的消息Activity
 * @author:czq
 * @date: 2017/5/31
 * @verdescript 2017/5/31  czq 初建
 * @Copyright: 2017/5/31 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class MyMessageActivity extends BaseActivity {


    private RecyclerView mRvMessage;
    private MyMessageAdpater mAdpater;
    private GridLayoutManager mLayoutManager;
    private Context mContext;
    private ArrayList<ConsulationRecordVO> mConsulationRecordVOs = new ArrayList<>();
    private ArrayList<String> mList=new ArrayList<String>();
    private ChatObserver mChatObserver = new ChatObserver();
    private Handler mChatHandler = new Handler();

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_message);
        mContext = this;
        initView();
        setReAdpater();
        getContentResolver().registerContentObserver(
                ChatProvider.CONTENT_URI, true, mChatObserver);// 开始聊天数据库
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNewData();
        mAdpater.setmDatas(mConsulationRecordVOs);
    }

    private void initView() {
        mRvMessage = (RecyclerView) findViewById(R.id.my_message_rv);

    }

    private void setReAdpater(){
        mAdpater = new MyMessageAdpater(mConsulationRecordVOs, mContext);
        mLayoutManager = new GridLayoutManager(mContext, 1);
        mRvMessage.setLayoutManager(mLayoutManager);
        mRvMessage.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mRvMessage.setAdapter(mAdpater);

    }



    private void loadNewData() {
       UserVO userVO=ApplicationSet.getInstance().getUserVO();
        mConsulationRecordVOs.clear();
        mList= (ArrayList) IMDBHelper.getInstance().getFriend(ApplicationSet.getInstance().getUserVO().getOpenfireLoginId());
        for(int i = 0; i <mList.size() ; i++) {
            ConsulationRecordVO consulationRecordVO=IMDBHelper.getInstance().
                    getFristWithFriend(ApplicationSet.getInstance().getUserVO().getOpenfireLoginId(),mList.get(i));
            int num=(IMDBHelper.getInstance().
                    getNoReadMessageWithFriend(ApplicationSet.getInstance().getUserVO().getOpenfireLoginId(),mList.get(i)));
            consulationRecordVO.setNoReadnum(num);
            mConsulationRecordVOs.add(consulationRecordVO);
        }

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

                        loadNewData();
                        e.onNext(mAdpater != null );
                    }
                }
        );
        Observer<Boolean> subscriber = new Observer<Boolean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                loadNewData();
            }

            @Override
            public void onNext(Boolean b) {
                if (b) {
                    mAdpater.setmDatas(mConsulationRecordVOs);
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(mChatObserver);
    }



}
