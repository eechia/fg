package com.feedgeorge.android;

import java.io.BufferedReader;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
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
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


	public class AddEvent extends Activity{
		
		
		private static final int START_DATE_DIALOG_ID = 3; 
		private static final int END_DATE_DIALOG_ID = 4; 
		
		Button startDateBtn, endDateBtn;
		TextView startDateTxt, endDateTxt;
		
		CharSequence strDate;
		String strDateTxt;
		
		HttpPostFG httppostFG;

		TextView textTargetUri;
		ImageView targetImage;
		
		ToggleButton location;
		
		String URL = "http://developer.feedgeorge.com/user/login";
    	String email,password, apiKey;
    	
    	
    	Button buttonLoadImage;
    	Button mSubmitBtn;
    	
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
    				
    				/*if(v.equals(mSendBtn)){
    					
    					//addPost();
    					
    					groupID = Constant.defaultGroupID;
    					
    					
    					lat = loc.getLat();
    					lng = loc.getLong();
    					
    					Log.i(TAG, "lat: " +lat + "  lng:"+lng);
    					
    					caption = captionText.getText().toString();
    				
    					try {
							//executeMultipartPost();
    						httppostFG.AddPost(groupID, lng, lat, bitmap, caption);
    					
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    					
    				}else*/
    				if(v.equals(buttonLoadImage)){
    							
    					Intent intent = new Intent(Intent.ACTION_PICK,
    						    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    							startActivityForResult(intent, 0);
    					
    				}else if(v.equals(mSubmitBtn)){
    					
    					
    					if(location.isChecked()){
    						lat = loc.getLat();
        					lng = loc.getLong();
    					}else{
    						lat = "-1";
        					lng = "-1";
    					}
    						
    					groupID = Constant.defaultGroupID;
    					caption = captionText.getText().toString();
    					
    						try {
								httppostFG.AddPost(groupID, lng, lat, bitmap, caption, Constant.ADD_EVENT, strDateTxt, null);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
    					
    					
    					//getContentList();
    					
    					/*
    					Intent intent = new Intent();
    				    intent.setClass(context,PostList.class);
    				    startActivity(intent);
    					*/
    					
    				}else if(v.equals(startDateBtn)){
    					showDialog(START_DATE_DIALOG_ID);
    					
    				}else if(v.equals(endDateBtn)){
    					showDialog(END_DATE_DIALOG_ID);
    				}
    				
    			}

				
    		};
    		
    	 

		 /** Called when the activity is first created. */
		 @Override
		 public void onCreate(Bundle savedInstanceState) {
		     super.onCreate(savedInstanceState);
		     setContentView(R.layout.add_event);
		     
		     context = this.getApplicationContext();
		     
		     httppostFG = HttpPostFG.getInstance();
		     
		     
		     startDateBtn = (Button) findViewById(R.id.startDateBtn);
		     startDateBtn.setOnClickListener(myClickListener);
		     endDateBtn = (Button) findViewById(R.id.endDateBtn);
		     endDateBtn.setOnClickListener(myClickListener);
		     
		     
		     buttonLoadImage = (Button)findViewById(R.id.loadimage);
		     textTargetUri = (TextView)findViewById(R.id.targeturi);
		     targetImage = (ImageView)findViewById(R.id.targetimage);
		     captionText = (EditText) findViewById(R.id.txtCaption);
		     
		     
		     
		     mSubmitBtn= (Button) findViewById(R.id.submitBtn);
		     mSubmitBtn.setOnClickListener(myClickListener);
		     
		  
		     
		     startDateTxt =  (TextView)findViewById(R.id.startDateTxt);
		     endDateTxt  =  (TextView)findViewById(R.id.endDateTxt);
		     
		 
		     buttonLoadImage.setOnClickListener(myClickListener);
		     
		     
		     
		     location = (ToggleButton) findViewById(R.id.toggleLocationBtn); 
		     location.setOnClickListener(myClickListener);
		     
		     mHttpClient = new DefaultHttpClient();
			 mHttpContext = new BasicHttpContext();
			 mCookieStore      = new BasicCookieStore();        
			 mHttpContext.setAttribute(ClientContext.COOKIE_STORE, mCookieStore);
			
			
			 loc = new MyLocListener();

			 myManager = (LocationManager) this.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

			 myManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, loc );
			
			
			 //groupID = "2";
			// apiKey = "f3f0f6dbc5e442f6afc6687e59912f23"; //android
			 
			
		     
		 }

		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK){
		 Uri targetUri = data.getData();
		 //textTargetUri.setText(targetUri.toString());
		
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
		
		
	
		
	
		 @Override
	     protected Dialog onCreateDialog(int id) {
			 
			 switch(id){
			 
			 case START_DATE_DIALOG_ID:
				 
				 DatePickerDialog dateDlg = new DatePickerDialog(this, 
				         new DatePickerDialog.OnDateSetListener() {
				          
				         public void onDateSet(DatePicker view, int year,
				                                             int monthOfYear, int dayOfMonth) 
				         {
				                    Time chosenDate = new Time();        
				                    chosenDate.set(dayOfMonth, monthOfYear, year);
				                    long dtDob = chosenDate.toMillis(true);
				                    //strDate = DateFormat.format("MMMM dd, yyyy", dtDob);
				                    CharSequence strDate = DateFormat.format("yyyy-MM-dd kk:mm", dtDob);
				                    
				                    startDateTxt.setText(strDate);
				                    strDateTxt = strDate.toString();
				                    Toast.makeText(context, 
				                         "START Date picked: " + strDate, Toast.LENGTH_SHORT).show();
				        }

						}, 2011,0, 1);
				             
				      dateDlg.setMessage("Start date");
				      return dateDlg;
				
			 case END_DATE_DIALOG_ID:
				 DatePickerDialog dateDlg1 = new DatePickerDialog(this, 
				         new DatePickerDialog.OnDateSetListener() {
				          
				         public void onDateSet(DatePicker view, int year,
				                                             int monthOfYear, int dayOfMonth) 
				         {
				                    Time chosenDate = new Time();        
				                    chosenDate.set(dayOfMonth, monthOfYear, year);
				                    long dtDob = chosenDate.toMillis(true);
				                    //CharSequence strDate = DateFormat.format("MMMM dd, yyyy", dtDob);
				                    CharSequence strDate = DateFormat.format("MM/dd/yy h:mmaa", dtDob);
				                    endDateTxt.setText(strDate);
				                    Toast.makeText(context, 
				                         "END Date picked: " + strDate, Toast.LENGTH_SHORT).show();
				        }

						}, 2011,0, 1);
				             
				      dateDlg1.setMessage("End date");
				      return dateDlg1;
				 
				      
			 }
			 
			 return null;
		 }
		 
		 @Override
	     protected void onPrepareDialog(int id, Dialog dialog) {
	             super.onPrepareDialog(id, dialog);
	             switch (id) {
	            
	                     
	             case START_DATE_DIALOG_ID:
			         DatePickerDialog dateDlg = (DatePickerDialog) dialog;
			         int iDay,iMonth,iYear;
			
			         // Always init the date picker to today's date
			         Calendar cal = Calendar.getInstance();
			         iDay = cal.get(Calendar.DAY_OF_MONTH);
			         iMonth = cal.get(Calendar.MONTH);
			         iYear = cal.get(Calendar.YEAR);
			         dateDlg.updateDate(iYear, iMonth, iDay);
			         break;
			         
	             case END_DATE_DIALOG_ID:
			         DatePickerDialog dateDlg1 = (DatePickerDialog) dialog;
			         int iDay1,iMonth1,iYear1;
			
			         // Always init the date picker to today's date
			         Calendar cal1 = Calendar.getInstance();
			         iDay1 = cal1.get(Calendar.DAY_OF_MONTH);
			         iMonth1 = cal1.get(Calendar.MONTH);
			         iYear1 = cal1.get(Calendar.YEAR);
			         dateDlg1.updateDate(iYear1, iMonth1, iDay1);
			         break;
			         
			         
	             }
	             return;
	     }
		

		
	  

		
		}