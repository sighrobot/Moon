package com.abe.moon;

import java.util.Calendar;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class Moon extends View {
	
	private Calendar calendar = Calendar.getInstance();
	private MoonPhase mp;
	private Canvas canvas;
	private String phaseString;
	

	public Moon(Context context) {
		super(context);
	}

	public Moon(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Moon(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	    int size = 0;
	    int width = getMeasuredWidth();
	    int height = getMeasuredHeight();
	 
	    if (width > height) {
	        size = height;
	    } else {
	        size = width;
	    }
	    setMeasuredDimension(size, size);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.canvas = canvas;
		this.drawMoon();
	}
	
	public void setCalendar(Calendar c) {
		calendar = c;
		mp = new MoonPhase(calendar);
		phaseString = mp.getPhaseIndexString(mp.getPhaseIndex());
	}
	
	public String getPhaseString () {
		return phaseString.toUpperCase();
	}
	
	private void drawMoon() {
		
		int width = this.getWidth();
		int height = this.getHeight();
		
		mp = new MoonPhase(calendar);
		double percentIlluminated = mp.getPhase() / 100;
		
		// moon
		Paint moonPaint = new Paint();
		moonPaint.setColor(Color.WHITE);
		moonPaint.setMaskFilter(new BlurMaskFilter(1, BlurMaskFilter.Blur.NORMAL));
		double scalingFactor = 0.95;
		float radius = (float) ( Math.min(width / 2, height / 2) * scalingFactor );
		
		if (percentIlluminated != 0.0) {
			canvas.drawCircle(width/2, height/2, radius, moonPaint); // moon
		}
		
		// waxing
		
		if (percentIlluminated > 0) {
			if (percentIlluminated <= 0.5) {
				
				Paint maskPaint = new Paint();
				maskPaint.setColor(Color.BLACK);
				maskPaint.setMaskFilter(new BlurMaskFilter(1, BlurMaskFilter.Blur.NORMAL));
				
				// Black rectangle on left
				canvas.drawRect(0, (float) ( height / 2 - radius - 8), width / 2, (float) ( height / 2 + radius + 8), maskPaint );
				
				// Black oval
				RectF ovalBounds = new RectF();
				float ovalLeft = (float) ( width / 2 - radius + 2 * radius * percentIlluminated);
				float ovalTop = (float) ( height / 2 - radius - 8 );
				float ovalRight = (float) ( width / 2 + radius - 2 * radius * percentIlluminated);
				float ovalBottom = (float) ( height / 2 + radius + 8 );
				ovalBounds.set(ovalLeft, ovalTop, ovalRight, ovalBottom);
				canvas.drawOval(ovalBounds, maskPaint);
				
			}
			else {
				
				Paint maskPaint = new Paint();
				maskPaint.setColor(Color.BLACK);
				maskPaint.setMaskFilter(new BlurMaskFilter(1, BlurMaskFilter.Blur.NORMAL));
				
				Paint ovalPaint = new Paint();
				ovalPaint.setColor(Color.WHITE);
				ovalPaint.setMaskFilter(new BlurMaskFilter(1, BlurMaskFilter.Blur.NORMAL));
				
				// Black rectangle on left
				canvas.drawRect(0, (float) ( height / 2 - radius - 8), width / 2, (float) ( height / 2 + radius + 8), maskPaint );
				
				// White oval
				RectF ovalBounds = new RectF();
				float ovalLeft = (float) ( width / 2 + radius - 2 * radius * percentIlluminated );
				float ovalTop = (float) ( height / 2 - radius );
				float ovalRight = (float) ( width / 2 - radius + 2 * radius * percentIlluminated);
				float ovalBottom = (float) ( height / 2 + radius );
				ovalBounds.set(ovalLeft, ovalTop, ovalRight, ovalBottom);
				canvas.drawOval(ovalBounds, ovalPaint);
				
			}
		}
		// waning
		else {
			if (percentIlluminated < -0.5) {
				
				Paint maskPaint = new Paint();
				maskPaint.setColor(Color.BLACK);
				maskPaint.setMaskFilter(new BlurMaskFilter(5, BlurMaskFilter.Blur.NORMAL));
				
				Paint ovalPaint = new Paint();
				ovalPaint.setColor(Color.WHITE);
				ovalPaint.setMaskFilter(new BlurMaskFilter(1, BlurMaskFilter.Blur.NORMAL));
				
				// Black rectangle on right
				canvas.drawRect((float)(width / 2), (float) ( height / 2 - radius - 8), width, (float) ( height / 2 + radius + 8), maskPaint );
				
				// White oval
				RectF ovalBounds = new RectF();
				float ovalLeft = (float) ( width / 2 + radius - 2 * radius * Math.abs(percentIlluminated) );
				float ovalTop = (float) ( height / 2 - radius );
				float ovalRight = (float) ( width / 2 - radius + 2 * radius * Math.abs(percentIlluminated) );
				float ovalBottom = (float) ( height / 2 + radius );
				ovalBounds.set(ovalLeft, ovalTop, ovalRight, ovalBottom);
				canvas.drawOval(ovalBounds, ovalPaint);
				
			}
			else {
				
				Paint maskPaint = new Paint();
				maskPaint.setColor(Color.BLACK);
				maskPaint.setMaskFilter(new BlurMaskFilter(1, BlurMaskFilter.Blur.NORMAL));
				
				// Black rectangle on right
				canvas.drawRect((float)(width / 2), (float) ( height / 2 - radius - 8), width, (float) ( height / 2 + radius + 8), maskPaint );
				
				// Black oval
				RectF ovalBounds = new RectF();
				float ovalLeft = (float) ( width / 2 - radius + 2 * radius * Math.abs(percentIlluminated));
				float ovalTop = (float) ( height / 2 - radius - 8 );
				float ovalRight = (float) ( width / 2 + radius - 2 * radius * Math.abs(percentIlluminated));
				float ovalBottom = (float) ( height / 2 + radius + 8 );
				ovalBounds.set(ovalLeft, ovalTop, ovalRight, ovalBottom);
				canvas.drawOval(ovalBounds, maskPaint);
				
			}
			
		}
		
	}

}
