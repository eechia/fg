package com.feedgeorge.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SignUpPage extends Activity {

	
	Button mSignUpBtn;
	
	ImageButton mCancelBtn;
	
	EditText mScreenNameEdit, mEmailSUEdit, mPwdSUEdit;
	HttpPostFG httpPostFG;
	
	ImageView logo;
	
	Context context = null;
	
	private RelativeLayout layoutToAdd;
	
	public static String  TAG = "FG-1";
	boolean clearEmailSignUp = true, clearPwdSignUp = true;
	boolean clearScreenName = true;
	
	private OnClickListener myClickListener = new OnClickListener() {
		public void onClick(View v) {
			
			 if(v.equals(mSignUpBtn)){
				
				httpPostFG.postAddUser(mEmailSUEdit.getText().toString(),mPwdSUEdit.getText().toString());
				
			}else if(v.equals(mCancelBtn)){
				
				/*
				Intent intent = new Intent();
			    intent.setClass(context, PlacesList.class);
			    startActivity(intent);
				*/
				Log.i(TAG,">>> CANCEL PRESSED");
				//finishActivity(Constant.LOGIN);
				
				//Intent intent = new Intent();
				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				
				onBackPressed();
				
			}else if(v.equals(mEmailSUEdit)){
				
				if(clearEmailSignUp){
					mEmailSUEdit.setText("");
					clearEmailSignUp = false;
				}
				
				
				
			}else if(v.equals(mPwdSUEdit)){
				
				if(clearPwdSignUp){
					mPwdSUEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					mPwdSUEdit.setText("");
					clearPwdSignUp = false;
					
				}
				
			}else if(v.equals(mScreenNameEdit)){
				
				if(clearScreenName){
					
					mScreenNameEdit.setText("");
					clearScreenName = false;
					
				}
				
			}
			else if(v.equals(logo)){
				mEmailSUEdit.setText("apple@b.com");
				mPwdSUEdit.setText("abc");
				
				
			}
			
			
			
		}

		
	};
	
	
	
		
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.signup);
	        
	        //layoutToAdd = (RelativeLayout) findViewById(R.layout.login_signup);
	        
	        
	        layoutToAdd = new RelativeLayout(this);
	        
	        httpPostFG = HttpPostFG.getInstance();
	        
	        
	        logo = (ImageView) this.findViewById(R.id.loginLogo);
	        logo.setOnClickListener(myClickListener);
	        
	        mSignUpBtn = (Button) findViewById(R.id.btnSignUp);
            mSignUpBtn.setOnClickListener(myClickListener);
	        
	       
	        
	        mCancelBtn = (ImageButton) this.findViewById(R.id.btnCancel);
	        
	        
	       
	        
	       
	        
	        mCancelBtn.setOnClickListener(myClickListener);
	       
	        
	        
	      
	        
	        
	       
	        
	     
	        mScreenNameEdit = (EditText) this.findViewById(R.id.editScreenName);
	        mEmailSUEdit = (EditText) this.findViewById(R.id.editEmailSU);
	        mPwdSUEdit = (EditText) this.findViewById(R.id.editPasswordSU);
	        
	       
	        mScreenNameEdit.setOnClickListener(myClickListener);
	        mEmailSUEdit.setOnClickListener(myClickListener);
	        mPwdSUEdit.setOnClickListener(myClickListener);
	        
	        
	        context = getApplicationContext();
	       
	     
		}
	 
	 public void setContext(Context c){
		 context = c;
	 }
	 
	 public void onBackPressed() {
		    // do something on back.
		 Log.i(TAG,">>> onBackPressed");
		 super.onBackPressed();
		    return;
		}
}

