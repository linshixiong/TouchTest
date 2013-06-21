package com.example.touchtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

//新建一个类继承View
public class DrawView extends View {
	
	private int startX=100;
	private int startY=100;
	private int endX=700;
	private int endY=380;
	
	private int downCount=0;
	
	private int mov_x;// 声明起点坐标
	private int mov_y;
	private int end_x;
	private int end_y;

	
	private int[] start=new int[2];
	private int[] end=new int[2];
	
	
	private Paint paint;// 声明画笔
	private Canvas canvas;// 画布
	private Bitmap bitmap;// 位图
	boolean isContinuous = false;
	private TouchTestResultListener listener;
	
	public DrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint(Paint.DITHER_FLAG);// 创建一个画笔
		bitmap = Bitmap.createBitmap(800, 480, Bitmap.Config.ARGB_8888); // 设置位图的宽高
		canvas = new Canvas();
		canvas.setBitmap(bitmap);

		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(3);// 
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);
	}
	public DrawView(Context context) {
		super(context);
		paint = new Paint(Paint.DITHER_FLAG);// 创建一个画笔
		bitmap = Bitmap.createBitmap(800, 480, Bitmap.Config.ARGB_8888); // 设置位图的宽高
		canvas = new Canvas();
		canvas.setBitmap(bitmap);

		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(3);// 
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);

	}

	public void setTouchTestResultListener(TouchTestResultListener listener){
		this.listener=listener;
	}
	
	public void setStartEnd(int startX,int startY,int endX,int endY){
	
		this.startX=startX;
		this.startY=startY;
		this.endX=endX;
		this.endY=endY;
		Log.d(getClass().getName(),"setStartEnd startX="+startX+",startY="+startY+",endX="+endX+",endY="+endY);
	}
	
	public void clear() {
		// Log.d(getClass().getName(), "clear draw");

		canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
		this.postInvalidate();
	}

	// 画位图
	@Override
	protected void onDraw(Canvas canvas) {
		// super.onDraw(canvas);
		canvas.drawBitmap(bitmap, 0, 0, null);
	}

	// 触摸事件
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		//Log.d(getClass().getName(), "MotionEvent action=" + event.getAction());
		if (event.getAction() == MotionEvent.ACTION_MOVE) {// 如果拖动
			Log.d(getClass().getName(), "MotionEvent.ACTION_MOVE");
			int x1 = mov_x;
			int y1 = mov_y;
			int x2 = (int) event.getX();
			int y2 = (int) event.getY();
			// Log.d(getClass().getName(),
			// "move x1="+x1+",y1="+y1+",x2="+x2+",y2="+y2);
			canvas.drawLine(x1, y1, x2, y2, paint);// 画线
			invalidate();
		}
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// 如果点击
			handler.removeMessages(0);
			Log.d(getClass().getName(), "MotionEvent.ACTION_DOWN");
			// clear();
			downCount++;
			mov_x = (int) event.getX();
			mov_y = (int) event.getY();
			//第一次按下时记录坐标位置
			if(downCount==1){
				
			}
			canvas.drawPoint(mov_x, mov_y, paint);// 画点
			invalidate();
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {// 抬起
			

			handler.sendEmptyMessageDelayed(0, 300);
			
			
			Log.d(getClass().getName(), "MotionEvent.ACTION_UP downCount="+downCount);
		}
		if (event.getAction() == MotionEvent.ACTION_CANCEL) {
			Log.d(getClass().getName(), "MotionEvent.ACTION_CANCEL");
		}
		mov_x = (int) event.getX();
		mov_y = (int) event.getY();
		// Log.d(getClass().getName(),"touch x="+mov_x+",y="+mov_y);
		return true;
	}
	
	
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
		
			switch (msg.what) {
			case 0:
				
				clear();
				if(listener!=null){
					listener.onTouchTestResultOK(downCount==1);
				}
				downCount=0;
				break;

			default:
				break;
			}	
			
			super.handleMessage(msg);
		}

		
	};
	

}