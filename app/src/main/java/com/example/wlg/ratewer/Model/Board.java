package com.example.wlg.ratewer.Model;

import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;

/**
 * Created by Jean on 21.04.2015.
 */
/*
public class Board
{
    // kommt dann in die gameActivity
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        View.OnClickListener imageClickListener;
        imageClickListener = new OnClickListener()
        {

            @Override
            public void onClick(View v) {
                System.out.println("id clicked: " + v.getId());
            }
        };
    // Ende GameActivity

        for (int i = 0; i<images.length; i++)
        {
            GridLayout il = new GridLayout(this);

            int imageid = 0;
            ImageButton ib;
            BitmapDrawable imagebd;
            imageid = getResources().getIdentifier("drawable/" + images[i], null, getPackageName());
            imagebd = resizeImage(imageid);
            ib = new ImageButton(this);
            ib.setId(i);


            ib.setClickable(true);
            ib.setOnClickListener(imageClickListener);
            ib.setImageDrawable(imagebd);
            ib.setMinimumHeight(size);
            ib.setMinimumWidth(size);
            ib.setMaxHeight(size);
            ib.setMaxWidth(size);
            imageButtons.add(ib);
            il.addView(ib);
            System.out.println("id: " + ib.getId());

            ll.addView(il);
        }
        this.setContentView(sv);

    }

}
*/