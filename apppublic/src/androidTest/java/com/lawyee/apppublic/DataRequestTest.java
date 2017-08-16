package com.lawyee.apppublic;

import android.app.Application;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.CommonService;
import com.lawyee.apppublic.dal.UserService;
import com.lawyee.apppublic.ui.MainActivity;
import com.lawyee.apppublic.vo.UserVO;

import net.lawyee.mobilelib.utils.L;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

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
public class DataRequestTest extends Application {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void init(){

    }
    @Test
    public void testOnBtnClick(){
        /*onView(withId(R.id.text_btn1))
                .perform(click()) ;
        Assert.assertTrue(mActivityRule.getActivity().getKey());*/
    }


    @Test
    public void requestTest()
    {
        final CountDownLatch signal = new CountDownLatch(1);//用于单元测试异步任务时使用，用简单的CountDownLatch信号对象来实现wait-notify机制
        UserService service = new UserService(mActivityRule.getActivity());
        service.userLogin("13579246810", "321456", UserVO.CSTR_USERROLE_PUBLIC, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                L.d("requestTest onComplete","content：" + content);
                signal.countDown();
            }

            @Override
            public void onError(String msg, String content) {
                L.d("requestTest onError","content：" + content +" msg:"+msg);
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
    public void requestDataDictoryTest()
    {
        final CountDownLatch signal = new CountDownLatch(1);//用于单元测试异步任务时使用，用简单的CountDownLatch信号对象来实现wait-notify机制
        CommonService service = new CommonService(mActivityRule.getActivity());
        service.getDataDictionary(new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                L.d("requestTest onComplete","content：" + content);
                signal.countDown();
            }

            @Override
            public void onError(String msg, String content) {
                L.d("requestTest onError","content：" + content +" msg:"+msg);
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
    public void testUserRegister()
    {
        final CountDownLatch signal = new CountDownLatch(1);//用于单元测试异步任务时使用，用简单的CountDownLatch信号对象来实现wait-notify机制

        UserService service = new UserService(mActivityRule.getActivity());
        service.userRegister("13579246810", "000000", "0000", new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                L.d("testUserRegister onComplete","content：" + content);
                signal.countDown();
            }

            @Override
            public void onError(String msg, String content) {
                L.d("testUserRegister onError","content：" + content +" msg:"+msg);
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
    public void testUserLogin()
    {
        final CountDownLatch signal = new CountDownLatch(1);//用于单元测试异步任务时使用，用简单的CountDownLatch信号对象来实现wait-notify机制

        UserService service = new UserService(mActivityRule.getActivity());
        service.userLogin("13579246810", "000000",UserVO.CSTR_USERROLE_PUBLIC, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                L.d("testUserLogin onComplete","content：" + content);
                signal.countDown();
            }

            @Override
            public void onError(String msg, String content) {
                L.d("testUserLogin onError","content：" + content +" msg:"+msg);
                signal.countDown();

            }
        });
        try {
            signal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void destroy(){
    }
}
