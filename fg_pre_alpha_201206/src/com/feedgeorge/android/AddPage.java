package com.feedgeorge.android;

import com.feedgeorge.android.PlacesList.PlacesListMainAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

public class AddPage extends Activity{

	Button addPostBtn, addEventBtn, addSurveyBtn;
	
	public static String  TAG = "FG-1";
	
	Context context;
	
	private OnClickListener myClickListener = new OnClickListener() {
		public void onClick(View v) {
			
			if(v.equals(addPostBtn)){
				
				
				Toast.makeText(context, "Add Post", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setClass(context,AddPost.class);
			    startActivity(intent);
			   
				
			   
				
			}
			else if(v.equals(addEventBtn)){
				
				Toast.makeText(context, "Add EVENT", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setClass(context,AddEventOLD.class);
			    startActivity(intent);
				
			}else if(v.equals(addSurveyBtn)){
				
				Toast.makeText(context, "Add SURVEY", Toast.LENGTH_SHORT).show();
				Toast.makeText(context, "Add SURVEY", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setClass(context,AddEvent.class);
			    startActivity(intent);
				/*
				Intent intent = new Intent();
			    intent.setClass(context,PostList.class);
			    startActivity(intent);
				*/
				
			}
			
		}

		
	};
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpage);
        
      
       
       
        context = PlacesList.getAppContext();
        
        addPostBtn = (Button) findViewById(R.id.AddPostBtn);
        addEventBtn  = (Button) findViewById(R.id.AddEventBtn);
        addSurveyBtn = (Button) findViewById(R.id.AddSurveyBtn);
        
        
        addPostBtn.setOnClickListener(myClickListener);
        addEventBtn.setOnClickListener(myClickListener);
        addSurveyBtn.setOnClickListener(myClickListener);
        
        
        
              
	}
}
