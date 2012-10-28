package my.experimental.rowRecorder;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class RowRecorderActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	ListAdapter mListAdapter;
	DBHelper dbHelper;
	final String LOG_TAG = "myLogs";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button addBtn = (Button) findViewById(R.id.btn_add);
        addBtn.setOnClickListener(this);
		if (mListAdapter == null) {
			mListAdapter = new ListAdapter(this,0);
			ListView list = (ListView) findViewById(R.id.text_list);
			list.setAdapter(mListAdapter);
		}
		dbHelper = new DBHelper(this);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
	 	Log.d(LOG_TAG, "--- Rows in mytable: ---");
	 	// ������ ������ ���� ������ �� ������� mytable, �������� Cursor 
	 	Cursor c = db.query("mytable", null, null, null, null, null, null); 

	 	// ������ ������� ������� �� ������ ������ �������
	 	// ���� � ������� ��� �����, �������� false
	 	if (c.moveToFirst()) {

        // ���������� ������ �������� �� ����� � �������
	        int idColIndex = c.getColumnIndex("id");
	        int nameColIndex = c.getColumnIndex("name");
	        int emailColIndex = c.getColumnIndex("email");

	        do {
	          // �������� �������� �� ������� �������� � ����� ��� � ���
	          Log.d(LOG_TAG,
	              "ID = " + c.getInt(idColIndex) + 
	              ", name = " + c.getString(nameColIndex) + 
	              ", email = " + c.getString(emailColIndex));
	          mListAdapter.add(c.getString(emailColIndex));
	          // ������� �� ��������� ������ 
	          // � ���� ��������� ��� (������� - ���������), �� false - ������� �� �����
	        } while (c.moveToNext());
	 	} else
	 		Log.d(LOG_TAG, "0 rows");
	 	dbHelper.close();
    }
    
    public void onClick(View v) {
    	ContentValues cv = new ContentValues();
    	SQLiteDatabase db = dbHelper.getWritableDatabase();
    	if (mListAdapter != null) {
    		TextView additem = (TextView) findViewById(R.id.additem);
    		mListAdapter.add(additem.getText().toString());
    		Log.d(LOG_TAG, "--- Insert in mytable: ---");
    		// ���������� ������ ��� ������� � ���� ���: ������������ ������� - ��������
          
    		cv.put("name", "zzzz");
    		cv.put("email", additem.getText().toString());
    		// ��������� ������ � �������� �� ID
    		long rowID = db.insert("mytable", null, cv);
    		Log.d(LOG_TAG, "row inserted, ID = " + rowID);
        	additem.setText("");
    	}
    	dbHelper.close();
    }
    
    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
          // ����������� �����������
          super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
          Log.d(LOG_TAG, "--- onCreate database ---");
          db.execSQL("create table mytable ("
              + "id integer primary key autoincrement," 
              + "name text,"
              + "email text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}