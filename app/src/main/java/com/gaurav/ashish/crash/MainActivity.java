package com.gaurav.ashish.crash;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner s1, s2, s3;
    String sp3;
    String sp1;
    int j;
    String[] bike_arr = {"BAJAJ", "HERO", "HONDA", "KINETIC", "LML", "MAHINDRA", "VESPA", "ROYALENFIELD", "SUZUKI", "TVS", "YAMAHA"};
    String[] car_arr = {"HONDA", "ASHOKLEYLAND", "MAHINDRAHCV", "MAHINDRALCV", "PIAGIOLCV", "MCVTATA", "LCVTATA", "AUDI", "BMW", "CHEVROLET", "SKODA", "FORD", "VOLKSWAGEN", "TOYOTA", "FIAT", "HYUNDAI", "MAHINDRA", "RENAULT", "NISSAN", "TATA", "MARUTI"};
    //    String bike_company[] = getResources().getStringArray(R.array.bike_company);
//    String car_company[] = getResources().getStringArray(R.array.car_company);
    // Spinner Drop down elements
    List<String> list = new ArrayList<String>();
    List<String> listwb = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        s1 = (Spinner) findViewById(R.id.spinner1);
        s2 = (Spinner) findViewById(R.id.spinner2);
        s3 = (Spinner) findViewById(R.id.spinner3);
        s1.setOnItemSelectedListener(this);
        s2.setOnItemSelectedListener(this);
        s3.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        switch (arg0.getId()) {
            case R.id.spinner1:
                sp1 = valueOf(s1.getSelectedItem());
                for (String k : car_arr) {
                    if (sp1.contentEquals(k)) {

                        list=loadSpinnerData("C" + k, 0);
                        ArrayAdapter<String> dataAdapter20 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
                        dataAdapter20.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter20.notifyDataSetChanged();
                        s2.setAdapter(dataAdapter20);

                        listwb=loadSpinnerData("C" + k, 2);
                        break;
                    }
                }
                break;
            case R.id.spinner3:
                sp3 = valueOf(s3.getSelectedItem()); //table name of bike in database
                for (String k : bike_arr) {
                    if (sp3.contentEquals(k)) {

                        list=loadSpinnerData(k, 0);
                        ArrayAdapter<String> dataAdapter20 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
                        dataAdapter20.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter20.notifyDataSetChanged();
                        s2.setAdapter(dataAdapter20);

                        listwb=loadSpinnerData(k, 2);
                        break;
                    }
                }
                break;

            case R.id.spinner2:
                j = Integer.valueOf(listwb.get(arg2).toString());
            default:
                break;
        }

    }

    public void calcVel(View view) {
        EditText noFrame = (EditText) findViewById(R.id.edit_no_of_frame);
        float noFrameInt = Float.parseFloat(noFrame.getText().toString());
        EditText videoFrame = (EditText) findViewById(R.id.edit_video_Frame);
        float videoFrameInt = Float.parseFloat(videoFrame.getText().toString());
        float velocity = videoFrameInt / noFrameInt * j * 18 / 5000;
        TextView vel = (TextView) findViewById(R.id.vel);
        vel.setText(Float.toString(velocity) + " kmph");
    }

    private List<String> loadSpinnerData(String tableName, int columnPos) {
        TestAdapter mDbHelper = new TestAdapter(getApplicationContext());
        mDbHelper.createDatabase();
        mDbHelper.open();
        List<String> arr=new ArrayList<>();
//        list.clear();
        Cursor testdata = mDbHelper.getTestData(tableName);
        testdata.moveToFirst();
        do {
            arr.add(testdata.getString(columnPos));
        } while (testdata.moveToNext());
        testdata.close();
        mDbHelper.close();
        return arr;
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
