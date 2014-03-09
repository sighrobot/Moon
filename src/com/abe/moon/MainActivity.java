package com.abe.moon;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.os.Bundle;
import android.os.Handler;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	Moon moon;
	MoonPhase moonPhase;
	Button todayButton;
	Button leftButton;
	Button rightButton;
	Calendar calendar;
	TextView phaseString;
	TextView dateString;
	SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy");
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);        
        //Moon m = new Moon(this);
        //setContentView(m);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        
        moon = (Moon) findViewById(R.id.moon1);
        todayButton = (Button) findViewById(R.id.button1);
        leftButton = (Button) findViewById(R.id.button4);
        rightButton = (Button) findViewById(R.id.button3);
        phaseString = (TextView) findViewById(R.id.textView1);
        dateString = (TextView) findViewById(R.id.textView2);
        
        calendar = Calendar.getInstance();
		reinitialize();
		todayButton.setVisibility(View.GONE);
        
        todayButton.setOnClickListener(
        		new OnClickListener() {
        			@Override
					public void onClick(View arg0) {
        				calendar = Calendar.getInstance();
        				reinitialize();
        				todayButton.setVisibility(View.GONE);
        			}
        		});
        
        leftButton.setOnTouchListener(
        		new View.OnTouchListener() {
        			
        			private Handler mHandler;
        			
        			@Override
					public boolean onTouch(View v, MotionEvent event) {
        				
        				switch(event.getAction()) {
        		        case MotionEvent.ACTION_DOWN:
        		            if (mHandler != null) return true;
        		            mHandler = new Handler();
        		            mHandler.postDelayed(mAction, 25);
        		            break;
        		        case MotionEvent.ACTION_UP:
        		            if (mHandler == null) return true;
        		            mHandler.removeCallbacks(mAction);
        		            mHandler = null;
        		            break;
        		        }
        		        return false;
        				
        			}
        			Runnable mAction = new Runnable() {
        				@Override
        				public void run() {
        					calendar.add(Calendar.DATE, -1);
            				reinitialize();
            				if (datesMatch(calendar)) todayButton.setVisibility(View.GONE);
            				else todayButton.setVisibility(View.VISIBLE);
        					mHandler.postDelayed(this, 100);
        				}
        			};
        		});
        
        rightButton.setOnTouchListener(
        		new View.OnTouchListener() {
        			
        			private Handler mHandler;
        			
        			@Override
					public boolean onTouch(View v, MotionEvent event) {
        				
        				switch(event.getAction()) {
        		        case MotionEvent.ACTION_DOWN:
        		            if (mHandler != null) return true;
        		            mHandler = new Handler();
        		            mHandler.postDelayed(mAction, 25);
        		            break;
        		        case MotionEvent.ACTION_UP:
        		            if (mHandler == null) return true;
        		            mHandler.removeCallbacks(mAction);
        		            mHandler = null;
        		            break;
        		        }
        		        return false;
        				
        			}
        			Runnable mAction = new Runnable() {
        				@Override
        				public void run() {
        					calendar.add(Calendar.DATE, 1);
            				reinitialize();
            				if (datesMatch(calendar)) todayButton.setVisibility(View.GONE);
            				else todayButton.setVisibility(View.VISIBLE);
        					mHandler.postDelayed(this, 100);
        				}
        			};
        		});
        
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                    int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                
                reinitialize();
				if (datesMatch(calendar)) todayButton.setVisibility(View.GONE);
				else todayButton.setVisibility(View.VISIBLE);
                
                
            }

        };

           dateString.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    new DatePickerDialog(MainActivity.this, date, calendar
                            .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private void reinitialize() {
    	moon.setCalendar(calendar);
		phaseString.setText(moon.getPhaseString());
		dateString.setText(sdf.format(calendar.getTime()).toUpperCase());
		moon.invalidate();
    }
    
    private boolean datesMatch(Calendar c) {
    	Calendar currCal = Calendar.getInstance();
    	int currYr = currCal.get(Calendar.YEAR);
    	int currMo = currCal.get(Calendar.MONTH);
    	int currDy = currCal.get(Calendar.DAY_OF_MONTH);
    	int otherYr = c.get(Calendar.YEAR);
    	int otherMo = c.get(Calendar.MONTH);
    	int otherDy = c.get(Calendar.DAY_OF_MONTH);
    	//System.out.println(currYr + " " + currMo + " " + currDy);
    	if (currYr == otherYr && currMo == otherMo && currDy == otherDy) return true;
    	else return false;
    }
    
}
