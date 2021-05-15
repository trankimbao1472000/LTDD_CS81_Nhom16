package com.minhquan.stepcounter.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.minhquan.stepcounter.R;
import com.minhquan.stepcounter.utils.SharedPreferencesUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class History extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    DatePickerDialog datediag;
    TextView txtT, txtHistory;
    String datepick = "";
    SharedPreferencesUtils share;


    private void Load_setting() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        initialize();
        findViewById(R.id.toggleButton).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                showDateDialog();
                showHistory();
            }
        });



    }

    private void initialize() {
        txtT = findViewById(R.id.txtTest);
        txtHistory = findViewById(R.id.txtHistory);
        share = new SharedPreferencesUtils(this);

    }

    private void showDateDialog() {
        int nam = Calendar.getInstance().get(Calendar.YEAR);
        int thang = Calendar.getInstance().get(Calendar.MONTH);
        int ngay =  Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        datediag = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year,month,dayOfMonth);
                SimpleDateFormat s = new SimpleDateFormat("dd/MM/yy");
                datepick = s.format(c.getTime());
                txtT.setText(datepick);
            }
        },nam,thang,ngay);

        datediag.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<String> getHistory(){
        Map<String,?> map = share.getAll();
        List<String> list = new ArrayList<>();

        for (Map.Entry<String, ?> entry : map.entrySet()) {
            //System.out.println(entry.getKey() + ":" + entry.getValue());
            if(entry.getKey().contains(datepick)){
                list.add(entry.getKey() );
                list.add(entry.getValue().toString());
            }
        }

        return list;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showHistory(){
        List<String> list = (ArrayList)getHistory();
        String history = "";
        StringBuilder builder = new StringBuilder(history);

        for(int i = 0; i < list.size() ; i++){
            if(i % 2 == 0){
                builder.append(list.get(i) + " : ");
            }else{
                builder.append(list.get(i));
                builder.append("\n");
            }



            //builder.append(list.get(i+1) + "\n");
        }
        txtHistory.setText(builder);
    }
    @Override
    protected void onResume() {
        Load_setting();
        super.onResume();
    }


}