package com.lawyee.apppublic.config;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.baidu.mapapi.SDKInitializer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.smack.SmackListenerManager;
import com.lawyee.apppublic.smack.SmackManager;
import com.lawyee.apppublic.util.db.IMDBHelper;
import com.lawyee.apppublic.vo.AreaVO;
import com.lawyee.apppublic.vo.DataDictionaryVO;
import com.lawyee.apppublic.vo.LoginResult;
import com.lawyee.apppublic.vo.UserVO;
import com.lqr.emoji.IImageLoader;
import com.lqr.emoji.LQREmotionKit;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.utils.L;

import net.lawyee.mobilelib.app.AppContext;
import net.lawyee.mobilelib.json.JsonParser;
import net.lawyee.mobilelib.utils.FileUtil;
import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.vo.ResponseVO;

import org.jivesoftware.smack.roster.RosterEntry;
import org.reactivestreams.Publisher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.nostra13.universalimageloader.core.ImageLoader.TAG;

/**
 * 全局变量
 * Created by wuzhu on 2016/3/1.
 */
public class ApplicationSet extends Application {
    /**
     * 公众用户登录信息
     */
    private UserVO mUserVO;

    /**
     * 数据字典
     */
    private List<DataDictionaryVO> mDataDictionarys;

    /**
     * 区域数据
     */
    private List<AreaVO> mAreas;

    private List<Activity> mActivities = new ArrayList<Activity>();

    private static ApplicationSet mApplication;

    private SmackManager mSmackManager = SmackManager.getInstance();

    public synchronized static ApplicationSet getInstance() {
        return mApplication;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
        mApplication = this;
        mMainThread = Thread.currentThread();
        mMainThreadId = android.os.Process.myTid();
        mMainLooper = getMainLooper();
        mHandler = new Handler();
        initImageLoader(this);
        LQREmotionKit.init(this, new IImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
            }
        });
    }

    public String getOpenfireLoginId()
    {
        if(mUserVO==null)
            return "";
        return mUserVO.getOpenfireLoginId();
    }


    static {
        setGlobalParams();
    }

    static void setGlobalParams()
    {
        JsonParser.PACKAGE_NAME = "com.lawyee.apppublic.vo.";
        Constants.DataFileDir = "apppublic";
    }


    public  static DisplayImageOptions  CDIO_LAW = new DisplayImageOptions.Builder()//
            .showImageOnLoading(R.drawable.ic_default_avatar)         //设置图片在下载期间显示的图片
            .showImageForEmptyUri(R.drawable.ic_default_avatar)       //设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.drawable.ic_default_avatar)            //设置图片加载/解码过程中错误时候显示的图片
            .cacheInMemory(true)                                //设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)                                  //设置下载的图片是否缓存在SD卡中
            .displayer(new RoundedBitmapDisplayer(360))//是否设置为圆角，弧度为多少
            .build();                                           //构建完成

    public static DisplayImageOptions CDIO_DEFAULT = new DisplayImageOptions.Builder()//
            .showImageOnLoading(R.mipmap.icon_image_def_min)         //设置图片在下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.icon_image_def_min)       //设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.icon_image_def_min)            //设置图片加载/解码过程中错误时候显示的图片
            .cacheInMemory(true)                                //设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)                                  //设置下载的图片是否缓存在SD卡中
            .build();                                           //构建完成

    /**
     * ImageLoader 图片组件初始化
     *
     * @param context
     */
    public static void initImageLoader(Context context) {
        String caseDir = net.lawyee.mobilelib.Constants.getDataStoreDir(context) + net.lawyee.mobilelib.Constants.CSTR_IMAGECACHEDIR;
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                //.memoryCacheSize(2 * 1024 * 1024)
                .diskCache(new UnlimitedDiskCache(new File(caseDir)))//自定义缓存路径
                .defaultDisplayImageOptions(CDIO_DEFAULT)
                //.writeDebugLogs()
                .build();

        ImageLoader.getInstance().init(config);
    }
    //以下属性应用于整个应用程序，合理利用资源，减少资源浪费
    private static Thread mMainThread;//主线程
    private static long mMainThreadId;//主线程id
    private static Looper mMainLooper;//循环队列
    private static Handler mHandler;//主线程Handler


    public static Thread getMainThread() {
        return mMainThread;
    }

    public static void setMainThread(Thread mMainThread) {
        ApplicationSet.mMainThread = mMainThread;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static void setMainThreadId(long mMainThreadId) {
        ApplicationSet.mMainThreadId = mMainThreadId;
    }

    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    public static void setMainLooper(Looper mMainLooper) {
        ApplicationSet.mMainLooper = mMainLooper;
    }

    public static Handler getMainHandler() {
        return mHandler;
    }

    public static void setMainHandler(Handler mHandler) {
        ApplicationSet.mHandler = mHandler;
    }
    /**
     * 设置公众用户登录信息
     * @param userVO 如果注销，请传入null清除数据
     * @param save 是否保存
     */
    public void setUserVO(UserVO userVO, Boolean save) {
        if(save)
            UserVO.saveVO(userVO==null?new UserVO():userVO,
                    UserVO.dataFileName(getApplicationContext()));

        if((mUserVO==null||StringUtil.isEmpty(mUserVO.getLoginId()))&&
                (userVO==null||StringUtil.isEmpty(userVO.getLoginId())))//原来用户为空且新用户也为空，则不处理
            return;
        if((mUserVO==null||StringUtil.isEmpty(mUserVO.getLoginId())))//原来用户为空时
        {
            //新用户一定不为空.连接openfire，并登录
           loginOpenfireServer(false,userVO);
        }else//原来用户不为空时
        {
            if((userVO==null||StringUtil.isEmpty(userVO.getLoginId()))) {
                //新用户为空，注销openfire
                if(mSmackManager.isConnected())
                    mSmackManager.disconnect();
            }else if(mUserVO.getLoginId().equals(userVO.getLoginId()))
            {
                //新用户和旧用户id一样,不处理
            }else {
                //新用户和旧用户不一样 ,注销openfire后登录新用户
                loginOpenfireServer(true,userVO);
            }
        }
        this.mUserVO = userVO;
    }

    public UserVO getUserVO() {
        return mUserVO;
    }

    /**
     *
     * @param reconnect 是否重新连接
     * @return
     */
    public void loginOpenfireServer(boolean reconnect,UserVO userVO)
    {
        if(reconnect&&mSmackManager.isConnected()) {
            SmackListenerManager.getInstance().destroy();
            mSmackManager.disconnect();
        }
        if(userVO==null||StringUtil.isEmpty(userVO.getLoginId()))
            return;
        Flowable flowable = Flowable.just(userVO)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<UserVO, Publisher<LoginResult>>() {
                    @Override
                    public Publisher<LoginResult> apply(@NonNull UserVO userVO) throws Exception {
                        if(!SmackManager.getInstance().isConnected())
                            SmackManager.getInstance().initConnect();
                        LoginResult result = SmackManager.getInstance().login(userVO);
                        if(result.isSuccess())
                        {
                            Set<RosterEntry> res= SmackManager.getInstance().getAllFriends();
                            //因为getAllFriends可能需要较长时间，如果用户在这期间退出登录再登录其他帐号，可能导致异常
                            UserVO userVO1 =  getUserVO();
                            if(userVO1==null||!userVO1.getLoginId().equals(userVO.getLoginId()))
                            {
                                return Flowable.just(new LoginResult(false,"用户变更"));
                            }
                            IMDBHelper.getInstance().deleteAllRosterEntryFromDB();
                            IMDBHelper.getInstance().addAllRosterToDB(res);

                            IMDBHelper.getInstance().offlieMessageProcess(SmackManager.getInstance().getConnection());
                        }
                        return Flowable.just(result);
                    }
                });
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<LoginResult>() {
                    @Override
                    public void accept(@NonNull LoginResult loginResult) throws Exception {
                        if(loginResult.isSuccess())
                        {
                            //普通消息接收监听
                            SmackListenerManager.getInstance().addGlobalListener();
                        }
                        L.d(TAG+"-LoginResult:"+loginResult.isSuccess()+" "+loginResult.getErrorMsg());
                    }
                });
    }

    /**
     * 获取数据字典
     * @param autoinit 是否自动初始化
     * @return
     */
    public List<DataDictionaryVO> getDataDictionarys(boolean autoinit) {
        if(autoinit)
            return getDataDictionarys();
        return mDataDictionarys;
    }

    /**
     * 获取数据字典
     * @return 数据为空时读取默认数据
     */
    public List<DataDictionaryVO> getDataDictionarys() {
        if(mDataDictionarys==null)
            initDataDictionary();
        return mDataDictionarys;
    }

    public void setDataDictionarys(List<DataDictionaryVO> dataDictionarys) {
        this.mDataDictionarys = dataDictionarys;
    }

    /**
     * 获取区域信息
     * @param autoinit 是否自动初始化
     * @return
     */
    public List<AreaVO> getAreas(boolean autoinit) {
        if(autoinit)
            return getAreas();
        return mAreas;
    }
    /**
     * 获取区域信息
     * @return 数据为空时读取默认数据
     */
    public List<AreaVO> getAreas() {
        if(mAreas==null)
            initAreas();
        return mAreas;
    }

    public void setAreas(List<AreaVO> areas) {
        this.mAreas = areas;
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        if (mActivities != null && mActivities.size() > 0) {
            if(!mActivities.contains(activity)){
                mActivities.add(activity);
            }
        }else{
            mActivities.add(activity);
        }

    }

    public void removeActivity(Activity activity) {
        if (mActivities != null && mActivities.size() > 0) {
            if(mActivities.contains(activity)){
                mActivities.remove(activity);
            }
        }
    }

    public boolean checkActivityIsForeground(Class c)
    {
        if (c==null||mActivities == null || mActivities.isEmpty())
            return false;
        Activity foreground = mActivities.get(mActivities.size()-1);
        return c.getName().equals(foreground.getClass().getName());
    }

    public Activity getForegroundActivity()
    {
        if(mActivities == null || mActivities.isEmpty())
            return null;
        return mActivities.get(mActivities.size()-1);
    }

    // 遍历所有Activity并finish
    public void finishAllActivity() {
        if (mActivities != null && mActivities.size() > 0) {
            for (Activity activity : mActivities) {
                activity.finish();
            }
        }
    }


    /**
     * 缺省方式初始化区域表
     */
    public static void initAreas()
    {
        //本地读取区域信息
        String areas = FileUtil.readFileFromAssetsFile(AppContext.context(),"Areas.json");
        List<AreaVO> areaVOs = null;
        if(!StringUtil.isEmpty(areas)) {
            areaVOs = (List<AreaVO>) JsonParser.parseJsonToList(areas,new ResponseVO());
        }
        if(areaVOs==null)
            areaVOs = new ArrayList<AreaVO>();
        ApplicationSet.getInstance().setAreas(areaVOs);
    }

    /**
     * 缺省方式初始化数据字典
     */
    public static void initDataDictionary()
    {
        //先读取缓存
        List dataDictionaryVOs = DataDictionaryVO.loadVOList(DataDictionaryVO.dataListFileName(AppContext.context()));
        if(dataDictionaryVOs==null||dataDictionaryVOs.isEmpty())
        {
            //缓存为空，就读取默认的
            String dataDictionarys = FileUtil.readFileFromAssetsFile(AppContext.context(), "DataDictionary.json");
            if (!StringUtil.isEmpty(dataDictionarys)) {
                dataDictionaryVOs = JsonParser.parseJsonToList(dataDictionarys, new ResponseVO());
            }
        }
        if(dataDictionaryVOs==null)
            dataDictionaryVOs = new ArrayList();
        ApplicationSet.getInstance().setDataDictionarys(dataDictionaryVOs);
    }
}
