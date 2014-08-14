package com.huan.examdemo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import cn.trinea.android.common.util.HttpUtils;
import cn.trinea.android.common.util.JSONUtils;

import com.huan.examdemo.data.ExamData;
import com.huan.examdemo.db.DB;
import com.huan.examdemo.db.DBUtils;

public class ExamActivity extends Activity {
	private TextView tvTitle;
	private RadioGroup radioGroup;
	private RadioButton radioA, radioB, radioC, radioD;
	private Button btnNext;
	private DB db;
	private SQLiteDatabase dbWrite, dbRead;
	private ExamData examData;//没用到这个
	private Cursor c;
	private static final String url = "http://2.bjtuweb.sinaapp.com/getExam.php";
	private int current_number = 2;  //当前题号
	private Handler handlerQuery = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 0x123) {
				//解析Json
				String jsonString = (String) msg.obj;
				System.out.println(jsonString);
				JSONArray jsonArray = new JSONArray();
				try {
					jsonArray = new JSONArray(jsonString);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (int i = 0; i < 10; i++) {
					JSONObject jsonObject = new JSONObject();
					try {
						jsonObject = jsonArray.getJSONObject(i);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					int get_Id = JSONUtils.getInt(jsonObject, "_id", -1);
					//System.out.println(">>>>>>>>>>" + get_Id);
					String getTitle = JSONUtils.getString(jsonObject, "title",
							"");
					String getRadioA = JSONUtils.getString(jsonObject,
							"radioA", "");
					String getRadioB = JSONUtils.getString(jsonObject,
							"radioB", "");
					String getRadioC = JSONUtils.getString(jsonObject,
							"radioC", "");
					String getRadioD = JSONUtils.getString(jsonObject,
							"radioD", "");
					String getAnswer = JSONUtils.getString(jsonObject,
							"answer", "");
					//解析后添加到数据库中
					ContentValues cv = new ContentValues();
					cv.put(DB.EXAM_COLUMN_ID, get_Id);
					cv.put(DB.EXAM_COLUMN_TITLE, getTitle);
					cv.put(DB.EXAM_COLUMN_RADIOA, getRadioA);
					cv.put(DB.EXAM_COLUMN_RADIOB, getRadioB);
					cv.put(DB.EXAM_COLUMN_RADIOC, getRadioC);
					cv.put(DB.EXAM_COLUMN_RADIOD, getRadioD);
					cv.put(DB.EXAM_COLUMN_ANSWER, getAnswer);
					dbWrite.insert(DB.EXAM_TABLE_NAME, null, cv);
				}

				//全局Cursor变量
				c = dbRead.query(DB.EXAM_TABLE_NAME, null, null, null, null,
						null, null);

				//设置第一道题的显示
				c.moveToNext();
				String tvtitle, tvradioA, tvradioB, tvradioC, tvradioD, tvanswer;
				tvtitle = c.getString(c.getColumnIndex(DB.EXAM_COLUMN_TITLE));
				tvradioA = c.getString(c.getColumnIndex(DB.EXAM_COLUMN_RADIOA));
				tvradioB = c.getString(c.getColumnIndex(DB.EXAM_COLUMN_RADIOB));
				tvradioC = c.getString(c.getColumnIndex(DB.EXAM_COLUMN_RADIOC));
				tvradioD = c.getString(c.getColumnIndex(DB.EXAM_COLUMN_RADIOD));
				tvanswer = c.getString(c.getColumnIndex(DB.EXAM_COLUMN_ANSWER));

				tvTitle.setText("1、" + tvtitle);
				radioA.setText("A、" + tvradioA);
				radioB.setText("B、" + tvradioB);
				radioC.setText("C、" + tvradioC);
				radioD.setText("D、" + tvradioD);
			}
		}
	};

	OnClickListener btnNextListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			if (c.moveToNext()) {
				String tvtitle, tvradioA, tvradioB, tvradioC, tvradioD, tvanswer;
				tvtitle = c.getString(c.getColumnIndex(DB.EXAM_COLUMN_TITLE));
				tvradioA = c.getString(c.getColumnIndex(DB.EXAM_COLUMN_RADIOA));
				tvradioB = c.getString(c.getColumnIndex(DB.EXAM_COLUMN_RADIOB));
				tvradioC = c.getString(c.getColumnIndex(DB.EXAM_COLUMN_RADIOC));
				tvradioD = c.getString(c.getColumnIndex(DB.EXAM_COLUMN_RADIOD));
				tvanswer = c.getString(c.getColumnIndex(DB.EXAM_COLUMN_ANSWER));

				tvTitle.setText(current_number++ + "、" + tvtitle);
				radioA.setText("A、" + tvradioA);
				radioB.setText("B、" + tvradioB);
				radioC.setText("C、" + tvradioC);
				radioD.setText("D、" + tvradioD);
				//清除radioGroup的上一次选中状态
				radioGroup.clearCheck();
			} else {
				// Toast.makeText(ExamActivity.this, "已经是最后一题了", 1).show();
				AlertDialog.Builder builder = new AlertDialog.Builder(
						ExamActivity.this);
				builder.setTitle("提示")
						.setMessage("已经是最后一题，是否提交")
						.setPositiveButton("是",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										Judge();
									}
								});
				builder.setNegativeButton("否", null);
				builder.create().show();
			}
		}
	};

	//10道题做完时判题
	private void Judge() {
		Cursor cursor = dbRead.query(DB.DBDONE_TABLE_NAME, null, null, null,
				null, null, null);
		int rightAnswer = 0;
		while (cursor.moveToNext()) {
			String _answer = cursor.getString(cursor
					.getColumnIndex(DB.DBDONE_COLUMN_ANSWER));
			String _userAnswer = cursor.getString(cursor
					.getColumnIndex(DB.DBDONE_COLUMN_USER_ANSWER));
			if (_answer.equals(_userAnswer))
				rightAnswer++;
		}
		Toast.makeText(ExamActivity.this, "您答对了" + rightAnswer + "道题",
				Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exam);

		tvTitle = (TextView) findViewById(R.id.tvTitle);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		btnNext = (Button) findViewById(R.id.btnNext);
		radioA = (RadioButton) findViewById(R.id.radioA);
		radioB = (RadioButton) findViewById(R.id.radioB);
		radioC = (RadioButton) findViewById(R.id.radioC);
		radioD = (RadioButton) findViewById(R.id.radioD);
		btnNext.setOnClickListener(btnNextListener);

		db = new DB(ExamActivity.this);
		dbRead = db.getReadableDatabase();
		dbWrite = db.getWritableDatabase();

		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//获取网络Json数据
				String jsonData = HttpUtils.httpGetString(url);
				// System.out.println("jsonData>>>>>>>>>>" + jsonData);

				Message msg = new Message();
				msg.obj = jsonData;
				msg.what = 0x123;
				handlerQuery.sendMessage(msg);

			}
		}).start();

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				System.out.println("checkId>>>>>>>>>" + checkedId);
				if(checkedId!=-1){//当radioGroup.clearCheck()触发时，checkedId==-1
					int selectId = group.getCheckedRadioButtonId();
					RadioButton selectRB = (RadioButton) findViewById(selectId);
	
					// c.moveToNext();
					int _id = c.getInt(c.getColumnIndex(DB.EXAM_COLUMN_ID));
					String answer = c.getString(c
							.getColumnIndex(DB.EXAM_COLUMN_ANSWER));
					String useranswer = (String) selectRB.getTag();
	
					System.out.println(useranswer + "被选中了");
	
					ContentValues cv = new ContentValues();
					cv.put(DB.DBDONE_COLUMN_ID, _id);
					cv.put(DB.DBDONE_COLUMN_ANSWER, answer);
					cv.put(DB.DBDONE_COLUMN_USER_ANSWER, useranswer);
	
					//记录已做题到 DB.DBDONE_TABLE_NAME 表中
					Cursor cursor = dbRead.query(DB.DBDONE_TABLE_NAME, null,
							"_id=?", new String[] { _id + "" }, null, null, null);
					if (cursor.moveToNext()) {
						//更换选项时
						dbWrite.update(DB.DBDONE_TABLE_NAME, cv, "_id=?",
								new String[] { _id + "" });
						System.out.println("更新成功");
					} else {
						//第一次选中时
						dbWrite.insert(DB.DBDONE_TABLE_NAME, null, cv);
						System.out.println("插入成功");
					}
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//清空数据表
		dbWrite.execSQL("DELETE FROM " + DB.EXAM_TABLE_NAME);
		dbWrite.execSQL("DELETE FROM " + DB.DBDONE_TABLE_NAME);
	}

	/*测试用数据库，没用了
	private void createInitDB() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 10; i++) {
			ContentValues cv = new ContentValues();
			cv.put(DB.EXAM_COLUMN_TITLE, "第 " + i + " 题");
			cv.put(DB.EXAM_COLUMN_RADIOA, "我是A选项");
			cv.put(DB.EXAM_COLUMN_RADIOB, "我是B选项");
			cv.put(DB.EXAM_COLUMN_RADIOC, "我是C选项");
			cv.put(DB.EXAM_COLUMN_RADIOD, "我是D选项");
			cv.put(DB.EXAM_COLUMN_ANSWER, "A");
			dbWrite.insert(DB.EXAM_TABLE_NAME, null, cv);
		}
	}
	*/
}
