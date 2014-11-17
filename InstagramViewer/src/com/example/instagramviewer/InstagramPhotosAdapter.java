package com.example.instagramviewer;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.*;

import com.squareup.picasso.Picasso;

import android.content.*;


public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Take data and return view for the data item
		// Get data item
		InstagramPhoto photo = getItem(position);
		// Check if we are using recycled view
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo,parent,false);
		}
		// Lookup the subview within the template
		TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
		ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.imgPhoto);
		// Populate the subviews with correct data
		tvCaption.setText(photo.caption);
		// Set the image height before loading
		imgPhoto.getLayoutParams().height = photo.imageHeight;
		// Reset the image from the recycled view
		imgPhoto.setImageResource(0);
		// Ask for the photo to be added to image view based on photo url
		// Background : Send a network request to the url, download the image bytes, convert into bitmap, resizing the image, insert bitmap into the imageview
		Picasso.with(getContext()).load(photo.imageUrl).into(imgPhoto);
		// Return view for the data item
		return convertView;
		
	}

	public InstagramPhotosAdapter(Context context, List<InstagramPhoto> photos) {
		super(context, android.R.layout.simple_list_item_1,photos);
	}

}
