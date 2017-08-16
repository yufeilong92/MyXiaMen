package com.lawyee.apppublic.adapter;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.adapter
 * @Description: ${todo}(城市列表Adpater)
 * @author: czq
 * @date: 2017/5/24 09:49
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.vo.BaseCommonDataVO;

import java.util.List;

public class PopupAdapter extends ArrayAdapter<BaseCommonDataVO> {
    private int resource;
    private int normalBg;
    private int pressBg;
    private int selection;
    private int mType;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
        notifyDataSetChanged();
    }

    private boolean isCheck;

    public PopupAdapter(Context context, int resource, List<BaseCommonDataVO> objects, int normalBg, int pressBg,int type) {
        super(context, resource, objects);
        initParams(resource, normalBg, pressBg);
        this.mType=type;
    }

    private void initParams(int resource, int normalBg, int pressBg){
        this.resource = resource;
        this.normalBg = normalBg;
        this.pressBg = pressBg;
        this.selection = -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String s = getItem(position).getName();
        View view;
        ViewHolder holder;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resource,null);
            holder = new ViewHolder();
            holder.tv = (TextView) view.findViewById(R.id.tv_name);
            holder.ivCheck= (ImageView) view.findViewById(R.id.iv_check);
            holder.rlBg= (RelativeLayout) view.findViewById(R.id.rl_bg);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.tv.setText(s);
        if(mType==0){
            holder.ivCheck.setVisibility(View.GONE);
            if(position == selection) {
                holder.rlBg.setBackgroundResource(pressBg);
            } else {
                holder.rlBg.setBackgroundResource(normalBg);
            }
        }else {
            if(!isCheck) {
                if (position == selection) {
                    holder.ivCheck.setVisibility(View.VISIBLE);
                } else {
                    holder.ivCheck.setVisibility(View.GONE);
                }
            }else {
                holder.ivCheck.setVisibility(View.GONE);
            }
        }

        return view;
    }

    public void setPressPostion(int position) {
        this.selection = position;
    }
    class ViewHolder{
        RelativeLayout rlBg;
        TextView tv;
        ImageView ivCheck;
    }
}
