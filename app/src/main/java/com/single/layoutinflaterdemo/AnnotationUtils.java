package com.single.layoutinflaterdemo;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.single.layoutinflaterdemo.anotation.IntentAnnotation;

import java.lang.reflect.Field;

public class AnnotationUtils {
    private static final String TAG = "AnnotationUtils";

    public static void inject(final Activity activity) {
        Class<? extends Activity> aClass = activity.getClass();
        //获取class文件所有的属性集合
        Field[] fields = aClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            //判断当前属性是不是有想要的注解
            if (field.isAnnotationPresent(IntentAnnotation.class)) {
                final IntentAnnotation annotation = field.getAnnotation(IntentAnnotation.class);
                Log.d(TAG, "field.getGenericType().toString():" + field.getGenericType().toString());
                //判断下属性的类型
                if (field.getGenericType().toString().startsWith(
                        "class android.widget.")) {
                    try {
                        //此处就是要干的事情
                        View view = (View) field.get(activity);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(activity, annotation.value());
                                activity.startActivity(intent);
                            }
                        });
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else {
                    throw new RuntimeException("the field must be a view");
                }
            }

        }
    }

}
