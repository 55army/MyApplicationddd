package com.example.com.myapplication;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    DatePicker dp;
    EditText edtDiary;
    Button btnWrite;
    String fileName;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("간단일기장");

        dp = (DatePicker)findViewById(R.id.datePicker1);
        edtDiary  = (EditText)findViewById(R.id.edtDiary);
        btnWrite = (Button)findViewById(R.id.btnWrite);

        Calendar cal = Calendar.getInstance();
        int cYear = cal.get(Calendar.YEAR);
        int cMonth = cal.get(Calendar.MONTH);
        int cDay = cal.get(Calendar.DAY_OF_MONTH);

        fileName = Integer.toString(cYear) + "_" + Integer.toString(cMonth)
                + "_" + Integer.toString(cDay) + ".txt";
        String str = readDiary(fileName);
        edtDiary.setText(str);


        dp.init(cYear,cMonth,cDay, new DatePicker.OnDateChangedListener() {

            public void onDateChanged(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                // TODO Auto-generated method stub
                fileName = Integer.toString(year) + "_"
                        +  Integer.toString(monthOfYear+1) + "_"
                        +  Integer.toString(dayOfMonth) + ".txt";
                String str = readDiary(fileName);
                edtDiary.setText(str);
                btnWrite.setEnabled(true);
            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                // TODO Auto-generated method stub
                try{
                    FileOutputStream outFs = openFileOutput(fileName, MODE_WORLD_WRITEABLE);
                    String str = edtDiary.getText().toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                    Toast.makeText(getApplicationContext(), fileName+"이 저장됨", 0).show();


                }catch (IOException e) {
                    // TODO: handle exception
                }
            }
        });
    }

    String readDiary(String fName){
        String diaryStr = null;
        FileInputStream inFs;
        try{
            inFs = openFileInput(fName);
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            diaryStr = (new String(txt)).trim();
            btnWrite.setText("수정하기");
        }catch(IOException e){

            edtDiary.setHint("일기 내용");
            btnWrite.setText("새로저장");

        }

        return diaryStr;


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
