package com.example.wlg.ratewer.Activities;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.wlg.ratewer.R;


public class GameActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        View.OnClickListener imageClickListener;
        imageClickListener = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                System.out.println("id clicked: " + v.getId());
            }
        };


        for (int i = 0; i<10; i++)
        {
            LinearLayout il = new LinearLayout(this);
            il.setOrientation(LinearLayout.HORIZONTAL);
            il.setMinimumHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            il.setMinimumWidth(LinearLayout.LayoutParams.WRAP_CONTENT);

            int imageid = 0;
            ImageButton ib;
            BitmapDrawable imagebd;
            imageid = getResources().getIdentifier("drawable/lego50x50", null, null);
            ib = new ImageButton(this);


            ib.setClickable(true);
            ib.setId(i);
            ib.setOnClickListener(imageClickListener);
            il.addView(ib);
            System.out.println("id: " + ib.getId());

            //ll.addView(il);
        }
        //this.setContentView(sv);

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
