package com.single.layoutinflaterdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.single.layoutinflaterdemo.apt.BinderViewTools;
import com.single.layoutinflaterdemo.bus.EventBus;
import com.single.layoutinflaterdemo.bus.onEvent;
import com.single.router_annimator.BindView;

public class A_Activity extends AppCompatActivity {
    @BindView(R.id.button)
    public Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        BinderViewTools.init(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(A_Activity.this, B_Activity.class));
            }
        });
//        button = findViewById(R.id.button);
        View onEvent = findViewById(R.id.onEvent);
        onEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(A_Activity.this);
            }
        });

        EventBus.getDefault().register(this);
//        AnnotationUtils.inject(this);
    }

    @onEvent
    public void onEventMethod() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("我是注解调用的方法");
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
