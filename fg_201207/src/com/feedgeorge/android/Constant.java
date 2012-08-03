package com.feedgeorge.android;

import android.widget.TabHost;

public class Constant {
	
	/*
	 * String id, type, text, groupId, authorId;
	String authorName, image, lat, lng, lastUpdate;
	 */
	
	 /* RETURN: {"success":true,"error":200,"reason":"Success",
	"result":
	{"content":[
	{"id":"46",
	"type":"1",
	"text":"jln sultan",
	"groupId":"2",
	"authorId":"6",
	"authorName":"chfoo",
	"image":"http:\/\/assets.feedgeorge.com\/photos\/t\/7\/38f82706a95b41cbbd3ada3075612a19.jpg",
	"lat":"3.128668",
	"lng":"101.678940",
	"lastUpdate":"2012-06-05 17:08:35"},
 */
	
	
	static public boolean READ_FR_DB = false;
	static public String SELECTED_CONTENT_TYPE = "all";
	
	
//static final String GROUP_ID = "groupId";
	
	static public String FG_CONTENT_TABLE_NAME = "contenttable";
	static public String FG_USER_TABLE_NAME = "usertable";
	static public String FG_MD5_TABLE_NAME = "md5tracker";
	
	static public String MD5 = "md5";
	
	static public boolean addContentToDB = true;
	
	static public String defaultGroupID = null;
	static public String currentGroupName = null;
	static public String currentGroupIcon = null;
	
	//public static TabHost tabHost;
	
	public static boolean LOGGED_IN = false;
	
	//public String groupID = "2";
	static final String apiKey_value = "f3f0f6dbc5e442f6afc6687e59912f23"; 
	
	static final String URL_USER = "http://developer.feedgeorge.com/user/";
	static final String URL_GROUP = "http://developer.feedgeorge.com/group/";
	static final String URL_CONTENT= "http://developer.feedgeorge.com/content/";
	//
	
	//POST TYPE:
	static final String ALL= "0";
	static final String POST= "1";
	static final String EVENT = "2";
	static final String SURVEY = "3";
	
	//POST 
	static final int LOGIN = 0;
	static final int SIGN_UP = 1;
	static final int JOIN_GROUP = 2;
	static final int GET_JOINED_GROUP = 3;
	static final int GET_GRP_CONTENT = 4;
	static final int ADD_POST = 5;
	static final int GET_COMMENTS = 6;
	static final int ADD_COMMENT = 7;
	static final int ADD_EVENT = 8;
	static final int ADD_SURVEY = 9;
	static final int GET_FULLCONTENT = 10;
	
	
	//POST MESSAG
	static final String NO_ERROR = "200";
	
	static final String API_KEY = "apiKey";
	
	static final String SUCCESS = "success";
	static final String ERROR = "error";
	static final String REASON = "reason";
	static final String RESULT = "result";
	static final String CONTENT = "content";
	
	
	
	
	static final String ID = "id";
	static final String TYPE = "type";
	static final String TEXT = "text";
	static final String GROUP_ID = "groupId";
	
	static final String AUTHOR_ID = "authorId";
	static final String AUTHOR_NAME = "authorName";
	static final String IMAGE = "image";
	
	static final String LAT = "lat";
	static final String LNG = "lng";
	static final String LAST_UPDATE = "lastUpdate";
	static final String COMMENT_COUNT = "commentCount";
	
	//extras for all Posts :report, event, survey
	static final String IMAGE_FULL = "imageFull";
	
	//extras for EVENT
	static final String EVENT_DATE = "eventDate";
	
	//extras for SURVEY
	//static final String USER_ANSWER = "userAnswer";
	static final String OPTION = "options";
	
	//extras for db
	static final String FULL_CONTENT = "fullContent";
	
	//COMMENT
	static final String eventDate = "eventDate";
	
	//SURVEY
	static final String OPTIONS = "options";
	static final String SURVEY_ID = "idSurvey";
	static final String SURVEY_DESC = "description";
	static final String SURVEY_COUNT = "count";
	static final String USER_ANSWER = "userAnswer";
	
	
	//group
	static final String GROUPS = "groups";
	static final String NAME = "name";
	static final String DESC = "description";
	static final String ROLE = "role";
	static final String POLYGON = "polygon";
	static final String ICON = "icon";
	static final String POST_COUNT = "postCount";
	static final String MEMBER_COUNT = "memberCount";
	
	//login
	static final String DISPLAY_NAME = "displayName";
	static final String PROFILE_PIC = "pic";
	
	//comment
	
	static final String COMMENTER_ID = "commenterId";
	static final String COMMENT_ID = "id";
	//static final String LAST_UPDATE = "lastUpdate";
	static final String COMMENTER_NAME = "commenterName";
	static final String COMMENT = "comment";
	static final String COMMENTS = "comments";
	static final String MORE = "more";
	
	
	
	static final int RESUME = 0;
}
