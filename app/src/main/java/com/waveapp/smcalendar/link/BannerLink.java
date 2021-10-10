package com.waveapp.smcalendar.link;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.waveapp.smcalendar.R;
import com.waveapp.smcalendar.SMActivity;
import com.waveapp.smcalendar.common.ComConstant;
import com.waveapp.smcalendar.common.VersionConstant;
import com.waveapp.smcalendar.util.RecycleUtil;

public class BannerLink  extends AppCompatActivity {

	LinearLayout  lin_banner ;
	
//	@Override
////	public void onCreate(Bundle savedInstanceState) {
////		super.onCreate(savedInstanceState);
////	}
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);

//		MobileAds.initialize(this, new OnInitializationCompleteListener() {
//			@Override
//			public void onInitializationComplete(InitializationStatus initializationStatus) {
//			}
//		});
}
	/*
	 * Addmob 광고 
	 */
	public void callBannerLink( final Context ctx ) { 

		lin_banner = ((Activity) ctx).findViewById( R.id.lin_banner );
		
		//lite 버전만 (정식:01 lite:02)
//		if ( VersionConstant.APPID.equals(VersionConstant.APP_NORMAL)) {
//
//			lin_banner.removeAllViews();
//
//		} else {
			lin_banner.setVisibility(View.VISIBLE);
//			com.cauly.android.ad.AdView
//					caulyview = ((Activity) ctx).findViewById( R.id.caulyView );
//			com.google.ads.AdView
//					addmobview = ((Activity) ctx).findViewById( R.id.addmobView );
//			com.google.android.gms.ads.AdView
//					addmobview= ((Activity) ctx).findViewById( R.id.addmobView );
			
			//한국은 카울리 나머지는 애드몹
//			if ( ComConstant.LOCALE_KO.equals(ComConstant.LOCALE)) {
//
//
//				caulyview.setVisibility(View.VISIBLE);
//				addmobview.setVisibility(View.GONE);
//				addmobview = null;
//
//				if ( caulyview != null ) {
//
//					caulyview.setAdListener( new com.cauly.android.ad.AdListener() {
//						@Override
//						public void onFailedToReceiveAd(boolean arg0) {
//							// TODO Auto-generated method stub
//						}
//						@Override
//						public void onReceiveAd() {
//							// TODO Auto-generated method stub
//						}
//					});
//				}
//
//
//			} else {

				//Add Mob Link
				/*
				caulyview.setVisibility(View.GONE);
				caulyview.destroyDrawingCache();
				caulyview = null;
				*/
				//addmobview.setVisibility(View.VISIBLE);
				
				
				
//				if ( addmobview != null ) {
					
//					AdRequest re = new AdRequest();
//
////					re.addTestDevice(AdRequest.TEST_EMULATOR);
//					re.addTestDevice("304D1913DF39594E");    // My T-Mobile G1 test phone
////					re.setTesting(true);
////					request.setGender(AdRequest.Gender.FEMALE);
////					re.setLocation(location);
//					addmobview.loadAd(re);
//					MobileAds.initialize(this, new OnInitializationCompleteListener() {
//						@Override
//						public void onInitializationComplete(InitializationStatus initializationStatus) {
//						}
//					});

					//addmobview = findViewById(R.id.ad_view);
					//AdRequest adRequest = new AdRequest.Builder().build();
					//addmobview.setAdSize(AdSize.BANNER);
					//addmobview.setAdUnitId("ca-app-pub-2960266420718368~8291820007");
					//addmobview.loadAd(adRequest);
//				}
//			}

//		}

	}
    @Override
    protected void onDestroy() { 
    	super.onDestroy();
        
        RecycleUtil.recursiveRecycle(lin_banner);
        
    }	
}