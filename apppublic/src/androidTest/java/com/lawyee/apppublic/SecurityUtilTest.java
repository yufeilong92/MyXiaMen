package com.lawyee.apppublic;

import android.support.test.runner.AndroidJUnit4;

import com.lawyee.apppublic.config.Constants;

import org.junit.Test;
import org.junit.runner.RunWith;

import net.lawyee.mobilelib.utils.L;
import net.lawyee.mobilelib.utils.SecurityUtil;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class SecurityUtilTest {
    @Test
    public void securityTest()
    {
        String mkey;
        String mivs;
        mkey = SecurityUtil.getLegalKey("2324klkdsfk存测试啊。leq32f砸坏3打法");
        mivs = SecurityUtil.getLegalKey("a;dkfa;dskjfadap2340123ldanad.daf=39140");
        L.d("test","key：" + mkey + " len:" + mkey.length());
        L.d("test","ivs："+mivs+ " len:"+mivs.length());

        String password="{'用户密码',特殊字符*\\}";
        String encryptstr = SecurityUtil.Encrypt(password,mkey,mivs);
        L.d("test","encryptstr：" + encryptstr);//1+XbvoVToIN0NpG4B6BwpYmMP7nCaVjU5k5hXqJ15ps=
        String descryptstr = SecurityUtil.Decrypt(encryptstr,mkey,mivs);
        L.d("test","descryptstr：" + descryptstr);
        assertEquals(password,descryptstr);
    }

    @Test
    public void securityTest2()
    {
        String mkey;
        String mivs;
        mkey = SecurityUtil.getLegalKey("20170604145916");
        mivs = SecurityUtil.getLegalKey(Constants.CSTR_IVS);
        L.d("test","key：" + mkey + " len:" + mkey.length());
        L.d("test","ivs："+mivs+ " len:"+mivs.length());

        String password="222222";
        String encryptstr = SecurityUtil.Encrypt(password,mkey,mivs);
        L.d("test","encryptstr：" + encryptstr);//1+XbvoVToIN0NpG4B6BwpYmMP7nCaVjU5k5hXqJ15ps=
        String descryptstr = SecurityUtil.Decrypt(encryptstr,mkey,mivs);
        L.d("test","descryptstr：" + descryptstr);
        assertEquals(password,descryptstr);
    }

}
