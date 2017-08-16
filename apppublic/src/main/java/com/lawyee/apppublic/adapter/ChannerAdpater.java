package com.lawyee.apppublic.adapter;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.adapter
 * @Description: ${todo}(首页模块入口部分的Adpater)
 * @author: czq
 * @date: 2017/5/15 14:30
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lawyee.apppublic.R;

import java.util.List;


public class ChannerAdpater extends BaseAdapter{
    private final List<String> mDatas;
    private Context mContext;

    public ChannerAdpater(Context mContext, List<String> channel_info) {
        this.mContext=mContext;
        this.mDatas=channel_info;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view==null){
            view=View.inflate(mContext, R.layout.item_channel,null);
            viewHolder=new ViewHolder();
            viewHolder.iv_icon= (ImageView) view.findViewById(R.id.iv_channel);
            viewHolder.tv_title= (TextView) view.findViewById(R.id.tv_channel);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        //本地类型入口
        switch (i){
            case 0:
                viewHolder.iv_icon.setImageResource(R.drawable.selector_btn_jaaid);
                break;
            case 1:
                viewHolder.iv_icon.setImageResource(R.drawable.selector_btn_nota);
                break;
            case 2:
                viewHolder.iv_icon.setImageResource(R.drawable.selector_btn_jamed);
                break;
            case 3:
                viewHolder.iv_icon.setImageResource(R.drawable.selector_btn_jalaw_ser);
                break;
            case 4:
                viewHolder.iv_icon.setImageResource(R.drawable.selector_btn_buty_lawer);
                break;
            case 5:
                viewHolder.iv_icon.setImageResource(R.drawable.selector_btn_legalpublicity);
                break;
            case 6:
                viewHolder.iv_icon.setImageResource(R.drawable.selector_btn_auth);
                break;
            case 7:
                viewHolder.iv_icon.setImageResource(R.drawable.selector_btn_basic_law_service);
                break;

        }
        viewHolder.tv_title.setText(mDatas.get(i));
        return view;
    }
    static  class ViewHolder{
        ImageView iv_icon;
        TextView tv_title;
    }
}
