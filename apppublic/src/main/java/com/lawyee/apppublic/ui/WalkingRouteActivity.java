package com.lawyee.apppublic.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.utils.DistanceUtil;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.FunctionAdpater;
import com.lawyee.apppublic.util.WalkingRouteOverlay;
import com.lawyee.apppublic.widget.RecycleViewDivider;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.lawyee.apppublic.ui.WalkingRouteActivity.Method.BuXin;

/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  标题
 * @Package com.lawyee.apppublic.ui
 * @Description:    地图路线页
 * @author:lzh
 * @date:   2017/8/9
 * @version
 * @verdescript   2017/8/9  lzh 初建
 * @Copyright: 2017/8/9 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class WalkingRouteActivity extends BaseActivity {


    public final static String LATITUDE = "latitude";
    public final static String LONGITUDE = "longitude";
    public final static String ADDRESS = "address";
    public final static String SERCESCALL = "ServiceCalls";
    public final static String TITLENAME="titlename";
    MapView mMap;
    private PoiInfo mPoiInfo;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private MyLocationListenner myListener = new MyLocationListenner();
    //    private LatLng mLatLng;
    private LatLng mLatLng;
    private boolean isFirstLoc = true; // 是否首次定位
    private ProgressDialog progressDialog;
    private Method mMethod = BuXin;
    private WalkingRouteOverlay mWalkingRouteOverlay;
    private TextView mTvLawIrmsAddress;
    private TextView mTvLawServiceCalls;
    private LinearLayout mLlPhone;
    private ImageView mBtnMore;
    private MaterialDialog mPopWindowsShow;
    private Context mContext;
    public enum Method {
        BuXin, GongJiao, ZiJia
    }
    private double mLatitude;
    private double mLongitude;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_walkingroute);
        mContext=this;
        initView();
        boolean b = initIntent();
        if (!b) {
            Toast.makeText(this, "数据为空", Toast.LENGTH_SHORT).show();
            return;
        }
        checkLocal();


    }

    private void initView() {
         mMap= (MapView) findViewById(R.id.map);
        mTvLawIrmsAddress = (TextView) findViewById(R.id.tv_law_irms_address);
        mTvLawServiceCalls = (TextView) findViewById(R.id.tv_law_service_calls);
        mLlPhone= (LinearLayout) findViewById(R.id.ll_phone);
        mBtnMore= (ImageView) findViewById(R.id.btn_more);
        mBtnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> mData=new ArrayList<>();
                mData.add("显示路线");
                mData.add("百度地图导航");
                mData.add("取消");
                handlerPopWindos(mData);
            }
        });
    }
    /**
     * @param mData  数据
     */
    private void handlerPopWindos(final List<String> mData) {
        final FunctionAdpater applyPopAdapter = new FunctionAdpater(mData, this);
        final GridLayoutManager manager = new GridLayoutManager(this, 1);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        if (mPopWindowsShow == null || !mPopWindowsShow.isShowing()) {
            mPopWindowsShow = new MaterialDialog.Builder(this)
                    .adapter(applyPopAdapter, manager)
                    .backgroundColorRes(R.color.activity_content_bg)
                    .show();
            mPopWindowsShow.getRecyclerView().addItemDecoration(new RecycleViewDivider(this, GridLayoutManager.VERTICAL, R.drawable.bg_rlv_diving));
        }
        applyPopAdapter.setOnRecyclerItemClickListener(new FunctionAdpater.OnRecyclerItemClickListener() {
            @Override
            public void OnItemClickListener(View view, String itemVo, int position) {
                if(position==2){
                    mPopWindowsShow.dismiss();
                    return;
                }
                if (position==0){
                    mPopWindowsShow.dismiss();
                    showMapWithLocationClient();
                    initBaiDuMap();
                }else {
                    Intent intent;
                    if (isAvilible(mContext, "com.baidu.BaiduMap")) {//传入指定应用包名

                        try {
//                          intent = Intent.getIntent("intent://map/direction?origin=latlng:34.264642646862,108.95108518068|name:我家&destination=大雁塔&mode=driving®ion=西安&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                            intent = Intent.getIntent("intent://map/direction?" +
                                    //"origin=latlng:"+"34.264642646862,108.95108518068&" +   //起点  此处不传值默认选择当前位置
                                    "destination=latlng:" + mLatitude + "," + mLongitude + "|name:我的目的地" +        //终点
                                    "&mode=walking&" +          //导航路线方式
                                    "&src=司法服务#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                            mContext.startActivity(intent); //启动调用
                        } catch (URISyntaxException e) {
                            Log.e("intent", e.getMessage());
                        }
                    } else {//未安装
                        //market为路径，id为包名
                        String map_address = "http://api.map.baidu.com/direction?origin=latlng:" +
                                mLatLng.latitude + "," + mLatLng.longitude + "|name:&destination=latlng:" +
                                mLatitude + "," + mLongitude + "|name:&mode=walking&region=" +
                                "中国&output=html&src=意法|司法";
                        intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(map_address);
                        intent.setData(content_url);
                        startActivity(intent);
                    }
                }
            }
        });
    }
    /**
     * 显示当前的位置信息
     */
    private void showMapWithLocationClient() {
        String str1 = "正在刷新";
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(str1);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            public void onCancel(DialogInterface arg0) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                finish();
            }
        });

        progressDialog.show();
    }
    private void initBaiDuMap() {
//        mBaiduMap = mMap.getMap();
//        // 开启定位图层
//        mBaiduMap.setMyLocationEnabled(true);
//
//        // 定位初始化
//        mLocationClient = new LocationClient(this);
//        mLocationClient.registerLocationListener(myListener);
//
//        // 设置地图缩放级别为15
//        mBaiduMap.setMapStatus(MapStatusUpdateFactory
//                .newMapStatus(new MapStatus.Builder().zoom(15).build()));
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setScanSpan(1000);//扫描间隔 单位毫秒
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        mLocationClient.setLocOption(option);
        //设置箭头
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL, true, null));
        mLocationClient.start();

    }

    private boolean initIntent() {
        Intent intent = getIntent();
        mLatitude = intent.getDoubleExtra(LATITUDE, 0);
        mLongitude = intent.getDoubleExtra(LONGITUDE, 0);
        String address =intent.getStringExtra(ADDRESS);
        String phone =intent.getStringExtra(SERCESCALL);
        if(phone==null||phone.equals("")){
            mLlPhone.setVisibility(View.GONE);
        }else{
            mTvLawServiceCalls.setText(phone);
        }
        mTvLawIrmsAddress.setText(address);


        if (mLatitude == 0 || mLongitude == 0) {
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMap.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMap.onPause();
    }

    /**
     * 查看别人发过来，或者已经发送出去的位置信息
     *
     * @param latitude   维度
     * @param longtitude 经度
     *
     */
    private void showMap(double latitude, double longtitude) {

        mBaiduMap = mMap.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        // 定位初始化
        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(myListener);

        // 设置地图缩放级别为15
        mBaiduMap.setMapStatus(MapStatusUpdateFactory
                .newMapStatus(new MapStatus.Builder().zoom(15).build()));

        LatLng llA = new LatLng(latitude, longtitude);
        OverlayOptions ooA = new MarkerOptions().position(llA).icon(BitmapDescriptorFactory
                .fromResource(R.drawable.icon_yourself_lication))
                .zIndex(4).draggable(true);
        mBaiduMap.addOverlay(ooA);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(llA, 17.0f);
        mBaiduMap.animateMapStatus(u);
    }

    private void cleanView() {
        mBaiduMap.clear();
    }

    private void initBuXin() {
        RoutePlanSearch planSearch = RoutePlanSearch.newInstance();
        planSearch.setOnGetRoutePlanResultListener(new MyOnGetRoutePlanResultListener());

        WalkingRoutePlanOption option = new WalkingRoutePlanOption();
        PlanNode from = PlanNode.withLocation(mLatLng);
        LatLng latLng=new LatLng(mLatitude,mLongitude);

        PlanNode to = PlanNode.withLocation(latLng);

        option.from(from);// 设置起点
        option.to(to);// 设置终点
        planSearch.walkingSearch(option);

        double distance = DistanceUtil.getDistance(mLatLng, latLng);
        Log.e("tag", "距离:" + distance+"米");
    }



    class MyOnGetRoutePlanResultListener implements OnGetRoutePlanResultListener {
        /**
         * 步行结果
         *
         * @param
         */
        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult result) {
            if (result == null
                    || SearchResult.ERRORNO.RESULT_NOT_FOUND == result.error) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                Toast.makeText(getApplicationContext(), "未查询到结果", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mMethod == BuXin) {

                mWalkingRouteOverlay = new MyWalkingRouteOverlay(mBaiduMap);
                mBaiduMap.setOnMarkerClickListener(mWalkingRouteOverlay);
                WalkingRouteLine line = result.getRouteLines().get(0);
                mWalkingRouteOverlay.setData(line);// 设置数据
                mWalkingRouteOverlay.addToMap();// 添加到地图上
                mWalkingRouteOverlay.zoomToSpan();// 自动缩放级别
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
//

            }
        }

        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

        }

        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

        }

        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

        }


        /**
         * 室内路线规划回调
         *
         * @param indoorRouteResult
         */
        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

        }


    }

    class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {


            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
                      mLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            if (isFirstLoc) {
                isFirstLoc = false;
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(mLatLng).zoom(16.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                cleanView();
               initBuXin();
            }

        }
    }

    class MyWalkingRouteOverlay extends WalkingRouteOverlay {

        public MyWalkingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
        }
    }
/* 检查手机上是否安装了指定的软件
     * @param context
     * @param packageName：应用包名
     * @return
             */
    public static boolean isAvilible(Context context, String packageName){
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if(packageInfos != null){
            for(int i = 0; i < packageInfos.size(); i++){
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 检查是否有访问定位的权限
     */
    public void checkLocal() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        performCodeWithPermission(getString(R.string.rationale_location), RC_LOCATION_PERM, perms, new PermissionCallback() {
            @Override
            public void hasPermission(List<String> allPerms) {
//                mIntent = new Intent(mContext, SessionMapActivity.class);
//                startActivityForResult(mIntent, MAP_PICKER);
                Log.e("czq","有权限。。。。。。。。。。");
                showMap(mLatitude,mLongitude);

            }

            @Override
            public void noPermission(List<String> deniedPerms, List<String> grantedPerms, Boolean hasPermanentlyDenied) {
                if (hasPermanentlyDenied) {
                    alertAppSetPermission(getString(R.string.rationale_ask_again), RC_SETTINGS_SCREEN);
                }
            }
        });
    }
}
