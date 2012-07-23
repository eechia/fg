package com.feedgeorge.android;

import java.util.List;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MyGroupAdapter extends ArrayAdapter<Group> {

	MyGroupPage context = null;
	List<Group> groupQueue = null;
	
	public static String  TAG = "FG";
	
	public MyGroupAdapter(MyGroupPage context,int resource, List<Group> postQueue) 
	{
		super(context, resource, postQueue);
		this.groupQueue = postQueue;
		 Log.i(TAG,"^^^MyGroupAdapter: groupQueue length: "+groupQueue.size());
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		
		if(rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.mygroups_row, parent, false);
		}

		TextView label = (TextView)rowView.findViewById(R.id.joinedGroupText);
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
}
