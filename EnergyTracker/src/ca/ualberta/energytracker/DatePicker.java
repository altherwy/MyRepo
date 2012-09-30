package ca.ualberta.energytracker;

import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.TextView;

public  class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{
	
  Activity AC ;
TextView dataTextView;
public DatePicker (Activity ac){
	AC = ac;
}
     public Dialog onCreateDialog(Bundle savedInstanceState) {
         final Calendar c= Calendar.getInstance();
         int year  = c.get(Calendar.YEAR);
         int month = c.get(Calendar.MONTH);
         int day =  c.get(Calendar.DAY_OF_MONTH);
         return new DatePickerDialog(getActivity(), this, year, month, day);
     }

	@Override
	// this method used to display the selected date in a text view //
	public void onDateSet(android.widget.DatePicker view, int year,
			int monthOfYear, int dayOfMonth) {
		// TODO Auto-generated method stub
		dataTextView = (TextView) AC.findViewById(R.id.displaycurrentdate);
		dataTextView.setText(new StringBuilder().append(monthOfYear + 1)
				   .append("-").append(dayOfMonth).append("-").append(year)
				   .append(" "));
	
		}
	

}
