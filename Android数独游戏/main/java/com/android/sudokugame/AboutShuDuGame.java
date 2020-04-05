package com.android.sudokugame;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutShuDuGame extends Activity {
	private TextView txtMessage=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_shu_du_game);
		txtMessage=(TextView) findViewById(R.id.txtMessage);
		String msg="    数独是源自18世纪瑞士的一种数学游戏。玩家需要根据9×9盘面上的已知数字，推理出所有剩余空格的数字，并满足每一行、每一列、每一个粗线宫（3*3）内的数字均含1-9，不重复。";
		txtMessage.setText(msg);
	}

}
