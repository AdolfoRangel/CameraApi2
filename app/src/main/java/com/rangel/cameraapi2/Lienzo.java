package com.rangel.cameraapi2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class Lienzo extends View {

    private static Path drawPath;                              // trazo del que vamos pintando
    private static Paint drawPaint, canvasPaint;        // es como el pincel,
    private static int paintColor = 0xFF660000;                //color inicial
    private static Canvas drawCanvas;                   //lienzo, fondo
    private static Bitmap canvasBitmap;                 //tipo de archivo par apoder guardarlo
    static Bitmap mutableBitmap;//se ra la imagen

    private static Boolean estaborrando;

    private float iniciotouchX ;
    private float iniciotouchY ;

    float touchX;
    float touchY;

    public Lienzo(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpDrawing();
    }

    private static void setUpDrawing() {
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(10);
        drawPaint.setStyle(Paint.Style.STROKE); //pintar solo bordes, trazos
        drawPaint.setStrokeJoin(Paint.Join.ROUND); //pintura sera redondeada
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Toast.makeText(getContext(), "w: "+w +" h:"+ h, Toast.LENGTH_SHORT).show();
        /*canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);*/

        if(canvasBitmap != null){
            canvasBitmap = Bitmap.createScaledBitmap(canvasBitmap, w, h, true);
            mutableBitmap = canvasBitmap.copy(Bitmap.Config.ARGB_8888, true);
            drawCanvas = new Canvas(mutableBitmap);
            drawCanvas.setBitmap(mutableBitmap);
        }
//        if(){
//            canvas.drawRect(new RectF(iniciotouchX, iniciotouchY, touchX, touchY ),drawPaint);
//        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
         touchX = event.getX();
         touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                iniciotouchX  = touchX;
                iniciotouchY = touchY;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
        canvas.drawRect(new RectF(iniciotouchX, iniciotouchY, touchX, touchY ),drawPaint);
    }

    //actualiza color
    public void setColor(String newColor) {
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }

    public static void setBitmap( Bitmap bitmap){
        canvasBitmap = bitmap;
         //drawCanvas.setBitmap(mutableBitmap);
        //drawCanvas = new Canvas(mutableBitmap);
        setUpDrawing();
    }


}