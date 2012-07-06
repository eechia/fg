package com.feedgeorge.android;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class postView extends MapActivity {
	Bundle bundle;
	String lat,lng, title, author;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.postview);
	    
	    MapView mapView = (MapView) findViewById(R.id.mapview);
	    mapView.setBuiltInZoomControls(true);
	    mapView.setStreetView(true);
	    
	    List<Overlay> mapOverlays = mapView.getOverlays();
	    Drawable drawable = this.getResources().getDrawable(R.drawable.default_userpic);
	    PostItemizedOverlay itemizedoverlay = new PostItemizedOverlay(drawable, this);
	    
	    Intent i = getIntent();
	    bundle = i.getExtras();
        lat = bundle.getString(Constant.LAT);
       // String content = bundle.getString("content");
        lng = bundle.getString(Constant.LNG);
        title = bundle.getString(Constant.TEXT);
        author = bundle.getString(Constant.AUTHOR_NAME);
        
        double lat_d = Double.parseDouble(lat);  
        double lng_d = Double.parseDouble(lng);  
        
        int lat_int = (int)lat_d;
        int lng_int = (int)lng_d;
        
        
	    
	   //GeoPoint point = new GeoPoint(19240000,-99120000);
        GeoPoint point = new GeoPoint((int) (lat_d * 1E6) ,(int) (lng_d* 1E6));
	    OverlayItem overlayitem = new OverlayItem(point, author, title);
	 
	    
	   // GeoPoint point2 = new GeoPoint(35410000, 139460000);
	    //OverlayItem overlayitem = new OverlayItem(point2, "Sekai, konichiwa!", "I'm in Japan!");
	    
	    
	    itemizedoverlay.addOverlay(overlayitem);
	    mapOverlays.add(itemizedoverlay);
	    
	 
	}

	
	protected boolean isRouteDisplayed() {
	    return false;
	}
}
