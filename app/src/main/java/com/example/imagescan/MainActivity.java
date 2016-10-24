package com.example.imagescan;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;



public class MainActivity extends Activity {
	private HashMap<String, List<String>> mGruopMap = new HashMap<String, List<String>>();
	private List<ImageBean> list = new ArrayList<ImageBean>();
	private final static int SCAN_OK = 1;
	private ProgressDialog mProgressDialog;
	private GroupAdapter adapter;
	private GridView mGroupGridView;
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case SCAN_OK:

				mProgressDialog.dismiss();
				adapter = new GroupAdapter(MainActivity.this, list = subGroupOfImage(mGruopMap), mGroupGridView);
				mGroupGridView.setAdapter(adapter);
				break;
			}
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mGroupGridView = (GridView) findViewById(R.id.main_grid);
		
		getImages();
		
		mGroupGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				List<String> childList = mGruopMap.get(list.get(position).getFolderName());
				
				Intent mIntent = new Intent(MainActivity.this, ShowImageActivity.class);
				mIntent.putStringArrayListExtra("data", (ArrayList<String>)childList);
				startActivity(mIntent);
				
			}
		});
	}

	private void getImages() {
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			Toast.makeText(this, "我发现没存卡", Toast.LENGTH_SHORT).show();
			return;
		}
		mProgressDialog = ProgressDialog.show(this, null, "正在加载...");
		new Thread(new Runnable() {

			@Override
			public void run() {
				Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver mContentResolver = MainActivity.this.getContentResolver();
				Cursor mCursor = mContentResolver.query(mImageUri, null,
						MediaStore.Images.Media.MIME_TYPE + "=? or "
								+ MediaStore.Images.Media.MIME_TYPE + "=?",
						new String[] { "image/jpeg", "image/png" }, MediaStore.Images.Media.DATE_MODIFIED);
				
				while (mCursor.moveToNext()) {

                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));

                    if (path != null) {
                        String parentName = new File(path).getParentFile().getName();

                        if (!mGruopMap.containsKey(parentName)) {
                            List<String> chileList = new ArrayList<String>();
                            chileList.add(path);
                            mGruopMap.put(parentName, chileList);
                        } else {
                            mGruopMap.get(parentName).add(path);
                        }
                    }
                }
				mCursor.close();
				mHandler.sendEmptyMessage(SCAN_OK);
			}
		}).start();
	}

	private List<ImageBean> subGroupOfImage(HashMap<String, List<String>> mGruopMap){
		if(mGruopMap.size() == 0){
			return null;
		}
		List<ImageBean> list = new ArrayList<ImageBean>();
		
		Iterator<Map.Entry<String, List<String>>> it = mGruopMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, List<String>> entry = it.next();
			ImageBean mImageBean = new ImageBean();
			String key = entry.getKey();
			List<String> value = entry.getValue();
			
			mImageBean.setFolderName(key);
			mImageBean.setImageCounts(value.size());
			mImageBean.setTopImagePath(value.get(0));
			
			list.add(mImageBean);
		}
		return list;
	}
}
