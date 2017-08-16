package com.lawyee.apppublic.adapter;


import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.ui.ViewImageActivity;
import com.lawyee.apppublic.ui.WalkingRouteActivity;
import com.lawyee.apppublic.util.ImageLoaderManager;
import com.lawyee.apppublic.vo.Message;
import com.lawyee.apppublic.widget.BubbleImageView;
import com.lqr.adapter.LQRAdapterForRecyclerView;
import com.lqr.adapter.LQRViewHolderForRecyclerView;
import com.lqr.emoji.MoonUtils;

import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.TimeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lawyee.apppublic.ui.ViewImageActivity.CSTR_EXTRA_IMAGE_STR_LOCAL;


/**
 * @创建者 czq
 * @描述 会话列表适配器
 */
public class SessionAdapter extends LQRAdapterForRecyclerView<Message> {

    private Context mContext;
    private static final int SEND_TEXT = R.layout.item_text_send;
    private static final int RECEIVE_TEXT = R.layout.item_text_receive;
    private static final int SEND_IMAGE = R.layout.item_image_send;
    private static final int RECEIVE_IMAGE = R.layout.item_image_receive;
    private static final int SEND_LOCATION = R.layout.item_location_send;
    private static final int RECEIVE_LOCATION = R.layout.item_location_receive;
    private Map<String, Float> mProgress = new HashMap<>();
    public SessionAdapter(Context context, List<Message> data) {
        super(context, data);
        mContext = context;
    }


    @Override
    public void convert(LQRViewHolderForRecyclerView helper, final Message item, final int position) {

        //取上一条的时间显示记录
        String predate = "";
        if(position>0)
        {
            Message prmesage= getItem(position-1);
            if(prmesage!=null) {
                predate = TimeUtil.getChatTime(prmesage.getDate());
            }
        }
        String date = TimeUtil.getChatTime(item.getDate());
        TextView tvtime = helper.getView(R.id.tvTime);
        if(!StringUtil.isEmpty(date)&&!date.equals(predate))
        {
            //显示时间
            tvtime.setText(date);
            tvtime.setVisibility(View.VISIBLE);
        }else
        {
            tvtime.setVisibility(View.GONE);
        }
        //设置时间
        //setTime(helper, item, position);

      // if (position==0) {
            //设置头像
        setHeader(helper, item);
     //   }
        //文本消息
        if (item.getType() == Message.CINT_MESSAGE_TYPE_NR) {
            setTextMessage(helper, item);
        }
        //图片消息
        else if (item.getType() == Message.CINT_MESSAGE_TYPE_IMAGE) {
            setImageMessage(helper, item);
        }
        //地图信息
        else if(item.getType()==Message.CINT_MESSAGE_TYPE_ADDRESS){
            setLocationMessage(helper, item);
        }
    }

    private void setHeader(LQRViewHolderForRecyclerView helper, Message item) {
        ImageView ivAvatar = helper.getView(R.id.ivAvatar);
        String avatar=item.getPhoto();
        if (!TextUtils.isEmpty(avatar)) {
            ImageLoaderManager.LoadNetImage(avatar, ivAvatar);
        } else {
            //先做个演示效果
            if(item.isSend()){
                ivAvatar.setImageResource(R.drawable.ic_session_avatar);
            }else {
                ivAvatar.setImageResource(R.drawable.ic_default_avatar);
            }
        }
    }

    private void setTextMessage(LQRViewHolderForRecyclerView helper, Message item) {
        helper.setText(R.id.tvText, item.getContent());
        //识别并显示表情
        MoonUtils.identifyFaceExpression(ApplicationSet.getInstance().getApplicationContext(), helper.getView(R.id.tvText), item.getContent(), ImageSpan.ALIGN_BOTTOM);
    }
    private void setImageMessage(LQRViewHolderForRecyclerView helper, final Message item) {
        final BubbleImageView bivPic = helper.getView(R.id.bivPic);
        if (!TextUtils.isEmpty(item.getContent())) {
            ImageLoaderManager.LoadLocalImage(item.getContent(), bivPic);
            bivPic.setPercent(100);
        } else {
        }
        //查看图片大图
        bivPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ViewImageActivity.class);
                intent.putExtra(CSTR_EXTRA_IMAGE_STR_LOCAL, item.getContent());
                mContext.startActivity(intent);
            }
        });
    }
    private void setLocationMessage(LQRViewHolderForRecyclerView helper, final Message item) {
        ImageView bivPic = helper.getView(R.id.iv_map);
        TextView tvMap=helper.getView(R.id.tv_map);
        LinearLayout ll_map=helper.getView(R.id.ll_map);
        Log.e("czq",item.getAddress());
        tvMap.setText(item.getContent());
        ImageLoaderManager.LoadNetImage(item.getAddress(), bivPic);
        //查看地图详情
        ll_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, WalkingRouteActivity.class);
                intent.putExtra(WalkingRouteActivity.LATITUDE, item.getLatitude());
                intent.putExtra(WalkingRouteActivity.LONGITUDE, item.getLongitude());
               intent.putExtra(WalkingRouteActivity.ADDRESS, item.getContent());
                Log.e("czq",item.getLatitude()+"");
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        Message msg = getData().get(position);
        int msgType = msg.getType();

        if (msgType == Message.CINT_MESSAGE_TYPE_NR) {
            if (msg.isSend()) {
                return SEND_TEXT;
            } else {
                return RECEIVE_TEXT;
            }
        }
        if (msgType == Message.CINT_MESSAGE_TYPE_IMAGE) {
            if (msg.isSend()) {
                return SEND_IMAGE;
            } else {
                return RECEIVE_IMAGE;
            }
        }
        if(msgType==Message.CINT_MESSAGE_TYPE_ADDRESS){
            if (msg.isSend()) {
                return SEND_LOCATION;
            } else {
                return RECEIVE_LOCATION;
            }
        }

        return super.getItemViewType(position);
    }

}

