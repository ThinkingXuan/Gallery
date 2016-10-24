package com.example.imagescan;



import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by lenovo on 2016/8/4.
 */

public class PreviewFragment extends Fragment {

    private List<String> list;
    private List<ZoomImageView> listview;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private pagerAdapter mAdapter;
    private Button delete;
    private  int position;
    private static final String TAG ="PreviewFragment";

    public PreviewFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //拿到上个Activity的数据
        list = getActivity().getIntent().getStringArrayListExtra("data");
        //初始化存放ZoomImageView的集合
        listview = new ArrayList<ZoomImageView>(list.size());
    }

    public static PreviewFragment newInstance(int text) {
        PreviewFragment fragment = new PreviewFragment();
        Bundle args = new Bundle();
        args.putInt("param",text);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.preview,container,false);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        delete = (Button) view.findViewById(R.id.delete);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);


        if (getArguments() != null) {
            position = getArguments().getInt("param");
            Log.d("main",position+"");
        }
        initViewPager();
        return view;
    }

    ZoomImageView iv;

    private void initViewPager(){

        LayoutInflater infaltero = getActivity().getLayoutInflater();

        for (int i = 0; i < list.size(); i++) {
           // final ImageView iv  = (ImageView) infaltero.inflate(R.layout.image,null).findViewById(R.id.image_one);
            iv = new ZoomImageView(getActivity(),null);
          //  iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            listview.add(iv);
        }

        mAdapter = new pagerAdapter(listview,list,getActivity(),mToolbar);


        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(position);  //在加载适配器后调用

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  //listview.remove(position);
                mViewPager.removeView(listview.get(position));

                mAdapter.destroyItem(null,position,listview.get(position));
                mViewPager.setCurrentItem(position+1);
                mAdapter.notifyDataSetChanged();
                position += 1;
            }
        });

    }

}
