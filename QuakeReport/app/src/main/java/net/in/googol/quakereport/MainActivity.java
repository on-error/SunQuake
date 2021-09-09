package net.in.googol.quakereport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Earthquake>> {
    private ListView listView;
    private ArrayList<Earthquake> earthquakes = new ArrayList<>();
    EarthquakeAdapter adapter;
    private ProgressBar progressBar;
    private TextView emptyView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

        listView = findViewById(R.id.listView);
        emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);


        adapter = new EarthquakeAdapter(this, earthquakes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Uri uri = Uri.parse(earthquakes.get(i).getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

//        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
//        task.execute();



        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            getSupportLoaderManager().initLoader(1, null, this).forceLoad();
        } else {
            progressBar.setVisibility(View.GONE);
            emptyView.setText("No Internet Connection !!");
        }
    }

    @NonNull
    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int id, @Nullable Bundle args) {
        return new EarthquakeLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> data) {
        adapter.clear();
           if(data != null && !data.isEmpty()){
                adapter.addAll(data);
            }
        progressBar.setVisibility(View.GONE);

        emptyView.setText("No earthquakes found !!");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Earthquake>> loader) {
        adapter.clear();
    }

//    private class EarthquakeAsyncTask extends AsyncTask<Void, Void, ArrayList<Earthquake>>{
//
//        @Override
//        protected ArrayList<Earthquake> doInBackground(Void... voids) {
//            earthquakes = QueryUtil.extractEarthquakes();
//            return earthquakes;
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<Earthquake> earthquakes) {
//            adapter.clear();
//            if(earthquakes != null && !earthquakes.isEmpty()){
//                adapter.addAll(earthquakes);
//            }
//        }
//    }
}