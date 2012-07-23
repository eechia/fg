package com.feedgeorge.android;

import java.io.FileNotFoundException;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AddEventOLD extends Activity{
	
	Button startDateBtn, endDateBtn, uploadImgBtnEvent,submitEventBtn;
	
	private static final int START_DATE_DIALOG_ID = 3; 
	private static final int END_DATE_DIALOG_ID = 4; 
	Context context;
	
	TextView startDateEventTxt, endDateEventTxt;
	TextView eventCapTxt;
	
	ToggleButton toggleLocationEvent;
	
	ImageView eventImg;
	
	Bitmap bitmap;
	
	public static String  TAG = "FG-1";
	
	private OnClickListener myClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			if(v.equals(startDateBtn)){
				
				 showDialog(START_DATE_DIALOG_ID);
				
			}else if(v.equals(endDateBtn)){
				showDialog(END_DATE_DIALOG_ID);
			
			}else if(v.equals(uploadImgBtnEvent)){
				Intent intent = new Intent(Intent.ACTION_PICK,
    			android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				//intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
    			startActivityForResult(intent, 0);
			}
			
		}
		
	};
	
	@Override
	 public void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.add_event);
	     
	     context = PlacesList.getAppContext();
	     
	     startDateBtn = (Button) findViewById(R.id.startDateBtn);
	     startDateBtn.setOnClickListener(myClickListener);
	     endDateBtn = (Button) findViewById(R.id.endDateBtn);
	     endDateBtn.setOnClickListener(myClickListener);
	     
	     
	     uploadImgBtnEvent = (Button) findViewById(R.id.uploadImgBtnEvent);
	     uploadImgBtnEvent.setOnClickListener(myClickListener);
	     
	     /*
	     submitEventBtn = (Button) findViewById(R.id.submitEventBtn);
	     submitEventBtn.setOnClickListener(myClickListener);
	     */
	     toggleLocationEvent = (ToggleButton) findViewById(R.id.toggleLocationEvent);
	     
	     startDateEventTxt = (TextView) findViewById(R.id.startDateEventTxt); 
	     endDateEventTxt = (TextView) findViewById(R.id.endDateEventTxt);
	     eventCapTxt = (TextView) findViewById(R.id.eventCapTxt);
	     
	     
	     eventImg = (ImageView) findViewById(R.id.eventImg);
	     
	}
	
	 @Override
     protected Dialog onCreateDialog(int id) {
		 
		 switch(id){
		 
		 case START_DATE_DIALOG_ID:
			 
			 DatePickerDialog dateDlg = new DatePickerDialog(this, 
			         new DatePickerDialog.OnDateSetListener() {
			          
			         public void onDateSet(DatePicker view, int year,
			                                             int monthOfYear, int dayOfMonth) 
			         {
			                    Time chosenDate = new Time();        
			                    chosenDate.set(dayOfMonth, monthOfYear, year);
			                    long dtDob = chosenDate.toMillis(true);
			                    CharSequence strDate = DateFormat.format("MMMM dd, yyyy", dtDob);
			                    startDateEventTxt.setText(strDate);
			                    Toast.makeText(context, 
			                         "START Date picked: " + strDate, Toast.LENGTH_SHORT).show();
			        }

					}, 2011,0, 1);
			             
			      dateDlg.setMessage("Start date");
			      return dateDlg;
			
		 case END_DATE_DIALOG_ID:
			 DatePickerDialog dateDlg1 = new DatePickerDialog(this, 
			         new DatePickerDialog.OnDateSetListener() {
			          
			         public void onDateSet(DatePicker view, int year,
			                                             int monthOfYear, int dayOfMonth) 
			         {
			                    Time chosenDate = new Time();        
			                    chosenDate.set(dayOfMonth, monthOfYear, year);
			                    long dtDob = chosenDate.toMillis(true);
			                    CharSequence strDate = DateFormat.format("MMMM dd, yyyy", dtDob);
			                    endDateEventTxt.setText(strDate);
			                    Toast.makeText(context, 
			                         "END Date picked: " + strDate, Toast.LENGTH_SHORT).show();
			        }

					}, 2011,0, 1);
			             
			      dateDlg1.setMessage("End date");
			      return dateDlg1;
			 
			      
		 }
		 
		 return null;
	 }
	 
	 @Override
     protected void onPrepareDialog(int id, Dialog dialog) {
             super.onPrepareDialog(id, dialog);
             switch (id) {
            
                     
             case START_DATE_DIALOG_ID:
		         DatePickerDialog dateDlg = (DatePickerDialog) dialog;
		         int iDay,iMonth,iYear;
		
		         // Always init the date picker to today's date
		         Calendar cal = Calendar.getInstance();
		         iDay = cal.get(Calendar.DAY_OF_MONTH);
		         iMonth = cal.get(Calendar.MONTH);
		         iYear = cal.get(Calendar.YEAR);
		         dateDlg.updateDate(iYear, iMonth, iDay);
		         break;
		         
             case END_DATE_DIALOG_ID:
		         DatePickerDialog dateDlg1 = (DatePickerDialog) dialog;
		         int iDay1,iMonth1,iYear1;
		
		         // Always init the date picker to today's date
		         Calendar cal1 = Calendar.getInstance();
		         iDay1 = cal1.get(Calendar.DAY_OF_MONTH);
		         iMonth1 = cal1.get(Calendar.MONTH);
		         iYear1 = cal1.get(Calendar.YEAR);
		         dateDlg1.updateDate(iYear1, iMonth1, iDay1);
		         break;
		         
		         
             }
             return;
     }
	 
	 @Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK){
		 Uri targetUri = data.getData();
		 Log.i(TAG, "TARGETURI: "+targetUri.toString());
		// textTargetUri.setText(targetUri.toString());
		
		 try {
			 bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
			 eventImg.setImageBitmap(bitmap);
		 } catch (FileNotFoundException e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
		 }
		}
		}

}
