package rosegold.example.sudoku;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SudokuGridView extends GridView {

    private Context context;

    public SudokuGridView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        SudokuGridViewAdapter gridViewAdapter = new SudokuGridViewAdapter(context);
        setAdapter(gridViewAdapter);

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int x = position / 9;
                int y = position % 9;
                GameEngine.getInstance().setSelected(x, y);
                //Toast.makeText(context, "Item x:"+x+" y: "+y,Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}

class SudokuGridViewAdapter extends BaseAdapter {

    private Context context;

    public SudokuGridViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 81;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*View v = convertView;

        if(v == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            v = inflater.inflate(R.layout.cell, parent, false);
        }

        int x = position % 9;
        int y = position / 9;

        SudokuCell cell = (SudokuCell) v;
        cell.setNumber(GameEngine.getInstance().getSudoku()[x][y]);*/

        //return v;
        return GameEngine.getInstance().getGrid().getItem(position);
    }
}
