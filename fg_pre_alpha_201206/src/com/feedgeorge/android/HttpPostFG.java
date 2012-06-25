package com.feedgeorge.android;

import java.io.BufferedReader;
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
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class HttpPostFG {
	
	private static HttpPostFG instance = null;
	
	public static DefaultHttpClient mHttpClient;
	public static BasicHttpContext mHttpContext;
	CookieStore mCookieStore;
	
	public static String  TAG = "FG-1";
	Context context;
	
	 //pupulate nearby groups
	 public static ArrayList<Group> nearbyGroupsList;
	
	protected HttpPostFG(){
		
		mHttpClient = new DefaultHttpClient();
		mHttpContext = new BasicHttpContext();
		mCookieStore      = new BasicCookieStore();        
		mHttpContext.setAttribute(ClientContext.COOKIE_STORE, mCookieStore);
		context = PlacesList.getAppContext();
		Log.i(TAG,"creating instance: HttpPostFG()");
		
	}
	
	public static HttpPostFG getInstance() {
	      if(instance == null) {
	         instance = new HttpPostFG();
	      }
	      return instance;
	   }
	
	
	public void getApplicationContext(Context c){
		context = c;
	}
	
	
	 /*
		 * list joined groups 
		 */
		
		 public void getNearbyGroup(){
				
				
			
				HttpParams params = new BasicHttpParams();
		        params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
     
		        String action = "search";
		        HttpPost httppost = new HttpPost(Constant.URL_GROUP+action);
		        Log.i(TAG, ">>>>>> getNearbyGroups");
		        
		       
		        
		        try {
		            // Add your data

		        	
		            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		           
		            nameValuePairs.add(new BasicNameValuePair("apiKey", "f3f0f6dbc5e442f6afc6687e59912f23"));
		            nameValuePairs.add(new BasicNameValuePair(Constant.LAT, "-1")); 
		            nameValuePairs.add(new BasicNameValuePair(Constant.LNG, "-1")); 
		            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		                    
		            HttpResponse response = mHttpClient.execute(httppost, mHttpContext);
    
		            Log.i(TAG, ">>>>>>>>>>>>>>>>>>>>>>>");
		             
		          
		           String posts = inputStreamToString(response.getEntity().getContent());
		           parseNearbyGroups(posts);
		            
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
		 
		 
		 
		 public void parseNearbyGroups(String groupsStr){
				
				Log.i(TAG, "---- parseJoinedGroups()");
	
				
				nearbyGroupsList = new ArrayList<Group>();
					
				Group currentGrp;
				
				try {
				
					JSONObject jsonResponse = new JSONObject(groupsStr.trim());
					
					String query_status, query_error, query_reason,result,groups;
					
					query_status = jsonResponse.getString(Constant.SUCCESS);
					query_error = jsonResponse.getString(Constant.ERROR);
					query_reason = jsonResponse.getString(Constant.REASON);
					result = jsonResponse.getString(Constant.RESULT);
					
					Log.i(TAG, "query_status: "+query_status);
					Log.i(TAG, "query_error: "+query_error);
					Log.i(TAG, "query_reason: "+query_reason);
					Log.i(TAG, "result: "+result);
					
					JSONObject resultObject = new JSONObject(result);
					
					groups = resultObject.getString(Constant.GROUPS);
					
					
					
					JSONArray contentArray = new JSONArray(groups);
					
					String title;
					
					for(int i=0; i<contentArray.length();i++)
					{
						currentGrp = new Group();
						JSONObject item = contentArray.getJSONObject(i);
						
						
						currentGrp.setId(item.getString(Constant.ID));
						currentGrp.setName(item.getString(Constant.NAME));
						currentGrp.setDescription(item.getString(Constant.DESC));
						currentGrp.setLat(item.getString(Constant.LAT));
						currentGrp.setLng(item.getString(Constant.LNG));
						currentGrp.setPolygon(item.getString(Constant.POLYGON));
						currentGrp.setIcon(item.getString(Constant.ICON));
						currentGrp.setMemberCount(item.getString(Constant.MEMBER_COUNT));
						currentGrp.setPostCount(item.getString(Constant.POST_COUNT));
	
						
						Log.i(TAG, "group: "+currentGrp.getName() +" post count: "+currentGrp.getPostCount());
						nearbyGroupsList.add(currentGrp);
						
						

					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//readPost();
				
			}
		 
		 public ArrayList getNearbyGroupsList(){
			 
			 return nearbyGroupsList;
			 
		 }
		 
		 public void postLogin(String email, String password) {
		        // Create a new HttpClient and Post Header
		    	
				email = "chfoo@feedgeorge.com";
		    	password = "adm123m";
		    	
		    	//apiKey = "f8343c8ebd00438983353f03a4ada999";;
				
				Log.i(	TAG, "---------postLogin()");
		    	
				
				Log.i(	TAG, "------email: "+email);
				Log.i(	TAG, "------password: "+password);
		    	
		    	HttpParams params = new BasicHttpParams();
		        params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		          

		        HttpPost httppost = new HttpPost(Constant.URL_USER+"login");
		        //Log.i(TAG, "sending response");
		        
		        try {
		            // Add your data

		        	
		            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		            nameValuePairs.add(new BasicNameValuePair("email", email));
		            nameValuePairs.add(new BasicNameValuePair("password", password));
		            nameValuePairs.add(new BasicNameValuePair("apiKey", "f3f0f6dbc5e442f6afc6687e59912f23"));    
		            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		            // Execute HTTP Post Request
    
		            HttpResponse response = mHttpClient.execute(httppost, mHttpContext);
            
		            Log.i(TAG, ">>>>>>>>>>>>>>>>>>>>>>>");
   
		            String post = inputStreamToString(response.getEntity().getContent());
		            parseResponse(Constant.LOGIN, post );
		            
		         
		            
		        } catch (ClientProtocolException e) {
		            // TODO Auto-generated catch block
		        	Log.i(TAG, "Protocol exception: "+e.toString());
		        } catch (IOException e) {
		            // TODO Auto-generated catch block
		        	Log.i(TAG, "IOException:" +e.toString());
		        }
		    }
		 
		 
		 public void postAddUser(String email, String password) {
		        // Create a new HttpClient and Post Header
		    	
				//email = "chfoo@feedgeorge.com";
		    	//password = "adm123m";
		    	
		    	//apiKey = "f8343c8ebd00438983353f03a4ada999";;
				
				Log.i(	TAG, "postAddUser()");
		    	
				
				Log.i(	TAG, "------email: "+email);
				Log.i(	TAG, "------password: "+password);
		    	
		    	HttpParams params = new BasicHttpParams();
		        params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		          

		        HttpPost httppost = new HttpPost(Constant.URL_USER+"add");
		        Log.i(TAG, "sending response");
		        
		        try {
		            // Add your data

		        	
		            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		            nameValuePairs.add(new BasicNameValuePair("email", email));
		            nameValuePairs.add(new BasicNameValuePair("password", password));
		            nameValuePairs.add(new BasicNameValuePair("apiKey", "f3f0f6dbc5e442f6afc6687e59912f23"));    
		            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		            // Execute HTTP Post Request
 
		            HttpResponse response = mHttpClient.execute(httppost, mHttpContext);
         
		            Log.i(TAG, ">>>>>>>>>>>>>>>>>>>>>>>");

		            //inputStreamToString(response.getEntity().getContent());
		            String post = inputStreamToString(response.getEntity().getContent());
		            parseResponse(Constant.LOGIN, post );
		            
		           

		            
		        } catch (ClientProtocolException e) {
		            // TODO Auto-generated catch block
		        	Log.i(TAG, "Protocol exception: "+e.toString());
		        } catch (IOException e) {
		            // TODO Auto-generated catch block
		        	Log.i(TAG, "IOException:" +e.toString());
		        }
		    }
		 
		 
		 public void postToServer(int action, ArrayList<String> keyList, ArrayList<String> valueList) {
		        // Create a new HttpClient and Post Header
		    	
				//email = "chfoo@feedgeorge.com";
		    	//password = "adm123m";
		    	
		    	//apiKey = "f8343c8ebd00438983353f03a4ada999";;
				
				Log.i(	TAG, "postToServer()");
				
				if(keyList == null){
					keyList = new ArrayList<String>();
					valueList = new ArrayList<String>();
				}
				
				keyList.add("apiKey");
				valueList.add("f8343c8ebd00438983353f03a4ada999");
				
				//Log.i(	TAG, "------email: "+email);
				//Log.i(	TAG, "------password: "+password);
		    	
		    	HttpParams httpparams = new BasicHttpParams();
		    	httpparams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		          
		    	HttpPost httppost = null;
		        int count = keyList.size();
		        
		        try {
		            // Add your data

		        	
		        	switch(action){
		        	
		        		case Constant.JOIN_GROUP:
		        			httppost = new HttpPost(Constant.URL_GROUP+"join");
						     Log.i(TAG, "JOIN_GROUP");
						     
				           
				            
				           
		        	
		        	}
		        	
		        	 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(count);
		        	
		        	 for(int i=0;i<count;i++){
			            	nameValuePairs.add(new BasicNameValuePair( keyList.get(i).toString(),valueList.get(i).toString()));
			            }
			            
			                
			            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			            // Execute HTTP Post Request

			            HttpResponse response = mHttpClient.execute(httppost, mHttpContext);
	      
			            Log.i(TAG, ">>>>>>>>>>>>>>>>>>>>>>>");

			            //inputStreamToString(response.getEntity().getContent());
			            String post = inputStreamToString(response.getEntity().getContent());
			            parseResponse(action, post );
		            
		           

		            
		        } catch (ClientProtocolException e) {
		            // TODO Auto-generated catch block
		        	Log.i(TAG, "Protocol exception: "+e.toString());
		        } catch (IOException e) {
		            // TODO Auto-generated catch block
		        	Log.i(TAG, "IOException:" +e.toString());
		        }
		    }
		 
		 
		 
		 
		 public void parseResponse(int action, String post){
			 
			 Log.i(TAG, "---- parseResponse() FOR : " +action);
				
				
				//nearbyGroupsList = new ArrayList<Group>();
					
				//Group currentGrp;
			 
			
				 try {
						
						JSONObject jsonResponse = new JSONObject(post.trim());
						
						String query_status, query_error, query_reason,result;
						
						query_status = jsonResponse.getString(Constant.SUCCESS);
						query_error = jsonResponse.getString(Constant.ERROR);
						query_reason = jsonResponse.getString(Constant.REASON);
						
						
						Log.i(TAG, "query_status: "+query_status);
						Log.i(TAG, "query_error: "+query_error);
						Log.i(TAG, "query_reason: "+query_reason);
						
						
						
						
						boolean noerror = query_error.equals(Constant.NO_ERROR);
						
						Log.i(TAG, "noerror: "+noerror);
						
						if(noerror){
							
							Log.i(TAG, ">>>> EQUAL");
							
							result = jsonResponse.getString(Constant.RESULT);
							JSONObject resultObject = new JSONObject(result);
							Log.i(TAG, "result: "+result);
							
							switch(action){
							
							case Constant.LOGIN:
								
								 String displayName, pic;
								 String id = resultObject.getString(Constant.ID);
								 
								 displayName = resultObject.getString(Constant.DISPLAY_NAME);
								 pic = resultObject.getString(Constant.PROFILE_PIC);
								 
								 Log.i(TAG, "ID:"+id + " displayName: "+ displayName +"pic: " +pic);
								 
								  Intent intent = new Intent();
								  intent.setClass(this.context ,PlacesList.class);
								  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								  context.startActivity(intent);
								 
								  Toast.makeText(PlacesList.context, "Successfully logged in", Toast.LENGTH_SHORT).show();
								  
						
							case Constant.JOIN_GROUP:	  	
								Toast.makeText(PlacesList.context, "Successfully JOIN GROUP", Toast.LENGTH_LONG).show();
							/*
							 groups = resultObject.getString(Constant.GROUPS);
							
							
							
							JSONArray contentArray = new JSONArray(groups);
							
							String title;
							
							for(int i=0; i<contentArray.length();i++)
							{
								currentGrp = new Group();
								JSONObject item = contentArray.getJSONObject(i);
								
								
								currentGrp.setId(item.getString(Constant.ID));
								currentGrp.setName(item.getString(Constant.NAME));
								currentGrp.setDescription(item.getString(Constant.DESC));
								currentGrp.setLat(item.getString(Constant.LAT));
								currentGrp.setLng(item.getString(Constant.LNG));
								currentGrp.setPolygon(item.getString(Constant.POLYGON));
								currentGrp.setIcon(item.getString(Constant.ICON));
			
								
								Log.i(TAG, "group: "+currentGrp.getName());
								nearbyGroupsList.add(currentGrp);
								
								

							}
							 */
								
							}
							
						}else{
							
							Log.i(TAG, ">>>> NOT EQUAL");
							Toast.makeText(context, query_reason, Toast.LENGTH_SHORT).show();
						}
		
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//readPost();
					
				}
			}
				
							 
		 



