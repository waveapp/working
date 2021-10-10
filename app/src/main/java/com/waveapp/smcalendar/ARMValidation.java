package com.waveapp.smcalendar ;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/*------------------------------------
 * 마켓별 복제방지 시스템 적용
 * 1) Tstore: ARM
 * 2) LG U+ : ARM
 * 3) 안드로이드마켓:라이센스(미적용)
 ------------------------------------*/


public class ARMValidation extends SMActivity{
//	public class ARMValidation extends SMActivity implements ArmListener{

    //	Dialog di;
    Context ctx ;
    //	private ArmManager 					arm;
//	private String						TStoreAID = "OA00266624";  //정식버전AppId(In Tstore)
    private static final String TAG 	= "ARMValidation";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //2.View ID Set
        setContentView(R.layout.main);

        this.ctx = this;


//		******.TStore 복제방지 API (ARM 적용)******
//        ******.안드로이드 적용시 삭제******
//        String appid = VersionConstant.APPID;
//        if ( appid.equals(VersionConstant.APP_NORMAL)) {
//        	runArmService();
//        }

//*********************************************************

        //안드로이드
        callSMCalendar();
    }

//	private void runArmService() {
//		try {
//			arm = null;
//			arm = new ArmManager(ARMValidation.this);
//			arm.setArmListener(ARMValidation.this);
//			arm.ARM_Plugin_ExecuteARM(TStoreAID);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void onArmResult() {
//		Log.d(TAG, "############ onArmResult[" + arm.nNetState
//				+ "]  ############");
//		switch (arm.nNetState) {
//		case SERVICE_CONNECT:
//			/*
//			 * ARMService성공적으로 연결되었을때 팝업 창을 띄우지 않음 Application 정상적으로 실행.
//			 */
//			callSMCalendar();
//			break;
//
//		case SERVICE_FAIL:
//		case SERVICE_NOT_EXIST:
//			/*
//			 * ARMService가 설치 되지 않았을 때 Application 강제 종료 후 오류 메세지를 팝업 창으로 띄움.
//			 */
//			ComUtil.showToastLong(this, "ARM connection fail or invalid App!!(" + arm.sResMsg +")" );
//	    	finish();
//			break;
//		}
//
//	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

//        //달력화면 open
//        callCalendar();
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState){
        super.onRestoreInstanceState(outState);
    }
    @Override
    protected void onResume() {
        super.onResume();


    }
    @Override
    protected void onPause() {
        super.onPause();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//    	if ( di != null && di.isShowing() ) {
//    		di.dismiss();
//    		di = null;
//    	}
    }
    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void callSMCalendar() {

        Intent i = new Intent(this, SMCalendar.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

        finish();
    }


}