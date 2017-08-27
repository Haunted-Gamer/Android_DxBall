package Dx.Ball;


import java.util.ArrayList;
import java.util.Timer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class GameCanvas extends View {
	int xx=50;
	int widt;
 boolean isFirstTime=true;
 int rs;
	Paint paint;
	float x=0,y=0;
	double barLocation=50;
	double barLocation2=250;
	boolean firstTime=true;
	boolean goingDown=true;
	boolean goingRight=true;
	boolean crushRight=false;
	boolean crushLeft=false;
	boolean crushUp=false;
	boolean crushDown=false;
	int collisionValue=0 ;
	ArrayList<Pair> values = new ArrayList<Pair>();
	protected void calculateNextPos(Canvas can){
		int wid=can.getWidth();
		
		int hei=can.getHeight();
		if(wallCollision(hei,wid)!=0){
	    collisionValue = wallCollision(hei,wid);
		}
		switch(collisionValue){
		case 1: if(goingDown==true){
				x--;
				y++;
				break;
				
		}
		else{	x--;
				y--;
				break;
		}
		
		case 2: if(goingDown==true){
			x++;
			y++;
			break;
	}
	else{	x++;
			y--;
			break;
	}
		
		
		case 3: if(goingRight==true){
			x++;
			y--;
			break;
	}
	else{	x--;
			y--;
			break;
	}
		
		
		case 4: if(goingRight==true){
			x++;
			y++;
			break;
	}
	else{	x--;
			y++;
			break;
	}
		default: {x++;
					y++;
		
		}
		
		}
		
		
	}
	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		
		float x = e.getX();
	if(x<widt/2){xx=xx-2;
	return true;}
	else if (x>widt/2){xx=xx+2; return true;}
	return false;
	}
	
	
	
	
	protected void onDraw(Canvas canvas)
	{
		widt=canvas.getWidth();
		if(firstTime)
		{
			firstTime=false;
			x=canvas.getWidth() / 2;
			y=canvas.getHeight() / 2;
		}
		
		canvas.drawRGB(25, 255, 255);
		drawCircle(canvas);
		
		
		//canvas.drawRect(100, 100, 200, 200, paint);
		drawRectangle(canvas);
		detectBrickCollision();
		drawBar(canvas);
		new Thread(new Task()).start();
		//invalidate();
	}
	
	public GameCanvas(Context context) {
		super(context);
		paint = new Paint();
	}
	
	public void drawRectangle(Canvas canv){
		boolean firsttime=true;
		int k = 1;
		Pair[] pair=new Pair[7];
		pair[0]=new Pair();
		pair[1]=new Pair();
		pair[2]=new Pair();
		pair[3]=new Pair();
		pair[4]=new Pair();
		pair[5]=new Pair();
		pair[6]=new Pair();
		int hei=canv.getHeight();
		int wid=canv.getWidth();
		float startheight=60;
		float start=0;
		float rectWidth=(float) wid/6;
		float x=(float) 0.0;
	 float brutal=(float) x+rectWidth;
	 
		while(brutal<wid){
			if(isFirstTime==true && firsttime == true){
			pair[0].setX(0);
			pair[0].setY(rectWidth);
			//Log.d(Double.toString(pair[0].getX()), Double.toString(pair[0].getY()));
			values.add(pair[0]);
			firsttime=false;
			}
			if(isFirstTime==true){
				pair[k].setX(brutal);
				pair[k].setY(brutal+rectWidth);
				//Log.d(Double.toString(pair[k].getX()), Double.toString(pair[k].getY()));
				values.add(pair[k]);
				}
			k++;
	brutal=brutal+rectWidth;	
		}
		//pair[k]=values.get(0);
		float ddd=(float)pair[k].getX();
		//Log.d(Float.toString(ddd),Float.toString(ddd));
		
		for (rs=0;rs<values.size();rs++){
			float dd=(float) values.get(rs).getX();
			float ds=(float)values.size();
			float dr=(float) values.get(rs).getY();
			//Log.d(Float.toString(dd),Float.toString(dr));
			paint.setColor(Color.MAGENTA);
			paint.setStyle(Style.FILL);
			
			canv.drawRect(dd,startheight,dr,100,paint);
	
	
	paint.setColor(Color.YELLOW);
	paint.setStyle(Style.STROKE);
	canv.drawRect(dd,startheight,dr,100,paint);
	
			
		}
		isFirstTime=false;
	}
	public void drawCircle(Canvas canv){
		
		calculateNextPos(canv);
		
		paint.setColor(Color.RED);
		paint.setStyle(Style.FILL);
		
		canv.drawCircle(x,y, 20, paint);
	}
	public void drawBar(Canvas canv){
		int height=canv.getHeight();
		
		paint.setColor(Color.RED);
		paint.setStyle(Style.FILL);
		canv.drawRect((float)barLocation+xx, (float)height-30, (float)barLocation2+xx , (float)height-5, paint);
		
	}
	
	public int wallCollision(int height, int width){
		if (x>width){
			goingRight=false;
			return 1;
		}
		else if (x<0){
			goingRight=true;
			return 2;}
		else if (y>height){
			goingDown=false;
			return 3;}
		else if (y<0){
			goingDown=true;
			return 4;}
		return 0;
		
	}
	public void detectBrickCollision(){
		double v,t;
		for (int i=0;i<values.size();i++){
			Pair xtreme=values.get(i);
			v=xtreme.getX();
			t=xtreme.getY();
			Log.d("SAD", Double.toString(t));
			if(v<x && x<t && y>60 && y<100){
				values.remove(i);
				if(goingDown==false){
				goingDown=true;}
				else goingDown=false;
			}
		}
		
	}
	
    class Task implements Runnable {
    			
    		      

						public void run() 
						{
	    		               // try 
	    		                //{
	    		                    //Thread.sleep(1);
							long t = (long) 0.000000000000002;
	    		                     postInvalidateDelayed (t);
	    		                //} 
	    		                //catch (InterruptedException e) 
	    		                //{
	    		                    //e.printStackTrace();
	    		                //}
						}
    				}
    
    public class Pair {
        private double x = 0.0;
        private double y = 0.0;

        public Pair(double x, double y)
        {
            this.x = x;
            this.y = y;
        }

        public Pair()
        {

        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }


    }
    
    
    
}
				
   
