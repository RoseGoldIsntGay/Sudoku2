package rosegold.example.sudoku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button back;
    public static String diff;
    GameEngine engine;
    private boolean shouldRun;
    private Handler timerHandler;
    private Runnable timerRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        diff = getIntent().getStringExtra("diff");
        Log.i("MainActivity", "diff: " +diff);
        System.out.println("diff: "+diff);

        engine = GameEngine.getInstance().createGrid(this);

        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(this);

        shouldRun = true;
        timerHandler = new Handler();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                if(shouldRun) {
                    //Toast.makeText(MainActivity.this, "Checking if won...", Toast.LENGTH_SHORT).show();
                    if(engine.checkWon()) {
                        Intent i = new Intent(MainActivity.this, MainMenu.class);
                        i.putExtra("wonKey", true);
                        startActivity(i);
                    }
                    timerHandler.postDelayed(this, 500);
                }
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        timerHandler.postDelayed(timerRunnable, 0);
    }

    @Override
    public void onPause() {
        super.onPause();
        shouldRun = false;
        timerHandler.removeCallbacksAndMessages(timerRunnable);
    }
    @Override
    public void onClick(View v) {
        if(v == back) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Are you sure you want to go back?");
            builder.setMessage("This action will delete your progress");
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(MainActivity.this, MainMenu.class);
                            startActivity(i);
                        }
                    });
            builder.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    });
            builder.show();
        }
    }
}

    /*private void printSudoku(int Sudoku[][]) {
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                System.out.print(Sudoku[i][j] + "|");
            }
            System.out.println();
        }
    }*/

