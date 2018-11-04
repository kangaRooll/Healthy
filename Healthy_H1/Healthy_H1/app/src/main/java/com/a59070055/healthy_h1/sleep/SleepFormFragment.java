package com.a59070055.healthy_h1.sleep;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.a59070055.healthy_h1.R;

public class SleepFormFragment extends Fragment {
    private SQLiteDatabase db;
    private Sleep sleep;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        db = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);
        sleep = Sleep.getSleepInstance();

        initsaveButton();
        initBackButton();
        if(sleep != null) {
            loadData();
        }
    }
    void initBackButton(){
        Button _backBtn = getView().findViewById(R.id.sleep_form_back_btn);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new SleepFragment()).addToBackStack(null).commit();
            }
        });
    }
    void initsaveButton(){
        Button _saveBtn = getView().findViewById(R.id.sleep_form_save_btn);

        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText _dateTxt = getView().findViewById(R.id.sleep_form_date);
                EditText _timetosleepTxt = getView().findViewById(R.id.sleep_form_start_sleep);
                EditText _timetowakeupTxt = getView().findViewById(R.id.sleep_form_end_sleep);

                String _dateStr = _dateTxt.getText().toString();
                String _timetosleepStr = _timetosleepTxt.getText().toString();
                String _timetowakeupStr = _timetowakeupTxt.getText().toString();

                if(_dateStr.isEmpty() || _timetosleepStr.isEmpty() || _timetowakeupStr.isEmpty()) {
                    Toast.makeText(getActivity(), "กรุณาใส่ข้อมูลให้ครบถ้วน", Toast.LENGTH_LONG).show();

                } else {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("currentdate", _dateStr);
                    contentValues.put("timetosleep", _timetosleepStr);
                    contentValues.put("timetowakeup", _timetowakeupStr);

                    String[] _timetosleepSplit = _timetosleepStr.split(":");
                    String[] _timetowakeupSplit = _timetowakeupStr.split(":");

                    int _timetosleepHourInt = Integer.parseInt(_timetosleepSplit[0]);
                    int _timetosleepMinInt = Integer.parseInt(_timetosleepSplit[1]);
                    int _timetowakeupHourInt = Integer.parseInt(_timetowakeupSplit[0]);
                    int _timetowakeupMinInt = Integer.parseInt(_timetowakeupSplit[1]);

                    int calculateHour = Math.abs(24 - _timetosleepHourInt);
                    int calculateMin = Math.abs(00 - _timetosleepMinInt);

                    int resultHour = calculateHour + _timetowakeupHourInt;
                    int resultMin = calculateMin + _timetowakeupMinInt;

                    String resultMinStr;
                    if(resultMin == 0) {
                        resultMinStr = "00";
                        contentValues.put("counttime", resultHour + ":" + resultMinStr);
                    } else {
                        contentValues.put("counttime", resultHour + ":" + resultMin);
                    }

                    if(sleep.getPrimaryId() == 0) {
                        db.insert("sleep", null, contentValues);
                    } else {
                        db.update("sleep", contentValues, "_id=" + sleep.getPrimaryId(), null);
                    }

                    Toast.makeText(getActivity(), "เพิ่มข้อมูลเรียบร้อย", Toast.LENGTH_LONG).show();

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new SleepFragment()).commit();
                }
            }});
        }


        private void  loadData(){
            EditText _dateTxt = getView().findViewById(R.id.sleep_form_date);
            EditText _timetosleepTxt = getView().findViewById(R.id.sleep_form_start_sleep);
            EditText _timetowakeupTxt = getView().findViewById(R.id.sleep_form_end_sleep);

            _dateTxt.setText(sleep.getCurrentDate());
            _timetosleepTxt.setText(sleep.getTimetosleep());
            _timetowakeupTxt.setText(sleep.getTimetowakeup());
    }

}
