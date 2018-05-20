package atk.studentavatar;
import android.os.Bundle;
import android.util.Log;
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
    //limit number to 100, throw remaining numbers!!!!not yet do
    //clear edit text

    private TableLayout tableLayout;
    private LinearLayout linearLayout;
    private Button add, clear, paid, clear_paid;
    private EditText total, percentage, paid_edit;
    private TextView per_sum, per_total_sum, per_conv, paid_sum, paid_total_sum, remain_paid, remain_current_paid;
    private int rowTrack = 0;

    private float totalFee;

    //from percentage
    private List<Float> percent_list = new ArrayList<Float>();
    private List<Float> solid_list = new ArrayList<Float>();

    //for paid
    private List<Float> paid_list = new ArrayList<Float>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);

        initView();
        getTotal();
        enableAdd();
        enableClear();
        enablePaid();
        enableClearPaid();
    }

    private void initView()
    {
        tableLayout = findViewById(R.id.tableTrack);
        linearLayout = findViewById(R.id.secondSegmentCalc);

        add = findViewById(R.id.add_btn);
        clear = findViewById(R.id.clear_btn);
        paid = findViewById(R.id.paid_btn);
        clear_paid = findViewById(R.id.clear_paid_btn);

        total = findViewById(R.id.total_edit);
        percentage = findViewById(R.id.percent_edit);
        paid_edit = findViewById(R.id.paid_edit);

        //percentage sum list and total
        per_sum = findViewById(R.id.sum_per_txt);
        per_total_sum = findViewById(R.id.total_per_txt);

        //converted percentage sum list
        per_conv = findViewById(R.id.sum_conv_txt);

        //paid sum list and total
        paid_sum = findViewById(R.id.sum_paid_txt);
        paid_total_sum = findViewById(R.id.total_paid_txt);

        remain_paid = findViewById(R.id.remain_paid_txt);

        remain_current_paid = findViewById(R.id.remain_current_paid_txt);
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
                    add.setEnabled(true);
                    clear.setEnabled(true);
                    paid.setEnabled(true);
                    clear_paid.setEnabled(true);
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
                    solid_list.add((float) Math.round(totalFee * (percent_list.get(percent_list.size() - 1) * 0.01f)));
                    updateViewAdd();
                    //checkTableVisibility(true);
                    //deployRow();
                }

                if(!percent_list.isEmpty())
                {
                    percentage.setHint(getString(R.string.nextAmountText));
                }
                else
                {
                    percentage.setHint(getString(R.string.nowAmountText));
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
                    //checkTableVisibility(false);
                    //deleteRow();
                }
            }
        });
    }

    private void enablePaid()
    {
        paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(paid_edit.getText().toString().length() > 0)
                {
                    paid_list.add(Float.valueOf(paid_edit.getText().toString()));
                    updateViewPaid();
                }
            }
        });
    }

    private void enableClearPaid()
    {
        clear_paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!paid_list.isEmpty())
                {
                    paid_list.remove(paid_list.size() - 1);
                    updateViewClearPaid();
                }
            }
        });
    }

    private void updateViewAdd()
    {
        generalViewUpdateAdd(per_sum, "E", percent_list);

        percentage.setText("");
        generalSumTotal(percent_list, per_total_sum);

        generalViewUpdateAdd(per_conv, "A", solid_list);
    }

    private void updateViewClear()
    {
        generalViewUpdateClear(per_sum, getString(R.string.valPerText));

        generalSumTotal(percent_list, per_total_sum);

        generalViewUpdateClear(per_conv, getString(R.string.waitText));
    }

    private void updateViewPaid()
    {
        generalViewUpdateAdd(paid_sum, "A", paid_list);
        paid_edit.setText("");

        float totalPaid = generalSumTotal(paid_list, paid_total_sum);

        checkRemainChangeView(totalPaid);
        checkCurrentRemainChangeView(totalPaid);
    }

    private void updateViewClearPaid()
    {
        generalViewUpdateClear(paid_sum, getString(R.string.waitText));

        float totalPaid = generalSumTotal(paid_list, paid_total_sum);

        checkRemainChangeView(totalPaid);
        checkCurrentRemainChangeView(totalPaid);
    }

    private void checkRemainChangeView(float totalPaid)
    {
        float b = totalFee - totalPaid;
        String s = Float.toString(b);
        remain_paid.setText(s);
    }

    private void checkCurrentRemainChangeView(float totalPaid)
    {
        float b = 0f;
        float another = 0f;

        int install = 1;

        for(float i : solid_list)
        {
            b = b + i;
            if(totalPaid < b)
            {
                another = b - totalPaid;
                break;
            }
        }

        String s = Float.toString(another);

        b = 0f;

        for(float i : solid_list)
        {
            b = b + i;
            if(totalPaid >= b)
            {
                install++;
            }
            else
            {
                break;
            }
        }

        s = s + " on installment number " + Integer.toString(install);

        remain_current_paid.setText(s);
    }

    private float generalSumTotal(List<Float> list, TextView textView)
    {
        float b = 0f;

        for(float i : list)
        {
            b = b + i;
        }

        String s = "= ";
        s = s + Float.toString(b);

        textView.setText(s);
        return b;
    }

    private void generalViewUpdateAdd(TextView textView, String start_s, List<Float> theList)
    {
        String hold = "";
        String s = "";

        if(textView.getText().toString().length() > 0)
        {
            hold = textView.getText().toString();
            int i = hold.indexOf(start_s);

            if(i == -1)
            {
                s = hold + " + " + Float.toString(theList.get(theList.size() - 1));
            }
            else
            {
                s = Float.toString(theList.get(theList.size() - 1));
            }

        }

        textView.setText(s);
    }

    private void generalViewUpdateClear(TextView textView, String def_str)
    {
        String s = textView.getText().toString();

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
                s = def_str;
            }
        }

        textView.setText(s);
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














