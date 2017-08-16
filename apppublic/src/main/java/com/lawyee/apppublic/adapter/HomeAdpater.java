package com.lawyee.apppublic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.ui.basiclaw.BasicLawServiceActivity;
import com.lawyee.apppublic.ui.org.JaaidActivity;
import com.lawyee.apppublic.ui.lawyerService.DutyLawyersActivity;
import com.lawyee.apppublic.ui.lawyerService.LawServiceActivity;
import com.lawyee.apppublic.ui.org.JamedOrgActivity;
import com.lawyee.apppublic.ui.org.JanotaOrgActivity;
import com.lawyee.apppublic.ui.org.LegalpublicityActivity;
import com.lawyee.apppublic.vo.InfomationVO;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnLoadImageListener;

import java.util.ArrayList;
import java.util.List;


/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.adapter
 * @Description: ${todo}(首页RecycleView的Adpater)
 * @author: czq
 * @date: 2017/5/15 11:24
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class HomeAdpater extends RecyclerView.Adapter {
    /**
     *3种类型
     */
    public  static final  int BANNER=0;//横幅图片
    public  static final  int CHANNEL=1;//各模块入口
    public  static final  int INFO=2;//最新资讯信息
    private Context mContext;
    private ArrayList mData=new ArrayList<InfomationVO>() ;
    private LayoutInflater mLayoutInflater;
    private  boolean isRefresh=false;
    /**
     * 当前类型
     */
    public int currentType = BANNER;
    public HomeAdpater(Context context,ArrayList list) {
          this.mContext=context;
        this.mData=list;
        mLayoutInflater=LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==BANNER){
            return new BannerViewHolder(mContext,mLayoutInflater.inflate(R.layout.banner_viewpager,null));
        }else if(viewType==CHANNEL){
            return new ChannerViewHolder(mContext,mLayoutInflater.inflate(R.layout.channer_viewpager,null));
        }else if(viewType ==INFO){
            return new InfoViewHolder(mContext,mLayoutInflater.inflate(R.layout.info_item,null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(isRefresh)
        {
            if(getItemViewType(position)==INFO){
                InfoViewHolder seckillViewHolder= (InfoViewHolder) holder;
                seckillViewHolder.setData(mData);
            }
        }else{
            if(getItemViewType(position)==BANNER) {
                BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
                bannerViewHolder.setData();
            }else if(getItemViewType(position)==CHANNEL){
                ChannerViewHolder channerViewHolder= (ChannerViewHolder) holder;
                channerViewHolder.setData();
            } else if(getItemViewType(position)==INFO){
                InfoViewHolder seckillViewHolder= (InfoViewHolder) holder;
                seckillViewHolder.setData(mData);
            }
        }

    }
    /**
     * 根据位置得到类型-系统调用
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case INFO:
                currentType = INFO;
                break;
        }
        return currentType;
    }
    @Override
    public int getItemCount() {
        return 3;
    }
    //横幅Viewholder
    class BannerViewHolder extends  RecyclerView.ViewHolder{
        private Context mContext;
        private Banner banner;
        public BannerViewHolder(Context mContext, View view) {
            super(view);
            this.mContext=mContext;
            this.banner= (Banner) itemView.findViewById(R.id.banner);
        }
        //设置数据
        public void setData() {
            final List<Integer> imgUrls=new ArrayList<>();
            imgUrls.add(R.drawable.ic_banner);
            imgUrls.add(R.drawable.ic_banner2);
            imgUrls.add(R.drawable.ic_banner3);
            //设置循环指示器
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            //设置播放样式
            banner.setBannerAnimation(Transformer.Default);
            banner.setImages(imgUrls, new OnLoadImageListener() {
                @Override
                public void OnLoadImage(ImageView view, Object url) {
                    view.setImageResource((Integer) url);
                }
            });
        }
    }
    //模块入口holder
    class ChannerViewHolder extends  RecyclerView.ViewHolder{
        private Context mContext;
        private GridView gridView;
        private ChannerAdpater channerAdpater;
        public ChannerViewHolder(final Context mContext, View inflate) {
            super(inflate);
            this.mContext=mContext;
            gridView= (GridView) inflate.findViewById(R.id.gv_channel);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String title = (String) adapterView.getItemAtPosition(i);
                    if (!TextUtils.isEmpty(title)) {
                        if (title.equals(mContext.getString(R.string.legal_aid))){//法律援助
                            mContext.startActivity(new Intent(mContext, JaaidActivity.class));

                        }else
                        if (title.equals(mContext.getString(R.string.legal_publicity))) {//法治宣传
                /*            Intent intent = new Intent(mContext, InformationActivity.class);
                            intent.putExtra(InformationActivity.CSTR_EXTRA_INFOTYPENAME_STRARRAY,mContext.getResources().getStringArray(R.array.FZXCInfoName));
                            intent.putExtra(InformationActivity.CSTR_EXTRA_INFOTYPEID_STRARRAY,mContext.getResources().getStringArray(R.array.FZXCInfoID));
                            intent.putExtra(InformationActivity.CSTR_EXTRA_TITLE_STR,title);*/
                            Intent intent = new Intent(mContext, LegalpublicityActivity.class);
                            intent.putExtra(LegalpublicityActivity.CSTR_EXTRA_TITLE_STR,title);
                            mContext.startActivity(intent);
                        }else
                        if(title.equals(mContext.getString(R.string.lawyer_service))) {//律师服务
                            mContext.startActivity(new Intent(mContext, LawServiceActivity.class));
                        }else if (title.equals(mContext.getString(R.string.notary_public))){//公证服务
                            Intent intent = new Intent(mContext, JanotaOrgActivity.class);
                            intent.putExtra(JanotaOrgActivity.JANOTATYPE,JanotaOrgActivity.JANOTATITLEZONE);
                            intent.putExtra(JanotaOrgActivity.CSTR_EXTRA_TITLE_STR,title);
                            mContext.startActivity(intent);
                        /*}else if (title.equals(mContext.getString(R.string.judicial_examination))){//司法考试
                            Intent intent = new Intent(mContext, InformationActivity.class);
                            intent.putExtra("type",0);
                            intent.putExtra("title",title);
                            mContext.startActivity(intent);*/
                        }else if (title.equals(mContext.getString(R.string.judicial_expertise))){//鉴定机构
                            Intent intent = new Intent(mContext, JanotaOrgActivity.class);
                            intent.putExtra(JanotaOrgActivity.JANOTATYPE,JanotaOrgActivity.JANOTATITLEONE);
                            intent.putExtra(JanotaOrgActivity.CSTR_EXTRA_TITLE_STR,title);
                            mContext.startActivity(intent);
                        }else if (title.equals(mContext.getString(R.string.people_mediation))){//人民调解
                          mContext.startActivity(new Intent(mContext,JamedOrgActivity.class));
                        }else if(title.equals(mContext.getString(R.string.duty_lawyers))){
                            mContext.startActivity(new Intent(mContext,DutyLawyersActivity.class));
                        }else if(title.equals(mContext.getString(R.string.basic_legal_service))){
                            mContext.startActivity(new Intent(mContext,BasicLawServiceActivity.class));
                        }

                    }
                }
            });
        }


        public void setData() {
            //得到数据  设置G日的view的适配器
            ArrayList<String> arrayList=new ArrayList<>();
            arrayList.add(mContext.getString(R.string.legal_aid));
            arrayList.add(mContext.getString(R.string.notary_public));
            arrayList.add(mContext.getString(R.string.people_mediation));
            arrayList.add(mContext.getString(R.string.lawyer_service));
            arrayList.add(mContext.getString(R.string.duty_lawyers));
            arrayList.add(mContext.getString(R.string.legal_publicity));
            arrayList.add(mContext.getString(R.string.judicial_expertise));
            arrayList.add(mContext.getString(R.string.basic_legal_service));
            //arrayList.add(mContext.getString(R.string.judicial_examination));
            channerAdpater=new ChannerAdpater(mContext,arrayList);
            gridView.setAdapter(channerAdpater);
        }
    }
    //最新资讯holder
    class InfoViewHolder extends  RecyclerView.ViewHolder{
        public Context mContext;
        public RecyclerView mRvInfo;
        public GridLayoutManager mLayoutManager;
        public InfoAdpater mAdpater;
        public InfoViewHolder(Context context,View itemView) {
            super(itemView);
            this.mContext=context;
            mRvInfo= (RecyclerView) itemView.findViewById(R.id.rv_info);


        }
        public void setData(ArrayList<InfomationVO> list){
            mAdpater = new InfoAdpater(list, mContext);
            mRvInfo.setAdapter(mAdpater);
            mLayoutManager = new GridLayoutManager(mContext,1);
            mRvInfo.setLayoutManager(mLayoutManager);
           mRvInfo.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL)) ;
        }
    }
}
