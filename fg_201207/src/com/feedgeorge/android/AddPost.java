package com.feedgeorge.android;

import java.io.BufferedReader;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;



import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


	public class AddPost extends Activity{
		
		HttpPostFG httppostFG;

		TextView textTargetUri;
		ImageView targetImage;
		
		String URL = "http://developer.feedgeorge.com/user/login";
    	String email,password, apiKey;
    	
    	Button mSendBtn;
    	Button buttonLoadImage;
    	Button mShareBtn;
    	
    	EditText captionText;
    	
    	public static String  TAG = "FG-1";
    	
    	 Bitmap bitmap;
    	 
    	 String securitykey;

    	Context context;
    	 
    	public static DefaultHttpClient mHttpClient;
    	public static BasicHttpContext mHttpContext;
		CookieStore mCookieStore;
		
		
		LocationManager myManager;
		MyLocListener loc;
		
		String groupID, lat, lng, caption, photo;
		
		//populate content
		 List<Post> postQueue = new ArrayList<Post>();
		
		//String apiKey = "f3f0f6dbc5e442f6afc6687e59912f23"; //android
    	 
    	 private OnClickListener myClickListener = new OnClickListener() {
    			public void onClick(View v) {
    				
    				if(v.equals(mSendBtn)){
    					
    					//addPost();
    					
    					groupID = Constant.defaultGroupID;
    					
    					
    					lat = loc.getLat();
    					lng = loc.getLong();
    					
    					Log.i(TAG, "lat: " +lat + "  lng:"+lng);
    					
    					caption = captionText.getText().toString();
    				
    					try {
							//executeMultipartPost();
    						httppostFG.AddPost(groupID, lng, lat, bitmap, caption, Constant.ADD_POST, null, null);
    					
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    					
    				}else if(v.equals(buttonLoadImage)){
    							
    					Intent intent = new Intent(Intent.ACTION_PICK,
    					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    					startActivityForResult(intent, 0);
    					
    				}else if(v.equals(mShareBtn)){
    					
    					//getContentList();
    					Intent intent = new Intent();
    				    intent.setClass(context,PostList.class);
    				    startActivity(intent);
    					
    					
    				}
    				
    			}

				
    		};
    		
    	 

		 /** Called when the activity is first created. */
		 @Override
		 public void onCreate(Bundle savedInstanceState) {
		     super.onCreate(savedInstanceState);
		     setContentView(R.layout.add_post);
		     
		     context = this.getApplicationContext();
		     
		     httppostFG = HttpPostFG.getInstance();
		     
		     buttonLoadImage = (Button)findViewById(R.id.loadimage);
		     textTargetUri = (TextView)findViewById(R.id.targeturi);
		     targetImage = (ImageView)findViewById(R.id.targetimage);
		     captionText = (EditText) findViewById(R.id.txtCaption);
		     
		     
		     
		     mShareBtn= (Button) findViewById(R.id.shareBtn);
		     mShareBtn.setOnClickListener(myClickListener);
		     
		  
		     
		     mSendBtn = (Button) findViewById(R.id.sendBtn);
		     mSendBtn.setOnClickListener(myClickListener);
		     buttonLoadImage.setOnClickListener(myClickListener);
		     
		     
		     mHttpClient = new DefaultHttpClient();
			 mHttpContext = new BasicHttpContext();
			 mCookieStore      = new BasicCookieStore();        
			mHttpContext.setAttribute(ClientContext.COOKIE_STORE, mCookieStore);
			
			loc = new MyLocListener();

			
			
			
			
			myManager = (LocationManager) this.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
			
			
			myManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, loc );
			
			
			 groupID = "2";
			 apiKey = "f3f0f6dbc5e442f6afc6687e59912f23"; //android
			 
			
		     
		 }

		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK){
		 Uri targetUri = data.getData();
		 textTargetUri.setText(targetUri.toString());
		
		 try {
		  bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
		  targetImage.setImageBitmap(bitmap);
		 } catch (FileNotFoundException e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
		 }
		}
		}
		
		//connect to website
		/*
		 * http://developer.feedgeorge.com/user/login
		 * Android app: f3f0f6dbc5e442f6afc6687e59912f23
		 * 
		 * email:  chfoo@feedgeorge.com
		 * password: adm123m
		 * data: {email: email, password: password, apiKey:apikey},
		 */
		
	
		
		
		
		
		
		/*
		 * FOR GEO LOCATION
		 */
		
		public class MyLocListener implements LocationListener
		{
		    double lat;
			//Here you will get the latitude and longitude of a current location.
			double lng;
			
			public MyLocListener(){
				
				Log.e(TAG, "MyLocListener()");
			}
		   
		    public void onLocationChanged(Location location) 
		    {
		            if(location != null)
		            {
		            		lat = location.getLatitude() ;
		            		lng = location.getLongitude();
		            		
		                     Log.i(TAG, "1-Latitude :" +location.getLatitude()); 
		                     Log.i(TAG, "2-Long :" +location.getLongitude());
		            }
		    }
		    
		    public void onProviderDisabled(String provider) {
		        // TODO Auto-generated method stub
		        
		    }
		    
		    public void onProviderEnabled(String provider) {
		        // TODO Auto-generated method stub
		        
		    }
		    
		    public void onStatusChanged(String provider, int status, Bundle extras) {
		        // TODO Auto-generated method stub
		        
		    }
		    
		    public String getLat(){
		    	
		    	return Double.toString(lat); 
		    	
		    }
			
		    public String getLong(){
		    	
		    	return Double.toString(lng); 
		    	
		    }
		}
		
		
		 /* RETURN: {"success":true,"error":200,"reason":"Success",
			"result":
			{"content":[
			{"id":"46",
			"type":"1",
			"text":"jln sultan",
			"groupId":"2",
			"authorId":"6",
			"authorName":"chfoo",
			"image":"http:\/\/assets.feedgeorge.com\/photos\/t\/7\/38f82706a95b41cbbd3ada3075612a19.jpg",
			"lat":"3.128668",
			"lng":"101.678940",
			"lastUpdate":"2012-06-05 17:08:35"},
		 */
		
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
		
		

		
	  

		
		}