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


	public class PostPhotoActivity extends Activity{

		TextView textTargetUri;
		ImageView targetImage;
		
		String URL = "http://developer.feedgeorge.com/user/login";
    	String email,password, apiKey;
    	
    	Button mSendBtn;
    	Button buttonLoadImage;
    	Button mShareBtn;
    	
    	EditText captionText;
    	
    	public static String  TAG = "FG";
    	
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
    				
    					try {
							executeMultipartPost();
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
		     setContentView(R.layout.main);
		     
		     context = this.getApplicationContext();
		     
		     
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

			
			
			//LocationProvider locationProvider = LocationManager.NETWORK_PROVIDER;
			
			myManager = (LocationManager) this.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
			
			
			myManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, loc );
			//myManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, loc );
			
			//locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
			
			 groupID = "2";
			 apiKey = "f3f0f6dbc5e442f6afc6687e59912f23"; //android
			 
			 //linearLayout =  (LinearLayout) findViewById(R.id.l);
			 
			 //testJson();
			 
		     postData();
		     //addPost();
		  
		     /*
		     buttonLoadImage.setOnClickListener(new Button.OnClickListener(){

	
		 public void onClick(View arg0) {
		  // TODO Auto-generated method stub
		  Intent intent = new Intent(Intent.ACTION_PICK,
		    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		  startActivityForResult(intent, 0);
		 }}); */
		     
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
		
		public void postData() {
	        // Create a new HttpClient and Post Header
	    	
			email = "chfoo@feedgeorge.com";
	    	password = "adm123m";
	    	
	    	//apiKey = "f8343c8ebd00438983353f03a4ada999";;
			
			Log.i("FG", "postData()");
	    	
			
			
	    	
	    	HttpParams params = new BasicHttpParams();
	        params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
	          
	          
	       // HttpClient httpclient = new DefaultHttpClient(params);
	        
	        HttpPost httppost = new HttpPost(URL);
	        Log.i(TAG, "sending response");
	        
	        try {
	            // Add your data

	        	
	            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	            //nameValuePairs.add(new BasicNameValuePair("cmd", "login"));
	            nameValuePairs.add(new BasicNameValuePair("email", email));
	            nameValuePairs.add(new BasicNameValuePair("password", password));
	            nameValuePairs.add(new BasicNameValuePair("apiKey", apiKey));
	            
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	           

	          

	            // Execute HTTP Post Request
	            //HttpResponse response = httpclient.execute(httppost);
	            
	            HttpResponse response = mHttpClient.execute(httppost, mHttpContext);
	            
	            //Header[] cookie = response.getHeaders("Set-Cookie");
	            
	          // Log.i(TAG,"cookie: "+cookie[0].getValue()); 
	           
	           Header[] headers = response.getHeaders("Set-Cookie");
	           
	           int length = headers.length;
	           String[] cookie = new String[length];
               
	            for (int i=0; i < length; i++) {
	                Header h = headers[i];
	                Log.i(TAG, "Header names: "+h.getName());
	                Log.i(TAG, "Header Value: "+h.getValue());
	                cookie[i] = h.getValue();
	            }
	           
	            
 
	               int start = cookie[length-1].indexOf("=");
	               int end = cookie[length-1].indexOf(";");
	               
	               securitykey = cookie[length-1].substring(start+1, end);
	               
	               Log.i(TAG,"securitykey:" +securitykey);
	            
	            //Log.i(TAG,"HEADER:" + response.getAllHeaders);
	            //Log.i(TAG,"HEADER:" + response.getAllHeaders() + EntityUtils.toString(response .getEntity()));
	            
	            Log.i(TAG, ">>>>>>>>>>>>>>>>>>>>>>>");
	            Header[] headers1 = response.getAllHeaders();
	            for (int i=0; i < headers1.length; i++) {
	                Header h = headers1[i];
	                Log.i(TAG, "Header names: "+h.getName());
	                Log.i(TAG, "Header Value: "+h.getValue());
	            }
	            
	            Log.i(TAG, ">>>>>>>>>>>>>>>>>>>>>>>");
	            
	            //Log.i(TAG,"RESPONSE:" + response.getStatusLine() + EntityUtils.toString(response .getEntity()));
	            
	          
	            inputStreamToString(response.getEntity().getContent());
	            //response.
	            
	           // inputStreamToString();
	            
	        } catch (ClientProtocolException e) {
	            // TODO Auto-generated catch block
	        	Log.i(TAG, "Protocol exception: "+e.toString());
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	        	Log.i(TAG, "IOException:" +e.toString());
	        }
	    }
		
		/*
		 * RETURN: {"success":true,"error":200,"reason":"Success","result":{"id":"6"}}
		 */

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
	    
		
		/*
		 * 3.137875,101.68644
		 */
		
		void post(){
			
			String lng, lat, caption;
			
			//lng = "3.137875";
			
			
			//lat = "101.68644";
			
			caption = "test";
			
			
			
		}
		
		
		/*
		 * groupId =  2 or 5
			lat = -1 (if no lat)
			lng = -1 (if no lng)
			text
			photo
		 */
		
		
		
		public void addPost(){
			
			
			
			String groupID, lat, lng, text, photo;
			
			groupID = "2";
			
			//loc.
			
			lat = "-1";
			lng = "-1";
			text = "feafwaffewfewgegwef";
			
			
			apiKey = "f3f0f6dbc5e442f6afc6687e59912f23"; //android
			//apiKey = "f8343c8ebd00438983353f03a4ada999";
			
			
		
	       
	        HttpPost httppost = new HttpPost("http://developer.feedgeorge.com/content/addpost");
	       
	        Log.i(TAG, "addpost - securitykey: "+securitykey);
	        
	       // httppost.setHeader("Cookie", securitykey);
	        
	        Log.i(TAG, "addpost - after set-cookie");
	        
	        try {
	            // Add your data

	        	
	            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	            //nameValuePairs.add(new BasicNameValuePair("cmd", "login"));
	            nameValuePairs.add(new BasicNameValuePair("groupId", groupID));
	            nameValuePairs.add(new BasicNameValuePair("lat", lat));
	            nameValuePairs.add(new BasicNameValuePair("lng", lng));
	            nameValuePairs.add(new BasicNameValuePair("text", text));
	            nameValuePairs.add(new BasicNameValuePair("apiKey", apiKey));
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	           
	         

	            // Execute HTTP Post Request
	            HttpResponse response = mHttpClient.execute(httppost,mHttpContext);
	            
	            
	            Header[] headers = response.getHeaders("Set-Cookie");
		           
		          // String cookie = headers[0].getValue();
		          
		           /*
		           Log.i(TAG, "Header names 1: "+headers[0].getName());
	               Log.i(TAG, "Header Value 1: "+cookie);
	               
	               int start = cookie.indexOf("=");
	               int end = cookie.indexOf(";");
	               
	               securitykey = cookie.substring(start+1, end);
	               
	               Log.i(TAG,"securitykey:" +securitykey);
		           */
	               
		           Log.i(TAG,"ADD POST >>>" );
		            for (int i=0; i < headers.length; i++) {
		                Header h = headers[i];
		                Log.i(TAG, "Header names: "+h.getName());
		                Log.i(TAG, "Header Value: "+h.getValue());
		            }
		           
		            
	            
	            
	            inputStreamToString(response.getEntity().getContent());
	            
	            
	            
	            
	        } catch (ClientProtocolException e) {
	            // TODO Auto-generated catch block
	        	Log.i(TAG, "Protocol exception: "+e.toString());
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	        	Log.i(TAG, "IOException:" +e.toString());
	        }
			
		}
		
		
		/*
		 * groupId =  2 or 5
			lat = -1 (if no lat)
			lng = -1 (if no lng)
			text
			photo
		 */
		
		public void executeMultipartPost() throws Exception {
			
			
			//String lng, lat, ;
			
			
			
			groupID = "2";
			
			
			
			//lat = "3.137875";
			//lng = "101.68644";
			
			lat = loc.getLat();
			lng = loc.getLong();
			
			Log.i(TAG, "lat: " +lat + "  lng:"+lng);
			
			caption = captionText.getText().toString();
			
			try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				
				bitmap.compress(CompressFormat.JPEG, 75, bos);
				
				
				//bitmap.compress(CompressFormat.PNG , 100, bos);
				
				byte[] data = bos.toByteArray();
				
				//HttpClient httpClient = new DefaultHttpClient();
				HttpPost postRequest = new HttpPost(
						"http://developer.feedgeorge.com/content/addpost");
				
				
				postRequest.setHeader("Set-Cookie", securitykey);
				
				ByteArrayBody bab = new ByteArrayBody(data, "test.jpg");
				
				// File file= new File("/mnt/sdcard/forest.png");
				// FileBody bin = new FileBody(file);
				MultipartEntity reqEntity = new MultipartEntity(
						HttpMultipartMode.BROWSER_COMPATIBLE);
				reqEntity.addPart("groupId", new StringBody(groupID));
				reqEntity.addPart("lng", new StringBody(lng));
				reqEntity.addPart("lat", new StringBody(lat));
				reqEntity.addPart("photo", bab);
				reqEntity.addPart("text", new StringBody(caption));
				reqEntity.addPart("apiKey", new StringBody(apiKey));
				
				postRequest.setEntity(reqEntity);
				
				
				HttpResponse response = mHttpClient.execute(postRequest, mHttpContext );
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent(), "UTF-8"));
				String sResponse;
				StringBuilder s = new StringBuilder();

				while ((sResponse = reader.readLine()) != null) {
					s = s.append(sResponse);
				}
				//System.out.println("Response: " + s);
				Log.i(TAG, "Response: " + s);
			} catch (Exception e) {
				// handle exception here
				Log.e(e.getClass().getName(), e.getMessage());
			}
		}
		
		/*
		 * Get content list
		 * groupId: 2, apiKey:apikey
		 * 
		 * return:
		 * RETURN: {"success":true,"error":200,"reason":"Success",
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
		
		public void getContentList(){
			
			String page = "1";
			groupID = "2";
			 apiKey = "f3f0f6dbc5e442f6afc6687e59912f23"; 
		
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
	            nameValuePairs.add(new BasicNameValuePair("groupId", "2")); 
	            nameValuePairs.add(new BasicNameValuePair("apiKey", "f3f0f6dbc5e442f6afc6687e59912f23"));
	            //nameValuePairs.add(new BasicNameValuePair("page", page));
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	                    
	            HttpResponse response = mHttpClient.execute(httppost, mHttpContext);
	            
	 
	      
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
		
		
		public void testJson() {
	        // Create a new HttpClient and Post Header
	    	
	    	Log.i(TAG, "postData()");
	    	
	    	
	    	HttpParams params = new BasicHttpParams();
	        params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
	          
	          
	        HttpClient httpclient = new DefaultHttpClient(params);
	        HttpPost httppost = new HttpPost("http://api.s.malaysiakini.com/mobile/service2.php");
	        Log.i("POST-TEST", "sending response");
	        
	        try {
	            // Add your data
	        	
	        	String username = "wkkor";
	        	String password = "1234";
	        	Log.i(TAG, username+" / "+password);
	        	String reversed_u = new StringBuffer(username).reverse().toString();
	        	
	        	String secure = MD5(reversed_u+"loq7D12T"+password);
	        	
	        	Log.i(TAG, reversed_u+" / "+secure);
	        	
	            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	            nameValuePairs.add(new BasicNameValuePair("cmd", "login"));
	            nameValuePairs.add(new BasicNameValuePair("u", username));
	            nameValuePairs.add(new BasicNameValuePair("p", password));
	            nameValuePairs.add(new BasicNameValuePair("secure", secure));
	            nameValuePairs.add(new BasicNameValuePair("debug", "1" ));
	            
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	           

	          

	            // Execute HTTP Post Request
	            HttpResponse response = httpclient.execute(httppost);
	            inputStreamToString(response.getEntity().getContent());
	            //response.
	            
	           // inputStreamToString();
	            
	        } catch (ClientProtocolException e) {
	            // TODO Auto-generated catch block
	        	Log.i("POST-TEST", "Protocol exception: "+e.toString());
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	        	Log.i("POST-TEST", "IOException:" +e.toString());
	        }
	    } 
		
	    public String MD5(String md5) {
	    	   try {
	    	        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
	    	        byte[] array = md.digest(md5.getBytes());
	    	        StringBuffer sb = new StringBuffer();
	    	        for (int i = 0; i < array.length; ++i) {
	    	          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
	    	       }
	    	        return sb.toString();
	    	    } catch (java.security.NoSuchAlgorithmException e) {
	    	    }
	    	    return null;
	    	}

		
		}