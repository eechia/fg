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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class PlacesList extends ListActivity {
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
			    startActivity(intent);
				
				
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
        
       
        
        context = this.getApplicationContext();
        
        
    	/*
        String email = "chfoo@feedgeorge.com";
    	String password = "adm123m";
        httpPostFG.postLogin(email,password);
       */
        
        httpPostFG.getNearbyGroup();
        nearbyGroupList = httpPostFG.getNearbyGroupsList();
        setListAdapter(new PlacesListMainAdapter(this, R.layout.nearby_grouprow, nearbyGroupList));
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
            }
        }
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


    
    

}

