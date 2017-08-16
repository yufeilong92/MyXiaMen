package com.lawyee.apppublic.ui.org.japub;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.LawLookActivityPictureAdapter;
import com.lawyee.apppublic.adapter.LawVoteAdapter;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.util.ShowOrHide;

import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
import java.util.List;
/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  LawVoteActivity.java
 * @Package com.lawyee.apppublic.ui.org
 * @Description:    法制宣传投票页
 * @author: YFL
 * @date:   2017/7/25 11:21
 * @version V 1.0 xxxxxxxx
 * @verdescript  版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/7/25 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class LawVoteActivity extends BaseActivity implements View.OnClickListener {


    private GridLayoutManager mPictureManager;
    private ArrayList mPeopleLists;
    private LawVoteAdapter mPeopleAdapter;
    private LawVoteAdapter mCaseAdapter;
    private ArrayList mlists;
    private ImageView mIvShowTitile;
    private TextView mTvLawVoteActivityName;
    private TextView mTvLawVoteActivityDate;
    private TextView mTvLawVoteTime;
    private TextView mTvLawVoteHostUnit;
    private TextView mTvLawVoteContractorUnit;
    private ImageView mIvLawVoteInfom;
    private RelativeLayout mRlLawVoteActivityInfom;
    private TextView mTvLawVoteActivityInfom;
    private RelativeLayout mRlLawVoteActivityContent;
    private ImageView mIvLawvotePicture;
    private RelativeLayout mRlLawVoteActivityPicture;
    private ImageView mIvLawVotePictureLeft;
    private RelativeLayout mRlLawVotePictureLeft;
    private RecyclerView mRlvLawVotePicture;
    private ImageView mIvLawVotePictureRight;
    private RelativeLayout mRlLawVotePictureRight;
    private LinearLayout mLiearLawVotePicture;
    private ImageView mIvLawVotePeople;
    private RelativeLayout mRlLawVoteActivityPeople;
    private RecyclerView mRlvLawVotePeople;
    private ImageView mIvLawVoteCase;
    private RelativeLayout mRlLawVoteActivityCase;
    private RecyclerView mRlvLawVoteCase;
    private Button mBtnLawVoteSubmit;
    private LawLookActivityPictureAdapter mPictureAdapter;
    private int mItemPosition;

    private String mPeopleType = "peopletype";
    private String mCaseType = "casetype";

    private List<String> mPeopleSelects;
    private List<String> mCaseSelects;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_law_vote);
        initView();
        initData();
        setHide();
    }

    private void setHide() {
        mRlLawVoteActivityContent.setVisibility(View.GONE);
        mLiearLawVotePicture.setVisibility(View.GONE);
        mRlvLawVotePeople.setVisibility(View.GONE);
        mRlvLawVoteCase.setVisibility(View.GONE);
        mBtnLawVoteSubmit.setVisibility(View.GONE);
    }

    private void initData() {
//        ImageLoader.getInstance().displayImage(UrlUtil.getImageFileUrl(this, null), mIvShowTitile);
        mPeopleLists = new ArrayList<>();
        String url1 = "http://img3.utuku.china.com/466x0/news/20170709/6aae8452-7dd5-465f-9061-d58c291a7439.jpg";
        String url2 = "http://img2.utuku.china.com/463x0/news/20170709/a79d91d4-be7e-4276-bbbf-11855b4f230c.jpg";
        String url3 = "http://img3.utuku.china.com/412x0/news/20170709/1232f73d-f17a-4d58-bf4a-b847edebc8ec.jpg";
        String url4 = "http://img2.utuku.china.com/259x0/news/20170710/458415fc-f34c-47e2-af66-9355a763434f.jpg";
        String url5 = "http://img0.utuku.china.com/477x0/news/20170709/fae15d97-3448-468b-82a1-83fd60e2d3a6.jpg";
        String url6 = "http://img2.utuku.china.com/259x0/news/20170710/458415fc-f34c-47e2-af66-9355a763434f.jpg";
        String url7 = "http://img0.utuku.china.com/477x0/news/20170709/fae15d97-3448-468b-82a1-83fd60e2d3a6.jpg";
        String url8 = "http://img2.utuku.china.com/259x0/news/20170710/458415fc-f34c-47e2-af66-9355a763434f.jpg";
        String url9 = "http://img0.utuku.china.com/477x0/news/20170709/fae15d97-3448-468b-82a1-83fd60e2d3a6.jpg";
        String url0 = "http://img2.utuku.china.com/259x0/news/20170710/458415fc-f34c-47e2-af66-9355a763434f.jpg";
        String url11 = "http://img0.utuku.china.com/477x0/news/20170709/fae15d97-3448-468b-82a1-83fd60e2d3a6.jpg";
        if (mlists == null) {
            mlists = new ArrayList<>();
        }
        mlists.add(url1);
        mlists.add(url2);
        mlists.add(url3);
        mlists.add(url4);
        mlists.add(url5);
        mlists.add(url6);
        mlists.add(url7);
        mlists.add(url8);
        mlists.add(url9);
        mlists.add(url0);
        mlists.add(url11);
        for (int i = 0; i < 5; i++) {
            VoteVo voteVo = new VoteVo();
            voteVo.setName("小米" + i);
            voteVo.setId("a" + i);
            voteVo.setOffice("法治宣传员");
            voteVo.setUnit("贵阳市司法局");
            voteVo.setCases("xxx事件");
            mPeopleLists.add(voteVo);
        }
        setActivityPictureAdapter();
        setAdapterData();
    }

    private void setAdapterData() {
        if (mPeopleSelects == null)
            mPeopleSelects = new ArrayList<>();
        if (mCaseSelects == null)
            mCaseSelects = new ArrayList<>();
        mCaseAdapter = new LawVoteAdapter(mPeopleLists, this, LawVoteAdapter.CSRT_INTENTPARAMETER_CASE, LawVoteAdapter.CSRT_INTENTPARAMETER_VOTE);
        mPeopleAdapter = new LawVoteAdapter(mPeopleLists, this, LawVoteAdapter.CSRT_INTENTPARAMETER_PEOPLE, LawVoteAdapter.CSRT_INTENTPARAMETER_VOTE);
        setlawAdapter(mRlvLawVoteCase, mCaseAdapter, mCaseType);
        setlawAdapter(mRlvLawVotePeople, mPeopleAdapter, mPeopleType);
        mPeopleAdapter.setPeopleChboxSelectListener(new LawVoteAdapter.OnRecyclerPeopleChboxSelectListener() {
            @Override
            public void onChboxSelectListener(List<String> selectLists, int position) {
                Log.e(TAG, "People数据集合" + selectLists.size());
                if (mPeopleSelects != null && !mPeopleSelects.isEmpty()) {
                    mPeopleSelects.clear();
                }
                for (String s : selectLists) {
                    Log.e(TAG, "People数据" + s);
                    mPeopleSelects.add(s);
                }
                Log.e(TAG, "mPeopleSelects" + mPeopleSelects.size());
            }
        });
        mCaseAdapter.setCaseChboxSelectListener(new LawVoteAdapter.OnRecyclerCaseChboxSelectListener() {
            @Override
            public void onChboxSelectListener(List<String> selectLists, int position) {
                Log.e(TAG, "Case数据集合" + selectLists.size());
                if (mCaseSelects != null && !mCaseSelects.isEmpty()) {
                    mCaseSelects.clear();
                }
                for (String s : selectLists) {
                    Log.e(TAG, "Case数据" + s);
                    mCaseSelects.add(s);
                }
                Log.e(TAG, "mCaseSelects" + mCaseSelects.size());
            }
        });
    }


    /**
     * @param recyclerView 显示视图
     */
    private void setlawAdapter(RecyclerView recyclerView, LawVoteAdapter adapter, String mType) {
        GridLayoutManager layoutManager = null;
        if (mType.equals(mPeopleType)) {//法治人物
            layoutManager = new GridLayoutManager(this, 1);
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
        } else if (mType.equals(mCaseType)) {//法治事件
            layoutManager = new GridLayoutManager(this, 2);
            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    int position = parent.getChildAdapterPosition(view);
                    if (position > -1) {
                        outRect.right = 20;
                        outRect.top = 20;
                    }
                }
            });
        }
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setActivityPictureAdapter() {
        mPictureAdapter = new LawLookActivityPictureAdapter(mlists, this);
        mPictureManager = new GridLayoutManager(this, 1);
        mPictureManager.setOrientation(GridLayoutManager.HORIZONTAL);
        mRlvLawVotePicture.setLayoutManager(mPictureManager);
        mRlvLawVotePicture.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if (position > -1) {
                    outRect.right = 5;
                }
            }
        });
        mRlvLawVotePicture.setAdapter(mPictureAdapter);
        mRlvLawVotePicture.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int firstVisible = mPictureManager.findFirstVisibleItemPosition();
                int lastVisible = mPictureManager.findLastVisibleItemPosition();
                if (firstVisible <= 0) {
                    mIvLawVotePictureLeft.setImageResource(R.mipmap.icon_lawvote_shallowleft);
                    mIvLawVotePictureRight.setImageResource(R.mipmap.icon_lawvote_right);
                } else if (lastVisible >= mPictureAdapter.getItemCount() - 1) {
                    mIvLawVotePictureLeft.setImageResource(R.mipmap.icon_lawvote_left);
                    mIvLawVotePictureRight.setImageResource(R.mipmap.icon_lawvote_shallowright);
                } else {
                    mIvLawVotePictureLeft.setImageResource(R.mipmap.icon_lawvote_left);
                    mIvLawVotePictureRight.setImageResource(R.mipmap.icon_lawvote_right);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_lawVote_pictureLeft: //图片左按钮
                mItemPosition = mPictureManager.findFirstVisibleItemPosition();
                if (mItemPosition > 0) {
                    mItemPosition = mItemPosition - 1;
                    move(mItemPosition);
                }
                break;
            case R.id.rl_lawVote_PictureRight://图片右按钮
                mItemPosition = mPictureManager.findLastVisibleItemPosition();
                if (mItemPosition == mPictureAdapter.getItemCount()) {
                    move(mItemPosition);
                } else {
                    mItemPosition = mItemPosition + 1;
                    move(mItemPosition);
                }
                break;

            case R.id.btn_lawVote_Submit://提交
                if (mCaseSelects == null && mPeopleSelects == null)
                    return;
                if (mCaseSelects.size() == 0 && mPeopleSelects.size() == 0) {
                    T.showShort(this, "请对您喜爱的人物事件投票");
                    return;
                }
                if (mPeopleSelects.size() > 0) {
                    mPeopleAdapter.setVoteNumber(LawVoteAdapter.CSRT_INTENTPARAMETER_PEOPLE, LawVoteAdapter.CSRT_INTENTPARAMETER_LOOKVOTE, null);
                }
                if (mCaseSelects.size() > 0) {
                    mCaseAdapter.setVoteNumber(LawVoteAdapter.CSRT_INTENTPARAMETER_CASE, LawVoteAdapter.CSRT_INTENTPARAMETER_LOOKVOTE, null);
                }

                break;
            case R.id.rl_lawVote_activity_infom://活动详情
                ShowOrHide.showOrHide(mRlLawVoteActivityContent, mIvLawVoteInfom);
                break;
            case R.id.rl_lawVote_activity_picture://活动照片
                if (mlists != null && !mlists.isEmpty()) {
                    ShowOrHide.showOrHide(mLiearLawVotePicture, mIvLawvotePicture);
                }
                break;
            case R.id.rl_lawVote_activity_people://法治人物
                ShowOrHide.showOrHide(mRlvLawVotePeople, mIvLawVotePeople);
                ShowOrHide.showOrHideBtn(mRlvLawVotePeople, mRlvLawVoteCase, mBtnLawVoteSubmit);
                break;
            case R.id.rl_lawVote_activity_case://法治事件
                ShowOrHide.showOrHide(mRlvLawVoteCase, mIvLawVoteCase);
                ShowOrHide.showOrHideBtn(mRlvLawVotePeople, mRlvLawVoteCase, mBtnLawVoteSubmit);
                break;
        }

    }

    private void move(int position) {
        if (position <= 0) {
            mIvLawVotePictureLeft.setImageResource(R.mipmap.icon_lawvote_shallowleft);
            mIvLawVotePictureRight.setImageResource(R.mipmap.icon_lawvote_right);
        } else if (position >= mPictureAdapter.getItemCount() - 1) {
            mIvLawVotePictureLeft.setImageResource(R.mipmap.icon_lawvote_left);
            mIvLawVotePictureRight.setImageResource(R.mipmap.icon_lawvote_shallowright);
        } else {
            mIvLawVotePictureLeft.setImageResource(R.mipmap.icon_lawvote_left);
            mIvLawVotePictureRight.setImageResource(R.mipmap.icon_lawvote_right);
        }
        mRlvLawVotePicture.stopScroll();
        snoothMoveToPosition(position);
    }

    private void snoothMoveToPosition(int position) {
        int firstVisible = mPictureManager.findFirstVisibleItemPosition();
        int lastVisible = mPictureManager.findLastVisibleItemPosition();
        if (position <= firstVisible) {
            mRlvLawVotePicture.smoothScrollToPosition(position);
        } else if (position <= lastVisible) {
            int left = mRlvLawVotePicture.getChildAt(position - firstVisible).getLeft();
            mRlvLawVotePicture.smoothScrollBy(0, left);
        } else {
            mRlvLawVotePicture.smoothScrollToPosition(position);
        }
    }

    private void initView() {
        mIvShowTitile = (ImageView) findViewById(R.id.iv_showTitile);
        mTvLawVoteActivityName = (TextView) findViewById(R.id.tv_LawVoteActivityName);
        mTvLawVoteActivityDate = (TextView) findViewById(R.id.tv_LawVoteActivityDate);
        mTvLawVoteTime = (TextView) findViewById(R.id.tv_LawVoteTime);
        mTvLawVoteHostUnit = (TextView) findViewById(R.id.tv_lawVoteHost_Unit);
        mTvLawVoteContractorUnit = (TextView) findViewById(R.id.tv_LawVoteContractor_Unit);
        mIvLawVoteInfom = (ImageView) findViewById(R.id.iv_lawVote_infom);
        mRlLawVoteActivityInfom = (RelativeLayout) findViewById(R.id.rl_lawVote_activity_infom);
        mRlLawVoteActivityInfom.setOnClickListener(this);
        mTvLawVoteActivityInfom = (TextView) findViewById(R.id.tv_lawVote_activity_infom);
        mRlLawVoteActivityContent = (RelativeLayout) findViewById(R.id.rl_lawVote_activityContent);
        mIvLawvotePicture = (ImageView) findViewById(R.id.iv_lawvote_picture);
        mRlLawVoteActivityPicture = (RelativeLayout) findViewById(R.id.rl_lawVote_activity_picture);
        mRlLawVoteActivityPicture.setOnClickListener(this);
        mIvLawVotePictureLeft = (ImageView) findViewById(R.id.iv_LawVote_pictureLeft);
        mRlLawVotePictureLeft = (RelativeLayout) findViewById(R.id.rl_lawVote_pictureLeft);
        mRlLawVotePictureLeft.setOnClickListener(this);
        mRlvLawVotePicture = (RecyclerView) findViewById(R.id.rlv_lawVote_picture);
        mIvLawVotePictureRight = (ImageView) findViewById(R.id.iv_LawVote_PictureRight);
        mRlLawVotePictureRight = (RelativeLayout) findViewById(R.id.rl_lawVote_PictureRight);
        mRlLawVotePictureRight.setOnClickListener(this);
        mLiearLawVotePicture = (LinearLayout) findViewById(R.id.liear_lawVote_Picture);
        mIvLawVotePeople = (ImageView) findViewById(R.id.iv_lawVote_people);
        mRlLawVoteActivityPeople = (RelativeLayout) findViewById(R.id.rl_lawVote_activity_people);
        mRlLawVoteActivityPeople.setOnClickListener(this);
        mRlvLawVotePeople = (RecyclerView) findViewById(R.id.rlv_lawVote_People);
        mIvLawVoteCase = (ImageView) findViewById(R.id.iv_lawVote_case);
        mRlLawVoteActivityCase = (RelativeLayout) findViewById(R.id.rl_lawVote_activity_case);
        mRlLawVoteActivityCase.setOnClickListener(this);
        mRlvLawVoteCase = (RecyclerView) findViewById(R.id.rlv_lawVote_Case);
        mBtnLawVoteSubmit = (Button) findViewById(R.id.btn_lawVote_Submit);
        mIvLawVotePictureLeft.setOnClickListener(this);
        mIvLawVotePictureRight.setOnClickListener(this);
        mBtnLawVoteSubmit.setOnClickListener(this);
    }

    public class VoteVo {
        String id;
        String name;
        String unit;
        String office;
        String voteNumber;
        String cases;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCases() {
            return cases;
        }

        public void setCases(String cases) {
            this.cases = cases;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getOffice() {
            return office;
        }

        public void setOffice(String office) {
            this.office = office;
        }

        public String getVoteNumber() {
            return voteNumber;
        }

        public void setVoteNumber(String voteNumber) {
            this.voteNumber = voteNumber;
        }
    }

}
