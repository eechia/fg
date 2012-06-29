package com.feedgeorge.android;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class MyGroupPage extends ListActivity implements OnItemSelectedListener {
	
	public static String  TAG = "FG";
	
	//populate content
	 List<Group> groupQueue = new ArrayList<Group>();
	 
	 String[] filterByStr = {"All", "Posts","Events","Surveys"};
	 Button addBtn;
	 Spinner filterSpin;
	 Context context;
	 
	 ArrayList<Group> myGroupList;
	
	/*
	 * Listen to the option selected by the users.
	 */

	private OnClickListener myBtnClickListener = new OnClickListener() {
	


		
		public void onClick(View v) {
			
			Log.i(TAG,">>> myBtnClickListener!!!!!!: onClick");
			 
			if(v.equals(addBtn)){
				
				Log.i(TAG,">>> addBtn...........");
				
			}
			
			}

	
	};

	
	 
	public void onCreate(Bundle icicle) {
	    	
	        super.onCreate(icicle);
	        setContentView(R.layout.mygroups);
	        
	      
	        context = this.getApplicationContext();
	       
	        
	        addBtn = (Button) findViewById(R.id.addPlaceBtn);
	        addBtn.setOnClickListener(myBtnClickListener);
	        
	        /*
	        filterSpin = (Spinner) findViewById(R.id.filterSpinner);
	        filterSpin.setOnItemSelectedListener(this);
	        
	        
	        */
	       
	       
	        groupQueue = HttpPostFG.getJoinedGroupList(); //PostList.joinedGroupsList;
	        
	       setListAdapter(new MyGroupAdapter(this,R.layout.nearby_grouprow, groupQueue));
			
	       ((BaseAdapter) getListAdapter()).notifyDataSetChanged();
	 }
	 


		
		protected void onListItemClick(ListView l, View v, int position, long id) {
			
			
			super.onListItemClick(l, v, position, id);
			String selectedID = groupQueue.get(position).getId();
			
			/*
			Log.i(TAG,"--TITLE:" +postQueue.get(position).getText() + "long:  "+postQueue.get(position).getLng()
					+ "lat: "+postQueue.get(position).getLat());
			
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
		
		
		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			//selection.setText(items[position]);
			Log.i(TAG, "item selected: "+filterByStr[position]);
		}

		public void onNothingSelected(AdapterView<?> parent) {
			//selection.setText("");
		}
		
		
}
