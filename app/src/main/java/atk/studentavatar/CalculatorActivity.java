package atk.studentavatar;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class CalculatorActivity extends BaseActivity {

    //create list/array for percentage
    //every add goes to list/array, update view with value in list/array
    //change hint in initial percentage to consecutive percentage
    //when 100% disable add button
    //enable clear when amount added
    //limit number to 100, throw remaining numbers

    private TableLayout tableLayout;
    private LinearLayout linearLayout;
    private Button add, clear;
    private EditText total, percentage;
    private TextView per_sum;
    private int rowTrack = 0;

    private float totalFee;
    private float paidFee;

    private List<Float> percent_list = new ArrayList<Float>();
    private List<Float> solid_list = new ArrayList<Float>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);

        initView();
        getTotal();
        enableAdd();
        //enableClear();

    }

    private void initView()
    {
        tableLayout = findViewById(R.id.tableTrack);
        linearLayout = findViewById(R.id.secondSegmentCalc);
        add = findViewById(R.id.add_btn);
        clear = findViewById(R.id.clear_btn);
        total = findViewById(R.id.total_edit);
        percentage = findViewById(R.id.percent_edit);
        per_sum = findViewById(R.id.sum_per_txt);
    }

    private void getTotal()
    {
        total.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(total.getText().toString().length() > 0)
                {
                    String s = total.getText().toString();
                    Log.d("calc", "in on focus");
                    totalFee = Float.valueOf(s);
                }
            }
        });
    }

    private void enableAdd()
    {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(percentage.getText().toString().length() > 0)
                {
                    percent_list.add(Float.valueOf(percentage.getText().toString()));
                    solid_list.add(totalFee * (percent_list.get(percent_list.size() - 1) * 0.01f));
                    updateViewAdd();
                    checkTableVisibility(true);
                    //deployRow();
                }
            }
        });
    }

    private void enableClear()
    {
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!percent_list.isEmpty())
                {
                    percent_list.remove(percent_list.size() - 1);
                    solid_list.remove(solid_list.size() - 1);
                    updateViewClear();
                    checkTableVisibility(false);
                    deleteRow();
                }
            }
        });
    }

    private void updateViewAdd()
    {
        String hold = "";
        String s = "";

        if(per_sum.getText().toString().length() > 0)
        {
            hold = per_sum.getText().toString();
            int i = hold.indexOf("E");

            if(i == -1)
            {
                s = hold + " + " + Float.toString(percent_list.get(percent_list.size() - 1));
            }
            else
            {
                s = Float.toString(percent_list.get(percent_list.size() - 1));
            }

        }

        per_sum.setText(s);
    }

    private void updateViewClear()
    {
        String s = per_sum.getText().toString();

        if (s.length() > 0)
        {
            int endIndex = s.lastIndexOf("+");
            //-1 means the '+' does not exists
            if (endIndex != -1)
            {
                s = s.substring(0, endIndex); // not forgot to put check if(endIndex != -1)
            }
            else
            {
                s = getString(R.string.valPerText);
            }
        }

        per_sum.setText(s);
    }

    private void checkTableVisibility(boolean b)
    {
        if(b)
        {
            if(linearLayout.getVisibility() == View.GONE)
            {
                linearLayout.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            if(per_sum.toString().equals(getString(R.string.valPerText)))
            {
                if(linearLayout.getVisibility() == View.VISIBLE)
                {
                    linearLayout.setVisibility(View.GONE);
                }
            }
        }
    }

    private void deployRow()
    {
        TextView install, amount, paid;

        //add new row in table
        LayoutInflater inflater = CalculatorActivity.this.getLayoutInflater();
        TableRow row = new TableRow(getApplicationContext());
        inflater.inflate(R.layout.calculator_row, row);

        tableLayout.addView(row);
        install = row.findViewById(R.id.intallrow_txt);
        amount = row.findViewById(R.id.amountrow_txt);
        paid = row.findViewById(R.id.paidrow_txt);

        String s = solid_list.get(solid_list.size() - 1).toString();

        install.setText(percent_list.size());
        amount.setText(s);

        rowTrack++;
    }

    private void deleteRow()
    {
        //row 0 is the table headings
        if(rowTrack > 0)
        {
            tableLayout.removeViewAt(rowTrack);
            rowTrack--;
        }
    }

}














