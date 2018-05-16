package atk.studentavatar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;


public class CalculatorActivity extends BaseActivity {

    //create list/array for percentage
    //every add goes to list/array, update view with value in list/array
    //change hint in initial percentage to consecutive percentage
    //when 100% disable add button
    //enable clear when amount added
    //limit number to 100, throw remaining numbers

    private TableLayout tableLayout;
    private Button add, clear;
    private int rowTrack = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);

        tableLayout = findViewById(R.id.tableTrack);
        add = findViewById(R.id.add_btn);
        clear = findViewById(R.id.clear_btn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = CalculatorActivity.this.getLayoutInflater();
                TableRow row = new TableRow(getApplicationContext());
                inflater.inflate(R.layout.calculator_row, row);

                tableLayout.addView(row);
                rowTrack++;
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(rowTrack > 0)
                {
                    tableLayout.removeViewAt(rowTrack);
                    rowTrack--;
                }
            }
        });
    }

}
