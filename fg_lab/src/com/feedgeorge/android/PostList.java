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

public class PostList extends ListActivity implements OnItemSelectedListener {
	
	
	
	public static String  TAG = "FG";
	
	//populate content
	 List<Post> postQueue = new ArrayList<Post>();
	 
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
	        setContentView(R.layout.postlist);
	        
	        getContentList();
	        getJoinedGroups();
	        context = this.getApplicationContext();
	        
	        filterSpin = (Spinner) findViewById(R.id.filterSpinner);
	        filterSpin.setOnItemSelectedListener(this);
	        
	        homeBtn = (Button) findViewById(R.id.homeBtn);
	        homeBtn.setOnClickListener(myBtnClickListener);
	        
	        placesBtn = (Button) findViewById(R.id.PlacesBtn);
	        placesBtn.setOnClickListener(myBtnClickListener);
	        
	        addBtn = (Button) findViewById(R.id.AddBtn);
	        addBtn.setOnClickListener(myBtnClickListener);
	        
	        searchBtn = (Button) findViewById(R.id.SearchBtn);
	        searchBtn.setOnClickListener(myBtnClickListener);
	        
	        settingsBtn = (Button) findViewById(R.id.SettingBtn);
	        settingsBtn.setOnClickListener(myBtnClickListener);

			@SuppressWarnings("rawtypes")
			ArrayAdapter aa = new ArrayAdapter(
					this,
					android.R.layout.simple_spinner_item, 
					filterByStr);

			aa.setDropDownViewResource(
			   android.R.layout.simple_spinner_dropdown_item);
			filterSpin.setAdapter(aa);
	        
	        /*
			final CharSequence[] items = {"Red", "Green", "Blue"};

			AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
			builder.setTitle("Pick a color");
			builder.setItems(items, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
			    }
			});
			AlertDialog alert = builder.create();
			alert.show();
	        */
	       setListAdapter(new postListAdapter(this,R.layout.postrow, postQueue));
			
	       ((BaseAdapter) getListAdapter()).notifyDataSetChanged();
	 }
	 

	/*
	public Context getContext(){
		
		return this.getBaseContext();
	}
	*/
	 public void getContentList(){
			
			String page = "1";
			String groupID = "2";
			 //apiKey = "f3f0f6dbc5e442f6afc6687e59912f23"; 
		
			 HttpParams params = new BasicHttpParams();
	        params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
	          
	          
	       // HttpClient httpclient = new DefaultHttpClient(params);
	        String contentURL = "http://developer.feedgeorge.com/content/list";
	        
	        HttpPost httppost = new HttpPost(contentURL);
	        Log.i(TAG, "getContentList>>>>>>");
	        
	       
	        
	        try {
	            // Add your data

	        	
	            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	            //nameValuePairs.add(new BasicNameValuePair("cmd", "login"));
	            nameValuePairs.add(new BasicNameValuePair("groupId", groupID)); 
	            nameValuePairs.add(new BasicNameValuePair("apiKey", "f3f0f6dbc5e442f6afc6687e59912f23"));
	            //nameValuePairs.add(new BasicNameValuePair("page", page));
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	                    
	            HttpResponse response = PostPhotoActivity.mHttpClient.execute(httppost, PostPhotoActivity.mHttpContext);
	            
	 
	      
	            Log.i(TAG, ">>>>>>>>>>>>>>>>>>>>>>>");
	             
	          
	           String posts = inputStreamToString(response.getEntity().getContent());
	           
	           populateList(posts);
	            
	        } catch (ClientProtocolException e) {
	            // TODO Auto-generated catch block
	        	Log.i(TAG, "Protocol exception: "+e.toString());
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	        	Log.i(TAG, "IOException:" +e.toString());
	        }
		}
	 
	 private String inputStreamToString(InputStream is) throws IOException{
			// TODO Auto-generated method stub
			String line = "";
	        StringBuilder total = new StringBuilder();
	        
	        Log.i(TAG,"inputStreamToString()");
	        
	        // Wrap a BufferedReader around the InputStream
	        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

	        
	        
	        // Read response until the end
	        while ((line = rd.readLine()) != null) { 
	            total.append(line); 
	        }
	        
	        
			Log.i(TAG,"RETURN: "+total.toString());
			
			return total.toString();
		} 
	 
	 public void populateList(String post){
			
			Log.i(TAG, "---- populateList()");
			
			Log.i(TAG, "---- Post: "+post);
			
			
			
			
			postQueue = new ArrayList<Post>();
				
			Post currentPost;
			try {
			
				JSONObject jsonResponse = new JSONObject(post.trim());
				
				String query_status, query_error, query_reason,result,content;
				
				query_status = jsonResponse.getString(Constant.SUCCESS);
				query_error = jsonResponse.getString(Constant.ERROR);
				query_reason = jsonResponse.getString(Constant.REASON);
				result = jsonResponse.getString(Constant.RESULT);
				
				Log.i(TAG, "query_status: "+query_status);
				Log.i(TAG, "query_error: "+query_error);
				Log.i(TAG, "query_reason: "+query_reason);
				Log.i(TAG, "result: "+result);
				
				JSONObject resultObject = new JSONObject(result);
				
				content = resultObject.getString(Constant.CONTENT);
				
				//JSONObject contentObject = new JSONObject(content);
				
				JSONArray contentArray = new JSONArray(content);
				
				String title;
				
				for(int i=0; i<contentArray.length();i++)
				{
					currentPost = new Post();
					JSONObject item = contentArray.getJSONObject(i);
					
					
					currentPost.setId(item.getString(Constant.ID));
					currentPost.setAuthorId(item.getString(Constant.AUTHOR_ID));
					currentPost.setAuthorName(item.getString(Constant.AUTHOR_NAME));
					currentPost.setGroupId(item.getString(Constant.GROUP_ID));
					currentPost.setImage(item.getString(Constant.IMAGE));
					currentPost.setLastUpdate(item.getString(Constant.LAST_UPDATE));
					currentPost.setLat(item.getString(Constant.LAT)); 
					currentPost.setLng(item.getString(Constant.LNG));				
					currentPost.setText(item.getString(Constant.TEXT));
					currentPost.setType(item.getString(Constant.TYPE));
					
					Log.i(TAG, "title: "+item.getString(Constant.TEXT));
					postQueue.add(currentPost);
					
				
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			readPost();
			
		}
		
		void readPost(){
			
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
		
		protected void onListItemClick(ListView l, View v, int position, long id) {
			super.onListItemClick(l, v, position, id);
			Log.i(TAG,"--TITLE:" +postQueue.get(position).getText() + "long:  "+postQueue.get(position).getLng()
					+ "lat: "+postQueue.get(position).getLat());
			
			Intent intent = new Intent();
		    intent.setClass(this,postView.class);
		    
		    
		    intent.putExtra(Constant.LAT, postQueue.get(position).getLat());
		    intent.putExtra(Constant.LNG, postQueue.get(position).getLng());
		    intent.putExtra(Constant.TEXT, postQueue.get(position).getText());
		    intent.putExtra(Constant.AUTHOR_NAME, postQueue.get(position).getAuthorName());
		    
		    startActivity(intent);
			
			
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
		
		//list joined groups
		
		 public void getJoinedGroups(){
				
				String page = "1";
				String groupID = "2";
				 //apiKey = "f3f0f6dbc5e442f6afc6687e59912f23"; 
			
				 HttpParams params = new BasicHttpParams();
		        params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		          
		          
		       // HttpClient httpclient = new DefaultHttpClient(params);
		        String contentURL = "http://developer.feedgeorge.com/user/listgroups";
		        
		        HttpPost httppost = new HttpPost(contentURL);
		        Log.i(TAG, ">>>>>> getJoinedGroups");
		        
		       
		        
		        try {
		            // Add your data

		        	
		            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		            //nameValuePairs.add(new BasicNameValuePair("cmd", "login"));
		           // nameValuePairs.add(new BasicNameValuePair("groupId", groupID)); 
		            nameValuePairs.add(new BasicNameValuePair("apiKey", "f3f0f6dbc5e442f6afc6687e59912f23"));
		            //nameValuePairs.add(new BasicNameValuePair("page", page));
		            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		                    
		            HttpResponse response = PostPhotoActivity.mHttpClient.execute(httppost, PostPhotoActivity.mHttpContext);
		            
		 
		      
		            Log.i(TAG, ">>>>>>>>>>>>>>>>>>>>>>>");
		             
		          
		           String posts = inputStreamToString(response.getEntity().getContent());
		           
		          
		            
		        } catch (ClientProtocolException e) {
		            // TODO Auto-generated catch block
		        	Log.i(TAG, "Protocol exception: "+e.toString());
		        } catch (IOException e) {
		            // TODO Auto-generated catch block
		        	Log.i(TAG, "IOException:" +e.toString());
		        }
			}
		 
}
