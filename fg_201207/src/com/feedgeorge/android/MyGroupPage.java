package com.feedgeorge.android;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
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
import android.widget.TextView;

import android.widget.Toast;

public class MyGroupPage extends ListActivity implements OnItemSelectedListener{
	
	public static String  TAG = "FG";
	
	//populate content
	 List<Group> groupQueue = new ArrayList<Group>();
	 
	 String[] filterByStr = {"All", "Posts","Events","Surveys"};
	 Button addBtn;
	 Spinner filterSpin;
	 Context context;
	 
	 ArrayList<Group> myGroupList;
	 
	 HttpPostFG httppostFG;
	 
	 TextView groupNameTxt;
	
	/*
	 * Listen to the option selected by the users.
	 */

	private OnClickListener myBtnClickListener = new OnClickListener() {
	


		
		public void onClick(View v) {
			
			Log.i(TAG,">>> myBtnClickListener!!!!!!: onClick");
			 
			if(v.equals(addBtn)){
				
				Log.i(TAG,">>> addBtn...........");
				Intent intent = new Intent();
				  intent.setClass(context ,PlacesList.class);
				  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				  context.startActivity(intent);
				
			}
			
			}

	
	};

	
	 
	@SuppressWarnings("unchecked")
	public void onCreate(Bundle icicle) {
	    	
	        super.onCreate(icicle);
	        setContentView(R.layout.mygroups);
	        
	      
	        context = this.getApplicationContext();
	       
	        
	        addBtn = (Button) findViewById(R.id.addPlaceBtn);
	        addBtn.setOnClickListener(myBtnClickListener);
	        httppostFG = HttpPostFG.getInstance();
	    
	        
	        //
	       
	        groupQueue = HttpPostFG.getJoinedGroupList(); //PostList.joinedGroupsList;
	        
	        Log.i(TAG,"^^^groupQueue length: "+groupQueue.size());
	        
	        
	        
	        setListAdapter(new MyGroupAdapter(this,R.layout.nearby_grouprow, groupQueue));
			
	       ((BaseAdapter) getListAdapter()).notifyDataSetChanged();
	       
	       
	 }



	@Override
	public void onItemSelected(AdapterView<?> aparent, View v, int position,
			long id) {
		// TODO Auto-generated method stub
		Log.i(TAG," MyGroupPage::: onItemSelected!!!  ");
		Log.i(TAG, "item selected: "+groupQueue.get(position).getName());
		 Toast.makeText(this,"selected: " +groupQueue.get(position).getName(), Toast.LENGTH_LONG).show();

		
		
	}



	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	 
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.i(TAG," MyGroupPage::: onListItemClick!!!  ");
		
		Log.i(TAG, "item selected: "+groupQueue.get(position).getName());
		Toast.makeText(this,"selected: " +groupQueue.get(position).getName(), Toast.LENGTH_LONG).show();
		
		
		String gid = groupQueue.get(position).getId();
		Constant.defaultGroupID = gid;
		Constant.currentGroupName = groupQueue.get(position).getName();
		
		Log.i(TAG, "item selected GID: "+gid);
		 //Log.i(TAG, "defaultGroupID: "+Constant.defaultGroupID);
			
			ArrayList<String> keys = new ArrayList<String>();
			ArrayList<String> values = new ArrayList<String>();
			
			keys.add(Constant.GROUP_ID);
			values.add(gid);
				
			httppostFG.postToServer(Constant.GET_GRP_CONTENT, keys, values);
		 
		/*
		Intent intent = new Intent();
	    
		intent.setClass(this,postView.class);
	    
	    
	    intent.putExtra(Constant.LAT, postQueue.get(position).getLat());
	    intent.putExtra(Constant.LNG, postQueue.get(position).getLng());
	    intent.putExtra(Constant.TEXT, postQueue.get(position).getText());
	    intent.putExtra(Constant.AUTHOR_NAME, postQueue.get(position).getAuthorName());
	    
	    startActivity(intent);
		*/
		
		//Toast.makeText(this, "TITLE:" +postQueue.get(position).getText(), Toast.LENGTH_LONG).show();
	}	


	/*
		
		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			//selection.setText(items[position]);
			 Log.i(TAG," MyGroupPage::: onItemSelected!!!  ");
			Log.i(TAG, "item selected: "+filterByStr[position]);
		}

		public void onNothingSelected(AdapterView<?> parent) {
			//selection.setText("");
			 Log.i(TAG," MyGroupPage::: onNothingSelected!!!  ");
		}

		@Override
		protected void onListItemClick(ListView l, View v, int position, long id) {
			 // TODO Auto-generated method stub
			 super.onListItemClick(l, v, position, id);
			 Log.i(TAG," MyGroupPage::: ^^^onListItemClick!!!  ");
			 String selection = l.getItemAtPosition(position).toString();
			 Toast.makeText(this, selection, Toast.LENGTH_LONG).show();
			}
		
		*/
}
