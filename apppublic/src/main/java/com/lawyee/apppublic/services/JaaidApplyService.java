package com.lawyee.apppublic.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JaaidService;
import com.lawyee.apppublic.ui.WelcomeActivity;
import com.lawyee.apppublic.util.UrlUtil;
import com.lawyee.apppublic.vo.AttachmentVO;
import com.lawyee.apppublic.vo.JaaidApplyDetailVO;
import com.lawyee.apppublic.vo.UserVO;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadStatusDelegate;
import net.lawyee.mobilelib.json.JsonParser;
import net.lawyee.mobilelib.utils.FileUtil;
import net.lawyee.mobilelib.utils.ImgCompressor;
import net.lawyee.mobilelib.utils.L;
import net.lawyee.mobilelib.utils.SecurityUtil;
import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.TimeUtil;
import net.lawyee.mobilelib.vo.ResponseVO;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 标题
 * @Package com.lawyee.apppublic.services
 * @Description:
 * @author:wuzhu
 * @date: 2017/6/20
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JaaidApplyService extends Service {
    private static final  String TAG = JaaidApplyService.class.getSimpleName();
    /**
     * 传入参数
     */
    public static final String CSTR_EXTRA_JAAIDAPPLYDETAIL_VO = "jaaidapplyvo";

    /**
     * 传入参数-提交结果，成功还是失败 -1，失败,0 处理中，1成功
     */
    public static final String CSTR_EXTRA_JAAIDAPPLYRESULT_INT = "jaaidapplyresult";
    public enum JAAIDAPPLYRESULT
    {
        error(-1),processing(0),sucess(1),other(2);

        JAAIDAPPLYRESULT(int i) {
            value = i;
        }
        private int value;
        public int getValue() {
            return value;
        }
    }

    /**
     * 传入参数-提交反馈消息
     */
    public static final String CSTR_EXTRA_JAAIDAPPLYMESSAGE_STR = "jaaidapplymessage";
    /**
     * 上传父类别名称--服务端提供
     */
    public static final String CSTR_JAAIDAPPLY_UPLOAD_PARENT = "法援预申请";

    private NotificationManager mNotificationManager;
    private Notification mNotification;
    private JaaidApplyDetailVO mApplyDetailVO;

    protected static int CINT_JAAIDAPPLYSERVICE_NOTIFICATION = 5;
    @Override
    public void onCreate() {
        super.onCreate();
        L.d(TAG,"法援预申请提交服务创建");
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        buildNotification(getString(R.string.law_aid_application),false,JAAIDAPPLYRESULT.other);
        //startForeground(CINT_JAAIDAPPLYSERVICE_NOTIFICATION, mNotification);//让Service变成前台Service,并在系统的状态栏显示出来
    }

    @Override
    public void onDestroy() {
        L.d(TAG,"法援预申请提交服务结束");
        super.onDestroy();
    }

    /**
     *
     * @param message 提醒消息
     * @param autocancel 是否可以取消
     * @param result 提交结果
     */
    private void buildNotification(String message,boolean autocancel,JAAIDAPPLYRESULT result)
    {
        NotificationCompat.Builder builder =
        new NotificationCompat.Builder(this)
                //.setLargeIcon(R.mipmap.ic_launcher)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(getString(R.string.app_name))
                .setContentText(message)
                .setTicker(message)
                .setAutoCancel(autocancel)
                .setDefaults(Notification.DEFAULT_ALL);
        if(result.equals(JAAIDAPPLYRESULT.error))//错误时才传递参数
        {
            Intent notificationIntent = new Intent(this,WelcomeActivity.class);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            notificationIntent.putExtra(CSTR_EXTRA_JAAIDAPPLYDETAIL_VO,mApplyDetailVO);
            notificationIntent.putExtra(CSTR_EXTRA_JAAIDAPPLYRESULT_INT,result.value);
            notificationIntent.putExtra(CSTR_EXTRA_JAAIDAPPLYMESSAGE_STR,message);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
        }
        mNotification = builder.build();
        mNotificationManager.notify(CINT_JAAIDAPPLYSERVICE_NOTIFICATION,mNotification);
        if(result==JAAIDAPPLYRESULT.error||result==JAAIDAPPLYRESULT.sucess)//错误时或成功时就退出服务
            stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        L.d(TAG,"法援预申请提交服务开始");
        Object o = intent.getSerializableExtra(CSTR_EXTRA_JAAIDAPPLYDETAIL_VO);
        if(o instanceof JaaidApplyDetailVO)
        {
            mApplyDetailVO = (JaaidApplyDetailVO) o;
        }
        if(mApplyDetailVO==null) {
            buildNotification("无效的资讯提交信息",true,JAAIDAPPLYRESULT.other);
            this.stopSelf();
        }else {
            startApply();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 开始提交预申请信息，过程如下：先检查压缩图片 再上传资料，上传成功后再提交预申请信息
     */
    private void startApply()
    {
        //test
        /*if(1==1) {
            buildNotification("检查上传的材料-测试", false, JAAIDAPPLYRESULT.error);
            return;
        }*/
        if(mApplyDetailVO.getAttachments()==null||mApplyDetailVO.getAttachments().isEmpty())
        {
            postApply();
            return;
        }
        ImgCompressor imgCompressor = ImgCompressor.getInstance(getApplicationContext());
        imgCompressor.setOutPutDir(Constants.getDataStoreDir(getApplicationContext())
                + net.lawyee.mobilelib.Constants.CSTR_IMAGECACHEDIR);
        buildNotification("检查上传的材料",false,JAAIDAPPLYRESULT.processing);
        compressUploadImage(imgCompressor,0);
    }

    /**
     * 压缩处理上传图片
     * @param imgCompressor
     * @param index
     */
    private void compressUploadImage(final ImgCompressor imgCompressor , final int index)
    {
        if(index>=mApplyDetailVO.getAttachments().size()) {
            startUpload(0);
            return;
        }
        AttachmentVO avo = mApplyDetailVO.getAttachments().get(index);
        if(!StringUtil.isEmpty(avo.getOid()))//已经上传成功过的不重新压缩
        {
            compressUploadImage(imgCompressor,index+1);
            return;
        }
        imgCompressor.withListener(new ImgCompressor.CompressListener() {
            @Override
            public void onCompressStart() {

            }

            @Override
            public void onCompressEnd(ImgCompressor.CompressResult imageOutPath) {
                if(!StringUtil.isEmpty(imageOutPath.getOutPath()))
                    mApplyDetailVO.getAttachments().get(index).setLocfilepath(imageOutPath.getOutPath());
                compressUploadImage(imgCompressor,index+1);
            }
        }).starCompressWithDefault(avo.getLocfilepath());
    }

    /**
     * 开始上传图片
     */
    private void startUpload(final int index)
    {
        if(index>=mApplyDetailVO.getAttachments().size())
        {
            postApply();
            return;
        }
        final AttachmentVO avo = mApplyDetailVO.getAttachments().get(index);
        if(!StringUtil.isEmpty(avo.getOid()))////已经上传成功过的不重新上传
        {
            startUpload(index+1);
            return;
        }
        buildNotification("开始上传材料:"+avo.getDescription_(),false,JAAIDAPPLYRESULT.processing);
        MultipartUploadRequest req = null;
        String errormessage="";
        try {

            req = new MultipartUploadRequest(this, mApplyDetailVO.getOid(), UrlUtil.getUploadFileUrl(getApplicationContext()))
                    .setUsesFixedLengthStreamingMode(true)
                    .setMaxRetries(1)
                    .setUtf8Charset()
                    .setAutoDeleteFilesAfterSuccessfulUpload(true);
            //添加所需要的header
            /**
             * timespan：时间戳，yyyyMMddHHmmss
             loginId:登录帐号
             password:密码（加密后的，key为时间戳补0，iv使用预定义的）
             parent:父类别，如法律援助、人民调解
             sub:子类别
             description_：文件描述
             pid:业务id
             */
            String timespan = TimeUtil.dateToString(null,"yyyyMMddHHmmss");
            UserVO uservo = ApplicationSet.getInstance().getUserVO();
            String pwd =  SecurityUtil.Encrypt(uservo.getPassword(),SecurityUtil.getLegalKey(timespan), Constants.CSTR_IVS);
            req.addHeader("timespan",timespan);
            req.addHeader("loginId",uservo.getLoginId());
            req.addHeader("role",uservo.getRole());
            req.addHeader("password", URLEncoder.encode(pwd,Constants.CSTR_PAGECODE_DEFAULT));
            req.addHeader("parent",URLEncoder.encode(CSTR_JAAIDAPPLY_UPLOAD_PARENT,Constants.CSTR_PAGECODE_DEFAULT));
            req.addHeader("pid",mApplyDetailVO.getOid());

            //添加上传的文件
            req.addHeader("sub",avo.getSub());
            req.addHeader("description_",URLEncoder.encode(avo.getDescription_(),Constants.CSTR_PAGECODE_DEFAULT));
            req.addFileToUpload(avo.getLocfilepath(), Constants.CSTR_UPLOADFILE_PAREAMNAME, FileUtil.getFileName(avo.getLocfilepath()));
            req.setDelegate(new UploadStatusDelegate() {
                @Override
                public void onProgress(Context context, UploadInfo uploadInfo) {
                    //取消提醒，不然提醒太多
                    /*buildNotification("提交预申请材料-"+avo.getDescription_()+"，上传完成"+uploadInfo.getProgressPercent()+"%",
                            false,JAAIDAPPLYRESULT.processing);*/
                }

                @Override
                public void onError(Context context, UploadInfo uploadInfo, Exception exception) {
                    buildNotification("提交预申请失败：上传材料-"+avo.getDescription_()+"失败",true,JAAIDAPPLYRESULT.error);
                }

                @Override
                public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                    AttachmentVO result = parserResult(serverResponse.getBodyAsString());
                    if(result==null)
                        return;
                    buildNotification("提交预申请材料-"+avo.getDescription_()+"完成", false,JAAIDAPPLYRESULT.processing);
                    mApplyDetailVO.getAttachments().get(index).setOid(result.getOid());
                    mApplyDetailVO.getAttachments().get(index).setName(result.getName());
                    startUpload(index+1);
                }

                @Override
                public void onCancelled(Context context, UploadInfo uploadInfo) {
                    buildNotification("提交预申请失败：取消上传材料",true,JAAIDAPPLYRESULT.error);
                }
            });
            req.startUpload();
        } catch (FileNotFoundException e) {
            errormessage = "上传文件不存在";
        }catch (IllegalArgumentException e)
        {
            errormessage = "缺少相关参数设置";
        }
        catch (MalformedURLException e) {
            errormessage = "上传地址无效";
        } catch (UnsupportedEncodingException e) {
            errormessage = "无效的Header参数";
        }
        if(!StringUtil.isEmpty(errormessage)) {
            buildNotification("提交预申请失败："+errormessage,true,JAAIDAPPLYRESULT.error);
        }
    }

    private AttachmentVO parserResult(String response)
    {
        L.d(TAG,response);
        ResponseVO rvo = new ResponseVO();
        Object o = JsonParser.parseJsonToEntity(response,rvo);
        if(!rvo.isSucess()||!(o instanceof AttachmentVO))
        {
            buildNotification("提交预申请失败：服务端返回无效上传文件信息-"+rvo.getMsg(),true,JAAIDAPPLYRESULT.error);
            return null;
        }
        return (AttachmentVO)o;
    }

    /**
     * 提交预申请信息
     */
    private void postApply()
    {
        JaaidService jaaidService = new JaaidService(this);
        jaaidService.postApply(mApplyDetailVO, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                buildNotification(getString(R.string.submit_ok),true,JAAIDAPPLYRESULT.sucess);
            }

            @Override
            public void onError(String msg, String content) {
                buildNotification(msg,true,JAAIDAPPLYRESULT.error);
            }
        });
    }
}
