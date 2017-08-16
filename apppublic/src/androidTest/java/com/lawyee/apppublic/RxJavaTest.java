package com.lawyee.apppublic;

import android.app.Application;
import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Toast;

import com.lawyee.apppublic.smack.SmackManager;
import com.lawyee.apppublic.ui.MainActivity;
import com.lawyee.apppublic.vo.LoginResult;
import com.lawyee.apppublic.vo.UserVO;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 数据请求测试
 * @Package com.lawyee.apppublic
 * @Description:
 * @author:wuzhu
 * @date: 2017/5/24
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@RunWith(AndroidJUnit4.class)
public class RxJavaTest extends Application {
    private static final String TAG = RxJavaTest.class.getSimpleName();
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void init(){

    }

    @Test
    public void Test7()
    {
        final CountDownLatch signal = new CountDownLatch(1);//用于单元测试异步任务时使用，用简单的CountDownLatch信号对象来实现wait-notify机制

        UserVO uservo  = (UserVO) UserVO.loadVO(UserVO.dataFileName(mActivityRule.getActivity()));
        Flowable.just(uservo)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<UserVO, Publisher<LoginResult>>() {
                    @Override
                    public Publisher<LoginResult> apply(@NonNull UserVO userVO) throws Exception {
                        if(!SmackManager.getInstance().isConnected())
                            SmackManager.getInstance().initConnect();
                        LoginResult result = SmackManager.getInstance().login(userVO);
                        return Flowable.just(result);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginResult>() {
                    @Override
                    public void accept(@NonNull LoginResult loginResult) throws Exception {
                        System.out.println(TAG+"-LoginResult:"+loginResult.isSuccess()+" "+loginResult.getErrorMsg());
                        Assert.assertTrue(loginResult.isSuccess());

                        signal.countDown();
                    }
                });
        try {
            signal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test6()
    {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("将会在3秒后显示");
                SystemClock.sleep(3000);
                e.onNext("ittianyu");
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s);
                        Toast.makeText(mActivityRule.getActivity(), s, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Test
    public void Test5()
    {
        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(1);
        list.add(5);

        //和 map 不同之处在于 flatMap 返回的是一个 Flowable 对象。这正是我们想要的，我们可以把从List发射出来的一个一个的元素发射出去。
        Flowable.just(list)
                .flatMap(new Function<List<Integer>, Publisher<Integer>>() {
                    @Override
                    public Publisher<Integer> apply(List<Integer> integers) throws Exception {
                        return Flowable.fromIterable(integers);
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println(integer);
                    }
                });
    }

    @Test
    public void Test4()
    {
        //map 操作符更神奇的地方是，你可以返回任意类型的 Flowable，也就是说你可以使用 map 操作符发射一个新的数据类型的 Flowable 对象。
        Flowable.just("map1")
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return s.hashCode();
                    }
                })
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return integer.toString();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });
    }

    @Test
    public void Test3()
    {
        //map的作用就变换 Flowable 然后返回一个指定类型的 Flowable 对象。
        Flowable.just("map")
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return s + " -ittianyu";
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });
    }

    @Test
    public void Test2()
    {
        Flowable.just("hello RxJava 2")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });
    }

    @Test
    public void Test1()
    {
        // create
        Subscriber subscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("onSubscribe");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };

        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("hello RxJava 2");
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER);
        flowable.subscribe(subscriber);
    }

    @After
    public void destroy(){
    }
}
