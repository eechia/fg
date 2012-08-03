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

public class LoginSignUpPage extends Activity {

	
	Button mLoginBtn, mSignUpBtn, mGoSignUpBtn;
	
	ImageButton mCancelBtn;
	TextView mEmailTxt, mPwdTxt, mScreenNameTxt, mEmailSUTxt, mPwdSUTxt; 
	EditText mEmailEdit, mPwdEdit, mScreenNameEdit, mEmailSUEdit, mPwdSUEdit;
	HttpPostFG httpPostFG;
	
	ImageView logo;
	
	Context context = null;
	
	private RelativeLayout layoutToAdd;
	
	public static String  TAG = "FG-1";
	boolean clearEmailLogin = true, clearPwdLogin = true;
	
	private OnClickListener myClickListener = new OnClickListener() {
		public void onClick(View v) {
			
			if(v.equals(mLoginBtn)){
				
				//addPost();
				httpPostFG.postLogin(mEmailEdit.getText().toString(), mPwdEdit.getText().toString());
				finish();
				//httpPostFG.postLogin("test","test");
				
				
			}
			else if(v.equals(mSignUpBtn)){
				
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
				
			}else if(v.equals(mEmailEdit)){
				
				if(clearEmailLogin){
					mEmailEdit.setText("");
					clearEmailLogin = false;
				}
				
				
				
			}else if(v.equals(mPwdEdit)){
				
				if(clearPwdLogin){
					mPwdEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					mPwdEdit.setText("");
					clearPwdLogin = false;
					
				}
				
			}else if(v.equals(logo)){
				mEmailEdit.setText("apple@b.com");
				mPwdEdit.setText("abc");
				
				
			}else if(v.equals(mGoSignUpBtn)){
				
				
				Intent intent = new Intent();
			    intent.setClass(context, SignUpPage.class);
			    startActivity(intent);
				
				
			} 
			
			
			
		}

		
	};
	
	
	private OnClickListener SignUPClickListener = new OnClickListener() {
		public void onClick(View v) {
			
			if(v.equals(mSignUpBtn)){
				
				Log.i(TAG,"signup pressed");
				//httpPostFG.postAddUser(mEmailSUEdit.getText().toString(),mPwdSUEdit.getText().toString());
				
			}
		
		}
		};
		
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.login_signup);
	        
	        //layoutToAdd = (RelativeLayout) findViewById(R.layout.login_signup);
	        
	        
	        layoutToAdd = new RelativeLayout(this);
	        
	        httpPostFG = HttpPostFG.getInstance();
	        
	        
	        logo = (ImageView) this.findViewById(R.id.loginLogo);
	        logo.setOnClickListener(myClickListener);
	        
	        mLoginBtn = (Button) this.findViewById(R.id.btnLogin);
	        
	        mCancelBtn = (ImageButton) this.findViewById(R.id.btnCancel);
	        
	        
	        mGoSignUpBtn = (Button) this.findViewById(R.id.btnGoSignUp);
	        
	        mLoginBtn.setOnClickListener(myClickListener);
	        
	        mCancelBtn.setOnClickListener(myClickListener);
	        mGoSignUpBtn.setOnClickListener(myClickListener);
	        
	        
	        mEmailTxt = (TextView) this.findViewById(R.id.txtEmail);
	        
	        mPwdTxt = (TextView) this.findViewById(R.id.textPassword);
	        mScreenNameTxt = (TextView) this.findViewById(R.id.txtScreenName);
	        mEmailSUTxt = (TextView) this.findViewById(R.id.txtEmailSU);
	        mPwdSUTxt = (TextView) this.findViewById(R.id.textPassworSU);
	        
	        mEmailEdit = (EditText) this.findViewById(R.id.editEmail);
	        mPwdEdit = (EditText) this.findViewById(R.id.editPassword);
	        mScreenNameEdit = (EditText) this.findViewById(R.id.editScreenName);
	        mEmailSUEdit = (EditText) this.findViewById(R.id.editEmailSU);
	        mPwdSUEdit = (EditText) this.findViewById(R.id.editPasswordSU);
	        
	        mEmailEdit.setOnClickListener(myClickListener);
	        mPwdEdit.setOnClickListener(myClickListener);
	        
	        
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

