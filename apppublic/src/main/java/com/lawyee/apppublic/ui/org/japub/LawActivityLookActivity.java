package com.lawyee.apppublic.ui.org.japub;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.LawLookActivityPictureAdapter;
import com.lawyee.apppublic.adapter.LawOrganizerAdapter;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JapubService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.ui.WalkingRouteActivity;
import com.lawyee.apppublic.util.ImageLoaderManager;
import com.lawyee.apppublic.util.ShowOrHide;
import com.lawyee.apppublic.util.TextViewUtil;
import com.lawyee.apppublic.util.UrlUtil;
import com.lawyee.apppublic.vo.ActivityDetailVO;
import com.lawyee.apppublic.vo.AttachmentVO;
import com.lawyee.apppublic.vo.BaseCommonDataVO;
import com.lawyee.apppublic.vo.JapubHandleVO;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.lawyee.mobilelib.utils.T;
import net.lawyee.mobilelib.utils.TimeUtil;

import java.util.ArrayList;

import static com.lawyee.apppublic.R.id.iv_lawLook_activitymap;
import static com.lawyee.apppublic.R.id.iv_lawLook_map;
import static com.lawyee.apppublic.R.id.iv_lawLook_organizer;
import static com.lawyee.apppublic.R.id.liear_lawLook_Picture;
import static com.lawyee.apppublic.R.id.rl_lawlook_pictureLeft;
import static com.lawyee.apppublic.R.id.rlv_law_organizer;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawActivityLookActivity.java
 * @Package com.lawyee.apppublic.ui.org
 * @Description: 法治宣传活动查看页
 * @author: YFL
 * @date: 2017/7/25 11:22
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/7/25 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class LawActivityLookActivity extends BaseActivity implements View.OnClickListener {

    public static final String ACTIVITYLOOK = "ActivityLook";
    private GridLayoutManager mPictureManager;
    private ImageView mIvLawLookShowTitile;
    private RelativeLayout mRlLawlookActivityIntrodution;
    private TextView mTvLawLookactivityName;
    private TextView mTvLawLookActivityDate;
    private TextView mTvLawLookActivityhere;
    private TextView mTvLawLookActivityundertaker;
    private TextView mTvLawLookActivityOrganizer;
    private ImageView mIvLawLookInfom;
    private RelativeLayout mRlLawlookActivityInfom;
    private RelativeLayout mRlLawlookActivityInfomContent;
    private ImageView mIvLawLookProfile;
    private RelativeLayout mRlLawlookActivityProfile;
    private RecyclerView mRlvLawOrganizer;
    private ImageView mIvLawLookOrganizer;
    private RelativeLayout mRlLawlookActivityOrganizer;
    private RecyclerView mRlvLawIntroduction;
    private ImageView mIvLawLookPicture;
    private RelativeLayout mRlLawlookActivityPicture;
    private ImageView mIvLawLookPictureLeft;
    private RelativeLayout mRlLawlookPictureLeft;
    private RecyclerView mRlvLawlookPicture;
    private ImageView mIvLawLookPictureRight;
    private RelativeLayout mRlLawLookPictureRight;
    private LinearLayout mLiearLawLookPicture;
    private ImageView mIvLawLookMap;
    private RelativeLayout mRlLawlookActivityMap;
    private ImageView mIvLawLookActivitymap;
    private LawLookActivityPictureAdapter mPictureAdapter;
    private int mMovePosition;
    private ActivityDetailVO mActivityDetailVO;
    private Context mContext;
    private String mOid;
    private TextView mTvInfomContent;
    private TextView mTvNull;
    private LinearLayout mLiearLawLookActivitymap;
    private String mAddress = "";

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_law_activity_look);
        initView();
        initData();
        loadData();
    }


    private void initData() {
        mRlLawlookActivityInfomContent.setVisibility(View.GONE);
        mRlvLawIntroduction.setVisibility(View.GONE);
        mRlvLawOrganizer.setVisibility(View.GONE);
        mLiearLawLookPicture.setVisibility(View.GONE);
        mLiearLawLookActivitymap.setVisibility(View.GONE);
    }

    /**
     * 设置承/协办方数据
     *
     * @param mData        承/协办法数据
     * @param recyclerView 展示view
     */
    private void setOrganizerAdapterDate(ArrayList mData, RecyclerView recyclerView) {
        LawOrganizerAdapter organizerAdapter = new LawOrganizerAdapter(mData, this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if (position > -1) {
                    outRect.top = 20;
                }
            }
        });
        recyclerView.setAdapter(organizerAdapter);
        organizerAdapter.setItemTitleCilickListener(new LawOrganizerAdapter.OnItemTitleCilickListener() {
            @Override
            public void OnTitleCilickListener(JapubHandleVO vo, int position) {
                if (!TextUtils.isEmpty(vo.getLink())){
                    Intent intent = new Intent(LawActivityLookActivity.this, ImageLookActivity.class);
                    intent.putExtra(ImageLookActivity.CONTENT_PARRMTER_TYPE,ImageLookActivity.CONTENT_PARRMTER_WEB);
                    intent.putExtra(ImageLookActivity.CONTNETPARAMETER_URL, vo.getLink());
//                    intent.putExtra(ImageLookActivity.CONTNETPARAMETER_URL,"https://www.baidu.com");
                    intent.putExtra(ImageLookActivity.CSTR_EXTRA_TITLE_STR,vo.getName());
                    startActivity(intent);
                }
            }
        });

    }

    /**
     * 活动图片
     */
    private void setPictureAdapterData() {
        if (mActivityDetailVO.getAttachments() != null) {
            mPictureAdapter = new LawLookActivityPictureAdapter(mActivityDetailVO.getAttachments(), this);
            mPictureManager = new GridLayoutManager(this, 1);
            mPictureManager.setOrientation(GridLayoutManager.HORIZONTAL);
            mRlvLawlookPicture.setLayoutManager(mPictureManager);
            mRlvLawlookPicture.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    int position = parent.getChildAdapterPosition(view);
                    if (position > -1) {
                        outRect.right = 5;
                    }
                }
            });
            mRlvLawlookPicture.setAdapter(mPictureAdapter);
            mPictureAdapter.setItemOnClickListener(new LawLookActivityPictureAdapter.OnRecyclerItemOnClickListener() {
                @Override
                public void OnItemClickListener(AttachmentVO vo, int position) {
                    Intent intent = new Intent(LawActivityLookActivity.this, ImageLookActivity.class);
                    intent.putExtra(ImageLookActivity.CONTNETPARAMETER_URL, vo.getOid());
                    intent.putExtra(ImageLookActivity.CONTENT_PARRMTER_TYPE,ImageLookActivity.CONTENT_PARRMTER_IMAGE);
                    intent.putExtra(ImageLookActivity.CSTR_EXTRA_TITLE_STR,"图片查看");
                    startActivity(intent);
                }
            });
        }
        mRlvLawlookPicture.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int firstItem = mPictureManager.findFirstVisibleItemPosition();
                int lastItem = mPictureManager.findLastVisibleItemPosition();
                if (firstItem <= 0) {
                    mIvLawLookPictureLeft.setImageResource(R.mipmap.icon_lawvote_shallowleft);
                    mIvLawLookPictureRight.setImageResource(R.mipmap.icon_lawvote_right);
                } else if (lastItem >= mPictureAdapter.getItemCount() - 1) {
                    mIvLawLookPictureLeft.setImageResource(R.mipmap.icon_lawvote_left);
                    mIvLawLookPictureRight.setImageResource(R.mipmap.icon_lawvote_shallowright);
                } else {
                    mIvLawLookPictureLeft.setImageResource(R.mipmap.icon_lawvote_left);
                    mIvLawLookPictureRight.setImageResource(R.mipmap.icon_lawvote_right);
                }


            }
        });
    }

    private void initView() {
        mContext = this;
        mTvInfomContent = (TextView) findViewById(R.id.tv_infomContent);
        mIvLawLookShowTitile = (ImageView) findViewById(R.id.iv_lawLook_showTitile);
        mIvLawLookShowTitile.setOnClickListener(this);
        mRlLawlookActivityIntrodution = (RelativeLayout) findViewById(R.id.rl_lawlook_activity_introdution);
        mRlLawlookActivityIntrodution.setOnClickListener(this);
        mTvLawLookactivityName = (TextView) findViewById(R.id.tv_lawLookactivityName);
        mTvLawLookactivityName.setOnClickListener(this);
        mTvLawLookActivityDate = (TextView) findViewById(R.id.tv_lawLook_activityDate);
        mTvLawLookActivityDate.setOnClickListener(this);
        mTvLawLookActivityhere = (TextView) findViewById(R.id.tv_lawLook_activityhere);
        mTvLawLookActivityhere.setOnClickListener(this);
        mTvLawLookActivityundertaker = (TextView) findViewById(R.id.tv_lawLook_activityundertaker);
        mTvLawLookActivityundertaker.setOnClickListener(this);
        mTvLawLookActivityOrganizer = (TextView) findViewById(R.id.tv_lawLook_activityOrganizer);
        mTvLawLookActivityOrganizer.setOnClickListener(this);
        mIvLawLookInfom = (ImageView) findViewById(R.id.iv_lawLook_infom);
        mIvLawLookInfom.setOnClickListener(this);
        mRlLawlookActivityInfom = (RelativeLayout) findViewById(R.id.rl_lawlook_activity_infom);
        mRlLawlookActivityInfom.setOnClickListener(this);
        mRlLawlookActivityInfomContent = (RelativeLayout) findViewById(R.id.rl_lawlook_activity_infomContent);
        mRlLawlookActivityInfomContent.setOnClickListener(this);
        mIvLawLookProfile = (ImageView) findViewById(R.id.iv_lawLook_profile);
        mIvLawLookProfile.setOnClickListener(this);
        mRlLawlookActivityProfile = (RelativeLayout) findViewById(R.id.rl_lawlook_activity_profile);
        mRlLawlookActivityProfile.setOnClickListener(this);
        mRlvLawOrganizer = (RecyclerView) findViewById(rlv_law_organizer);
        mRlvLawOrganizer.setOnClickListener(this);
        mIvLawLookOrganizer = (ImageView) findViewById(iv_lawLook_organizer);
        mIvLawLookOrganizer.setOnClickListener(this);
        mRlLawlookActivityOrganizer = (RelativeLayout) findViewById(R.id.rl_lawlook_activity_organizer);
        mRlLawlookActivityOrganizer.setOnClickListener(this);
        mRlvLawIntroduction = (RecyclerView) findViewById(R.id.rlv_law_Introduction);
        mRlvLawIntroduction.setOnClickListener(this);
        mIvLawLookPicture = (ImageView) findViewById(R.id.iv_lawLook_picture);
        mIvLawLookPicture.setOnClickListener(this);
        mRlLawlookActivityPicture = (RelativeLayout) findViewById(R.id.rl_lawlook_activity_picture);
        mRlLawlookActivityPicture.setOnClickListener(this);
        mIvLawLookPictureLeft = (ImageView) findViewById(R.id.iv_LawLook_pictureLeft);
        mIvLawLookPictureLeft.setOnClickListener(this);
        mRlLawlookPictureLeft = (RelativeLayout) findViewById(R.id.rl_lawlook_pictureLeft);
        mRlLawlookPictureLeft.setOnClickListener(this);
        mRlvLawlookPicture = (RecyclerView) findViewById(R.id.rlv_lawlook_picture);
        mRlvLawlookPicture.setOnClickListener(this);
        mIvLawLookPictureRight = (ImageView) findViewById(R.id.iv_lawLook_pictureRight);
        mIvLawLookPictureRight.setOnClickListener(this);
        mRlLawLookPictureRight = (RelativeLayout) findViewById(R.id.rl_lawLook_pictureRight);
        mRlLawLookPictureRight.setOnClickListener(this);
        mLiearLawLookPicture = (LinearLayout) findViewById(liear_lawLook_Picture);
        mLiearLawLookPicture.setOnClickListener(this);
        mIvLawLookMap = (ImageView) findViewById(iv_lawLook_map);
        mIvLawLookMap.setOnClickListener(this);
        mRlLawlookActivityMap = (RelativeLayout) findViewById(R.id.rl_lawlook_activity_map);
        mRlLawlookActivityMap.setOnClickListener(this);
        mIvLawLookActivitymap = (ImageView) findViewById(iv_lawLook_activitymap);
        mIvLawLookActivitymap.setOnClickListener(this);
        mOid = getIntent().getStringExtra(ACTIVITYLOOK);
        if (mOid == null) {
            finish();
        }
        mTvNull = (TextView) findViewById(R.id.tv_null);
        mLiearLawLookActivitymap = (LinearLayout) findViewById(R.id.liear_lawLook_activitymap);
        mLiearLawLookActivitymap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case rl_lawlook_pictureLeft:
                mMovePosition = mPictureManager.findFirstVisibleItemPosition();
                if (mMovePosition > 0) {
                    mMovePosition = mMovePosition - 1;
                    move(mMovePosition);
                }
                break;
            case R.id.rl_lawLook_pictureRight:
                mMovePosition = mPictureManager.findLastVisibleItemPosition();
                if (mMovePosition == mPictureAdapter.getItemCount()) {
                    move(mMovePosition);
                } else {
                    mMovePosition = mMovePosition + 1;
                    move(mMovePosition);
                }
                break;
            case R.id.rl_lawlook_activity_infom://活动详情
                if (mActivityDetailVO != null && !TextUtils.isEmpty(mActivityDetailVO.getIntroduce()))
                    ShowOrHide.showOrHide(mRlLawlookActivityInfomContent, mIvLawLookInfom);
                break;
            case R.id.rl_lawlook_activity_organizer://协办方简介
                if (mActivityDetailVO != null && mActivityDetailVO.getCohandle() != null && !mActivityDetailVO.getCohandle().isEmpty())
                    ShowOrHide.showOrHide(mRlvLawIntroduction, mIvLawLookOrganizer);
                break;
            case R.id.rl_lawlook_activity_picture://活动图片
                if (mActivityDetailVO != null && mActivityDetailVO.getAttachments() != null && !mActivityDetailVO.getAttachments().isEmpty()) {
                    ShowOrHide.showOrHide(mLiearLawLookPicture, mIvLawLookPicture);
                }
                break;
            case R.id.rl_lawlook_activity_map://地图
                if (mActivityDetailVO != null && !TextUtils.isEmpty(mActivityDetailVO.getAxis()))
                    ShowOrHide.showOrHide(mLiearLawLookActivitymap, mIvLawLookMap);
                break;
            case R.id.rl_lawlook_activity_profile://承办方简介
                if (mActivityDetailVO != null && mActivityDetailVO.getUndertakehandle() != null && !mActivityDetailVO.getUndertakehandle().isEmpty())
                    ShowOrHide.showOrHide(mRlvLawOrganizer, mIvLawLookProfile);
                break;
            default:
                break;
        }
    }

    private void move(int n) {
        if (n <= 0) {
            mIvLawLookPictureLeft.setImageResource(R.mipmap.icon_lawvote_shallowleft);
            mIvLawLookPictureRight.setImageResource(R.mipmap.icon_lawvote_right);
        } else if (n >= mPictureAdapter.getItemCount() - 1) {
            mIvLawLookPictureLeft.setImageResource(R.mipmap.icon_lawvote_left);
            mIvLawLookPictureRight.setImageResource(R.mipmap.icon_lawvote_shallowright);
        } else {
            mIvLawLookPictureLeft.setImageResource(R.mipmap.icon_lawvote_left);
            mIvLawLookPictureRight.setImageResource(R.mipmap.icon_lawvote_right);
        }
        mRlvLawlookPicture.stopScroll();
        smoothMoveToPosition(n);
    }

    private void smoothMoveToPosition(int n) {
        int firstItem = mPictureManager.findFirstVisibleItemPosition();
        int lastItem = mPictureManager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRlvLawlookPicture.smoothScrollToPosition(n);
        } else if (n <= lastItem) {
            int left = mRlvLawlookPicture.getChildAt(n - firstItem).getLeft();
            mRlvLawlookPicture.smoothScrollBy(0, left);
        } else {
            mRlvLawlookPicture.smoothScrollToPosition(n);
        }
    }


    //加载详情数据
    private void loadData() {
        if (getInProgess())
            return;
        setInProgess(true);
        JapubService service = new JapubService(this);
        service.setProgressShowContent(mContext.getString(R.string.get_ing));
        service.setShowProgress(true);
        service.getActivityDetail(mOid, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                setInProgess(false);
                if (values == null || values.isEmpty() || !(values.get(0) instanceof ActivityDetailVO)) {
                    T.showLong(mContext, getString(R.string.get_error_noeffectdata));
                    return;
                }
                mActivityDetailVO = (ActivityDetailVO) values.get(0);
                bindViewData();
            }

            @Override
            public void onError(String msg, String content) {
                T.showLong(mContext, msg);
                setInProgess(false);
            }
        });
    }

    private void bindViewData() {

        ImageLoader.getInstance().displayImage(UrlUtil.getImageFileUrl(mContext,mActivityDetailVO.getPhoto()),mIvLawLookShowTitile);
        TextViewUtil.isEmpty(mTvLawLookactivityName, mActivityDetailVO.getTitle());
        TextViewUtil.isEmpty(mTvLawLookActivityDate,
                TimeUtil.getYMDT(mActivityDetailVO.getActivityTimeStart())
                        + mContext.getString(R.string.to)
                        + TimeUtil.getYMDT(mActivityDetailVO.getActivityTimeEnd()));
        BaseCommonDataVO baseCommonDataVO2 = BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getAreas(), mActivityDetailVO.getProvince());
        String province = "";
        if (baseCommonDataVO2 != null) {
            province = baseCommonDataVO2.getName();
        }
        BaseCommonDataVO baseCommonDataVO = BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getAreas(), mActivityDetailVO.getCity());
        String city = "";
        if (baseCommonDataVO != null) {
            city = baseCommonDataVO.getName();
        }
        BaseCommonDataVO baseCommonDataVO1 = BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getAreas(), mActivityDetailVO.getCounty());
        String country = "";
        if (baseCommonDataVO1 != null) {
            country = baseCommonDataVO1.getName();
        }
        if (mActivityDetailVO.getAddress() == null) {
            mAddress = province + city + country;
        } else {
            mAddress = province + city + country + mActivityDetailVO.getAddress();
        }

        TextViewUtil.isEmpty(mTvLawLookActivityhere, mAddress);
        StringBuffer s1 = new StringBuffer("");
        if (mActivityDetailVO.getUndertakehandle() != null) {
            for (int i = 0; i < mActivityDetailVO.getUndertakehandle().size(); i++) {
                s1.append(mActivityDetailVO.getUndertakehandle().get(i).getName());
                if (i != mActivityDetailVO.getUndertakehandle().size() - 1) {
                    s1.append("，");
                }
            }
        }
        TextViewUtil.isEmpty(mTvLawLookActivityundertaker, s1.toString());
        StringBuffer s2 = new StringBuffer("");
        if (mActivityDetailVO.getCohandle() != null) {
            for (int i = 0; i < mActivityDetailVO.getCohandle().size(); i++) {
                s2.append(mActivityDetailVO.getCohandle().get(i).getName());
                if (i != mActivityDetailVO.getCohandle().size() - 1) {
                    s2.append("，");
                }
            }
        }
        TextViewUtil.isEmpty(mTvLawLookActivityOrganizer, s2.toString());
        TextViewUtil.isEmpty(mTvInfomContent, mActivityDetailVO.getIntroduce()); //承办方简介
        if (mActivityDetailVO.getUndertakehandle() != null)
            setOrganizerAdapterDate((ArrayList) mActivityDetailVO.getUndertakehandle(), mRlvLawIntroduction);
        if (mActivityDetailVO.getCohandle() != null)
            setOrganizerAdapterDate((ArrayList) mActivityDetailVO.getCohandle(), mRlvLawOrganizer);
        setPictureAdapterData();
        initMap();
    }

    private void initMap() {
        if (mActivityDetailVO.getAxis() == null || mActivityDetailVO.getAxis().equals("")) {
            mIvLawLookActivitymap.setVisibility(View.GONE);
            mTvNull.setVisibility(View.VISIBLE);
        } else {
            mTvNull.setVisibility(View.GONE);
            mIvLawLookActivitymap.setVisibility(View.VISIBLE);
            String lawfirmAxis = mActivityDetailVO.getAxis();
            int i = lawfirmAxis.indexOf(",");
            final String longitude = lawfirmAxis.substring(0, i);
            final String latitude = lawfirmAxis.substring(i + 1, lawfirmAxis.length());
            mIvLawLookActivitymap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, WalkingRouteActivity.class);
                    intent.putExtra(WalkingRouteActivity.LATITUDE, Double.parseDouble(latitude));
                    intent.putExtra(WalkingRouteActivity.LONGITUDE, Double.parseDouble(longitude));
                    intent.putExtra(WalkingRouteActivity.ADDRESS, mAddress);
//                    intent.putExtra(WalkingRouteActivity.SERCESCALL, mJaglsOrgDetailVO.getTelephone());
//                    intent.putExtra(BaseActivity.CSTR_EXTRA_TITLE_STR, mJaglsOrgDetailVO.getName());
                    mContext.startActivity(intent);
                }
            });

            String map_addresss = UrlUtil.getStaticMapImgUrl(mContext, longitude, latitude);
            ImageLoaderManager.LoadNetImage(map_addresss, mIvLawLookActivitymap);
        }
    }

}
