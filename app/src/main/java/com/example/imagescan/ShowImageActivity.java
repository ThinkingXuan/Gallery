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

        //FragmentTransactionΪfragment��ʵ���������ר����������Fragment,ͨ��FragmentManager����ȡ
        //��Ӧʵ�����ܹ�����Fragment����ɾ�Ĳ� �������Ҫcommit���ύ�������
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.Content1, mContent);
        transaction.commit();                //����commit

    }
//  ���˷��ؼ�
//	@Override
//	public void onBackPressed() {
//		Toast.makeText(this, "ѡ�� " + adapter.getSelectItems().size() + " item", Toast.LENGTH_LONG).show();
//		super.onBackPressed();
//	}

    //Activity������֮��,����һ����Ҫ�Ķ���
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
                .addToBackStack(null)           //��fragment��ӵ�BackStack
                .commit();
    }


    @Override
    public boolean isDestroyed() {

        Log.d("destroy","main");
        return super.isDestroyed();
    }


}


