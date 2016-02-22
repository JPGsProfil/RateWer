package com.example.wlg.ratewer.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
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
                startActivity(firstIntent);
            }
        });

        final TableRow rSelectItem1 = (TableRow)findViewById(R.id.tableRow5);
        final TableRow rSelectItem2 = (TableRow)findViewById(R.id.tableRow6);
        final TableRow rSelectItem3 = (TableRow)findViewById(R.id.tableRow7);

        rSelectItem1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent firstIntent = new Intent(v.getContext(), CreatorSetActivity.class);
                EditCardSetFromList(firstIntent, 1);
            }
        });

        rSelectItem2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent firstIntent = new Intent(v.getContext(), CreatorSetActivity.class);
                EditCardSetFromList(firstIntent, 2);
            }
        });

        rSelectItem3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent firstIntent = new Intent(v.getContext(), CreatorSetActivity.class);
                EditCardSetFromList(firstIntent, 2);
            }
        });

    }

    public void EditCardSetFromList(Intent firstIntent, int id)
    {
        startActivity(firstIntent);
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


    @Override
    public void onStart()
    {
        super.onStart();
        controller = new EditorController();
        controller.writeFile(getApplicationContext());
        controller.readFile(getApplicationContext());
        final TableLayout lLayout = (TableLayout) findViewById(R.id.tableLayout1);
        for (int i = 1; i <= controller.GetSetsSize(); i++)
        {
            Log.d("test", "sadasd");
            TextView tv1=new TextView(CreatorMenuActivity.this);
            EditText et1 = new EditText(CreatorMenuActivity.this);
            tv1.setText("Eigenschaft: ");
            et1.setText("Unbekannt");
            et1.setTextColor(Color.BLACK);
            tv1.setTextColor(Color.BLACK);
            TextView tv2=new TextView(CreatorMenuActivity.this);
            tv2.setText("Test2");
            lLayout.addView(tv1);
            lLayout.addView(et1);

        }

    }
}
