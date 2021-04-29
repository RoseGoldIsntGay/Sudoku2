package rosegold.example.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SudokuCell extends BaseSudokuCell {

    private Paint paint;
    private double id;
    private boolean init = false;
    public boolean isFalse = false;
    public boolean friendSelected = false;


    public SudokuCell(Context context, double id) {
        super(context);
        this.id = id;
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawSelected(canvas);
        drawNumber(canvas);
        drawLines(canvas);

    }

    private void drawSelected(Canvas canvas) {
        if(friendSelected) {
            paint.setColor(Color.rgb(51, 153, 255));
            paint.setStyle(Paint.Style.FILL);
            paint.setFlags(paint.getFlags() | Paint.ANTI_ALIAS_FLAG | Paint.SUBPIXEL_TEXT_FLAG);
            canvas.drawRect(0, 0, getHeight(), getWidth(), paint);
        }
        if(isSelected()) {
            paint.setColor(Color.CYAN);
            paint.setStyle(Paint.Style.FILL);
            paint.setFlags(paint.getFlags() | Paint.ANTI_ALIAS_FLAG | Paint.SUBPIXEL_TEXT_FLAG);
            canvas.drawRect(0, 0, getHeight(), getWidth(), paint);
        }
        invalidate();
    }

    private void drawLines(Canvas canvas) {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        for(int i = 0; i < 2; i++) {
            if (id > 26+27*i && id < 36+27*i) {
                paint.setStrokeWidth(15);
                canvas.drawLine(0, 0, 0, getHeight(), paint);
            }
        }
        for(int i = 1; i < 3; i++) {
            for(int j = 0; j < 9; j++) {
                if (id == 3*i+9*j) {
                    paint.setStrokeWidth(15);
                    canvas.drawLine(0, 0, getWidth(), 0, paint);
                }
            }
        }


    }

    private void drawNumber(Canvas canvas) {
        if (getChangable()) {
            paint.setColor(Color.BLUE);
        } else {
            paint.setColor(Color.BLACK);
        }
        if(isFalse) {
            paint.setColor(Color.RED);
        }
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(64f);
        paint.setStyle(Paint.Style.FILL);

        Rect bounds = new Rect();
        paint.getTextBounds(String.valueOf(getValue()), 0, String.valueOf(getValue()).length(), bounds);
        //System.out.println(getValue());
        //System.out.println(bounds.width() + " " + bounds.height());

        if(getValue() != 0) {
            canvas.drawText(String.valueOf(getValue()), (getWidth() / 2), (getHeight() / 2 + bounds.height() / 2), paint);
        }

    }

    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            Toast.makeText(getContext(), id+"", Toast.LENGTH_SHORT).show();
        }
        return super.onTouchEvent(event);
    }*/
}
