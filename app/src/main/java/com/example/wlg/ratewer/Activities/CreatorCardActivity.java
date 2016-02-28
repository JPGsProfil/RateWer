package com.example.wlg.ratewer.Activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.wlg.ratewer.Controller.EditorController;
import com.example.wlg.ratewer.Model.EditorCard;
import com.example.wlg.ratewer.Model.EditorSet;
import com.example.wlg.ratewer.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;

/**
 * Created by Sabine on 16.12.2015.
 */
//TODO: gallery öffnen, speicher funktion, lösch funktion


public class CreatorCardActivity extends ActionBarActivity {

    private static final int SELECT_PICTURE = 1;

    private String selectedImagePath;

    private String dataString;

    private EditorController controller;

    private EditorCard Card;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_creator_card);

        //save progress of new card to the set and clean cache of the activity for new card
        final Button bNewCard = (Button) findViewById(R.id.bNextNewCard);
        bNewCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent firstIntent = new Intent(v.getContext(), CreatorCardActivity.class);

                Card.setCardName(((EditText) findViewById(R.id.eCardName)).getText().toString());
                Card.addCardAttribute(((EditText) findViewById(R.id.eAttr1)).getText().toString());
                Card.addCardAttribute(((EditText) findViewById(R.id.eAttr2)).getText().toString());
                Card.addCardAttribute(((EditText) findViewById(R.id.eAttr3)).getText().toString());
                Card.setID(controller.GetSet(getIntent().getExtras().getInt("setPos")).getCardCount()+1);

                if(!getIntent().hasExtra("cardPos"))
                {
                    Card.setID(controller.GetSet(getIntent().getExtras().getInt("setPos")).getCardCount()+1);

                    controller.GetSet(getIntent().getExtras().getInt("setPos")).addCard(Card);
                }else{
                    controller.GetSet(getIntent().getExtras().getInt("setPos")).setCard(Card.getID(),Card);
                }
                controller.writeFile(getApplicationContext());

                startActivity(firstIntent);
            }
        });

        //Save progress
        final Button bDone = (Button) findViewById(R.id.bDoneCard);
        bDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent firstIntent = new Intent(v.getContext(), CreatorSetActivity.class);
                firstIntent.putExtra("id", getIntent().getExtras().getInt("setPos"));
                firstIntent.putExtra("data", getIntent().getExtras().getString("data"));
                Card.setCardName(((EditText) findViewById(R.id.eCardName)).getText().toString());

                if(!getIntent().hasExtra("cardPos"))
                {
                    Card.setID(controller.GetSet(getIntent().getExtras().getInt("setPos")-1).getCardCount() + 1);
                    Card.addCardAttribute(((EditText) findViewById(R.id.eAttribute1)).getText().toString());
                    Card.addCardAttribute(((EditText)findViewById(R.id.eAttribute2)).getText().toString());
                    Card.addCardAttribute(((EditText) findViewById(R.id.eAttribute3)).getText().toString());

                    controller.GetSet(getIntent().getExtras().getInt("setPos")-1).addCard(Card);
                }else{
                    Card.setCardAttribute(0,((EditText) findViewById(R.id.eAttribute1)).getText().toString());
                    Card.setCardAttribute(1,((EditText)findViewById(R.id.eAttribute2)).getText().toString());
                    Card.setCardAttribute(2,((EditText) findViewById(R.id.eAttribute3)).getText().toString());

                    controller.GetSet(getIntent().getExtras().getInt("setPos")-1).setCard(Card.getID(),Card);

                    getIntent().removeExtra("cardPos");
                }

                controller.writeFile(getApplicationContext());

                getIntent().removeExtra("setPos");
                getIntent().removeExtra("data");

                startActivity(firstIntent);
            }
        });

        //Cancel Card creation
        final Button bDelete = (Button) findViewById(R.id.bDelete);
        bDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent firstIntent = new Intent(v.getContext(), CreatorSetActivity.class);

                controller.GetSet(getIntent().getExtras().getInt("setPos")).removeCard(getIntent().getExtras().getInt("setPos"));

                controller.writeFile(v.getContext());
                firstIntent.putExtra("id", getIntent().getExtras().getInt("setPos"));
                firstIntent.putExtra("data", getIntent().getExtras().getInt("data"));
                getIntent().removeExtra("setPos");
                getIntent().removeExtra("data");
                if(getIntent().hasExtra("cardPos"))
                {
                    getIntent().removeExtra("cardPos");
                }
                controller.writeFile(getApplicationContext());

                startActivity(firstIntent);
            }
        });

        findViewById(R.id.iImage)
                .setOnClickListener(new View.OnClickListener() {

                    public void onClick(View arg0) {
                        final Intent firstIntent = new Intent(arg0.getContext(), CreatorCardActivity.class);
                        startActivity(firstIntent);
                    }
                });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Integer.parseInt(Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        final Intent firstIntent = new Intent(this.findViewById(android.R.id.content).getContext(), CreatorSetActivity.class);
        startActivity(firstIntent);
    }

    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();

        Card = new EditorCard();

        dataString = getIntent().getExtras().getString("data");
        controller = new EditorController();
        controller.FillData(dataString);

        EditorSet set = new EditorSet();

        EditText eName = (EditText) findViewById(R.id.eCardName);
        TextView attr1 = (TextView) findViewById(R.id.tAttr1);
        TextView attr2 = (TextView) findViewById(R.id.tAttr2);
        TextView attr3 = (TextView) findViewById(R.id.tAttr3);

        EditText eAttr1 = (EditText) findViewById(R.id.eAttribute1);
        EditText eAttr2 = (EditText) findViewById(R.id.eAttribute2);
        EditText eAttr3 = (EditText) findViewById(R.id.eAttribute3);

        if (getIntent().hasExtra("setPos")) {
            set = controller.GetSet(getIntent().getExtras().getInt("setPos")-1);
            String[] attributes = set.getAttributes().split(",");
            attr1.setText(attributes[0]);
            attr2.setText(attributes[1]);
            attr3.setText(attributes[2]);

        }
        Button btn = (Button) findViewById(R.id.bDelete);
        if (getIntent().hasExtra("cardID")) {
            btn.setVisibility(View.VISIBLE);
            EditorCard card = set.getCard(getIntent().getExtras().getInt("cardPos"));

            Card.setCardName(card.getCardName());
            Card.setCardName(card.getCardImagePath());
            Card.addCardAttribute(card.getCardAttribute(0));
            Card.addCardAttribute(card.getCardAttribute(1));
            Card.addCardAttribute(card.getCardAttribute(2));
            Card.setID(getIntent().getExtras().getInt("cardPos"));

            eName.setText(card.getCardName());
            eAttr1.setText(card.getCardAttribute(0));
            eAttr2.setText(card.getCardAttribute(1));
            eAttr3.setText(card.getCardAttribute(2));
            ImageButton img = (ImageButton) findViewById(R.id.iImage);
            if (card.getCardImagePath().isEmpty())
            {

                img.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.si_manjula));

            }else
            {
                File file = new File(card.getCardImagePath());

                if (file.exists())
                {
                    Bitmap bImage = BitmapFactory.decodeFile(file.getAbsolutePath());
                    img.setImageBitmap(bImage);
                }else{
                    img.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.si_manjula));
                }
            }
        } else
        {
            btn.setVisibility(View.INVISIBLE);
            eName.setText("Testkarte");
            eAttr1.setText("Test1");
            eAttr2.setText("Test2");
            eAttr3.setText("Test3");
            ImageButton img = (ImageButton) findViewById(R.id.iImage);
            img.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.si_manjula));

        }

/*        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CreatorCard Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.wlg.ratewer.Activities/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
  */  }

/*    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CreatorCard Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.wlg.ratewer.Activities/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }*/
}