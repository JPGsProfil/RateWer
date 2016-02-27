package com.example.wlg.ratewer.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.wlg.ratewer.Controller.EditorController;
import com.example.wlg.ratewer.Model.EditorCard;
import com.example.wlg.ratewer.Model.EditorData;
import com.example.wlg.ratewer.R;

/**
 * Created by sabine on 16.12.2015.
 */
public class CreatorMenuActivity extends ActionBarActivity {

    EditorController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_menu);
        /* final Button bBack = (Button) findViewById(R.id.bBack);
        bBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent firstIntent = new Intent(v.getContext(), StartActivity.class);
                startActivity(firstIntent);
            }
        });
*/
        final Button bNewSet = (Button) findViewById(R.id.bNewSet);
        bNewSet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent firstIntent = new Intent(v.getContext(), CreatorSetActivity.class);
                firstIntent.putExtra("data", controller.GetData());
                startActivity(firstIntent);
            }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        final Intent firstIntent = new Intent(this.findViewById(android.R.id.content).getContext(), StartActivity.class);
        startActivity(firstIntent);
    }

    int[] counter;

    @Override
    public void onStart()
    {
        super.onStart();
        int dipPaddingValue = (int) (3 * getResources().getDisplayMetrics().density);
        controller = new EditorController();
        //controller.writeFile(getApplicationContext());
        controller.readFile(getApplicationContext());
        final TableLayout lLayout = (TableLayout) findViewById(R.id.tableLayout1);
        counter = new int[controller.GetSetsSize()];
        for (int i = 1; i <= controller.GetSetsSize(); i++)
        {
            final int number;
            int j = i;
            TableRow row            = new TableRow(CreatorMenuActivity.this);
            TextView setName        = new TextView(CreatorMenuActivity.this);
            TextView onlineStatus   = new TextView(CreatorMenuActivity.this);

            setName.setText(controller.GetSetName(i));
            setName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
            setName.setPadding(dipPaddingValue, dipPaddingValue, dipPaddingValue, dipPaddingValue);
            setName.setBackgroundColor(0x00022FFF);

            onlineStatus.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
            onlineStatus.setPadding(dipPaddingValue, dipPaddingValue, dipPaddingValue, dipPaddingValue);
            onlineStatus.setText(String.valueOf(i));
            onlineStatus.setGravity(Gravity.RIGHT);
            row.setId(Integer.valueOf(i));
            row.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    final Intent firstIntent = new Intent(v.getContext(), CreatorSetActivity.class);
                    firstIntent.putExtra("data", controller.GetData());
                    firstIntent.putExtra("id",v.getId());
                    startActivity(firstIntent);
                }
            });
            row.addView(setName);
            row.addView(onlineStatus);
            lLayout.addView(row);

        }

    }
}
