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

import android.util.Log;

public class HttpPostFG {
	
	private static HttpPostFG instance = null;
	
	public static DefaultHttpClient mHttpClient;
	public static BasicHttpContext mHttpContext;
	CookieStore mCookieStore;
	
	public static String  TAG = "FG";
	
	
	 //pupulate nearby groups
	 public static ArrayList<Group> nearbyGroupsList;
	
	protected HttpPostFG(){
		
		mHttpClient = new DefaultHttpClient();
		mHttpContext = new BasicHttpContext();
		mCookieStore      = new BasicCookieStore();        
		mHttpContext.setAttribute(ClientContext.COOKIE_STORE, mCookieStore);
		
	}
	
	public static HttpPostFG getInstance() {
	      if(instance == null) {
	         instance = new HttpPostFG();
	      }
	      return instance;
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
	
						
						Log.i(TAG, "group: "+currentGrp.getName());
						nearbyGroupsList.add(currentGrp);
						
						

					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//readPost();
				
			}
		 
		 public ArrayList getNearbyGroups(){
			 
			 return nearbyGroupsList;
			 
		 }


}
