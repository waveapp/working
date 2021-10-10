package com.waveapp.smcalendar.link;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.skt.arm.aidl.IArmService;
import com.waveapp.smcalendar.util.ComUtil;

/*
 * TStore 복제방지 모듈 (마켓적용전 항상 확인필요한 사항) : 정식버전에서만 사용
 */
public class TstoreArmLink extends Activity { 
	private final Context mCtx;
	
	private IArmService 				service;
	private ArmServiceConnection 		armCon;
	private String 						resMsg;
	private String						AID = "OA00266624";  //정식버전AppId(In Tstore)
	public TstoreArmLink(Context ctx) {
        this.mCtx = ctx;
    }	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if(!runService()){
        	resMsg = "ARM connection Fail"; 
        	ComUtil.showToastLong(mCtx, resMsg);
        }
    }
    
	/*
	 * 실제 호출되는 function
	 */
    private boolean runService() { 
		try{
			if(armCon == null){
				armCon = new ArmServiceConnection();
				boolean conRes = bindService(
							new Intent(TstoreArmLink.class.getName()), armCon, Context.BIND_AUTO_CREATE);
				if(conRes)	{
					return true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		releaseService();
		return false;
	}
	
	private void releaseService(){
		if(armCon != null){
			unbindService(armCon);
			armCon = null;
			service = null;
		}
	}
	
	class ArmServiceConnection implements ServiceConnection{
		
		public void onServiceConnected(ComponentName name, IBinder boundService) {
			if(service == null)
				service = IArmService.Stub.asInterface(boundService);
			try{
				int res = service.executeArm(AID);
				resMsg = "Result Code=" + Integer.toHexString(res);
				ComUtil.showToastLong(mCtx, resMsg);
			}catch(Exception e){
				e.printStackTrace();
				releaseService();
				resMsg = "Fail the service";
				ComUtil.showToastLong(mCtx, resMsg);
				return;
			}
			releaseService();
		}	
		
		public void onServiceDisconnected(ComponentName name) {
			service = null;
		}
	}
}
