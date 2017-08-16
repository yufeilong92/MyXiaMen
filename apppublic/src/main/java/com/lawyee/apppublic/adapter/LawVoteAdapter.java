package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.ui.org.japub.LawVoteActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawerApp
 * @Package com.lawyee.apppublic.adapter
 * @Description: $todo$
 * @author: YFL
 * @date: 2017/7/12 13:52
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class LawVoteAdapter extends RecyclerView.Adapter<LawVoteAdapter.ViewHolder> implements View.OnClickListener {
    public static String CSRT_INTENTPARAMETER_PEOPLE = "people";
    public static String CSRT_INTENTPARAMETER_CASE = "case";
    public static String CSRT_INTENTPARAMETER_VOTE = "Vote";
    public static String CSRT_INTENTPARAMETER_LOOKVOTE = "lookVote";

    private ArrayList mData;
    private Context mContext;
    private LayoutInflater inflater;
    private String mType;
    private String mType1;

    private List<String> mPeopleLists;//提交id
    private List<String> mCaseLists;//提交id
    private List<Integer> mCheckPeoplePositionlists;//选中数据集
    private List<Integer> mCheckCasePositionlist;//选中数据集
    private OnRecyclerItemClickListener OnItemClickListener = null;
    private OnRecyclerPeopleChboxSelectListener peopleChboxSelectListener = null;
    private OnRecyclerCaseChboxSelectListener caseChboxSelectListener = null;

    public static Map<Integer, Boolean> mPeopleSelected;
    public static Map<Integer, Boolean> mCaseSelected;

    private boolean isPeopleClick = false;
    private boolean isCaseClick = false;

    private List<String> mList;

    public LawVoteAdapter(ArrayList mData, Context mContext, String type, String type1) {
        this.mData = mData;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
        this.mType = type;
        this.mType1 = type1;
        mCheckPeoplePositionlists = new ArrayList<>();
        mCheckCasePositionlist = new ArrayList<>();
        initSelect();
    }

    private void initSelect() {
        mPeopleSelected = new HashMap<>();
        for (int i = 0; i < mData.size(); i++) {
            mPeopleSelected.put(i, false);
        }
        mCaseSelected = new HashMap<>();
        for (int i = 0; i < mData.size(); i++) {
            mCaseSelected.put(i, false);
        }
    }

    /**
     * @param type 类型/ 人物/事件
     * @param list 票数集合
     */
    public void setVoteNumber(String type, String type1, List<String> list) {
        this.mType = type;
//        this.mList = list;
        this.mType1 = type1;
        notifyDataSetChanged();
    }

    public interface OnRecyclerCheckBoxItemListener {
        void OnCheckBoxListener(boolean isCilck, int position, String item);
    }

    public interface OnRecyclerItemClickListener {
        void OnItemClickListener(String url, int position);
    }

    public interface OnRecyclerPeopleChboxSelectListener {
        void onChboxSelectListener(List<String> selectLists, int position);
    }

    public interface OnRecyclerCaseChboxSelectListener {
        void onChboxSelectListener(List<String> selectLists, int position);
    }

    public OnRecyclerItemClickListener getOnItemClickListener() {
        return OnItemClickListener;
    }

    public void setOnItemClickListener(OnRecyclerItemClickListener onItemClickListener) {
        OnItemClickListener = onItemClickListener;
    }

    public OnRecyclerPeopleChboxSelectListener getPeopleChboxSelectListener() {
        return peopleChboxSelectListener;
    }

    public void setPeopleChboxSelectListener(OnRecyclerPeopleChboxSelectListener peopleChboxSelectListener) {
        this.peopleChboxSelectListener = peopleChboxSelectListener;
    }

    public OnRecyclerCaseChboxSelectListener getCaseChboxSelectListener() {
        return caseChboxSelectListener;
    }

    public void setCaseChboxSelectListener(OnRecyclerCaseChboxSelectListener caseChboxSelectListener) {
        this.caseChboxSelectListener = caseChboxSelectListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (mType.equals(CSRT_INTENTPARAMETER_PEOPLE)) {
            view = View.inflate(mContext, R.layout.item_people, null);
            view.setOnClickListener(this);

        } else if (mType.equals(CSRT_INTENTPARAMETER_CASE)) {
            view = View.inflate(mContext, R.layout.item_case, null);
        }
        if (view != null)
            view.setOnClickListener(this);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (mCaseLists == null) {
            mCaseLists = new ArrayList<>();
        }
        if (mPeopleLists == null) {
            mPeopleLists = new ArrayList<>();
        }
        if (mType.equals(CSRT_INTENTPARAMETER_PEOPLE)) {
            handlerPeopleSelect(holder, position, mType, mType1);

        } else if (mType.equals(CSRT_INTENTPARAMETER_CASE)) {
            handlerCaseSelect(holder, position, mType, mType1);
        }
    }

    private void handlerPeopleSelect(final ViewHolder holder, final int position, final String mType, final String mType1) {
        final LawVoteActivity.VoteVo vo = (LawVoteActivity.VoteVo) mData.get(position);


        if (mType1.equals(CSRT_INTENTPARAMETER_VOTE)) {
            holder.mLinearItemVote.setVisibility(View.VISIBLE);
            holder.mLinearItemShowVoteNumber.setVisibility(View.GONE);

        } else if (mType1.equals(CSRT_INTENTPARAMETER_LOOKVOTE)) {
            holder.mLinearItemVote.setVisibility(View.GONE);
            holder.mLinearItemShowVoteNumber.setVisibility(View.VISIBLE);


        }
        holder.mChbItemPeople.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mCheckPeoplePositionlists.contains(position))
                            return false;
                        if (isPeopleClick) {
                            int size = mCheckPeoplePositionlists.size();
                            if (size > 2) {
                                showDailog(mType, "每天最多投票三个人");
                                return true;
                            }
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        holder.mChbItemPeople.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mPeopleSelected.put(position, isChecked);
                if (isChecked) {
                    if (!mCheckPeoplePositionlists.contains(position)) {
                        mCheckPeoplePositionlists.add(position);
                        isPeopleClick = true;
                    }
                    if (!mPeopleLists.contains(vo.getId())) {
                        mPeopleLists.add(vo.getId());
                    }
                } else {
                    if (mCheckPeoplePositionlists.contains(position)) {
                        int i = mCheckPeoplePositionlists.indexOf(position);
                        mCheckPeoplePositionlists.remove(i);
                        isPeopleClick = true;
                    }
                    if (mPeopleLists.contains(vo.getId())) {
                        int i = mPeopleLists.indexOf(vo.getId());
                        mPeopleLists.remove(i);
                    }
                }
                if (peopleChboxSelectListener != null)
                    peopleChboxSelectListener.onChboxSelectListener(mPeopleLists, position);
            }
        });

        holder.mChbItemPeople.setChecked(mPeopleSelected.get(position));
    }

    private void handlerCaseSelect(final ViewHolder holder, final int position, final String mType, final String mType1) {
        final LawVoteActivity.VoteVo vo = (LawVoteActivity.VoteVo) mData.get(position);
        if (mType1.equals(CSRT_INTENTPARAMETER_VOTE)) {
            holder.mTvItemCaseVoteNumber.setVisibility(View.GONE);
            holder.mChbItemCaseVote.setVisibility(View.VISIBLE);

        } else if (mType1.equals(CSRT_INTENTPARAMETER_LOOKVOTE)) {
           holder.mChbItemCaseVote.setVisibility(View.GONE);
            holder.mTvItemCaseVoteNumber.setVisibility(View.VISIBLE);
        }
        holder.mChbItemCaseVote.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mCheckCasePositionlist.contains(position))
                            return false;
                        if (isCaseClick) {
                            int size = mCheckCasePositionlist.size();
                            if (size > 2) {
                                showDailog(mType, "每天最多投票三个事件");
                                return true;
                            }
                        }
                        break;
                    default:
                        break;
                }

                return false;
            }
        });
        holder.mChbItemCaseVote.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCaseSelected.put(position, isChecked);
                if (isChecked) {
                    if (!mCheckCasePositionlist.contains(position)) {
                        mCheckCasePositionlist.add(position);
                        isCaseClick = true;
                    }
                    if (!mCaseLists.contains(vo.getId())) {
                        mCaseLists.add(vo.getId());
                    }

                } else {
                    if (mCheckCasePositionlist.contains(position)) {
                        int i = mCheckCasePositionlist.indexOf(position);
                        mCheckCasePositionlist.remove(i);
                        isCaseClick = true;
                    }
                    if (mCaseLists.contains(vo.getId())) {
                        int indexOf = mCaseLists.indexOf(vo.getId());
                        mCaseLists.remove(indexOf);
                    }
                }
                if (caseChboxSelectListener != null)
                    caseChboxSelectListener.onChboxSelectListener(mCaseLists, position);
            }
        });
        holder.mChbItemCaseVote.setChecked(mCaseSelected.get(position));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        if (OnItemClickListener != null) {
            OnItemClickListener.OnItemClickListener((String) v.getTag(), v.getId());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIvItemCasePicture;
        public TextView mTvItemCaseTitle;
        public TextView mTvItemCaseOrder;
        public TextView mTvItemCaseVoteNumber;
        public CheckBox mChbItemCaseVote;
        public LinearLayout mLinearItemCase;
        public TextView mTvItemPeopleorder;
        public CircleImageView mIvItemHead;
        public TextView mTvItemPeopleJob;
        public TextView mTvItemPeopleAddress;
        public TextView mTvItemPeopleName;
        public CheckBox mChbItemPeople;
        public LinearLayout mLinearItemVote;
        public TextView mTvItemVoteNumber;
        public TextView mTvItemVoteName;
        public LinearLayout mLinearItemShowVoteNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mTvItemPeopleorder = (TextView) itemView.findViewById(R.id.tv_item_peopleorder);
            this.mIvItemHead = (CircleImageView) itemView.findViewById(R.id.iv_item_head);
            this.mTvItemPeopleJob = (TextView) itemView.findViewById(R.id.tv_item_people_job);
            this.mTvItemPeopleAddress = (TextView) itemView.findViewById(R.id.tv_item_people_address);
            this.mTvItemPeopleName = (TextView) itemView.findViewById(R.id.tv_item_people_name);
            this.mChbItemPeople = (CheckBox) itemView.findViewById(R.id.Chb_item_people);
            this.mLinearItemVote = (LinearLayout) itemView.findViewById(R.id.linear_item_Vote);
            this.mTvItemVoteNumber = (TextView) itemView.findViewById(R.id.tv_item_voteNumber);
            this.mTvItemVoteName = (TextView) itemView.findViewById(R.id.tv_item_VoteName);
            this.mLinearItemShowVoteNumber = (LinearLayout) itemView.findViewById(R.id.linear_item_showVoteNumber);
            this.mIvItemCasePicture = (ImageView) itemView.findViewById(R.id.iv_item_CasePicture);
            this.mTvItemCaseTitle = (TextView) itemView.findViewById(R.id.tv_item_CaseTitle);
            this.mTvItemCaseOrder = (TextView) itemView.findViewById(R.id.tv_item_CaseOrder);
            this.mTvItemCaseVoteNumber = (TextView) itemView.findViewById(R.id.tv_item_CaseVoteNumber);
            this.mChbItemCaseVote = (CheckBox) itemView.findViewById(R.id.chb_item_CaseVote);
            this.mLinearItemCase = (LinearLayout) itemView.findViewById(R.id.linear_item_Case);

        }
    }

    public void showDailog(final String mType, String content) {
        new MaterialDialog.Builder(mContext)
                .limitIconToDefaultSize() // limits the displayed icon size to 48dp
                .title("温馨提示")
                .content(content)
                .positiveText(R.string.dl_btn_ok)
                .negativeText(R.string.dl_btn_cancel)
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        int size = mCheckPeoplePositionlists.size();
                        int caseSize = mCheckCasePositionlist.size();
                        if (mType.equals(CSRT_INTENTPARAMETER_PEOPLE)) {
                            if (size <= 2) {
                                isPeopleClick = false;
                            } else {
                                isPeopleClick = true;
                            }
                        } else if (mType.equals(CSRT_INTENTPARAMETER_CASE)) {
                            if (caseSize <= 2) {
                                isCaseClick = false;
                            } else {
                                isCaseClick = true;
                            }
                        }
                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        int size = mCheckPeoplePositionlists.size();
                        int caseSize = mCheckCasePositionlist.size();
                        if (mType.equals(CSRT_INTENTPARAMETER_PEOPLE)) {
                            if (size <= 2) {
                                isPeopleClick = false;
                            } else {
                                isPeopleClick = true;
                            }
                        } else if (mType.equals(CSRT_INTENTPARAMETER_CASE)) {
                            if (caseSize <= 2) {
                                isCaseClick = false;
                            } else {
                                isCaseClick = true;
                            }
                        }
                        dialog.dismiss();
                    }
                })
                .show();

/*        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("温馨提示");
        builder.setMessage("每天只能选择三个");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int size = mCheckPeoplePositionlists.size();
                int caseSize = mCheckCasePositionlist.size();
                if (mType.equals(CSRT_INTENTPARAMETER_PEOPLE)) {
                    if (size <= 2) {
                        isPeopleClick = false;
                    } else {
                        isPeopleClick = true;
                    }
                } else if (mType.equals(CSRT_INTENTPARAMETER_CASE)) {
                    if (caseSize <= 2) {
                        isCaseClick = false;
                    } else {
                        isCaseClick = true;
                    }
                }
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int size = mCheckPeoplePositionlists.size();
                int caseSize = mCheckCasePositionlist.size();
                if (mType.equals(CSRT_INTENTPARAMETER_PEOPLE)) {
                    if (size <= 2) {
                        isPeopleClick = false;
                    } else {
                        isPeopleClick = true;
                    }
                } else if (mType.equals(CSRT_INTENTPARAMETER_CASE)) {
                    if (caseSize <= 2) {
                        isCaseClick = false;
                    } else {
                        isCaseClick = true;
                    }
                }
            }
        });
        builder.create().show();*/
    }
}
