package com.feedgeorge.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginSignUpPage extends Activity {

	
	Button mLoginBtn, mSignUpBtn, mCancelBtn;
	TextView mEmailTxt, mPwdTxt, mScreenNameTxt, mEmailSUTxt, mPwdSUTxt; 
	EditText mEmailEdit, mPwdEdit, mScreenNameEdit, mEmailSUEdit, mPwdSUEdit;
	HttpPostFG httpPostFG;
	
	public static String  TAG = "FG-1";
	
	private OnClickListener myClickListener = new OnClickListener() {
		public void onClick(View v) {
			
			if(v.equals(mLoginBtn)){
				
				//addPost();
				httpPostFG.postLogin(mEmailEdit.getText().toString(), mPwdEdit.getText().toString());
				//httpPostFG.postLogin("test","test");
				
				
			}
			else if(v.equals(mSignUpBtn)){
				
				httpPostFG.postAddUser(mEmailSUEdit.getText().toString(),mPwdSUEdit.getText().toString());
				
			}else if(v.equals(mCancelBtn)){
				
				/*
				Intent intent = new Intent();
			    intent.setClass(context,PostList.class);
			    startActivity(intent);
				*/
				
			}
			
		}

		
	};
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.login_signup);
	        
	        httpPostFG = HttpPostFG.getInstance();
	        
	        mLoginBtn = (Button) this.findViewById(R.id.btnLogin);
	        mSignUpBtn = (Button) this.findViewById(R.id.btnSignUp);
	        mCancelBtn = (Button) this.findViewById(R.id.btnCancel);
	        
	        mLoginBtn.setOnClickListener(myClickListener);
	        mSignUpBtn.setOnClickListener(myClickListener);
	        mCancelBtn.setOnClickListener(myClickListener);
	        
	        
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
	        
	        
	       
	     
		}
}
