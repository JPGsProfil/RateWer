package com.example.wlg.ratewer.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wlg.ratewer.Controller.EditorController;
import com.example.wlg.ratewer.R;

/**
 * Created by Sabine on 16.12.2015.
 */

//TODO: dynammisches erstellen der Seite bei schon vorhandensein von daten

public class CreatorSetActivity extends ActionBarActivity {

    private static final int SELECT_PICTURE = 1;

    private String selectedImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_set);

        final Button bAddCard = (Button) findViewById(R.id.bAddCard);
        bAddCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent firstIntent = new Intent(v.getContext(), CreatorCardActivity.class);
                startActivity(firstIntent);
            }
        });

            final Button bDelSet = (Button) findViewById(R.id.bDeleteSet);
            bDelSet.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    final Intent firstIntent = new Intent(v.getContext(), CreatorMenuActivity.class);
                    startActivity(firstIntent);
                }
            });


            final Button bSave = (Button) findViewById(R.id.bSave);
            bSave.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    final Intent firstIntent = new Intent(v.getContext(), CreatorMenuActivity.class);
                    EditorController controller = new EditorController();
                    controller.writeFile(getApplicationContext());
                    controller.readFile(getApplicationContext());
                    System.out.println("file erstellt");
                    startActivity(firstIntent);
                }
            });

        findViewById(R.id.iCard1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {editCard(arg0); }});

        findViewById(R.id.iCard2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) { editCard(arg0);} });

        findViewById(R.id.iCard3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) { editCard(arg0);}
        });

        findViewById(R.id.iCard4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) { editCard(arg0);}});


        //TODO: fix set up new attribute
        final Button bAddAttribute = (Button) findViewById(R.id.bAddAttribute);
        final LinearLayout lLayout = (LinearLayout) findViewById(R.id.layoutWrap);
        bAddAttribute.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                ViewGroup layout=new LinearLayout(CreatorSetActivity.this);
                layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                TextView tv1=new TextView(CreatorSetActivity.this);
                EditText et1 = new EditText(CreatorSetActivity.this);
                tv1.setText("Eigenschaft: ");
                et1.setText("Unbekannt");
                et1.setTextColor(Color.BLACK);
                tv1.setTextColor(Color.BLACK);
                TextView tv2=new TextView(CreatorSetActivity.this);
                tv2.setText("Test2");
                layout.addView(tv1);
                layout.addView(et1);
                lLayout.addView(layout);

            }
        });
        //bAddAttribute
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        final Intent firstIntent = new Intent(this.findViewById(android.R.id.content).getContext(), CreatorMenuActivity.class);
        startActivity(firstIntent);
    }

    public void editCard(View v){
        // in onCreate or any event where your want the user to
        // select a file
        final Intent firstIntent = new Intent(v.getContext(), CreatorCardActivity.class);
        startActivity(firstIntent);
    }
}