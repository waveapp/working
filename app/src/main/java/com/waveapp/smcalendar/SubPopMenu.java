package com.waveapp.smcalendar;


import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.waveapp.smcalendar.handler.MenuHandler;
import com.waveapp.smcalendar.util.ViewUtil;

public class SubPopMenu extends PopView{
	private final Context context;
	private final LayoutInflater inflater;
	private final View root;
	private final TextView mLunarCal;
	// 추가 (공지,전체스케줄, 전체기념일.주간일정, 설정 )
	private final TextView mNotice;
	private final TextView mTotalSchedule;
	private final TextView mTotalSpecialDay;
	private final TextView mWeekSchedule;
	private final TextView mPreference;

//	   private final TextView mNotice;
//	   private final LinearLayout mLunarLin;


	public SubPopMenu(View anchor) {
		super(anchor);
		context  = anchor.getContext();

		ViewUtil.setLocaleFromPreference(context, context.getClass());
		inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		root  = (ViewGroup) inflater.inflate(R.layout.sub_menu_list, null);
		setContentView(root);
		//mTrack    = (ViewGroup) root.findViewById(R.id.viewRow); //팝업 View의 내용을 추가한 LinearLayout
//	      mLunarLin 	= (LinearLayout) root.findViewById(R.id.lin_menu); //음력달력 LinearLayout
		mLunarCal    	= (TextView) root.findViewById(R.id.lunarcalendar); //음력달력 TextView

		mNotice    		= (TextView) root.findViewById(R.id.notice); 			//공지사항 TextView
		mTotalSchedule   	= (TextView) root.findViewById(R.id.totalschedule); 	//전체스케줄 TextView
		mTotalSpecialDay 	= (TextView) root.findViewById(R.id.totalspecialday); 	//전체기념일 TextView
		mWeekSchedule    	= (TextView) root.findViewById(R.id.weekschedule); 		//주간스케줄 TextView
		mPreference    	= (TextView) root.findViewById(R.id.preference); 		//설정 TextView


		mLunarCal.setOnClickListener(new Button.OnClickListener( )
		{
			@Override
			public void onClick(View v)  {
				MenuHandler msgHd = new MenuHandler(context);
				msgHd.callLunarCalendar();
				window.dismiss();
			}

		} ) ;
		mNotice.setOnClickListener(new Button.OnClickListener( )
		{
			@Override
			public void onClick(View v)  {
				MenuHandler msgHd = new MenuHandler(context);
				msgHd.callNotice();
				window.dismiss();
			}

		} ) ;
		mTotalSchedule.setOnClickListener(new Button.OnClickListener( )
		{
			@Override
			public void onClick(View v)  {
				MenuHandler msgHd = new MenuHandler(context);
				msgHd.callScheduleUserList();
				window.dismiss();
			}

		} ) ;
		mTotalSpecialDay.setOnClickListener(new Button.OnClickListener( )
		{
			@Override
			public void onClick(View v)  {
				MenuHandler msgHd = new MenuHandler(context);
				msgHd.callSpecialdayAllList();
				window.dismiss();
			}

		} ) ;
		mWeekSchedule.setOnClickListener(new Button.OnClickListener( )
		{
			@Override
			public void onClick(View v)  {
				MenuHandler msgHd = new MenuHandler(context);
				msgHd.callScheduleDayOfWeek();
				window.dismiss();
			}

		} ) ;
		mPreference.setOnClickListener(new Button.OnClickListener( )
		{
			@Override
			public void onClick(View v)  {
				MenuHandler msgHd = new MenuHandler(context);
				msgHd.callPreference();
				window.dismiss();
			}

		} ) ;
	}

//	   private void callNotice() {
//			Intent intent = new Intent(this, Notice.class);
//			intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//			startActivity(intent);
//	   }

	public void show () {
		preShow(); //상속받은 PopView의 메서드
		int[] location   = new int[2];
		anchor.getLocationOnScreen(location);
		root.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		root.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		window.showAtLocation(this.anchor, Gravity.CENTER, 0, 0); //가운데 정렬 하여 보임
	}

	public void showAsDropDown (  ) {
		preShow(); //상속받은 PopView의 메서드
		int[] location   = new int[2];
		anchor.getLocationOnScreen(location);
		root.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		root.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		//window.showAtLocation(this.anchor, Gravity.RIGHT, XPos, YPos); //가운데 정렬 하여 보임
		window.showAsDropDown(this.anchor);
	}
	public void showDropUp () {
		preShow(); //상속받은 PopView의 메서드
		int[] location   = new int[2];
		anchor.getLocationOnScreen(location);
		root.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		root.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		window.showAtLocation(this.anchor, Gravity.BOTTOM|Gravity.RIGHT, 30, 70);
	}
}

