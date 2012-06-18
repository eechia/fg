package com.feedgeorge.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class PlacesList extends ListActivity implements OnItemSelectedListener {
	
	
	
	public static String  TAG = "FG";
	
	//populate content
	 List<Group> groupQueue = new ArrayList<Group>();
	 
	 String[] filterByStr = {"All", "Posts","Events","Surveys"};
	 Button homeBtn, placesBtn, addBtn, searchBtn, settingsBtn;
	 Spinner filterSpin;
	  Context context;
	
	/*
	 * Listen to the option selected by the users.
	 */

	private OnClickListener myBtnClickListener = new OnClickListener() {
	


		
		public void onClick(View v) {
			
			Log.i(TAG,">>> myBtnClickListener!!!!!!: onClick");
			 
			if(v.equals(homeBtn)){
				
				Log.i(TAG,">>> homeBtn...........");
				
			}else if(v.equals(placesBtn)){
				
				Log.i(TAG,">>> placesBtn");
				
				
				final CharSequence[] items = {"Red", "Green", "Blue"};

				AlertDialog.Builder builder = new AlertDialog.Builder(PlacesList.this);
				builder.setTitle("Pick a color");
				builder.setItems(items, new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int item) {
				        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
				    }
				});
				
				
				AlertDialog alert = builder.create();
				//alert.setOwnerActivity(context.ACTIVITY_SERVICE);
				alert.show();
				//AlertDialog dialog;

				/*
		         final CharSequence[] items = { "Item1", "Item2" };
		        
		         AlertDialog.Builder builder = new AlertDialog.Builder(context);
		       
		        builder.setTitle("test");
		        
		        builder.setItems(items, new DialogInterface.OnClickListener() {
		        	
		            public void onClick(DialogInterface dialog, int pos) {
		            switch (pos) {
		                case 0:
		                {
		                    Toast.makeText(context,"Clicked on:"+items[pos],Toast.LENGTH_SHORT).show();
		                	Log.i(TAG,"Clicked on:"+items[pos]);
		                }break;
		            case 1:
		              		{
		                            	  Log.i(TAG,"Clicked on:"+items[pos]);        	  //Toast.makeText(this,"Clicked on:"+items[pos],Toast.LENGTH_SHORT).show();

		                      }break;
		        }
		        }});
		        
		        AlertDialog dialog= builder.create();
		        dialog.show();
			*/
			}else if(v.equals(addBtn)){
				
				Log.i(TAG,">>> addBtn");
				
			}else if(v.equals(searchBtn)){
				
				Log.i(TAG,">>> searchBtn");
				
			}else if(v.equals(settingsBtn)){
				
				Log.i(TAG,">>> settingsBtn");
				
			}
			
			}

	
	};

	
	 
	public void onCreate(Bundle icicle) {
	    	
	        super.onCreate(icicle);
	        setContentView(R.layout.placeslist);
	        
	      
	        context = this.getApplicationContext();
	       
	        /*
	        filterSpin = (Spinner) findViewById(R.id.filterSpinner);
	        filterSpin.setOnItemSelectedListener(this);
	        
	        homeBtn = (Button) findViewById(R.id.homeBtn);
	        homeBtn.setOnClickListener(myBtnClickListener);
	        */
	       
	       
	        groupQueue = PostList.joinedGroupsList;
	        
	       setListAdapter(new PlacesListAdapter(this,R.layout.placerow, groupQueue));
			
	       ((BaseAdapter) getListAdapter()).notifyDataSetChanged();
	 }
	 


		
		protected void onListItemClick(ListView l, View v, int position, long id) {
			
			
			super.onListItemClick(l, v, position, id);
			
			/*
			Log.i(TAG,"--TITLE:" +postQueue.get(position).getText() + "long:  "+postQueue.get(position).getLng()
					+ "lat: "+postQueue.get(position).getLat());
			
			Intent intent = new Intent();
		    intent.setClass(this,postView.class);
		    
		    
		    intent.putExtra(Constant.LAT, postQueue.get(position).getLat());
		    intent.putExtra(Constant.LNG, postQueue.get(position).getLng());
		    intent.putExtra(Constant.TEXT, postQueue.get(position).getText());
		    intent.putExtra(Constant.AUTHOR_NAME, postQueue.get(position).getAuthorName());
		    
		    startActivity(intent);
			*/
			
			//Toast.makeText(this, "TITLE:" +postQueue.get(position).getText(), Toast.LENGTH_LONG).show();
		}	
		
		
		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			//selection.setText(items[position]);
			Log.i(TAG, "item selected: "+filterByStr[position]);
		}

		public void onNothingSelected(AdapterView<?> parent) {
			//selection.setText("");
		}
		
		
		
		 
}
