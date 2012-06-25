package com.feedgeorge.android;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GroupPage extends Activity {

	Button backBtn, joinBtn;
	TextView groupNameTxt, descTxt, memberCountTxt, postCountTxt; 
	
	HttpPostFG httpPostFG;
	
	public static String  TAG = "FG-1";
	
	static Group currentgroup;
	
	
	private OnClickListener myClickListener = new OnClickListener() {
		public void onClick(View v) {
			
			if(v.equals(backBtn)){
				
				
				
				
			}
			else if(v.equals(joinBtn)){
				
				ArrayList<String> key = new ArrayList<String>();
				ArrayList<String> value = new ArrayList<String>();
				
				key.add(Constant.GROUP_ID);
				value.add(currentgroup.getId());
				httpPostFG.postToServer(Constant.JOIN_GROUP, key, value);
			}
			
		}

		
	};
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.group_place);
	        
	        httpPostFG = HttpPostFG.getInstance();
	        
	        backBtn = (Button) this.findViewById(R.id.backBtn);
	        joinBtn = (Button) this.findViewById(R.id.joinBtn);
	        
	        backBtn.setOnClickListener(myClickListener);
	        joinBtn.setOnClickListener(myClickListener);
	        
	        groupNameTxt = (TextView) this.findViewById(R.id.textGrpName);
	        descTxt = (TextView) this.findViewById(R.id.textGrpDesc);
	        memberCountTxt = (TextView) this.findViewById(R.id.txtMemberCount);
	        postCountTxt = (TextView) this.findViewById(R.id.txtPostCount);
	        
	        
	        groupNameTxt.setText(currentgroup.getName());
	        descTxt.setText(currentgroup.getDescription());
	        memberCountTxt.setText("Total members: "+currentgroup.getMemberCount());
	        postCountTxt.setText("Total posts: "+currentgroup.getPostCount());
	        
	        
	        
	     
		}
	 
	 public static Group setSelectedGroup(Group g)
	 {
		 
		 return currentgroup = g;
	 }

	
}
