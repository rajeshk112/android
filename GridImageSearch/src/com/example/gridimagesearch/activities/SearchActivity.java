package com.example.gridimagesearch.activities;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.gridimagesearch.R;
import com.example.gridimagesearch.R.id;
import com.example.gridimagesearch.R.layout;
import com.example.gridimagesearch.R.menu;
import com.example.gridimagesearch.adapters.ImageResultsAdapter;
import com.example.gridimagesearch.models.ImageResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;


public class SearchActivity extends Activity {
	private EditText etQuery;
	private GridView gvResults;
	private ArrayList<ImageResult> imageResults;
	private ImageResultsAdapter aimageResults;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        // Creates data source
        imageResults = new ArrayList<ImageResult>();
        //Attaches data source to adapter
        aimageResults = new ImageResultsAdapter(this,imageResults);
        // Link the adapter to adapter view
        gvResults.setAdapter(aimageResults);
    }

    private void setupViews() {
    	etQuery = (EditText) findViewById(R.id.etQuery);
    	gvResults = (GridView) findViewById(R.id.gvResults);
    	gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Launch the image display activity
				
				// Creating an intent
				Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
				// Get the image results to display
				ImageResult result = imageResults.get(position);
				// Pass image result into the intent
				//i.putExtra("url", result.fullUrl);
				i.putExtra("result", result);
				// Launch the new activity
				startActivity(i);
			}
    		
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    // Called when search button is clicked
    public void onImageSearch(View v) {
    	String query  = etQuery.getText().toString();
    	//Toast.makeText(this, "Search for: " + query, Toast.LENGTH_SHORT).show();
    	AsyncHttpClient client = new AsyncHttpClient();
    	String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8";
    	client.get(searchUrl, new JsonHttpResponseHandler() {
    		@Override
    		public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
    			Log.d("DEBUG", response.toString());
    			JSONArray imageResultsJson = null;
    			try {
    				imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
    				// clear items from the array when its a new search
    				imageResults.clear(); 
    				
    				aimageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
    			} catch (JSONException e) {
    				e.printStackTrace();
    			}
    			Log.i("INFO",imageResults.toString());
    		}
    	});	
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
