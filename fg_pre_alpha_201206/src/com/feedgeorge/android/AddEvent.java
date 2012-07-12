package com.feedgeorge.android;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

public class AddEvent extends Activity{
	
	Button startDateBtn, endDateBtn;
	
	private static final int START_DATE_DIALOG_ID = 3; 
	private static final int END_DATE_DIALOG_ID = 4; 
	Context context;
	
	private OnClickListener myClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			if(v.equals(startDateBtn)){
				
				 showDialog(START_DATE_DIALOG_ID);
				
			}else if(v.equals(endDateBtn)){
				showDialog(START_DATE_DIALOG_ID);
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
			                    Toast.makeText(context, 
			                         "Date picked: " + strDate, Toast.LENGTH_SHORT).show();
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
			                    Toast.makeText(context, 
			                         "Date picked: " + strDate, Toast.LENGTH_SHORT).show();
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

}
