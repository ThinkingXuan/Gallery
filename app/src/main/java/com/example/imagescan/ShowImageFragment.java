package com.example.imagescan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by lenovo on 2016/8/4.
 */

public class ShowImageFragment extends Fragment implements AdapterView.OnItemClickListener {

    List<String> list;
    ChildAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = getActivity().getIntent().getStringArrayListExtra("data");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view;
        GridView mGridView;
        view = inflater.inflate(R.layout.show_image_activity,container,false);

        mGridView = (GridView) view.findViewById(R.id.child_grid);
        adapter = new ChildAdapter(getActivity(),list, mGridView);
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        PreviewFragment fragment = PreviewFragment.newInstance(position);

        if(fragment == null){
            return;
        }else {
            switchFragment(fragment);
        }
    }


    private void switchFragment(Fragment fragment) {
        if(getActivity() == null){
            return;
        }
        System.out.println("--->"+getActivity());
        if(getActivity() instanceof ShowImageActivity){
            ShowImageActivity main = (ShowImageActivity)getActivity();
            main.swichFragment(fragment);
        }

    }
    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
