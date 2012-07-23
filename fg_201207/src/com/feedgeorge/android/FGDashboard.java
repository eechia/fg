package com.feedgeorge.android;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class FGDashboard extends TabActivity implements OnTabChangeListener{

	
	private static final String TAB_HOME = "home";
	private static final String TAB_PLACE = "place";
	private static final String TAB_ADD = "add";
	private static final String TAB_SETTINGS = "Setting";
	
	private static final String TAG = "FG-1";

	TabSpec firstTabSpec;
    TabSpec secondTabSpec;
    TabSpec addTabSpec;
    TabSpec settingsTabSpec;
    Resources res;
    
    static TabHost tabHost;
    Context context;
    
    Spinner filterSpin;
    String[] filterByStr = {"All", "Posts","Events","Surveys"};
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab);
        
        context = PlacesList.getAppContext();
        
        tabHost = (TabHost)findViewById(android.R.id.tabhost);
        tabHost.setOnTabChangedListener(this);
        res = getResources(); 
        
        /* tid1 is firstTabSpec Id. Its used to access outside. */
        firstTabSpec = tabHost.newTabSpec(TAB_HOME);
        secondTabSpec = tabHost.newTabSpec(TAB_PLACE);
        addTabSpec = tabHost.newTabSpec(TAB_ADD);
        settingsTabSpec = tabHost.newTabSpec(TAB_SETTINGS);
        
        
        Intent homeIntent = new Intent();
        //homeIntent.setClass(context ,PostList.class);
        homeIntent.setClass(context ,TabGroup1Activity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        
        Intent placesIntent = new Intent();
        placesIntent.setClass(context ,MyGroupPage.class);
        placesIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        
        
        Intent addIntent = new Intent();
        addIntent.setClass(context ,AddPage.class);
        addIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        firstTabSpec.setIndicator("Home", res.getDrawable(R.drawable.taba_home)).setContent(homeIntent);
        secondTabSpec.setIndicator("Places", res.getDrawable(R.drawable.taba_places)).setContent(placesIntent);
        addTabSpec.setIndicator("Add", res.getDrawable(R.drawable.taba_add)).setContent(new Intent(addIntent));
        settingsTabSpec.setIndicator("Settings", res.getDrawable(R.drawable.taba_settings)).setContent(new Intent(context,LoginSignUpPage.class));
        
        
        tabHost.addTab(firstTabSpec);
        tabHost.addTab(secondTabSpec);
        tabHost.addTab(addTabSpec);
        tabHost.addTab(settingsTabSpec);
        
        //filter
        
       /*
        filterSpin = (Spinner) findViewById(R.id.contentSpinner);
        //filterSpin.setOnItemSelectedListener(this);
        
        @SuppressWarnings({ "rawtypes", "unchecked" })
		ArrayAdapter aa = new ArrayAdapter(
				this,
				android.R.layout.simple_spinner_item, 
				filterByStr);

		aa.setDropDownViewResource(
		   android.R.layout.simple_spinner_dropdown_item);
		filterSpin.setAdapter(aa);
        
        filterSpin.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                int item = filterSpin.getSelectedItemPosition();
                Toast.makeText(getBaseContext(), 
                    "You have selected : " + filterByStr[item], 
                    Toast.LENGTH_SHORT).show();
            }



			

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
        });

        */
       

		
        
        
    }
	
	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		
	}

}
