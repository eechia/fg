package com.feedgeorge.android;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class PostView extends Activity {
	protected static final String TAG = "FG-1";
	Bundle bundle;
	String lat,lng, title, author, photo_url, postID, commentstr;
	TextView timestamp, authorTxt, descTxt;
	ImageView imagePost;
	URL newurl = null;
	
	Button showMapBtn, backBtn, shareBtn, commentBtn, postCommentBtn;
	
	HttpPostFG httppostFG;
	static Comment lastComment;
	static ArrayList<Comment> commentList = new ArrayList<Comment>();
	
	TextView  commenterName, commentTxt, cmtTimestamp;
	
	ArrayList<String> keyList, valueList;

	
	private OnClickListener myBtnClickListener = new OnClickListener() {

		
		public void onClick(View v) {
			
			if(v.equals(showMapBtn)){
				Intent intent = new Intent(getParent(), PostMapView.class);
				 
				 intent.putExtra(Constant.LAT, lat);
				 intent.putExtra(Constant.LNG, lng);
				 intent.putExtra(Constant.TEXT, title);
				    
		         TabGroupActivity parentActivity = (TabGroupActivity)getParent();
		         parentActivity.startChildActivity("ViewMap", intent);
				
			}else if(v.equals(backBtn)){

				onBackPressed();
				
			}else if(v.equals(shareBtn)){
				
			}else if(v.equals(commentBtn)){
				
				
				
				
			}else if(v.equals(postCommentBtn)){
				
				keyList = new ArrayList<String>();
				valueList =  new ArrayList<String>();
				
				//commentstr = new String();
				
				keyList.add("contentId");
				valueList.add(postID);
				
				keyList.add("comment");
				valueList.add(commentTxt.getText().toString());
				
				httppostFG.postToServer(Constant.ADD_COMMENT, keyList, valueList);
				
				
				
			}
			
		}
		
	};
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.postview);
	    
	    httppostFG = HttpPostFG.getInstance();
	    lastComment = null;
	    Intent i = getIntent();
	    bundle = i.getExtras();
        lat = bundle.getString(Constant.LAT);
       // String content = bundle.getString("content");
        lng = bundle.getString(Constant.LNG);
        title = bundle.getString(Constant.TEXT);
        author = bundle.getString(Constant.AUTHOR_NAME);
        photo_url = bundle.getString(Constant.IMAGE);
        postID = bundle.getString(Constant.ID);
        
        
        
        getComments();
        
        backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(myBtnClickListener);
        
        shareBtn = (Button) findViewById(R.id.shareBtn);
        shareBtn.setOnClickListener(myBtnClickListener);
        
        showMapBtn = (Button) findViewById(R.id.mapPostBtn);
        showMapBtn.setOnClickListener(myBtnClickListener);
        
        
        //commentBtn = (Button) findViewById(R.id.commentPostBtn);
        //commentBtn.setOnClickListener(myBtnClickListener);
        
        
        postCommentBtn = (Button) findViewById(R.id.postCommentBtn);
        postCommentBtn.setOnClickListener(myBtnClickListener);
        
        
        //setting UI
        descTxt = (TextView) findViewById(R.id.descPostTxt);
        descTxt.setText(title);
        
        timestamp = (TextView) findViewById(R.id.timestampPostTxt);
        
        
        authorTxt = (TextView) findViewById(R.id.authorPostTxt);
        authorTxt.setText("Posted by "+author);
        
        imagePost = (ImageView) findViewById(R.id.postImage);
		//String photo_url = photo;
        
		Log.i(TAG,"^^PHOTO URL: "+photo_url);
		
		try {
						
			if(photo_url != null){
				newurl = new URL(photo_url);
				Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream()); 
				imagePost.setImageBitmap(mIcon_val);
				//imagePost.setScaleType(ScaleType.CENTER_INSIDE);
				
				//imagePost = transformImage(mIcon_val, imagePost);
				
				
				//final double viewWidthToBitmapWidthRatio = (double)imagePost.getWidth() / (double)mIcon_val.getWidth();
				//imagePost.getLayoutParams().height = (int) (mIcon_val.getHeight() * viewWidthToBitmapWidthRatio);
				
				/*
				 int width=360;
				 int height=320;
				 Bitmap resizedbitmap=Bitmap.createScaledBitmap(mIcon_val, width, height, true);
				 imagePost.setImageBitmap(resizedbitmap);
				 */
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		//for comment
		commenterName = (TextView) findViewById(R.id.commentName);
		commentTxt = (TextView) findViewById(R.id.commentTxt);
		cmtTimestamp = (TextView) findViewById(R.id.commentTimestamp);
		
        if(lastComment != null){
        	commenterName.setText(lastComment.getCommenterName());
    		commentTxt.setText(lastComment.getComment());
    		cmtTimestamp.setText(lastComment.getLastUpdate());
        }
		
        
	 
	}

	
	protected boolean isRouteDisplayed() {
	    return false;
	}
	
	
	public static void setLastComment(Comment c){
		Log.i(TAG,"setLastComment: "+c.getComment());
		lastComment = c;
	}
	
	public static void setCommentQueue(ArrayList<Comment> comments){
		commentList = comments;
	}
	
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
	
	public ImageView transformImage(Bitmap bitmapOrg, ImageView imageView)
	{
		  
	        
	        int width = bitmapOrg.getWidth();
	        int height = bitmapOrg.getHeight();
	        int newWidth = 600;
	        int newHeight = 400;
	        
	        // calculate the scale - in this case = 0.4f
	        float scaleWidth = ((float) newWidth) / width;
	        float scaleHeight = ((float) newHeight) / height;
	        
	        // createa matrix for the manipulation
	        Matrix matrix = new Matrix();
	        // resize the bit map
	        matrix.postScale(scaleWidth, scaleHeight);
	        // rotate the Bitmap
	       // matrix.postRotate(45);

	        // recreate the new Bitmap
	        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, 
	                          width, height, matrix, true); 
	    
	        // make a Drawable from Bitmap to allow to set the BitMap 
	        // to the ImageView, ImageButton or what ever
	        BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);
	        
	       
	        
	        // set the Drawable on the ImageView
	        imageView.setImageDrawable(bmd);
	     
	        // center the Image
	        imageView.setScaleType(ScaleType.CENTER);
	        
	        return imageView;
		 
	 }
	
}

 	
