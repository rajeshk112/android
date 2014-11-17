package com.example.instagramviewer;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;


public class PhotosActivity extends Activity {
	public static final String CLIENT_ID = "dc1be73f26c64732afd38761487f2e78";
    private ArrayList<InstagramPhoto> photos;
	private InstagramPhotosAdapter aPhotos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        fetchPopularPhotos();
    }

    private void fetchPopularPhotos() {
    	photos = new ArrayList<InstagramPhoto>();
    	// Create an adapter and bind it to data in the array list
    	aPhotos = new InstagramPhotosAdapter(this, photos);
    	//Populate data into the list view
    	ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
    	//Set adapter to list view
    	lvPhotos.setAdapter(aPhotos);
    	// https://api.instagram.com/v1/media/popular?client_id=<clientID>
    	// { "data" => [x] => "images" => "standard_resolution" => "url" }
    	// { "data" => [x] => "user" => "username" }
    	// { "data" => [x] => "caption" => "text" }
    	
    	// Setup endpoint
    	String popularURL = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
    	// Create network client
    	AsyncHttpClient client = new AsyncHttpClient();
    	// Trigger the request
    	client.get(popularURL, new JsonHttpResponseHandler() {
    		//define success and failure
    		// Handle the successful request
    		
    		@Override
    		public void onSuccess(int statusCode,Header[] headers,JSONObject response) {
    			// successful request
    			JSONArray photosJSON = null;
    	    	try {
    				photos.clear();
    				photosJSON = response.getJSONArray("data");
    	    		for (int i=0;i < photosJSON.length();i++) {
    	    			JSONObject photoJSON = photosJSON.getJSONObject(i);
    	    			InstagramPhoto photo = new InstagramPhoto();
    	    			photo.username = photoJSON.getJSONObject("user").getString("username");
    	    			if (photoJSON.getJSONObject("caption") != null) { 
    	    					photo.caption = photoJSON.getJSONObject("caption").getString("text");
    	    			}
    	    			photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
    	    			photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
    	    			photo.likescount = photoJSON.getJSONObject("likes").getInt("count");
    	    			//Log.i("DEBUG",photo.toString());
    	    			photos.add(photo);
    	    		}
    	    		// Notified the adapter that it should populate new changes to the listView
    	    		aPhotos.notifyDataSetChanged();
    	    	} catch (JSONException e) {
    	    		e.printStackTrace();
    	    	}
    	    		
        		}
        		
        	@Override
        	public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        		// failure request
        	 	super.onFailure(statusCode,headers,responseString,throwable);
        		}
    	});
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
