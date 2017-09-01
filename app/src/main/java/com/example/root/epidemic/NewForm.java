package com.example.root.epidemic;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

public class NewForm extends BaseActivity
{
    private Spinner age_spinner;
    private String[] ageCategories;
    private TextView date_tv,location_et;
    private EditText name_et;
    private final DecimalFormat dec_format=new DecimalFormat("#");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_form);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setTitle("New Form");
        }
        setSpinner();
        date_tv=(TextView)findViewById(R.id.date_tv);
        name_et=(EditText)findViewById(R.id.name_et);
        location_et=(TextView)findViewById(R.id.location_et);
        location_et.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int requestCode=1;
                startActivityForResult(new Intent(getApplicationContext(),MapActivity.class),requestCode);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode==1)
        {
            if(resultCode==RESULT_OK)
            {
                location_et.setText(data.getData().toString());
            }
        }


    }

    public void setDate(View view)
    {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        String day=dec_format.format(dayOfMonth);
                        String month=dec_format.format(monthOfYear+1);
                        String yearS=dec_format.format(year);
                        if(dayOfMonth<10)
                            day="0"+day;
                        if(monthOfYear<9)
                            month="0"+month;
                        if(year<10)
                            yearS="200"+yearS;
                        if(year<100)
                            yearS="20"+yearS;

                        date_tv.setText(day + "-" + month + "-" + yearS);
                        date_tv.setError(null);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void setSpinner()
    {
        ageCategories=getResources().getStringArray(R.array.Age_Categories);
        age_spinner=(Spinner)findViewById(R.id.age_spinner);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,ageCategories);
        age_spinner.setAdapter(adapter);
    }

    public void Submit(View view)
    {
        ConnectivityManager cm =(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(!isConnected)
        {
            Toast.makeText(this,"You need to be connected to internet to submit a new request",Toast.LENGTH_SHORT).show();
            return;
        }
        if(validateData())
        {
            String name=name_et.getText().toString();
            String age=age_spinner.getSelectedItem().toString();
            String location=location_et.getText().toString();
            String date=date_tv.getText().toString();

            final String nameEntry="entry.1347838367=",ageEntry="entry.1325273191=",locationEntry="entry.1974303013=",dateEntry="entry.2136146747=";
            try
            {
                name = URLEncoder.encode(name, "UTF-8");
                location = URLEncoder.encode(location, "UTF-8");
                age = URLEncoder.encode(age, "UTF-8");
                date = URLEncoder.encode(date, "UTF-8");
            } catch (UnsupportedEncodingException e)
            {
                Log.d("URL","Couldn't encode");
            }
            String dataToPost =nameEntry+name+"&"+ageEntry+age+"&"+locationEntry+location+"&"+dateEntry+date;

            new formSubmitClient().execute("https://docs.google.com/forms/d/1O2H_PBLWZTU6MO-0OJe-9ErsG5s2jXwPydHVszrMSjI/formResponse",dataToPost);

            Toast.makeText(this,"Your response has been submitted",Toast.LENGTH_SHORT).show();
            finish();

        }
    }

    private boolean validateData()
    {
        String name=name_et.getText().toString();
        String location=location_et.getText().toString();
        String date=date_tv.getText().toString();
        if(name.equals(""))
        {
            name_et.setError("Required");
            return false;
        }
        if(location.equals(""))
        {
            location_et.setError("Required");
            return false;
        }
        if(date.equals(""))
        {
            date_tv.setError("Required");
            return false;
        }
        return  true;
    }
}
