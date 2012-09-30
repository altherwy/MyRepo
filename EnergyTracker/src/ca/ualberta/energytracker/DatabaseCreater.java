package ca.ualberta.energytracker;

import java.text.DecimalFormat;
import java.util.Vector;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
// this class used to create and handle all DataBase operations
public class DatabaseCreater extends SQLiteOpenHelper{
	static final String DBName="EnergyTracker";
	public final String ActivityTable="Activity";
	public final String colID="Activity_ID";
	public final String colName="Activity_Name";
	public final String colDate="Activity_Date";
	public final String colStarting="Starting_Battery";
	public final String colEnding="Ending_Battery";
    public final String colSeconds="Seconds";
	 public final String ActivityView="View_Activity";
	 
	public DatabaseCreater(Context context) {
		super(context, DBName, null, 1);
		// TODO Auto-generated constructor stub
	}

	

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("Create Table "+ActivityTable+"("+colID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
		colName+" TEXT(30),"+colDate+" TEXT(20) ,"+ colStarting+" DOUBLE(10) ,"+colEnding+" DOUBLE(10) ,"
		+colSeconds+" DOUBLE(10) );");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	// this method gets the data stored in the DataBase
	public Cursor getRecords(){
		SQLiteDatabase DB = this.getReadableDatabase();
		Cursor cr = DB.rawQuery("SELECT * FROM Activity;", new String []{});
		return cr;
	}
	// this method inserts a new record into the Database
	public void saveRecord(String activity_name,double starting,double ending,String displaySeconds,String currentDate) throws Exception{
    	
    	SQLiteDatabase DB=this.getWritableDatabase();
    	ContentValues cv=new ContentValues();
    	cv.put (colName,activity_name);
    	cv.put(colStarting,starting);
    	cv.put(colEnding,ending);
    	cv.put(colSeconds,displaySeconds);
    	cv.put(colDate,currentDate);
    	DB.insert(ActivityTable , null, cv);
    	DB.close();
    	
    }
	// this method returns the overall battery used in % and overall seconds
	public Vector<Double> overallBatteryPres() throws Exception{
	    SQLiteDatabase DB = this.getReadableDatabase();
		Cursor cr = DB.rawQuery("SELECT Starting_Battery, Ending_Battery, SUM(Seconds) as seconds FROM Activity GROUP BY Starting_Battery, Ending_Battery ; ", new String []{});
		cr.moveToFirst();
		Vector<Double> batteryVector= new Vector<Double>(); 
		double startBattery=0;
		double endBattery=0;
		double seconds= cr.getDouble(2);
		int i=0;
		while(!cr.isAfterLast()){
			 startBattery += cr.getDouble(i);
			endBattery +=cr.getDouble(i+1);
			cr.moveToNext();
		}
		DecimalFormat dc = new DecimalFormat("#.##");
		DecimalFormat dc1 = new DecimalFormat("#.#");
		DecimalFormat dc2 = new DecimalFormat("###0.0");
		startBattery -= endBattery;
		dc.format(startBattery);
		dc1.format(seconds);
		batteryVector.add(startBattery);
		batteryVector.add(seconds);
		seconds /=3600;
		startBattery /=seconds;
		dc2.format(startBattery);
		batteryVector.add(startBattery);
		
		 return batteryVector;
		
	}
	

}
