package com.feedgeorge.android;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;
import android.widget.Toast;

public class HttpPostFG {
	
	
	//for database
	private SQLiteDatabase db = null;
	private SQLiteDatabase db1 = null;
	//private Cursor taskCursor = null;
	private ContentValues cv = null;
	private DatabaseHelper dbHelper = null;	
	
	
	
	private static HttpPostFG instance = null;
	
	public static DefaultHttpClient mHttpClient;
	public static BasicHttpContext mHttpContext;
	CookieStore mCookieStore;
	
	String newUserEmail, newUserPwd = null;
	
	public static String  TAG = "FG-1";
	Context context;
	
	boolean newSignedUp=false;
	
	
	static int getContentcount = 0;
	ArrayList<Post> postQueue = new ArrayList<Post>();
	
	ArrayList<Comment> commentList = new ArrayList<Comment>(); 
	
	 //pupulate nearby groups
	 public static ArrayList<Group> nearbyGroupsList, joinedGroupList;
	
	protected HttpPostFG(PlacesList placesListContext){
		
		mHttpClient = new DefaultHttpClient();
		mHttpContext = new BasicHttpContext();
		mCookieStore      = new BasicCookieStore();        
		mHttpContext.setAttribute(ClientContext.COOKIE_STORE, mCookieStore);
		//context = PlacesList.getAppContext();
		
		context = (PlacesList) placesListContext;
		instance = this;
		
		Log.i(TAG,"!!!!!!creating instance: HttpPostFG()");
		
		
		if((dbHelper == null) || db == null){
			
			dbHelper = new DatabaseHelper(placesListContext,db);
			
			Log.i(TAG,"!!!!!!HttpPostFG() : create dbHelper");
			//b = dbHelper.getWritableDatabase();
			cv = new ContentValues();
			
		}
		
		
	}
	
	public static HttpPostFG getInstance() {
		
		return instance;
		
		/*
		Log.i(TAG,"!!!!!! HttpPostFG getInstance()");
		
	      if(instance == null) {
	         instance = new HttpPostFG();
	      }
	      return instance;
	      */
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
		    	
				//email = "chfoo@feedgeorge.com";
		    	//password = "adm123m";
			 
			 	//email = "a@b.com";
		    	//password = "abc";
		    	
			 	//email = "apple@b.com";
		    	//password = "abc";
			 
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
		            parseResponse(Constant.LOGIN, post);
		            
		         
		            
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
				
				newUserEmail = email; 
				newUserPwd = password;
		    	
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
		            parseResponse(Constant.SIGN_UP, post );
		            
		           

		            
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
						     Log.i(TAG, ">>>>>>>POST: JOIN_GROUP");
						     break;
						     
		        		case Constant.GET_JOINED_GROUP:       			
		        			httppost = new HttpPost(Constant.URL_USER+"listgroups");
						    Log.i(TAG, ">>>>>>>POST: GET_JOINED_GROUP");
						    break;
						    
		        		case Constant.GET_GRP_CONTENT:
		        			 
		        			 httppost = new HttpPost(Constant.URL_CONTENT+"list");
							 
		        			 Log.i(TAG, ">>>>>>>POST: GET_GRP_CONTENT");
	 
							 break;
							 
		        		case Constant.GET_COMMENTS:
		        			httppost = new HttpPost(Constant.URL_CONTENT+"listcomments");
		        			break;
		        			
		        		case Constant.ADD_COMMENT:
		        			httppost = new HttpPost(Constant.URL_CONTENT+"addcomment");
		        			break;
		        			
		        		case Constant.GET_FULLCONTENT:
		        			httppost = new HttpPost(Constant.URL_CONTENT+"get");
		        			break;
						    
				            
				           
		        	
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
			            parseResponse(action, post);
		            
		           

		            
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
						
						String query_status, query_error, query_reason,result = null;
						
						query_status = jsonResponse.getString(Constant.SUCCESS);
						query_error = jsonResponse.getString(Constant.ERROR);
						query_reason = jsonResponse.getString(Constant.REASON);
						
						
						Log.i(TAG, "query_status: "+query_status);
						Log.i(TAG, "query_error: "+query_error);
						Log.i(TAG, "query_reason: "+query_reason);
						
						
						
						
						boolean noerror = query_error.equals(Constant.NO_ERROR);
						
						Log.i(TAG, "noerror: "+noerror);
						
						if(noerror){
							
							JSONObject resultObject = null;
							
							if(action != Constant.JOIN_GROUP)
							{
								result = jsonResponse.getString(Constant.RESULT);
								resultObject = new JSONObject(result);
								Log.i(TAG, "result: "+result);
							}
							
							
							
							switch( action){
							
								case Constant.LOGIN:
									
									Log.i(TAG, ">>>> LOGIN!!!");
									 String displayName, pic;
									 String id = resultObject.getString(Constant.ID);
									 
									 displayName = resultObject.getString(Constant.DISPLAY_NAME);
									 pic = resultObject.getString(Constant.PROFILE_PIC);
									 
									 Log.i(TAG, "ID:"+id + " displayName: "+ displayName +"pic: " +pic);
									 
									 Toast.makeText(PlacesList.context, "Successfully logged in... ", Toast.LENGTH_SHORT).show();
									 
									 
									 postToServer(Constant.GET_JOINED_GROUP, null, null);
									 
									 Constant.LOGGED_IN = true;
									 
									 /*
									 if(newSignedUp)
									 {
		 
										  Intent intent1 = new Intent();
										  intent1.setClass(this.context ,PlacesList.class);
										  intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
										  context.startActivity(intent1);
										  //newSignedUp = false;
			 
									 }
									 */
									 break;
									  
						
								case Constant.JOIN_GROUP:	
									Log.i(TAG, ">>>> JOIN GROUP");
									Toast.makeText(PlacesList.context, "Successfully JOIN GROUP", Toast.LENGTH_LONG).show();
									break;
								
								case Constant.SIGN_UP:
											 
									Log.i(TAG, ">>>> SIGNUP");
									newSignedUp = true;
									Toast.makeText(PlacesList.context, "Successfully signed up. Loggin in..", Toast.LENGTH_SHORT).show();
									postLogin(newUserEmail,newUserPwd);
									break;
									
								case Constant.GET_JOINED_GROUP:  
									Log.i(TAG, ">>>> GET_JOINED_GROUP");
									
									String groups = resultObject.getString(Constant.GROUPS);
									
									//JSONObject contentObject = new JSONObject(content);
									
									JSONArray contentArray = new JSONArray(groups);
									
									int count = contentArray.length();
									Intent placesIntent = new Intent();
									
									if(count == 0){
										
										placesIntent.setClass(context, PlacesList.class);
										placesIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
										context.startActivity(placesIntent);
										
									}else{
			
										Group currentGrp;
										String title;
										joinedGroupList = new ArrayList<Group>();
										for(int i=0; i<count;i++)
										{
											currentGrp = new Group();
											JSONObject item = contentArray.getJSONObject(i);
											
											
											currentGrp.setId(item.getString(Constant.ID));
											currentGrp.setName(item.getString(Constant.NAME));
											currentGrp.setDescription(item.getString(Constant.DESC));
											currentGrp.setRole(item.getString(Constant.ROLE));
											
											if(i==0){
												Constant.defaultGroupID = item.getString(Constant.ID);
												Constant.currentGroupName = currentGrp.getName();
												Log.i(TAG, "*****defaultGroupID: "+Constant.defaultGroupID +" name: "+currentGrp.getName());
												
											}
											Log.i(TAG, "group: "+currentGrp.getName());
											joinedGroupList.add(currentGrp);
											
											
											
											/*
											placesIntent.setClass(context, PlacesList.class);
											placesIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
											context.startActivity(placesIntent);
											*/
											//Intent placesIntent = new Intent();
										}
										
										//GET GROUP CONTENT
										//display the content of the first group
										ArrayList<String> keys = new ArrayList<String>();
										ArrayList<String> values = new ArrayList<String>();
										
										
										Log.i(TAG, "defaultGroupID: "+Constant.defaultGroupID);
										keys.add(Constant.GROUP_ID);
										values.add(Constant.defaultGroupID);
										
										postToServer(Constant.GET_GRP_CONTENT, keys, values);
										
										//DISPLAY A LIST OF JOINED GROUPS
										/*
										placesIntent.setClass(context, MyGroupPage.class);
										placesIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
										context.startActivity(placesIntent);
										*/
									}
									
									break;
							
								//>>>>>> GET CONTENT LIST
								case Constant.GET_GRP_CONTENT:
	
				        			 Log.i(TAG, " RESPONSE : GET_GRP_CONTENT: ");
				        			 Log.i(TAG, "result: "+result);
				        			 
				        			 Post currentPost;
				        			 resultObject = new JSONObject(result);
				     				
				     				String content = resultObject.getString(Constant.CONTENT);
				     				
				     				//JSONObject contentObject = new JSONObject(content);
				     				
				     				contentArray = new JSONArray(content);
				     				postQueue.clear();
				     				String title;
				     				
				     				//ADD DATE TO DB
				     				db = dbHelper.getWritableDatabase();
				     				
				     				//Cursor taskCursor = db.rawQuery(query, null);
				     				//messageList.startManagingCursor(taskCursor);
				     				
				     				//--------------------------
				     				
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
				     					currentPost.setCommentCount(item.getString(Constant.COMMENT_COUNT));
				     					
				     					
				     					//------------ database --------------
				     					
				     					cv.put(Constant.ID, item.getString(Constant.ID) );
				     					cv.put(Constant.AUTHOR_ID, item.getString(Constant.AUTHOR_ID));
				     					cv.put(Constant.AUTHOR_NAME, item.getString(Constant.AUTHOR_NAME));
				     					cv.put(Constant.GROUP_ID,item.getString(Constant.GROUP_ID));
				     					cv.put(Constant.IMAGE,item.getString(Constant.IMAGE));
				     					cv.put(Constant.LAST_UPDATE,item.getString(Constant.LAST_UPDATE));
				     					cv.put(Constant.LAT,item.getString(Constant.LAT));
				     					cv.put(Constant.LNG,item.getString(Constant.LNG));
				     					cv.put(Constant.TEXT,item.getString(Constant.TEXT));
				     					
				     					cv.put(Constant.TYPE,item.getString(Constant.TYPE));
				     					cv.put(Constant.COMMENT_COUNT,item.getString(Constant.COMMENT_COUNT));
				     					
				     					db.insert(Constant.FG_CONTENT_TABLE_NAME, Constant.ID, cv);
				     					
				     					//------------ database --------------
				     					
				     					Log.i(TAG, "title: "+item.getString(Constant.TEXT));
				     					Log.i(TAG, "+++++++++++++++++++++++++++++++++++++++++");
										postQueue.add(currentPost);
				     					
				     				
				     				}
				     				
				     				
				     				
				     				
				     				
				     				
				     				PostList.setPostQueue(postQueue);
				     				
				     				 Intent intent = new Intent();
				     				 intent.setClass(context ,FGDashboard.class);
									 // intent.setClass(this.context ,PostList.class);
									  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									  context.startActivity(intent);
									  
									  //db.close();
									  
									 break;
							
								case Constant.ADD_POST:
									/*
									 * {"success":true,"error":200,"reason":"Success","result":{"id":"62"}}
									 */
							
									Toast.makeText(PlacesList.context, "New post successfully added!", Toast.LENGTH_SHORT).show();
									
									
									FGDashboard.tabHost.setCurrentTab(1);
									/*
									Intent intent1 = new Intent();
				     				intent1.setClass(context ,FGDashboard.class);
									 
									intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									context.startActivity(intent1);
									*/
									  break;
									  
								case Constant.GET_COMMENTS:
									
									Log.i(TAG, " RESPONSE : GET_COMMENTS: ");
				        			 Log.i(TAG, "result: "+result);
				        			 
				        			 Comment currentComment;
				        			 resultObject = new JSONObject(result);
				     				
				     				String comments = resultObject.getString(Constant.COMMENTS);
				     				String more = resultObject.getString(Constant.MORE);
				     				
				     				Log.i(TAG, "comments: "+comments);
				     				Log.i(TAG, "more: "+more);
				     				
				     				JSONArray commentArray = new JSONArray(comments);
				     				commentList.clear();
				     				
				     				
				     				for(int i=0; i<commentArray.length();i++)
				     				{
				     					currentComment = new Comment();
				     					JSONObject item = commentArray.getJSONObject(i);
				     					
				     					
				     					currentComment.setId(item.getString(Constant.ID));
				     					currentComment.setComment(item.getString(Constant.COMMENT));
				     					currentComment.setCommentID(item.getString(Constant.COMMENT_ID));
				     					currentComment.setCommenterName(item.getString(Constant.COMMENTER_NAME));
				     					currentComment.setLastUpdate(item.getString(Constant.LAST_UPDATE));
				     					
				     					if(i==0)
				     						PostView.setLastComment(currentComment);
				     					
				     					Log.i(TAG, "comment: "+currentComment.getComment());
				     					
				     					commentList.add(currentComment);
				     					
				     				
				     				}
				     				
				     				PostView.setCommentQueue(commentList);
				     				
				     				/*
				     				PostList.setPostQueue(postQueue);
				     				
				     				 Intent intent = new Intent();
				     				 intent.setClass(context ,FGDashboard.class);
									 // intent.setClass(this.context ,PostList.class);
									  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									  context.startActivity(intent);
									*/
				     				
				     				break;
				     				
								case Constant.ADD_COMMENT:
									Toast.makeText(PlacesList.context, "Comment successfully added!", Toast.LENGTH_SHORT).show();
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
		 
		 
		 public static ArrayList getJoinedGroupList(){
			 
			 Log.i(TAG, "----------- getJoinedGroupList()");
			 return joinedGroupList;
		 }
		 
		 /*
		  * ADD POST
		  * reqEntity.addPart("groupId", new StringBody(groupID));
				reqEntity.addPart("lng", new StringBody(lng));
				reqEntity.addPart("lat", new StringBody(lat));
				reqEntity.addPart("photo", bab); //BITMAP
				reqEntity.addPart("text", new StringBody(caption));
				reqEntity.addPart("apiKey", new StringBody(apiKey));
		  */
		 
		 public void AddPost(String groupID, String lng, String lat, Bitmap photo, String caption, 
				 int option, String eventDate, String[] survey) throws Exception {
				

				
				Log.i(TAG, "lat: " +lat + "  lng:"+lng + " groupID: "+groupID +" caption: "+caption);
				
				//caption = captionText.getText().toString();
				HttpPost httppost = null;
				MultipartEntity reqEntity = new MultipartEntity(
						HttpMultipartMode.BROWSER_COMPATIBLE);
				
				try {
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					
					if(photo != null){
						Log.i(TAG,"PHOTO != NULL");
						photo.compress(CompressFormat.JPEG, 75, bos);
						byte[] data = bos.toByteArray();
						ByteArrayBody bab = new ByteArrayBody(data, "test.jpg");
						reqEntity.addPart("photo", bab);
					}else{
						Log.i(TAG,"PHOTO == NULL");
					}
					
			
					
					reqEntity.addPart("groupId", new StringBody(groupID));
					reqEntity.addPart("lng", new StringBody(lng));
					reqEntity.addPart("lat", new StringBody(lat));
					
					reqEntity.addPart("text", new StringBody(caption));
					reqEntity.addPart(Constant.API_KEY, new StringBody(Constant.apiKey_value));
					
					
					if(option == Constant.ADD_EVENT){
						httppost = new HttpPost(
							"http://developer.feedgeorge.com/content/addevent");
						reqEntity.addPart("eventDate", new StringBody(eventDate));
					
					}else if(option == Constant.ADD_POST){
						httppost = new HttpPost(
								"http://developer.feedgeorge.com/content/addpost");
						
					}else if(option == Constant.ADD_SURVEY){
						
						Log.i(TAG, "add SURVEY");
						httppost = new HttpPost(
								"http://developer.feedgeorge.com/content/addsurvey");
						
						int size = survey.length;
						
												
						for(int i=0;i<size;i++){
							
							reqEntity.addPart("choices[]", new StringBody(survey[i]));
						
						}
						
					}
					
					
						
						
					httppost.setEntity(reqEntity);
					
					
					
					
					HttpResponse response = mHttpClient.execute(httppost, mHttpContext );
					
					BufferedReader reader = new BufferedReader(new InputStreamReader(
							response.getEntity().getContent(), "UTF-8"));
					String sResponse;
					StringBuilder s = new StringBuilder();

					while ((sResponse = reader.readLine()) != null) {
						s = s.append(sResponse);
					}
					//System.out.println("Response: " + s);
					Log.i(TAG, "Response: " + s);
					parseResponse(Constant.ADD_POST,s.toString());
					
				} catch (Exception e) {
					// handle exception here
					Log.e(e.getClass().getName(),"error:" +e.getMessage());
				}
			}
		 
		 
		 public void viewContentListFrDB(String choice){
			 
			 postQueue.clear();
			 boolean selectAll= false;
			 String queryAll;
			 
			 if(choice.equals(Constant.POST)){
				 
				 choice = Constant.POST;
			
			 }else if(choice.equals(Constant.EVENT)){
				 choice = Constant.EVENT;
				 
			 }else if(choice.equals(Constant.SURVEY)){
				 choice = Constant.SURVEY;
				 
			 }else{
				 
				 selectAll = true;
			 }
			 

			 if(selectAll){

				 queryAll = "SELECT * "+
							" FROM "+ Constant.FG_CONTENT_TABLE_NAME+ 
									 " order by "+Constant.LAST_UPDATE + " DESC";

			 }else{
				 
				 queryAll = "SELECT * "+
							" FROM "+ Constant.FG_CONTENT_TABLE_NAME+ " where type ='"  +choice +"' " +
									 " order by "+Constant.LAST_UPDATE + " DESC";

			 }
			 
			 Log.i(TAG,"viewContentListFrDB | query:" +queryAll);
			 
			 db = dbHelper.getWritableDatabase();
			 
			 Post currentPost; 
			 
			 //SELECT * FROM feedgeorgev2 where type = '3' order by lastUpdate DESC;
			 /*
			 queryAll = "SELECT * "+
						" FROM "+ Constant.FG_CONTENT_TABLE_NAME+ " where type ='"  +choice +"' " +
								 " order by "+Constant.LAST_UPDATE + " DESC";
			 */
			 Cursor readCursor = db.rawQuery(queryAll, null);
			 
			 int readCount = readCursor.getCount();
			 
			 String id, author, text, authorId, lat, lng, lastUpdate, commentCount, image;
			 
			 if(readCount >0){
				 
				 
				 while(readCursor.moveToNext()){
					 
					 currentPost = new Post();
					 id = readCursor.getString(readCursor.getColumnIndex(Constant.ID));
					 author = readCursor.getString(readCursor.getColumnIndex(Constant.AUTHOR_NAME));
					 text = readCursor.getString(readCursor.getColumnIndex(Constant.TEXT));
					 authorId = readCursor.getString(readCursor.getColumnIndex(Constant.AUTHOR_ID));
					 lat = readCursor.getString(readCursor.getColumnIndex(Constant.LAT));
					 lng = readCursor.getString(readCursor.getColumnIndex(Constant.LNG));
					 lastUpdate = readCursor.getString(readCursor.getColumnIndex(Constant.LAST_UPDATE));
					 commentCount = readCursor.getString(readCursor.getColumnIndex(Constant.COMMENT_COUNT));
					 image = readCursor.getString(readCursor.getColumnIndex(Constant.IMAGE));
					 
					 Log.i(TAG,"viewContentListFrDB | text:" +text);
					 
					 currentPost.setId(id);
					 currentPost.setAuthorId(authorId);
					 currentPost.setAuthorId(author);
					 currentPost.setImage(image);
					 currentPost.setText(text);
					 currentPost.setLastUpdate(lastUpdate);
					 currentPost.setLat(lat);
					 currentPost.setLng(lng);
					 currentPost.setCommentCount(commentCount);
					 
					 postQueue.add(currentPost);
				 }
				 
			 }
			 
			 db.close();
			 PostList.setPostQueue(postQueue);
			 
			 
		 }
		 
}
				
							 
		 



