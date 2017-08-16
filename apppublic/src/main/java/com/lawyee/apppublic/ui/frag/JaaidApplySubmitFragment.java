package com.lawyee.apppublic.ui.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.ApplyOtherStuffrAdapter;
import com.lawyee.apppublic.vo.AttachmentVO;
import com.lawyee.apppublic.vo.JaaidApplyDetailVO;
import com.lawyee.apppublic.vo.JaaidApplyFourSubmitEvent;
import com.lawyee.apppublic.vo.JaaidIsNextFourEvent;

import net.lawyee.mobilelib.utils.FileUtil;
import net.lawyee.mobilelib.utils.T;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static android.app.Activity.RESULT_OK;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: Apply_Submit_fragment.java
 * @Package com.lawyee.apppublic.ui.frag
 * @Description: 法援申请提交页
 * @author: YFL
 * @date: 2017/6/2 15:31
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/6/2 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JaaidApplySubmitFragment extends Fragment implements View.OnClickListener {
    private TextView mTvApplySubmitIdcard;
    private TextView mTvApplySubmitCondition;
    private TextView mTvApplySubmitBook;
    private TextView mTvApplySubmitStuff;
    //启动actviity的请求码
    private static final int REQUEST_IMAGEIDCARD = 2;//身份证
    private static final int REQUEST_IMAGESCIENCE = 3;//经济材料
    private static final int REQUEST_IMAGEBOOK = 4;//委托书
    private static final int REQUEST_IMAGEMATERIAL = 5;//其它材料
    //    存放图片路径的list
    private ArrayList<String> mSelectPathID =new ArrayList<>();//身份证图片地址集合
    private ArrayList<String> mSelectPathCon=new ArrayList<>();//经济材料图片地址集合
    private ArrayList<String> mSelectPathBook=new ArrayList<>();//法援申请委托书图片地址集合
    private ArrayList<String> mSelectPathOther=new ArrayList<>();//其它材料图片地址集合
    private ArrayList<String> mSelectPathNameOther = new ArrayList<>();//简化名字集合
    private ArrayList<String> mSelectPathFianlOther = new ArrayList<>();//需提交数据
    private ImageView mIvIdCardDelete;
    private ImageView mIvBookDelete;
    private ListView mLvOtherMeterial;
    private ImageView mIvConditionDelete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apply_submit, container, false);
        initView(view);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(JaaidIsNextFourEvent event) {
        JaaidApplyDetailVO vo = event.getmData();
        submit(vo);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGEIDCARD) {//身份证
            if (resultCode == RESULT_OK) {
                mSelectPathID = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                String name = null;
                for (String str : mSelectPathID
                        ) {
                    name = FileUtil.getFileName(str);
                }
                mTvApplySubmitIdcard.setText(name);
                mTvApplySubmitIdcard.setBackgroundResource(0);
                mIvIdCardDelete.setVisibility(View.VISIBLE);
            }
        } else if (requestCode == REQUEST_IMAGESCIENCE) {//经济情况
            if (resultCode == RESULT_OK) {
                mSelectPathCon = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                String name = null;
                for (String str : mSelectPathCon
                        ) {
                    name = FileUtil.getFileName(str);
                }
                mTvApplySubmitCondition.setText(name);
                mTvApplySubmitCondition.setBackgroundResource(0);
                mIvConditionDelete.setVisibility(View.VISIBLE);
            }
        } else if (requestCode == REQUEST_IMAGEBOOK) {//委托书
            if (resultCode == RESULT_OK) {
                mSelectPathBook = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);

                String name = null;
                for (String str : mSelectPathBook
                        ) {
                    name = FileUtil.getFileName(str);
                }
                mTvApplySubmitBook.setText(name);
                mTvApplySubmitBook.setBackgroundResource(0);
                mIvBookDelete.setVisibility(View.VISIBLE);
            }
        } else if (requestCode == REQUEST_IMAGEMATERIAL) {//其它材料
            if (resultCode == RESULT_OK) {
                mSelectPathOther = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                setAdapter();
            }
        }
    }

    private void setAdapter() {
        for (String str : mSelectPathOther) {
            mSelectPathFianlOther.add(str);
        }
        for (String str : mSelectPathOther) {
            String name = FileUtil.getFileName(str);
            mSelectPathNameOther.add(name);
        }
        final ApplyOtherStuffrAdapter stuffrAdapter = new ApplyOtherStuffrAdapter(mSelectPathNameOther, getActivity());
        mLvOtherMeterial.setAdapter(stuffrAdapter);
        stuffrAdapter.setItemDelete(new ApplyOtherStuffrAdapter.OnClickItemDelete() {
            @Override
            public void onClickdelete(int position) {
                mSelectPathNameOther.remove(position);
                mSelectPathFianlOther.remove(position);
                stuffrAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initView(View view) {
        mTvApplySubmitIdcard = (TextView) view.findViewById(R.id.tv_apply_submit_idcard);
        mTvApplySubmitCondition = (TextView) view.findViewById(R.id.tv_apply_submit_condition);
        mTvApplySubmitBook = (TextView) view.findViewById(R.id.tv_apply_submit_book);
        mTvApplySubmitStuff = (TextView) view.findViewById(R.id.tv_apply_submit_stuff);
        mTvApplySubmitStuff.setOnClickListener(this);
        mTvApplySubmitBook.setOnClickListener(this);
        mTvApplySubmitCondition.setOnClickListener(this);
        mTvApplySubmitIdcard.setOnClickListener(this);
        mIvIdCardDelete = (ImageView) view.findViewById(R.id.iv_id_card_delete);
        mIvIdCardDelete.setOnClickListener(this);
        mIvBookDelete = (ImageView) view.findViewById(R.id.iv_book_delete);
        mIvBookDelete.setOnClickListener(this);
        mLvOtherMeterial = (ListView) view.findViewById(R.id.lv_other_meterial);
        mIvConditionDelete = (ImageView) view.findViewById(R.id.iv_condition_delete);
        mIvConditionDelete.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_apply_submit_idcard://身份证
                Intent intent = new Intent(getActivity(), MultiImageSelectorActivity.class);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
                // 默认选择
                if (mSelectPathID != null && mSelectPathID.size() > 0) {
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPathID);
                }
                startActivityForResult(intent, REQUEST_IMAGEIDCARD);
                break;
            case R.id.tv_apply_submit_condition://经济材料
                Intent intent2 = new Intent(getActivity(), MultiImageSelectorActivity.class);
                intent2.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                intent2.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
                intent2.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
                // 默认选择
                if (mSelectPathCon != null && mSelectPathCon.size() > 0) {
                    intent2.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPathCon);
                }
                startActivityForResult(intent2, REQUEST_IMAGESCIENCE);
                break;
            case R.id.tv_apply_submit_book://委托书
                Intent intent3 = new Intent(getActivity(), MultiImageSelectorActivity.class);
                intent3.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                intent3.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
                intent3.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
                // 默认选择
                if (mSelectPathBook != null && mSelectPathBook.size() > 0) {
                    intent3.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPathBook);
                }
                startActivityForResult(intent3, REQUEST_IMAGEBOOK);
                break;
            case R.id.tv_apply_submit_stuff://其它材料
                Intent intent4 = new Intent(getActivity(), MultiImageSelectorActivity.class);
                intent4.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                intent4.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
                intent4.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
                // 默认选择
                if (mSelectPathOther != null && mSelectPathOther.size() > 0) {
                    intent4.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPathOther);
                }
                startActivityForResult(intent4, REQUEST_IMAGEMATERIAL);
                break;
            case R.id.iv_id_card_delete:
                if (mSelectPathID!=null&&mSelectPathID.size() > 0)
                    mSelectPathID.clear();
                mTvApplySubmitIdcard.setText("");
                mIvIdCardDelete.setVisibility(View.GONE);
                mTvApplySubmitIdcard.setBackgroundResource(R.drawable.bg_input_box);
                break;
            case R.id.iv_condition_delete:
                if (mSelectPathCon!=null&&mSelectPathCon.size() > 0)
                    mSelectPathCon.clear();
                mTvApplySubmitCondition.setText("");
                mIvConditionDelete.setVisibility(View.GONE);
                mTvApplySubmitCondition.setBackgroundResource(R.drawable.bg_input_box);
                break;
            case R.id.iv_book_delete:
                if (mSelectPathBook!=null&&mSelectPathBook.size() > 0)
                    mSelectPathBook.clear();
                mTvApplySubmitBook.setText("");
                mIvBookDelete.setVisibility(View.GONE);
                mTvApplySubmitBook.setBackgroundResource(R.drawable.bg_input_box);
                break;
            default:
                break;
        }
    }

    private void submit(JaaidApplyDetailVO vo) {
        ArrayList<AttachmentVO> list = new ArrayList<>();
        if (mSelectPathID==null||mSelectPathID.size() == 0) {
            T.showShort(getActivity(), getString(R.string.jaaid_pleaseSumbitIdPicture));
            return;
        }
        for (String str : mSelectPathID) {
            AttachmentVO attachmentVO = new AttachmentVO();
            attachmentVO.setDescription_(getString(R.string.jaaid_idPicture));
            attachmentVO.setSub(AttachmentVO.CSTR_UPLOADSUB_JAAIDAPPLY_SFZ);
            attachmentVO.setLocfilepath(str);
            list.add(attachmentVO);
        }
        if (mSelectPathCon != null&&mSelectPathCon.size() > 0 ) {
            for (String str : mSelectPathCon) {
                AttachmentVO attachmentVO = new AttachmentVO();
                attachmentVO.setLocfilepath(str);
                attachmentVO.setDescription_(getString(R.string.jaaid_con_stuff));
                attachmentVO.setSub(AttachmentVO.CSTR_UPLOADSUB_JAAIDAPPLY_WT);
                list.add(attachmentVO);
            }
        }
        if (mSelectPathBook != null&&mSelectPathBook.size() > 0 ) {

            for (String str : mSelectPathBook) {
                AttachmentVO attachmentVO = new AttachmentVO();
                attachmentVO.setLocfilepath(str);
                attachmentVO.setDescription_(getString(R.string.jaaid_lawApplyBook));
                attachmentVO.setSub(AttachmentVO.CSTR_UPLOADSUB_JAAIDAPPLY_JJ);
                list.add(attachmentVO);
            }
        }
        if (mSelectPathFianlOther != null&&mSelectPathFianlOther.size() > 0 ) {
            for (String str : mSelectPathFianlOther) {
                AttachmentVO attachmentVO = new AttachmentVO();
                attachmentVO.setLocfilepath(str);
                attachmentVO.setDescription_(getString(R.string.jaaid_other_stuff));
                attachmentVO.setSub(AttachmentVO.CSTR_UPLOADSUB_JAAIDAPPLY_QT);
                list.add(attachmentVO);
            }
        }
        vo.setAttachments(list);
        EventBus.getDefault().post(new JaaidApplyFourSubmitEvent(vo, true));
    }
}
