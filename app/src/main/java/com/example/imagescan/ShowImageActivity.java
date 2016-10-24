package com.example.imagescan;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public class ShowImageActivity extends FragmentActivity  {

    private static ShowImageActivity instance;
    Fragment mContent;

//	private GridView mGridView;
//	private List<String> list;
//	private ChildAdapter adapter;

    public static ShowImageActivity getInstance(){
        if(instance == null){
            instance = new ShowImageActivity();
        }
        return instance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.show_image_activity);

//		mGridView = (GridView) findViewById(R.id.child_grid);
//		list = getIntent().getStringArrayListExtra("data");
//
//		adapter = new ChildAdapter(this, list, mGridView);
//		mGridView.setAdapter(adapter);

        if(savedInstanceState!=null)
        {
            mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
        }

        if(mContent == null){
            mContent = new ShowImageFragment();
        }
        setContentView(R.layout.show_activity);

        //FragmentTransaction为fragment的实务管理器，专门用来管理Fragment,通过FragmentManager来获取
        //相应实例，能够进行Fragment的增删改查 ，最后需要commit来提交事物管理
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.Content1, mContent);
        transaction.commit();                //别忘commit

    }
//  后退返回键
//	@Override
//	public void onBackPressed() {
//		Toast.makeText(this, "选中 " + adapter.getSelectItems().size() + " item", Toast.LENGTH_LONG).show();
//		super.onBackPressed();
//	}

    //Activity被销毁之后,保存一个必要的东西
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "mContent", mContent);
    }

    public void swichFragment(Fragment fragment1){
        mContent = fragment1;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.Content1,mContent)
                .addToBackStack(null)           //把fragment添加到BackStack
                .commit();
    }


    @Override
    public boolean isDestroyed() {

        Log.d("destroy","main");
        return super.isDestroyed();
    }


}


