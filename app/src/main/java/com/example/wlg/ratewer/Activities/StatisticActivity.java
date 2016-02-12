package com.example.wlg.ratewer.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.wlg.ratewer.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class StatisticActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        TextView tv_Name = (TextView) findViewById(R.id.tv_statistic_Name);
        tv_Name.setText("du");

        TextView tv_Won = (TextView) findViewById(R.id.tv_statistic_Won);
        tv_Won.setText("0 mal");

        TextView tv_Lost = (TextView) findViewById(R.id.tv_statistic_Lost);
        tv_Lost.setText("0 mal");



    }
}

