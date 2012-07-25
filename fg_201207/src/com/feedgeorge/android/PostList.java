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

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PostList extends TabGroupActivity  implements OnItemSelectedListener {
//ActivityGroup implements OnItemSelectedListener {
//ListActivity implements OnItemSelectedListener {
	
	
	
	public static String  TAG = "FG-1-PostList";
	
	static //populate content
	 List<Post> postQueue = new ArrayList<Post>();
	
	public static String POST_SELECTION = "all";
	
	
	final CharSequence[] items = {"All", "Posts","Events","Surveys"};
	 
	 String[] filterByStr = {"All", "Posts","Events","Surveys"};
	 Button mapBtn, spinnerBtn;
	 Spinner filterSpin;
	  Context context;
	  TextView groupNameTxt, groupNameLabel ;
	  
	  ListView postListview;
	  ListActivity postListAct;
	  postListAdapter PLAdapter;
	  
	  ArrayList<String> mIdList;
	  
	  ArrayList<String> keyList, valueList;
	  String postID;
	  HttpPostFG httppostFG;
	
	/*
	 * Listen to the option selected by the users.
	 */

	private OnClickListener myBtnClickListener = new OnClickListener() {

		
		public void onClick(View v) {
			
			
			 
			if(v.equals(mapBtn)){
				
				/*
				Log.i(TAG,">>> mapBtn <<<<");
				
				Intent intent = new Intent(getParent(), SelectionView.class);
				 
				
				 
		         TabGroupActivity parentActivity = (TabGroupActivity)getParent();
		         parentActivity.startChildActivity("SelectionView", intent);
		         */
			} 
			else if(v.equals(spinnerBtn)){
				
				Log.i(TAG,">>> spinnerBtn <<<<");
				
				Intent intent = new Intent(getParent(), SelectionView.class);
				 
				
				 
		         TabGroupActivity parentActivity = (TabGroupActivity)getParent();
		         parentActivity.startChildActivity("SelectionView", intent);
			}
			
			/*
			else if(v.equals(placesBtn)){
				
				Log.i(TAG,">>> placesBtn");
				
				
				final CharSequence[] items = {"Red", "Green", "Blue"};

				AlertDialog.Builder builder = new AlertDialog.Builder(PostList.this);
				builder.setTitle("Pick a color");
				builder.setItems(items, new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int item) {
				        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
				    }
				});
				
				
				AlertDialog alert = builder.create();
				//alert.setOwnerActivity(context.ACTIVITY_SERVICE);
				alert.show();
				
			}else if(v.equals(addBtn)){
				
				Log.i(TAG,">>> addBtn");
				
			}else if(v.equals(searchBtn)){
				
				Log.i(TAG,">>> searchBtn");
				
			}else if(v.equals(settingsBtn)){
				
				Log.i(TAG,">>> settingsBtn");
				
			}
			
			*/
			
			}

	
	};

	
	 
	public void onCreate(Bundle icicle) {
	    	
	        super.onCreate(icicle);
	        setContentView(R.layout.postlist);
	        
	        
	        
			if (mIdList == null)
	        	mIdList = new ArrayList<String>();
	
	        
	        //getContentList();
	        //getJoinedGroups();
	        context = this.getApplicationContext();
	        httppostFG = HttpPostFG.getInstance();
	        
	        //filterSpin = (Spinner) findViewById(R.id.filterSpinner);
	        //filterSpin.setOnItemSelectedListener(this);
	        
	       
	        groupNameTxt = (TextView) findViewById(R.id.groupNameTxt);
	        groupNameTxt.setText(Constant.currentGroupName);
	        
	        groupNameLabel = (TextView) findViewById(R.id.groupNameLabel);
	        groupNameLabel.setText(Constant.currentGroupName);
	        
	        mapBtn = (Button) findViewById(R.id.Map);
	        mapBtn.setOnClickListener(myBtnClickListener);
	        
	        spinnerBtn = (Button) findViewById(R.id.spinnerBtn);
	        spinnerBtn.setOnClickListener(myBtnClickListener);
	        
	        
	        
			/*
			ArrayAdapter aa = new ArrayAdapter(
					this,
					android.R.layout.simple_spinner_item, 
					filterByStr);

			aa.setDropDownViewResource(
			   android.R.layout.simple_spinner_dropdown_item);
			filterSpin.setAdapter(aa);
	        */
			readPost();
			
			postListview = (ListView) findViewById(android.R.id.list);
			postListview.setOnItemClickListener(myListItemListener);
			
			PLAdapter = new postListAdapter(this,R.layout.postrow, postQueue);
			
			postListview.setAdapter(PLAdapter);
			PLAdapter.setNotifyOnChange(true);
			
			/*
	       setListAdapter(new postListAdapter(this,R.layout.postrow, postQueue));
			
	       ((BaseAdapter) getListAdapter()).notifyDataSetChanged();
	       */
	 }
	 

	
		
		static void readPost(){
			
			Log.i(TAG, "---- readPost()");
			
			Post currentPost;
			String title, image, lat, lng;
			for(int i=0;i<postQueue.size();i++){

				currentPost = new Post();
				currentPost = postQueue.get(i);
				title = currentPost.getText();
				image = currentPost.getImage();
				lat = currentPost.getLat();
				lng = currentPost.getLng();
				
				Log.i(TAG,"i:"+i+ " " + title + " | } +lat: "+lat + "lng: " +lng +" | image: "+image);
			}
			
			
			
		}
		
		
		
		private OnItemClickListener myListItemListener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> l, View v, int position, long id) {
				// TODO Auto-generated method stub
				Log.i(TAG,"--TITLE:" +postQueue.get(position).getText() + "long:  "+postQueue.get(position).getLng()
						+ "lat: "+postQueue.get(position).getLat());
				
				//Toast.makeText(this, "TITLE:" +postQueue.get(position).getText(), Toast.LENGTH_LONG).show();
				
				
				/*
				Intent intent = new Intent();
			    
				intent.setClass(context,postView.class);
			    
			    
			    intent.putExtra(Constant.LAT, postQueue.get(position).getLat());
			    intent.putExtra(Constant.LNG, postQueue.get(position).getLng());
			    intent.putExtra(Constant.TEXT, postQueue.get(position).getText());
			    intent.putExtra(Constant.AUTHOR_NAME, postQueue.get(position).getAuthorName());
			    
			   // startActivity(intent);
			    replaceContentView("activity3", intent);
				*/
				
				/*
				 Intent intent = new Intent(getParent(), PostMapView.class);
				 
				 intent.putExtra(Constant.LAT, postQueue.get(position).getLat());
				 intent.putExtra(Constant.LNG, postQueue.get(position).getLng());
				 intent.putExtra(Constant.TEXT, postQueue.get(position).getText());
				    
		         TabGroupActivity parentActivity = (TabGroupActivity)getParent();
		         parentActivity.startChildActivity("ViewPost", intent);
		         */
				postID = postQueue.get(position).getId();
				getComments();
				
				Intent intent = new Intent(getParent(), PostView.class);
				 
				 intent.putExtra(Constant.LAT, postQueue.get(position).getLat());
				 intent.putExtra(Constant.LNG, postQueue.get(position).getLng());
				 intent.putExtra(Constant.TEXT, postQueue.get(position).getText());
				 intent.putExtra(Constant.AUTHOR_NAME, postQueue.get(position).getAuthorName());
				 intent.putExtra(Constant.IMAGE, postQueue.get(position).getImage());
				 intent.putExtra(Constant.ID,postID );
				 
		         TabGroupActivity parentActivity = (TabGroupActivity)getParent();
		         parentActivity.startChildActivity("ViewPost", intent);
			}
			
		};
		
		
		
		public void getComments(){
			keyList = new ArrayList<String>();
			valueList = new ArrayList<String>();
			
			Log.i(TAG, "POSTid: "+postID);
			keyList.add("contentId");
			valueList.add(postID);
			keyList.add("page");
			valueList.add("1");
			
			httppostFG.postToServer(Constant.GET_COMMENTS, keyList, valueList);
		}
		

		
		
		
		
		/*
		protected void onListItemClick(ListView l, View v, int position, long id) {
			super.onListItemClick(l, v, position, id);
			Log.i(TAG,"--TITLE:" +postQueue.get(position).getText() + "long:  "+postQueue.get(position).getLng()
					+ "lat: "+postQueue.get(position).getLat());
			
			Toast.makeText(this, "TITLE:" +postQueue.get(position).getText(), Toast.LENGTH_LONG).show();
			
			Intent intent = new Intent();
		    
			intent.setClass(this,postView.class);
		    
		    
		    intent.putExtra(Constant.LAT, postQueue.get(position).getLat());
		    intent.putExtra(Constant.LNG, postQueue.get(position).getLng());
		    intent.putExtra(Constant.TEXT, postQueue.get(position).getText());
		    intent.putExtra(Constant.AUTHOR_NAME, postQueue.get(position).getAuthorName());
		    
		    startActivity(intent);
			
			
			//Toast.makeText(this, "TITLE:" +postQueue.get(position).getText(), Toast.LENGTH_LONG).show();
		}	
		*/
		
		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			//selection.setText(items[position]);
			Log.i(TAG, "item selected: "+filterByStr[position]);
		}

		public void onNothingSelected(AdapterView<?> parent) {
			//selection.setText("");
		}
		
		public static void setPostQueue(ArrayList<Post> postQ){
			Log.i(TAG, "PostList.setPostQueue()");
			postQueue = postQ;
			//readPost();
		}
		
		
		public void replaceContentView(String id, Intent newIntent) {
			View view1 = getLocalActivityManager().startActivity
					(id,newIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)) .getDecorView(); this.setContentView(view1);
					//(id,newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)) .getDecorView(); this.setContentView(view);
		
			/*
			View view = ((ActivityGroup) context)
					.getLocalActivityManager()
					.startActivity(id,
					newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
					.getDecorView();
					((Activity) context).setContentView(view);
	*/
		
		}   
		
		/*
		@Override
		public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if (MathHelper.SHOW_DETAILS) {
		Log.e("back", "pressed accepted");
		Constants.LIST_ACTIVITY = 1;
		Constants.SHOW_DETAILS = false;
		Intent intent = new Intent(this, Tab_widget.class);
		startActivity(intent);
		finish();
		}
		
		}
		*/
		
		
		/**
		* This is called when a child activity of this one calls its finish method.
		* This implementation calls {@link LocalActivityManager#destroyActivity} on the child activity
		* and starts the previous activity.
		* If the last child activity just called finish(),this activity (the parent),
		* calls finish to finish the entire group.
		*/
		/*
		@Override
		public void finishFromChild(Activity child) {
		LocalActivityManager manager = getLocalActivityManager();
		int index = mIdList.size()-1;

		if (index < 1) {
		finish();
		return;
		}
		
			 
		}
		*/

		/**
		* Starts an Activity as a child Activity to this.
		* @param Id Unique identifier of the activity to be started.
		* @param intent The Intent describing the activity to be started.
		* @throws android.content.ActivityNotFoundException.
		*/
		/*
		public void startChildActivity(String Id, Intent intent) {
		Window window = getLocalActivityManager().startActivity(Id,intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		if (window != null) {
		mIdList.add(Id);
		setContentView(window.getDecorView());
		}
		}
		*/
		
		
		 @Override
		    public boolean onKeyDown(int keyCode, KeyEvent event) {
		        if (keyCode == KeyEvent.KEYCODE_BACK) {
		            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
		            return true;
		        }
		        return super.onKeyDown(keyCode, event);
		    }
		
		/**
		* Overrides the default implementation for KeyEvent.KEYCODE_BACK
		* so that all systems call onBackPressed().
		*/
		@Override
		public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
		onBackPressed();
		return true;
		}
		return super.onKeyUp(keyCode, event);
		}
		
		/**
		* If a Child Activity handles KeyEvent.KEYCODE_BACK.
		* Simply override and add this method.
		*/
		@Override
		public void onBackPressed () {
		int length = mIdList.size();
			if ( length > 1) {
			Activity current = getLocalActivityManager().getActivity(mIdList.get(length-1));
			current.finish();
			Log.i(TAG,"!!!!!selection: "+POST_SELECTION);
			Toast.makeText(PlacesList.getAppContext(),
					"selection: "+POST_SELECTION, Toast.LENGTH_SHORT).show();
			
			}
		}
		
		/*
		@Override
		public void onBackPressed() {
		TabGroupActivity parentActivity = (TabGroupActivity)getParent();
		parentActivity.onBackPressed();
		}
		*/

}
