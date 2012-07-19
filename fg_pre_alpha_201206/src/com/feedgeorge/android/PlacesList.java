package com.feedgeorge.android;

import java.util.ArrayList;

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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class PlacesList extends ListActivity implements OnItemSelectedListener{
    /** Called when the activity is first created. */
	
	HttpPostFG httpPostFG = HttpPostFG.getInstance();
	ListView allPlaceslist;
	private ArrayAdapter<String> nearbyGrouplistAdapter ;
	ArrayList<Group> nearbyGroupList;
	Button loginAndSignUpBtn;
	static Context context;
	
	
	
	public static String  TAG = "FG-1";
	
	private OnClickListener myClickListener = new OnClickListener() {
		public void onClick(View v) {
			
			if(v.equals(loginAndSignUpBtn)){
				
				//addPost();
				
				Intent intent = new Intent();
			    intent.setClass(context,LoginSignUpPage.class);
			    
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
        setContentView(R.layout.main);
        
      
       
        //allPlaceslist = (ListView) findViewById( R.id);
        
        
        
        loginAndSignUpBtn = (Button) findViewById(R.id.loginSignUpBtn);
        loginAndSignUpBtn.setOnClickListener(myClickListener);
        
        if(Constant.LOGGED_IN )
        	loginAndSignUpBtn.setVisibility(View.GONE);
        
        context = this.getApplicationContext();
        
        httpPostFG.getApplicationContext(context);
        
    	/*
        String email = "chfoo@feedgeorge.com";
    	String password = "adm123m";
        httpPostFG.postLogin(email,password);
       */
        
        httpPostFG.getNearbyGroup();
        
        nearbyGroupList = httpPostFG.getNearbyGroupsList();
        
        if(nearbyGroupList != null){
        	setListAdapter(new PlacesListMainAdapter(this, R.layout.nearby_grouprow, nearbyGroupList));
            ((BaseAdapter) getListAdapter()).notifyDataSetChanged();
        
        }else{
        	Toast.makeText(this, "Server is down", Toast.LENGTH_LONG).show();
        }
        
        
        
       
       
	}

	
    
    
    protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
    	/*
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
        */
    }
	
    
    class PlacesListMainAdapter extends ArrayAdapter<Group> {
    	public PlacesListMainAdapter(PlacesList placesList, int nearbyGrouprow, ArrayList<Group> nearbyGroupList) {
    		super(PlacesList.this, R.layout.nearby_grouprow, nearbyGroupList );
    	}

    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {
    		View rowView = convertView;				
    		if(rowView == null) {
    			LayoutInflater inflater = getLayoutInflater();
    			rowView = inflater.inflate(R.layout.nearby_grouprow, parent, false);
    		}
    		TextView label = (TextView)rowView.findViewById(R.id.groupText);	
    		label.setText(nearbyGroupList.get(position).getName());				
    		
    		return(rowView);
    	}	
    }

    public static Context getAppContext(){
    	
    	//context = getApplicationContext();
    	 return context; 
    }



    protected void onListItemClick(ListView l, View v, int position, long id) {
		
		
    	super.onListItemClick(l, v, position, id);
    	Log.i(TAG, ">>>>>>>>>>>>>>>!!!!onListItemClick: "+nearbyGroupList.get(position).getName());
    	
    	GroupPage.setSelectedGroup(nearbyGroupList.get(position));
    	Intent intent = new Intent();
	    intent.setClass(context,GroupPage.class);
	    startActivity(intent);
		
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {
		//selection.setText(items[position]);
    	Log.i(TAG, "!!!!!!!item selected: "+nearbyGroupList.get(position).getName());
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

