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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;



public class SelectionView  extends Activity {
	protected static final String TAG = "FG-1";

	Button okBtn, cancelBtn;
	RadioButton selectionRadioBtn;
	RadioGroup selectionRadioGrp;
	
	public static PostList postlist = null;
	
	
	
	
	//public static String selection;
	
	private OnClickListener myBtnClickListener = new OnClickListener() {

		
		public void onClick(View v) {
			
			if(v.equals(okBtn)){
				
				int selectedId = selectionRadioGrp.getCheckedRadioButtonId();
				 
				// find the radiobutton by returned id
				selectionRadioBtn = (RadioButton) findViewById(selectedId);
	 
				//Toast.makeText(PlacesList.getAppContext(),
					//	selectionRadioBtn.getText(), Toast.LENGTH_SHORT).show();
				
				String selected = selectionRadioBtn.getText().toString();
				PostList.POST_SELECTION = selected;
				
				//Constant.READ_FR_DB = true;
				//Constant.SELECTED_CONTENT_TYPE = selected;
				
				HttpPostFG.getInstance().viewContentListFrDB(selected);
				
				//KeyEvent.KEYCODE_BACK
				
				//doInjectKeyEvent(new KeyEvent(KeyEvent.KEYCODE_BACK, KeyCode));
				
				
				//postlist.onBackPressed();
				
				/*
				Intent returnIntent = new Intent();
			    returnIntent.putExtra("selected",selectionRadioBtn.getText().toString());
			    
			    
			    
			    if (getParent() == null) {
			        setResult(Activity.RESULT_OK, returnIntent);
			    } else {
			        getParent().setResult(Activity.RESULT_OK, returnIntent);
			    }
			    finish();
			    
			    
				setResult(1,returnIntent);
				*/
				
				/*
				Intent intent = new Intent();
				 intent.setClass(PlacesList.getAppContext() ,FGDashboard.class);
				 // intent.setClass(this.context ,PostList.class);
				  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				  PlacesList.getAppContext().startActivity(intent);
				*/
				
				/*
				HttpPostFG.getInstance().viewContentListFrDB(selected);
				Intent postlist = new Intent(getParent(), PostList.class);
				startActivity(postlist);
				*/
				//TabGroupActivity parentActivity = (TabGroupActivity)getParent();
		        //parentActivity.startChildActivity("SelectionView", postlist);
				
				
				finish();
				//onBackPressed();
			
			}else if(v.equals(cancelBtn)){
				onBackPressed();
			}
		
			
		}
		
	};
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.radiobutton);
	    
	    okBtn = (Button) findViewById(R.id.OkBtn);
	    cancelBtn = (Button) findViewById(R.id.CancelBtn);
	    okBtn.performClick();
	    
	    okBtn.setOnClickListener(myBtnClickListener);
	    cancelBtn.setOnClickListener(myBtnClickListener);
	    
	    selectionRadioGrp = (RadioGroup) findViewById(R.id.radioSelection);
	}
	
	public static void setPostList(PostList instance){
		
		postlist = instance;
	}
}

 	
