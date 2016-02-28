package com.example.wlg.ratewer.Activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.ImageView;
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

    // Image loading result to pass to startActivityForResult method.
    private static int LOAD_IMAGE_RESULTS = 1;
    private static final int SELECT_PICTURE = 1;

    private String selectedImagePath;

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
        int dipSizeValue = (int) (100 * getResources().getDisplayMetrics().density);

        setContentView(R.layout.activity_creator_card);

        //save progress of new card to the set and clean cache of the activity for new card
        final Button bNewCard = (Button) findViewById(R.id.bNextNewCard);
        bNewCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final Intent firstIntent = new Intent(v.getContext(), CreatorCardActivity.class);
                int count = controller.GetSet(getIntent().getExtras().getInt("setPos")-1).getCardCount();

                Card.setCardName(((EditText) findViewById(R.id.eCardName)).getText().toString());
                Card.addCardAttribute(((EditText) findViewById(R.id.eAttribute1)).getText().toString());
                Card.addCardAttribute(((EditText) findViewById(R.id.eAttribute2)).getText().toString());
                Card.addCardAttribute(((EditText) findViewById(R.id.eAttribute3)).getText().toString());

                if(!getIntent().hasExtra("cardPos"))
                {
                    Card.setID(count);

                    controller.GetSet(getIntent().getExtras().getInt("setPos")-1).addCard(Card);

                }else{
                    if(getIntent().hasExtra("cardPos") && getIntent().getExtras().getInt("cardPos") <  count-1)
                        firstIntent.putExtra("cardPos", Integer.valueOf(getIntent().getExtras().getInt("cardPos") + 1));

                    controller.GetSet(getIntent().getExtras().getInt("setPos") - 1).setCard(getIntent().getExtras().getInt("cardPos"), Card);
                }

                controller.writeFile(getApplicationContext());
                firstIntent.putExtra("setPos", getIntent().getExtras().getInt("setPos"));
                startActivity(firstIntent);
            }
        });

        //Save progress
        final Button bDone = (Button) findViewById(R.id.bDoneCard);
        bDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final Intent firstIntent = new Intent(v.getContext(), CreatorSetActivity.class);

                firstIntent.putExtra("id", getIntent().getExtras().getInt("setPos"));

                Card.setCardName(((EditText) findViewById(R.id.eCardName)).getText().toString());

                if(!getIntent().hasExtra("cardPos"))
                {
                    Card.setID(controller.GetSet(getIntent().getExtras().getInt("setPos")-1).getCardCount() + 1);
                    Card.addCardAttribute(((EditText) findViewById(R.id.eAttribute1)).getText().toString());
                    Card.addCardAttribute(((EditText)findViewById(R.id.eAttribute2)).getText().toString());
                    Card.addCardAttribute(((EditText) findViewById(R.id.eAttribute3)).getText().toString());

                    controller.GetSet(getIntent().getExtras().getInt("setPos")-1).addCard(Card);

                }else
                {

                    Card.setCardAttribute(0,((EditText) findViewById(R.id.eAttribute1)).getText().toString());
                    Card.setCardAttribute(1,((EditText)findViewById(R.id.eAttribute2)).getText().toString());
                    Card.setCardAttribute(2,((EditText) findViewById(R.id.eAttribute3)).getText().toString());

                    controller.GetSet(getIntent().getExtras().getInt("setPos")-1).setCard(getIntent().getExtras().getInt("cardPos"),Card);

                    getIntent().removeExtra("cardPos");
                }

                controller.writeFile(getApplicationContext());

                getIntent().removeExtra("setPos");

                startActivity(firstIntent);
            }
        });

        //Cancel Card creation
        final Button bDelete = (Button) findViewById(R.id.bDelete);
        bDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent firstIntent = new Intent(v.getContext(), CreatorSetActivity.class);

                controller.GetSet(getIntent().getExtras().getInt("setPos") - 1).removeCard(getIntent().getExtras().getInt("cardPos") - 1);

                controller.writeFile(v.getContext());

                firstIntent.putExtra("id", getIntent().getExtras().getInt("setPos"));

                getIntent().removeExtra("setPos");

                if (getIntent().hasExtra("cardPos")) {
                    getIntent().removeExtra("cardPos");
                }

                controller.writeFile(getApplicationContext());

                startActivity(firstIntent);
            }
        });
        final ImageButton imgBtn = (ImageButton)findViewById(R.id.iImage);
        findViewById(R.id.iImage).setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
                startActivityForResult(i, LOAD_IMAGE_RESULTS);
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
        firstIntent.putExtra("id", getIntent().getExtras().getInt("setPos"));
        firstIntent.removeExtra("setPos");
        startActivity(firstIntent);
    }

    public void onStart() {
        super.onStart();
        int dipPaddingValue = (int) (3 * getResources().getDisplayMetrics().density);
        Log.d("start", "GGGGGGGGGG");
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client.connect();
        if(Card == null) {
            Card = new EditorCard();
        }
        controller = new EditorController();
        controller.readFile(getApplicationContext());

        EditorSet set = new EditorSet();

        EditText eName = (EditText) findViewById(R.id.eCardName);
        TextView attr1 = (TextView) findViewById(R.id.tAttr1);
        TextView attr2 = (TextView) findViewById(R.id.tAttr2);
        TextView attr3 = (TextView) findViewById(R.id.tAttr3);

        EditText eAttr1 = (EditText) findViewById(R.id.eAttribute1);
        EditText eAttr2 = (EditText) findViewById(R.id.eAttribute2);
        EditText eAttr3 = (EditText) findViewById(R.id.eAttribute3);

        if (getIntent().hasExtra("setPos"))
        {

            set = controller.GetSet(getIntent().getExtras().getInt("setPos")-1);
            String[] attributes = set.getAttributes().split(",");
            attr1.setText(attributes[0]);
            attr2.setText(attributes[1]);
            attr3.setText(attributes[2]);

        }
        Button btn = (Button) findViewById(R.id.bDelete);

        if (getIntent().hasExtra("cardPos"))
        {
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
                img.setBackground(null);

                img.setImageBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.si_manjula));

            }else
            {
                File file = new File(card.getCardImagePath());

                if (file.exists())
                {
                    Bitmap bImage = BitmapFactory.decodeFile(file.getAbsolutePath());
                    img.setBackground(null);
                    img.setImageBitmap(bImage);

                }else{
                    img.setBackground(null);
                    img.setImageBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.si_manjula));
                }
            }
            img.setMaxHeight(dipPaddingValue);
            img.setMaxWidth(dipPaddingValue);
            img.setScaleType(ImageView.ScaleType.FIT_CENTER);
            img.setAdjustViewBounds(true);
        } else
        {
            btn.setVisibility(View.INVISIBLE);
            eName.setText("Testkarte");
            eAttr1.setText("Test1");
            eAttr2.setText("Test2");
            eAttr3.setText("Test3");
            ImageButton img = (ImageButton) findViewById(R.id.iImage);
            img.setMaxHeight(dipPaddingValue);
            img.setMaxWidth(dipPaddingValue);
            img.setScaleType(ImageView.ScaleType.FIT_CENTER);
            img.setImageBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.si_manjula));
            img.setBackground(null);
            img.setAdjustViewBounds(true);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == LOAD_IMAGE_RESULTS && resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
            Card.setCardImagePath(imagePath);
            ImageButton img = (ImageButton)findViewById(R.id.iImage);
            img.setImageBitmap (BitmapFactory.decodeFile(imagePath));
            img.setBackground(null);

            Card.setCardName(((EditText) findViewById(R.id.eCardName)).getText().toString());

            if(!getIntent().hasExtra("cardPos"))
            {
                Card.setID(controller.GetSet(getIntent().getExtras().getInt("setPos")-1).getCardCount() + 1);
                Card.addCardAttribute(((EditText) findViewById(R.id.eAttribute1)).getText().toString());
                Card.addCardAttribute(((EditText)findViewById(R.id.eAttribute2)).getText().toString());
                Card.addCardAttribute(((EditText) findViewById(R.id.eAttribute3)).getText().toString());

                controller.GetSet(getIntent().getExtras().getInt("setPos")-1).addCard(Card);

            }else
            {

                Card.setCardAttribute(0,((EditText) findViewById(R.id.eAttribute1)).getText().toString());
                Card.setCardAttribute(1,((EditText)findViewById(R.id.eAttribute2)).getText().toString());
                Card.setCardAttribute(2,((EditText) findViewById(R.id.eAttribute3)).getText().toString());

                controller.GetSet(getIntent().getExtras().getInt("setPos")-1).setCard(getIntent().getExtras().getInt("cardPos"),Card);

            }

            controller.writeFile(getApplicationContext());





            // close the cursor to not end with the RuntimeException!
            cursor.close();
        }
    }


}