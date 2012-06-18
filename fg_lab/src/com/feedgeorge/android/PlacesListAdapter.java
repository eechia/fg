package com.feedgeorge.android;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PlacesListAdapter extends ArrayAdapter<Group> {

	PlacesList context = null;
	List<Group> groupQueue = null;
	
	public static String  TAG = "FG";
	
	public PlacesListAdapter(PlacesList context,int resource, List<Group> postQueue) 
	{
		super(context, resource, postQueue);
		this.groupQueue = postQueue;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		
		if(rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.placerow, parent, false);
		}

		TextView label = (TextView)rowView.findViewById(R.id.groupText);
		label.setText(groupQueue.get(position).getName());
		
		/*
		ImageView imagePost = (ImageView) rowView.findViewById(R.id.imagePost);
		String photo_url = postQueue.get(position).getImage();
		URL newurl;
		try {
			
			newurl = new URL(photo_url);
			if(newurl != null){
				Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream()); 
				imagePost.setImageBitmap(mIcon_val);	
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		*/
		
		
		/*
		ImageView icon = (ImageView)rowView.findViewById(R.id.newsIcon);
		if(newsChannel[position].equals("Berita Harian")|
		newsChannel[position].equals("Utusan Malaysia")) {
		icon.setImageResource(R.drawable.malaysia);
		} else {
		icon.setImageResource(R.drawable.malaysia_bright);
		}
	*/
		return(rowView);
	
	}
	
	
	
	protected void onListItemClick(ListView l, View v, int position, long id) {
		//selection.setText("You have selected " + newsChannel[position]);
		Log.i(TAG,"Group:" +groupQueue.get(position).getName());
		
		//Toast.makeText(context, "+Group:" +groupQueue.get(position).getName()));
	}
		

	
	public void getImage(){
		
	}

	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

}


