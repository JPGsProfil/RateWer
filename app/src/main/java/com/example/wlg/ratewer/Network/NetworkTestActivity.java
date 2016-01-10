package com.example.wlg.ratewer.Network;

import android.app.ListActivity;
import android.os.Bundle;


import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import com.example.wlg.ratewer.R;

public class NetworkTestActivity { //TODO  extends ListActivity implements Callback<TestCardContainer> {

    /*
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        ArrayAdapter<TestCard> arrayAdapter =
                new ArrayAdapter<TestCard>(this,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        new ArrayList<TestCard>());
        setListAdapter(arrayAdapter);
        setProgressBarIndeterminateVisibility(true);
        setProgressBarVisibility(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test_card_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setProgressBarIndeterminateVisibility(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.stackexchange.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // prepare call in Retrofit 2.0
        CardAPI stackOverflowAPI = retrofit.create(CardAPI.class);

        Call<TestCardContainer> call = stackOverflowAPI.loadCards();
        //asynchronous call
        call.enqueue(this);

        // synchronous call would be with execute, in this case you
        // would have to perform this outside the main thread
        // call.execute()

        // to cancel a running request
        // call.cancel();
        // calls can only be used once but you can easily clone them
        //Call<StackOverflowQuestions> c = call.clone();
        //c.enqueue(this);

        return true;
    }

    @Override
    public void onResponse(Response<TestCardContainer> response, Retrofit retrofit) {
        setProgressBarIndeterminateVisibility(false);
       ArrayAdapter<TestCard> adapter = (ArrayAdapter<TestCard>) getListAdapter();
       adapter.clear();
       adapter.addAll(response.body().items);
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(NetworkTestActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }*/
}
