package com.example.miumiu.activities;

import static com.example.miumiu.utils.Constants.LIST_MONTH;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miumiu.R;
import com.shawnlin.numberpicker.NumberPicker;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private TextView dob, choose_dob;
    private EditText username, passwd, phone;
    private CheckBox checkMale, checkFemale, checkGay;
    private ImageButton back, finish;
    private RelativeLayout frame_dob;
    private Button btn_done;
    private com.shawnlin.numberpicker.NumberPicker month, day, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username_register);
        passwd = findViewById(R.id.pass_register);
        phone = findViewById(R.id.phone_register);
        choose_dob = findViewById(R.id.dob_register);
        checkMale = findViewById(R.id.checkbox_male);
        checkFemale = findViewById(R.id.checkbox_female);
        checkGay = findViewById(R.id.checkbox_gay);

        back = findViewById(R.id.btn_exit_register);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, AuthActivity.class));
                finish();
            }
        });

        finish = findViewById(R.id.btn_finish_register);

        chooseSex();
        chooseDoB();

    }

    private void chooseSex() {
        checkMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFemale.setChecked(false);
                checkGay.setChecked(false);
            }
        });

        checkFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkMale.setChecked(false);
                checkGay.setChecked(false);
            }
        });

        checkGay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFemale.setChecked(false);
                checkMale.setChecked(false);
            }
        });
    }

    private void chooseDoB() {
        frame_dob = findViewById(R.id.frame_dob);
        btn_done = findViewById(R.id.btn_done);

        choose_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username.setVisibility(View.INVISIBLE);
                passwd.setVisibility(View.INVISIBLE);
                phone.setVisibility(View.INVISIBLE);
                choose_dob.setVisibility(View.INVISIBLE);
                checkMale.setVisibility(View.INVISIBLE);
                checkFemale.setVisibility(View.INVISIBLE);
                checkGay.setVisibility(View.INVISIBLE);
                back.setVisibility(View.INVISIBLE);
                finish.setVisibility(View.INVISIBLE);

                frame_dob.setVisibility(View.VISIBLE);
                btn_done.setVisibility(View.VISIBLE);
            }
        });

        month = findViewById(R.id.pick_month);
        day = findViewById(R.id.pick_day);
        year = findViewById(R.id.pick_year);
        setDateDefault();
        setDate();

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username.setVisibility(View.VISIBLE);
                passwd.setVisibility(View.VISIBLE);
                phone.setVisibility(View.VISIBLE);
                choose_dob.setVisibility(View.VISIBLE);
                checkMale.setVisibility(View.VISIBLE);
                checkFemale.setVisibility(View.VISIBLE);
                checkGay.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
                finish.setVisibility(View.VISIBLE);

                frame_dob.setVisibility(View.INVISIBLE);
                btn_done.setVisibility(View.INVISIBLE);

                String hint = (day.getValue() < 10 ? '0' : "") + String.valueOf(day.getValue());
                hint = hint + '/' + (month.getValue() < 10 ? '0' : "") + month.getValue();
                hint = hint + '/' + year.getValue();

                choose_dob.setHint(hint);
            }
        });
    }

    private void setDate() {
        month.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                int m = view.getValue();
                int y = year.getValue();
                if (m == 4 || m == 6 || m == 9 || m == 11)
                    day.setMaxValue(30);
                else if (m == 2)
                {
                    if (y % 400 == 0 || (y % 4 == 0 && y % 100 != 0))
                        day.setMaxValue(29);
                    else
                        day.setMaxValue(28);
                }
                else
                    day.setMaxValue(31);
                setDoB(LIST_MONTH[month.getValue() - 1], String.valueOf(day.getValue()), String.valueOf(year.getValue()));
            }
        });

        day.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                setDoB(LIST_MONTH[month.getValue() - 1], String.valueOf(day.getValue()), String.valueOf(year.getValue()));
            }
        });

        year.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                int y = view.getValue();
                if (month.getValue() == 2)
                {
                    if (y % 400 == 0 || (y % 4 == 0 && y % 100 != 0))
                        day.setMaxValue(29);
                    else
                        day.setMaxValue(28);
                }
                setDoB(LIST_MONTH[month.getValue() - 1], String.valueOf(day.getValue()), String.valueOf(year.getValue()));
            }
        });
    }

    public void setDateDefault() {
        int[] month_30days = {4, 6, 9, 11};
        dob = findViewById(R.id.date_now);
        LocalDate date = LocalDate.now();
        month.setDisplayedValues(LIST_MONTH);
        month.setValue(date.getMonthValue());


        day.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return ((value < 10) ? "0" : "") + String.valueOf(value);
            }
        });
        if (Arrays.asList(month_30days).contains(date.getMonthValue()))
            day.setMaxValue(30);
        else if (date.getMonthValue() == 2) {
            if (date.getYear() % 400 == 0 || (date.getYear() % 4 == 0 && date.getYear() % 100 != 0))
                day.setMaxValue(29);
            day.setMaxValue(28);
        } else
            day.setMaxValue(31);

        year.setMaxValue(date.getYear());
        year.setValue(date.getYear());
        year.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.valueOf(value);
            }
        });

        setDoB(LIST_MONTH[month.getValue() - 1], String.valueOf(day.getValue()), String.valueOf(year.getValue()));
    }


    public void setDoB(String m, String d, String y) {
        dob.setText(m + ' ' + d + ", " + y);
    }
}