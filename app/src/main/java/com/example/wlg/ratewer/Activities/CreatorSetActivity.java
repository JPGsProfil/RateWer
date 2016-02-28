package com.example.wlg.ratewer.Activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wlg.ratewer.Controller.EditorController;
import com.example.wlg.ratewer.Model.EditorSet;
import com.example.wlg.ratewer.R;

import java.util.jar.Attributes;

/**
 * Created by Sabine on 16.12.2015.
 */

public class CreatorSetActivity extends ActionBarActivity {

    private static final int SELECT_PICTURE = 1;

    private EditorController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_set);

        //Add new Card to the Set
        final Button bAddCard = (Button) findViewById(R.id.bAddCard);
        bAddCard.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                final Intent firstIntent = new Intent(v.getContext(), CreatorCardActivity.class);

                int pos = -1;

                if(getIntent().hasExtra("id"))
                {
                    pos = getIntent().getExtras().getInt("id");
                }

                //Save data
                saveData();

                if(pos  > -1)
                {
                    firstIntent.putExtra("setPos", pos);
                }else
                {
                    firstIntent.putExtra("setPos", controller.GetSetsSize());
                }

                getIntent().removeExtra("id");
                startActivity(firstIntent);

            }
        });

        //Delete the Set, Images from Setup Cards wont be effect and stay on the phone
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
                    startActivity(firstIntent);
                }
        });


        //Save the set
        final Button bSave = (Button) findViewById(R.id.bSave);
        bSave.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {
                    final Intent firstIntent = new Intent(v.getContext(), CreatorMenuActivity.class);
                    saveData();
                    getIntent().removeExtra("id");
                    startActivity(firstIntent);
                }
        });

/*        findViewById(R.id.iCard1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {editCard(arg0); }});

        findViewById(R.id.iCard2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) { editCard(arg0);} });

        findViewById(R.id.iCard3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) { editCard(arg0);}
        });

        findViewById(R.id.iCard4).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) { editCard(arg0);}});

*/
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


    //Android Return BUtton is used as Cancel, which wont save
    @Override
    public void onBackPressed() {
        final Intent firstIntent = new Intent(this.findViewById(android.R.id.content).getContext(), CreatorMenuActivity.class);
        if(getIntent().hasExtra("id"))
            getIntent().removeExtra("id");

        startActivity(firstIntent);
    }

    public void onStart()
    {

        super.onStart();
        int dipSizeValue = (int) (100 * getResources().getDisplayMetrics().density);

        if(!getIntent().hasExtra("id"))
        {
            Button btn = (Button)findViewById(R.id.bDeleteSet);
            btn.setVisibility(View.INVISIBLE);
        }

        controller = new EditorController();
        controller.readFile(getApplicationContext());

        controller.GetSetsSize();

        if(getIntent().getExtras().containsKey("id"))
        {
            EditorSet set = controller.GetSet(getIntent().getExtras().getInt("id") - 1);

            EditText name = (EditText)findViewById(R.id.eSetName);
            name.setText(set.getSetName());

            String[] attributes = set.getAttributes().split(",");

            if(attributes.length < 3)
            {
                EditText attr1 = (EditText) findViewById(R.id.eAttr1);
                attr1.setText("Haarfarbe");

                EditText attr2 = (EditText) findViewById(R.id.eAttr2);
                attr2.setText("Augenfarbe");

                EditText attr3 = (EditText) findViewById(R.id.eAttr3);
                attr3.setText("Job");

            }else
            {
                EditText attr1 = (EditText) findViewById(R.id.eAttr1);
                attr1.setText(attributes[0]);

                EditText attr2 = (EditText) findViewById(R.id.eAttr2);
                attr2.setText(attributes[1]);

                EditText attr3 = (EditText) findViewById(R.id.eAttr3);
                attr3.setText(attributes[2]);
            }

            LinearLayout hll = new LinearLayout(getApplicationContext());
            LinearLayout ll = (LinearLayout)findViewById(R.id.vPLL);

            if(set.getCardCount() > 0)
            {

                ImageButton img;
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(15,15,5,15);
                for (int i = 0; i < set.getCardCount();i++)
                {
                    if(i % 5==0)
                    {
                        hll = new LinearLayout(getApplicationContext());
                        hll.setOrientation(LinearLayout.HORIZONTAL);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                        hll.setLayoutParams(params);
                    }
                    img = new ImageButton(getApplicationContext());
                    // Now we need to set the GUI ImageView data with data read from the picked file.
                    if(set.getCard(i).getCardImagePath().isEmpty())
                    {
                        img.setImageBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.si_manjula));
                    }else{
                        img.setBackground(null);
                        img.setImageBitmap (BitmapFactory.decodeFile(set.getCard(i).getCardImagePath()));
                    }
                    img.setId(i);
                    img.setMaxWidth(dipSizeValue);
                    img.setMaxHeight(dipSizeValue);
                    img.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    img.setAdjustViewBounds(true);
                    img.setLayoutParams(lp);

                    img.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View arg0)
                        {
                            final Intent firstIntent = new Intent(arg0.getContext(), CreatorCardActivity.class);
                            saveData();

                            firstIntent.putExtra("cardPos", arg0.getId());
                            firstIntent.putExtra("setPos", getIntent().getExtras().getInt("id"));

                            getIntent().removeExtra("id");

                            startActivity(firstIntent);
                        }
                    });

                    hll.addView(img);
                    if(i % 5==0) {

                        ll.addView(hll);
                    }
                }
            }

            final LinearLayout llwrap = (LinearLayout)findViewById(R.id.layoutWrap);

            for(int i = 4; i <= attributes.length; i++)
            {
/*                   LinearLayout ll = new LinearLayout(CreatorSetActivity.this);
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
            set.getCardCount();



        }else{

        }
    }

    public void saveData()
    {


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
            newSet.setName(nameString);

            newSet.setID(controller.GetSetsSize() + 1);
            String attr = new String(attr1String + "," +  attr2String + "," + attr3String);
            newSet.setAttributes(attr);
            controller.AddNewSet(newSet);
            getIntent().putExtra("id",controller.GetSetsSize());

        }else
        {

            int position = getIntent().getExtras().getInt("id") - 1;

            EditorSet editSet = controller.GetSet(position);
            editSet.setName(nameString);
            editSet.setAttributes(new String(attr1String + "," + attr2String + "," + attr3String));
        }
        controller.writeFile(getApplicationContext());
    }
}