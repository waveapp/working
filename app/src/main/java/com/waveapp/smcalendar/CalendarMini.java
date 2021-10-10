package com.waveapp.smcalendar;


import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.waveapp.smcalendar.util.gsCalendar;


public class CalendarMini extends SMActivity 
{

    TextView[] tvs;
    ImageButton[] btns;
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.calendar_mini);
        LinearLayout lv = findViewById( R.id.calendar_lLayout );
        
        tvs = new TextView[3] ;
        tvs[0] = findViewById( R.id.year );
        tvs[1] = findViewById( R.id.month );
        //tvs[2] = (TextView)findViewById( R.id.day ) ;
        
        btns = new ImageButton[4] ;
        btns[0] = findViewById( R.id.previousyear );
        btns[1] = findViewById( R.id.nextyear );
        btns[2] = findViewById( R.id.previousmonth );
        btns[3] = findViewById( R.id.nextmonth );
        
        gsCalendar cal = new gsCalendar( this, lv ) ;
        
        //cal.setSizePixel( 420, 385 ) ;
        
        cal.setControl( btns ) ;
        cal.setViewTarget( tvs ) ;
        
        cal.initCalendar( ) ;
        
        
        
    }

}