package com.example.amore.instagramclient;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class PhotosActivity extends AppCompatActivity {

    public static final String CLIENT_ID = "a1e74432c63d4bf38784fbc82a10ec82";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPopularPhotos();
                swipeContainer.setRefreshing(false);
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light);

        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);

        photos = new ArrayList<>();
        aPhotos = new InstagramPhotosAdapter(this, photos);

        lvPhotos.setAdapter(aPhotos);
        fetchPopularPhotos();
    }

    public void fetchPopularPhotos() {
        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                aPhotos.clear();

                try {
                    JSONArray photosJSON = response.getJSONArray("data");

                    for (int i = 0; i < photosJSON.length(); i++) {
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        int commentCount = photoJSON.getJSONObject("comments").getJSONArray("data").length();
                        InstagramPhoto photo = new InstagramPhoto(
                                photoJSON.getJSONObject("user").getString("username"),
                                photoJSON.getJSONObject("caption").getString("text"),
                                photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url"),
                                photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height"),
                                photoJSON.getJSONObject("likes").getInt("count"),
                                photoJSON.getJSONObject("user").getString("profile_picture"),
                                photoJSON.getJSONObject("comments").getJSONArray("data").getJSONObject(commentCount - 1).getJSONObject("from").getString("username"),
                                photoJSON.getJSONObject("comments").getJSONArray("data").getJSONObject(commentCount - 1).getString("text")
                        );
                        photos.add(photo);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();;
                }

                aPhotos.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
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
