package com.feedgeorge.android;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class PlacesList extends ListActivity {
    /** Called when the activity is first created. */
	
	HttpPostFG httpPostFG = HttpPostFG.getInstance();
	ListView allPlaceslist;
	private ArrayAdapter<String> nearbyGrouplistAdapter ;
	ArrayList<Group> nearbyGroupList;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        httpPostFG.getNearbyGroup();
        nearbyGroupList = httpPostFG.getNearbyGroups();
        //allPlaceslist = (ListView) findViewById( R.id);
        
        setListAdapter(new dynamicAdapter(this, R.layout.nearby_grouprow, nearbyGroupList));
        /*
        nearbyGrouplistAdapter = new ArrayAdapter<String>(this, R.layout.nearby_grouprow, httpPostFG.getNearbyGroups());
        
        allPlaceslist.setAdapter(nearbyGrouplistAdapter);
        */
        //nearbyGroupList = httpPostFG.getNearbyGroups();
       
	}

	//TextView label = (TextView) findViewById(R.id.groupText);
	
    
    class dynamicAdapter extends ArrayAdapter<Group> {
    	public dynamicAdapter(PlacesList placesList, int nearbyGrouprow, ArrayList<Group> nearbyGroupList) {
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

