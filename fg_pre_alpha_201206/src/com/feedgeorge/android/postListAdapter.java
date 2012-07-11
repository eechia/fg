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

public class postListAdapter extends ArrayAdapter<Post> {

	PostList context = null;
	List<Post> postQueue = null;
	
	public static String  TAG = "FG-1";
	
	TextView timestampTxt, authorTxt, commentsTxt;
	
	public postListAdapter(PostList context,int resource, List<Post> postQueue) 
	{
		super(context, resource, postQueue);
		this.postQueue = postQueue;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		
		if(rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.postrow, parent, false);
		}

		TextView label = (TextView)rowView.findViewById(R.id.postText);
		label.setText(postQueue.get(position).getText());
		
		TextView timestampTxt = (TextView)rowView.findViewById(R.id.timePostTxt);
		TextView authorTxt = (TextView)rowView.findViewById(R.id.authorPostTxt);
		TextView commentsTxt = (TextView)rowView.findViewById(R.id.commentPostTxt);
		
		//timestampTxt.setText(postQueue.get(position).getAuthorName()); 
		authorTxt.setText("Posted by " +postQueue.get(position).getAuthorName()); 
		commentsTxt.setText(postQueue.get(position).getCommentCount() + " comments");
		
		
		
		ImageView imagePost = (ImageView) rowView.findViewById(R.id.imagePost);
		String photo_url = postQueue.get(position).getImage();
		
		Log.i(TAG, "@@@@@ photo_url: "+ photo_url);
		
		
		URL newurl = null;
		try {
			
	
			
			if(photo_url.contains("http")){
				Log.i(TAG, "@@@@@ TRUE: "+ photo_url);
				newurl = new URL(photo_url);
				Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream()); 
				imagePost.setImageBitmap(mIcon_val);
			}
			
			/*
			if(newurl != null){
				
				Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream()); 
				imagePost.setImageBitmap(mIcon_val);	
			}
			*/
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		
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
		Log.i(TAG,"TITLE:" +postQueue.get(position).getText() + "long:  "+postQueue.get(position).getLng()
				+ "lat: "+postQueue.get(position).getLat());
		
		Toast.makeText(context, "+TITLE:" +postQueue.get(position).getText(), Toast.LENGTH_SHORT);
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


