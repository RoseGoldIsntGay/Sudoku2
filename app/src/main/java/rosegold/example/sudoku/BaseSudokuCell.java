package rosegold.example.sudoku;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class BaseSudokuCell extends View {

    private int value = -1;
    private int prevValue = -1;
    private boolean changable = true;
    private boolean selected = false;

    public BaseSudokuCell(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    public void setNotChangable() {
        changable = false;
    }

    public boolean getChangable() {
        return changable;
    }

    public void setValue(int value) {
        prevValue = this.value;
        if(changable) {
            this.value = value;
        }
        invalidate();
    }
    public void setInit(int value) {
        this.value = value;
        invalidate();
    }

    public int getValue() {
        return value;
    }

    public int getPrevValue() {
        return prevValue;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        invalidate();
    }

}
