package com.example.imagescan;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.util.List;

/**
 * Created by lenovo on 2016/8/4.
 */

public class pagerAdapter extends PagerAdapter{

    private List<ZoomImageView> mImageViewList;
    private List<String> path;
    private Context mContext;
    private Toolbar mToolbar;
    private Boolean onclick_state = false;           //imageView点击的标识符

    public pagerAdapter(List<ZoomImageView> list, List<String> path, Context context,Toolbar toolbar) {
        this.mImageViewList = list;
        this.path = path;
        this.mContext = context;
        this.mToolbar = toolbar;
    }

    @Override
    public int getCount() {
        return mImageViewList.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {



        final ZoomImageView iv = mImageViewList.get(position);
            iv.setTag(path.get(position));
            iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(path.get(position), new NativeImageLoader.NativeImageCallBack() {
                @Override
                public void onImageLoader(Bitmap bitmap, String path) {
                    ImageView mImageView = (ImageView) iv.findViewWithTag(path);
                    if(bitmap != null && mImageView != null){
                        mImageView.setImageBitmap(bitmap);
                    }
                }
            });

            if(bitmap != null){
                iv.setImageBitmap(bitmap);
            }else{
                Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.friends_sends_pictures_no);
                iv.setImageBitmap(bmp);
            }

            //为每页添加一个点击事件,控制ToolBar
            View view = mImageViewList.get(position);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!onclick_state) {
                            mToolbar.setVisibility(View.VISIBLE);
                            onclick_state = true;
                        }else{
                            mToolbar.setVisibility(View.INVISIBLE);
                            onclick_state = false;
                        }
                    }
                });

        container.addView(mImageViewList.get(position));
        return mImageViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        if(container != null) {
            container.removeView(mImageViewList.get(position));
            ((ViewGroup) container).removeView((View) object);
            object = null;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
