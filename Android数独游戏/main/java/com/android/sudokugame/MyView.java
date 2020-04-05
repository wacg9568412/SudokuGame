package com.android.sudokugame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MyView extends View {

	private static final String VIEW_STATE = "viewState";
	private float width;
	private float height;
	private Paint myPaint = new Paint();
	private Game gameContext;
	private Intent difficultyIntent;
	private float xNum;
	private float yNum;
	public int row;
	public int column;
	public char number;
	public char[] str;
	public static int[] unChange_Num;
	public boolean isover=false;

	public MyView(Context context) {
		super(context);
		gameContext = (Game) context;
		str = getNumber();
		setFocusable(true);
		setFocusableInTouchMode(true);
	}
	public MyView(Context context,String strContinue) {
		super(context);
		gameContext = (Game) context;
		str=strContinue.toCharArray();
		setFocusable(true);
		setFocusableInTouchMode(true);
	}
	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable p=super.onSaveInstanceState();
		Bundle bundle=new Bundle();
		bundle.putParcelable(VIEW_STATE, p);
		return bundle;
	}
	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		Bundle bundle=(Bundle)state;
		super.onRestoreInstanceState(bundle.getParcelable(VIEW_STATE));
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.WHITE);
		myPaint.setColor(getResources().getColor(R.color.greyColor));
		for (int i = 0; i < 9; i++) {
			canvas.drawLine(0, (i + 1) * height, getWidth(), (i + 1) * height,
					myPaint);
			canvas.drawLine((i + 1) * width, 0, (i + 1) * width, getHeight(),
					myPaint);
		}
		myPaint.setStyle(Style.FILL);
		myPaint.setTextScaleX(width / height);
		myPaint.setTextAlign(Align.CENTER);
		myPaint.setTextSize(height * 0.75f);
		FontMetrics fm = myPaint.getFontMetrics();
		float x = width / 2;
		float y = height / 2 - (fm.ascent + fm.descent) / 2;
		int count = 0;
		boolean isChangeColor=true;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				for (int z = 0; z < unChange_Num.length; z++) {
					if (unChange_Num[z]==count) {
						isChangeColor=false;
						break;
					}
				}
				if (isChangeColor) {
					myPaint.setColor(getResources().getColor(R.color.glcolor));
				}
				else {
					myPaint.setColor(getResources().getColor(R.color.greyColor));
				}
				canvas.drawText((str[count] + "").equals("0") ? " "
								: str[count] + "", x + j * width, y + i * height,
						myPaint);
				count++;
				isChangeColor=true;
			}
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		width = w / 9f;
		height = h / 9f;
	}

	protected char[] getNumber() {
		String[] numbers = new String[] {
				"187362549254719638693458271321685497965174382478923156742836915519247863836591724",
				"587246319214935678396817542642381795751694283938752461865173924429568137173429856",
				"649527831158463729732189465461298573987345612523716948315874296294651387876932154",
				"145328769367495812982671345538217496619843257724569138453786921271954683896132574",
				"978241635341865972256379841714958263539624187862137459493582716627413598185796324",
				"152839647743261589896745321267513894514986732938472165671324958485697213329158476",
				"614537892935128674728649351583496217271385469469271538147863925856912743392754186",
				"762541389814793526539826471348652917196478235257139648473915862625387194981264753",
				"785412639643579218291368547367251984924783156158694723816935472572846391439127865",
				"435217968689345271172986345768432519924561783351879426243658197516794832897123654",
				"271685439354719628896423517937854261125367984648291375463578192712946853589132746" };
		int number = (int) (Math.random() * 11);
		// //////////////////////根据难易程度选择遮盖数目////////////////////////////////
		char[] myChar = numbers[number].toCharArray();
		difficultyIntent = gameContext.getIntent();
		int difficulty = difficultyIntent.getExtras().getInt("difficulty");
		int difficultyNum=0;
		switch (difficulty) {
			case 0:
				difficultyNum=20;
				break;
			case 1:
				difficultyNum=30;
				break;
			case 2:
				difficultyNum=40;
				break;
			case Game.DIFContinue:
				myChar=gameContext.getPreferences(Game.MODE_PRIVATE)
						.getString("strContinue",String.valueOf(myChar)).toCharArray();
				return myChar;
			default:
				break;
		}
		for (int i = 0; i < difficultyNum; i++) {
			int num = (int) (Math.random() * 81);
			myChar[num] = '0';
		}
		//获取不能改变的数的下标
		int unChangeLength=0;
		int count=0;
		for (char c : myChar) {
			if (c!='0') {
				unChangeLength++;
			}
		}
		unChange_Num=new int[unChangeLength];
		for (int i = 0; i < myChar.length; i++) {
			if (myChar[i]!='0') {
				unChange_Num[count++]=i;
			}
		}
		return myChar;
	}

	public void updateNumber(char[] str,int x,int y,char number) {
		if (number!='0') {
			str[x*9+y]=number;
		}
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// ////////////////////////////////////////////////
		xNum=event.getX();
		yNum=event.getY();
		row=(int)(yNum/height);
		column=(int)(xNum/width);
		for (int i = 0; i < unChange_Num.length; i++) {
			if (unChange_Num[i]==row*9+column) {
				startAnimation(AnimationUtils.loadAnimation(gameContext,
						R.anim.shake));
				return false;
			}
		}
		LayoutInflater myInflater = LayoutInflater.from(gameContext);
		View numView = myInflater.inflate(R.layout.number, null);
		final AlertDialog numAlertDialog = new AlertDialog.Builder(gameContext)
				.setTitle("请选择数字：").create();
		numBtnListener(numAlertDialog, numView,getUnvisibleNum(row,column));
		numAlertDialog.setView(numView);
		if(!isover){
			numAlertDialog.show();
			numAlertDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					updateNumber(str, row, column, number);
					//重绘九宫格
					invalidate();
					number='0';
					if (!(String.valueOf(str).contains("0"))) {
						isover=true;
						Game.stopClock();
						Game.recordTime+=GetTime.recordLongTime;
						Game.recordContinueTime=GetTime.transmitLongToString(Game.recordTime);
						AlertDialog overDialog=new AlertDialog.Builder(gameContext).create();
						overDialog.setTitle("系统提示：");
						if (GetTime.recordLongTime==0) {
							overDialog.setMessage("挑战成功!\n"+"共用时间："+Game.usedTime);
						}
						else {
							overDialog.setMessage("挑战成功!\n"+"共用时间："+Game.recordContinueTime);
						}
						overDialog.setButton(DialogInterface.BUTTON_POSITIVE, "再来一局", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								gameContext.onPause();
								gameContext.onCreate(null);
							}
						});
						overDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "退出",new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								gameContext.finish();
								unChange_Num=null;
							}
						});
						overDialog.show();
					}
					else {
						isover=false;
					}
				}
			});
		}
		return super.onTouchEvent(event);
	}

	// ///////////numAlertDialog Button// Listener/////////////////////
	public void numBtnListener(AlertDialog alertDialog, View numView,String unVisibleStr) {
		final AlertDialog numAlertDialog = alertDialog;
		final Button one = (Button) numView.findViewById(R.id.one);
		final Button two = (Button) numView.findViewById(R.id.two);
		final Button three = (Button) numView.findViewById(R.id.three);
		final Button four = (Button) numView.findViewById(R.id.four);
		final Button five = (Button) numView.findViewById(R.id.five);
		final Button six = (Button) numView.findViewById(R.id.six);
		final Button seven = (Button) numView.findViewById(R.id.seven);
		final Button eight = (Button) numView.findViewById(R.id.eight);
		final Button nine = (Button) numView.findViewById(R.id.nine);
		final Button[] btns=new Button[]{one,two,three,four,five,six,seven,eight,nine};
		if (ShuDuGameSettings.isChbHint) {
			for (Button button : btns) {
				isBtnVisible(button, unVisibleStr);
			}
		}
		one.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				number=one.getText().toString().charAt(0);
				numAlertDialog.cancel();
			}
		});

		two.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				number=two.getText().toString().charAt(0);
				numAlertDialog.cancel();
			}
		});
		three.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				number=three.getText().toString().charAt(0);
				numAlertDialog.cancel();
			}
		});
		four.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				number=four.getText().toString().charAt(0);
				numAlertDialog.cancel();
			}
		});
		five.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				number=five.getText().toString().charAt(0);
				numAlertDialog.cancel();
			}
		});
		six.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				number=six.getText().toString().charAt(0);
				numAlertDialog.cancel();
			}
		});
		seven.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				number=seven.getText().toString().charAt(0);
				numAlertDialog.cancel();
			}
		});
		eight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				number=eight.getText().toString().charAt(0);
				numAlertDialog.cancel();
			}
		});
		nine.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				number=nine.getText().toString().charAt(0);
				numAlertDialog.cancel();
			}
		});
	}
	public String getUnvisibleNum(int row,int column) {
		char[][] positionNum=new char[9][9];
		int count=0;
		for (int i = 0; i <9; i++) {
			for (int j = 0; j < 9; j++) {
				positionNum[i][j]=str[count++];
			}
		}
		String isContain="";
		for (int i = 0; i < 9; i++) {
			if (positionNum[row][i]!='0') {
				isContain+=positionNum[row][i]+"";
			}
			if (positionNum[i][column]!='0') {
				isContain+=positionNum[i][column]+"";
			}
		}
		int blockx=row/3*3;
		int blocky=column/3*3;
		for (int i = blockx; i < blockx+3; i++) {
			for (int j = blocky; j < blocky+3; j++) {
				if (positionNum[i][j]!='0')
					isContain+=positionNum[i][j];
			}
		}
		return isContain;
	}
	// /////////////////////////////////////////////////////////////////////////////////////
	public void isBtnVisible(Button btn,String str) {
		String btnStr=btn.getText().toString();
		if (str.contains(btnStr)) {
			btn.setEnabled(false);
		}
	}
}
