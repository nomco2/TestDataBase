package arabiannight.tistory.com.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import arabiannight.tistory.com.R;
import arabiannight.tistory.com.adapter.CustomAdapter;
import arabiannight.tistory.com.conf.Constants;
import arabiannight.tistory.com.data.InfoClass;
import arabiannight.tistory.com.util.DLog;

public class TestDataBaseActivity extends Activity {
	
	private static final String TAG = "TestDataBaseActivity";
	private DbOpenHelper_button mDbOpenHelper;
	private Cursor mCursor;
	private InfoClass_btn_data mInfoClass;
	private ArrayList<InfoClass_btn_data> mInfoArray;
	private CustomAdapter mAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setLayout();
        
        // DB Create and Open
        mDbOpenHelper = new DbOpenHelper_button(this,"test");
        mDbOpenHelper.open();

        if(!mDbOpenHelper.is_same_btn_existed("처음 버튼")){
			mDbOpenHelper.insertColumn_button_data("처음 버튼",100 , 200, "1");
			Toast.makeText(getApplicationContext(), "넣음", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(getApplicationContext(), "안넣음", Toast.LENGTH_SHORT).show();

		}


        startManagingCursor(mCursor);
        
        
        mInfoArray = new ArrayList<InfoClass_btn_data>();

        doWhileCursorToArray();

//        for(InfoClass_btn_data i : mInfoArray){
//        	DLog.d(TAG, "ID = " + i._id);
//        	DLog.d(TAG, "btn_name = " + i.btn_name);
//        	DLog.d(TAG, "contact = " + i.contact);
//        	DLog.d(TAG, "email = " + i.email);
//        }

//        mAdapter = new CustomAdapter(this, mInfoArray);
//        mListView.setAdapter(mAdapter);
//        mListView.setOnItemLongClickListener(longClickListener);







    }// oncreate 끝
    
    @Override
    protected void onDestroy() {
    	mDbOpenHelper.close();
    	super.onDestroy();
    }
    
    
    /**
     * ListView의 Item을 롱클릭 할때 호출 ( 선택한 아이템의 DB 컬럼과 Data를 삭제 한다. )
     */
    private OnItemLongClickListener longClickListener = new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int position, long arg3) {
			
			DLog.e(TAG, "position = " + position);
			
			boolean result = mDbOpenHelper.deleteColumn(position + 1);
			DLog.e(TAG, "result = " + result);
			
			if(result){
				mInfoArray.remove(position);
//				mAdapter.setArrayList(mInfoArray);
//				mAdapter.notifyDataSetChanged();
			}else {
				Toast.makeText(getApplicationContext(), "INDEX를 확인해 주세요.", 
						Toast.LENGTH_LONG).show();
			}
			
			return false;
		}
	};
	
	
	/**
	 * DB에서 받아온 값을 ArrayList에 Add
	 */
	private void doWhileCursorToArray(){
		
		mCursor = null;
		mCursor = mDbOpenHelper.getAllColumns_btn_data();
		DLog.e(TAG, "COUNT = " + mCursor.getCount());
		
		while (mCursor.moveToNext()) {

			mInfoClass = new InfoClass_btn_data(

					mCursor.getInt(mCursor.getColumnIndex("_id")),
					mCursor.getString(mCursor.getColumnIndex("btn_name")),
					mCursor.getInt(mCursor.getColumnIndex("x")),
					mCursor.getInt(mCursor.getColumnIndex("y")),
					mCursor.getString(mCursor.getColumnIndex("coding"))



			);
			Log.i("class", mInfoClass +"");
			mInfoArray.add(mInfoClass);

		}

		mCursor.close();
	}
    
    
	/**
	 * OnClick Button
	 * @param v
	 */
    public void onClick(View v){
    	switch (v.getId()) {
		case R.id.btn_add:
			mDbOpenHelper.insertColumn
					(
					mEditTexts[Constants.NAME].getText().toString().trim(),
					mEditTexts[Constants.CONTACT].getText().toString().trim(),
					mEditTexts[Constants.EMAIL].getText().toString().trim()
					);
			
			mInfoArray.clear();
			
			doWhileCursorToArray();
			
//			mAdapter.setArrayList(mInfoArray);
//			mAdapter.notifyDataSetChanged();
			
			mCursor.close();
			
			break;

		default:
			break;
		}
    }
    
    /*
     * Layout
     */
    private EditText[] mEditTexts;
    private ListView mListView;
    
    private void setLayout(){
    	mEditTexts = new EditText[]{
    			(EditText)findViewById(R.id.et_name),
    			(EditText)findViewById(R.id.et_contact),
    			(EditText)findViewById(R.id.et_email)
    	};
    	
    	mListView = (ListView) findViewById(R.id.lv_list);
    }
}




