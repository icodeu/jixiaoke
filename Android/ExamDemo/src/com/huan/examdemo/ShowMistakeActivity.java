package com.huan.examdemo;

import com.huan.examdemo.data.ExamData;
import com.huan.examdemo.db.DB;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ShowMistakeActivity extends Activity {
	private TextView tvTitle, tvIndicator, tvRightAnswer;
	private RadioGroup radioGroup;
	private RadioButton radioA, radioB, radioC, radioD;
	private Button btnNext, btnPre;
	private DB db;
	private SQLiteDatabase dbWrite, dbRead;
	private Cursor c;
	private int current_number = 2; // 当前题号
	private int TOTAL_ERROR_NUM; // 每次总共错题数

	OnClickListener btnNextListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			boolean last_flag = true;
			while (c.moveToNext()){
				last_flag = false;
				String _answer = c.getString(c
						.getColumnIndex(DB.EXAM_COLUMN_ANSWER));
				String _userAnswer = c.getString(c
						.getColumnIndex(DB.EXAM_COLUMN_USER_ANSWER));
				if (!_answer.equals(_userAnswer)){//发现一道错题
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
					
					tvRightAnswer.setText("正确答案 ： " +  tvanswer);
					tvIndicator.setText(current_number-1 + "/" + TOTAL_ERROR_NUM);
					
					//清除radioGroup的上一次选中状态
					radioGroup.clearCheck();
					
					break;
				} 
			}
			if(last_flag){
				Toast.makeText(ShowMistakeActivity.this, "已经是最后一道错题", Toast.LENGTH_LONG).show();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_mistake);

		tvTitle = (TextView) findViewById(R.id.errorTitle);
		tvIndicator = (TextView) findViewById(R.id.errorIndicator);
		tvRightAnswer = (TextView) findViewById(R.id.tvRightAnswer);
		radioGroup = (RadioGroup) findViewById(R.id.errorradioGroup);
		btnNext = (Button) findViewById(R.id.errorbtnNext);
		btnPre = (Button) findViewById(R.id.errorbtnPre);
		radioA = (RadioButton) findViewById(R.id.errorradioA);
		radioB = (RadioButton) findViewById(R.id.errorradioB);
		radioC = (RadioButton) findViewById(R.id.errorradioC);
		radioD = (RadioButton) findViewById(R.id.errorradioD);
		btnNext.setOnClickListener(btnNextListener);

		db = new DB(ShowMistakeActivity.this);
		dbRead = db.getReadableDatabase();
		dbWrite = db.getWritableDatabase();

		//得到错题总数
		c = dbRead.query(DB.EXAM_TABLE_NAME, null, null, null, null, null, null);
		TOTAL_ERROR_NUM = 0;
		while (c.moveToNext()){
			String _answer = c.getString(c
					.getColumnIndex(DB.EXAM_COLUMN_ANSWER));
			String _userAnswer = c.getString(c
					.getColumnIndex(DB.EXAM_COLUMN_USER_ANSWER));
			if (!_answer.equals(_userAnswer)){
				TOTAL_ERROR_NUM++;
			}
		}
		
		//显示第一道错题
		c = dbRead.query(DB.EXAM_TABLE_NAME, null, null, null, null, null, null);
		while (c.moveToNext()){
			String _answer = c.getString(c
					.getColumnIndex(DB.EXAM_COLUMN_ANSWER));
			String _userAnswer = c.getString(c
					.getColumnIndex(DB.EXAM_COLUMN_USER_ANSWER));
			if (!_answer.equals(_userAnswer)){//发现第一道错题
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
				
				tvRightAnswer.setText("正确答案 ： " +  tvanswer);
				tvIndicator.setText("1/" + TOTAL_ERROR_NUM);
				
				break;
			}
		}
	}
}
