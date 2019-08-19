package com.single.layoutinflaterdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout layout = findViewById(R.id.main);
        Log.d(TAG, "R.id.main:" + R.id.main);
        View inflate = getLayoutInflater().inflate(R.layout.inflate1, layout, true);
//        layout.addView(inflate);
        Log.d(TAG, "inflate:" + inflate.getId());
//        ViewGroup.LayoutParams lp = inflate.getLayoutParams();
//        Log.d(TAG, "lp is null:" + (lp == null));
////        layout.addView(inflate);
////        Log.d(TAG, "add view========");
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) inflate.getLayoutParams();
//        Log.d(TAG, "layoutParams_width:" + layoutParams.width);
//        Log.d(TAG, "layoutParams_height:" + layoutParams.height);
    }
}
