package com.example.wlg.ratewer.Activities;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.ActionBarActivity;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wlg.ratewer.Controller.EditorController;
import com.example.wlg.ratewer.Model.EditorSet;
import com.example.wlg.ratewer.R;

/**
 * Created by Sabine on 16.12.2015.
 */

//TODO: dynammisches erstellen der Seite bei schon vorhandensein von daten

public class CreatorSetActivity extends ActionBarActivity {

    private static final int SELECT_PICTURE = 1;

    private String selectedImagePath;
    private EditorController controller;


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

                    if(getIntent().hasExtra("id"))
                    {
                        controller.RemoveSet(getIntent().getExtras().getInt("id") -1);
                    }
                    controller.writeFile(v.getContext());
                    getIntent().removeExtra("id");
                    getIntent().removeExtra("data");
                    startActivity(firstIntent);
                }
            });


            final Button bSave = (Button) findViewById(R.id.bSave);
            bSave.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    final Intent firstIntent = new Intent(v.getContext(), CreatorMenuActivity.class);

                    EditText name = (EditText)findViewById(R.id.eSetName);

                    String nameString = name.getText().toString();
                    if(nameString.isEmpty())
                        nameString = "Set Name";

                    EditText attr1 = (EditText)findViewById(R.id.eAttr1);

                    String attr1String = attr1.getText().toString();
                    if(attr1String.isEmpty())
                        attr1String = " ";
                    EditText attr2 = (EditText)findViewById(R.id.eAttr2);
                    String attr2String = attr2.getText().toString();
                    if(attr2String.isEmpty())
                        attr2String = " ";

                    EditText attr3 = (EditText)findViewById(R.id.eAttr3);
                    String attr3String = attr3.getText().toString();

                    if(attr3String.isEmpty())
                        attr3String = " ";

                    if(!getIntent().hasExtra("id"))
                    {

                        EditorSet newSet = new EditorSet();
                        newSet.SetName(nameString);

                        newSet.SetID(controller.GetSetsSize() + 1);
                        String attr = new String(attr1String + "," +  attr2String + "," + attr3String);
                        newSet.SetAttributes(attr);
                        controller.AddNewSet(newSet);

                    }else{

                        int position = getIntent().getExtras().getInt("id") - 1;
                        EditorSet editSet = controller.GetSet(position);
                        editSet.SetName(nameString);
                        editSet.SetAttributes(new String(attr1String + "," + attr2String + "," + attr3String));

                        getIntent().removeExtra("id");
                        getIntent().removeExtra("data");
                    }
                    controller.writeFile(getApplicationContext());
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
        //final Button bAddAttribute = (Button) findViewById(R.id.bAddAttribute);
        //final LinearLayout lLayout = (LinearLayout) findViewById(R.id.layoutWrap);
        /*bAddAttribute.setOnClickListener(new View.OnClickListener() {

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
        });*/
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

    public void onStart()
    {
        super.onStart();

        if(!getIntent().hasExtra("id"))
        {
            Button btn = (Button)findViewById(R.id.bDeleteSet);
            btn.setVisibility(View.INVISIBLE);
        }

        controller = new EditorController();
        String Data;
        if(getIntent().hasExtra("data"))
        {
            Data = getIntent().getExtras().getString("data");

            controller.FillData(Data);
            controller.GetSetsSize();
            if(getIntent().getExtras().containsKey("id"))
            {

                EditorSet set = controller.GetSet(getIntent().getExtras().getInt("id") - 1);

                EditText name = (EditText)findViewById(R.id.eSetName);
                name.setText(set.getSetName());


                String[] attributes = set.getAttributes ().split(",");
                if(attributes.length < 3)
                {
                    EditText attr1 = (EditText) findViewById(R.id.eAttr1);
                    attr1.setText(" ");

                    EditText attr2 = (EditText) findViewById(R.id.eAttr2);
                    attr2.setText(" ");

                    EditText attr3 = (EditText) findViewById(R.id.eAttr3);
                    attr3.setText(" ");

                }else
                {
                    EditText attr1 = (EditText) findViewById(R.id.eAttr1);
                    attr1.setText(attributes[0]);

                    EditText attr2 = (EditText) findViewById(R.id.eAttr2);
                    attr2.setText(attributes[1]);

                    EditText attr3 = (EditText) findViewById(R.id.eAttr3);
                    attr3.setText(attributes[2]);
                }

                final LinearLayout llwrap = (LinearLayout)findViewById(R.id.layoutWrap);

                for(int i = 4; i <= attributes.length; i++)
                {
/*                    LinearLayout ll = new LinearLayout(CreatorSetActivity.this);
                    ll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    ll.setOrientation(LinearLayout.HORIZONTAL);

                    TextView attLabel = new TextView(CreatorSetActivity.this);
                    attLabel.setWidth(147);
                    attLabel.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
                    attLabel.setText("Eigenschaft " + i);
                    attLabel.setTextColor(Color.parseColor("#79c1cc"));
                    attLabel.setEms(10);
                    attLabel.setTextSize(18);
                    attLabel.setPadding(10, 10, 10, 10);
                    attLabel.setTypeface(null, Typeface.BOLD);
                    attLabel.setGravity(Gravity.CENTER_VERTICAL);

                    EditText attrX = new EditText(CreatorSetActivity.this);
                    //attrX.setWidth(ActionBar.LayoutParams.MATCH_PARENT);
                    //attrX.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
                    attrX.setText(attributes[i - 1]);
                    attrX.setGravity(Gravity.CENTER_VERTICAL);

                    ll.addView(attLabel);
                    ll.addView(attrX);
                    llwrap.addView(ll);
  */              }




            }else{

            }
        }

    }
}