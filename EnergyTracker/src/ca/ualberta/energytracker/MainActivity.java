package ca.ualberta.energytracker;

import java.util.Vector;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "ca.ualberta.energytracker.MESSAGE";
	
	
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    // this method starts new activity
    public void addEntry (View v){
    	
    	Intent i = new Intent(this, AddEntryActivity.class);
    	startActivity(i);
    	
    }
    public void showmessage(View v) {
    	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    	try{
    	
    	DatabaseCreater DB = new DatabaseCreater(this);
    	Vector<Double> bb =DB.overallBatteryPres();
		dialog.setMessage(bb.get(1).toString()+"\n"+bb.get(0).toString()+"\n"+bb.get(2).toString());
		dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Please correct your entries", Toast.LENGTH_SHORT).show();
			}
		});
		dialog.show();
    	}
    	catch(Exception e){
    		dialog.setMessage(e.getMessage());
    	}
    }
    
}
