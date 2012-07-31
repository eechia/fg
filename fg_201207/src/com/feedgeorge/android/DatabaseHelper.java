/* 
 * Copyright 2010 Mkini Dot Com Sdn Bhd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.feedgeorge.android;

/*
 * This class is used to create the database.
 * 
 * 
 * @author ee chia (eechia@malaysiakini.com)
 * 
 */



import java.text.SimpleDateFormat;
import java.util.Date;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	public static String  TAG = "FG-1";
	
	public static final String DBNAME = "feedgeorge123";
	
	
	public static final int DATABASE_VERSION = 1;
	public static final String MKINI_EN_TABLE_NAME = "mkini_en";
	public static final String MKINI_INFO_TABLE_NAME = "mkini_info";
	public static final String MKINI_TEST_TABLE_NAME = "mkini_test";
	
	public static final String SID = "sid";
	public static final String TITLE = "title";
	public static final String CAT = "category";
	public static final String DATETIME = "datetime";
	public static final String AUTHOR = "author";
	public static final String CONTENT = "content";
	
	public static final String LASTDATE = "last_date";
	public static final String USERNAME = "user_name";
	
	public final Context myContext;
	public SQLiteDatabase myDB;
	public DatabaseHelper myDBHelper;
	
	 private static final String FG_CONTENT_TABLE_CREATE =
	         "CREATE TABLE " + Constant.FG_CONTENT_TABLE_NAME + " (" +
	         Constant.ID + " TEXT, "+ 
	         Constant.TYPE + " TEXT, " + 
	         Constant.GROUP_ID  + " INTEGER, " +
	         Constant.AUTHOR_ID  + " INTEGER, "+
	         Constant.AUTHOR_NAME  + " TEXT, " +
	         Constant.TEXT  + " TEXT, " +
	         Constant.IMAGE  + " TEXT, "+
	         Constant.LAT  + " TEXT, "+
	         Constant.LNG  + " TEXT, "+
	         Constant.LAST_UPDATE  + " INTEGER, "+
	         Constant.COMMENT_COUNT  + " INTEGER, "+
	         Constant.FULL_CONTENT  + " INTEGER, "+
	         
			 Constant.EVENT_DATE  + " TEXT, "+ 
			 Constant.USER_ANSWER + " TEXT, " +
			 Constant.OPTION  + " TEXT, "+ 
		     
	         Constant.IMAGE_FULL  + " TEXT);"; 
	 
	 private static final String FG_USER_TABLE_CREATE =
	                "CREATE TABLE " + MKINI_INFO_TABLE_NAME + " (" +
	                LASTDATE + " TEXT, "+
	                USERNAME + " TEXT);";
	 
	 private static final String MKINI_TEST_TABLE_CREATE =
         "CREATE TABLE " + MKINI_TEST_TABLE_NAME + " (" +
         LASTDATE + " TEXT);";

	 
	 
	 public DatabaseHelper(Context context) {
			super(context, DBNAME, null, 1);
			
			Log.i(TAG,"!!!!!!DatabaseHelper(): context: "+context.getPackageName());
			myContext = context;
			
		}
	
	public DatabaseHelper(Context context,SQLiteDatabase db) {
		
		
		super(context, DBNAME, null, 1);
		
		Log.i(TAG,">>>> DatabaseHelper: dbname: "+ DBNAME);
		
		Log.i(TAG, "context: "+context.getPackageName());
		
		myContext = context;
		//myDB = db;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		//db.execSQL("CREATE TABLE tasks (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, details TEXT);");
		//db.
		Log.i(TAG,">>>>>>>>> DBHELPER: onCreate table: " +Constant.FG_CONTENT_TABLE_NAME);
		
		if(!tableExists(Constant.FG_CONTENT_TABLE_NAME, db)){
			
			db.execSQL(FG_CONTENT_TABLE_CREATE);
			Log.i(TAG,">>>>>>>>> DBHELPER:" +Constant.FG_CONTENT_TABLE_NAME+ " created!");
		
		}
		/*
		else if(!tableExists(Constant.MKINI_USER_TABLE_NAME, db)){
			
			db.execSQL(MKINI_USER_TABLE_CREATE);
			Log.i("comment","creating user table...");
		
		} */
		
		 //db.close();
		
	
	}
	
	public void test(SQLiteDatabase db){
		
		String lastdate="";
		ContentValues cv = null;
		
		 SimpleDateFormat formatter = new SimpleDateFormat(
         "yyyy-MM-dd%20HH:mm:ss");
		 
		 Date currentTime_1 = new Date();
		 String currentDateTime = formatter.format(currentTime_1);
		 
		
		 
		 
		 String query = "SELECT "+ LASTDATE + 
			" FROM " +DatabaseHelper.MKINI_INFO_TABLE_NAME;
		 Cursor taskCursor = db.rawQuery(query, null);
		//String[] str = new String[taskCursor.getCount()];
		//Log.d("db","Mkini_INFO: CursorCOUNT: " +taskCursor.getCount());
		
	
		if(taskCursor.getCount() >0)
        {
           
			lastdate = taskCursor.getString(taskCursor.getColumnIndex(LASTDATE));
		
        }
        else
        {
        	lastdate = "1960-01-01%2000:00:00";// new String[] {};
        	currentDateTime = lastdate;

        }
		
		System.out.println("lastdate: "+lastdate);
		//Log.i("db","lastdate: "+lastdate);
		
		cv.put(LASTDATE, currentDateTime);						
		db.insert(MKINI_INFO_TABLE_NAME, LASTDATE, cv);
		taskCursor.requery();
		taskCursor.close();
		//db.close();
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+Constant.FG_CONTENT_TABLE_NAME);
		//db.execSQL("DROP TABLE IF EXISTS "+MKINI_INFO_TABLE_NAME);
		onCreate(db);
		
	}
	
	public DatabaseHelper open() throws SQLException {
	     myDBHelper = new DatabaseHelper(myContext,myDB);
	     myDB = myDBHelper.getWritableDatabase();
	     return this;
	  }
	
	
	public boolean tableExists(String tableName, SQLiteDatabase db){
		
		 String checkTable = "SELECT name FROM sqlite_master WHERE type='table' AND name='"  
			 +tableName + "';";
		 Log.i("sync","tableName: "+tableName);
		 
		 Cursor taskCursor = db.rawQuery(checkTable, null);
		 int size = taskCursor.getCount();
		 taskCursor.close();
		 Log.i("sync","size: "+size);
		 
		 
		 if(size<1)
		 { 
			 return false; 
		 }else{
			 return true;
		 }
		 
		 
		
	}
	
	}

