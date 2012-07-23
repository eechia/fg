package com.feedgeorge.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class MyGroupPageList extends ListActivity implements OnItemSelectedListener{
    /** Called when the activity is first created. */
	
	HttpPostFG httpPostFG = HttpPostFG.getInstance();
	ListView allPlaceslist;
	
	
	static Context context;
	
	String[] filterByStr = {"All", "Posts","Events","Surveys"};
	 Button addBtn;
	 Spinner filterSpin;
	 
	 List<Group> groupQueue = new ArrayList<Group>();
	 ArrayList<Group> myGroupList;
	
	public static String  TAG = "FG-1";
	
	private OnClickListener myClickListener = new OnClickListener() {
		public void onClick(View v) {
			
			if(v.equals(addBtn)){
				
				//addPost();
				
				Intent intent = new Intent();
			    intent.setClass(context,PlacesList.class);
			    
			    startActivityForResult(intent, Constant.LOGIN);
			   
			   // startActivity(intent);
				/*
				Intent intent = new Intent();
			    intent.setClass(context,FGDashboard.class);			    
			    startActivityForResult(intent, Constant.LOGIN);
				*/
				
			}/*
			else if(v.equals(buttonLoadImage)){
						
				Intent intent = new Intent(Intent.ACTION_PICK,
					    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						startActivityForResult(intent, 0);
				
			}else if(v.equals(mShareBtn)){
				
				//getContentList();
				Intent intent = new Intent();
			    intent.setClass(context,PostList.class);
			    startActivity(intent);
				
				
			}*/
			
		}

		
	};
	
    @SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mygroups);
        
        addBtn = (Button) findViewById(R.id.addPlaceBtn);
        addBtn.setOnClickListener(myClickListener);
        
    
        
       
       
        groupQueue = HttpPostFG.getJoinedGroupList(); //PostList.joinedGroupsList;
        
        Log.i(TAG,"^^^groupQueue length: "+groupQueue.size());
     
        
        context = PlacesList.getAppContext();
        		
        httpPostFG.getApplicationContext(context);
        
    	/*
        String email = "chfoo@feedgeorge.com";
    	String password = "adm123m";
        httpPostFG.postLogin(email,password);
       */
        
        //httpPostFG.getNearbyGroup();
        myGroupList = httpPostFG.getJoinedGroupList();
        setListAdapter(new MyGroupListAdapter(this, R.layout.mygroups_row, myGroupList));
        ((BaseAdapter) getListAdapter()).notifyDataSetChanged();
        
        /*
        nearbyGrouplistAdapter = new ArrayAdapter<String>(this, R.layout.nearby_grouprow, httpPostFG.getNearbyGroups()); 
        allPlaceslist.setAdapter(nearbyGrouplistAdapter);
        */
        //nearbyGroupList = httpPostFG.getNearbyGroups();
       
	}

	
    
    
    protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
        if (requestCode == Constant.RESUME) {
            if (resultCode == RESULT_OK) {
                // A contact was picked.  Here we will just display it
                // to the user.
                //startActivity(new Intent(Intent.ACTION_VIEW, data));
            	Log.i(TAG,">>>>>>>>>>RESUME!!!!!!!!!");
            	onResume();
            	
            }else if(resultCode == Constant.LOGIN){
            	Log.i(TAG,">>>>>>>>>>onActivityResult - Login!!!!!!!!!");
            	onResume();
            }
        }
    }
	
    
    class MyGroupListAdapter extends ArrayAdapter<Group> {
    	public MyGroupListAdapter(MyGroupPageList placesList, int nearbyGrouprow, ArrayList<Group> nearbyGroupList) {
    		super(MyGroupPageList.this, R.layout.mygroups_row, nearbyGroupList );
    	}

    	@Override
    	public View getView(final int position, View convertView, ViewGroup parent) {
    		View rowView = convertView;				
    		if(rowView == null) {
    			LayoutInflater inflater = getLayoutInflater();
    			rowView = inflater.inflate(R.layout.mygroups_row, parent, false);
    		}
    		TextView label = (TextView)rowView.findViewById(R.id.joinedGroupText);	
    		label.setText(myGroupList.get(position).getName());		
    		
    		/*
    		Button leaveGrpBtn = (Button)rowView.findViewById(R.id.leaveGrpBtn);
    		
    		leaveGrpBtn.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//Toast.makeText(this,"onclicked: " +myGroupList.get(position).getName(), Toast.LENGTH_LONG).show();
					Log.i(TAG, "^^^^^LEAVE GROUP: "+myGroupList.get(position).getName());
				}
    			
    			
    		} );
    		*/
    		return(rowView);
    	}	
    }

    public static Context getAppContext(){
    	
    	//context = getApplicationContext();
    	 return context; 
    }



    protected void onListItemClick(ListView l, View v, int position, long id) {
		
		
    	super.onListItemClick(l, v, position, id);
    	Log.i(TAG, ">>>>>>>>>>>>>>>!!!!onListItemClick: "+myGroupList.get(position).getName());
    	
    	Toast.makeText(this,"onclicked: " +myGroupList.get(position).getName(), Toast.LENGTH_LONG).show();
    	/*
    	GroupPage.setSelectedGroup(myGroupList.get(position));
    	Intent intent = new Intent();
	    intent.setClass(context,GroupPage.class);
	    startActivity(intent);
		*/
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {
		//selection.setText(items[position]);
    	Log.i(TAG, "!!!!!!!item selected: "+myGroupList.get(position).getName());
    	
    	Toast.makeText(this,"onItemSelected: " +myGroupList.get(position).getName(), Toast.LENGTH_LONG).show();
    	/*
    	Log.i(TAG, "item selected: "+nearbyGroupList.get(position).getName());
    	
    	GroupPage.setSelectedGroup(nearbyGroupList.get(position));
    	Intent intent = new Intent();
	    intent.setClass(context,GroupPage.class);
	    startActivity(intent);
	    */
	}

	public void onNothingSelected(AdapterView<?> parent) {
		//selection.setText("");
		Log.i(TAG, "onNothingSelected()");
	}

    
    

}

