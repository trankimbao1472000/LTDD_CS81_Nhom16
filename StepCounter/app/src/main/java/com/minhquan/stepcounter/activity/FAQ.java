package com.minhquan.stepcounter.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.minhquan.stepcounter.R;
import com.minhquan.stepcounter.utils.SharedPreferencesUtils;

public class FAQ extends AppCompatActivity {
    TextView txtT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq);
        final TextView t = (TextView) findViewById(R.id.twfaq);
        t.setText("- Ứng Dụng tự động đếm số bước chân của bạn khi được cài và sẽ lưu dữ liệu khi bạn nhấn nút "+
                "SAVE hoặc khởi động lại khi ấn RESET. \n" + "- Bạn có thể vào MenuBar chọn Settings để chỉnh sáng tối hoặc chọn History để xem lịch sử đã lưu.");
    }
    private void Load_setting(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
    }
    @Override
    protected void onResume() {
        Load_setting();
        super.onResume();
    }

}
