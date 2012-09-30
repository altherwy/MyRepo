package ca.ualberta.energytracker;

import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddEntryActivity extends Activity {
private int year;
private int month;
private int day;
EditText seconds ;
TextView displaySeconds;
EditText minutes;
EditText hours;
EditText activity_name;
EditText starting;
EditText ending;
TextView currentDate;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
       currentDate = (TextView) findViewById(R.id.displaycurrentdate);
      final Calendar calendar = Calendar.getInstance();
      year = calendar.get(Calendar.YEAR);
      month = calendar.get(Calendar.MONTH);
      day = calendar.get(Calendar.DAY_OF_MONTH);
     currentDate.setText(new StringBuilder().append(month + 1)
			   .append("-").append(day).append("-").append(year)
			   .append(" "));
     
     //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
      
      
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_entry, menu);
        return true;
    }
    // this method populates DropDown menu with values //
    public void populateSpinner(){
    	/*Spinner days = (Spinner) findViewById(R.id.displaydays);
    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.days, android.R.layout.simple_spinner_item);
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	days.setAdapter(adapter);*/
    }
    // this method shows a date dialog //
    public void showDateDialog(View V){
    	 DialogFragment DF = new DatePicker(this);
         DF.show(getFragmentManager(), "MyDatePicker");
    }
    // this method converts the given times(hour,minutes) to seconds then display it in the screen
    public void CalculateSeconds(View V){
    	
    	 displaySeconds = (TextView) findViewById(R.id.seconds);
         hours = (EditText) findViewById(R.id.consumptionhour);
         minutes = (EditText) findViewById(R.id.consumptionminute);
         seconds = (EditText) findViewById(R.id.consumptionsecond);
         Integer h= Integer.parseInt(hours.getText().toString());
         Integer m = Integer.parseInt(minutes.getText().toString());
         Integer s = Integer.parseInt(seconds.getText().toString());
         h*=3600;
         m*=60;
         h=h+m+s;
         displaySeconds.setText(new StringBuilder().append(h).append("s"));
         
    }
    // Save records to DataBase
    public void saveRecords(View v){
    	try {
    	DatabaseCreater DBC = new DatabaseCreater(this);
    	SQLiteDatabase DB=DBC.getWritableDatabase();
    	//ContentValues cv=new ContentValues();
    	currentDate = (TextView) findViewById(R.id.displaycurrentdate);
    	activity_name = (EditText) findViewById(R.id.description);
    	starting = (EditText) findViewById(R.id.startingbattery);
    	ending=(EditText) findViewById(R.id.endingbattery);
    	displaySeconds = (TextView) findViewById(R.id.seconds);
    	DBC.saveRecord(activity_name.getText().toString(), Double.parseDouble(starting.getText().toString()), Double.parseDouble(ending.getText().toString()), displaySeconds.getText().toString(), currentDate.getText().toString());
    	/*cv.put (DBC.colName,activity_name.getText().toString() );
    	cv.put(DBC.colStarting,Integer.parseInt(starting.getText().toString()));
    	cv.put(DBC.colEnding,Integer.parseInt(Ending.getText().toString()));
    	cv.put(DBC.colSeconds,displaySeconds.getText().toString());
    	cv.put(DBC.colDate,currentDate.getText().toString());
    	DB.insert(DBC.ActivityTable , null, cv);*/
    	DB.close();
    	}
    	catch(Exception e){
    		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    		dialog.setMessage(e.getMessage());
    		dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "Please correct your entries", Toast.LENGTH_SHORT).show();
				}
			});
    		dialog.show();
    	}
    	
    }
    
}
