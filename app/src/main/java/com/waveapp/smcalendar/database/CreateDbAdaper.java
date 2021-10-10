package com.waveapp.smcalendar.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.waveapp.smcalendar.common.ComConstant;
import com.waveapp.smcalendar.util.ComUtil;
import com.waveapp.smcalendar.util.ViewUtil;

/**
 * 데이터베이스 생성 및 테이블 생성
 * 1.생성테이블 : usermanager, schedule
 * 2.데이터베이스삭제
 * ===========================
 * Version 2 : specialday 변경, todomemo 변경, todofinish 신규
 *           : 공휴일 임시 업데이트
 */

public class CreateDbAdaper {

    private static final String TAG = "SMCalendarDbCreater";
    private DatabaseHelper mDbHelper;

    private SQLiteDatabase mDb;
    /**
     * Database creation sql statement
     * 0. 데이터베이스파일이 정상 적용되었는지 확인할 수 있는 테이블
     * (정상처리후 update 처리)
     */
    
    public static final String DATABASE_CREATE_INSTALLCHECK =
            "create table  IF NOT EXISTS " + ComConstant.DATABASE_TABLE_INSTALLCHECK  +
            "(	_id 		integer 	primary key autoincrement, " +
            "	complete	text			  	" +
            " );";
    
    /**
     * Database creation sql statement
     * 1. 사용자정보테이블 생성 sql문
     * <<변경이력>>
     * 2011.05.20 삭제일,연락처,사용자색깔항목추가
     */
    
    public static final String DATABASE_CREATE_USERMANAGER =
            "create table  IF NOT EXISTS " + ComConstant.DATABASE_TABLE_USERMANAGER  +
            "(	_id 		integer 	primary key autoincrement, " +
            "	name		text		not null, 	" +
            "	birthdate	text		not null,	" +
            "	relation	text		not null,	" +
            "	usercolor	text		 		,	" +
            "	tel1		text		 		,	" +
            "	address		text		 		,	" +
            "	memo		text				,	" +
            "	delyn		text		 		,	" +
            "	deldate		text		 		,	" +
            "	confirmdate	text		not null,	" +
            "	modifydate	text		not null	" + 
            " );";
    public static final String DATABASE_CREATE_IDX1_USERMANAGER =
        "create index IF NOT EXISTS " + ComConstant.DATABASE_INDEX1_USERMANAGER +
        "       				ON  " + ComConstant.DATABASE_TABLE_USERMANAGER  +
        "(	name		asc, 	" +
        "	delyn		asc  	" +
        " );";    
    
    /**
     * Database creation sql statement
     * 2. 스케줄정보테이블 생성 sql문
     */
    
    public static final String DATABASE_CREATE_SCHEDULE =
            "create table IF NOT EXISTS " + ComConstant.DATABASE_TABLE_SCHEDULE  +
            "(	_id 		integer 	primary key autoincrement, " +
            "	name		text		not null, 	" +
            "	userid		text		not null,	" +
            "	cycle		text		not	null,	" +
            "	sunday		text				,	" +
            "	monday		text				,	" +
            "	tuesday		text				,	" +
            "	wednesday	text				,	" +
            "	thursday	text				,	" +
            "	friday		text				,	" +
            "	saturday	text				,	" +
            "	startdate	text		not null,	" +
            "	enddate		text		not null,	" +
            "	alldayyn	text		not null,	" +
            "	starttime	text		 		,	" +
            "	endtime		text		 		,	" +
            "	alarm		text				,	" +
            "	tel1		text				,	" +
            "	tel2		text				,	" +
            "	cost		integer				,	" +
            "	memo		text				,	" +
            "	otherkind	text		 		,	" +
            "	otherid		integer		 		,	" +
            "	delyn		text		 		,	" +
            "	deldate		text		 		,	" +
            "	confirmdate	text		not null,	" +
            "	modifydate	text		not null,	" + 
            "	repeatdate	text		 		,	" +
            "	alarm2		text		 	 	" +
            " );";
    public static final String DATABASE_CREATE_IDX1_SCHEDULE =
	        "create index IF NOT EXISTS " + ComConstant.DATABASE_INDEX1_SCHEDULE +
	        "       				ON  " + ComConstant.DATABASE_TABLE_SCHEDULE  +
	        "(	startdate	asc, 	" +
	        "	enddate		asc, 	" +
	        "	delyn		asc	    " + 
	        " );";  
    public static final String DATABASE_CREATE_IDX2_SCHEDULE =
	        "create index IF NOT EXISTS " + ComConstant.DATABASE_INDEX2_SCHEDULE +
	        "       				ON  " + ComConstant.DATABASE_TABLE_SCHEDULE  +
	        "(	userid		asc, 	" +
	        "	delyn		asc	    " +
	        " );";      
    /**
     * Database creation sql statement
     * 3. 정규스케줄변경정보테이블 생성 sql문
     * --> 미사용
     */        
    public static final String DATABASE_CREATE_NEWSCHEDULE =
	        "create table IF NOT EXISTS " + ComConstant.DATABASE_TABLE_NEWSCHEDULE  +
	        "(	_id 			integer 	primary key autoincrement, " +
	        "	scheduleid		integer		not null, 	" +
	        "	olddate			text		not null, 	" +
	        "	newdate			text		not null, 	" +
	        "	oldstarttime	text		not null, 	" +
	        "	oldendtime		text		not null, 	" +
	        "	newstarttime	text		not null, 	" +
	        "	newendtime		text		not null,	" +
            "	confirmdate		text		not null,	" +
            "	modifydate		text		not null	" + 
            " );";
    
    /**
     * Database creation sql statement
     * 4. 기념일테이블 생성 sql문
     */ 
    
    public static final String DATABASE_CREATE_SPECIALDAY =
	        "create table IF NOT EXISTS " + ComConstant.DATABASE_TABLE_SPECIALDAY  +
	        "(	_id 		integer 	primary key autoincrement, " +
	        "	locale		text		not null, 	" +
	        "	gubun		text		not null, 	" +
	        "	event		text		not null, 	" +
	        "	name		text		    	, 	" +
	        "	subname		text		    	, 	" +
	        "	holidayyn	text		    	, 	" +
	        "	repeatyn	text		    	, 	" +
	        "	year		text		    	, 	" +
	        "	monthday	text		not null, 	" +	 
	        "	leap		text		    	,	" +
	        "	memo		text		    	,	" +
	        "	delyn		text		 		,	" +
            "	deldate		text		 		,	" +
            "	confirmdate	text		not null,	" +
            "	modifydate	text		not null,	" +
            "	usergroup	text		 		,	" +
            "	alarm		text		 	 	" +
            " );";
//    public static final String DATABASE_ALTER_SPECIALDAY_V2 =
//        "alter table " + ComConstant.DATABASE_TABLE_SPECIALDAY  +
//        " add 		"  +
//        " 	event		text	" +
//        " ; ";    
    public static final String DATABASE_CREATE_IDX1_SPECIALDAY =
	        "create index IF NOT EXISTS " + ComConstant.DATABASE_INDEX1_SPECIALDAY +
	        "       				ON  " + ComConstant.DATABASE_TABLE_SPECIALDAY  +
	        "(	monthday	asc, 	" +
	        "	year		asc, 	" +
	        "	repeatyn	asc,    " + 
	        "	locale		asc,    " + 
	        "	gubun		asc	    " + 
	        " );";  
    public static final String DATABASE_CREATE_IDX2_SPECIALDAY =
        "create index IF NOT EXISTS " + ComConstant.DATABASE_INDEX2_SPECIALDAY +
        "       				ON  " + ComConstant.DATABASE_TABLE_SPECIALDAY  +
        "(	locale	    asc, 	" +
        "	gubun		asc  	" + 
        " );";   
    /**
     * Database creation sql statement
     * 5. 할일메모 관리(todolist)
     */  
    
    public static final String DATABASE_CREATE_TODOMEMO =
	        "create table IF NOT EXISTS " + ComConstant.DATABASE_TABLE_TODOMEMO  +
	        "(	_id 		integer 	primary key autoincrement, " +
	        "	memo		text		not null, 	" +
	        "	yearmonth	text		not null, 	" +
	        "	termyn		text		    	, 	" +
	        "	finishterm	text		    	, 	" +
	        "	finish		text		    	, 	" +
            "	confirmdate	text		not null,	" +
            "	modifydate	text		not null,	" + 
            "	alarm		text		 	 		" +
            
            " );";
//    public static final String DATABASE_CREATE_IDX1_TODOMEMO =
//        "create index IF NOT EXISTS " + ComConstant.DATABASE_INDEX1_TODOMEMO +
//        "       				ON  " + ComConstant.DATABASE_TABLE_TODOMEMO  +
//        "(	solar	asc, 	" +
//        "	lunar	asc, 	" +
//        "	leap	    	" + 
//        " );";
    
    /**
     * Database creation sql statement
     * 6. 공지 테이블
     */        
    public static final String DATABASE_CREATE_NOTICE =
	        "create table IF NOT EXISTS " + ComConstant.DATABASE_TABLE_NOTICE  +
	        "(	_id 		integer 	primary key autoincrement, " +
	        "	key			text		not null, 	" +
	        "	locale		text		not null, 	" +
	        "	appid		text		not null, 	" +
	        "	title		text		not null,	" + 
	        "	content		text		not null,	" + 
	        "	applydate	text		not null,	" + 
	        "	version		text		not null,	" + 
	        "	readdate	text		 		,	" +
            "	confirmdate	text		not null,	" +
            "	modifydate	text		not null	" + 
            " );";
    public static final String DATABASE_CREATE_IDX1_NOTICE =
	        "create index IF NOT EXISTS " + ComConstant.DATABASE_INDEX1_NOTICE +
	        "       				ON  " + ComConstant.DATABASE_TABLE_NOTICE  +
	        "(	appid	desc 	" +
	        " );";

   
    /**
     * Database creation sql statement
     * 10. 음력데이터 테이블
     */        
    public static final String DATABASE_CREATE_LUNARDATA =
	        "create table IF NOT EXISTS " + ComConstant.DATABASE_TABLE_LUNARDATA  +
	        "(	_id 		integer 	primary key autoincrement, " +
	        "	leap		text		not null, 	" +
	        "	solar		text		not null, 	" +
	        "	lunar		text		not null,	" + 
	        "	sixtyyear		text	not null,	" + 
	        "	sixtymonth		text	not null,	" + 
	        "	sixtyday		text	not null,	" + 
	        "	solarterms		text				" +
            " );";
    public static final String DATABASE_CREATE_IDX1_LUNARDATA =
	        "create index IF NOT EXISTS " + ComConstant.DATABASE_INDEX1_LUNARDATA +
	        "       				ON  " + ComConstant.DATABASE_TABLE_LUNARDATA  +
	        "(	solar	asc, 	" +
	        "	lunar	asc, 	" +
	        "	leap	    	" + 
	        " );";

//    /**
//     * Database creation sql statement
//     * 11. 할일메모월완료상세
//     */   
//    public static final String DATABASE_CREATE_TODOFINISH =
//	        "create table IF NOT EXISTS " + ComConstant.DATABASE_TABLE_TODOFINISH  +
//	        "(	_id 		integer 	primary key autoincrement, " +
//	        "	todoid		integer		not null, 	" +
//	        "	yearmonth	text		not null, 	" +
//	        "	finish		text		not null, 	" +    
//            "	confirmdate	text		not null,	" +
//            "	modifydate	text		not null	" + 
//            " );";
//    public static final String DATABASE_CREATE_IDX1_TODOFINISH =
//	        "create index IF NOT EXISTS " + ComConstant.DATABASE_INDEX1_TODOFINISH +
//	        "       				ON  " + ComConstant.DATABASE_TABLE_TODOFINISH  +
//	        "(	todoid		asc, 	" +
//	        "	yearmonth	    	" + 
//	        " );";

    
    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, ComConstant.DATABASE_NAME, null, ComConstant.DATABASE_VERSION);
        }

        @Override
        /*
         * Table 일괄생성
         */
        public void onCreate( SQLiteDatabase db ) {
        	try {
        		//1.table create
        		onCreateTable(db);
	            //index create
	            onCreateIdx(db);
	            
            
        	} catch (SQLiteException e) { 
        		Log.w(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>  DB CREATE ERR!!" );
        	}
        }
        /*
         * TABLE 일괄생성
         */
        public void onCreateTable( SQLiteDatabase db ) {
        	try {
        		db.execSQL(DATABASE_CREATE_INSTALLCHECK);
        		db.execSQL(DATABASE_CREATE_USERMANAGER);
        		db.execSQL(DATABASE_CREATE_SCHEDULE);
        		db.execSQL(DATABASE_CREATE_SPECIALDAY);
        		db.execSQL(DATABASE_CREATE_NEWSCHEDULE);
        		db.execSQL(DATABASE_CREATE_LUNARDATA);
        		db.execSQL(DATABASE_CREATE_TODOMEMO);
        		db.execSQL(DATABASE_CREATE_NOTICE);
            
        	} catch (SQLiteException e) { 
        		Log.w(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>  DB TABLE CREATE ERR!!" );
        	}
        }             
        /*
         * INDEX 일괄생성
         */
        public void onCreateIdx( SQLiteDatabase db ) {
        	try {
        		
        		db.execSQL(DATABASE_CREATE_IDX1_USERMANAGER);
        		db.execSQL(DATABASE_CREATE_IDX1_SCHEDULE);
        		db.execSQL(DATABASE_CREATE_IDX2_SCHEDULE);
        		db.execSQL(DATABASE_CREATE_IDX1_SPECIALDAY);
        		db.execSQL(DATABASE_CREATE_IDX2_SPECIALDAY);
        		db.execSQL(DATABASE_CREATE_IDX1_LUNARDATA);
        		db.execSQL(DATABASE_CREATE_IDX1_NOTICE);
            
        	} catch (SQLiteException e) { 
        		Log.w(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>  DB INDEX CREATE ERR!!" );
        	}
        }       	
        @Override
        //Table and Index  : Upgrade ( db 버전 변경시 테이블변경만 가능)
        //버전별로 변경테이블 정보 반영 ( history 유지 해야함...)
        public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
            Log.i(	TAG, 
            		">>>>>>>>> SMCalendar : Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            
            //version 1 --> 2(2012.5.5)
            if ( oldVersion == 1 && newVersion == 2 ) {
            	
            	//기존테이블 변경 (기념일,할일)
            	addTableCoulume(db, ComConstant.DATABASE_TABLE_SPECIALDAY, "usergroup", "text");
            	addTableCoulume(db, ComConstant.DATABASE_TABLE_SPECIALDAY, "alarm", 	"text");
            	
            	addTableCoulume(db, ComConstant.DATABASE_TABLE_TODOMEMO, "alarm", 		"text");
            	
            	//스케줄테이블 변경
            	addTableCoulume(db, ComConstant.DATABASE_TABLE_SCHEDULE, "repeatdate", 	"text");
            	addTableCoulume(db, ComConstant.DATABASE_TABLE_SCHEDULE, "alarm2", 		"text");            	
            	
//            	//신규테이블 생성
//            	db.execSQL( DATABASE_CREATE_TODOFINISH );
//            	db.execSQL(DATABASE_CREATE_IDX1_TODOFINISH);
            } 
            
//            //임시업데이트 (프로그램 버전으로 체크) : 3버전 이하인 경우 수행
//            if ( oldVersion <= 2 && newVersion == 3 ) {
//            	updateJustOneLow(db,  ComConstant.DATABASE_TABLE_SPECIALDAY, columname, condition, value)
//            }
            
        }
        
        /*
         * 컬럼추가
         */
        public void addTableCoulume( SQLiteDatabase db, String tablename, String columname, String type ) {

        	db.execSQL(	" ALTER TABLE   " + tablename +
        				" ADD " + columname + " " + type +  " " +  
        				"; ");
        }
        /*
         * 업데이트
         */
        public void updateJustOneRow( SQLiteDatabase db, String tablename, String columname, String condition, String value ) {

        	db.execSQL(	" UPDATE " + tablename +
        				" SET    " + columname + " = '" + value + "' " +        	
        				" WHERE  " + columname + " = '" + condition + "' " +        				
        				"; ");
        } 
        
//        /*
//         * 값추가
//         */
//        public void insertJustOneRow( SQLiteDatabase db, String tablename, String columname, String condition, String value ) {
//
//        	db.execSQL(	" UPDATE " + tablename +
//        				" SET    " + columname + " = '" + value + "' " +        	
//        				" WHERE  " + columname + " = '" + condition + "' " +        				
//        				"; ");
//        }        
//        private void checkDBVersion ( SQLiteDatabase db, int dbversion ) {
//        	
//        	//db비정상 반영시 재처리를 위한 로직
//        	if ( dbversion == 2 ) {
//            	//일별 스케줄 정보 db select
//        		try {
//        			db.execSQL(	" SELECT alarm2 FROM schedule ;");
//				} catch (SQLiteException e) {
//	            	//기존테이블 변경 (기념일,할일)
//	            	addTableCoulume(db, ComConstant.DATABASE_TABLE_SPECIALDAY, "usergroup", "text");
//	            	addTableCoulume(db, ComConstant.DATABASE_TABLE_SPECIALDAY, "alarm", 	"text");
//	            	
//	            	addTableCoulume(db, ComConstant.DATABASE_TABLE_TODOMEMO, "alarm", 		"text");
//	            	
//	            	//스케줄테이블 변경
//	            	addTableCoulume(db, ComConstant.DATABASE_TABLE_SCHEDULE, "repeatdate", 	"text");
//	            	addTableCoulume(db, ComConstant.DATABASE_TABLE_SCHEDULE, "alarm2", 		"text");     
//				}
//        		
//            	      		
//        	}
//
//        }        
//		/*
//		 * 데이터베이스 버전에 따른 처리 로직 일괄 처리
//		 * -.호출시점은 데이터베이스파일 정상다운로드후...
//		 * 1) version 2 
//		 */
//        //버전별로 변경테이블 정보 반영 ( history 유지 해야함...)
//        public void onUpdateAll( SQLiteDatabase db ) {
//            Log.i(	TAG, 
//            		">>>>>>>>> SMCalendar onUpdateAll : Upgrading database from version " + mOldVersion + " to "
//                    + mNewVersion + ", which will destroy all old data");
//            
//            //version 1 --> 2
//            if ( mOldVersion == 1 && mNewVersion == 2 ) {
//            	
//            	//기존테이블 변경
//            	db.execSQL(	" ALTER TABLE  "+ ComConstant.DATABASE_TABLE_SPECIALDAY +
//            				" ADD group  text " + 
//            				"; ");
//            			
//            	//신규테이블 생성
//            	db.execSQL( DATABASE_CREATE_TODOFINISH );
//            	db.execSQL(DATABASE_CREATE_IDX1_TODOFINISH);
//            }
//        }     
//        
        
        public void onDropTable( SQLiteDatabase db ) {
           
            db.execSQL("DROP TABLE IF EXISTS "+ ComConstant.DATABASE_TABLE_USERMANAGER );
            db.execSQL("DROP TABLE IF EXISTS "+ ComConstant.DATABASE_TABLE_SCHEDULE );
            db.execSQL("DROP TABLE IF EXISTS "+ ComConstant.DATABASE_TABLE_NEWSCHEDULE );
            db.execSQL("DROP TABLE IF EXISTS "+ ComConstant.DATABASE_TABLE_SPECIALDAY );
            db.execSQL("DROP TABLE IF EXISTS "+ ComConstant.DATABASE_TABLE_TODOMEMO );
            db.execSQL("DROP TABLE IF EXISTS "+ ComConstant.DATABASE_TABLE_NOTICE );
            //다량의 데이터로 우선 drop 은 안함... db정책 적용후 결정..
            //db.execSQL("DROP TABLE IF EXISTS "+ ComConstant.DATABASE_TABLE_LUNARDATA );
            
        }   
        public void onDropIndex( SQLiteDatabase db ) {
            
        	db.execSQL("DROP INDEX IF EXISTS "+ ComConstant.DATABASE_INDEX1_USERMANAGER);
            db.execSQL("DROP INDEX IF EXISTS "+ ComConstant.DATABASE_INDEX1_LUNARDATA );
            db.execSQL("DROP INDEX IF EXISTS "+ ComConstant.DATABASE_INDEX1_SCHEDULE);
            db.execSQL("DROP INDEX IF EXISTS "+ ComConstant.DATABASE_INDEX1_SPECIALDAY);
            db.execSQL("DROP INDEX IF EXISTS "+ ComConstant.DATABASE_INDEX2_SPECIALDAY);
            db.execSQL("DROP INDEX IF EXISTS "+ ComConstant.DATABASE_INDEX2_SCHEDULE);
            db.execSQL("DROP INDEX IF EXISTS "+ ComConstant.DATABASE_INDEX1_NOTICE);
        }         
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     *   param ctx the Context within which to work
     */
    public CreateDbAdaper(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public CreateDbAdaper open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
    	mDb = mDbHelper.getWritableDatabase();
    	checkDBVersion();
        return this;
    }
    
    public void close() {
    	
    	mDbHelper.close();
    }
    
   public void checkDBVersion( )  {
    	
    	Cursor mCursor = null;

    	if ( ViewUtil.isdatabaseExist ( mCtx ) && ComConstant.DATABASE_VERSION == 2 ) {
//    	if ( ComConstant.DATABASE_VERSION == 2 ) {
    	   	//table이 있는경우 데이터 존재시 true, 없는 경우 false    	
        	try {
            	//2버전 컬럼 있는지 확인후 없는 경우 변경로직 추가
            	mCursor = mDb.rawQuery(" SELECT alarm2 FROM schedule "  , null);        						  
            	mCursor.close();
            	
        	} catch (SQLException e) {   
        		//오류가 생겨도 정상처리하기 위해
        		if ( mCursor != null && !mCursor.isClosed()) mCursor.close();
        		
        		try {
                	//기존테이블 변경 (기념일,할일)
                	mDbHelper.addTableCoulume(mDb, ComConstant.DATABASE_TABLE_SPECIALDAY, "usergroup", "text");
            		mDbHelper.addTableCoulume(mDb, ComConstant.DATABASE_TABLE_SPECIALDAY, "alarm", 	"text");
                	
            		mDbHelper.addTableCoulume(mDb, ComConstant.DATABASE_TABLE_TODOMEMO, "alarm", 		"text");
                	
                	//스케줄테이블 변경
            		mDbHelper.addTableCoulume(mDb, ComConstant.DATABASE_TABLE_SCHEDULE, "repeatdate", 	"text");
            		mDbHelper.addTableCoulume(mDb, ComConstant.DATABASE_TABLE_SCHEDULE, "alarm2", 		"text");      			
          			
        		} catch (SQLException e1) { 
        			//오류나면 그냥 skip
        		}
 
    		}
        	
        	
        	
    	}
    }
    
}
